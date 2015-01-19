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
import io.cortical.rest.model.Context;
import io.cortical.rest.model.Fingerprint;
import io.cortical.rest.model.Model;
import io.cortical.rest.model.Term;
import io.cortical.services.api.client.ApiException;
import java.util.List;


/**
 * 
 * The Expression Retina's API.
 */
public interface Expressions {
    /**
     * Retrieve similar terms for each item in the model's array.
     * 
     * @param contextId : the id of the relevant context (or null if all contexts are to be used).
     * @param posType : a part of speech type.
     * @param pagination : a response's items pagination mechanism configuration.
     * @param includeFingerprint : true if a fingerprint field should  be provided for each of the response items.
     * @param sparsity : a value used for re-sparsifying the evaluated expression.
     * @param models : model(s) for which a list of terms is generated. (for each model a list of {@link Term} is generated.) 
     * @return a List containing a List of terms generated for each item in the models.
     * @throws JsonProcessingException if it is impossible to generate the request using the model(s).
     * @throws ApiException : if there are server or connection issues.
     */
    List<List<Term>> getSimilarTermsBulk(Integer contextId, PosType posType, Pagination pagination,
            Boolean includeFingerprint, Double sparsity, Model... models) throws JsonProcessingException, ApiException;
    
    /**
     * Retrieve similar terms for the each item in the model's array.
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
     * 
     * @param contextId : the id of the relevant context (or null if all contexts are to be used).
     * @param posType : a part of speech type.
     * @param includeFingerprint : true if a fingerprint field should  be provided for each of the response items.
     * @param sparsity : a value used for re-sparsifying the evaluated expression.
     * @param models : model(s) for which a list of terms is generated. (for each model a list of {@link Term} is generated.) 
     * @return a List containing a List of terms generated for each item in the models.
     * @throws JsonProcessingException if it is impossible to generate the request using the model(s).
     * @throws ApiException : if there are server or connection issues.
     */
    List<List<Term>> getSimilarTermsBulk(Integer contextId, PosType posType, Boolean includeFingerprint,
            Double sparsity, Model... models) throws JsonProcessingException, ApiException;
    
    /**
     * Retrieve similar terms for each item in the model's array.
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
     * <br/> Whether to include a fingerprint in the response is defined by {@link DefaultValues#DEF_VALUE_PROVIDE_FINGERPRINT}
     * 
     * @param contextId : the id of the relevant context.
     * @param posType : a part of speech type.
     * @param sparsity : a value used for re-sparsifying the evaluated expression.
     * @param models : model(s) for which a list of terms is generated. (for each model a list of {@link Term} is generated.) 
     * @return a List containing a List of terms generated for each item in the models.
     * @throws JsonProcessingException if it is impossible to generate the request using the model(s).
     * @throws ApiException : if there are server or connection issues.
     */
    List<List<Term>> getSimilarTermsBulk(Integer contextId, PosType posType, Double sparsity, Model... models)
            throws JsonProcessingException, ApiException;
    
    /**
     * Retrieve similar terms for each item in the model's array.
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
     * <br/>The default sparsity is {@link DefaultValues#DEF_VALUE_SPARSITY}.
     * 
     * @param contextId : the id of the relevant context.
     * @param posType : a part of speech type.
     * @param includeFingerprint : true if a fingerprint field should  be provided for each of the response items.
     * @param models : model(s) for which a list of terms is generated. (for each model a list of {@link Term} is generated.) 
     * @return a List containing a List of terms generated for each item in the models.
     * @throws JsonProcessingException if it is impossible to generate the request using the model(s).
     * @throws ApiException : if there are server or connection issues.
     */
    List<List<Term>> getSimilarTermsBulk(Integer contextId, PosType posType, Boolean includeFingerprint,
            Model... models) throws JsonProcessingException, ApiException;
    
    /**
     * Retrieve similar terms for each item in the model's array.
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
     * <br/>The default sparsity is {@link DefaultValues#DEF_VALUE_SPARSITY}.
     * 
     * <br/> Whether to include a fingerprint in the response is defined by {@link DefaultValues#DEF_VALUE_PROVIDE_FINGERPRINT}
     * 
     * @param contextId : the id of the relevant context.
     * @param posType : a part of speech type.
     * @param models : model(s) for which a list of terms is generated. (for each model a list of {@link Term} is generated.) 
     * @return a List containing a List of terms generated for each item in the models.
     * @throws JsonProcessingException if it is impossible to generate the request using the model(s).
     * @throws ApiException : if there are server or connection issues.
     */
    List<List<Term>> getSimilarTermsBulk(Integer contextId, PosType posType, Model... models)
            throws JsonProcessingException, ApiException;
    
    /**
     * Retrieve similar terms for each item in the model's array including all contexts.
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
     * <br/>The default sparsity is {@link DefaultValues#DEF_VALUE_SPARSITY}.
     * 
     * <br/> Whether to include a fingerprint in the response is defined by {@link DefaultValues#DEF_VALUE_PROVIDE_FINGERPRINT}
     * 
     * @param posType : a part of speech type.
     * @param models : model(s) for which a list of terms is generated. (for each model a list of {@link Term} is generated.) 
     * @return a List containing a List of terms generated for each item in the models.
     * @throws JsonProcessingException if it is impossible to generate the request using the model(s).
     * @throws ApiException : if there are server or connection issues.
     */
    List<List<Term>> getSimilarTermsBulk(PosType posType, Model... models) throws JsonProcessingException, ApiException;
    
