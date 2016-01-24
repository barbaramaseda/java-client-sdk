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
import io.cortical.retina.model.LanguageRest;
import io.cortical.retina.model.Text;
import io.cortical.retina.service.ApiException;
import io.cortical.retina.service.TextApi;

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
        assertEquals("[93, 170, 593, 744, 763, 986, 1094, 1142, 1160, 1295, 1400, 1476, 1719, 1727, 1807, "
            + "2003, 2030, 2200, 2246, 2379, 2601, 2625, 2713, 2794, 2807, 3085, 3193, 3241, 3276, 3458, "
            + "3573, 4059, 4093, 4209, 4217, 4292, 4398, 4410, 4440, 4443, 4476, 4730, 4743, 4756, 4884, "
            + "4918, 5328, 5387, 5403, 5696, 6032, 6093, 6460, 6479, 7501, 7519, 7552, 7863, 8065, 8229, "
            + "8430, 8456, 8798, 8935, 9182, 9453, 9733, 9897, 9970, 9975, 10109, 10390, 10759, 10819, "
            + "11212, 11243, 11248, 11502, 11747, 11823, 11976, 12261, 12534, 12594, 12737, 12823, 13164, "
            + "13226, 13499, 13505, 13558, 13713, 13870, 13923, 14157, 14429, 14756, 14913, 15130, 15525, 15775]", 
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
     * @throws ApiException
     * @throws JsonProcessingException
     */
    @Test
    public void testGetLanguageForText() throws ApiException, JsonProcessingException {
        String testText = "Identifies the language of the text and returns it";
        when(textApi.getLanguage(eq(testText))).thenReturn(createLanguage());
        LanguageRest lr = texts.getLanguageForText(testText);
        assertEquals("English", lr.getLanguage());
        assertEquals("en", lr.getIso_tag());
        assertEquals("http://en.wikipedia.org/wiki/English_language", lr.getWiki_url());
        verify(textApi, times(1)).getLanguage(eq(testText));
    }
}
