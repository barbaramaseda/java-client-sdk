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
import static io.cortical.retina.service.RestServiceConstants.NULL_TERM_MSG;
import static org.apache.commons.lang3.StringUtils.isBlank;
import io.cortical.retina.model.Context;
import io.cortical.retina.model.Term;
import io.cortical.retina.service.ApiException;
import io.cortical.retina.service.TermsApi;

import java.util.List;



/**
 * 
 * The Retina's Terms API implementation. 
 */
public class Terms extends AbstractRetinas {
    /** Rest Service access for the Terms endpoint */
    private final TermsApi api;
    
    Terms(String apiKey, String basePath, String retinaName) {
        super(retinaName);
        
        if (isBlank(apiKey)) {
            throw new IllegalArgumentException(NULL_API_KEY_MSG);
        }
        
        if (isBlank(basePath)) {
            throw new IllegalArgumentException(NULL_BASE_PATH_MSG);
        }
        
        this.api = new TermsApi(apiKey);
        this.api.setBasePath(basePath);
    }
    
    Terms(TermsApi api, String retinaName) {
        super(retinaName);
        this.api = api;
    }
    
    /**
     * Retrieve a term with meta-data for an exact match, or a list of potential retina terms. 
     * 
     * @param term                  the {@link Term} for which to retrieve a term or a list of potential terms.
     * @param startIndex            the index marking the beginning of a page of responses
     * @param maxResults            the number of results to return
     * @param includeFingerprint    true if the fingerprint should be provided in the response.
     * @return term with meta-data of potential terms.
     * @throws ApiException if there are server or connection issues.
     */
    public List<Term> getTerms(String term, int startIndex, int maxResults, boolean includeFingerprint) throws ApiException {
        return api.getTerm(term, includeFingerprint, retinaName, startIndex, maxResults);
    }
    
    /**
     * Retrieve contexts for the input term.
     * 
     * @param term                  the input term.
     * @param startIndex            the response item's start index.
     * @param maxResults            the number of results to return
     * @param includeFingerprint    true if the fingerprint should be provided in the response.
     * @return List of contexts for the input term. 
     * @throws ApiException     if there are server or connection issues.
     */
    public List<Context> getContextsForTerm(String term, int startIndex, int maxResults, boolean includeFingerprint)
            throws ApiException {
        
        validateTerm(term);
        
        return api.getContextsForTerm(term, includeFingerprint, retinaName, startIndex, maxResults);
    }
    
    /**
     * Retrieve all similar terms for the input.
     * <br>If any context is specified, only the similar terms related to this context are returned.
     * 
     * <ul>
     * <li> No input context: returns all similar terms without context filtering.
     * <li> 0..N-1 : returns all similar terms for the Nth context.
     * </ul>
     * 
     * <br>Uses pagination 
     * 
     * @param term                  the input term
     * @param contextId             the context id
     * @param posType               the posType used for filtering
     * @param startIndex            the response item's start index.
     * @param maxResults            the number of results to return
     * @param includeFingerprint    true if the fingerprint should be provided in the response.
     * @return A list of similar terms.
     * @throws ApiException : if there are server or connection issues.
     */
    public List<Term> getSimilarTermsForTerm(String term, int contextId, PosType posType, int startIndex,
        int maxResults, boolean includeFingerprint) throws ApiException {
        
        validateTerm(term);
        
        String posTypeName = null;
        if (posType != null && posType != PosType.ANY) { // Allow PosType.ANY to specify null name
            posTypeName = posType.name();
        }
        
        return api.getSimilarTerms(term, contextId, posTypeName, includeFingerprint, retinaName,
            startIndex, maxResults);
    }
    
    private void validateTerm(String term) {
        if (term == null || term.trim().length() == 0) {
            throw new IllegalArgumentException(NULL_TERM_MSG);
        }
    }
}
