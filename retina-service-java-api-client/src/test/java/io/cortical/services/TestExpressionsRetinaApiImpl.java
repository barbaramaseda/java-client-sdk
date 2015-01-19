/*******************************************************************************
 * Copyright (c) cortical.io GmbH. All rights reserved.
 *  
 * This software is confidential and proprietary information.
 * You shall use it only in accordance with the terms of the
 * license agreement you entered into with cortical.io GmbH.
 ******************************************************************************/
package io.cortical.services;

import static io.cortical.rest.model.TestDataMother.createContexts;
import static io.cortical.rest.model.TestDataMother.createFingerprints;
import static io.cortical.rest.model.TestDataMother.createTerms;
import static io.cortical.services.ApiTestUtils.NOT_NULL_RETINA;
import io.cortical.rest.model.Context;
import io.cortical.rest.model.Fingerprint;
import io.cortical.rest.model.Term;
import io.cortical.rest.model.Text;
import io.cortical.services.ExpressionsRetinaApiImpl;
import io.cortical.services.Pagination;
import io.cortical.services.PosType;
import io.cortical.services.api.client.ApiException;
import io.cortical.services.api.client.api.ExpressionsApi;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;



/**
 * {@link ExpressionsRetinaApiImpl} test class. 
 * 
 */
public class TestExpressionsRetinaApiImpl {
    /**
     * 
     */
    private static final Term TERM_1 = new Term("term_1");
    private static final Text TEXT_1 = new Text("the short text");
    private static final String TERM_1_TEXT_1_JSON;
    private static final String TERM_1_JSON;
    
    static {
        try {
            TERM_1_TEXT_1_JSON = Term.toJson(TERM_1, TEXT_1);
            TERM_1_JSON = TERM_1.toJson();
        }
        catch (JsonProcessingException e) {
            throw new IllegalStateException("Impossible to initialize test input data.");
        }
    }
    /**
     * 
     */
    @Mock
    private ExpressionsApi expressionsApi;
    private ExpressionsRetinaApiImpl expressionsRetinaApiImpl;
    
    @Before
    public void initialize() {
        initMocks(this);
        expressionsRetinaApiImpl = new ExpressionsRetinaApiImpl(expressionsApi, NOT_NULL_RETINA);
    }
    /**
     * 
     * {@link ExpressionsRetinaApiImpl#getContextsBulk(io.cortical.rest.model.Model...)} method test.
     * @throws JsonProcessingException : should never be thrown.
     * @throws ApiException : should never be thrown.
     */
    @Test
    public void getContextsForBulkExpressionTest() throws JsonProcessingException, ApiException {
        int count = 5;
        List<List<Context>> listOfContexts = new ArrayList<>();
        listOfContexts.add(createContexts(count));
        listOfContexts.add(createContexts(count));
        
        when(expressionsApi.getContextsForBulkExpression(eq(TERM_1_TEXT_1_JSON), isNull(Boolean.class), eq(NOT_NULL_RETINA), isNull(Integer.class), isNull(Integer.class), isNull(Double.class))).thenReturn(listOfContexts); 
        List<List<Context>> actualListOfContexts = expressionsRetinaApiImpl.getContextsBulk(TERM_1, TEXT_1);
        assertEquals(listOfContexts.size(), actualListOfContexts.size());
        verify(expressionsApi, times(1)).getContextsForBulkExpression(eq(TERM_1_TEXT_1_JSON), isNull(Boolean.class), eq(NOT_NULL_RETINA), isNull(Integer.class), isNull(Integer.class), isNull(Double.class));
    }
    
    /**
     * 
     * {@link ExpressionsRetinaApiImpl#getContextsBulk(Boolean, io.cortical.rest.model.Model...)} method test.
     * @throws JsonProcessingException : should never be thrown.
     * @throws ApiException : should never be thrown.
     */
    @Test
    public void getContextsForBulkExpressionTest_fingerprints() throws JsonProcessingException, ApiException {
        int count = 5;
        boolean getFingerprint = false;
        List<List<Context>> listOfContexts = new ArrayList<>();
        listOfContexts.add(createContexts(count));
        listOfContexts.add(createContexts(count));
        
        when(expressionsApi.getContextsForBulkExpression(eq(TERM_1_TEXT_1_JSON), eq(getFingerprint), eq(NOT_NULL_RETINA), isNull(Integer.class), isNull(Integer.class), isNull(Double.class))).thenReturn(listOfContexts); 
        List<List<Context>> actualListOfContexts = expressionsRetinaApiImpl.getContextsBulk(getFingerprint, TERM_1, TEXT_1);
        assertEquals(listOfContexts.size(), actualListOfContexts.size());
        verify(expressionsApi, times(1)).getContextsForBulkExpression(eq(TERM_1_TEXT_1_JSON), eq(getFingerprint), eq(NOT_NULL_RETINA), isNull(Integer.class), isNull(Integer.class), isNull(Double.class));
    }
    
    /**
     * 
     * {@link ExpressionsRetinaApiImpl#getContextsBulk(Double, io.cortical.rest.model.Model...)} method test.
     * @throws JsonProcessingException : should never be thrown.
     * @throws ApiException : should never be thrown.
     */
    @Test
    public void getContextsForBulkExpressionTest_sparsity() throws JsonProcessingException, ApiException {
        int count = 5;
        Double sparsity = 0.5;
        List<List<Context>> listOfContexts = new ArrayList<>();
        listOfContexts.add(createContexts(count));
        listOfContexts.add(createContexts(count));
        
        when(expressionsApi.getContextsForBulkExpression(eq(TERM_1_TEXT_1_JSON), isNull(Boolean.class), eq(NOT_NULL_RETINA), isNull(Integer.class), isNull(Integer.class), eq(sparsity))).thenReturn(listOfContexts); 
        List<List<Context>> actualListOfContexts = expressionsRetinaApiImpl.getContextsBulk(sparsity, TERM_1, TEXT_1);
        assertEquals(listOfContexts.size(), actualListOfContexts.size());
        verify(expressionsApi, times(1)).getContextsForBulkExpression(eq(TERM_1_TEXT_1_JSON), isNull(Boolean.class), eq(NOT_NULL_RETINA), isNull(Integer.class), isNull(Integer.class), eq(sparsity));
    }
    
    /**
     * 
     * {@link ExpressionsRetinaApiImpl#getContextsBulk(Boolean, Double, io.cortical.rest.model.Model...)} method test.
     * @throws JsonProcessingException : should never be thrown.
     * @throws ApiException : should never be thrown.
     */
    @Test
    public void getContextsForBulkExpressionTest_fingerprintSparsity() throws JsonProcessingException, ApiException {
        int count = 5;
        boolean getFingerprint = false;
        Double sparsity = 0.5;
        List<List<Context>> listOfContexts = new ArrayList<>();
        listOfContexts.add(createContexts(count));
        listOfContexts.add(createContexts(count));
        
        when(expressionsApi.getContextsForBulkExpression(eq(TERM_1_TEXT_1_JSON), eq(getFingerprint), eq(NOT_NULL_RETINA), isNull(Integer.class), isNull(Integer.class), eq(sparsity))).thenReturn(listOfContexts); 
        List<List<Context>> actualListOfContexts = expressionsRetinaApiImpl.getContextsBulk(getFingerprint, sparsity, TERM_1, TEXT_1);
        assertEquals(listOfContexts.size(), actualListOfContexts.size());
        verify(expressionsApi, times(1)).getContextsForBulkExpression(eq(TERM_1_TEXT_1_JSON), eq(getFingerprint), eq(NOT_NULL_RETINA), isNull(Integer.class), isNull(Integer.class), eq(sparsity));
    }
    
