/*******************************************************************************
 * Copyright (c) cortical.io GmbH. All rights reserved.
 *  
 * This software is confidential and proprietary information.
 * You shall use it only in accordance with the terms of the
 * license agreement you entered into with cortical.io GmbH.
 ******************************************************************************/
package io.cortical.retina.core;


import static io.cortical.retina.core.ApiTestUtils.NOT_NULL_RETINA;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import io.cortical.retina.core.Compare.CompareModel;
import io.cortical.retina.model.Metric;
import io.cortical.retina.model.Model;
import io.cortical.retina.model.Term;
import io.cortical.retina.model.Text;
import io.cortical.retina.service.ApiException;
import io.cortical.retina.service.CompareApi;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import com.fasterxml.jackson.core.JsonProcessingException;


/**
 * 
 * {@link Compare} test class.
 */
public class TestCompare {
    /**
     * 
     */
    private static final Metric METRIC;
    private static final Term TERM_1 = new Term("term_1");
    private static final Text TEXT_1 = new Text("the short text");
    private static final String TERM_1_TEXT_1_JSON;
    static {
        try {
            TERM_1_TEXT_1_JSON = Term.toJson(TERM_1, TEXT_1);
            
            Map<String, Double> m = new HashMap<>();
            m.put("Cosine-Similarity", 0.54321);
            m.put("Euclidean-Distance", 0.54321);
            m.put("Jaccard-Distance", 0.54321);
            m.put("Overlapping-all", 0.54321);
            m.put("Overlapping-left-right", 0.54321);
            m.put("Overlapping-right-left", 0.54321);
            m.put("Weighted-Scoring", 0.54321);
            m.put("Size-left", 0.54321);
            m.put("Size-right", 0.54321);
            METRIC = new Metric(m);
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
        when(api.compare(eq(TERM_1_TEXT_1_JSON), eq(NOT_NULL_RETINA))).thenReturn(METRIC);
        Metric metric = compare.compare(TERM_1, TEXT_1);
        assertEquals(METRIC, metric);
        verify(api, times(1)).compare(eq(TERM_1_TEXT_1_JSON), eq(NOT_NULL_RETINA));
    }
    
    /**
     * 
     * {@link Compare#compare(io.cortical.retina.model.Model, io.cortical.retina.model.Model)} method test.
     * @throws JsonProcessingException : should never be thrown.
     * @throws ApiException : should never be thrown.
     */
    @Test
    public void compareTest_bulk() throws JsonProcessingException, ApiException {
        List<CompareModel> compareModels = Arrays.asList(
            new CompareModel(TERM_1, TEXT_1), new CompareModel(TERM_1, TEXT_1), new CompareModel(TERM_1, TEXT_1));
        
        Model[][] toCompare = new Model[compareModels.size()][2];
        int i = 0;
        for (CompareModel pair: compareModels) {
            toCompare[i++] = pair.getModels();
        }
        
        Metric[] metrics = new Metric[] { METRIC, METRIC, METRIC };
        when(api.compareBulk(eq(Model.toJsonBulk(toCompare)), eq(NOT_NULL_RETINA))).thenReturn(metrics);
        Metric[] retVal = compare.compareBulk(compareModels);
        assertTrue(Arrays.equals(metrics, retVal));
        verify(api, times(1)).compareBulk(eq(Model.toJsonBulk(toCompare)), eq(NOT_NULL_RETINA));
    }
}