    /**
     * Retrieve similar terms (all parts of speech) for each item in the model's array.
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
     * <br/>The default sparsity is {@link DefaultValues#DEF_VALUE_SPARSITY}.
     * 
     * <br/> Whether to include a fingerprint in the response is defined by {@link DefaultValues#DEF_VALUE_PROVIDE_FINGERPRINT}
     * 
     * @param contextId : the id of the relevant context.
     * @param models : model(s) for which a list of terms is generated. (for each model a list of {@link Term} is generated.) 
     * @return a List containing a List of terms generated for each item in the models.
     * @throws JsonProcessingException if it is impossible to generate the request using the model(s).
     * @throws ApiException : if there are server or connection issues.
     */
    List<List<Term>> getSimilarTermsBulk(Integer contextId, Model... models) throws JsonProcessingException,
            ApiException;
    
    /**
     * Retrieve similar terms for each item in the models array for all parts of speech and all contexts.
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
     * <br/>The default sparsity is {@link DefaultValues#DEF_VALUE_SPARSITY}.
     * 
     * <br/> Whether to include a fingerprint in the response is defined by {@link DefaultValues#DEF_VALUE_PROVIDE_FINGERPRINT}
     * 
     * @param models : model(s) for which a list of terms is generated. (for each model a list of {@link Term} is generated.) 
     * @return a List containing a List of terms generated for each item in the models.
     * @throws JsonProcessingException if it is impossible to generate the request using the model(s).
     * @throws ApiException : if there are server or connection issues.
     */
    List<List<Term>> getSimilarTermsBulk(Model... models) throws JsonProcessingException, ApiException;
    
    /**
     * Retrieve similar terms for each item in the model's array for all contexts.
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
     * <br/>The default sparsity is {@link DefaultValues#DEF_VALUE_SPARSITY}.
     * 
     * <br/> Whether to include a fingerprint in the response is defined by {@link DefaultValues#DEF_VALUE_PROVIDE_FINGERPRINT}
     * 
     * @param posType : a part of speech type.
     * @param jsonModel : model(s) for which a list of terms is generated in the json representation. (for each model a list of {@link Term} is generated.) 
     * @return a List containing a List of terms generated for each item in the models.
     * @throws JsonProcessingException if it is impossible to generate the request using the model(s).
     * @throws ApiException : if there are server or connection issues.
     */
    List<List<Term>> getSimilarTermsBulk(PosType posType, String jsonModel) throws JsonProcessingException,
            ApiException;
    
    /**
     * Retrieve similar terms for each item in the models array for all parts of speech.
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
     * <br/>The default sparsity is {@link DefaultValues#DEF_VALUE_SPARSITY}.
     * 
     * <br/> Whether to include a fingerprint in the response is defined by {@link DefaultValues#DEF_VALUE_PROVIDE_FINGERPRINT}
     * 
     * @param contextId : the id of the relevant context.
     * @param jsonModel : model(s) for which a list of terms is generated in the json representation. (for each model a list of {@link Term} is generated.) 
     * @return a List containing a List of terms generated for each item in the models.
     * @throws JsonProcessingException if it is impossible to generate the request using the model(s).
     * @throws ApiException : if there are server or connection issues.
     */
    List<List<Term>> getSimilarTermsBulk(Integer contextId, String jsonModel) throws JsonProcessingException,
            ApiException;
    
    /**
     * Retrieve similar terms for each item in the model's array for all parts of speech and all contexts.
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
     * <br/>The default sparsity is {@link DefaultValues#DEF_VALUE_SPARSITY}.
     * 
     * <br/> Whether to include a fingerprint in the response is defined by {@link DefaultValues#DEF_VALUE_PROVIDE_FINGERPRINT}
     * 
     * @param jsonModel : model(s) for which a list of terms is generated in the json representation. (for each model a list of {@link Term} is generated.) 
     * @return a List containing a List of terms generated for each item in the models.
     * @throws JsonProcessingException if it is impossible to generate the request using the model(s).
     * @throws ApiException : if there are server or connection issues.
     */
    List<List<Term>> getSimilarTermsBulk(String jsonModel) throws JsonProcessingException, ApiException;
    
    /**
     * Resolves a bulk expression call. 
     * 
     * @param models : model(s) for which the list of fingerprints is generated.
     * @param sparsity : a value used for re-sparsifying the evaluated expression.
     * @return a list of fingerprints generated for each of the input model(s).
     * @throws JsonProcessingException if it is impossible to generate the request using the model(s).
     * @throws ApiException : if there are server or connection issues.
     */
    List<Fingerprint> resolveBulk(Double sparsity, Model... models) throws JsonProcessingException, ApiException;
    
    /**
     * Resolves a bulk expression. 
     * <br/>The default sparsity is {@link DefaultValues#DEF_VALUE_SPARSITY}.
     * @param models : a model(s) for which the list of fingerprints is generated.
     * @return a list of fingerprints generated for each of the input model(s).
     * @throws JsonProcessingException if it is impossible to generate the request using the model(s).
     * @throws ApiException : if there are server or connection issues.
     */
    List<Fingerprint> resolveBulk(Model... models) throws JsonProcessingException, ApiException;
    
    /**
     * Calculate contexts for each models. 
     * 
     * <br>Returns a list of {@link Context} for each of the input expressions in the bulk expression call. The returned
     * Response object will contain a list of lists of Contexts.
     * 
     * @param pagination : the response's items pagination mechanism configuration.
     * @param includeFingerprint : true if a fingerprint field should  be provided for each of the response items.
     * @param sparsity : a value used for re-sparsifying the evaluated expression.
     * @param models : model(s) for which a list of contexts is generated. (for each model a list of {@link Context} is generated.) 
     * @return a list of contexts' lists generated from the input model(s).
     * @throws JsonProcessingException if it is impossible to generate the request using the model(s).
     * @throws ApiException : if there are server or connection issues.
     */
    List<List<Context>> getContextsBulk(Pagination pagination, Boolean includeFingerprint, Double sparsity,
            Model... models) throws JsonProcessingException, ApiException;
    
