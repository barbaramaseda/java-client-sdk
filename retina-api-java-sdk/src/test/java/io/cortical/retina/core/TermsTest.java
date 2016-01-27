/*******************************************************************************
 * Copyright (c) cortical.io GmbH. All rights reserved.
 *  
 * This software is confidential and proprietary information.
 * You shall use it only in accordance with the termsList of the
 * license agreement you entered into with cortical.io GmbH.
 ******************************************************************************/
package io.cortical.retina.core;

import static io.cortical.retina.core.ApiTestUtils.NOT_NULL_RETINA;
import static io.cortical.retina.model.TestDataHarness.createContexts;
import static io.cortical.retina.model.TestDataHarness.createTerms;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import io.cortical.retina.model.Context;
import io.cortical.retina.model.Term;
import io.cortical.retina.rest.ApiException;
import io.cortical.retina.rest.TermsApi;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;


/**
 * 
 * {@link TermsRetinaApiImpl} test class.
 */
public class TermsTest {
    /**
     * 
     */
    private static final String TERM = "term";
    /**
     * 
     */
    @Mock
    private TermsApi termApi;
    private Terms terms;
    
    /**
     * initialization.
     */
    @Before
    public void before() {
        initMocks(this);
        terms = new Terms(termApi, NOT_NULL_RETINA);
    }
    
    /**
     * {@link Terms#getContextsForTerm(String, int, int, Boolean)} test method.
     * 
     * @throws ApiException should never be thrown
     */
    @Test
    public void testGetContextsForTerm() throws ApiException {
        int count = 5;
        
        when(termApi.getContextsForTerm(eq(TERM), eq(false), eq(NOT_NULL_RETINA), eq(0), eq(10)))
            .thenReturn(createContexts(count));
        List<Context> contexts = terms.getContextsForTerm(TERM, 0, 10, false);
        assertEquals(count, contexts.size());
        verify(termApi, times(1)).getContextsForTerm(eq(TERM), eq(false), eq(NOT_NULL_RETINA), eq(0), eq(10));
    }
    
    /**
     * {@link Terms#getContextsForTerm(String, int, int, Boolean)} test method.
     * 
     * @throws ApiException should never be thrown
     */
    @Test
    public void testGetContextsForTermTest_withFingerprint() throws ApiException {
        int count = 5;
        when(termApi.getContextsForTerm(eq(TERM), eq(true), eq(NOT_NULL_RETINA), eq(0),eq(10)))
            .thenReturn(createContexts(count));
        List<Context> contexts = terms.getContextsForTerm(TERM, 0, 10, true);
        assertEquals(count, contexts.size());
        verify(termApi, times(1)).getContextsForTerm(eq(TERM), eq(true), eq(NOT_NULL_RETINA), eq(0),eq(10));
    }
    
    /**
     * {@link Terms#getContextsForTerm(String, int, int, Boolean)} test method.
     * 
     * @throws ApiException should never be thrown
     */
    @Test
    public void testGetContextsForTermTest_withFingerprintAndStartIndex() throws ApiException {
        int count = 5;
        int startIndex = 10;
        int maxResults = 10;
        when(termApi.getContextsForTerm(eq(TERM), eq(true), eq(NOT_NULL_RETINA), eq(startIndex), 
            eq(maxResults))).thenReturn(createContexts(count));
        List<Context> contexts = terms.getContextsForTerm(TERM, 10, 10, true);
        assertEquals(count, contexts.size());
        verify(termApi, times(1)).getContextsForTerm(eq(TERM), eq(true), eq(NOT_NULL_RETINA), eq(startIndex),
                eq(maxResults));
    }
    
    
    /**
     * {@link Terms#getSimilarTermsForTerm(String, Integer, PosType, int, int, Boolean))} test method.
     * 
     * @throws ApiException should never be thrown
     */
    @Test
    public void testGetSimilarTermsForTerm() throws ApiException {
        int count = 5;
        int contextId = 0;
        PosType posType = PosType.VERB;
        when(termApi.getSimilarTerms(eq(TERM), eq(contextId), eq(posType.name()), eq(false),
            eq(NOT_NULL_RETINA), eq(0), eq(10))).thenReturn(
                createTerms(count));
        List<Term> termsList = terms.getSimilarTermsForTerm(TERM, contextId, posType, 0, 10, false);
        assertEquals(count, termsList.size());
        verify(termApi, times(1)).getSimilarTerms(eq(TERM), eq(contextId), eq(posType.name()), eq(false),
                        eq(NOT_NULL_RETINA), eq(0), eq(10));
    }
    
