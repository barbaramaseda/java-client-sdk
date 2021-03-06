/*******************************************************************************
 * Copyright (c) cortical.io GmbH. All rights reserved.
 *  
 * This software is confidential and proprietary information.
 * You shall use it only in accordance with the terms of the
 * license agreement you entered into with cortical.io GmbH.
 ******************************************************************************/
package io.cortical.services;

import static io.cortical.rest.model.TestDataMother.createRetina;
import static io.cortical.rest.model.TestDataMother.createRetinas;
import io.cortical.rest.model.Retina;
import io.cortical.services.RetinaInfoApiImpl;
import io.cortical.services.api.client.ApiException;
import io.cortical.services.api.client.api.RetinasApi;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;


/**
 * {@link RetinaInfoApiImpl} test class.
 * 
 */
public class TestRetinaInfoApiImpl {
    /**
     * 
     */
    @Mock
    private RetinasApi api;
    private RetinaInfoApiImpl retinaInfoApiImpl;
    
    @Before
    public void before() {
        initMocks(this);
        retinaInfoApiImpl = new RetinaInfoApiImpl(api);
    }
    
    /**
     * {@link RetinaInfoApiImpl#getAllRetinas()} method test.
     * 
     * @throws ApiException : should never be thrown
     */
    @Test
    public void getAllRetinasTest() throws ApiException {
        int count = 7;
        when(api.getRetinas(isNull(String.class))).thenReturn(createRetinas(count));
        List<Retina> retinas = retinaInfoApiImpl.getAllRetinas();
        assertEquals(count, retinas.size());
        verify(api, times(1)).getRetinas(isNull(String.class));
    }
    
    /**
     *  {@link RetinaInfoApiImpl#retinaByName(String)} method test.
     * 
     * @throws ApiException : should never be thrown
     */
    @Test
    public void retinaByNameTest() throws ApiException {
        String retinaName = "some_retina_name";
        Retina[] retinaArray = { createRetina(retinaName) };
        when(api.getRetinas(eq(retinaName))).thenReturn(asList(retinaArray));
        Retina retina = retinaInfoApiImpl.retinaByName(retinaName);
        assertNotNull(retina);
        verify(api, times(1)).getRetinas(eq(retinaName));
    }
}