    /**
     * Calculate contexts for each item in the model's array. 
     * 
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
     * @param includeFingerprint : true if a fingerprint field should  be provided for each of the response items.
     * @param sparsity : a value used for re-sparsifying the evaluated expression.
     * @param models : model(s) for which a list of contexts is generated. (for each model a list of {@link Context} is generated.) 
     * @return a list of contexts' lists generated from passed model(s).
     * @throws JsonProcessingException if it is impossible to generate the request using the model(s).
     * @throws ApiException : if there are server or connection issues.
     */
    List<List<Context>> getContextsBulk(Boolean includeFingerprint, Double sparsity, Model... models)
            throws JsonProcessingException, ApiException;
    
    /**
     * Calculate contexts for each item in the model's array. 
     * 
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
     * <br/> Whether to include a fingerprint in the response is defined by {@link DefaultValues#DEF_VALUE_PROVIDE_FINGERPRINT}
     *  
     * @param sparsity : a value used for re-sparsifying the evaluated expression.
     * @param models : model(s) for which a list of contexts is generated. (for each model a list of {@link Context} is generated.) 
     * @return a list of contexts' lists generated from passed model(s).
     * @throws JsonProcessingException if it is impossible to generate the request using the model(s).
     * @throws ApiException : if there are server or connection issues.
     */
    List<List<Context>> getContextsBulk(Double sparsity, Model... models) throws JsonProcessingException, ApiException;
    
    /**
     * Calculate contexts for each item in the model's array. 
     * 
     * 
     * <br/>The default sparsity is {@link DefaultValues#DEF_VALUE_SPARSITY}.
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
     * @param includeFingerprint : true if a fingerprint field should  be provided for each of the response items.
     * @param models : model(s) for which a list of contexts is generated. (for each model a list of {@link Context} is generated.) 
     * @return a list of contexts' lists generated from passed model(s).
     * @throws JsonProcessingException if it is impossible to generate the request using the model(s).
     * @throws ApiException : if there are server or connection issues.
     */
    List<List<Context>> getContextsBulk(Boolean includeFingerprint, Model... models) throws JsonProcessingException,
            ApiException;
    
    /**
     * Calculate contexts for each item in the model's array. 
     * 
     * <br>Returns a list of {@link Context} for each one of the input expressions in the bulk, so the returned
     * Response object will contain a list of lists of Contexts.
     * 
     * <br/>The default sparsity is {@link DefaultValues#DEF_VALUE_SPARSITY}.
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
     * <br/> Whether to include a fingerprint in the response is defined by {@link DefaultValues#DEF_VALUE_PROVIDE_FINGERPRINT}
     *  
     * @param models : the model(s) for which a list of contexts is generated. (for each model a list of {@link Context} is generated.) 
     * @return a list of contexts' lists generated from passed model(s).
     * @throws JsonProcessingException if it is impossible to generate the request using the model(s).
     * @throws ApiException : if there are server or connection issues.
     */
    List<List<Context>> getContextsBulk(Model... models) throws JsonProcessingException, ApiException;
    
    /**
     * 
     * Resolves an expression.
     * 
     * @param sparsity :  a value used for re-sparsifying the evaluated expression.
     * @param model : a model for which a fingerprint is generated. 
     * @return a fingerprint for the input model.
     * @throws JsonProcessingException if it is impossible to generate the request using the model(s).
     * @throws ApiException : if there are server or connection issues.
     */
    Fingerprint resolve(Double sparsity, Model model) throws JsonProcessingException, ApiException;
    
    /**
     * 
     * Resolves an expression.
     * 
     * <br/>The default sparsity is {@link DefaultValues#DEF_VALUE_SPARSITY}.
     * 
     * @param model : a model for which a fingerprint is generated. 
     * @return a fingerprint for the input model.
     * @throws JsonProcessingException if it is impossible to generate the request using the model(s).
     * @throws ApiException : if there are server or connection issues.
     */
    Fingerprint resolve(Model model) throws JsonProcessingException, ApiException;
    
    
    /**
     *  Calculate contexts of the result of an expression.
     * 
     * @param pagination : the response item's pagination mechanism configuration.
     * @param includeFingerprint : true if a fingerprint field should  be provided for each of the response items.
     * @param sparsity : a value used for re-sparsifying the evaluated expression.
     * @param model : a model for which a list of contexts is generated. 
     * @return a list of contexts generated from the input model.
     * @throws JsonProcessingException if it is impossible to generate the request using the model(s).
     * @throws ApiException : if there are server or connection issues.
     */
    List<Context> getContexts(Pagination pagination, Boolean includeFingerprint, Double sparsity, Model model)
            throws JsonProcessingException, ApiException;
    
    /**
     *  Calculate contexts of the result of an expression.
     * <br/> The default pagination configuration is:
     * <ul>
     * <li>
     * Start from: {@link DefaultValues#DEF_VALUE_START_INDEX}
     * </li>
     * <li>
     * Max count of items in a result: {@link DefaultValues#DEF_VALUE_MAX_CONTEXTS_COUNT}
     * </li>
     * </ul>
     * @param includeFingerprint : true if a fingerprint field should  be provided for each of the response items.
     * @param sparsity : a value used for re-sparsifying the evaluated expression.
     * @param model : a model for which a list of contexts is generated. 
     * @return a list of contexts generated from the input model.
     * @throws JsonProcessingException if it is impossible to generate the request using the model(s).
     * @throws ApiException : if there are server or connection issues.
     */
    List<Context> getContexts(Boolean includeFingerprint, Double sparsity, Model model) throws JsonProcessingException,
            ApiException;
    