    /**
     * 
     * {@link ExpressionsRetinaApiImpl#getContextsBulk(Pagination, Boolean, Double, io.cortical.rest.model.Model...)} method test.
     * @throws JsonProcessingException : should never be thrown.
     * @throws ApiException : should never be thrown.
     */
    @Test
    public void getContextsForBulkExpressionTest_fingerprintSparsityPagination() throws JsonProcessingException, ApiException {
        int count = 5;
        boolean getFingerprint = false;
        int startIndex = 0;
        int maxResults = 100;
        Double sparsity = 0.5;
        Pagination pagination = new Pagination(startIndex, maxResults);
        List<List<Context>> listOfContexts = new ArrayList<>();
        listOfContexts.add(createContexts(count));
        listOfContexts.add(createContexts(count));
        
        when(expressionsApi.getContextsForBulkExpression(eq(TERM_1_TEXT_1_JSON), eq(getFingerprint), eq(NOT_NULL_RETINA), eq(startIndex), eq(maxResults), eq(sparsity))).thenReturn(listOfContexts); 
        List<List<Context>> actualListOfContexts = expressionsRetinaApiImpl.getContextsBulk(pagination, getFingerprint, sparsity, TERM_1, TEXT_1);
        assertEquals(listOfContexts.size(), actualListOfContexts.size());
        verify(expressionsApi, times(1)).getContextsForBulkExpression(eq(TERM_1_TEXT_1_JSON), eq(getFingerprint), eq(NOT_NULL_RETINA), eq(startIndex), eq(maxResults), eq(sparsity));
    }
    
    
    /**
     * 
     * {@link ExpressionsRetinaApiImpl#getContextsBulk(String)} method test.
     * @throws JsonProcessingException : should never be thrown.
     * @throws ApiException : should never be thrown.
     */
    @Test
    public void getContextsForBulkExpressionTest_json() throws JsonProcessingException, ApiException {
        int count = 5;
        List<List<Context>> listOfContexts = new ArrayList<>();
        listOfContexts.add(createContexts(count));
        listOfContexts.add(createContexts(count));
        
        when(expressionsApi.getContextsForBulkExpression(eq(TERM_1_TEXT_1_JSON), isNull(Boolean.class), eq(NOT_NULL_RETINA), isNull(Integer.class), isNull(Integer.class), isNull(Double.class))).thenReturn(listOfContexts); 
        List<List<Context>> actualListOfContexts = expressionsRetinaApiImpl.getContextsBulk(TERM_1_TEXT_1_JSON);
        assertEquals(listOfContexts.size(), actualListOfContexts.size());
        verify(expressionsApi, times(1)).getContextsForBulkExpression(eq(TERM_1_TEXT_1_JSON), isNull(Boolean.class), eq(NOT_NULL_RETINA), isNull(Integer.class), isNull(Integer.class), isNull(Double.class));
    }
    
    /**
     * 
     * {@link ExpressionsRetinaApiImpl#getContextsBulk(Boolean, String)} method test.
     * @throws JsonProcessingException : should never be thrown.
     * @throws ApiException : should never be thrown.
     */
    @Test
    public void getContextsForBulkExpressionTest_fingerprints_json() throws JsonProcessingException, ApiException {
        int count = 5;
        boolean getFingerprint = false;
        List<List<Context>> listOfContexts = new ArrayList<>();
        listOfContexts.add(createContexts(count));
        listOfContexts.add(createContexts(count));
        
        when(expressionsApi.getContextsForBulkExpression(eq(TERM_1_TEXT_1_JSON), eq(getFingerprint), eq(NOT_NULL_RETINA), isNull(Integer.class), isNull(Integer.class), isNull(Double.class))).thenReturn(listOfContexts); 
        List<List<Context>> actualListOfContexts = expressionsRetinaApiImpl.getContextsBulk(getFingerprint, TERM_1_TEXT_1_JSON);
        assertEquals(listOfContexts.size(), actualListOfContexts.size());
        verify(expressionsApi, times(1)).getContextsForBulkExpression(eq(TERM_1_TEXT_1_JSON), eq(getFingerprint), eq(NOT_NULL_RETINA), isNull(Integer.class), isNull(Integer.class), isNull(Double.class));
    }
    
    /**
     * 
     * {@link ExpressionsRetinaApiImpl#getContextsBulk(Double, String)} method test.
     * @throws JsonProcessingException : should never be thrown.
     * @throws ApiException : should never be thrown.
     */
    @Test
    public void getContextsForBulkExpressionTest_sparsity_json() throws JsonProcessingException, ApiException {
        int count = 5;
        Double sparsity = 0.5;
        List<List<Context>> listOfContexts = new ArrayList<>();
        listOfContexts.add(createContexts(count));
        listOfContexts.add(createContexts(count));
        
        when(expressionsApi.getContextsForBulkExpression(eq(TERM_1_TEXT_1_JSON), isNull(Boolean.class), eq(NOT_NULL_RETINA), isNull(Integer.class), isNull(Integer.class), eq(sparsity))).thenReturn(listOfContexts); 
        List<List<Context>> actualListOfContexts = expressionsRetinaApiImpl.getContextsBulk(sparsity, TERM_1_TEXT_1_JSON);
        assertEquals(listOfContexts.size(), actualListOfContexts.size());
        verify(expressionsApi, times(1)).getContextsForBulkExpression(eq(TERM_1_TEXT_1_JSON), isNull(Boolean.class), eq(NOT_NULL_RETINA), isNull(Integer.class), isNull(Integer.class), eq(sparsity));
    }
    
    /**
     * 
     * {@link ExpressionsRetinaApiImpl#getContextsBulk(Boolean, Double, String)} method test.
     * @throws JsonProcessingException : should never be thrown.
     * @throws ApiException : should never be thrown.
     */
    @Test
    public void getContextsForBulkExpressionTest_fingerprintSparsity_json() throws JsonProcessingException, ApiException {
        int count = 5;
        boolean getFingerprint = false;
        Double sparsity = 0.5;
        List<List<Context>> listOfContexts = new ArrayList<>();
        listOfContexts.add(createContexts(count));
        listOfContexts.add(createContexts(count));
        
        when(expressionsApi.getContextsForBulkExpression(eq(TERM_1_TEXT_1_JSON), eq(getFingerprint), eq(NOT_NULL_RETINA), isNull(Integer.class), isNull(Integer.class), eq(sparsity))).thenReturn(listOfContexts); 
        List<List<Context>> actualListOfContexts = expressionsRetinaApiImpl.getContextsBulk(getFingerprint, sparsity, TERM_1_TEXT_1_JSON);
        assertEquals(listOfContexts.size(), actualListOfContexts.size());
        verify(expressionsApi, times(1)).getContextsForBulkExpression(eq(TERM_1_TEXT_1_JSON), eq(getFingerprint), eq(NOT_NULL_RETINA), isNull(Integer.class), isNull(Integer.class), eq(sparsity));
    }
    
