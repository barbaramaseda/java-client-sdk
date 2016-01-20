/*******************************************************************************
 * Copyright (c) cortical.io GmbH. All rights reserved.
 *  
 * This software is confidential and proprietary information.
 * You shall use it only in accordance with the terms of the
 * license agreement you entered into with cortical.io GmbH.
 ******************************************************************************/
package io.cortical.retina.client.core;


import static io.cortical.retina.client.core.ApiTestUtils.NOT_NULL_RETINA;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.cortical.retina.model.Term;
import io.cortical.retina.model.Text;
import io.cortical.retina.service.ApiException;
import io.cortical.retina.service.CompareApi;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;


/**
 * 
 * {@link Compare} test class.
 */
public class TestCompare {
    /**
     * 
     */
    private static final Term TERM_1 = new Term("term_1");
    private static final Text TEXT_1 = new Text("the short text");
    private static final String TERM_1_TEXT_1_JSON;
    private static final String TERM_1_JSON;
    private static final String TEXT_1_JSON;
    static {
        try {
            TERM_1_TEXT_1_JSON = Term.toJson(TERM_1, TEXT_1);
            TERM_1_JSON = TERM_1.toJson();
            TEXT_1_JSON = TEXT_1.toJson();
        }
        catch (JsonProcessingException e) {
            throw new IllegalStateException("Impossible to initialize test input data.");
        }
    }
    /**
     * 
     */
    @Mock
    private CompareApi api;
    private Compare compare;
    
    @Before
    public void before() {
        initMocks(this);
        compare = new Compare(api, NOT_NULL_RETINA);
    }
    
    /**
     * 
     * {@link Compare#compare(io.cortical.retina.model.Model, io.cortical.retina.model.Model)} method test.
     * @throws JsonProcessingException : should never be thrown.
     * @throws ApiException : should never be thrown.
     */
    @Test
    public void compareTest_javaModels() throws JsonProcessingException, ApiException {
        compare.compare(TERM_1, TEXT_1);
        verify(api, times(1)).compare(eq(TERM_1_TEXT_1_JSON), eq(NOT_NULL_RETINA));
    }
    
    /**
     * 
     * {@link Compare#compare(String, io.cortical.retina.model.Model)} method test.
     * @throws JsonProcessingException : should never be thrown.
     * @throws ApiException : should never be thrown.
     */
    @Test
    public void compareTest_javaAndJsonModels() throws JsonProcessingException, ApiException {
        compare.compare(TERM_1_JSON, TEXT_1);
        verify(api, times(1)).compare(eq(TERM_1_TEXT_1_JSON), eq(NOT_NULL_RETINA));
    }
    
    /**
     * 
     * {@link Compare#compare(String, String)} method test.
     * @throws JsonProcessingException : should never be thrown.
     * @throws ApiException : should never be thrown.
     */
    @Test
    public void compareTest_jsonModels() throws JsonProcessingException, ApiException {
        compare.compare(TERM_1_JSON, TEXT_1_JSON);
        verify(api, times(1)).compare(eq(TERM_1_TEXT_1_JSON), eq(NOT_NULL_RETINA));
    }
}
