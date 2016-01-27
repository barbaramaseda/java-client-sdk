/*******************************************************************************
 * Copyright (c) cortical.io GmbH. All rights reserved.
 *  
 * This software is confidential and proprietary information.
 * You shall use it only in accordance with the terms of the
 * license agreement you entered into with cortical.io GmbH.
 ******************************************************************************/
package io.cortical.retina.rest;

import static io.cortical.retina.core.ApiTestUtils.NOT_NULL_API_KEY;
import static io.cortical.retina.core.ApiTestUtils.NOT_NULL_BASE_PATH;
import static io.cortical.retina.core.ApiTestUtils.NOT_NULL_RETINA;
import static io.cortical.retina.core.ApiTestUtils.prepareApiPostMethod;
import static io.cortical.retina.core.ApiTestUtils.setApiInvoker;
import static io.cortical.retina.core.ApiTestUtils.verifyApiPostMethod;
import io.cortical.retina.model.Context;
import io.cortical.retina.model.Fingerprint;
import io.cortical.retina.model.Term;
import io.cortical.retina.rest.ApiException;
import io.cortical.retina.rest.ApiInvoker;
import io.cortical.retina.rest.ExpressionsApi;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static java.nio.file.Files.readAllBytes;
import static java.nio.file.Paths.get;
import static org.junit.Assert.assertNotNull;
import static org.mockito.MockitoAnnotations.initMocks;


/**
 * 
 * {@link ExpressionsApi} test class.
 */