    /**
     * 
     * {@link ExpressionsRetinaApiImpl#getContextsBulk(Pagination, Boolean, Double, String)} method test.
     * @throws JsonProcessingException : should never be thrown.
     * @throws ApiException : should never be thrown
     */
    @Test
    public void getContextsForBulkExpressionTest_fingerprintSparsityPagination_json() throws JsonProcessingException, ApiException {
        int count = 5;
        boolean getFingerprint = false;
        int startIndex = 0;
        int maxResults = 100;
        Double sparsity = 0.5;
        Pagination pagination = new Pagination(startIndex, maxResults);
        List<List<Context>> listOfContexts = new ArrayList<>();
        listOfContexts.add(createContexts(count));
        listOfContexts.add(createContexts(count));
        
        when(expressionsApi.getContextsForBulkExpression(eq(TERM_1_TEXT_1_JSON), eq(getFingerprint), eq(NOT_NULL_RETINA), eq(startIndex), eq(maxResults), eq(sparsity))).thenReturn(listOfContexts); 
        List<List<Context>> actualListOfContexts = expressionsRetinaApiImpl.getContextsBulk(pagination, getFingerprint, sparsity, TERM_1_TEXT_1_JSON);
        assertEquals(listOfContexts.size(), actualListOfContexts.size());
        verify(expressionsApi, times(1)).getContextsForBulkExpression(eq(TERM_1_TEXT_1_JSON), eq(getFingerprint), eq(NOT_NULL_RETINA), eq(startIndex), eq(maxResults), eq(sparsity));
        
    }
    
    /**
     * 
     * {@link ExpressionsRetinaApiImpl#getContexts(io.cortical.rest.model.Model)} method test.
     * @throws JsonProcessingException : should never be thrown
     * @throws ApiException : should never be thrown
     */
    @Test
    public void getContextsForExpressionTest() throws ApiException, JsonProcessingException {
        
        int count = 5;
        List<Context> contexts = createContexts(count);
        
        when(expressionsApi.getContextsForExpression(eq(TERM_1_JSON), isNull(Boolean.class), eq(NOT_NULL_RETINA), isNull(Integer.class), isNull(Integer.class), isNull(Double.class))).thenReturn(contexts); 
        List<Context> actualContexts = expressionsRetinaApiImpl.getContexts(TERM_1);
        assertEquals(contexts.size(), actualContexts.size());
        verify(expressionsApi, times(1)).getContextsForExpression(eq(TERM_1_JSON), isNull(Boolean.class), eq(NOT_NULL_RETINA), isNull(Integer.class), isNull(Integer.class), isNull(Double.class));
    }
    
    /**
     * 
     * {@link ExpressionsRetinaApiImpl#getContexts(Boolean, io.cortical.rest.model.Model)} method test.
     * @throws JsonProcessingException : should never be thrown
     * @throws ApiException : should never be thrown
     */
    @Test
    public void getContextsForExpressionTest_fingerprint() throws ApiException, JsonProcessingException {
        
        int count = 5;
        boolean getFingerprint = false;
        List<Context> contexts = createContexts(count);
        
        when(expressionsApi.getContextsForExpression(eq(TERM_1_JSON), eq(getFingerprint), eq(NOT_NULL_RETINA), isNull(Integer.class), isNull(Integer.class), isNull(Double.class))).thenReturn(contexts); 
        List<Context> actualContexts = expressionsRetinaApiImpl.getContexts(getFingerprint, TERM_1);
        assertEquals(contexts.size(), actualContexts.size());
        verify(expressionsApi, times(1)).getContextsForExpression(eq(TERM_1_JSON), eq(getFingerprint), eq(NOT_NULL_RETINA), isNull(Integer.class), isNull(Integer.class), isNull(Double.class));
    }
    
    /**
     * 
     * {@link ExpressionsRetinaApiImpl#getContexts(Double, io.cortical.rest.model.Model)} method test.
     * @throws JsonProcessingException : should never be thrown
     * @throws ApiException : should never be thrown
     */
    @Test
    public void getContextsForExpressionTest_sparsity() throws ApiException, JsonProcessingException {
        
        int count = 5;
        Double sparsity = 0.5;
        List<Context> contexts = createContexts(count);
        
        when(expressionsApi.getContextsForExpression(eq(TERM_1_JSON), isNull(Boolean.class), eq(NOT_NULL_RETINA), isNull(Integer.class), isNull(Integer.class), eq(sparsity))).thenReturn(contexts); 
        List<Context> actualContexts = expressionsRetinaApiImpl.getContexts(sparsity, TERM_1);
        assertEquals(contexts.size(), actualContexts.size());
        verify(expressionsApi, times(1)).getContextsForExpression(eq(TERM_1_JSON), isNull(Boolean.class), eq(NOT_NULL_RETINA), isNull(Integer.class), isNull(Integer.class), eq(sparsity));
    }
    
    /**
     * 
     * {@link ExpressionsRetinaApiImpl#getContexts(Boolean, Double, io.cortical.rest.model.Model)} method test.
     * @throws JsonProcessingException : should never be thrown
     * @throws ApiException : should never be thrown
     */
    @Test
    public void getContextsForExpressionTest_sparsityFingerprint() throws ApiException, JsonProcessingException {
        
        int count = 5;
        boolean getFingerprint = false;
        Double sparsity = 0.5;
        List<Context> contexts = createContexts(count);
        
        when(expressionsApi.getContextsForExpression(eq(TERM_1_JSON), eq(getFingerprint), eq(NOT_NULL_RETINA), isNull(Integer.class), isNull(Integer.class), eq(sparsity))).thenReturn(contexts); 
        List<Context> actualContexts = expressionsRetinaApiImpl.getContexts(getFingerprint, sparsity, TERM_1);
        assertEquals(contexts.size(), actualContexts.size());
        verify(expressionsApi, times(1)).getContextsForExpression(eq(TERM_1_JSON), eq(getFingerprint), eq(NOT_NULL_RETINA), isNull(Integer.class), isNull(Integer.class), eq(sparsity));
    }
    
    /**
     * 
     * {@link ExpressionsRetinaApiImpl#getContexts(Pagination, Boolean, Double, io.cortical.rest.model.Model)} method test.
     * @throws JsonProcessingException : should never be thrown
     * @throws ApiException : should never be thrown
     */
    @Test
    public void getContextsForExpressionTest_sparsityFingerprintPagination() throws ApiException, JsonProcessingException {
        
        int count = 5;
        boolean getFingerprint = false;
        int startIndex = 0;
        int maxResults = 100;
        Double sparsity = 0.5;
        Pagination pagination = new Pagination(startIndex, maxResults);
        List<Context> contexts = createContexts(count);
        
        when(expressionsApi.getContextsForExpression(eq(TERM_1_JSON), eq(getFingerprint), eq(NOT_NULL_RETINA), eq(startIndex), eq(maxResults), eq(sparsity))).thenReturn(contexts); 
        List<Context> actualContexts = expressionsRetinaApiImpl.getContexts(pagination, getFingerprint, sparsity, TERM_1);
        assertEquals(contexts.size(), actualContexts.size());
        verify(expressionsApi, times(1)).getContextsForExpression(eq(TERM_1_JSON), eq(getFingerprint), eq(NOT_NULL_RETINA), eq(startIndex), eq(maxResults), eq(sparsity));
    }
    
    
    /**
     * 
     * {@link ExpressionsRetinaApiImpl#getContexts(String)} method test.
     * @throws JsonProcessingException : should never be thrown
     * @throws ApiException : should never be thrown
     */
    @Test
    public void getContextsForExpressionTest_json() throws ApiException, JsonProcessingException {
        int count = 5;
        List<Context> contexts = createContexts(count);
        
        when(expressionsApi.getContextsForExpression(eq(TERM_1_JSON), isNull(Boolean.class), eq(NOT_NULL_RETINA), isNull(Integer.class), isNull(Integer.class), isNull(Double.class))).thenReturn(contexts); 
        List<Context> actualContexts = expressionsRetinaApiImpl.getContexts(TERM_1_JSON);
        assertEquals(contexts.size(), actualContexts.size());
        verify(expressionsApi, times(1)).getContextsForExpression(eq(TERM_1_JSON), isNull(Boolean.class), eq(NOT_NULL_RETINA), isNull(Integer.class), isNull(Integer.class), isNull(Double.class));
    }
    
