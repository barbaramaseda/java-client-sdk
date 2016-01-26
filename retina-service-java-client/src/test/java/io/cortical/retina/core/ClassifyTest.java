/*******************************************************************************
 * Copyright (c) cortical.io GmbH. All rights reserved.
 *  
 * This software is confidential and proprietary information.
 * You shall use it only in accordance with the terms of the
 * license agreement you entered into with cortical.io GmbH.
 ******************************************************************************/
package io.cortical.retina.core;

import static io.cortical.retina.core.ApiTestUtils.NOT_NULL_RETINA;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import io.cortical.retina.model.CategoryFilter;
import io.cortical.retina.model.Sample;
import io.cortical.retina.rest.ApiException;
import io.cortical.retina.rest.ClassifyApi;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * 
 * {@link io.cortical.retina.core.Images} test class.
 */
public class ClassifyTest {

    private static final CategoryFilter cf = new CategoryFilter()
    {
        {
            setCategoryName("12");
            setPositions(new int[] { 3, 6, 7 });
        }
    };
    
    /**
     *
     */
    @Mock
    private ClassifyApi classifyApi;
    private Classify classify;
    
    /**
     * initialization.
     */
    @Before
    public void before() {
        initMocks(this);
        //FIXME Add a test method in TestRetinasApi to test for existence of each api
        classify = new Classify(classifyApi, NOT_NULL_RETINA);
    }
    
    /**
     * {@link io.cortical.services.TextRetinaApiImpl#getKeywords(String)} test method.
     *
     * @throws io.cortical.services.api.client.ApiException : should never be thrown
     */
    @Test
    public void testCreateCategoryFilter() throws ApiException {
        List<String> pos = Arrays.asList(
            "Shoe with a lining to help keep your feet dry and comfortable on wet terrain.",
            "running shoes providing protective cushioning.");
        List<String> neg = Arrays.asList(
            "The most comfortable socks for your feet.",
            "6 feet USB cable basic white");
        
        Sample sample = new Sample();
        sample.addAllPositive(pos.toArray(new String[pos.size()]));
        sample.addAllNegative(neg.toArray(new String[neg.size()]));
        
        String json = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.setSerializationInclusion(Include.NON_NULL);
            json = mapper.writeValueAsString(sample);
        }catch(Exception e) {
            e.printStackTrace();
        }

        when(classifyApi.createCategoryFilter(eq("12"), eq(json), eq("en_associative"))).thenReturn(cf);
        CategoryFilter result = classify.createCategoryFilter("12", pos, neg);
        assertTrue(result.getCategoryName().equals("12"));
        assertTrue(result.getPositions().length == 3);
    }

}
