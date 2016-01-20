/*******************************************************************************
 * Copyright (c) cortical.io GmbH. All rights reserved.
 *  
 * This software is confidential and proprietary information.
 * You shall use it only in accordance with the terms of the
 * license agreement you entered into with cortical.io GmbH.
 ******************************************************************************/
package io.cortical.retina.client.core;

import com.fasterxml.jackson.core.JsonProcessingException;

import io.cortical.retina.rest.DefaultValues;
import io.cortical.retina.model.Fingerprint;
import io.cortical.retina.model.Retina;
import io.cortical.retina.model.Text;
import io.cortical.retina.service.ApiException;
import io.cortical.retina.service.TextApi;

import java.util.List;

import org.apache.commons.logging.Log;

import static io.cortical.retina.rest.RestServiceConstants.NULL_API_KEY_MSG;
import static io.cortical.retina.rest.RestServiceConstants.NULL_BASE_PATH_MSG;
import static io.cortical.retina.rest.RestServiceConstants.NULL_TEXT_MSG;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isEmpty;
import static org.apache.commons.logging.LogFactory.getLog;



/**
 * 
 * The Retina's Texts API implementation. 
 */
class Texts extends AbstractRetinas {
    /**
     * 
     */
    private static final Log LOG = getLog(Texts.class);
    /**
     * 
     */
    private final TextApi api;
    
    Texts(String apiKey, String basePath, String retinaName) {
        super(retinaName);
        
        if (isBlank(apiKey)) {
            throw new IllegalArgumentException(NULL_API_KEY_MSG);
        }
        
        if (isBlank(basePath)) {
            throw new IllegalArgumentException(NULL_BASE_PATH_MSG);
        }
        LOG.info("Initialize Text Retina Api with retina: " + retinaName);
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
     * @param model : the input model.
     * @return an array of keywords
     * @throws ApiException : if there are some server or connection issues.
     */
    public List<String> getKeywords(String text) throws ApiException {
        if (isEmpty(text)) {
            throw new IllegalArgumentException(NULL_TEXT_MSG);
        }
        LOG.debug("Retrieve keywords for the text: " + text);
        return this.api.getKeywordsForText(text, retinaName);
    }
    
    /**
     * Retrieve fingerprints for the input text (text is split and for each item a fingerprint is generated).
     * 
     * @param model : model for which a fingerprint is generated.
     * @return fingerprints generated for the input model.
     * @throws ApiException : if there are server or connection issues.
     */
    public List<Fingerprint> getFingerprints(String text) throws ApiException {
        if (isEmpty(text)) {
            throw new IllegalArgumentException(NULL_TEXT_MSG);
        }
        LOG.debug("Retrieve representation for the text: " + text);
        return this.api.getRepresentationForText(text, retinaName);
    }
    
    /**
     * Retrieve a list of fingerprints obtained from input texts (one fingerprint per text). 
     * 
     * @param texts : input texts.
     * @param sparsity : the value used for re-sparsifying the expression. Not used here!
     * @return a list of fingerprints generated using the input model.
     * @throws JsonProcessingException if it is impossible to generate the request using the input model.
     * @throws ApiException : if there are server or connection issues.
     */
    public List<Fingerprint> getFingerprintBulk(Double sparsity, Text... texts) throws JsonProcessingException,
            ApiException {
        validateRequiredModels(texts);
        LOG.debug("Retrieve representation for the bulk Text: " + toJson(texts) + "  sparsity: " + sparsity);
        return this.api.getRepresentationsForBulkText(toJson(texts), retinaName, sparsity);
    }
    
    /**
     * Slice the text.
     * 
     * @param text : a text to slice.
     * @param pagination : a pagination configuration. 
     * @param includeFingerprint : true if a fingerprint should  be provided with the response items.
     * @return list of slices in the {@link Text} representation.
     * @throws ApiException : if there are server or connection issues.
     */
    public List<Text> getSlices(String text, Pagination pagination, Boolean includeFingerprint) throws ApiException {
        if (isEmpty(text)) {
            throw new IllegalArgumentException(NULL_TEXT_MSG);
        }
        pagination = initPagination(pagination);
        
        LOG.debug("Retrieve slices for the text: " + text + " pagination: " + pagination.toString()
                + "  include fingerprint: " + includeFingerprint);
        return this.api.getSlicesForText(text, includeFingerprint, retinaName, pagination.getStartIndex(),
                pagination.getMaxResults());
    }
    
    /**
     * Retrieve a list of lists of tokens for the input model: a list of sentences containing lists of 
     * tokens. 
     * @param text : input text. 
     * @param posTags : array of pos tags used in the token generation.
     * @return : a list of tokens.
     * @throws ApiException : if there are server or connection issues.
     */
    public List<String> getTokens(String text, PosTag[] posTags) throws ApiException {
        if (isEmpty(text)) {
            throw new IllegalArgumentException(NULL_TEXT_MSG);
        }
        LOG.debug("Retrieve tokens for the text: " + text);
        return this.api.getTokensForText(text, cluePosTags(posTags), retinaName);
    }
    
    private String cluePosTags(PosTag[] posTags) {
        if (posTags.length == 0) {
            return "";
        }
        StringBuilder cluedPosTags = new StringBuilder();
        cluedPosTags.append(posTags[0].getLabel());
        for (int i = 1; i < posTags.length; i++) {
            cluedPosTags.append(",");
            cluedPosTags.append(posTags[i].getLabel());
        }
        return cluedPosTags.toString();
    }
    
    /**
     * Slice the text.
     * 
     * <br/> The default pagination configuration is:
     * <ul>
     * <li>
     * Start from: {@link DefaultValues#DEF_VALUE_START_INDEX}
     * </li>
     * <li>
     * Max count of items in a result: {@link DefaultValues#DEF_VALUE_MAX_ITEMS_COUNT}
     * </li>
     * </ul>
     * 
     * @param text : a text to slice.
     * @param includeFingerprint : true if a fingerprint should  be provided with the response items.
     * @return list of slices in the {@link Text} representation.
     * @throws ApiException : if there are server or connection issues.
     */
    public List<Text> getSlices(String text, Boolean includeFingerprint) throws ApiException {
        return getSlices(text, null, includeFingerprint);
    }
    
    /**
     * Slice the text.
     * 
     * <br/> The default pagination configuration is:
     * <ul>
     * <li>
     * Start from: {@link DefaultValues#DEF_VALUE_START_INDEX}
     * </li>
     * <li>
     * Max count of items in a result: {@link DefaultValues#DEF_VALUE_MAX_ITEMS_COUNT}
     * </li>
     * </ul>
     *  <br/> The ability of a response items to have/provide fingerprint is defined by {@link DefaultValues#DEF_VALUE_PROVIDE_FINGERPRINT}
     * 
     * @param text : a text to slice.
     * @return list of slices in the {@link Text} representation.
     * @throws ApiException : if there are server or connection issues.
     */
    public List<Text> getSlices(String text) throws ApiException {
        return getSlices(text, null);
    }
    
    /**
     * Identifies the language of the text and returns (if possible) a relevant {@link Retina} object.
     * 
     * @param text the input text
     * @return a {@link Retina} object.
     * @throws ApiException : if there are server or connection issues.
     */
    public Retina identifyRetinaByText(String text) throws ApiException {
        if (isEmpty(text)) {
            throw new IllegalArgumentException(NULL_TEXT_MSG);
        }
        return this.api.getLanguage(text);
    }
}