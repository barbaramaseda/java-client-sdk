/*******************************************************************************
 * Copyright (c) cortical.io GmbH. All rights reserved.
 *  
 * This software is confidential and proprietary information.
 * You shall use it only in accordance with the terms of the
 * license agreement you entered into with cortical.io GmbH.
 ******************************************************************************/
package io.cortical.retina.core;

import static io.cortical.retina.service.RestServiceConstants.NULL_API_KEY_MSG;
import static io.cortical.retina.service.RestServiceConstants.NULL_BASE_PATH_MSG;
import static io.cortical.retina.service.RestServiceConstants.NULL_TEXT_MSG;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isEmpty;
import io.cortical.retina.model.Fingerprint;
import io.cortical.retina.model.LanguageRest;
import io.cortical.retina.model.Retina;
import io.cortical.retina.model.Text;
import io.cortical.retina.service.ApiException;
import io.cortical.retina.service.TextApi;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.core.JsonProcessingException;



/**
 * 
 * The Retina's Texts API implementation. 
 */
public class Texts extends AbstractRetinas {
    /** Rest Service access for the Texts endpoint */
    private final TextApi api;
    
    Texts(String apiKey, String basePath, String retinaName) {
        super(retinaName);
        
        if (isBlank(apiKey)) {
            throw new IllegalArgumentException(NULL_API_KEY_MSG);
        }
        
        if (isBlank(basePath)) {
            throw new IllegalArgumentException(NULL_BASE_PATH_MSG);
        }
        
        this.api = new TextApi(apiKey);
        this.api.setBasePath(basePath);
    }
    
    Texts(final TextApi api, String retinaName) {
        super(retinaName);
        this.api = api;
    }
    
    /**
     * Retrieve a list of keywords from the input text.
     * 
     * @param text   the input String.
     * @return an array of keywords
     * @throws ApiException     if there are some server or connection issues.
     */
    public List<String> getKeywordsForText(String text) throws ApiException {
        if (text == null || isEmpty(text)) {
            throw new IllegalArgumentException(NULL_TEXT_MSG);
        }
        return this.api.getKeywordsForText(text, retinaName);
    }
    
    /**
     * Retrieve fingerprints for the input text (text is split and for each item a fingerprint is generated).
     * 
     * @param text              text for which a fingerprint is generated.
     * @return fingerprints     generated for the input model.
     * @throws ApiException     if there are server or connection issues.
     */
    public Fingerprint getFingerprintForText(String text) throws ApiException {
        if (text == null || isEmpty(text)) {
            throw new IllegalArgumentException(NULL_TEXT_MSG);
        }
        return this.api.getRepresentationForText(text, retinaName).get(0);
    }
    
    /**
     * Retrieve a list of fingerprints obtained from input texts (one fingerprint per text). 
     * 
     * @param texts             input Strings.
     * @param sparsity          the value used for re-sparsifying the expression. Not used here!
     * 
     * @return a list of fingerprints generated using the input model.
     * @throws JsonProcessingException if it is impossible to generate the request using the input model.
     * @throws ApiException     if there are server or connection issues.
     */
    public List<Fingerprint> getFingerprintsForTexts(List<String> texts, double sparsity) throws JsonProcessingException,
            ApiException {
        return this.api.getRepresentationsForBulkText(toJson(convertToTextModel(texts)), retinaName, sparsity);
    }
    
    /**
     * Returns tokenized input text.
     * 
     * (Retrieves a list of lists of tokens for the input model: a list of sentences containing lists of 
     * tokens).
     *  
     * @param text      input text String. 
     * @param posTags   array of pos tags used in the token generation.
     * @return a list of tokens.
     * @throws ApiException     if there are server or connection issues.
     */
    public List<String> getTokensForText(String text, Set<PosTag> posTags) throws ApiException {
        if (text == null || isEmpty(text)) {
            throw new IllegalArgumentException(NULL_TEXT_MSG);
        }
        return this.api.getTokensForText(text, formatPosTags(posTags), retinaName);
    }
    
    /**
     * Slice the text.
     * 
     * @param text                  a text String to slice.
     * @param pagination            a pagination configuration. 
     * @param includeFingerprint    true if a fingerprint should  be provided with the response items.
     * @return list of slices in the {@link Text} representation.
     * @throws ApiException     if there are server or connection issues.
     */
    public List<Text> getSlicesForText(String text, int startIndex, int maxResults, boolean includeFingerprint) throws ApiException {
        if (text == null || isEmpty(text)) {
            throw new IllegalArgumentException(NULL_TEXT_MSG);
        }
        return this.api.getSlicesForText(text, includeFingerprint, retinaName, startIndex, maxResults);
    }
    
    /**
     * Identifies the language of the text and returns (if possible) a relevant {@link Retina} object.
     * 
     * @param text the input {@link Text}
     * @return a {@link Retina} object.
     * @throws ApiException if there are server or connection issues.
     */
    public LanguageRest getLanguageForText(String text) throws ApiException {
        if (text == null || isEmpty(text)) {
            throw new IllegalArgumentException(NULL_TEXT_MSG);
        }
        return this.api.getLanguage(text);
    }
    
    /**
     * Returns a comma separated label string for the specified tags.
     * @param posTags
     * @return
     */
    private String formatPosTags(Set<PosTag> posTags) {
        if (posTags == null || posTags.size() == 0) {
            return "";
        }
        
        List<PosTag> tags = new ArrayList<>(posTags);
        StringBuilder cluedPosTags = new StringBuilder();
        cluedPosTags.append(tags.get(0).getLabel());
        for (int i = 1; i < tags.size(); i++) {
            cluedPosTags.append(",");
            cluedPosTags.append(tags.get(i).getLabel());
        }
        return cluedPosTags.toString();
    }
}
