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
     * Returns a {@link Fingerprint} for the specified expression.
     * 
     * @param sparsity      a {@link ExpressionModel} used for re-sparsifying the evaluated expression.
     * @param model         a model for which a fingerprint is generated. 
     * @return a fingerprint for the input model.
     * @throws JsonProcessingException if it is impossible to generate the request using the model(s).
     * @throws ApiException     if there are server or connection issues.
     */
    public Fingerprint getFingerprintForExpression(Model model, double sparsity) 
        throws JsonProcessingException, ApiException {
        validateRequiredModels(model);
        return this.expressionsApi.resolveExpression(model.toJson(), retinaName, sparsity);
    }
    
    /**
     * Resolves a bulk expression call. 
     * 
     * @param models        {@link Model}(s) for which the list of fingerprints is generated.
     * @param sparsity      a value used for re-sparsifying the evaluated expression.
     * 
     * @return a list of fingerprints generated for each of the input model(s).
     * @throws JsonProcessingException  if it is impossible to generate the request using the model(s).
     * @throws ApiException             if there are server or connection issues.
     */
    public <T extends Model> List<Fingerprint> getFingerprintsForExpressions(
        List<T> models, double sparsity) throws JsonProcessingException, ApiException {
        
        validateRequiredModels(models);
        return this.expressionsApi.resolveBulkExpression(toJson(models), retinaName, sparsity);
    }
    
    /**
     *  Calculate contexts of the result of an expression.
     * 
     * @param model                 a {@link Model} for which a list of contexts is generated.
     * @param startIndex            the response item's first result
     * @param maxResults            the maximum number of results to return
     * @param includeFingerprint    true if a fingerprint field should  be provided for each of the response items.
     * @param sparsity              a value used for re-sparsifying the evaluated expression.
     *  
     * @return a list of contexts generated from the input model.
     * @throws JsonProcessingException if it is impossible to generate the request using the model(s).
     * @throws ApiException : if there are server or connection issues.
     */
    public List<Context> getContextsForExpression(
        Model model, int startIndex, int maxResults, double sparsity, boolean includeFingerprint)
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
    public <T extends Model> List<List<Context>> getContextsForExpressions(
        List<T> expressionModels, int startIndex, int maxResults, 
            boolean includeFingerprint, double sparsity) throws JsonProcessingException, ApiException {
        
        validateRequiredModels(expressionModels);
        
        return this.expressionsApi.getContextsForBulkExpression(
            toJson(expressionModels), includeFingerprint, retinaName, startIndex, 
                maxResults, sparsity);
    }
    
    /**
     * Gets similar terms for the expression.
     * 
     * @param model       {@link Model} for which a list of terms is generated. 
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
    public List<Term> getSimilarTermsForExpression(Model model, int startIndex, 
        int maxResults, int contextId, PosType posType, boolean includeFingerprint, double sparsity) 
            throws JsonProcessingException, ApiException {
        
        String posTypeName = null;
        if (posType != null) {
            posTypeName = posType.name();
        }
        
        return this.expressionsApi.getSimilarTermsForExpressionContext(model.toJson(), contextId, 
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
    public <T extends Model> List<List<Term>> getSimilarTermsForExpressions(
        List<T> models, int startIndex, int maxResults, int contextId, PosType posType, 
            boolean includeFingerprint, double sparsity) throws JsonProcessingException, ApiException {
        
        String posTypeName = null;
        if (posType != null) {
            posTypeName = posType.name();
        }
        return this.expressionsApi.getSimilarTermsForBulkExpressionContext(toJson(models), contextId, 
            posTypeName, includeFingerprint, retinaName, startIndex, maxResults, sparsity);
    }
}
