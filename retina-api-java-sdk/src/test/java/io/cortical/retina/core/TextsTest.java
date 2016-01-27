/*******************************************************************************
 * Copyright (c) cortical.io GmbH. All rights reserved.
 *  
 * This software is confidential and proprietary information.
 * You shall use it only in accordance with the terms of the
 * license agreement you entered into with cortical.io GmbH.
 ******************************************************************************/
package io.cortical.retina.core;

import static io.cortical.retina.core.ApiTestUtils.NOT_NULL_RETINA;
import static io.cortical.retina.model.TestDataHarness.createFingerprints;
import static io.cortical.retina.model.TestDataHarness.createLanguage;
import static io.cortical.retina.model.TestDataHarness.createStrings;
import static io.cortical.retina.model.TestDataHarness.createTexts;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import io.cortical.retina.model.Fingerprint;
import io.cortical.retina.model.Language;
import io.cortical.retina.model.Text;
import io.cortical.retina.rest.ApiException;
import io.cortical.retina.rest.TextApi;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import com.fasterxml.jackson.core.JsonProcessingException;


/**
 * 
 * {@link TextRetinaApiImpl} test class.
 */
public class TextsTest {
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
     * {@link Texts#getKeywordsForText(String)} test method.
     * 
     * @throws ApiException : should never be thrown
     */
    @Test
    public void testGetKeywordsForText() throws ApiException {
        when(textApi.getKeywordsForText(eq(TEXT), eq(NOT_NULL_RETINA))).thenReturn(asList(KEYWORDS));
        List<String> keywords = texts.getKeywordsForText(TEXT);
        assertEquals("[KEY1, KEY2, KEY3]", keywords.toString());
        verify(textApi, times(1)).getKeywordsForText(eq(TEXT), eq(NOT_NULL_RETINA));
    }
    
    /**
     * {@link Texts#getFingerprintForText(String)} test method.
     * 
     * @throws ApiException : should never be thrown
     */
    @Test
    public void testGetFingerprintForText() throws ApiException {
        int count = 1;
        when(textApi.getRepresentationForText(eq(TEXT), eq(NOT_NULL_RETINA))).thenReturn(
            createFingerprints(count));
        Fingerprint fingerprint = texts.getFingerprintForText(TEXT);
        assertEquals("[181, 514, 612, 785, 861, 895, 1315, 1321, 1485, 1496, 2235, 2466, 2474, "
            + "2489, 2599, 2821, 2906, 2937, 3092, 3210, 3261, 3436, 3596, 4106, "
            + "4492, 4517, 4539, 4596, 4778, 5058, 5186, 5542, 5649, 5864, 5902, "
            + "5982, 6042, 6047, 6200, 6252, 6333, 6843, 6897, 7121, 7148, 7151, "
            + "7205, 7393, 7492, 7541, 7596, 7684, 7744, 7873, 7886, 7972, 8732, "
            + "8981, 8993, 9355, 9503, 9624, 9737, 9762, 10344, 10430, 10545, "
            + "10629, 10904, 11193, 11311, 11402, 11595, 11688, 11920, 12286, "
            + "12308, 12329, 12472, 12486, 12608, 12827, 12920, 13079, 13084, "
            + "13398, 13442, 13532, 13554, 13662, 14183, 14310, 14800, 15062, "
            + "15247, 15434, 15562, 15580, 15769, 15958, 16354]", 
            Arrays.toString(fingerprint.getPositions()));
        verify(textApi, times(1)).getRepresentationForText(eq(TEXT), eq(NOT_NULL_RETINA));
    }
    
    /**
     * {@link Texts#getFingerprintsForTexts(String)} test method.
     * 
     * @throws ApiException     should never be thrown
     * @throws JsonProcessingException 
     */
    @Test
    public void testGetFingerprintsForTexts() throws ApiException, JsonProcessingException {
        double sparsity = 0.02;
        int count = 4;
        when(textApi.getRepresentationsForBulkText(any(String.class), eq(NOT_NULL_RETINA), eq(sparsity))).thenReturn(
            createFingerprints(count, sparsity));
        List<Fingerprint> fingerprints =
            texts.getFingerprintsForTexts(Arrays.asList(TEXT, TEXT_2, TEXT_3), sparsity);
        assertEquals(sparsity, ((double)fingerprints.get(0).getPositions().length)/16384.0d, 0.001);
        assertEquals(count, fingerprints.size());
        verify(textApi, times(1)).getRepresentationsForBulkText(any(String.class), eq(NOT_NULL_RETINA), eq(sparsity));
    }
    
    /**
     * {@link Texts#getTokensForText(String, Set<PosTag>)} test method.
     * 
     * @throws ApiException     should never be thrown
     * @throws JsonProcessingException 
     */
    @Test
    public void testGetTokensForText() throws ApiException, JsonProcessingException {
        Set<PosTag> tags = new LinkedHashSet<>(Arrays.asList(PosTag.DT, PosTag.JJS));
        int count = 4;
        String expectedPosTags =  PosTag.DT.getLabel() + "," +  PosTag.JJS.getLabel();
        when(textApi.getTokensForText(eq(TEXT), eq(expectedPosTags), eq(NOT_NULL_RETINA))).thenReturn(createStrings(count));
        List<String> tokens = texts.getTokensForText(TEXT, tags);
        assertEquals(count, tokens.size());
        verify(textApi, times(1)).getTokensForText(eq(TEXT), eq(expectedPosTags), eq(NOT_NULL_RETINA));
    }
    
    /**
     * {@link Texts#getSlicesForText(String, int, int, boolean)} test method.
     * 
     * @throws ApiException     should never be thrown
     * @throws JsonProcessingException 
     */
    @Test
    public void testGetSlicesForText() throws ApiException, JsonProcessingException {
        int count = 4;
        when(textApi.getSlicesForText(eq(TEXT), eq(false), eq(NOT_NULL_RETINA), eq(0), eq(10))).thenReturn(createTexts(count));
        List<Text> textsList = texts.getSlicesForText(TEXT, 0, 10, false);
        assertEquals(count, textsList.size());
        verify(textApi, times(1)).getSlicesForText(eq(TEXT), eq(false), eq(NOT_NULL_RETINA), eq(0), eq(10));
    }
    
    /**
     * Tests {@link Texts#getLanguageForText(String)}
     * @throws ApiException     should never be thrown
     * @throws JsonProcessingException
     */
    @Test
    public void testGetLanguageForText() throws ApiException, JsonProcessingException {
        String testText = "Identifies the language of the text and returns it";
        when(textApi.getLanguage(eq(testText))).thenReturn(createLanguage());
        Language lr = texts.getLanguageForText(testText);
        assertEquals("English", lr.getLanguage());
        assertEquals("en", lr.getIso_tag());
        assertEquals("http://en.wikipedia.org/wiki/English_language", lr.getWiki_url());
        verify(textApi, times(1)).getLanguage(eq(testText));
    }
}