    /**
     * 
     * {@link ExpressionsRetinaApiImpl#getContexts(Boolean, String)} method test.
     * @throws JsonProcessingException : should never be thrown
     * @throws ApiException : should never be thrown
     */
    @Test
    public void getContextsForExpressionTest_fingerprint_json() throws ApiException, JsonProcessingException {
        
        int count = 5;
        boolean getFingerprint = false;
        List<Context> contexts = createContexts(count);
        
        when(expressionsApi.getContextsForExpression(eq(TERM_1_JSON), eq(getFingerprint), eq(NOT_NULL_RETINA), isNull(Integer.class), isNull(Integer.class), isNull(Double.class))).thenReturn(contexts); 
        List<Context> actualContexts = expressionsRetinaApiImpl.getContexts(getFingerprint, TERM_1_JSON);
        assertEquals(contexts.size(), actualContexts.size());
        verify(expressionsApi, times(1)).getContextsForExpression(eq(TERM_1_JSON), eq(getFingerprint), eq(NOT_NULL_RETINA), isNull(Integer.class), isNull(Integer.class), isNull(Double.class));
    }
    
    /**
     * 
     * {@link ExpressionsRetinaApiImpl#getContexts(Double, String)} method test.
     * @throws JsonProcessingException : should never be thrown
     * @throws ApiException : should never be thrown
     */
    @Test
    public void getContextsForExpressionTest_sparsity_json() throws ApiException, JsonProcessingException {
        
        int count = 5;
        Double sparsity = 0.5;
        List<Context> contexts = createContexts(count);
        
        when(expressionsApi.getContextsForExpression(eq(TERM_1_JSON), isNull(Boolean.class), eq(NOT_NULL_RETINA), isNull(Integer.class), isNull(Integer.class), eq(sparsity))).thenReturn(contexts); 
        List<Context> actualContexts = expressionsRetinaApiImpl.getContexts(sparsity, TERM_1_JSON);
        assertEquals(contexts.size(), actualContexts.size());
        verify(expressionsApi, times(1)).getContextsForExpression(eq(TERM_1_JSON), isNull(Boolean.class), eq(NOT_NULL_RETINA), isNull(Integer.class), isNull(Integer.class), eq(sparsity));
    }
    
    /**
     * 
     * {@link ExpressionsRetinaApiImpl#getContexts(Boolean, Double, String)} method test.
     * @throws JsonProcessingException : should never be thrown
     * @throws ApiException : should never be thrown
     */
    @Test
    public void getContextsForExpressionTest_sparsityFingerprint_json() throws ApiException, JsonProcessingException {
        
        int count = 5;
        boolean getFingerprint = false;
        Double sparsity = 0.5;
        List<Context> contexts = createContexts(count);
        
        when(expressionsApi.getContextsForExpression(eq(TERM_1_JSON), eq(getFingerprint), eq(NOT_NULL_RETINA), isNull(Integer.class), isNull(Integer.class), eq(sparsity))).thenReturn(contexts); 
        List<Context> actualContexts = expressionsRetinaApiImpl.getContexts(getFingerprint, sparsity, TERM_1_JSON);
        assertEquals(contexts.size(), actualContexts.size());
        verify(expressionsApi, times(1)).getContextsForExpression(eq(TERM_1_JSON), eq(getFingerprint), eq(NOT_NULL_RETINA), isNull(Integer.class), isNull(Integer.class), eq(sparsity));
    }
    
    /**
     * 
     * {@link ExpressionsRetinaApiImpl#getContexts(Pagination, Boolean, Double, String)} method test.
     * @throws JsonProcessingException : should never be thrown
     * @throws ApiException : should never be thrown
     */
    @Test
    public void getContextsForExpressionTest_sparsityFingerprintPagination_json() throws ApiException, JsonProcessingException {
        
        int count = 5;
        boolean getFingerprint = false;
        int startIndex = 0;
        int maxResults = 100;
        Double sparsity = 0.5;
        Pagination pagination = new Pagination(startIndex, maxResults);
        List<Context> contexts = createContexts(count);
        
        when(expressionsApi.getContextsForExpression(eq(TERM_1_JSON), eq(getFingerprint), eq(NOT_NULL_RETINA), eq(startIndex), eq(maxResults), eq(sparsity))).thenReturn(contexts); 
        List<Context> actualContexts = expressionsRetinaApiImpl.getContexts(pagination, getFingerprint, sparsity, TERM_1_JSON);
        assertEquals(contexts.size(), actualContexts.size());
        verify(expressionsApi, times(1)).getContextsForExpression(eq(TERM_1_JSON), eq(getFingerprint), eq(NOT_NULL_RETINA), eq(startIndex), eq(maxResults), eq(sparsity));
    }
    
    /**
     * 
     * {@link ExpressionsRetinaApiImpl#getSimilarTermsBulk(Integer, PosType, io.cortical.rest.model.Model...)} method test.
     * @throws JsonProcessingException : should never be thrown
     * @throws ApiException : should never be thrown
     */
    @Test
    public void getSimilarTermsForBulkExpressionContextTest() throws ApiException, JsonProcessingException {
        int count = 5;
        int contextId = 0;
        PosType posType = PosType.NOUN;
        List<List<Term>> listOfTerms = new ArrayList<>();
        listOfTerms.add(createTerms(count));
        listOfTerms.add(createTerms(count));
        
        when(expressionsApi.getSimilarTermsForBulkExpressionContext(eq(TERM_1_TEXT_1_JSON), eq(contextId), eq(posType.name()), isNull(Boolean.class), eq(NOT_NULL_RETINA), isNull(Integer.class), isNull(Integer.class), isNull(Double.class))).thenReturn(listOfTerms);
        List<List<Term>> actualListOfTerms = expressionsRetinaApiImpl.getSimilarTermsBulk(contextId, posType, TERM_1, TEXT_1);
        assertEquals(listOfTerms.size(), actualListOfTerms.size());
        verify(expressionsApi, times(1)).getSimilarTermsForBulkExpressionContext(eq(TERM_1_TEXT_1_JSON), eq(contextId), eq(posType.name()), isNull(Boolean.class), eq(NOT_NULL_RETINA), isNull(Integer.class), isNull(Integer.class), isNull(Double.class));
    }
    
    /**
     * 
     * {@link ExpressionsRetinaApiImpl#getSimilarTermsBulk(Integer, PosType, Boolean, io.cortical.rest.model.Model...)} method test.
     * @throws JsonProcessingException : should never be thrown
     * @throws ApiException : should never be thrown
     */
    @Test
    public void getSimilarTermsForBulkExpressionContextTest_fingerprint() throws ApiException, JsonProcessingException {
        int count = 5;
        boolean getFingerprint = false;
        int contextId = 0;
        PosType posType = PosType.NOUN;
        List<List<Term>> listOfTerms = new ArrayList<>();
        listOfTerms.add(createTerms(count));
        listOfTerms.add(createTerms(count));
        
        when(expressionsApi.getSimilarTermsForBulkExpressionContext(eq(TERM_1_TEXT_1_JSON), eq(contextId), eq(posType.name()), eq(getFingerprint), eq(NOT_NULL_RETINA), isNull(Integer.class), isNull(Integer.class), isNull(Double.class))).thenReturn(listOfTerms);
        List<List<Term>> actualListOfTerms = expressionsRetinaApiImpl.getSimilarTermsBulk(contextId, posType, getFingerprint, TERM_1, TEXT_1);
        assertEquals(listOfTerms.size(), actualListOfTerms.size());
        verify(expressionsApi, times(1)).getSimilarTermsForBulkExpressionContext(eq(TERM_1_TEXT_1_JSON), eq(contextId), eq(posType.name()), eq(getFingerprint), eq(NOT_NULL_RETINA), isNull(Integer.class), isNull(Integer.class), isNull(Double.class));
    }
    