public class TestExpressionsApi {
    
    
    private static final String EXPRESSION_VALID_JSON =
            "{\"sub\":[ { \"term\" : \"apple\" }, { \"term\" : \"banana\" } ] }";
    private static final String VALID_FINGERPRINT_JSON =
            "{ \"positions\" : [ 5, 30, 31, 32, 35, 36, 37, 38, 68, 69, 70, 98, 100, 117, 130, 141, 144, 163, 164, 165, 166, 183, 185, 186, 216, 218, 224, 238, 246, 247, 250, 251, 270, 272, 276, 278, 287, 302, 303, 304, 307, 309, 310, 330, 333, 334, 335, 336, 337, 365, 366, 368, 369, 398, 401, 404, 439, 447, 505, 512, 521, 548, 556, 564, 577, 616, 742, 774, 783, 810, 870, 871, 937, 938, 959, 960, 966, 968, 978, 982, 992, 993 ] }";
    private static final String VALID_LIST_OF_CONTEXTS_JSON =
            "[[{\"context_id\":0,\"context_label\":\"software\",\"fingerprint\":{\"positions\":[]}},{\"context_id\":1,\"context_label\":\"user\",\"fingerprint\":{\"positions\":[]}}],[{\"context_id\":0,\"context_label\":\"thing\",\"fingerprint\":{\"positions\":[]}},{\"context_id\":1,\"context_label\":\"test\",\"fingerprint\":{\"positions\":[]}},{\"context_id\":2,\"context_label\":\"tour\",\"fingerprint\":{\"positions\":[]}},{\"context_id\":3,\"context_label\":\"clause\",\"fingerprint\":{\"positions\":[]}},{\"context_id\":4,\"context_label\":\"band\",\"fingerprint\":{\"positions\":[]}}],[{\"context_id\":0,\"context_label\":\"something\",\"fingerprint\":{\"positions\":[]}},{\"context_id\":1,\"context_label\":\"using\",\"fingerprint\":{\"positions\":[]}},{\"context_id\":2,\"context_label\":\"match\",\"fingerprint\":{\"positions\":[]}},{\"context_id\":3,\"context_label\":\"article\",\"fingerprint\":{\"positions\":[]}},{\"context_id\":4,\"context_label\":\"band\",\"fingerprint\":{\"positions\":[]}}],[{\"context_id\":0,\"context_label\":\"wheelbase\",\"fingerprint\":{\"positions\":[]}},{\"context_id\":1,\"context_label\":\"race\",\"fingerprint\":{\"positions\":[]}},{\"context_id\":2,\"context_label\":\"line\",\"fingerprint\":{\"positions\":[]}},{\"context_id\":3,\"context_label\":\"police\",\"fingerprint\":{\"positions\":[]}},{\"context_id\":4,\"context_label\":\"sedan\",\"fingerprint\":{\"positions\":[]}}],[{\"context_id\":0,\"context_label\":\"using\",\"fingerprint\":{\"positions\":[]}},{\"context_id\":1,\"context_label\":\"test\",\"fingerprint\":{\"positions\":[]}},{\"context_id\":2,\"context_label\":\"translation\",\"fingerprint\":{\"positions\":[]}},{\"context_id\":3,\"context_label\":\"article\",\"fingerprint\":{\"positions\":[]}},{\"context_id\":4,\"context_label\":\"bar\",\"fingerprint\":{\"positions\":[]}}],[{\"context_id\":0,\"context_label\":\"wheelbase\",\"fingerprint\":{\"positions\":[]}},{\"context_id\":1,\"context_label\":\"race\",\"fingerprint\":{\"positions\":[]}},{\"context_id\":2,\"context_label\":\"service\",\"fingerprint\":{\"positions\":[]}},{\"context_id\":3,\"context_label\":\"police\",\"fingerprint\":{\"positions\":[]}},{\"context_id\":4,\"context_label\":\"sedan\",\"fingerprint\":{\"positions\":[]}}]]";
    private static final String VALID_CONTEXTS_JSON =
            "[{\"context_id\":0,\"context_label\":\"software\",\"fingerprint\":{\"positions\":[]}},{\"context_id\":1,\"context_label\":\"hardware\",\"fingerprint\":{\"positions\":[]}},{\"context_id\":2,\"context_label\":\"selling\",\"fingerprint\":{\"positions\":[]}},{\"context_id\":3,\"context_label\":\"tree\",\"fingerprint\":{\"positions\":[]}},{\"context_id\":4,\"context_label\":\"iphone\",\"fingerprint\":{\"positions\":[]}}]";
    private static final String VALID_LIST_OF_TERMS_JSON =
            "[[{\"term\":\"software\",\"df\":0.004772707002451764,\"score\":55.0,\"pos_types\":[\"NOUN\"],\"fingerprint\":{\"positions\":[]}},{\"term\":\"apple\",\"df\":0.0013443430694127692,\"score\":55.0,\"pos_types\":[\"VERB\",\"NOUN\"],\"fingerprint\":{\"positions\":[]}},{\"term\":\"functionality\",\"df\":8.319026198355362E-4,\"score\":48.0,\"pos_types\":[\"NOUN\"],\"fingerprint\":{\"positions\":[]}},{\"term\":\"interface\",\"df\":0.0017454776949633536,\"score\":48.0,\"pos_types\":[\"NOUN\"],\"fingerprint\":{\"positions\":[]}},{\"term\":\"files\",\"df\":0.0016724784207640582,\"score\":47.0,\"pos_types\":[\"NOUN\"],\"fingerprint\":{\"positions\":[]}}],[{\"term\":\"thing\",\"df\":0.002723667969233914,\"score\":189.0,\"pos_types\":[\"NOUN\"],\"fingerprint\":{\"positions\":[]}},{\"term\":\"something\",\"df\":0.005368699096558092,\"score\":159.0,\"pos_types\":[\"NOUN\"],\"fingerprint\":{\"positions\":[]}},{\"term\":\"everything\",\"df\":0.0029043592420044473,\"score\":124.0,\"pos_types\":[\"NOUN\"],\"fingerprint\":{\"positions\":[]}},{\"term\":\"anything\",\"df\":0.0031318856926771032,\"score\":121.0,\"pos_types\":[\"NOUN\"],\"fingerprint\":{\"positions\":[]}},{\"term\":\"know\",\"df\":0.005312178866435468,\"score\":120.0,\"pos_types\":[\"VERB\"],\"fingerprint\":{\"positions\":[]}}],[{\"term\":\"something\",\"df\":0.005368699096558092,\"score\":325.0,\"pos_types\":[\"NOUN\"],\"fingerprint\":{\"positions\":[]}},{\"term\":\"thing\",\"df\":0.002723667969233914,\"score\":159.0,\"pos_types\":[\"NOUN\"],\"fingerprint\":{\"positions\":[]}},{\"term\":\"everything\",\"df\":0.0029043592420044473,\"score\":159.0,\"pos_types\":[\"NOUN\"],\"fingerprint\":{\"positions\":[]}},{\"term\":\"anything\",\"df\":0.0031318856926771032,\"score\":156.0,\"pos_types\":[\"NOUN\"],\"fingerprint\":{\"positions\":[]}},{\"term\":\"know\",\"df\":0.005312178866435468,\"score\":155.0,\"pos_types\":[\"VERB\"],\"fingerprint\":{\"positions\":[]}}],[{\"term\":\"wheelbase\",\"df\":3.436025243004465E-4,\"score\":147.0,\"pos_types\":[\"NOUN\"],\"fingerprint\":{\"positions\":[]}},{\"term\":\"car\",\"df\":0.00991633704964688,\"score\":147.0,\"pos_types\":[\"NOUN\"],\"fingerprint\":{\"positions\":[]}},{\"term\":\"chevrolet\",\"df\":3.0674150465525766E-4,\"score\":120.0,\"pos_types\":[\"NOUN\"],\"fingerprint\":{\"positions\":[]}},{\"term\":\"chassis\",\"df\":0.0011824436890103711,\"score\":118.0,\"pos_types\":[\"NOUN\"],\"fingerprint\":{\"positions\":[]}},{\"term\":\"hatchback\",\"df\":2.6395381126319533E-4,\"score\":118.0,\"pos_types\":[\"NOUN\"],\"fingerprint\":{\"positions\":[]}}],[{\"term\":\"using\",\"df\":0.027119013535510966,\"score\":132.0,\"pos_types\":[\"NOUN\"],\"fingerprint\":{\"positions\":[]}},{\"term\":\"text\",\"df\":0.005426520303844662,\"score\":81.0,\"pos_types\":[\"NOUN\"],\"fingerprint\":{\"positions\":[]}},{\"term\":\"tool\",\"df\":0.0020052394686982727,\"score\":79.0,\"pos_types\":[\"NOUN\"],\"fingerprint\":{\"positions\":[]}},{\"term\":\"enables\",\"df\":9.583865107749097E-4,\"score\":79.0,\"pos_types\":[\"NOUN\"],\"fingerprint\":{\"positions\":[]}},{\"term\":\"easier\",\"df\":0.0019706912973445467,\"score\":79.0,\"pos_types\":[\"NOUN\"],\"fingerprint\":{\"positions\":[]}}],[{\"term\":\"wheelbase\",\"df\":3.436025243004465E-4,\"score\":147.0,\"pos_types\":[\"NOUN\"],\"fingerprint\":{\"positions\":[]}},{\"term\":\"car\",\"df\":0.00991633704964688,\"score\":147.0,\"pos_types\":[\"NOUN\"],\"fingerprint\":{\"positions\":[]}},{\"term\":\"chevrolet\",\"df\":3.0674150465525766E-4,\"score\":120.0,\"pos_types\":[\"NOUN\"],\"fingerprint\":{\"positions\":[]}},{\"term\":\"chassis\",\"df\":0.0011824436890103711,\"score\":118.0,\"pos_types\":[\"NOUN\"],\"fingerprint\":{\"positions\":[]}},{\"term\":\"hatchback\",\"df\":2.6395381126319533E-4,\"score\":118.0,\"pos_types\":[\"NOUN\"],\"fingerprint\":{\"positions\":[]}}]]";
    private static final String VALID_TERMS_JSON =
            "[{\"term\":\"software\",\"df\":0.004772707002451764,\"score\":126.0,\"pos_types\":[\"NOUN\"],\"fingerprint\":{\"positions\":[]}},{\"term\":\"apple\",\"df\":0.0013443430694127692,\"score\":126.0,\"pos_types\":[\"VERB\",\"NOUN\"],\"fingerprint\":{\"positions\":[]}},{\"term\":\"desktop\",\"df\":6.159404106201946E-4,\"score\":107.0,\"pos_types\":[\"NOUN\"],\"fingerprint\":{\"positions\":[]}},{\"term\":\"hardware\",\"df\":0.0019212541651145287,\"score\":106.0,\"pos_types\":[\"NOUN\"],\"fingerprint\":{\"positions\":[]}},{\"term\":\"os\",\"df\":6.776645493986089E-4,\"score\":101.0,\"pos_types\":[\"NOUN\"],\"fingerprint\":{\"positions\":[]}}]";
    private static String BULK_EXPRESSION_VALID_JSON;
    /**
     * 
     */
    @Mock
    private ApiInvoker apiInvoker;
    private ExpressionsApi expressionsApi;
    
