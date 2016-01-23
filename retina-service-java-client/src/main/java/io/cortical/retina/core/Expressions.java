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
import static org.apache.commons.lang3.StringUtils.isBlank;
import io.cortical.retina.model.Context;
import io.cortical.retina.model.ExpressionFactory.ExpressionModel;
import io.cortical.retina.model.Fingerprint;
import io.cortical.retina.model.Model;
import io.cortical.retina.model.Term;
import io.cortical.retina.service.ApiException;
import io.cortical.retina.service.DefaultValues;
import io.cortical.retina.service.ExpressionsApi;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * 
 * The Expression Retina's API implementation.
 */
public class Expressions extends AbstractRetinas {
    /** Rest Service access for the Expressions endpoint */
    private final ExpressionsApi expressionsApi;
    
    Expressions(String apiKey, String basePath, String retinaName) {
        super(retinaName);
        
        if (isBlank(apiKey)) {
            throw new IllegalArgumentException(NULL_API_KEY_MSG);
        }
        
        if (isBlank(basePath)) {
            throw new IllegalArgumentException(NULL_BASE_PATH_MSG);
        }

        this.expressionsApi = new ExpressionsApi(apiKey);
        this.expressionsApi.setBasePath(basePath);
    }
    
    Expressions(ExpressionsApi expressionsApi, String retinaName) {
        super(retinaName);
        this.expressionsApi = expressionsApi;
    }
    
    //////////////////////////////////////////////////
    //                   New Methods                //
    //////////////////////////////////////////////////
    /**
     * Resolves an expression.
     * 
     * @param sparsity      a {@link ExpressionModel} used for re-sparsifying the evaluated expression.
     * @param model         a model for which a fingerprint is generated. 
     * @return a fingerprint for the input model.
     * @throws JsonProcessingException if it is impossible to generate the request using the model(s).
     * @throws ApiException     if there are server or connection issues.
     */
    public Fingerprint getFingerprintForExpression(Model model, Double sparsity) 
        throws JsonProcessingException, ApiException {
        validateRequiredModels(model);
        return this.expressionsApi.resolveExpression(model.toJson(), retinaName, sparsity);
    }
    
    /**
     * Resolves a bulk expression call. 
     * 
     * @param models        {@link ExpressionModel}(s) for which the list of fingerprints is generated.
     * @param sparsity      a value used for re-sparsifying the evaluated expression.
     * 
     * @return a list of fingerprints generated for each of the input model(s).
     * @throws JsonProcessingException  if it is impossible to generate the request using the model(s).
     * @throws ApiException             if there are server or connection issues.
     */
    public List<Fingerprint> getFingerprintsForExpressions(
        List<Model> expressionModels, Double sparsity) throws JsonProcessingException, ApiException {
        
        validateRequiredModels(expressionModels);
        return this.expressionsApi.resolveBulkExpression(toJson(expressionModels), retinaName, sparsity);
    }
    
    /**
     *  Calculate contexts of the result of an expression.
     * 
     * @param model                 a {@link Model} for which a list of contexts is generated.
     * @param startIndex            the response item's first result
     * @param 
     * @param includeFingerprint    true if a fingerprint field should  be provided for each of the response items.
     * @param sparsity              a value used for re-sparsifying the evaluated expression.
     *  
     * @return a list of contexts generated from the input model.
     * @throws JsonProcessingException if it is impossible to generate the request using the model(s).
     * @throws ApiException : if there are server or connection issues.
     */
    public List<Context> getContextsForExpression(
        Model model, int startIndex, int maxResults, Double sparsity, Boolean includeFingerprint)
            throws JsonProcessingException, ApiException {
        
        validateRequiredModels(model);
        return this.expressionsApi.getContextsForExpression(model.toJson(), includeFingerprint, retinaName,
            startIndex, maxResults, sparsity);
    }
    
