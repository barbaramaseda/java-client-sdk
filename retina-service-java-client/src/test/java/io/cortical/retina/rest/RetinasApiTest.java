/*******************************************************************************
 * Copyright (c) cortical.io GmbH. All rights reserved.
 *  
 * This software is confidential and proprietary information.
 * You shall use it only in accordance with the terms of the
 * license agreement you entered into with cortical.io GmbH.
 ******************************************************************************/
package io.cortical.retina.rest;

import static io.cortical.retina.core.ApiTestUtils.NOT_NULL_API_KEY;
import static io.cortical.retina.core.ApiTestUtils.NOT_NULL_BASE_PATH;
import static io.cortical.retina.core.ApiTestUtils.prepareApiPostMethod;
import static io.cortical.retina.core.ApiTestUtils.setApiInvoker;
import io.cortical.retina.model.Retina;
import io.cortical.retina.rest.ApiException;
import io.cortical.retina.rest.ApiInvoker;
import io.cortical.retina.rest.RetinasApi;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.assertNotNull;
import static org.mockito.MockitoAnnotations.initMocks;


/**
 * {@link RetinasApi} test class.
 * 
 */
public class RetinasApiTest {
    /**
     * 
     */
    private static final String VALID_RETINAS_JSON = "[{\"retinaName\":\"en_synonymous\",\"description\":\"An english language retina focusing on synonymous similarity.\",\"numberOfTermsInRetina\":854376,\"numberOfRows\":128,\"numberOfColumns\":128},{\"retinaName\":\"en_associative\",\"description\":\"An english language retina balancing synonymous and associative similarity.\",\"numberOfTermsInRetina\":854376,\"numberOfRows\":128,\"numberOfColumns\":128}]";
    /**
     * 
     */
    @Mock
    private ApiInvoker apiInvoker;
    private RetinasApi retinasApi;
    
    /**
     * set up.
     * @throws java.lang.Exception if it happens.
     */
    @Before
    public void setUp() throws Exception {
        initMocks(this);
        retinasApi = new RetinasApi(NOT_NULL_API_KEY);
        retinasApi.setBasePath(NOT_NULL_BASE_PATH);
        setApiInvoker(apiInvoker, retinasApi);
    }
    
    
    /**
     * {@link RetinasApi#getRetinas(String)} method's test.
     * 
     * @throws ApiException : shouldn't be thrown.
     */
    @Test
    public void getRetinasTest() throws ApiException {
        prepareApiPostMethod(null, VALID_RETINAS_JSON, apiInvoker);
        List<Retina> retinas = retinasApi.getRetinas(null);
        for (Retina retina: retinas) {
            assertNotNull(retina.getRetinaDescription());
            assertNotNull(retina.getNumberOfColumns());
            assertNotNull(retina.getNumberOfRows());
            assertNotNull(retina.getNumberOfTermsInRetina());
            assertNotNull(retina.getRetinaName());
        }
    }
    
}