    /**
     * set up.
     * @throws java.lang.Exception if it happens.
     */
    @Before
    public void setUp() throws Exception {
        initMocks(this);
        expressionsApi = new ExpressionsApi(NOT_NULL_API_KEY);
        expressionsApi.setBasePath(NOT_NULL_BASE_PATH);
        BULK_EXPRESSION_VALID_JSON = new String(readAllBytes(get("src/test/resources/bulkInput.json")));
        setApiInvoker(apiInvoker, expressionsApi);
    }
    
    /**
     * {@link ExpressionsApi#resolveExpression(String, String, Double)} failure test.
     * 
     * @throws ApiException : expected error.
     */
    @Test(expected = ApiException.class)
    public void resolveExpressionTest_nullRetinaName() throws ApiException {
        expressionsApi.resolveExpression(EXPRESSION_VALID_JSON, null, 0.5);
    }
    
    /**
     * {@link ExpressionsApi#resolveExpression(String, String, Double)} failure test.
     * 
     * @throws ApiException : expected error.
     */
    @Test(expected = ApiException.class)
    public void resolveExpressionTest_nullBody() throws ApiException {
        expressionsApi.resolveExpression(null, NOT_NULL_RETINA, 0.5);
    }
    
    /**
     * {@link ExpressionsApi#resolveExpression(String, String, Double)} execution test.
     * 
     * @throws ApiException : should never be thrown.
     */
    @Test
    public void resolveExpressionTest() throws ApiException {
        prepareApiPostMethod(EXPRESSION_VALID_JSON, VALID_FINGERPRINT_JSON, apiInvoker);
        Fingerprint fingerprint = expressionsApi.resolveExpression(EXPRESSION_VALID_JSON, NOT_NULL_RETINA, 0.5);
        assertNotNull(fingerprint);
        assertNotNull(fingerprint.getPositions());
        verifyApiPostMethod(EXPRESSION_VALID_JSON, apiInvoker);
    }
    
