/*******************************************************************************
 * Copyright (c) cortical.io GmbH. All rights reserved.
 *  
 * This software is confidential and proprietary information.
 * You shall use it only in accordance with the terms of the
 * license agreement you entered into with cortical.io GmbH.
 ******************************************************************************/
package io.cortical.services;

import static io.cortical.rest.model.TestDataMother.createContexts;
import static io.cortical.rest.model.TestDataMother.createTerms;
import static io.cortical.services.ApiTestUtils.NOT_NULL_RETINA;
import io.cortical.rest.model.Context;
import io.cortical.rest.model.Term;
import io.cortical.services.Pagination;
import io.cortical.services.PosType;
import io.cortical.services.TermsRetinaApiImpl;
import io.cortical.services.api.client.ApiException;
import io.cortical.services.api.client.api.TermsApi;
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
 * 
 * {@link TermsRetinaApiImpl} test class.
 */
public class TestTermsRetinaApiImpl {
    /**
     * 
     */
    private static final String TERM = "term";
    /**
     * 
     */
    @Mock
    private TermsApi termApi;
    private TermsRetinaApiImpl termRetinaApiImpl;
    
    /**
     * initialization.
     */
    @Before
    public void before() {
        initMocks(this);
        termRetinaApiImpl = new TermsRetinaApiImpl(termApi, NOT_NULL_RETINA);
        
    }
    
    /**
     * {@link TermsRetinaApiImpl#getContexts(String)} test method.
     * 
     * @throws ApiException : should never be thrown
     */
    @Test
    public void getContextsForTermTest() throws ApiException {
        int count = 5;
        
        when(
                termApi.getContextsForTerm(eq(TERM), isNull(Boolean.class), eq(NOT_NULL_RETINA), isNull(Integer.class),
                        isNull(Integer.class))).thenReturn(createContexts(count));
        List<Context> contexts = termRetinaApiImpl.getContexts(TERM);
        assertEquals(count, contexts.size());
        verify(termApi, times(1)).getContextsForTerm(eq(TERM), isNull(Boolean.class), eq(NOT_NULL_RETINA),
                isNull(Integer.class), isNull(Integer.class));
    }
    
    /**
     * {@link TermsRetinaApiImpl#getContexts(String, Boolean)} test method.
     * 
     * @throws ApiException : should never be thrown
     */
    @Test
    public void getContextsForTermTest_withFingerprint() throws ApiException {
        int count = 5;
        boolean getFingerprint = false;
        when(
                termApi.getContextsForTerm(eq(TERM), eq(getFingerprint), eq(NOT_NULL_RETINA), isNull(Integer.class),
                        isNull(Integer.class))).thenReturn(createContexts(count));
        List<Context> contexts = termRetinaApiImpl.getContexts(TERM, getFingerprint);
        assertEquals(count, contexts.size());
        verify(termApi, times(1)).getContextsForTerm(eq(TERM), eq(getFingerprint), eq(NOT_NULL_RETINA),
                isNull(Integer.class), isNull(Integer.class));
    }
    
    /**
     * {@link TermsRetinaApiImpl#getContexts(String, Pagination, Boolean)} test method.
     * 
     * @throws ApiException : should never be thrown
     */
    @Test
    public void getContextsForTermTest_withFingerprintAndPagination() throws ApiException {
        int count = 5;
        boolean getFingerprint = false;
        int startIndex = 10;
        int maxResults = 10;
        Pagination pagination = new Pagination(startIndex, maxResults);
        when(
                termApi.getContextsForTerm(eq(TERM), eq(getFingerprint), eq(NOT_NULL_RETINA), eq(startIndex),
                        eq(maxResults))).thenReturn(createContexts(count));
        List<Context> contexts = termRetinaApiImpl.getContexts(TERM, pagination, getFingerprint);
        assertEquals(count, contexts.size());
        verify(termApi, times(1)).getContextsForTerm(eq(TERM), eq(getFingerprint), eq(NOT_NULL_RETINA), eq(startIndex),
                eq(maxResults));
    }
    
    
    /**
     * {@link TermsRetinaApiImpl#getSimilarTerms(String, Integer, PosType)} test method.
     * 
     * @throws ApiException : should never be thrown
     */
    @Test
    public void getSimilarTermsTest() throws ApiException {
        int count = 5;
        int contextId = 0;
        PosType posType = PosType.VERB;
        when(
                termApi.getSimilarTerms(eq(TERM), eq(contextId), eq(posType.name()), isNull(Boolean.class),
                        eq(NOT_NULL_RETINA), isNull(Integer.class), isNull(Integer.class))).thenReturn(
                createTerms(count));
        List<Term> terms = termRetinaApiImpl.getSimilarTerms(TERM, contextId, posType);
        assertEquals(count, terms.size());
        verify(termApi, times(1)).getSimilarTerms(eq(TERM), eq(contextId), eq(posType.name()), isNull(Boolean.class),
                eq(NOT_NULL_RETINA), isNull(Integer.class), isNull(Integer.class));
    }
    