    /**
     * Calculate contexts for each model. 
     * 
     * <br>Returns a list of {@link Context} for each one of the input expressions in the bulk, so the returned
     * Response object will contain a list of lists of Contexts.
     * 
     * @param jsonModel             json model(s) for which a list of contexts is generated. 
     *                              (for each model a list of {@link Context} is generated.) 
     * @param startIndex            the index of the first response required
     * @param maxResults            the maximum number of results to return
     * @param includeFingerprint    true if a fingerprint field should  be provided for each of the response items.
     * @param sparsity              a value used for re-sparsifying the evaluated expression.
     * 
     * @return a list of contexts lists generated from the input model(s).
     * @throws JsonProcessingException if it is impossible to generate the request using the model(s).
     * @throws ApiException     if there are server or connection issues.
     */
    public List<List<Context>> getContextsForExpressions(
        List<Model> expressionModels, int startIndex, int maxResults, 
            Boolean includeFingerprint, Double sparsity) throws JsonProcessingException, ApiException {
        
        validateRequiredModels(expressionModels);
        
        return this.expressionsApi.getContextsForBulkExpression(
            toJson(expressionModels), includeFingerprint, retinaName, startIndex, 
                maxResults, sparsity);
    }
    
    /**
     * Gets similar terms for the expression.
     * 
     * @param expressionModel       {@link ExpressionModel} for which a list of terms is generated. 
     * @param startIndex            the index of the first {@link Term} to return
     * @param maxResults            the maximum number of results to return
     * @param contextId             an id identifying a {@link Term}'s context
     * @param posType               a part of speech type
     * @param includeFingerprint    true if a fingerprint field should  be provided for each of the response items.
     * @param sparsity              a value used for re-sparsifying the evaluated expression.
     * 
     * @return a list of similar terms generated from the input model.
     * @throws JsonProcessingException if it is impossible to generate the request using the model(s).
     * @throws ApiException     if there are server or connection issues.      
     */
    public List<Term> getSimilarTermsForExpression(Model expressionModel, int startIndex, 
        int maxResults, Integer contextId, PosType posType, Boolean includeFingerprint, Double sparsity) 
            throws JsonProcessingException, ApiException {
        
        String posTypeName = null;
        if (posType != null) {
            posTypeName = posType.name();
        }
        
        return this.expressionsApi.getSimilarTermsForExpressionContext(expressionModel.toJson(), contextId, 
            posTypeName, includeFingerprint, retinaName, startIndex, maxResults, sparsity);
    }
    
    /**
     * Retrieve similar terms for the each item in the model's array.
     * 
     * @param expressionModels      an {@link ExpressionModel} for which a list of terms is generated. 
     *                              (for each model a list of {@link Term} is generated.) 
     * @param startIndex            the index of the first {@link Term} to return
     * @param maxResults            the total number of results to return
     * @param contextId             an id identifying a {@link Term}'s context
     * @param posType               a part of speech type.
     * @param includeFingerprint    true if a fingerprint field should  be provided for each of the response items.
     * @param sparsity              a value used for re-sparsifying the evaluated expression.
     * 
     * @return A list containing a list of terms generated for each item in the models.
     * @throws JsonProcessingException if it is impossible to generate the request using the model(s).
     * @throws ApiException if there are server or connection issues.
     */
    public List<List<Term>> getSimilarTermsForExpressions(
        List<Model> expressionModels, int startIndex, int maxResults, Integer contextId, PosType posType, 
            Boolean includeFingerprint, Double sparsity) throws JsonProcessingException, ApiException {
        
        String posTypeName = null;
        if (posType != null) {
            posTypeName = posType.name();
        }
        return this.expressionsApi.getSimilarTermsForBulkExpressionContext(toJson(expressionModels), contextId, 
            posTypeName, includeFingerprint, retinaName, startIndex, maxResults, sparsity);
    }
    
    //////////////////////////////////////////////////
    //                   Old Methods                //
    //////////////////////////////////////////////////
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
    public List<List<Term>> getSimilarTermsBulk(Integer contextId, PosType posType, Pagination pagination,
            Boolean includeFingerprint, Double sparsity, Model... models) throws JsonProcessingException, ApiException {
        validateRequiredModels(models);
        return getSimilarTermsBulk(contextId, posType, pagination, includeFingerprint, sparsity, toJson(models));
    }
    
    /**
     * Resolves a bulk expression call. 
     * 
     * @param models : model(s) for which the list of fingerprints is generated.
     * @param sparsity : a value used for re-sparsifying the evaluated expression.
     * @return a list of fingerprints generated for each of the input model(s).
     * @throws JsonProcessingException if it is impossible to generate the request using the model(s).
     * @throws ApiException : if there are server or connection issues.
     */
    public List<Fingerprint> resolveBulk(Double sparsity, Model... models) throws JsonProcessingException, ApiException {
        validateRequiredModels(models);
        return this.expressionsApi.resolveBulkExpression(toJson(models), retinaName, sparsity);
    }
    