    /**
     * {@link ExpressionsApi#getContextsForBulkExpression(String, Boolean, String, Integer, Integer, Double)} failure test.
     * 
     * @throws ApiException : expected error.
     */
    @Test(expected = ApiException.class)
    public void getContextsForBulkExpressionTest_nullRetinaName() throws ApiException {
        expressionsApi.getContextsForBulkExpression(BULK_EXPRESSION_VALID_JSON, false, null, 0, 5, 0.5);
    }
    
    /**
     * {@link ExpressionsApi#getContextsForBulkExpression(String, Boolean, String, Integer, Integer, Double)} failure test.
     * 
     * @throws ApiException : expected error.
     */
    @Test(expected = ApiException.class)
    public void getContextsForBulkExpressionTest_nullBody() throws ApiException {
        expressionsApi.getContextsForBulkExpression(null, false, NOT_NULL_RETINA, 0, 5, 0.5);
    }
    
    /**
     * {@link ExpressionsApi#getContextsForBulkExpression(String, Boolean, String, Integer, Integer, Double)} execution test.
     * 
     * @throws ApiException : should never be thrown.
     */
    @Test
    public void getContextsForBulkExpressionTest() throws ApiException {
        prepareApiPostMethod(BULK_EXPRESSION_VALID_JSON, VALID_LIST_OF_CONTEXTS_JSON, apiInvoker);
        List<List<Context>> listOfContexts =
                expressionsApi.getContextsForBulkExpression(BULK_EXPRESSION_VALID_JSON, false, NOT_NULL_RETINA, 0, 5,
                        0.5);
        assertNotNull(listOfContexts);
        for (List<Context> contexts : listOfContexts) {
            for (Context context : contexts) {
                assertNotNull(context.getContextLabel());
                assertNotNull(context.getContextId());
                assertNotNull(context.getFingerprint());
            }
        }
        verifyApiPostMethod(BULK_EXPRESSION_VALID_JSON, apiInvoker);
    }
    
    /**
     * {@link ExpressionsApi#getContextsForExpression(String, Boolean, String, Integer, Integer, Double)} failure test.
     * 
     * @throws ApiException : expected error.
     */
    @Test(expected = ApiException.class)
    public void getContextsForExpressionTest_nullRetinaName() throws ApiException {
        expressionsApi.getContextsForExpression(EXPRESSION_VALID_JSON, false, null, 0, 5, 0.5);
    }
    