    /**
     *  Calculate contexts of the result of an expression.
     * <br/> The default pagination configuration is:
     * <ul>
     * <li>
     * Start from: {@link DefaultValues#DEF_VALUE_START_INDEX}
     * </li>
     * <li>
     * Max count of items in a result: {@link DefaultValues#DEF_VALUE_MAX_CONTEXTS_COUNT}
     * </li>
     * </ul>
     * <br/> Whether to include a fingerprint in the response is defined by {@link DefaultValues#DEF_VALUE_PROVIDE_FINGERPRINT}

     * @param sparsity : a value used for re-sparsifying the evaluated expression.
     * @param model : a model for which a list of contexts is generated. 
     * @return a list of contexts generated from the input model.
     * @throws JsonProcessingException if it is impossible to generate the request using the model(s).
     * @throws ApiException : if there are server or connection issues.
     */
    List<Context> getContexts(Double sparsity, Model model) throws JsonProcessingException, ApiException;
    
    /**
     *  Calculate contexts of the result of an expression.
     * <br/> The default pagination configuration is:
     * <ul>
     * <li>
     * Start from: {@link DefaultValues#DEF_VALUE_START_INDEX}
     * </li>
     * <li>
     * Max count of items in a result: {@link DefaultValues#DEF_VALUE_MAX_CONTEXTS_COUNT}
     * </li>
     * </ul>
     *  <br/> The default sparsity is {@link DefaultValues#DEF_VALUE_SPARSITY}
     *  
     * @param includeFingerprint : true if a fingerprint field should  be provided for each of the response items.
     * @param model : a model for which a list of contexts is generated. 
     * @return a list of contexts generated from the input model.
     * @throws JsonProcessingException if it is impossible to generate the request using the model(s).
     * @throws ApiException : if there are server or connection issues.
     */
    List<Context> getContexts(Boolean includeFingerprint, Model model) throws JsonProcessingException, ApiException;
    
    /**
     *  Calculate contexts of the result of an expression.
     * <br/> The default pagination configuration is:
     * <ul>
     * <li>
     * Start from: {@link DefaultValues#DEF_VALUE_START_INDEX}
     * </li>
     * <li>
     * Max count of items in a result: {@link DefaultValues#DEF_VALUE_MAX_CONTEXTS_COUNT}
     * </li>
     * </ul>
     * <br/> Whether to include a fingerprint in the response is defined by {@link DefaultValues#DEF_VALUE_PROVIDE_FINGERPRINT}
     *   <br/> The default sparsity is {@link DefaultValues#DEF_VALUE_SPARSITY}
     *   
     * @param sparsity : a value used for re-sparsifying the evaluated expression.
     * @param model : a model for which a list of contexts is generated. 
     * @return a list of contexts generated from the input model.
     * @throws JsonProcessingException if it is impossible to generate the request using the model(s).
     * @throws ApiException : if there are server or connection issues.
     */
    List<Context> getContexts(Model model) throws JsonProcessingException, ApiException;
    
    /**
     * Get similar terms for the expression.
     * 
     * @param contextId : the id of the relevant context.
     * @param posType : a part of speech type.
     * @param pagination : a response's items pagination mechanism configuration.
     * @param includeFingerprint : true if a fingerprint field should  be provided for each of the response items.
     * @param sparsity : a value used for re-sparsifying the evaluated expression.
     * @param model : a model for which a list of terms is generated. 
     * @return a list of similar terms generated from the input model.
     * @throws JsonProcessingException if it is impossible to generate the request using the model(s).
     * @throws ApiException : if there are server or connection issues.
     */
    List<Term> getSimilarTerms(Integer contextId, PosType posType, Pagination pagination, Boolean includeFingerprint,
            Double sparsity, Model model) throws JsonProcessingException, ApiException;
    
    /**
     * Get similar terms for the expression.
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
     * @param contextId : the id of the relevant context.
     * @param posType : a part of speech type.
     * @param includeFingerprint : true if a fingerprint field should  be provided for each of the response items.
     * @param sparsity : a value used for re-sparsifying the evaluated expression.
     * @param model : a model for which a list of terms is generated. 
     * @return a list of similar terms generated from the input model.
     * @throws JsonProcessingException if it is impossible to generate the request using the model(s).
     * @throws ApiException : if there are server or connection issues.
     */
    List<Term> getSimilarTerms(Integer contextId, PosType posType, Boolean includeFingerprint, Double sparsity,
            Model model) throws JsonProcessingException, ApiException;
    
    /**
     * Get similar terms for the expression.
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
     * <br/> Whether to include a fingerprint in the response is defined by {@link DefaultValues#DEF_VALUE_PROVIDE_FINGERPRINT}
     * 
     * @param contextId : the id of the relevant context.
     * @param posType : a part of speech type.
     * @param sparsity : a value used for re-sparsifying the evaluated expression.
     * @param model : a model for which a list of terms is generated. 
     * @return a list of similar terms generated from the input model.
     * @throws JsonProcessingException if it is impossible to generate the request using the model(s).
     * @throws ApiException : if there are server or connection issues.
     */
    List<Term> getSimilarTerms(Integer contextId, PosType posType, Double sparsity, Model model)
            throws JsonProcessingException, ApiException;
    
    /**
     * Get similar terms for the expression.
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
     *  <br/> The default sparsity is {@link DefaultValues#DEF_VALUE_SPARSITY}
     * 
     * @param contextId : the id of the relevant context.
     * @param posType : a part of speech type.
     * @param includeFingerprint : true if a fingerprint field should  be provided for each of the response items.
     * @param model : a model for which a list of terms is generated. 
     * @return a list of similar terms generated from the input model.
     * @throws JsonProcessingException if it is impossible to generate the request using the model(s).
     * @throws ApiException : if there are server or connection issues.
     */
    List<Term> getSimilarTerms(Integer contextId, PosType posType, Boolean includeFingerprint, Model model)
            throws JsonProcessingException, ApiException;
    