    /**
     * Calculate contexts for each models. 
     * 
     * <br>Returns a list of {@link Context} for each of the input expressions in the bulk expression call. The returned
     * Response object will contain a list of lists of Contexts.
     * 
     * @param pagination : the response's items pagination mechanism configuration.
     * @param includeFingerprint : true if a fingerprint field should  be provided for each of the response items.
     * @param sparsity : a value used for re-sparsifying the evaluated expression.
     * @param models : model(s) for which a list of contexts is generated. (for each model a list of {@link Context} is 
     *          generated.) 
     * @return a list of contexts' lists generated from the input model(s).
     * @throws JsonProcessingException if it is impossible to generate the request using the model(s).
     * @throws ApiException : if there are server or connection issues.
     */
    public List<List<Context>> getContextsBulk(Pagination pagination, Boolean includeFingerprint, Double sparsity,
            Model... models) throws JsonProcessingException, ApiException {
        validateRequiredModels(models);
        return getContextsBulk(pagination, includeFingerprint, sparsity, toJson(models));
    }
    
    /**
     * Resolves an expression.
     * 
     * @param sparsity :  a value used for re-sparsifying the evaluated expression.
     * @param model : a model for which a fingerprint is generated. 
     * @return a fingerprint for the input model.
     * @throws JsonProcessingException if it is impossible to generate the request using the model(s).
     * @throws ApiException : if there are server or connection issues.
     */
    public Fingerprint resolve(Double sparsity, Model model) throws JsonProcessingException, ApiException {
        validateRequiredModels(model);
        return this.expressionsApi.resolveExpression(model.toJson(), retinaName, sparsity);
    }
    
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
    public List<Context> getContexts(Pagination pagination, Boolean includeFingerprint, Double sparsity, Model model)
            throws JsonProcessingException, ApiException {
        validateRequiredModels(model);
        return getContexts(pagination, includeFingerprint, sparsity, model.toJson());
    }
    
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
    public List<Term> getSimilarTerms(Integer contextId, PosType posType, Pagination pagination,
            Boolean includeFingerprint, Double sparsity, Model model) throws JsonProcessingException, ApiException {
        validateRequiredModels(model);
        return getSimilarTerms(contextId, posType, pagination, includeFingerprint, sparsity, model.toJson());
    }
    
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
    public List<List<Term>> getSimilarTermsBulk(Integer contextId, PosType posType, Boolean includeFingerprint,
            Double sparsity, Model... models) throws JsonProcessingException, ApiException {
        return getSimilarTermsBulk(contextId, posType, null, includeFingerprint, sparsity, models);
    }
    
    
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
    public List<List<Term>> getSimilarTermsBulk(Integer contextId, PosType posType, Double sparsity, Model... models)
            throws JsonProcessingException, ApiException {
        return getSimilarTermsBulk(contextId, posType, null, sparsity, models);
    }
    
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
    public List<List<Term>> getSimilarTermsBulk(Integer contextId, PosType posType, Boolean includeFingerprint,
            Model... models) throws JsonProcessingException, ApiException {
        return getSimilarTermsBulk(contextId, posType, includeFingerprint, null, models);
    }
    
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
    public List<List<Term>> getSimilarTermsBulk(Integer contextId, PosType posType, Model... models)
            throws JsonProcessingException, ApiException {
        return getSimilarTermsBulk(contextId, posType, null, null, models);
    }
    
