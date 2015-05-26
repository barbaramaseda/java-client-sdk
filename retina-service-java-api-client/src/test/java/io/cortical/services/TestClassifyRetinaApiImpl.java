/*******************************************************************************
 * Copyright (c) cortical.io GmbH. All rights reserved.
 *  
 * This software is confidential and proprietary information.
 * You shall use it only in accordance with the terms of the
 * license agreement you entered into with cortical.io GmbH.
 ******************************************************************************/
package io.cortical.services;

import io.cortical.rest.model.CategoryFilter;
import io.cortical.services.api.client.ApiException;
import io.cortical.services.api.client.api.ClassifyApi;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import static io.cortical.services.ApiTestUtils.NOT_NULL_RETINA;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;


/**
 * 
 * {@link io.cortical.services.TextRetinaApiImpl} test class.
 */
public class TestClassifyRetinaApiImpl {

    
    private static final String ftoString = "{"
            + " \"positiveExamples\" : ["
            + "{ \"text\" : \"Shoe with a lining to help keep your feet dry and comfortable on wet terrain.\" },"
            + "{ \"text\" : \"running shoes providing protective cushioning.\" }"
            +  "], "
            + " \"negativeExamples\" : [ "
            + " { \"text\" : \"The most comfortable socks for your feet.\"}, "
            + "{ \"text\" : \"6 feet USB cable basic white\"}"
            + "]}";
    
    @SuppressWarnings("serial")
    private static final CategoryFilter cf = new CategoryFilter()
    {
        {
            setCategoryName("12");
            setPositions(new ArrayList<Integer>()
            {
                {
                    add(3);
                    add(6);
                    add(7);
                }
            });
        }
    };
    
    /**
     *
     */
    @Mock
    private ClassifyApi classifyApi;
    private ClassifyRetinaApiImpl classifyRetinaApi;
    
    /**
     * initialization.
     */
    @Before
    public void before() {
        initMocks(this);
        //FIXME Add a test method in TestRetinasApi to test for existence of each api
        classifyRetinaApi = new ClassifyRetinaApiImpl(classifyApi, NOT_NULL_RETINA);
    }
    
    /**
     * {@link io.cortical.services.TextRetinaApiImpl#getKeywords(String)} test method.
     *
     * @throws io.cortical.services.api.client.ApiException : should never be thrown
     */
    @Test
    public void testCreateCategoryFilter() throws ApiException {
        when(classifyApi.createCategoryFilter(eq("12"), eq(ftoString), eq(NOT_NULL_RETINA))).thenReturn(cf);
        CategoryFilter result = classifyRetinaApi.createCategoryFilter("12", ftoString);
        assertTrue(result.getCategoryName().equals("12"));
        assertTrue(result.getPositions().size() == 3);
    }

}