    /**
     * Get similar terms for the expression.
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
     * <br/> Whether to include a fingerprint in the response is defined by {@link DefaultValues#DEF_VALUE_PROVIDE_FINGERPRINT}
     *  <br/> The default sparsity is {@link DefaultValues#DEF_VALUE_SPARSITY}
     * 
     * @param contextId : the id of the relevant context.
     * @param posType : a part of speech type.
     * @param model : a model for which a list of terms is generated. 
     * @return a list of similar terms generated from the input model.
     * @throws JsonProcessingException if it is impossible to generate the request using the model(s).
     * @throws ApiException : if there are server or connection issues.
     */
    List<Term> getSimilarTerms(Integer contextId, PosType posType, Model model) throws JsonProcessingException,
            ApiException;
    
    /**
     * Get similar terms for the expression (including all contexts).
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
     * <br/> Whether to include a fingerprint in the response is defined by {@link DefaultValues#DEF_VALUE_PROVIDE_FINGERPRINT}
     *  <br/> The default sparsity is {@link DefaultValues#DEF_VALUE_SPARSITY}
     * 
     * @param posType : a part of speech type.
     * @param model : a model for which a list of terms is generated. 
     * @return a list of similar terms generated from the input model.
     * @throws JsonProcessingException if it is impossible to generate the request using the model(s).
     * @throws ApiException : if there are server or connection issues.
     */
    List<Term> getSimilarTerms(PosType posType, Model model) throws JsonProcessingException, ApiException;
    
    /**
     * Get similar terms for the expression (including all parts of speech).
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
     * <br/> Whether to include a fingerprint in the response is defined by {@link DefaultValues#DEF_VALUE_PROVIDE_FINGERPRINT}
     *  <br/> The default sparsity is {@link DefaultValues#DEF_VALUE_SPARSITY}
     * 
     * @param contextId : the id of the relevant context.
     * @param model : a model for which a list of terms is generated. 
     * @return a list of similar terms generated from the input model.
     * @throws JsonProcessingException if it is impossible to generate the request using the model(s).
     * @throws ApiException : if there are server or connection issues.
     */
    List<Term> getSimilarTerms(Integer contextId, Model model) throws JsonProcessingException, ApiException;
    
    /**
     * Get similar terms for the expression (including all parts of speech and contexts).
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
     * <br/> Whether to include a fingerprint in the response is defined by {@link DefaultValues#DEF_VALUE_PROVIDE_FINGERPRINT}
     *  <br/> The default sparsity is {@link DefaultValues#DEF_VALUE_SPARSITY}
     * 
     * @param model : a model for which a list of terms is generated. 
     * @return a list of similar terms generated from the input model.
     * @throws JsonProcessingException if it is impossible to generate the request using the model(s).
     * @throws ApiException : if there are server or connection issues.
     */
    List<Term> getSimilarTerms(Model model) throws JsonProcessingException, ApiException;
    
    /**
     * Get similar terms for the expression (including all contexts).
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
     * <br/> Whether to include a fingerprint in the response is defined by {@link DefaultValues#DEF_VALUE_PROVIDE_FINGERPRINT}
     *  <br/> The default sparsity is {@link DefaultValues#DEF_VALUE_SPARSITY}
     * 
     * @param posType : a part of speech type.
     * @param jsonModel : a model for which a list of terms is generated in the json representation. 
     * @return a list of similar terms generated from the input model.
     * @throws JsonProcessingException if it is impossible to generate the request using the model(s).
     * @throws ApiException : if there are server or connection issues.
     */
    List<Term> getSimilarTerms(PosType posType, String jsonModel) throws JsonProcessingException, ApiException;
    
    /**
     * Get similar terms for the expression (including all parts of speech).
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
     * <br/> Whether to include a fingerprint in the response is defined by {@link DefaultValues#DEF_VALUE_PROVIDE_FINGERPRINT}
     *  <br/> The default sparsity is {@link DefaultValues#DEF_VALUE_SPARSITY}
     * 
     * @param contextId : a context id
     * @param jsonModel : a model for which a list of terms is generated. 
     * @return a list of similar terms generated from the input model.
     * @throws JsonProcessingException if it is impossible to generate the request using the model(s).
     * @throws ApiException : if there are server or connection issues.
     */
    List<Term> getSimilarTerms(Integer contextId, String jsonModel) throws JsonProcessingException, ApiException;
    
    /**
     * Get similar terms for the expression (including all parts of speech and contexts).
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
     * <br/> Whether to include a fingerprint in the response is defined by {@link DefaultValues#DEF_VALUE_PROVIDE_FINGERPRINT}
     *  <br/> The default sparsity is {@link DefaultValues#DEF_VALUE_SPARSITY}
     * 
     * @param jsonModel : a model for which a list of terms is generated. 
     * @return a list of similar terms generated from the input model.
     * @throws JsonProcessingException if it is impossible to generate the request using the model(s).
     * @throws ApiException : if there are server or connection issues.
     */
    List<Term> getSimilarTerms(String jsonModel) throws JsonProcessingException, ApiException;
    
    /**
     * Retrieve similar terms for the each item in the model's array.
     * 
     * @param contextId : a context id
     * @param posType : a part of speech type.
     * @param pagination : a response's items pagination mechanism configuration.
     * @param includeFingerprint : true if a fingerprint field should  be provided for each of the response items.
     * @param sparsity : a value used for re-sparsifying the evaluated expression.
     * @param jsonModel : json model for which a list of terms is generated. (for each model a list of {@link Term} is generated.) 
     * @return A list containing a list of terms generated for each item in the models.
     * @throws JsonProcessingException if it is impossible to generate the request using the model(s).
     * @throws ApiException : if there are server or connection issues.
     */
    List<List<Term>> getSimilarTermsBulk(Integer contextId, PosType posType, Pagination pagination,
            Boolean includeFingerprint, Double sparsity, String jsonModel) throws JsonProcessingException, ApiException;
    
