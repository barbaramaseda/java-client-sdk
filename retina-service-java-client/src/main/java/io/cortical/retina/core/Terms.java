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
import io.cortical.retina.service.DefaultValues;
import io.cortical.retina.service.TermsApi;

import java.util.List;



/**
 * 
 * The Retina's Terms API implementation. 
 */
class Terms extends AbstractRetinas {
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
     * Retrieve contexts for the input term.
     * 
     * @param term : the input term.
     * @param pagination : the response's items pagination mechanism configuration.
     * @param includeFingerprint : true if the fingerprint should be provided in the response.
     * @return List of contexts for the input term. 
     * @throws ApiException : if there are server or connection issues.
     */
    public List<Context> getContexts(String term, Pagination pagination, Boolean includeFingerprint)
            throws ApiException {
        pagination = initPagination(pagination);
        validateTerm(term);
        
        return api.getContextsForTerm(term, includeFingerprint, retinaName, pagination.getStartIndex(),
                pagination.getMaxResults());
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
     * @param term : the input term
     * @param contextId : the context id
     * @param posType : the posType used for filtering
     * @param pegination : the response items pagination mechanism configuration.
     * @param includeFingerprint : true if the fingerprint should be provided in the response.
     * @return A list of similar terms.
     * @throws ApiException : if there are server or connection issues.
     */
    public List<Term> getSimilarTerms(String term, Integer contextId, PosType posType, Pagination pagination,
            Boolean includeFingerprint) throws ApiException {
        
        pagination = initPagination(pagination);
        validateTerm(term);
        
        String posTypeName = null;
        if (posType != null) {
            posTypeName = posType.name();
        }
        
        return api.getSimilarTerms(term, contextId, posTypeName, includeFingerprint, retinaName,
                pagination.getStartIndex(), pagination.getMaxResults());
    }
    
    /**
     * Retrieve a term with meta-data for an exact match, or a list of potential retina terms. 
     * 
     * @param term : the term for which to retrieve a term or a list of potential terms.
     * @param pagination : the response's items pagination mechanism configuration.
     * @param includeFingerprint : true if the fingerprint should be provided in the response.
     * @return term with meta-data of potential terms.
     * @throws ApiException : if there are server or connection issues.
     */
    public List<Term> getTerm(String term, Pagination pagination, Boolean includeFingerprint) throws ApiException {
        
        pagination = initPagination(pagination);
        
        return api.getTerm(term, includeFingerprint, retinaName, pagination.getStartIndex(), 
            pagination.getMaxResults());
    }
    
    private void validateTerm(String term) {
        if (term == null || term.trim().length() == 0) {
            throw new IllegalArgumentException(NULL_TERM_MSG);
        }
    }
    
    /**
     * Retrieve contexts for the input term.
     * 
     * <br/> The default pagination configuration is:
     * <ul>
     * <li>
     * Start from: {@link DefaultValues#DEF_VALUE_START_INDEX}
     * </li>
     * <li>
     * Max count of items in a result: {@link DefaultValues#DEF_VALUE_MAX_CONTEXTS_COUNT}
     * </li>
     * </ul>
     *  
     * @param term : the input term
     * @param includeFingerprint : true if the fingerprint should be provided in the response.
     * @return List of contexts for the input term. 
     * @throws ApiException : if there are server or connection issues.
     */
    public List<Context> getContexts(String term, Boolean includeFingerprint) throws ApiException {
        return getContexts(term, null, includeFingerprint);
    }
    
    /**
     * Retrieve contexts for the input term.
     * 
     * <br/> Whether to include a fingerprint in the response is defined by {@link DefaultValues#DEF_VALUE_PROVIDE_FINGERPRINT}
     * <br/> The default pagination configuration is:
     * <ul>
     * <li>
     * Start from: {@link DefaultValues#DEF_VALUE_START_INDEX}
     * </li>
     * <li>
     * Max count of items in a result: {@link DefaultValues#DEF_VALUE_MAX_CONTEXTS_COUNT}
     * </li>
     * </ul>
     * @param term : the input term.
     * @return List of contexts for the input term. 
     * @throws ApiException : if there are server or connection issues.
     */
    public List<Context> getContexts(String term) throws ApiException {
        return getContexts(term, null);
    }
    
    /**
     * 
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
     * @param term : the input term
     * @param contextId : the context id
     * @param posType : the posType used for filtering
     * @param pegination : the response items pagination mechanism configuration.
     * @param includeFingerprint : true if the fingerprint should be provided in the response.
     * @return A list of similar terms.
     * @throws ApiException : if there are server or connection issues.
     */
    public List<Term> getSimilarTerms(String term, Integer contextId, PosType posType, Boolean includeFingerprint)
            throws ApiException {
        return getSimilarTerms(term, contextId, posType, null, includeFingerprint);
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
     * 
     * <br/> Whether to include a fingerprint in the response is defined by {@link DefaultValues#DEF_VALUE_PROVIDE_FINGERPRINT}
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
     * @param term : the input term
     * @param contextId : the context id
     * @param posType : the posType used for filtering
     * @return A list of similar terms.
     * @throws ApiException : if there are server or connection issues.
     */
    public List<Term> getSimilarTerms(String term, Integer contextId, PosType posType) throws ApiException {
        return getSimilarTerms(term, contextId, posType, null);
    }
    
    /**
     * Retrieve a term with meta-data for an exact match, or a list of potential retina terms. 
     * 
     * <br/> Whether to include a fingerprint in the response is defined by {@link DefaultValues#DEF_VALUE_PROVIDE_FINGERPRINT}
     *   
     * @param term : the term for which to retrieve a {@link Term} object or a list of potential terms.
     * @param includeFingerprint : true if the fingerprint should be provided in the response.
     * @return term with meta-data of potential terms.
     * @throws ApiException : if there are server or connection issues.
     */
    public List<Term> getTerm(String term, Boolean includeFingerprint) throws ApiException {
        return getTerm(term, null, includeFingerprint);
    }
    
    /**
     * Retrieve a term with meta-data for an exact match, or a list of potential retina terms. 
     * <br/> Whether to include a fingerprint in the response is defined by {@link DefaultValues#DEF_VALUE_PROVIDE_FINGERPRINT}
     * <br/> The default pagination configuration is:
     * <ul>
     * <li>
     * Start from: {@link DefaultValues#DEF_VALUE_START_INDEX}
     * </li>
     * <li>
     * Max count of items in a result: {@link DefaultValues#DEF_VALUE_MAX_ITEMS_COUNT}
     * </li>
     * </ul>
     * @param term : the term for which to retrieve a {@link Term} or a list of potential terms.
     * @return term with meta-data of potential terms.
     * @throws ApiException : if there are server or connection issues.
     */
    public List<Term> getTerm(String term) throws ApiException {
        return getTerm(term, null);
    }
    
    /**
     * Return all available {@link Term}s in the retina.  
     * 
     * @param pagination : the response items pagination mechanism configuration.
     * @return list of all available {@link Term}s with pagination.
     * @throws ApiException : if there are server or connection issues.
     */
    public List<Term> getAllTerms(Pagination pagination) throws ApiException {
        return getTerm(null, pagination, null);
    }
    
    /**
     * Return all available {@link Term}s in the retina.  
     * 
     * @param pagination : the response's items pagination mechanism configuration.
     * @param includeFingerprint : true if the fingerprint should be provided in the response.
     * @return list of all available {@link Term} with pagination.
     * @throws ApiException : if there are server or connection issues.
     */
    public List<Term> getAllTerms(Pagination pagination, Boolean includeFingerprint) throws ApiException {
        return getTerm(null, pagination, includeFingerprint);
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
     * 
     * <br/> Whether to include a fingerprint in the response is defined by {@link DefaultValues#DEF_VALUE_PROVIDE_FINGERPRINT}
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
     * @param term : the input term
     * @param contextId : the context id
     * @param posType : the posType used for filtering
     * @return A list of similar terms.
     * @throws ApiException : if there are server or connection issues.
     */
    public List<Term> getSimilarTerms(String term) throws ApiException {
        return getSimilarTerms(term, null, null);
    }
}