    /**
     * {@link Terms#getSimilarTermsForTerm(String, Integer, PosType, int, int, Boolean))} test method.
     * 
     * @throws ApiException should never be thrown
     */
    @Test
    public void testGetSimilarTerms_withFingerprint() throws ApiException {
        int count = 5;
        int contextId = 0;
        PosType posType = PosType.VERB;
        when(termApi.getSimilarTerms(eq(TERM), eq(contextId), eq(posType.name()), eq(true),
            eq(NOT_NULL_RETINA), eq(0), eq(10))).thenReturn(createTerms(count));
        List<Term> termsList = terms.getSimilarTermsForTerm(TERM, contextId, posType, 0, 10, true);
        assertEquals(count, termsList.size());
        verify(termApi, times(1)).getSimilarTerms(eq(TERM), eq(contextId), eq(posType.name()), eq(true),
            eq(NOT_NULL_RETINA), eq(0), eq(10));
    }
    
    /**
     * {@link Terms#getSimilarTermsForTerm(String, Integer, PosType, int, int, Boolean))} test method.
     * 
     * @throws ApiException should never be thrown
     */
    @Test
    public void testGetSimilarTerms_withFingerprintAndStartIndex() throws ApiException {
        int count = 5;
        boolean getFingerprint = true;
        int contextId = 0;
        int startIndex = 0;
        int maxResults = 100;
        PosType posType = PosType.VERB;
        when(termApi.getSimilarTerms(eq(TERM), eq(contextId), eq(posType.name()), eq(getFingerprint),
            eq(NOT_NULL_RETINA), eq(startIndex), eq(maxResults))).thenReturn(createTerms(count));
        List<Term> termsList = terms.getSimilarTermsForTerm(TERM, contextId, posType, 0, 100, getFingerprint);
        assertEquals(count, termsList.size());
        verify(termApi, times(1)).getSimilarTerms(eq(TERM), eq(contextId), eq(posType.name()), eq(getFingerprint),
            eq(NOT_NULL_RETINA), eq(startIndex), eq(maxResults));
    }
    
    /**
     * {@link Terms#getTerms(String, int, int, boolean)} test method.
     * 
     * @throws ApiException should never be thrown
     */
    @Test
    public void testGetTerm() throws ApiException {
        int count = 5;
        when(termApi.getTerm(eq(TERM), eq(false), eq(NOT_NULL_RETINA), eq(0),
            eq(5))).thenReturn(createTerms(count));
        List<Term> termsList = terms.getTerms(TERM, 0, 5, false);
        assertEquals(count, termsList.size());
        verify(termApi, times(1)).getTerm(eq(TERM), eq(false), eq(NOT_NULL_RETINA), eq(0), eq(5));
    }
    
    /**
     * {@link Terms#getTerms(String, int, int, boolean)} test method.
     * 
     * @throws ApiException should never be thrown
     */
    @Test
    public void testGetTerm_withfingerprint() throws ApiException {
        int count = 5;
        boolean getFingerprint = true;
        when(termApi.getTerm(eq(TERM), eq(getFingerprint), eq(NOT_NULL_RETINA), eq(0),
            eq(5))).thenReturn(createTerms(count));
        List<Term> termsList = terms.getTerms(TERM, 0, 5, getFingerprint);
        assertEquals(count, termsList.size());
        verify(termApi, times(1)).getTerm(eq(TERM), eq(getFingerprint), eq(NOT_NULL_RETINA), eq(0), eq(5));
    }
    
    /**
     * {@link Terms#getTerms(String, int, int, boolean)} test method.
     * 
     * @throws ApiException should never be thrown
     */
    @Test
    public void testGetTerm_withFingerprintAndStartIndex() throws ApiException {
        int count = 5;
        boolean getFingerprint = false;
        int startIndex = 0;
        int maxResults = 100;
        when(termApi.getTerm(eq(TERM), eq(getFingerprint), eq(NOT_NULL_RETINA), eq(0),
            eq(maxResults))).thenReturn(createTerms(count));
        List<Term> termsList = terms.getTerms(TERM, startIndex, maxResults, getFingerprint);
        assertEquals(count, termsList.size());
        verify(termApi, times(1)).getTerm(eq(TERM), eq(getFingerprint), eq(NOT_NULL_RETINA), eq(0), eq(maxResults));
    }
}