    /**
     * Retrieve similar terms for the each item in the model's array.
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
     * @param contextId : a context id
     * @param posType : a part of speech type.
     * @param includeFingerprint : true if a fingerprint field should  be provided for each of the response items.
     * @param sparsity : a value used for re-sparsifying the evaluated expression.
     * @param jsonModel : model(s) for which a list of terms is generated. (for each model a list of {@link Term} is generated.) 
     * @return A list containing a list of terms generated for each item in the models.
     * @throws JsonProcessingException if it is impossible to generate the request using the model(s).
     * @throws ApiException : if there are server or connection issues.
     */
    List<List<Term>> getSimilarTermsBulk(Integer contextId, PosType posType, Boolean includeFingerprint,
            Double sparsity, String jsonModel) throws JsonProcessingException, ApiException;
    
    /**
     * Retrieve similar terms for the each item in the model's array.
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
     * <br/> Whether to include a fingerprint in the response is defined by {@link DefaultValues#DEF_VALUE_PROVIDE_FINGERPRINT}
     * 
     * @param contextId : a context id
     * @param posType : a part of speech type.
     * @param sparsity : a value used for re-sparsifying the evaluated expression.
     * @param jsonModel : json model(s) for which a list of terms is generated. (for each model a list of {@link Term} is generated.) 
     * @return A list containing a list of terms generated for each item in the models.
     * @throws JsonProcessingException if it is impossible to generate the request using the model(s).
     * @throws ApiException : if there are server or connection issues.
     */
    List<List<Term>> getSimilarTermsBulk(Integer contextId, PosType posType, Double sparsity, String jsonModel)
            throws JsonProcessingException, ApiException;
    
    /**
     * Retrieve similar terms for the each item in the model's array.
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
     * <br/>The default sparsity is {@link DefaultValues#DEF_VALUE_SPARSITY}.
     * 
     * @param contextId : a context id
     * @param posType : a part of speech type.
     * @param includeFingerprint : true if a fingerprint field should  be provided for each of the response items.
     * @param jsonModel : a json model(s) for which a list of terms is generated. (for each model a list of {@link Term} is generated.) 
     * @return A list containing a list of terms generated for each item in the models.
     * @throws JsonProcessingException if it is impossible to generate the request using the model(s).
     * @throws ApiException : if there are server or connection issues.
     */
    List<List<Term>> getSimilarTermsBulk(Integer contextId, PosType posType, Boolean includeFingerprint,
            String jsonModel) throws JsonProcessingException, ApiException;
    
    /**
     * Retrieve similar terms for the each item in the model's array.
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
     * <br/> Whether to include a fingerprint in the response is defined by {@link DefaultValues#DEF_VALUE_PROVIDE_FINGERPRINT}
     * <br/>The default sparsity is {@link DefaultValues#DEF_VALUE_SPARSITY}.
     * 
     * @param contextId : a context id
     * @param posType : a part of speech type.
     * @param jsonModel : json  model(s) for which a list of terms is generated. (for each model a list of {@link Term} is generated.) 
     * @return A list containing a list of terms generated for each item in the models.
     * @throws JsonProcessingException if it is impossible to generate the request using the model(s).
     * @throws ApiException : if there are server or connection issues.
     */
    List<List<Term>> getSimilarTermsBulk(Integer contextId, PosType posType, String jsonModel)
            throws JsonProcessingException, ApiException;
    
    
    /**
     * Resolves a bulk expression. 
     * 
     * @param  jsonModel : json model(s) for which the list of fingerprints is generated.
     * @param sparsity : the value used for re-sparsifying the evaluated expression.
     * @return a list of fingerprints generated for the input model(s).
     * @throws JsonProcessingException if it is impossible to generate the request using the model(s).
     * @throws ApiException : if there are server or connection issues.
     */
    List<Fingerprint> resolveBulk(Double sparsity, String jsonModel) throws JsonProcessingException, ApiException;
    
    /**
     * Resolves a bulk expression. 
     * <br/>The default sparsity is {@link DefaultValues#DEF_VALUE_SPARSITY}.
     * @param  jsonModel : json model(s) for which the list of fingerprints is generated.
     * @return a list of fingerprints generated for the input model(s).
     * @throws JsonProcessingException if it is impossible to generate the request using the model(s).
     * @throws ApiException : if there are server or connection issues.
     */
    List<Fingerprint> resolveBulk(String jsonModel) throws JsonProcessingException, ApiException;
    
    /**
     * Calculate contexts for each model. 
     * 
     * <br>Returns a list of {@link Context} for each one of the input expressions in the bulk, so the returned
     * Response object will contain a list of lists of Contexts.
     * 
     * @param pagination : a response's items pagination mechanism configuration.
     * @param includeFingerprint : true if a fingerprint field should  be provided for each of the response items.
     * @param sparsity : a value used for re-sparsifying the evaluated expression.
     * @param jsonModel : json model(s) for which a list of contexts is generated. (for each model a list of {@link Context} is generated.) 
     * @return a list of contexts lists generated from the input model(s).
     * @throws JsonProcessingException if it is impossible to generate the request using the model(s).
     * @throws ApiException : if there are server or connection issues.
     */
    List<List<Context>> getContextsBulk(Pagination pagination, Boolean includeFingerprint, Double sparsity,
            String jsonModel) throws JsonProcessingException, ApiException;
    
    /**
     * Calculate contexts for each items in the model's array. 
     * 
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
     * @param includeFingerprint : true if a fingerprint field should  be provided for each of the response items.
     * @param sparsity : a value used for re-sparsifying the evaluated expression.
     * @param jsonModel : json model(s) for which a list of contexts is generated. (for each model a list of {@link Context} is generated.) 
     * @return a list of contexts lists generated from the input model(s).
     * @throws JsonProcessingException if it is impossible to generate the request using the model(s).
     * @throws ApiException : if there are server or connection issues.
     */
    List<List<Context>> getContextsBulk(Boolean includeFingerprint, Double sparsity, String jsonModel)
            throws JsonProcessingException, ApiException;
    