    /**
     * 
     * {@link ExpressionsRetinaApiImpl#getSimilarTermsBulk(Integer, PosType, Double, io.cortical.rest.model.Model...)} method test.
     * @throws JsonProcessingException : should never be thrown
     * @throws ApiException : should never be thrown
     */
    @Test
    public void getSimilarTermsForBulkExpressionContextTest_sparsity() throws ApiException, JsonProcessingException {
        int count = 5;
        int contextId = 0;
        PosType posType = PosType.NOUN;
        Double sparsity = 0.5;
        List<List<Term>> listOfTerms = new ArrayList<>();
        listOfTerms.add(createTerms(count));
        listOfTerms.add(createTerms(count));
        
        when(expressionsApi.getSimilarTermsForBulkExpressionContext(eq(TERM_1_TEXT_1_JSON), eq(contextId), eq(posType.name()), isNull(Boolean.class), eq(NOT_NULL_RETINA), isNull(Integer.class), isNull(Integer.class), eq(sparsity))).thenReturn(listOfTerms);
        List<List<Term>> actualListOfTerms = expressionsRetinaApiImpl.getSimilarTermsBulk(contextId, posType, sparsity, TERM_1, TEXT_1);
        assertEquals(listOfTerms.size(), actualListOfTerms.size());
        verify(expressionsApi, times(1)).getSimilarTermsForBulkExpressionContext(eq(TERM_1_TEXT_1_JSON), eq(contextId), eq(posType.name()), isNull(Boolean.class), eq(NOT_NULL_RETINA), isNull(Integer.class), isNull(Integer.class), eq(sparsity));
    }
    
    /**
     * 
     * {@link ExpressionsRetinaApiImpl#getSimilarTermsBulk(Integer, PosType, Boolean, Double, io.cortical.rest.model.Model...)} method test.
     * @throws JsonProcessingException : should never be thrown
     * @throws ApiException : should never be thrown
     */
    @Test
    public void getSimilarTermsForBulkExpressionContextTest_sparsityFingerprint() throws ApiException, JsonProcessingException {
        int count = 5;
        boolean getFingerprint = false;
        int contextId = 0;
        PosType posType = PosType.NOUN;
        Double sparsity = 0.5;
        List<List<Term>> listOfTerms = new ArrayList<>();
        listOfTerms.add(createTerms(count));
        listOfTerms.add(createTerms(count));
        
        when(expressionsApi.getSimilarTermsForBulkExpressionContext(eq(TERM_1_TEXT_1_JSON), eq(contextId), eq(posType.name()), eq(getFingerprint), eq(NOT_NULL_RETINA), isNull(Integer.class), isNull(Integer.class), eq(sparsity))).thenReturn(listOfTerms);
        List<List<Term>> actualListOfTerms = expressionsRetinaApiImpl.getSimilarTermsBulk(contextId, posType, getFingerprint, sparsity, TERM_1, TEXT_1);
        assertEquals(listOfTerms.size(), actualListOfTerms.size());
        verify(expressionsApi, times(1)).getSimilarTermsForBulkExpressionContext(eq(TERM_1_TEXT_1_JSON), eq(contextId), eq(posType.name()), eq(getFingerprint), eq(NOT_NULL_RETINA), isNull(Integer.class), isNull(Integer.class), eq(sparsity));
    }
    
    
    /**
     * 
     * {@link ExpressionsRetinaApiImpl#getSimilarTermsBulk(Integer, PosType, Pagination, Boolean, Double, io.cortical.rest.model.Model...)} method test.
     * @throws JsonProcessingException : should never be thrown
     * @throws ApiException : should never be thrown
     */
    @Test
    public void getSimilarTermsForBulkExpressionContextTest_sparsityFingerprintPagination() throws ApiException, JsonProcessingException {
        int count = 5;
        boolean getFingerprint = false;
        int startIndex = 0;
        int maxResults = 100;
        int contextId = 0;
        Pagination pagination = new Pagination(startIndex, maxResults);
        PosType posType = PosType.NOUN;
        Double sparsity = 0.5;
        List<List<Term>> listOfTerms = new ArrayList<>();
        listOfTerms.add(createTerms(count));
        listOfTerms.add(createTerms(count));
        
        when(expressionsApi.getSimilarTermsForBulkExpressionContext(eq(TERM_1_TEXT_1_JSON), eq(contextId), eq(posType.name()), eq(getFingerprint), eq(NOT_NULL_RETINA), eq(startIndex), eq(maxResults), eq(sparsity))).thenReturn(listOfTerms);
        List<List<Term>> actualListOfTerms = expressionsRetinaApiImpl.getSimilarTermsBulk(contextId, posType, pagination, getFingerprint, sparsity, TERM_1, TEXT_1);
        assertEquals(listOfTerms.size(), actualListOfTerms.size());
        verify(expressionsApi, times(1)).getSimilarTermsForBulkExpressionContext(eq(TERM_1_TEXT_1_JSON), eq(contextId), eq(posType.name()), eq(getFingerprint), eq(NOT_NULL_RETINA), eq(startIndex), eq(maxResults), eq(sparsity));
    }
    
    /**
     * 
     * {@link ExpressionsRetinaApiImpl#getSimilarTermsBulk(Integer, PosType, String)} method test.
     * @throws JsonProcessingException : should never be thrown
     * @throws ApiException : should never be thrown
     */
    @Test
    public void getSimilarTermsForBulkExpressionContextTest_json() throws ApiException, JsonProcessingException {
        int count = 5;
        int contextId = 0;
        PosType posType = PosType.NOUN;
        List<List<Term>> listOfTerms = new ArrayList<>();
        listOfTerms.add(createTerms(count));
        listOfTerms.add(createTerms(count));
        
        when(expressionsApi.getSimilarTermsForBulkExpressionContext(eq(TERM_1_TEXT_1_JSON), eq(contextId), eq(posType.name()), isNull(Boolean.class), eq(NOT_NULL_RETINA), isNull(Integer.class), isNull(Integer.class), isNull(Double.class))).thenReturn(listOfTerms);
        List<List<Term>> actualListOfTerms = expressionsRetinaApiImpl.getSimilarTermsBulk(contextId, posType, TERM_1_TEXT_1_JSON);
        assertEquals(listOfTerms.size(), actualListOfTerms.size());
        verify(expressionsApi, times(1)).getSimilarTermsForBulkExpressionContext(eq(TERM_1_TEXT_1_JSON), eq(contextId), eq(posType.name()), isNull(Boolean.class), eq(NOT_NULL_RETINA), isNull(Integer.class), isNull(Integer.class), isNull(Double.class));
    }
    
    /**
     * 
     * {@link ExpressionsRetinaApiImpl#getSimilarTermsBulk(Integer, PosType, Boolean, String)} method test.
     * @throws JsonProcessingException : should never be thrown
     * @throws ApiException : should never be thrown
     */
    @Test
    public void getSimilarTermsForBulkExpressionContextTest_fingerprint_json() throws ApiException, JsonProcessingException {
        int count = 5;
        boolean getFingerprint = false;
        int contextId = 0;
        PosType posType = PosType.NOUN;
        List<List<Term>> listOfTerms = new ArrayList<>();
        listOfTerms.add(createTerms(count));
        listOfTerms.add(createTerms(count));
        
        when(expressionsApi.getSimilarTermsForBulkExpressionContext(eq(TERM_1_TEXT_1_JSON), eq(contextId), eq(posType.name()), eq(getFingerprint), eq(NOT_NULL_RETINA), isNull(Integer.class), isNull(Integer.class), isNull(Double.class))).thenReturn(listOfTerms);
        List<List<Term>> actualListOfTerms = expressionsRetinaApiImpl.getSimilarTermsBulk(contextId, posType, getFingerprint, TERM_1_TEXT_1_JSON);
        assertEquals(listOfTerms.size(), actualListOfTerms.size());
        verify(expressionsApi, times(1)).getSimilarTermsForBulkExpressionContext(eq(TERM_1_TEXT_1_JSON), eq(contextId), eq(posType.name()), eq(getFingerprint), eq(NOT_NULL_RETINA), isNull(Integer.class), isNull(Integer.class), isNull(Double.class));
    }
    
