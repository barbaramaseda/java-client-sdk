/*******************************************************************************
 * Copyright (c) cortical.io GmbH. All rights reserved.
 *  
 * This software is confidential and proprietary information.
 * You shall use it only in accordance with the terms of the
 * license agreement you entered into with cortical.io GmbH.
 ******************************************************************************/
package io.cortical.retina.core;

import static io.cortical.retina.core.ApiTestUtils.NOT_NULL_RETINA;
import static io.cortical.retina.model.TestDataHarness.createContexts;
import static io.cortical.retina.model.TestDataHarness.createFingerprint;
import static io.cortical.retina.model.TestDataHarness.createFingerprints;
import static io.cortical.retina.model.TestDataHarness.createTerms;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import io.cortical.retina.model.Context;
import io.cortical.retina.model.Fingerprint;
import io.cortical.retina.model.Model;
import io.cortical.retina.model.Term;
import io.cortical.retina.service.ApiException;
import io.cortical.retina.service.ExpressionsApi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import com.fasterxml.jackson.core.JsonProcessingException;



/**
 * {@link Expressions} test class. 
 * 
 */
public class ExpressionsTest {
    /**
     * 
     */
    private static final Term TERM_1 = new Term("term_1");
    private static final Term TERM_2 = new Term("term_2");
    private static final String TERM_1_JSON;
    