    /**
     * {@link TermsRetinaApiImpl#getSimilarTerms(String, Integer, PosType, Boolean)} test method.
     * 
     * @throws ApiException : should never be thrown
     */
    @Test
    public void getSimilarTermsTest_withFingerprint() throws ApiException {
        int count = 5;
        boolean getFingerprint = false;
        int contextId = 0;
        PosType posType = PosType.VERB;
        when(
                termApi.getSimilarTerms(eq(TERM), eq(contextId), eq(posType.name()), eq(getFingerprint),
                        eq(NOT_NULL_RETINA), isNull(Integer.class), isNull(Integer.class))).thenReturn(
                createTerms(count));
        List<Term> terms = termRetinaApiImpl.getSimilarTerms(TERM, contextId, posType, getFingerprint);
        assertEquals(count, terms.size());
        verify(termApi, times(1)).getSimilarTerms(eq(TERM), eq(contextId), eq(posType.name()), eq(getFingerprint),
                eq(NOT_NULL_RETINA), isNull(Integer.class), isNull(Integer.class));
    }
    
    /**
     * {@link TermsRetinaApiImpl#getSimilarTerms(String, Integer, PosType, Pagination, Boolean)} test method.
     * 
     * @throws ApiException : should never be thrown
     */
    @Test
    public void getSimilarTermsTest_withFingerprintAndPagination() throws ApiException {
        int count = 5;
        boolean getFingerprint = false;
        int contextId = 0;
        int startIndex = 0;
        int maxResults = 100;
        PosType posType = PosType.VERB;
        Pagination pagination = new Pagination(startIndex, maxResults);
        when(
                termApi.getSimilarTerms(eq(TERM), eq(contextId), eq(posType.name()), eq(getFingerprint),
                        eq(NOT_NULL_RETINA), eq(startIndex), eq(maxResults))).thenReturn(createTerms(count));
        List<Term> terms = termRetinaApiImpl.getSimilarTerms(TERM, contextId, posType, pagination, getFingerprint);
        assertEquals(count, terms.size());
        verify(termApi, times(1)).getSimilarTerms(eq(TERM), eq(contextId), eq(posType.name()), eq(getFingerprint),
                eq(NOT_NULL_RETINA), eq(startIndex), eq(maxResults));
    }
    
    /**
     * {@link TermsRetinaApiImpl#getTerm(String)} test method.
     * 
     * @throws ApiException : should never be thrown
     */
    @Test
    public void getTermTest() throws ApiException {
        int count = 5;
        when(
                termApi.getTerm(eq(TERM), isNull(Boolean.class), eq(NOT_NULL_RETINA), isNull(Integer.class),
                        isNull(Integer.class))).thenReturn(createTerms(count));
        List<Term> terms = termRetinaApiImpl.getTerm(TERM);
        assertEquals(count, terms.size());
        verify(termApi, times(1)).getTerm(eq(TERM), isNull(Boolean.class), eq(NOT_NULL_RETINA), isNull(Integer.class),
                isNull(Integer.class));
    }
    
    /**
     * {@link TermsRetinaApiImpl#getTerm(String, Boolean)} test method.
     * 
     * @throws ApiException : should never be thrown
     */
    @Test
    public void getTermTest_withfingerprint() throws ApiException {
        int count = 5;
        boolean getFingerprint = false;
        when(
                termApi.getTerm(eq(TERM), eq(getFingerprint), eq(NOT_NULL_RETINA), isNull(Integer.class),
                        isNull(Integer.class))).thenReturn(createTerms(count));
        List<Term> terms = termRetinaApiImpl.getTerm(TERM, getFingerprint);
        assertEquals(count, terms.size());
        verify(termApi, times(1)).getTerm(eq(TERM), eq(getFingerprint), eq(NOT_NULL_RETINA), isNull(Integer.class),
                isNull(Integer.class));
    }
    
    /**
     * {@link TermsRetinaApiImpl#getTerm(String, Pagination, Boolean)} test method.
     * 
     * @throws ApiException : should never be thrown
     */
    @Test
    public void getTermTest_withFingerprintAndPagination() throws ApiException {
        int count = 5;
        boolean getFingerprint = false;
        int startIndex = 0;
        int maxResults = 100;
        Pagination pagination = new Pagination(startIndex, maxResults);
        when(termApi.getTerm(eq(TERM), eq(getFingerprint), eq(NOT_NULL_RETINA), eq(startIndex), eq(maxResults)))
                .thenReturn(createTerms(count));
        List<Term> terms = termRetinaApiImpl.getTerm(TERM, pagination, getFingerprint);
        assertEquals(count, terms.size());
        verify(termApi, times(1)).getTerm(eq(TERM), eq(getFingerprint), eq(NOT_NULL_RETINA), eq(startIndex),
                eq(maxResults));
    }
}
