/*******************************************************************************
 * Copyright (c) cortical.io GmbH. All rights reserved.
 *  
 * This software is confidential and proprietary information.
 * You shall use it only in accordance with the terms of the
 * license agreement you entered into with cortical.io GmbH.
 ******************************************************************************/
package io.cortical.services;

import io.cortical.rest.DefaultValues;
import io.cortical.rest.model.Context;
import io.cortical.rest.model.Term;
import io.cortical.services.api.client.ApiException;
import java.util.List;


/**
 * 
 * The Retina's Term API.
 */
public interface Terms {
    /**
     * Retrieve contexts for the input term.
     * 
     * @param term : the input term.
     * @param pagination : the response's items pagination mechanism configuration.
     * @param includeFingerprint : true if the fingerprint should be provided in the response.
     * @return List of contexts for the input term. 
     * @throws ApiException : if there are server or connection issues.
     */
    List<Context> getContexts(String term, Pagination pagination, Boolean includeFingerprint) throws ApiException;

    
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
    List<Context> getContexts(String term, Boolean includeFingerprint) throws ApiException;
    
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
    List<Context> getContexts(String term) throws ApiException;
    
    
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
    List<Term> getSimilarTerms(String term, Integer contextId, PosType posType, Pagination pegination, Boolean includeFingerprint) throws ApiException;
    
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
     * @param includeFingerprint : true if the fingerprint should be provided in the response.
     * @return A list of similar terms.
     * @throws ApiException : if there are server or connection issues.
     */
    List<Term> getSimilarTerms(String term, Integer contextId, PosType posType, Boolean includeFingerprint) throws ApiException;
    
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
    List<Term> getSimilarTerms(String term, Integer contextId, PosType posType) throws ApiException;
    
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
    List<Term> getSimilarTerms(String term) throws ApiException;
    
    /**
     * Retrieve a term with meta-data for an exact match, or a list of potential retina terms. 
     * 
     * @param term : the term for which to retrieve a term or a list of potential terms.
     * @param pagination : the response's items pagination mechanism configuration.
     * @param includeFingerprint : true if the fingerprint should be provided in the response.
     * @return term with meta-data of potential terms.
     * @throws ApiException : if there are server or connection issues.
     */
    List<Term> getTerm(String term, Pagination pagination, Boolean includeFingerprint) throws ApiException;
    
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
    List<Term> getTerm(String term, Boolean includeFingerprint) throws ApiException;
    
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
    List<Term> getTerm(String term) throws ApiException;
    
    /**
     * Return all available {@link Term}s in the retina.  
     * 
     * @param pagination : the response items pagination mechanism configuration.
     * @return list of all available {@link Term}s with pagination.
     * @throws ApiException : if there are server or connection issues.
     */
    List<Term> getAllTerms(Pagination pagination) throws ApiException;
    /**
     * Return all available {@link Term}s in the retina.  
     * 
     * @param pagination : the response's items pagination mechanism configuration.
     * @param includeFingerprint : true if the fingerprint should be provided in the response.
     * @return list of all available {@link Term} with pagination.
     * @throws ApiException : if there are server or connection issues.
     */
    List<Term> getAllTerms(Pagination pagination, Boolean includeFingerprint) throws ApiException;
}