    /**
     * Resolves a bulk expression. 
     * <br/>The default sparsity is {@link DefaultValues#DEF_VALUE_SPARSITY}.
     * @param models : a model(s) for which the list of fingerprints is generated.
     * @return a list of fingerprints generated for each of the input model(s).
     * @throws JsonProcessingException if it is impossible to generate the request using the model(s).
     * @throws ApiException : if there are server or connection issues.
     */
    public List<Fingerprint> resolveBulk(Model... models) throws JsonProcessingException, ApiException {
        return resolveBulk(null, models);
    }
    
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
    public List<List<Context>> getContextsBulk(Boolean includeFingerprint, Double sparsity, Model... models)
            throws JsonProcessingException, ApiException {
        return getContextsBulk(null, includeFingerprint, sparsity, models);
    }
    
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
    public List<List<Context>> getContextsBulk(Double sparsity, Model... models) throws JsonProcessingException,
            ApiException {
        return getContextsBulk(null, sparsity, models);
    }
    
    
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
    public List<List<Context>> getContextsBulk(Boolean includeFingerprint, Model... models)
            throws JsonProcessingException, ApiException {
        return getContextsBulk(includeFingerprint, null, models);
    }
    
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
    public List<List<Context>> getContextsBulk(Model... models) throws JsonProcessingException, ApiException {
        return getContextsBulk(null, null, models);
    }
    
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
    public Fingerprint resolve(Model model) throws JsonProcessingException, ApiException {
        return resolve(null, model);
    }
    
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
    public List<Context> getContexts(Boolean includeFingerprint, Double sparsity, Model model)
            throws JsonProcessingException, ApiException {
        return getContexts(null, includeFingerprint, sparsity, model);
    }
    
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
    public List<Context> getContexts(Double sparsity, Model model) throws JsonProcessingException, ApiException {
        return getContexts(null, sparsity, model);
    }
    
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
    public List<Context> getContexts(Boolean includeFingerprint, Model model) throws JsonProcessingException,
            ApiException {
        return getContexts(includeFingerprint, null, model);
    }
    
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
    public List<Context> getContexts(Model model) throws JsonProcessingException, ApiException {
        return getContexts(null, null, model);
    }
    
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
    public List<Term> getSimilarTerms(Integer contextId, PosType posType, Boolean includeFingerprint, Double sparsity,
            Model model) throws JsonProcessingException, ApiException {
        return getSimilarTerms(contextId, posType, null, includeFingerprint, sparsity, model);
    }
    
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
    public List<Term> getSimilarTerms(Integer contextId, PosType posType, Double sparsity, Model model)
            throws JsonProcessingException, ApiException {
        return getSimilarTerms(contextId, posType, null, sparsity, model);
    }
    
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
    public List<Term> getSimilarTerms(Integer contextId, PosType posType, Boolean includeFingerprint, Model model)
            throws JsonProcessingException, ApiException {
        return getSimilarTerms(contextId, posType, includeFingerprint, null, model);
    }
    
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
    public List<Term> getSimilarTerms(Integer contextId, PosType posType, Model model) throws JsonProcessingException,
            ApiException {
        return getSimilarTerms(contextId, posType, null, null, model);
    }
    
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
    public List<List<Term>> getSimilarTermsBulk(Integer contextId, PosType posType, Pagination pagination,
            Boolean includeFingerprint, Double sparsity, String jsonModel) throws JsonProcessingException, ApiException {
        pagination = initPagination(pagination);
        String posTypeName = null;
        if (posType != null) {
            posTypeName = posType.name();
        }
        return this.expressionsApi.getSimilarTermsForBulkExpressionContext(jsonModel, contextId, posTypeName,
                includeFingerprint, retinaName, pagination.getStartIndex(), pagination.getMaxResults(), sparsity);
    }
    
    /**
     * Resolves a bulk expression. 
     * 
     * @param  jsonModel : json model(s) for which the list of fingerprints is generated.
     * @param sparsity : the value used for re-sparsifying the evaluated expression.
     * @return a list of fingerprints generated for the input model(s).
     * @throws JsonProcessingException if it is impossible to generate the request using the model(s).
     * @throws ApiException : if there are server or connection issues.
     */
    public List<Fingerprint> resolveBulk(Double sparsity, String jsonModels) throws JsonProcessingException,
            ApiException {
        return this.expressionsApi.resolveBulkExpression(jsonModels, retinaName, sparsity);
    }
    
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
    public List<List<Context>> getContextsBulk(Pagination pagination, Boolean includeFingerprint, Double sparsity,
            String jsonModels) throws JsonProcessingException, ApiException {
        pagination = initPagination(pagination);
        return this.expressionsApi.getContextsForBulkExpression(jsonModels, includeFingerprint, retinaName,
                pagination.getStartIndex(), pagination.getMaxResults(), sparsity);
    }
    
    /**
     * 
     * Resolves an expression.
     * @param sparsity :  a value used for re-sparsifying the evaluated expression.
     * @param jsonModel : json  model for which a fingerprint is generated. 
     * @return a fingerprint for the input model.
     * @throws JsonProcessingException if it is impossible to generate the request using the model(s).
     * @throws ApiException : if there are server or connection issues.      
     */
    public Fingerprint resolve(Double sparsity, String jsonModel) throws JsonProcessingException, ApiException {
        return this.expressionsApi.resolveExpression(jsonModel, retinaName, sparsity);
    }
    