    /**
     * 
     * {@link ExpressionsRetinaApiImpl#getSimilarTermsBulk(Integer, PosType, Double, String)} method test.
     * @throws JsonProcessingException : should never be thrown
     * @throws ApiException : should never be thrown
     */
    @Test
    public void getSimilarTermsForBulkExpressionContextTest_sparsity_json() throws ApiException, JsonProcessingException {
        int count = 5;
        int contextId = 0;
        PosType posType = PosType.NOUN;
        Double sparsity = 0.5;
        List<List<Term>> listOfTerms = new ArrayList<>();
        listOfTerms.add(createTerms(count));
        listOfTerms.add(createTerms(count));
        
        when(expressionsApi.getSimilarTermsForBulkExpressionContext(eq(TERM_1_TEXT_1_JSON), eq(contextId), eq(posType.name()), isNull(Boolean.class), eq(NOT_NULL_RETINA), isNull(Integer.class), isNull(Integer.class), eq(sparsity))).thenReturn(listOfTerms);
        List<List<Term>> actualListOfTerms = expressionsRetinaApiImpl.getSimilarTermsBulk(contextId, posType, sparsity, TERM_1_TEXT_1_JSON);
        assertEquals(listOfTerms.size(), actualListOfTerms.size());
        verify(expressionsApi, times(1)).getSimilarTermsForBulkExpressionContext(eq(TERM_1_TEXT_1_JSON), eq(contextId), eq(posType.name()), isNull(Boolean.class), eq(NOT_NULL_RETINA), isNull(Integer.class), isNull(Integer.class), eq(sparsity));
    }
    
    /**
     * 
     * {@link ExpressionsRetinaApiImpl#getSimilarTermsBulk(Integer, PosType, Boolean, Double, String)} method test.
     * @throws JsonProcessingException : should never be thrown
     * @throws ApiException : should never be thrown
     */
    @Test
    public void getSimilarTermsForBulkExpressionContextTest_sparsityFingerprint_json() throws ApiException, JsonProcessingException {
        int count = 5;
        boolean getFingerprint = false;
        int contextId = 0;
        PosType posType = PosType.NOUN;
        Double sparsity = 0.5;
        List<List<Term>> listOfTerms = new ArrayList<>();
        listOfTerms.add(createTerms(count));
        listOfTerms.add(createTerms(count));
        
        when(expressionsApi.getSimilarTermsForBulkExpressionContext(eq(TERM_1_TEXT_1_JSON), eq(contextId), eq(posType.name()), eq(getFingerprint), eq(NOT_NULL_RETINA), isNull(Integer.class), isNull(Integer.class), eq(sparsity))).thenReturn(listOfTerms);
        List<List<Term>> actualListOfTerms = expressionsRetinaApiImpl.getSimilarTermsBulk(contextId, posType, getFingerprint, sparsity, TERM_1_TEXT_1_JSON);
        assertEquals(listOfTerms.size(), actualListOfTerms.size());
        verify(expressionsApi, times(1)).getSimilarTermsForBulkExpressionContext(eq(TERM_1_TEXT_1_JSON), eq(contextId), eq(posType.name()), eq(getFingerprint), eq(NOT_NULL_RETINA), isNull(Integer.class), isNull(Integer.class), eq(sparsity));
    }
    
    
    /**
     * 
     * {@link ExpressionsRetinaApiImpl#getSimilarTermsBulk(Integer, PosType, Pagination, Boolean, Double, String)} method test.
     * @throws JsonProcessingException : should never be thrown
     * @throws ApiException : should never be thrown
     */
    @Test
    public void getSimilarTermsForBulkExpressionContextTest_sparsityFingerprintPagination_json() throws ApiException, JsonProcessingException {
        int count = 5;
        boolean getFingerprint = false;
        int startIndex = 0;
        int maxResults = 100;
        int contextId = 0;
        Pagination pagination = new Pagination(startIndex, maxResults);
        PosType posType = PosType.NOUN;
        Double sparsity = 0.5;
        List<List<Term>> listOfTerms = new ArrayList<>();
        listOfTerms.add(createTerms(count));
        listOfTerms.add(createTerms(count));
        
        when(expressionsApi.getSimilarTermsForBulkExpressionContext(eq(TERM_1_TEXT_1_JSON), eq(contextId), eq(posType.name()), eq(getFingerprint), eq(NOT_NULL_RETINA), eq(startIndex), eq(maxResults), eq(sparsity))).thenReturn(listOfTerms);
        List<List<Term>> actualListOfTerms = expressionsRetinaApiImpl.getSimilarTermsBulk(contextId, posType, pagination, getFingerprint, sparsity, TERM_1_TEXT_1_JSON);
        assertEquals(listOfTerms.size(), actualListOfTerms.size());
        verify(expressionsApi, times(1)).getSimilarTermsForBulkExpressionContext(eq(TERM_1_TEXT_1_JSON), eq(contextId), eq(posType.name()), eq(getFingerprint), eq(NOT_NULL_RETINA), eq(startIndex), eq(maxResults), eq(sparsity));
    }
    
    /**
     * 
     * {@link ExpressionsRetinaApiImpl#getSimilarTerms(Integer, PosType, io.cortical.rest.model.Model)} method test.
     * @throws JsonProcessingException : should never be thrown
     * @throws ApiException : should never be thrown
     */
    @Test
    public void getSimilarTermsForExpressionContextTest() throws ApiException, JsonProcessingException {
        int count = 5;
        int contextId = 0;
        PosType posType = PosType.NOUN;
        
        when(expressionsApi.getSimilarTermsForExpressionContext(eq(TERM_1_JSON), eq(contextId), eq(posType.name()), isNull(Boolean.class), eq(NOT_NULL_RETINA), isNull(Integer.class), isNull(Integer.class), isNull(Double.class))).thenReturn(createTerms(count));
        List<Term> actualTerms = expressionsRetinaApiImpl.getSimilarTerms(contextId, posType, TERM_1);
        assertEquals(count, actualTerms.size());
        verify(expressionsApi, times(1)).getSimilarTermsForExpressionContext(eq(TERM_1_JSON), eq(contextId), eq(posType.name()), isNull(Boolean.class), eq(NOT_NULL_RETINA), isNull(Integer.class), isNull(Integer.class), isNull(Double.class));
    }
    
    /**
     * 
     * {@link ExpressionsRetinaApiImpl#getSimilarTerms(Integer, PosType, Boolean, io.cortical.rest.model.Model)} method test.
     * @throws JsonProcessingException : should never be thrown
     * @throws ApiException : should never be thrown
     */
    @Test
    public void getSimilarTermsForExpressionContextTest_fingerprint() throws ApiException, JsonProcessingException {
        int count = 5;
        boolean getFingerprint = false;
        int contextId = 0;
        PosType posType = PosType.NOUN;
        
        when(expressionsApi.getSimilarTermsForExpressionContext(eq(TERM_1_JSON), eq(contextId), eq(posType.name()), eq(getFingerprint), eq(NOT_NULL_RETINA), isNull(Integer.class), isNull(Integer.class), isNull(Double.class))).thenReturn(createTerms(count));
        List<Term> actualTerms = expressionsRetinaApiImpl.getSimilarTerms(contextId, posType, getFingerprint, TERM_1);
        assertEquals(count, actualTerms.size());
        verify(expressionsApi, times(1)).getSimilarTermsForExpressionContext(eq(TERM_1_JSON), eq(contextId), eq(posType.name()), eq(getFingerprint), eq(NOT_NULL_RETINA), isNull(Integer.class), isNull(Integer.class), isNull(Double.class));
    }
    