    /**
     * Calculate contexts for each items in the model's array. 
     * 
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
     * <br/> Whether to include a fingerprint in the response is defined by {@link DefaultValues#DEF_VALUE_PROVIDE_FINGERPRINT}
     * @param sparsity : a value used for re-sparsifying the evaluated expression.
     * @param jsonModel : json  model(s) for which a list of contexts is generated. (for each model a list of {@link Context} is generated.) 
     * @return a list of contexts lists generated from passed model(s).
     * @throws JsonProcessingException if it is impossible to generate the request using the model(s).
     * @throws ApiException : if there are server or connection issues.
     */
    List<List<Context>> getContextsBulk(Double sparsity, String jsonModel) throws JsonProcessingException, ApiException;
    
    /**
     * Calculate contexts for each items in the model's array. 
     * 
     * 
     * <br/> The default sparsity is {@link DefaultValues#DEF_VALUE_SPARSITY}
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
     * @param includeFingerprint : true if a fingerprint field should  be provided for each of the response items.
     * @param jsonModel : json model(s) for which a list of contexts is generated. (for each model a list of {@link Context} is generated.) 
     * @return a list of contexts lists generated from passed model(s).
     * @throws JsonProcessingException if it is impossible to generate the request using the model(s).
     * @throws ApiException : if there are server or connection issues.
     */
    List<List<Context>> getContextsBulk(Boolean includeFingerprint, String jsonModel) throws JsonProcessingException,
            ApiException;
    
    /**
     * Calculate contexts for each items in the model's array. 
     * 
     * <br>Returns a list of {@link Context} for each one of the input expressions in the bulk. The returned
     * Response object will contain a list of lists of Contexts.
     * 
     * <br/> The default sparsity is {@link DefaultValues#DEF_VALUE_SPARSITY}
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
     * <br/> Whether to include a fingerprint in the response is defined by {@link DefaultValues#DEF_VALUE_PROVIDE_FINGERPRINT}
     *  
     * @param jsonModel : json model(s) for which a list of contexts is generated. (for each model a list of {@link Context} is generated.) 
     * @return a list of contexts lists generated from passed model(s).
     * @throws JsonProcessingException if it is impossible to generate the request using the model(s).
     * @throws ApiException : if there are server or connection issues.      */
    List<List<Context>> getContextsBulk(String jsonModel) throws JsonProcessingException, ApiException;
    
    /**
     * 
     * Resolves an expression.
     * @param sparsity :  a value used for re-sparsifying the evaluated expression.
     * @param jsonModel : json  model for which a fingerprint is generated. 
     * @return a fingerprint for the input model.
     * @throws JsonProcessingException if it is impossible to generate the request using the model(s).
     * @throws ApiException : if there are server or connection issues.      */
    Fingerprint resolve(Double sparsity, String jsonModel) throws JsonProcessingException, ApiException;
    
    /**
     * 
     * Resolves an expression.
     * 
     * <br/>The default sparsity is {@link DefaultValues#DEF_VALUE_SPARSITY}.
     * 
     * @param jsonModel : json model for which a fingerprint is generated. 
     * @return a fingerprint for the input model.
     * @throws JsonProcessingException if it is impossible to generate the request using the model(s).
     * @throws ApiException : if there are server or connection issues.      */
    Fingerprint resolve(String jsonModel) throws JsonProcessingException, ApiException;
    
    
    /**
     *  Calculate a contexts of the result of an expression.
     * 
     * @param pagination : a response's items pagination mechanism configuration.
     * @param includeFingerprint : true if a fingerprint field should  be provided for each of the response items.
     * @param sparsity : a value used for re-sparsifying the evaluated expression.
     * @param  jsonModel : json model for which a list of contexts is generated. 
     * @return a list of contexts generated from the input model.
     * @throws JsonProcessingException if it is impossible to generate the request using the model(s).
     * @throws ApiException : if there are server or connection issues.      */
    List<Context> getContexts(Pagination pagination, Boolean includeFingerprint, Double sparsity, String jsonModel)
            throws JsonProcessingException, ApiException;
    
    /**
     *  Calculate a contexts of the result of an expression.
     * <br/> The default pagination configuration is:
     * <ul>
     * <li>
     * Start from: {@link DefaultValues#DEF_VALUE_START_INDEX}
     * </li>
     * <li>
     * Max count of items in a result: {@link DefaultValues#DEF_VALUE_MAX_CONTEXTS_COUNT}
     * </li>
     * </ul>
     * @param includeFingerprint : true if a fingerprint field should  be provided for each of the response items.
     * @param sparsity : a value used for re-sparsifying the evaluated expression.
     * @param jsonModel : json model for which a list of contexts is generated. 
     * @return a list of contexts generated from the input model.
     * @throws JsonProcessingException if it is impossible to generate the request using the model(s).
     * @throws ApiException : if there are server or connection issues.      */
    List<Context> getContexts(Boolean includeFingerprint, Double sparsity, String jsonModel)
            throws JsonProcessingException, ApiException;
    
    /**
     *  Calculate a contexts of the result of an expression.
     * <br/> The default pagination configuration is:
     * <ul>
     * <li>
     * Start from: {@link DefaultValues#DEF_VALUE_START_INDEX}
     * </li>
     * <li>
     * Max count of items in a result: {@link DefaultValues#DEF_VALUE_MAX_CONTEXTS_COUNT}
     * </li>
     * </ul>
     * <br/> Whether to include a fingerprint in the response is defined by {@link DefaultValues#DEF_VALUE_PROVIDE_FINGERPRINT}
     * @param sparsity : a value used for re-sparsifying the evaluated expression.
     * @param jsonModel : a json model for which a list of contexts is generated. 
     * @return a list of contexts generated from passed model.
     * @throws JsonProcessingException if it is impossible to generate the request using the model(s).
     * @throws ApiException : if there are server or connection issues.      */
    List<Context> getContexts(Double sparsity, String jsonModel) throws JsonProcessingException, ApiException;
    