    /**
     *  Calculate a contexts of the result of an expression.
     * 
     * @param pagination : a response's items pagination mechanism configuration.
     * @param includeFingerprint : true if a fingerprint field should  be provided for each of the response items.
     * @param sparsity : a value used for re-sparsifying the evaluated expression.
     * @param  jsonModel : json model for which a list of contexts is generated. 
     * @return a list of contexts generated from the input model.
     * @throws JsonProcessingException if it is impossible to generate the request using the model(s).
     * @throws ApiException : if there are server or connection issues.      
     */
    public List<Context> getContexts(Pagination pagination, Boolean includeFingerprint, Double sparsity,
            String jsonModel) throws JsonProcessingException, ApiException {
        pagination = initPagination(pagination);
        return this.expressionsApi.getContextsForExpression(jsonModel, includeFingerprint, retinaName,
                pagination.getStartIndex(), pagination.getMaxResults(), sparsity);
    }
    
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
     * @throws ApiException : if there are server or connection issues.      
     */
    public List<Term> getSimilarTerms(Integer contextId, PosType posType, Pagination pagination,
            Boolean includeFingerprint, Double sparsity, String jsonModel) throws JsonProcessingException, ApiException {
        
        pagination = initPagination(pagination);
        String posTypeName = null;
        if (posType != null) {
            posTypeName = posType.name();
        }
        return this.expressionsApi.getSimilarTermsForExpressionContext(jsonModel, contextId, posTypeName,
                includeFingerprint, retinaName, pagination.getStartIndex(), pagination.getMaxResults(), sparsity);
    }
    
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
    public List<List<Term>> getSimilarTermsBulk(Integer contextId, PosType posType, Boolean includeFingerprint,
            Double sparsity, String jsonModel) throws JsonProcessingException, ApiException {
        return getSimilarTermsBulk(contextId, posType, null, includeFingerprint, sparsity, jsonModel);
    }
    
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
    public List<List<Term>> getSimilarTermsBulk(Integer contextId, PosType posType, Double sparsity, String jsonModel)
            throws JsonProcessingException, ApiException {
        return getSimilarTermsBulk(contextId, posType, null, sparsity, jsonModel);
    }
    
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
    public List<List<Term>> getSimilarTermsBulk(Integer contextId, PosType posType, Boolean includeFingerprint,
            String jsonModel) throws JsonProcessingException, ApiException {
        return getSimilarTermsBulk(contextId, posType, includeFingerprint, null, jsonModel);
    }
    
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
    public List<List<Term>> getSimilarTermsBulk(Integer contextId, PosType posType, String jsonModel)
            throws JsonProcessingException, ApiException {
        return getSimilarTermsBulk(contextId, posType, null, null, jsonModel);
    }
    
