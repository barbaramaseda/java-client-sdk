/*******************************************************************************
 * Copyright (c) cortical.io GmbH. All rights reserved.
 *  
 * This software is confidential and proprietary information.
 * You shall use it only in accordance with the terms of the
 * license agreement you entered into with cortical.io GmbH.
 ******************************************************************************/
package io.cortical.retina.core;

import static io.cortical.retina.model.TestDataHarness.createRetina;
import static io.cortical.retina.model.TestDataHarness.createRetinas;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import io.cortical.retina.core.Endpoints;
import io.cortical.retina.model.Retina;
import io.cortical.retina.service.ApiException;
import io.cortical.retina.service.RetinasApi;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;


/**
 * {@link Endpoints} test class.
 * 
 */
public class EndpointsTest {
    /**
     * 
     */
    @Mock
    private RetinasApi api;
    private Endpoints endpoints;
    
    @Before
    public void before() {
        initMocks(this);
        endpoints = Endpoints.makeTestRetinas(api);
    }
    
    /**
     * {@link Endpoints#getAllRetinas()} method test.
     * 
     * @throws ApiException : should never be thrown
     */
    @Test
    public void getAllRetinasTest() throws ApiException {
        int count = 7;
        when(api.getRetinas(isNull(String.class))).thenReturn(createRetinas(count));
        List<Retina> retinasList = endpoints.getAllRetinas();
        assertEquals(count, retinasList.size());
        verify(api, times(1)).getRetinas(isNull(String.class));
    }
    
    /**
     *  {@link Endpoints#retinaByName(String)} method test.
     * 
     * @throws ApiException : should never be thrown
     */
    @Test
    public void retinaByNameTest() throws ApiException {
        String retinaName = "some_retina_name";
        Retina[] retinaArray = { createRetina(retinaName) };
        when(api.getRetinas(eq(retinaName))).thenReturn(asList(retinaArray));
        Retina retina = endpoints.retinaByName(retinaName);
        assertNotNull(retina);
        verify(api, times(1)).getRetinas(eq(retinaName));
    }
}
