/*******************************************************************************
 * Copyright (c) cortical.io GmbH. All rights reserved.
 *  
 * This software is confidential and proprietary information.
 * You shall use it only in accordance with the terms of the
 * license agreement you entered into with cortical.io GmbH.
 ******************************************************************************/
package io.cortical.retina.service;

import static io.cortical.retina.core.ApiTestUtils.NOT_NULL_API_KEY;
import static io.cortical.retina.core.ApiTestUtils.NOT_NULL_BASE_PATH;
import static io.cortical.retina.core.ApiTestUtils.NOT_NULL_RETINA;
import static io.cortical.retina.core.ApiTestUtils.prepareApiPostMethod;
import static io.cortical.retina.core.ApiTestUtils.setApiInvoker;
import static io.cortical.retina.core.ApiTestUtils.verifyApiPostMethod;
import io.cortical.retina.model.Metric;
import io.cortical.retina.service.ApiException;
import io.cortical.retina.service.ApiInvoker;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.assertNotNull;
import static org.mockito.MockitoAnnotations.initMocks;


/**
 * The {@link CompareApi} class test.
 * 
 */
public class TestCompareApi {
    /**
     * 
     */
    private static final String TWO_TERMS_JSON = "[ { \"term\" : \"apple\" }, { \"term\" : \"banana\" } ]";
    private static final String METRIC_JSON =
            "{\"cosineSimilarity\":0.18597560975609753,\"euclideanDistance\":0.8140243902439024,\"jaccardDistance\":0.8974789915966387,\"overlappingAll\":61,\"overlappingLeftRight\":0.18597560975609756,\"overlappingRightLeft\":0.18597560975609756,\"sizeLeft\":328,\"sizeRight\":328,\"weightedScoring\":10.418091300234659}";
    /**
     * 
     */
    @Mock
    private ApiInvoker apiInvoker;
    private CompareApi compareApi;
    
    /**
     * set up.
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        initMocks(this);
        compareApi = new CompareApi(NOT_NULL_API_KEY);
        compareApi.setBasePath(NOT_NULL_BASE_PATH);
        setApiInvoker(apiInvoker, compareApi);
    }
    
    /**
     * {@link CompareApi#compare(String, String)} method test.
     * @throws ApiException 
     */
    @Test
    public void testCompare() throws ApiException {
        prepareApiPostMethod(TWO_TERMS_JSON, METRIC_JSON, apiInvoker);
        Metric resultMetric = compareApi.compare(TWO_TERMS_JSON, NOT_NULL_RETINA);
        assertNotNull(resultMetric);
        assertNotNull(resultMetric.getCosineSimilarity());
        assertNotNull(resultMetric.getEuclideanDistance());
        assertNotNull(resultMetric.getJaccardDistance());
        assertNotNull(resultMetric.getOverlappingAll());
        assertNotNull(resultMetric.getOverlappingLeftRight());
        assertNotNull(resultMetric.getOverlappingRightLeft());
        assertNotNull(resultMetric.getSizeLeft());
        assertNotNull(resultMetric.getSizeRight());
        verifyApiPostMethod(TWO_TERMS_JSON, apiInvoker);
    }
    
    /**
     *{@link CompareApi#compare(String, String)} method test. (wrong parameters.)
     * 
     * @throws ApiException : expected.
     */
    @Test(expected = ApiException.class)
    public void testCompare_nullRetinaName() throws ApiException {
        compareApi.compare(TWO_TERMS_JSON, null);
    }
    
    /**
     * {@link CompareApi#compare(String, String)} method test. (wrong parameters.)
     * 
     * @throws ApiException : expected.
     */
    @Test(expected = ApiException.class)
    public void testCompare_nullBody() throws ApiException {
        compareApi.compare(null, NOT_NULL_RETINA);
    }
}