    /**
     * 
     * {@link ExpressionsRetinaApiImpl#getSimilarTerms(Integer, PosType, Double, io.cortical.rest.model.Model)} method test.
     * @throws JsonProcessingException : should never be thrown
     * @throws ApiException : should never be thrown
     */
    @Test
    public void getSimilarTermsForExpressionContextTest_sparsity() throws ApiException, JsonProcessingException {
        int count = 5;
        int contextId = 0;
        PosType posType = PosType.NOUN;
        Double sparsity = 0.5;
        
        when(expressionsApi.getSimilarTermsForExpressionContext(eq(TERM_1_JSON), eq(contextId), eq(posType.name()), isNull(Boolean.class), eq(NOT_NULL_RETINA), isNull(Integer.class), isNull(Integer.class), eq(sparsity))).thenReturn(createTerms(count));
        List<Term> actualTerms = expressionsRetinaApiImpl.getSimilarTerms(contextId, posType, sparsity, TERM_1);
        assertEquals(count, actualTerms.size());
        verify(expressionsApi, times(1)).getSimilarTermsForExpressionContext(eq(TERM_1_JSON), eq(contextId), eq(posType.name()), isNull(Boolean.class), eq(NOT_NULL_RETINA), isNull(Integer.class), isNull(Integer.class), eq(sparsity));
    }
    
    /**
     * 
     * {@link ExpressionsRetinaApiImpl#getSimilarTerms(Integer, PosType, Boolean, Double, io.cortical.rest.model.Model)} method test.
     * @throws JsonProcessingException : should never be thrown
     * @throws ApiException : should never be thrown
     */
    @Test
    public void getSimilarTermsForExpressionContextTest_sparsityFingerprint() throws ApiException, JsonProcessingException {
        int count = 5;
        boolean getFingerprint = false;
        int contextId = 0;
        PosType posType = PosType.NOUN;
        Double sparsity = 0.5;
        
        when(expressionsApi.getSimilarTermsForExpressionContext(eq(TERM_1_JSON), eq(contextId), eq(posType.name()), eq(getFingerprint), eq(NOT_NULL_RETINA), isNull(Integer.class), isNull(Integer.class), eq(sparsity))).thenReturn(createTerms(count));
        List<Term> actualTerms = expressionsRetinaApiImpl.getSimilarTerms(contextId, posType, getFingerprint, sparsity, TERM_1);
        assertEquals(count, actualTerms.size());
        verify(expressionsApi, times(1)).getSimilarTermsForExpressionContext(eq(TERM_1_JSON), eq(contextId), eq(posType.name()), eq(getFingerprint), eq(NOT_NULL_RETINA), isNull(Integer.class), isNull(Integer.class), eq(sparsity));
    }
    
    /**
     * 
     * {@link ExpressionsRetinaApiImpl#getSimilarTerms(Integer, PosType, Pagination, Boolean, Double, io.cortical.rest.model.Model)} method test.
     * @throws JsonProcessingException : should never be thrown
     * @throws ApiException : should never be thrown
     */
    @Test
    public void getSimilarTermsForExpressionContextTest_sparsityFingerprintPagination() throws ApiException, JsonProcessingException {
        int count = 5;
        boolean getFingerprint = false;
        int startIndex = 0;
        int maxResults = 100;
        int contextId = 0;
        PosType posType = PosType.NOUN;
        Double sparsity = 0.5;
        Pagination pagination = new Pagination(startIndex, maxResults);
        
        when(expressionsApi.getSimilarTermsForExpressionContext(eq(TERM_1_JSON), eq(contextId), eq(posType.name()), eq(getFingerprint), eq(NOT_NULL_RETINA), eq(startIndex), eq(maxResults), eq(sparsity))).thenReturn(createTerms(count));
        List<Term> actualTerms = expressionsRetinaApiImpl.getSimilarTerms(contextId, posType, pagination, getFingerprint, sparsity, TERM_1);
        assertEquals(count, actualTerms.size());
        verify(expressionsApi, times(1)).getSimilarTermsForExpressionContext(eq(TERM_1_JSON), eq(contextId), eq(posType.name()), eq(getFingerprint), eq(NOT_NULL_RETINA), eq(startIndex), eq(maxResults), eq(sparsity));
    }
    
    
    /**
     * 
     * {@link ExpressionsRetinaApiImpl#getSimilarTerms(Integer, PosType, String)} method test.
     * @throws JsonProcessingException : should never be thrown
     * @throws ApiException : should never be thrown
     */
    @Test
    public void getSimilarTermsForExpressionContextTest_json() throws ApiException, JsonProcessingException {
        int count = 5;
        int contextId = 0;
        PosType posType = PosType.NOUN;
        
        when(expressionsApi.getSimilarTermsForExpressionContext(eq(TERM_1_JSON), eq(contextId), eq(posType.name()), isNull(Boolean.class), eq(NOT_NULL_RETINA), isNull(Integer.class), isNull(Integer.class), isNull(Double.class))).thenReturn(createTerms(count));
        List<Term> actualTerms = expressionsRetinaApiImpl.getSimilarTerms(contextId, posType, TERM_1_JSON);
        assertEquals(count, actualTerms.size());
        verify(expressionsApi, times(1)).getSimilarTermsForExpressionContext(eq(TERM_1_JSON), eq(contextId), eq(posType.name()), isNull(Boolean.class), eq(NOT_NULL_RETINA), isNull(Integer.class), isNull(Integer.class), isNull(Double.class));
    }
    
    /**
     * 
     * {@link ExpressionsRetinaApiImpl#getSimilarTerms(Integer, PosType, Boolean, String)} method test.
     * @throws JsonProcessingException : should never be thrown
     * @throws ApiException : should never be thrown
     */
    @Test
    public void getSimilarTermsForExpressionContextTest_fingerprint_json() throws ApiException, JsonProcessingException {
        int count = 5;
        boolean getFingerprint = false;
        int contextId = 0;
        PosType posType = PosType.NOUN;
        
        when(expressionsApi.getSimilarTermsForExpressionContext(eq(TERM_1_JSON), eq(contextId), eq(posType.name()), eq(getFingerprint), eq(NOT_NULL_RETINA), isNull(Integer.class), isNull(Integer.class), isNull(Double.class))).thenReturn(createTerms(count));
        List<Term> actualTerms = expressionsRetinaApiImpl.getSimilarTerms(contextId, posType, getFingerprint, TERM_1_JSON);
        assertEquals(count, actualTerms.size());
        verify(expressionsApi, times(1)).getSimilarTermsForExpressionContext(eq(TERM_1_JSON), eq(contextId), eq(posType.name()), eq(getFingerprint), eq(NOT_NULL_RETINA), isNull(Integer.class), isNull(Integer.class), isNull(Double.class));
    }
    