    /**
     * {@link ExpressionsApi#getContextsForExpression(String, Boolean, String, Integer, Integer, Double)} failure test.
     * 
     * @throws ApiException : expected error.
     */
    @Test(expected = ApiException.class)
    public void getContextsForExpressionTest_nullBody() throws ApiException {
        expressionsApi.getContextsForExpression(null, false, NOT_NULL_RETINA, 0, 5, 0.5);
    }
    
    
    /**
     * {@link ExpressionsApi#getContextsForExpression(String, Boolean, String, Integer, Integer, Double)} execution test.
     * 
     * @throws ApiException : should never be thrown.
     */
    @Test
    public void getContextsForExpressionTest() throws ApiException {
        prepareApiPostMethod(EXPRESSION_VALID_JSON, VALID_CONTEXTS_JSON, apiInvoker);
        List<Context> contexts =
                expressionsApi.getContextsForExpression(EXPRESSION_VALID_JSON, false, NOT_NULL_RETINA, 0, 5, 0.5);
        assertNotNull(contexts);
        for (Context context : contexts) {
            assertNotNull(context.getContextLabel());
            assertNotNull(context.getContextId());
            assertNotNull(context.getFingerprint());
        }
        verifyApiPostMethod(EXPRESSION_VALID_JSON, apiInvoker);
    }
    
    
    /**
     * {@link ExpressionsApi#getSimilarTermsForBulkExpressionContext(String, Integer, String, Boolean, String, Integer, Integer, Double)} failure test.
     * 
     * @throws ApiException : expected error.
     */
    @Test(expected = ApiException.class)
    public void getSimilarTermsForBulkExpressionContextTest_nullRetinaName() throws ApiException {
        expressionsApi.getSimilarTermsForBulkExpressionContext(BULK_EXPRESSION_VALID_JSON, 0, "NOUN", false, null, 0,
                5, 0.5);
    }
    
    /**
     * {@link ExpressionsApi#getSimilarTermsForBulkExpressionContext(String, Integer, String, Boolean, String, Integer, Integer, Double)} failure test.
     * 
     * @throws ApiException : expected error.
     */
    @Test(expected = ApiException.class)
    public void getSimilarTermsForBulkExpressionContextTest_nullBody() throws ApiException {
        expressionsApi.getSimilarTermsForBulkExpressionContext(null, 0, "NOUN", false, NOT_NULL_RETINA, 0, 5, 0.5);
    }
    
    /**
     * {@link ExpressionsApi#getSimilarTermsForBulkExpressionContext(String, Integer, String, Boolean, String, Integer, Integer, Double)} execution test.
     * 
     * @throws ApiException : should never be thrown.
     */
    @Test
    public void getSimilarTermsForBulkExpressionContextTest() throws ApiException {
        prepareApiPostMethod(BULK_EXPRESSION_VALID_JSON, VALID_LIST_OF_TERMS_JSON, apiInvoker);
        List<List<Term>> listOfTerms =
                expressionsApi.getSimilarTermsForBulkExpressionContext(BULK_EXPRESSION_VALID_JSON, 0, "NOUN", false,
                        NOT_NULL_RETINA, 0, 5, 0.5);
        for (List<Term> terms : listOfTerms) {
            for (Term term : terms) {
                assertNotNull(term.getTerm());
                assertNotNull(term.getDf());
            }
        }
        verifyApiPostMethod(BULK_EXPRESSION_VALID_JSON, apiInvoker);
    }
    
    
    /**
     * {@link ExpressionsApi#getSimilarTermsForExpressionContext(String, Integer, String, Boolean, String, Integer, Integer, Double)} failure test.
     * 
     * @throws ApiException : expected error.
     */
    @Test(expected = ApiException.class)
    public void getSimilarTermsForExpressionContextTest_nullRetinaName() throws ApiException {
        expressionsApi.getSimilarTermsForExpressionContext(EXPRESSION_VALID_JSON, 0, "NOUN", false, null, 0, 5, 0.5);
    }
    
    /**
     * {@link ExpressionsApi#getSimilarTermsForExpressionContext(String, Integer, String, Boolean, String, Integer, Integer, Double)} failure test.
     * 
     * @throws ApiException : expected error.
     */
    @Test(expected = ApiException.class)
    public void getSimilarTermsForExpressionContextTest_nullBody() throws ApiException {
        expressionsApi.getSimilarTermsForExpressionContext(null, 0, "NOUN", false, NOT_NULL_RETINA, 0, 5, 0.5);
    }
    
    /**
     * {@link ExpressionsApi#getSimilarTermsForExpressionContext(String, Integer, String, Boolean, String, Integer, Integer, Double)} execution test.
     * 
     * @throws ApiException : should never be thrown.
     */
    @Test
    public void getSimilarTermsForExpressionContextTest() throws ApiException {
        prepareApiPostMethod(EXPRESSION_VALID_JSON, VALID_TERMS_JSON, apiInvoker);
        List<Term> terms =
                expressionsApi.getSimilarTermsForExpressionContext(EXPRESSION_VALID_JSON, 0, "NOUN", false,
                        NOT_NULL_RETINA, 0, 5, 0.5);
        for (Term term : terms) {
            assertNotNull(term.getTerm());
            assertNotNull(term.getDf());
        }
        verifyApiPostMethod(EXPRESSION_VALID_JSON, apiInvoker);
    }
    
    
}
