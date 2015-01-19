/*******************************************************************************
 * Copyright (c) cortical.io GmbH. All rights reserved.
 *  
 * This software is confidential and proprietary information.
 * You shall use it only in accordance with the terms of the
 * license agreement you entered into with cortical.io GmbH.
 ******************************************************************************/
package io.cortical.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.cortical.rest.DefaultValues;
import io.cortical.rest.model.Fingerprint;
import io.cortical.rest.model.Retina;
import io.cortical.rest.model.Text;
import io.cortical.services.api.client.ApiException;
import java.util.List;


/**
 * 
 * The Retina's Texts API.
 */
public interface Texts {
    /**
     * 
     * Retrieve a list of keywords from the input text.
     * 
     * @param model : the input model.
     * @return an array of keywords
     * @throws ApiException : if there are some server or connection issues.
     */
    List<String> getKeywords(String text) throws ApiException;
    
    /**
     * Retrieve fingerprints for the input text (text is split and for each item a fingerprint is generated).
     * 
     * @param model : model for which a fingerprint is generated.
     * @return fingerprints generated for the input model.
     * @throws ApiException : if there are server or connection issues.
     */
    List<Fingerprint> getFingerprints(String text) throws ApiException;
    
    /**
     * Retrieve a list of fingerprints obtained from input texts (one fingerprint per text). 
     * 
     * @param texts : input texts.
     * @param sparsity : the value used for re-sparsifying the expression. Not used here!
     * @return a list of fingerprints generated using the input model.
     * @throws JsonProcessingException if it is impossible to generate the request using the input model.
     * @throws ApiException : if there are server or connection issues.
     */
    List<Fingerprint> getFingerprintBulk(Double sparsity, Text... texts) throws JsonProcessingException,
            ApiException;
    /**
     * Slice the text.
     * 
     * @param text : a text to slice.
     * @param pagination : a pagination configuration. 
     * @param includeFingerprint : true if a fingerprint should  be provided with the response items.
     * @return list of slices in the {@link Text} representation.
     * @throws ApiException : if there are server or connection issues.
     */
    List<Text> getSlices(String text, Pagination pagination, Boolean includeFingerprint) throws ApiException;
    
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
    List<Text> getSlices(String text, Boolean includeFingerprint) throws ApiException;
    
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
    List<Text> getSlices(String text) throws ApiException;
    
    /**
     * Retrieve a list of lists of tokens for the input model: a list of sentences containing lists of 
     * tokens. 
     * @param text : input text. 
     * @param posTags : array of pos tags used in the token generation.
     * @return : a list of tokens.
     * @throws ApiException : if there are server or connection issues.
     */
    List<String> getTokens(String text, PosTag[] posTags) throws ApiException;
    
    /**
     * Identifies the language of the text and returns (if possible) a relevant {@link Retina} object.
     * 
     * @param text the input text
     * @return a {@link Retina} object.
     * @throws ApiException : if there are server or connection issues.
     */
    Retina identifyRetinaByText(String text) throws ApiException; 
}
