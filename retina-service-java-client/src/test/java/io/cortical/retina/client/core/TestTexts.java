/*******************************************************************************
 * Copyright (c) cortical.io GmbH. All rights reserved.
 *  
 * This software is confidential and proprietary information.
 * You shall use it only in accordance with the terms of the
 * license agreement you entered into with cortical.io GmbH.
 ******************************************************************************/
package io.cortical.retina.client.core;

import static io.cortical.retina.model.TestDataHarness.createFingerprints;
import static io.cortical.retina.model.TestDataHarness.createStrings;
import static io.cortical.retina.model.TestDataHarness.createTexts;
import static io.cortical.retina.client.core.ApiTestUtils.NOT_NULL_RETINA;
import io.cortical.retina.model.Fingerprint;
import io.cortical.retina.model.Text;
import io.cortical.retina.client.core.Pagination;
import io.cortical.retina.client.core.PosTag;
import io.cortical.retina.client.core.Texts;
import io.cortical.retina.service.ApiException;
import io.cortical.retina.service.TextApi;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;


/**
 * 
 * {@link TextRetinaApiImpl} test class.
 */
public class TestTexts {
    /**
     * 
     */
    private static final String[] KEYWORDS = { "KEY1", "KEY2", "KEY3" };
    private static final String TEXT = "test text";
    private static final String TEXT_2 = "test text 2";
    private static final String TEXT_3 = "test text 3";
    /**
     * 
     */
    @Mock
    private TextApi textApi;
    private Texts texts;
    
    /**
     * initialization.
     */
    @Before
    public void before() {
        initMocks(this);
        texts = new Texts(textApi, NOT_NULL_RETINA);
    }
    
    /**
     * {@link TextRetinaApiImpl#getKeywords(String)} test method.
     * 
     * @throws ApiException : should never be thrown
     */
    @Test
    public void getKeywordsForTextTest() throws ApiException {
        when(textApi.getKeywordsForText(eq(TEXT), eq(NOT_NULL_RETINA))).thenReturn(asList(KEYWORDS));
        texts.getKeywords(TEXT);
        verify(textApi, times(1)).getKeywordsForText(eq(TEXT), eq(NOT_NULL_RETINA));
    }
    
    /**
     * {@link TextRetinaApiImpl#getFingerprints(String)} test method.
     * 
     * @throws ApiException : should never be thrown
     */
    @Test
    public void getRepresentationForTextTest() throws ApiException {
        int count = 4;
        when(textApi.getRepresentationForText(eq(TEXT), eq(NOT_NULL_RETINA))).thenReturn(
                createFingerprints(count));
        List<Fingerprint> fingerprints = texts.getFingerprints(TEXT);
        assertEquals(count, fingerprints.size());
        verify(textApi, times(1)).getRepresentationForText(eq(TEXT), eq(NOT_NULL_RETINA));
    }
    
    /**
     * {@link TextRetinaApiImpl#getFingerprints(String)} test method.
     * 
     * @throws ApiException : should never be thrown
     * @throws JsonProcessingException 
     */
    @Test
    public void getRepresentationsForBulkTextTest() throws ApiException, JsonProcessingException {
        double sparsity = 0.5;
        int count = 4;
        when(textApi.getRepresentationsForBulkText(any(String.class), eq(NOT_NULL_RETINA), eq(sparsity))).thenReturn(
                createFingerprints(count));
        List<Fingerprint> fingerprints =
                texts.getFingerprintBulk(sparsity, new Text(TEXT), new Text(TEXT_2), new Text(
                        TEXT_3));
        assertEquals(count, fingerprints.size());
        verify(textApi, times(1)).getRepresentationsForBulkText(any(String.class), eq(NOT_NULL_RETINA), eq(sparsity));
    }
    
    /**
     * {@link TextRetinaApiImpl#getSlices(String)} test method.
     * 
     * @throws ApiException : should never be thrown
     * @throws JsonProcessingException 
     */
    @Test
    public void getSlicesForTextTest() throws ApiException, JsonProcessingException {
        int count = 4;
        when(textApi.getSlicesForText(eq(TEXT), isNull(Boolean.class), eq(NOT_NULL_RETINA), isNull(Integer.class), isNull(Integer.class))).thenReturn(createTexts(count));
        List<Text> textsList = texts.getSlices(TEXT);
        assertEquals(count, textsList.size());
        verify(textApi, times(1)).getSlicesForText(eq(TEXT), isNull(Boolean.class), eq(NOT_NULL_RETINA), isNull(Integer.class), isNull(Integer.class));
    }
    
    /**
     * {@link TextRetinaApiImpl#getSlices(String, Boolean)} test method.
     * 
     * @throws ApiException : should never be thrown
     * @throws JsonProcessingException 
     */
    @Test
    public void getSlicesForTextTest_withFingerprints() throws ApiException, JsonProcessingException {
        int count = 4;
        boolean getFingerprint = false;
        when(textApi.getSlicesForText(eq(TEXT), eq(getFingerprint), eq(NOT_NULL_RETINA), isNull(Integer.class), isNull(Integer.class))).thenReturn(createTexts(count));
        List<Text> textsList = texts.getSlices(TEXT, getFingerprint);
        assertEquals(count, textsList.size());
        verify(textApi, times(1)).getSlicesForText(eq(TEXT), eq(getFingerprint), eq(NOT_NULL_RETINA), isNull(Integer.class), isNull(Integer.class));
    }
    
    /**
     * {@link TextRetinaApiImpl#getSlices(String, Pagination, Boolean)} test method.
     * 
     * @throws ApiException : should never be thrown
     * @throws JsonProcessingException 
     */
    @Test
    public void getSlicesForTextTest_withFingerprintsAndPegenation() throws ApiException, JsonProcessingException {
        int count = 4;
        boolean getFingerprint = false;
        int startIndex = 10;
        int maxResults = 10;
        Pagination pagination = new Pagination(startIndex, maxResults);
        when(textApi.getSlicesForText(eq(TEXT), eq(getFingerprint), eq(NOT_NULL_RETINA), eq(startIndex), eq(maxResults))).thenReturn(createTexts(count));
        List<Text> textsList = texts.getSlices(TEXT, pagination, getFingerprint);
        assertEquals(count, textsList.size());
        verify(textApi, times(1)).getSlicesForText(eq(TEXT), eq(getFingerprint), eq(NOT_NULL_RETINA), eq(startIndex), eq(maxResults));
    }
    
    /**
     * {@link TextRetinaApiImpl#getTokens(String, PosTag[])} test method.
     * 
     * @throws ApiException : should never be thrown
     * @throws JsonProcessingException 
     */
    @Test
    public void getTokensForTextTest() throws ApiException, JsonProcessingException {
        PosTag[] tags = {PosTag.DT, PosTag.JJS};
        int count = 4;
        String expectedPosTags =  PosTag.DT.getLabel() + "," +  PosTag.JJS.getLabel();
        when(textApi.getTokensForText(eq(TEXT), eq(expectedPosTags), eq(NOT_NULL_RETINA))).thenReturn(createStrings(count));
        List<String> tokens = texts.getTokens(TEXT, tags);
        assertEquals(count, tokens.size());
        verify(textApi, times(1)).getTokensForText(eq(TEXT), eq(expectedPosTags), eq(NOT_NULL_RETINA));
    }
}