    static {
        try {
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
    private Expressions expressions;
    
    @Before
    public void initialize() {
        initMocks(this);
        expressions = new Expressions(expressionsApi, NOT_NULL_RETINA);
    }
    
    /**
     * {@link Expressions#getFingerprintForExpression(io.cortical.retina.model.Model, double)}
     * @throws JsonProcessingException      should never be thrown
     * @throws ApiException     should never be thrown
     */
    @Test
    public void testGetFingerprintForExpression() throws ApiException, JsonProcessingException {
        double sparsity = 0.02;
        
        when(expressionsApi.resolveExpression(eq(TERM_1_JSON), eq(NOT_NULL_RETINA), eq(0.02))).thenReturn(createFingerprint(sparsity));
        Fingerprint fingerprint = expressions.getFingerprintForExpression(TERM_1, sparsity);
        assertEquals("[124, 133, 146, 181, 192, 230, 249, 279, 442, 447, 514, 597, 612, "
            + "659, 785, 858, 861, 895, 1150, 1247, 1262, 1315, 1321, 1485, "
            + "1496, 1518, 1522, 1535, 1580, 1685, 1701, 1882, 1896, 2054, "
            + "2068, 2097, 2108, 2115, 2231, 2235, 2290, 2404, 2405, 2432, "
            + "2466, 2474, 2489, 2502, 2520, 2534, 2599, 2623, 2799, 2800, "
            + "2821, 2838, 2906, 2937, 2963, 3033, 3092, 3210, 3213, 3261, "
            + "3286, 3401, 3436, 3596, 3987, 4106, 4123, 4160, 4229, 4263, "
            + "4352, 4492, 4517, 4539, 4546, 4568, 4596, 4623, 4651, 4666, "
            + "4752, 4763, 4777, 4778, 4871, 4965, 5006, 5058, 5090, 5163, "
            + "5166, 5186, 5383, 5444, 5513, 5542, 5566, 5627, 5635, 5649, "
            + "5864, 5902, 5904, 5922, 5982, 6005, 6042, 6047, 6078, 6124, "
            + "6133, 6161, 6200, 6252, 6268, 6290, 6301, 6333, 6353, 6429, "
            + "6467, 6484, 6496, 6513, 6586, 6635, 6843, 6862, 6897, 6933, "
            + "6938, 6955, 7066, 7090, 7121, 7126, 7148, 7151, 7205, 7236, "
            + "7253, 7302, 7393, 7492, 7501, 7516, 7526, 7541, 7592, 7596, "
            + "7678, 7684, 7729, 7744, 7869, 7873, 7886, 7927, 7972, 7998, "
            + "8148, 8274, 8332, 8335, 8505, 8514, 8544, 8732, 8756, 8758, "
            + "8845, 8894, 8981, 8983, 8993, 8994, 9115, 9172, 9355, 9365, "
            + "9396, 9503, 9559, 9624, 9642, 9676, 9737, 9762, 9791, 9811, "
            + "9877, 10061, 10078, 10096, 10264, 10288, 10313, 10338, 10344, "
            + "10368, 10405, 10430, 10495, 10527, 10545, 10587, 10629, 10732, "
            + "10766, 10782, 10800, 10822, 10830, 10904, 10986, 11193, 11235, "
            + "11276, 11286, 11311, 11371, 11402, 11421, 11423, 11466, 11502, "
            + "11570, 11595, 11688, 11798, 11885, 11896, 11920, 11953, 12091, "
            + "12208, 12218, 12286, 12308, 12329, 12342, 12413, 12419, 12472, "
            + "12486, 12530, 12608, 12623, 12633, 12699, 12704, 12792, 12827, "
            + "12920, 12954, 13023, 13040, 13042, 13079, 13084, 13108, 13140, "
            + "13195, 13201, 13256, 13264, 13391, 13398, 13442, 13463, 13487, "
            + "13532, 13554, 13584, 13659, 13662, 13683, 13884, 13931, 14014, "
            + "14018, 14136, 14183, 14194, 14283, 14310, 14515, 14559, 14603, "
            + "14647, 14666, 14706, 14722, 14732, 14800, 14804, 14819, 14820, "
            + "14886, 14953, 15062, 15081, 15247, 15380, 15403, 15434, 15471, "
            + "15562, 15580, 15765, 15769, 15835, 15851, 15878, 15889, 15958, "
            + "15991, 16016, 16032, 16137, 16143, 16318, 16354, 16366]", 
            Arrays.toString(fingerprint.getPositions()));
        assertEquals(Math.rint(16384.* 0.02), fingerprint.getPositions().length, 0.001);
        verify(expressionsApi, times(1)).resolveExpression(eq(TERM_1_JSON), eq(NOT_NULL_RETINA), eq(sparsity));
    }
    
    /**
     * {@link Expressions#getFingerprintsForExpressions(io.cortical.retina.model.Model, double)}
     * @throws JsonProcessingException      should never be thrown
     * @throws ApiException     should never be thrown
     */
    @Test
    public void testGetFingerprintsForExpressions() throws ApiException, JsonProcessingException {
        int count = 2;
        double sparsity = 0.02;
        List<Term> terms = Arrays.asList(TERM_1, TERM_2);
        String termJson = Model.toJson(terms);
        
        when(expressionsApi.resolveBulkExpression(eq(termJson), eq(NOT_NULL_RETINA), eq(0.02))).thenReturn(createFingerprints(count, sparsity));
        List<Fingerprint> fingerprints = expressions.getFingerprintsForExpressions(terms, sparsity);
        assertEquals("[124, 133, 146, 181, 192, 230, 249, 279, 442, 447, 514, 597, 612, "
            + "659, 785, 858, 861, 895, 1150, 1247, 1262, 1315, 1321, 1485, "
            + "1496, 1518, 1522, 1535, 1580, 1685, 1701, 1882, 1896, 2054, "
            + "2068, 2097, 2108, 2115, 2231, 2235, 2290, 2404, 2405, 2432, "
            + "2466, 2474, 2489, 2502, 2520, 2534, 2599, 2623, 2799, 2800, "
            + "2821, 2838, 2906, 2937, 2963, 3033, 3092, 3210, 3213, 3261, "
            + "3286, 3401, 3436, 3596, 3987, 4106, 4123, 4160, 4229, 4263, "
            + "4352, 4492, 4517, 4539, 4546, 4568, 4596, 4623, 4651, 4666, "
            + "4752, 4763, 4777, 4778, 4871, 4965, 5006, 5058, 5090, 5163, "
            + "5166, 5186, 5383, 5444, 5513, 5542, 5566, 5627, 5635, 5649, "
            + "5864, 5902, 5904, 5922, 5982, 6005, 6042, 6047, 6078, 6124, "
            + "6133, 6161, 6200, 6252, 6268, 6290, 6301, 6333, 6353, 6429, "
            + "6467, 6484, 6496, 6513, 6586, 6635, 6843, 6862, 6897, 6933, "
            + "6938, 6955, 7066, 7090, 7121, 7126, 7148, 7151, 7205, 7236, "
            + "7253, 7302, 7393, 7492, 7501, 7516, 7526, 7541, 7592, 7596, "
            + "7678, 7684, 7729, 7744, 7869, 7873, 7886, 7927, 7972, 7998, "
            + "8148, 8274, 8332, 8335, 8505, 8514, 8544, 8732, 8756, 8758, "
            + "8845, 8894, 8981, 8983, 8993, 8994, 9115, 9172, 9355, 9365, "
            + "9396, 9503, 9559, 9624, 9642, 9676, 9737, 9762, 9791, 9811, "
            + "9877, 10061, 10078, 10096, 10264, 10288, 10313, 10338, 10344, "
            + "10368, 10405, 10430, 10495, 10527, 10545, 10587, 10629, 10732, "
            + "10766, 10782, 10800, 10822, 10830, 10904, 10986, 11193, 11235, "
            + "11276, 11286, 11311, 11371, 11402, 11421, 11423, 11466, 11502, "
            + "11570, 11595, 11688, 11798, 11885, 11896, 11920, 11953, 12091, "
            + "12208, 12218, 12286, 12308, 12329, 12342, 12413, 12419, 12472, "
            + "12486, 12530, 12608, 12623, 12633, 12699, 12704, 12792, 12827, "
            + "12920, 12954, 13023, 13040, 13042, 13079, 13084, 13108, 13140, "
            + "13195, 13201, 13256, 13264, 13391, 13398, 13442, 13463, 13487, "
            + "13532, 13554, 13584, 13659, 13662, 13683, 13884, 13931, 14014, "
            + "14018, 14136, 14183, 14194, 14283, 14310, 14515, 14559, 14603, "
            + "14647, 14666, 14706, 14722, 14732, 14800, 14804, 14819, 14820, "
            + "14886, 14953, 15062, 15081, 15247, 15380, 15403, 15434, 15471, "
            + "15562, 15580, 15765, 15769, 15835, 15851, 15878, 15889, 15958, "
            + "15991, 16016, 16032, 16137, 16143, 16318, 16354, 16366]", 
            Arrays.toString(fingerprints.get(0).getPositions()));
        assertEquals(Math.rint(16384.* 0.02), fingerprints.get(0).getPositions().length, 0.001);
        assertEquals(Math.rint(16384.* 0.02), fingerprints.get(1).getPositions().length, 0.001);
        verify(expressionsApi, times(1)).resolveBulkExpression(eq(termJson), eq(NOT_NULL_RETINA), eq(0.02));
    }
    
    /**
     * {@link Expressions#getContextsForExpression(Model, int, int, double, boolean)}
     * @throws JsonProcessingException      should never be thrown
     * @throws ApiException     should never be thrown
     */
    @Test
    public void testGetContextsForExpression() throws ApiException, JsonProcessingException {
        int count = 5;
        List<Context> contexts = createContexts(count);
        
        when(expressionsApi.getContextsForExpression(eq(TERM_1_JSON), eq(false), eq(NOT_NULL_RETINA), eq(0), eq(10), eq(0.02))).thenReturn(contexts); 
        List<Context> actualContexts = expressions.getContextsForExpression(TERM_1, 0, 10, 0.02, false);
        assertEquals(contexts.size(), actualContexts.size());
        verify(expressionsApi, times(1)).getContextsForExpression(eq(TERM_1_JSON), eq(false), eq(NOT_NULL_RETINA), eq(0), eq(10), eq(0.02));
    }
    
    /**
     * 
     * {@link Expressions#getContextsForExpressions(List, int, int, boolean, double)}
     * @throws JsonProcessingException      should never be thrown.
     * @throws ApiException     should never be thrown.
     */
    @Test
    public void testGetContextsForExpressions() throws JsonProcessingException, ApiException {
        int count = 5;
        List<Term> listOfTerms = Arrays.asList(TERM_1, TERM_2);
        String jsonTermList = Model.toJson(listOfTerms);
        List<List<Context>> listOfContexts = new ArrayList<>();
        listOfContexts.add(createContexts(count));
        listOfContexts.add(createContexts(count));
        
        when(expressionsApi.getContextsForBulkExpression(eq(jsonTermList), eq(false), eq(NOT_NULL_RETINA), eq(0), eq(10), eq(0.02))).thenReturn(listOfContexts); 
        List<List<Context>> actualListOfContexts = expressions.getContextsForExpressions(listOfTerms, 0, 10, false, 0.02);
        assertEquals(listOfContexts.size(), actualListOfContexts.size());
        verify(expressionsApi, times(1)).getContextsForBulkExpression(eq(jsonTermList), eq(false), eq(NOT_NULL_RETINA), eq(0), eq(10), eq(0.02));
    }
    
    /**
     * 
     * {@link Expressions#getSimilarTermsForExpression(Model, int, int, int, PosType, boolean, double)}
     * @throws JsonProcessingException      should never be thrown
     * @throws ApiException     should never be thrown
     */
    @Test
    public void testGetSimilarTermsForExpression() throws ApiException, JsonProcessingException {
        int count = 5;
        int contextId = 0;
        PosType posType = PosType.NOUN;
        
        when(expressionsApi.getSimilarTermsForExpressionContext(eq(TERM_1_JSON), eq(contextId), eq(posType.name()), eq(false), eq(NOT_NULL_RETINA), eq(0), eq(10), eq(0.02))).thenReturn(createTerms(count));
        List<Term> actualTerms = expressions.getSimilarTermsForExpression(TERM_1, 0, 10, contextId, posType, false, 0.02);
        assertEquals(count, actualTerms.size());
        verify(expressionsApi, times(1)).getSimilarTermsForExpressionContext(eq(TERM_1_JSON), eq(contextId), eq(posType.name()), eq(false), eq(NOT_NULL_RETINA), eq(0), eq(10), eq(0.02));
    }
    
    /**
     * 
     * {@link Expressions#getSimilarTermsForExpressions(List, int, int, int, PosType, boolean, double)}
     * @throws JsonProcessingException : should never be thrown
     * @throws ApiException : should never be thrown
     */
    @Test
    public void testGetSimilarTermsForExpressions() throws ApiException, JsonProcessingException {
        int count = 5;
        int contextId = 0;
        PosType posType = PosType.NOUN;
        List<Term> listOfTerms = Arrays.asList(TERM_1, TERM_2);
        String jsonTermList = Model.toJson(listOfTerms);
        List<List<Term>> listOfSimTerms = new ArrayList<>();
        listOfSimTerms.add(createTerms(count));
        listOfSimTerms.add(createTerms(count));
        
        when(expressionsApi.getSimilarTermsForBulkExpressionContext(eq(jsonTermList), eq(contextId), eq(posType.name()), eq(false), eq(NOT_NULL_RETINA), eq(0), eq(10), eq(0.02))).thenReturn(listOfSimTerms);
        List<List<Term>> actualTerms = expressions.getSimilarTermsForExpressions(listOfTerms, 0, 10, contextId, posType, false, 0.02);
        assertEquals(listOfSimTerms.size(), actualTerms.size());
        verify(expressionsApi, times(1)).getSimilarTermsForBulkExpressionContext(eq(jsonTermList), eq(contextId), eq(posType.name()), eq(false), eq(NOT_NULL_RETINA), eq(0), eq(10), eq(0.02));
    }
}