    /**
     * Resolves a bulk expression. 
     * <br/>The default sparsity is {@link DefaultValues#DEF_VALUE_SPARSITY}.
     * @param  jsonModel : json model(s) for which the list of fingerprints is generated.
     * @return a list of fingerprints generated for the input model(s).
     * @throws JsonProcessingException if it is impossible to generate the request using the model(s).
     * @throws ApiException : if there are server or connection issues.
     */
    public List<Fingerprint> resolveBulk(String jsonModel) throws JsonProcessingException, ApiException {
        return resolveBulk(null, jsonModel);
    }
    
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
    public List<List<Context>> getContextsBulk(Boolean includeFingerprint, Double sparsity, String jsonModel)
            throws JsonProcessingException, ApiException {
        return getContextsBulk(null, includeFingerprint, sparsity, jsonModel);
    }
    
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
    public List<List<Context>> getContextsBulk(Double sparsity, String jsonModel) throws JsonProcessingException,
            ApiException {
        return getContextsBulk(null, sparsity, jsonModel);
    }
    
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
    public List<List<Context>> getContextsBulk(Boolean includeFingerprint, String jsonModel)
            throws JsonProcessingException, ApiException {
        return getContextsBulk(includeFingerprint, null, jsonModel);
    }
    
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
     * @throws ApiException : if there are server or connection issues.      
     */
    public List<List<Context>> getContextsBulk(String jsonModel) throws JsonProcessingException, ApiException {
        return getContextsBulk(null, null, jsonModel);
    }
    
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
    public Fingerprint resolve(String jsonModel) throws JsonProcessingException, ApiException {
        return resolve(null, jsonModel);
    }
    
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
     * @throws ApiException : if there are server or connection issues.      
     */
    public List<Context> getContexts(Boolean includeFingerprint, Double sparsity, String jsonModel)
            throws JsonProcessingException, ApiException {
        return getContexts(null, includeFingerprint, sparsity, jsonModel);
    }
    
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
     * @throws ApiException : if there are server or connection issues.      
     */
    public List<Context> getContexts(Double sparsity, String jsonModel) throws JsonProcessingException, ApiException {
        return getContexts(null, sparsity, jsonModel);
    }
    
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
     * @throws ApiException : if there are server or connection issues.      
     */
    public List<Context> getContexts(Boolean includeFingerprint, String jsonModel) throws JsonProcessingException,
            ApiException {
        return getContexts(includeFingerprint, null, jsonModel);
    }
    
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
     * @throws ApiException : if there are server or connection issues.      
     */
    public List<Context> getContexts(String jsonModel) throws JsonProcessingException, ApiException {
        return getContexts(null, null, jsonModel);
    }
    
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
    public List<Term> getSimilarTerms(Integer contextId, PosType posType, Boolean includeFingerprint, Double sparsity,
            String jsonModel) throws JsonProcessingException, ApiException {
        return getSimilarTerms(contextId, posType, null, includeFingerprint, sparsity, jsonModel);
    }
    
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
    public List<Term> getSimilarTerms(Integer contextId, PosType posType, Double sparsity, String jsonModel)
            throws JsonProcessingException, ApiException {
        return getSimilarTerms(contextId, posType, null, sparsity, jsonModel);
    }
    
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
    public List<Term> getSimilarTerms(Integer contextId, PosType posType, Boolean includeFingerprint, String jsonModel)
            throws JsonProcessingException, ApiException {
        return getSimilarTerms(contextId, posType, includeFingerprint, null, jsonModel);
    }
    
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
    public List<Term> getSimilarTerms(Integer contextId, PosType posType, String jsonModel)
            throws JsonProcessingException, ApiException {
        return getSimilarTerms(contextId, posType, null, null, jsonModel);
    }
    
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
    public List<List<Term>> getSimilarTermsBulk(PosType posType, Model... models) throws JsonProcessingException,
            ApiException {
        return getSimilarTermsBulk(null, posType, models);
    }
    
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
    public List<List<Term>> getSimilarTermsBulk(Integer contextId, Model... models) throws JsonProcessingException,
            ApiException {
        return getSimilarTermsBulk(contextId, null, models);
    }
    
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
    public List<List<Term>> getSimilarTermsBulk(Model... models) throws JsonProcessingException, ApiException {
        return getSimilarTermsBulk(null, null, models);
    }
    
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
    public List<List<Term>> getSimilarTermsBulk(PosType posType, String jsonModel) throws JsonProcessingException,
            ApiException {
        return getSimilarTermsBulk(null, posType, jsonModel);
    }
    
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
    public List<List<Term>> getSimilarTermsBulk(Integer contextId, String jsonModel) throws JsonProcessingException,
            ApiException {
        return getSimilarTermsBulk(contextId, null, jsonModel);
    }
    
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
    public List<List<Term>> getSimilarTermsBulk(String jsonModel) throws JsonProcessingException, ApiException {
        return getSimilarTermsBulk(null, null, jsonModel);
    }
    
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
    public List<Term> getSimilarTerms(PosType posType, Model model) throws JsonProcessingException, ApiException {
        return getSimilarTerms(null, posType, model);
    }
    
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
    public List<Term> getSimilarTerms(Integer contextId, Model model) throws JsonProcessingException, ApiException {
        return getSimilarTerms(contextId, null, model);
    }
    
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
    public List<Term> getSimilarTerms(Model model) throws JsonProcessingException, ApiException {
        return getSimilarTerms(null, null, model);
    }
    
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
    public List<Term> getSimilarTerms(PosType posType, String jsonModel) throws JsonProcessingException, ApiException {
        return getSimilarTerms(null, posType, jsonModel);
    }
    
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
    public List<Term> getSimilarTerms(Integer contextId, String jsonModel) throws JsonProcessingException, ApiException {
        return getSimilarTerms(contextId, null, jsonModel);
    }
    
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
    public List<Term> getSimilarTerms(String jsonModel) throws JsonProcessingException, ApiException {
        return getSimilarTerms(null, null, jsonModel);
    }
    
    
}