    /**
     *  Calculate a contexts of the result of an expression.
     * <br/> The default pagination configuration is:
     * <ul>
     * <li>
     * Start from: {@link DefaultValues#DEF_VALUE_START_INDEX}
     * </li>
     * <li>
     * Max count of items in a result: {@link DefaultValues#DEF_VALUE_MAX_CONTEXTS_COUNT}
     * </li>
     * </ul>
     *  <br/> The default sparsity is {@link DefaultValues#DEF_VALUE_SPARSITY}
     * @param includeFingerprint : true if a fingerprint field should  be provided for each of the response items.
     * @param jsonModel : json model for which a list of contexts is generated. 
     * @return a list of contexts generated from the input model.
     * @throws JsonProcessingException if it is impossible to generate the request using the model(s).
     * @throws ApiException : if there are server or connection issues.      */
    List<Context> getContexts(Boolean includeFingerprint, String jsonModel) throws JsonProcessingException,
            ApiException;
    
    /**
     *  Calculate contexts of the result of an expression.
     * <br/> The default pagination configuration is:
     * <ul>
     * <li>
     * Start from: {@link DefaultValues#DEF_VALUE_START_INDEX}
     * </li>
     * <li>
     * Max count of items in a result: {@link DefaultValues#DEF_VALUE_MAX_CONTEXTS_COUNT}
     * </li>
     * </ul>
     * <br/> Whether to include a fingerprint in the response is defined by {@link DefaultValues#DEF_VALUE_PROVIDE_FINGERPRINT}
     *  <br/> The default sparsity is {@link DefaultValues#DEF_VALUE_SPARSITY}
     * @param sparsity : a value used for re-sparsifying the evaluated expression.
     * @param jsonModel : json model for which a list of contexts is generated. 
     * @return a list of contexts generated from the input model.
     * @throws JsonProcessingException if it is impossible to generate the request using the model(s).
     * @throws ApiException : if there are server or connection issues.      */
    List<Context> getContexts(String jsonModel) throws JsonProcessingException, ApiException;
    
    /**
     * Gets similar terms for the expression.
     * 
     * @param contextId : a context id
     * @param posType : a part of speech type
     * @param pagination : a response's items pagination mechanism configuration.
     * @param includeFingerprint : true if a fingerprint field should  be provided for each of the response items.
     * @param sparsity : a value used for re-sparsifying the evaluated expression.
     * @param jsonModel : json  model for which a list of terms is generated. 
     * @return a list of similar terms generated from the input model.
     * @throws JsonProcessingException if it is impossible to generate the request using the model(s).
     * @throws ApiException : if there are server or connection issues.      */
    List<Term> getSimilarTerms(Integer contextId, PosType posType, Pagination pagination, Boolean includeFingerprint,
            Double sparsity, String jsonModel) throws JsonProcessingException, ApiException;
    
    /**
     * Gets similar terms for the expression.
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
     * @param contextId : a context id
     * @param posType : a part of speech type
     * @param includeFingerprint : true if a fingerprint field should  be provided for each of the response items.
     * @param sparsity : a value used for re-sparsifying the evaluated expression.
     * @param jsonModel : json model for which a list of terms is generated. 
     * @return a list of similar terms generated from the input model.
     * @throws JsonProcessingException if it is impossible to generate the request using the model(s).
     * @throws ApiException : if there are server or connection issues.      */
    List<Term> getSimilarTerms(Integer contextId, PosType posType, Boolean includeFingerprint, Double sparsity,
            String jsonModel) throws JsonProcessingException, ApiException;
    
    /**
     * Gets similar terms for the expression.
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
     * <br/> Whether to include a fingerprint in the response is defined by {@link DefaultValues#DEF_VALUE_PROVIDE_FINGERPRINT}
     * 
     * @param contextId : a context id
     * @param posType : a part of speech type
     * @param sparsity : a value used for re-sparsifying the evaluated expression.
     * @param jsonModel : json model for which a list of terms is generated. 
     * @return a list of similar terms generated from the input model.
     * @throws JsonProcessingException if it is impossible to generate the request using the model(s).
     * @throws ApiException : if there are server or connection issues.      */
    List<Term> getSimilarTerms(Integer contextId, PosType posType, Double sparsity, String jsonModel)
            throws JsonProcessingException, ApiException;
    
    /**
     * Gets similar terms for the expression.
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
     *  <br/> The default sparsity is {@link DefaultValues#DEF_VALUE_SPARSITY}
     * 
     * @param contextId : a context id
     * @param posType : a part of speech type
     * @param includeFingerprint : true if a fingerprint field should  be provided for each of the response items.
     * @param jsonModel : json model for which a list of terms is generated. 
     * @return a list of similar terms generated from the input model.
     * @throws JsonProcessingException if it is impossible to generate the request using the model(s).
     * @throws ApiException : if there are server or connection issues.      */
    List<Term> getSimilarTerms(Integer contextId, PosType posType, Boolean includeFingerprint, String jsonModel)
            throws JsonProcessingException, ApiException;
    
    /**
     * Gets similar terms for the expression.
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
     * <br/> Whether to include a fingerprint in the response is defined by {@link DefaultValues#DEF_VALUE_PROVIDE_FINGERPRINT}
     *  <br/> The default sparsity is {@link DefaultValues#DEF_VALUE_SPARSITY}
     * 
     * @param contextId : a context id
     * @param posType : a part of speech type
     * @param jsonModel : json model for which a list of terms is generated. 
     * @return a list of similar terms generated from the input model.
     * @throws JsonProcessingException if it is impossible to generate the request using the model(s).
     * @throws ApiException : if there are server or connection issues.      */
    List<Term> getSimilarTerms(Integer contextId, PosType posType, String jsonModel) throws JsonProcessingException,
            ApiException;
}
