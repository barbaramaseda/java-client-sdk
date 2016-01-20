/*******************************************************************************
 * Copyright (c) cortical.io GmbH. All rights reserved.
 *  
 * This software is confidential and proprietary information.
 * You shall use it only in accordance with the terms of the
 * license agreement you entered into with cortical.io GmbH.
 ******************************************************************************/
package io.cortical.retina.client.core;

import io.cortical.retina.service.ApiException;
import io.cortical.retina.service.ApiInvoker;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import org.mockito.ArgumentMatcher;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.argThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


/**
 * Test Utils.
 * 
 */
public abstract class ApiTestUtils {
    /**
     * 
     */
    public static final String NOT_NULL_RETINA = "en_associative";
    public static final String NOT_NULL_BASE_PATH = "base_path";
    public static final String NOT_NULL_API_KEY = "some_key";
    
    /**
     * Set a {@link ApiInvoker} to a retina's API for a test purposes.
     * 
     * @param apiInvoker : invoker to set.
     * @param api : api to be changed. 
     * @throws SecurityException 
     * @throws NoSuchFieldException 
     * @throws IllegalAccessException 
     * @throws IllegalArgumentException 
     */
    public static void setApiInvoker(ApiInvoker apiInvoker, Object api) throws NoSuchFieldException, SecurityException,
            IllegalArgumentException, IllegalAccessException {
        Field field = api.getClass().getDeclaredField("apiInvoker");
        field.setAccessible(true);
        field.set(api, apiInvoker);
    }
    
    /**
     * Prepare Api's POST mocked method. 
     * 
     * @param requestBody : request body.
     * @param responseJson : response body.
     * @param apiInvoker : apiInvoker mock.
     * @throws ApiException : should never be thrown.
     */
    @SuppressWarnings("unchecked")
    public static void prepareApiPostMethod(String requestBody, Object responseJson, ApiInvoker apiInvoker)
            throws ApiException {
        when(
                apiInvoker.invokeAPI(eq(NOT_NULL_BASE_PATH), any(String.class), any(String.class), any(Map.class),
                        eq(requestBody), any(Map.class), any(String.class))).thenReturn(responseJson);
    }
    
    
    /**
     * Prepare Api's GET mocked method. 
     * 
     * @param requestArguments : request's arguments.
     * @param responseJson : response body.
     * @param apiInvoker : apiInvoker mock.
     * @throws ApiException : should never be thrown.
     */
    @SuppressWarnings("unchecked")
    public static void prepareApiGetMethod(final Map<String, String> requestArguments, String responseJson,
            ApiInvoker apiInvoker) throws ApiException {
        ArgumentMatcher<Map<String, String>> matcher = requestParamMap(requestArguments);
        when(
                apiInvoker.invokeAPI(eq(NOT_NULL_BASE_PATH), any(String.class), any(String.class), argThat(matcher),
                        any(String.class), any(Map.class), any(String.class))).thenReturn(responseJson);
    }
    
    /**
     * Verify Api's POST mocked method.
     * 
     * @param requestBody : request body.
     * @param apiInvoker : apiInvoker mock.
     * @throws ApiException : should never be thrown.
     */
    @SuppressWarnings("unchecked")
    public static void verifyApiPostMethod(String requestBody, ApiInvoker apiInvoker) throws ApiException {
        verify(apiInvoker, times(1)).invokeAPI(eq(NOT_NULL_BASE_PATH), any(String.class), any(String.class),
                any(Map.class), eq(requestBody), any(Map.class), any(String.class));
    }
    
    /**
     * Verify Api's Get mocked method.
     * 
     * @param requestArguments : request's arguments.
     * @param apiInvoker : apiInvoker mock.
     * @throws ApiException : should never be thrown.
     */
    @SuppressWarnings("unchecked")
    public static void verifyApiGetMethod(final Map<String, String> requestArguments, ApiInvoker apiInvoker)
            throws ApiException {
        ArgumentMatcher<Map<String, String>> matcher = requestParamMap(requestArguments);
        verify(apiInvoker, times(1)).invokeAPI(eq(NOT_NULL_BASE_PATH), any(String.class), any(String.class),
                argThat(matcher), any(String.class), any(Map.class), any(String.class));
    }
    
    
    private static ArgumentMatcher<Map<String, String>> requestParamMap(final Map<String, String> requestArguments) {
        ArgumentMatcher<Map<String, String>> matcher = new ArgumentMatcher<Map<String, String>>()
        {
            
            @SuppressWarnings("rawtypes")
            @Override
            public boolean matches(Object argument) {
                @SuppressWarnings("unchecked")
                Map<String, String> agrumentThat = (Map) argument;
                boolean isEquals = true;
                for (Entry<String, String> entryThat : agrumentThat.entrySet()) {
                    isEquals =
                            isEquals && Objects.equals(entryThat.getValue(), requestArguments.get(entryThat.getKey()));
                }
                return isEquals;
            }
        };
        return matcher;
    }
}