    /**
     * 
     * {@link ExpressionsRetinaApiImpl#getSimilarTerms(Integer, PosType, Double, String)} method test.
     * @throws JsonProcessingException : should never be thrown
     * @throws ApiException : should never be thrown
     */
    @Test
    public void getSimilarTermsForExpressionContextTest_sparsity_json() throws ApiException, JsonProcessingException {
        int count = 5;
        int contextId = 0;
        PosType posType = PosType.NOUN;
        Double sparsity = 0.5;
        
        when(expressionsApi.getSimilarTermsForExpressionContext(eq(TERM_1_JSON), eq(contextId), eq(posType.name()), isNull(Boolean.class), eq(NOT_NULL_RETINA), isNull(Integer.class), isNull(Integer.class), eq(sparsity))).thenReturn(createTerms(count));
        List<Term> actualTerms = expressionsRetinaApiImpl.getSimilarTerms(contextId, posType, sparsity, TERM_1_JSON);
        assertEquals(count, actualTerms.size());
        verify(expressionsApi, times(1)).getSimilarTermsForExpressionContext(eq(TERM_1_JSON), eq(contextId), eq(posType.name()), isNull(Boolean.class), eq(NOT_NULL_RETINA), isNull(Integer.class), isNull(Integer.class), eq(sparsity));
    }
    
    /**
     * 
     * {@link ExpressionsRetinaApiImpl#getSimilarTerms(Integer, PosType, Boolean, Double, String)} method test.
     * @throws JsonProcessingException : should never be thrown
     * @throws ApiException : should never be thrown
     */
    @Test
    public void getSimilarTermsForExpressionContextTest_sparsityFingerprint_json() throws ApiException, JsonProcessingException {
        int count = 5;
        boolean getFingerprint = false;
        int contextId = 0;
        PosType posType = PosType.NOUN;
        Double sparsity = 0.5;
        
        when(expressionsApi.getSimilarTermsForExpressionContext(eq(TERM_1_JSON), eq(contextId), eq(posType.name()), eq(getFingerprint), eq(NOT_NULL_RETINA), isNull(Integer.class), isNull(Integer.class), eq(sparsity))).thenReturn(createTerms(count));
        List<Term> actualTerms = expressionsRetinaApiImpl.getSimilarTerms(contextId, posType, getFingerprint, sparsity, TERM_1_JSON);
        assertEquals(count, actualTerms.size());
        verify(expressionsApi, times(1)).getSimilarTermsForExpressionContext(eq(TERM_1_JSON), eq(contextId), eq(posType.name()), eq(getFingerprint), eq(NOT_NULL_RETINA), isNull(Integer.class), isNull(Integer.class), eq(sparsity));
    }
    
    /**
     * 
     * {@link ExpressionsRetinaApiImpl#getSimilarTerms(Integer, PosType, Pagination, Boolean, Double, String)} method test.
     * @throws JsonProcessingException : should never be thrown
     * @throws ApiException : should never be thrown
     */
    @Test
    public void getSimilarTermsForExpressionContextTest_sparsityFingerprintPagination_json() throws ApiException, JsonProcessingException {
        int count = 5;
        boolean getFingerprint = false;
        int startIndex = 0;
        int maxResults = 100;
        int contextId = 0;
        PosType posType = PosType.NOUN;
        Double sparsity = 0.5;
        Pagination pagination = new Pagination(startIndex, maxResults);
        
        when(expressionsApi.getSimilarTermsForExpressionContext(eq(TERM_1_JSON), eq(contextId), eq(posType.name()), eq(getFingerprint), eq(NOT_NULL_RETINA), eq(startIndex), eq(maxResults), eq(sparsity))).thenReturn(createTerms(count));
        List<Term> actualTerms = expressionsRetinaApiImpl.getSimilarTerms(contextId, posType, pagination, getFingerprint, sparsity, TERM_1_JSON);
        assertEquals(count, actualTerms.size());
        verify(expressionsApi, times(1)).getSimilarTermsForExpressionContext(eq(TERM_1_JSON), eq(contextId), eq(posType.name()), eq(getFingerprint), eq(NOT_NULL_RETINA), eq(startIndex), eq(maxResults), eq(sparsity));
    }
    
    /**
     * 
     * {@link ExpressionsRetinaApiImpl#resolveBulk(io.cortical.rest.model.Model...)} method test.
     * @throws JsonProcessingException : should never be thrown
     * @throws ApiException : should never be thrown
     */
    @Test
    public void resolveBulkExpressionTest() throws ApiException, JsonProcessingException {
        int count = 5;
        
        when(expressionsApi.resolveBulkExpression(eq(TERM_1_TEXT_1_JSON), eq(NOT_NULL_RETINA), isNull(Double.class))).thenReturn(createFingerprints(count));
        List<Fingerprint> actualFingerprints = expressionsRetinaApiImpl.resolveBulk(TERM_1, TEXT_1);
        assertEquals(count, actualFingerprints.size());
        verify(expressionsApi, times(1)).resolveBulkExpression(eq(TERM_1_TEXT_1_JSON), eq(NOT_NULL_RETINA), isNull(Double.class));
    }
    
    /**
     * 
     * {@link ExpressionsRetinaApiImpl#resolveBulk(Double, io.cortical.rest.model.Model...)} method test.
     * @throws JsonProcessingException : should never be thrown
     * @throws ApiException : should never be thrown
     */
    @Test
    public void resolveBulkExpressionTest_sparsity() throws ApiException, JsonProcessingException {
        int count = 5;
        Double sparsity = 0.5;
        
        when(expressionsApi.resolveBulkExpression(eq(TERM_1_TEXT_1_JSON), eq(NOT_NULL_RETINA), eq(sparsity))).thenReturn(createFingerprints(count));
        List<Fingerprint> actualFingerprints = expressionsRetinaApiImpl.resolveBulk(sparsity, TERM_1, TEXT_1);
        assertEquals(count, actualFingerprints.size());
        verify(expressionsApi, times(1)).resolveBulkExpression(eq(TERM_1_TEXT_1_JSON), eq(NOT_NULL_RETINA), eq(sparsity));
    }
    
    /**
     * 
     * {@link ExpressionsRetinaApiImpl#resolveBulk(String)} method test.
     * @throws JsonProcessingException : should never be thrown
     * @throws ApiException : should never be thrown
     */
    @Test
    public void resolveBulkExpressionTest_json() throws ApiException, JsonProcessingException {
        int count = 5;
        
        when(expressionsApi.resolveBulkExpression(eq(TERM_1_TEXT_1_JSON), eq(NOT_NULL_RETINA), isNull(Double.class))).thenReturn(createFingerprints(count));
        List<Fingerprint> actualFingerprints = expressionsRetinaApiImpl.resolveBulk(TERM_1_TEXT_1_JSON);
        assertEquals(count, actualFingerprints.size());
        verify(expressionsApi, times(1)).resolveBulkExpression(eq(TERM_1_TEXT_1_JSON), eq(NOT_NULL_RETINA), isNull(Double.class));
    }
    
    /**
     * 
     * {@link ExpressionsRetinaApiImpl#resolveBulk(Double, String)} method test.
     * @throws JsonProcessingException : should never be thrown
     * @throws ApiException : should never be thrown
     */
    @Test
    public void resolveBulkExpressionTest_sparsity_json() throws ApiException, JsonProcessingException {
        int count = 5;
        Double sparsity = 0.5;
        
        when(expressionsApi.resolveBulkExpression(eq(TERM_1_TEXT_1_JSON), eq(NOT_NULL_RETINA), eq(sparsity))).thenReturn(createFingerprints(count));
        List<Fingerprint> actualFingerprints = expressionsRetinaApiImpl.resolveBulk(sparsity, TERM_1_TEXT_1_JSON);
        assertEquals(count, actualFingerprints.size());
        verify(expressionsApi, times(1)).resolveBulkExpression(eq(TERM_1_TEXT_1_JSON), eq(NOT_NULL_RETINA), eq(sparsity));
    }
}
