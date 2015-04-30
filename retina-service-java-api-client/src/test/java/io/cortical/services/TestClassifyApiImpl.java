/*******************************************************************************
 * Copyright (c) cortical.io GmbH. All rights reserved.
 *  
 * This software is confidential and proprietary information.
 * You shall use it only in accordance with the terms of the
 * license agreement you entered into with cortical.io GmbH.
 ******************************************************************************/
package io.cortical.services;

import static io.cortical.rest.model.TestDataMother.*;
import static io.cortical.services.ApiTestUtils.NOT_NULL_RETINA;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isNull;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.cortical.rest.model.CategoryFilter;
import io.cortical.rest.model.FilterTrainingObject;
import io.cortical.services.api.client.api.ClassifyApi;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import com.fasterxml.jackson.core.JsonProcessingException;

import io.cortical.rest.model.Fingerprint;
import io.cortical.rest.model.Text;
import io.cortical.services.api.client.ApiException;
import io.cortical.services.api.client.api.TextApi;


/**
 * 
 * {@link io.cortical.services.TextRetinaApiImpl} test class.
 */
public class TestClassifyApiImpl {
    /**
     *
     */
    private static final FilterTrainingObject fto = new FilterTrainingObject(){{
        getPositiveExamples().add(new Text("Shoe with a lining to help keep your feet dry and comfortable on wet terrain."));
        getPositiveExamples().add(new Text("running shoes providing protective cushioning."));
    }};

    private static final CategoryFilter cf = new CategoryFilter(){{
       setCategoryName("12");
        setPositions(new ArrayList<Integer>(){{
            add(3);
            add(6);
            add(7);
        }});
    }};

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
        classifyRetinaApi = new ClassifyRetinaApiImpl(classifyApi, NOT_NULL_RETINA);
    }

    /**
     * {@link io.cortical.services.TextRetinaApiImpl#getKeywords(String)} test method.
     *
     * @throws io.cortical.services.api.client.ApiException : should never be thrown
     */
    @Test
    public void testCreateCategoryFilter() throws ApiException {
        when(classifyApi.createCategoryFilter(eq("12"), eq(fto), eq(NOT_NULL_RETINA))).thenReturn(cf);
        CategoryFilter result = classifyRetinaApi.createCategoryFilter("12", fto);
        assertTrue(result.getCategoryName().equals("12"));
        assertTrue(result.getPositions().size() == 3);
    }


}
