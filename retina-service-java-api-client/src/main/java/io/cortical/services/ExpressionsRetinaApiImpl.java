/*******************************************************************************
 * Copyright (c) cortical.io GmbH. All rights reserved.
 *  
 * This software is confidential and proprietary information.
 * You shall use it only in accordance with the terms of the
 * license agreement you entered into with cortical.io GmbH.
 ******************************************************************************/
package io.cortical.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.cortical.rest.model.Context;
import io.cortical.rest.model.Fingerprint;
import io.cortical.rest.model.Model;
import io.cortical.rest.model.Term;
import io.cortical.services.api.client.ApiException;
import io.cortical.services.api.client.api.ExpressionsApi;
import java.util.List;
import org.apache.commons.logging.Log;
import static io.cortical.rest.RestServiceConstants.NULL_API_KEY_MSG;
import static io.cortical.rest.RestServiceConstants.NULL_BASE_PATH_MSG;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.logging.LogFactory.getLog;

/**
 * 
 * The Expression Retina's API implementation.
 */
class ExpressionsRetinaApiImpl extends BaseRetinaApi implements Expressions {
    /**
     * 
     */
    private static final Log LOG = getLog(ExpressionsRetinaApiImpl.class);
    /**
     * 
     */
    private final ExpressionsApi expressionsApi;
    
    ExpressionsRetinaApiImpl(String apiKey, String basePath, String retinaName) {
        super(retinaName);
        
        if (isBlank(apiKey)) {
            throw new IllegalArgumentException(NULL_API_KEY_MSG);
        }
        
        if (isBlank(basePath)) {
            throw new IllegalArgumentException(NULL_BASE_PATH_MSG);
        }
        LOG.info("Initialize Expressions Retina Api with retina: " + retinaName);
        this.expressionsApi = new ExpressionsApi(apiKey);
        this.expressionsApi.setBasePath(basePath);
    }
    
    ExpressionsRetinaApiImpl(ExpressionsApi expressionsApi, String retinaName) {
        super(retinaName);
        this.expressionsApi = expressionsApi;
    }
    
    /** {@inheritDoc} */
    @Override
    public List<List<Term>> getSimilarTermsBulk(Integer contextId, PosType posType, Pagination pagination,
            Boolean includeFingerprint, Double sparsity, Model... models) throws JsonProcessingException, ApiException {
        validateRequiredModels(models);
        return getSimilarTermsBulk(contextId, posType, pagination, includeFingerprint, sparsity, toJson(models));
    }
    
    
    /** {@inheritDoc} */
    @Override
    public List<Fingerprint> resolveBulk(Double sparsity, Model... models) throws JsonProcessingException, ApiException {
        validateRequiredModels(models);
        LOG.debug("Resolve bulk expression for models: " + toJson(models) + "  sparsity: " + sparsity);
        return this.expressionsApi.resolveBulkExpression(toJson(models), retinaName, sparsity);
    }
    
    /** {@inheritDoc} */
    @Override
    public List<List<Context>> getContextsBulk(Pagination pagination, Boolean includeFingerprint, Double sparsity,
            Model... models) throws JsonProcessingException, ApiException {
        validateRequiredModels(models);
        return getContextsBulk(pagination, includeFingerprint, sparsity, toJson(models));
    }
    
    
    /** {@inheritDoc} */
    @Override
    public Fingerprint resolve(Double sparsity, Model model) throws JsonProcessingException, ApiException {
        validateRequiredModels(model);
        LOG.debug("Resolve expression for model: " + model.toJson());
        return this.expressionsApi.resolveExpression(model.toJson(), retinaName, sparsity);
    }
    
    
    /** {@inheritDoc} */
    @Override
    public List<Context> getContexts(Pagination pagination, Boolean includeFingerprint, Double sparsity, Model model)
            throws JsonProcessingException, ApiException {
        validateRequiredModels(model);
        return getContexts(pagination, includeFingerprint, sparsity, model.toJson());
    }
    
    
    /** {@inheritDoc} */
    @Override
    public List<Term> getSimilarTerms(Integer contextId, PosType posType, Pagination pagination,
            Boolean includeFingerprint, Double sparsity, Model model) throws JsonProcessingException, ApiException {
        validateRequiredModels(model);
        return getSimilarTerms(contextId, posType, pagination, includeFingerprint, sparsity, model.toJson());
    }
    
    
    /** {@inheritDoc} */
    @Override
    public List<List<Term>> getSimilarTermsBulk(Integer contextId, PosType posType, Boolean includeFingerprint,
            Double sparsity, Model... models) throws JsonProcessingException, ApiException {
        return getSimilarTermsBulk(contextId, posType, null, includeFingerprint, sparsity, models);
    }
    
    
    /** {@inheritDoc} */
    @Override
    public List<List<Term>> getSimilarTermsBulk(Integer contextId, PosType posType, Double sparsity, Model... models)
            throws JsonProcessingException, ApiException {
        return getSimilarTermsBulk(contextId, posType, null, sparsity, models);
    }
    
    
    /** {@inheritDoc} */
    @Override
    public List<List<Term>> getSimilarTermsBulk(Integer contextId, PosType posType, Boolean includeFingerprint,
            Model... models) throws JsonProcessingException, ApiException {
        return getSimilarTermsBulk(contextId, posType, includeFingerprint, null, models);
    }
    
    
    /** {@inheritDoc} */
    @Override
    public List<List<Term>> getSimilarTermsBulk(Integer contextId, PosType posType, Model... models)
            throws JsonProcessingException, ApiException {
        return getSimilarTermsBulk(contextId, posType, null, null, models);
    }
    
    
    /** {@inheritDoc} */
    @Override
    public List<Fingerprint> resolveBulk(Model... models) throws JsonProcessingException, ApiException {
        return resolveBulk(null, models);
    }
    
    
    /** {@inheritDoc} */
    @Override
    public List<List<Context>> getContextsBulk(Boolean includeFingerprint, Double sparsity, Model... models)
            throws JsonProcessingException, ApiException {
        return getContextsBulk(null, includeFingerprint, sparsity, models);
    }
    
    
    /** {@inheritDoc} */
    @Override
    public List<List<Context>> getContextsBulk(Double sparsity, Model... models) throws JsonProcessingException,
            ApiException {
        return getContextsBulk(null, sparsity, models);
    }
    
    
    /** {@inheritDoc} */
    @Override
    public List<List<Context>> getContextsBulk(Boolean includeFingerprint, Model... models)
            throws JsonProcessingException, ApiException {
        return getContextsBulk(includeFingerprint, null, models);
    }
    
    
    /** {@inheritDoc} */
    @Override
    public List<List<Context>> getContextsBulk(Model... models) throws JsonProcessingException, ApiException {
        return getContextsBulk(null, null, models);
    }
    
    
    /** {@inheritDoc} */
    @Override
    public Fingerprint resolve(Model model) throws JsonProcessingException, ApiException {
        return resolve(null, model);
    }
    
    
    /** {@inheritDoc} */
    @Override
    public List<Context> getContexts(Boolean includeFingerprint, Double sparsity, Model model)
            throws JsonProcessingException, ApiException {
        return getContexts(null, includeFingerprint, sparsity, model);
    }
    
    
    /** {@inheritDoc} */
    @Override
    public List<Context> getContexts(Double sparsity, Model model) throws JsonProcessingException, ApiException {
        return getContexts(null, sparsity, model);
    }
    
    
    /** {@inheritDoc} */
    @Override
    public List<Context> getContexts(Boolean includeFingerprint, Model model) throws JsonProcessingException,
            ApiException {
        return getContexts(includeFingerprint, null, model);
    }
    
    
    /** {@inheritDoc} */
    @Override
    public List<Context> getContexts(Model model) throws JsonProcessingException, ApiException {
        return getContexts(null, null, model);
    }
    
    
    /** {@inheritDoc} */
    @Override
    public List<Term> getSimilarTerms(Integer contextId, PosType posType, Boolean includeFingerprint, Double sparsity,
            Model model) throws JsonProcessingException, ApiException {
        return getSimilarTerms(contextId, posType, null, includeFingerprint, sparsity, model);
    }
    
    
    /** {@inheritDoc} */
    @Override
    public List<Term> getSimilarTerms(Integer contextId, PosType posType, Double sparsity, Model model)
            throws JsonProcessingException, ApiException {
        return getSimilarTerms(contextId, posType, null, sparsity, model);
    }
    
    
    /** {@inheritDoc} */
    @Override
    public List<Term> getSimilarTerms(Integer contextId, PosType posType, Boolean includeFingerprint, Model model)
            throws JsonProcessingException, ApiException {
        return getSimilarTerms(contextId, posType, includeFingerprint, null, model);
    }
    
    
    /** {@inheritDoc} */
    @Override
    public List<Term> getSimilarTerms(Integer contextId, PosType posType, Model model) throws JsonProcessingException,
            ApiException {
        return getSimilarTerms(contextId, posType, null, null, model);
    }
    
    /** {@inheritDoc} */
    @Override
    public List<List<Term>> getSimilarTermsBulk(Integer contextId, PosType posType, Pagination pagination,
            Boolean includeFingerprint, Double sparsity, String jsonModel) throws JsonProcessingException, ApiException {
        pagination = initPagination(pagination);
        String posTypeName = null;
        if (posType != null) {
            posTypeName = posType.name();
        }
        LOG.debug("Retrieve similar terms for bulk expression: model: " + jsonModel + " pagination: "
                + pagination.toString() + "  sparsity: " + sparsity + "  include fingerprint: " + includeFingerprint);
        return this.expressionsApi.getSimilarTermsForBulkExpressionContext(jsonModel, contextId, posTypeName,
                includeFingerprint, retinaName, pagination.getStartIndex(), pagination.getMaxResults(), sparsity);
    }
    
    
    /** {@inheritDoc} */
    @Override
    public List<Fingerprint> resolveBulk(Double sparsity, String jsonModels) throws JsonProcessingException,
            ApiException {
        LOG.debug("Resolve bulk expression for models: " + jsonModels + "  sparsity: " + sparsity);
        return this.expressionsApi.resolveBulkExpression(jsonModels, retinaName, sparsity);
    }
    
    /** {@inheritDoc} */
    @Override
    public List<List<Context>> getContextsBulk(Pagination pagination, Boolean includeFingerprint, Double sparsity,
            String jsonModels) throws JsonProcessingException, ApiException {
        pagination = initPagination(pagination);
        LOG.debug("Retrieve contexts for bulk expression: " + jsonModels + " pagination: " + pagination.toString()
                + "  sparsity: " + sparsity + "  include fingerprint: " + includeFingerprint);
        return this.expressionsApi.getContextsForBulkExpression(jsonModels, includeFingerprint, retinaName,
                pagination.getStartIndex(), pagination.getMaxResults(), sparsity);
    }
    
    
    /** {@inheritDoc} */
    @Override
    public Fingerprint resolve(Double sparsity, String jsonModel) throws JsonProcessingException, ApiException {
        LOG.debug("Resolve expression for model: " + jsonModel);
        return this.expressionsApi.resolveExpression(jsonModel, retinaName, sparsity);
    }
    
    
    /** {@inheritDoc} */
    @Override
    public List<Context> getContexts(Pagination pagination, Boolean includeFingerprint, Double sparsity,
            String jsonModel) throws JsonProcessingException, ApiException {
        pagination = initPagination(pagination);
        LOG.debug("Retrieve contexts for expression: model: " + jsonModel + " pagination: " + pagination.toString()
                + "  sparsity: " + sparsity + "  include fingerprint: " + includeFingerprint);
        return this.expressionsApi.getContextsForExpression(jsonModel, includeFingerprint, retinaName,
                pagination.getStartIndex(), pagination.getMaxResults(), sparsity);
    }
    
    
    /** {@inheritDoc} */
    @Override
    public List<Term> getSimilarTerms(Integer contextId, PosType posType, Pagination pagination,
            Boolean includeFingerprint, Double sparsity, String jsonModel) throws JsonProcessingException, ApiException {
        
        pagination = initPagination(pagination);
        String posTypeName = null;
        if (posType != null) {
            posTypeName = posType.name();
        }
        LOG.debug("Retrieve similar terms for model: " + jsonModel + " pagination: " + pagination.toString()
                + "  sparsity: " + sparsity + "  include fingerprint: " + includeFingerprint);
        return this.expressionsApi.getSimilarTermsForExpressionContext(jsonModel, contextId, posTypeName,
                includeFingerprint, retinaName, pagination.getStartIndex(), pagination.getMaxResults(), sparsity);
    }
    
    
    /** {@inheritDoc} */
    @Override
    public List<List<Term>> getSimilarTermsBulk(Integer contextId, PosType posType, Boolean includeFingerprint,
            Double sparsity, String jsonModel) throws JsonProcessingException, ApiException {
        return getSimilarTermsBulk(contextId, posType, null, includeFingerprint, sparsity, jsonModel);
    }
    
    
    /** {@inheritDoc} */
    @Override
    public List<List<Term>> getSimilarTermsBulk(Integer contextId, PosType posType, Double sparsity, String jsonModel)
            throws JsonProcessingException, ApiException {
        return getSimilarTermsBulk(contextId, posType, null, sparsity, jsonModel);
    }
    
    
    /** {@inheritDoc} */
    @Override
    public List<List<Term>> getSimilarTermsBulk(Integer contextId, PosType posType, Boolean includeFingerprint,
            String jsonModel) throws JsonProcessingException, ApiException {
        return getSimilarTermsBulk(contextId, posType, includeFingerprint, null, jsonModel);
    }
    
    
    /** {@inheritDoc} */
    @Override
    public List<List<Term>> getSimilarTermsBulk(Integer contextId, PosType posType, String jsonModel)
            throws JsonProcessingException, ApiException {
        return getSimilarTermsBulk(contextId, posType, null, null, jsonModel);
    }
    
    
    /** {@inheritDoc} */
    @Override
    public List<Fingerprint> resolveBulk(String jsonModel) throws JsonProcessingException, ApiException {
        return resolveBulk(null, jsonModel);
    }
    
    
    /** {@inheritDoc} */
    @Override
    public List<List<Context>> getContextsBulk(Boolean includeFingerprint, Double sparsity, String jsonModel)
            throws JsonProcessingException, ApiException {
        return getContextsBulk(null, includeFingerprint, sparsity, jsonModel);
    }
    
    
    /** {@inheritDoc} */
    @Override
    public List<List<Context>> getContextsBulk(Double sparsity, String jsonModel) throws JsonProcessingException,
            ApiException {
        return getContextsBulk(null, sparsity, jsonModel);
    }
    
    
    /** {@inheritDoc} */
    @Override
    public List<List<Context>> getContextsBulk(Boolean includeFingerprint, String jsonModel)
            throws JsonProcessingException, ApiException {
        return getContextsBulk(includeFingerprint, null, jsonModel);
    }
    
    
    /** {@inheritDoc} */
    @Override
    public List<List<Context>> getContextsBulk(String jsonModel) throws JsonProcessingException, ApiException {
        return getContextsBulk(null, null, jsonModel);
    }
    
    
    /** {@inheritDoc} */
    @Override
    public Fingerprint resolve(String jsonModel) throws JsonProcessingException, ApiException {
        return resolve(null, jsonModel);
    }
    
    
    /** {@inheritDoc} */
    @Override
    public List<Context> getContexts(Boolean includeFingerprint, Double sparsity, String jsonModel)
            throws JsonProcessingException, ApiException {
        return getContexts(null, includeFingerprint, sparsity, jsonModel);
    }
    
    
    /** {@inheritDoc} */
    @Override
    public List<Context> getContexts(Double sparsity, String jsonModel) throws JsonProcessingException, ApiException {
        return getContexts(null, sparsity, jsonModel);
    }
    
    
    /** {@inheritDoc} */
    @Override
    public List<Context> getContexts(Boolean includeFingerprint, String jsonModel) throws JsonProcessingException,
            ApiException {
        return getContexts(includeFingerprint, null, jsonModel);
    }
    
    
    /** {@inheritDoc} */
    @Override
    public List<Context> getContexts(String jsonModel) throws JsonProcessingException, ApiException {
        return getContexts(null, null, jsonModel);
    }
    
    
    /** {@inheritDoc} */
    @Override
    public List<Term> getSimilarTerms(Integer contextId, PosType posType, Boolean includeFingerprint, Double sparsity,
            String jsonModel) throws JsonProcessingException, ApiException {
        return getSimilarTerms(contextId, posType, null, includeFingerprint, sparsity, jsonModel);
    }
    
    
    /** {@inheritDoc} */
    @Override
    public List<Term> getSimilarTerms(Integer contextId, PosType posType, Double sparsity, String jsonModel)
            throws JsonProcessingException, ApiException {
        return getSimilarTerms(contextId, posType, null, sparsity, jsonModel);
    }
    
    
    /** {@inheritDoc} */
    @Override
    public List<Term> getSimilarTerms(Integer contextId, PosType posType, Boolean includeFingerprint, String jsonModel)
            throws JsonProcessingException, ApiException {
        return getSimilarTerms(contextId, posType, includeFingerprint, null, jsonModel);
    }
    
    
    /** {@inheritDoc} */
    @Override
    public List<Term> getSimilarTerms(Integer contextId, PosType posType, String jsonModel)
            throws JsonProcessingException, ApiException {
        return getSimilarTerms(contextId, posType, null, null, jsonModel);
    }
    
    /** {@inheritDoc} */
    @Override
    public List<List<Term>> getSimilarTermsBulk(PosType posType, Model... models) throws JsonProcessingException,
            ApiException {
        return getSimilarTermsBulk(null, posType, models);
    }
    
    /** {@inheritDoc} */
    @Override
    public List<List<Term>> getSimilarTermsBulk(Integer contextId, Model... models) throws JsonProcessingException,
            ApiException {
        return getSimilarTermsBulk(contextId, null, models);
    }
    
    /** {@inheritDoc} */
    @Override
    public List<List<Term>> getSimilarTermsBulk(Model... models) throws JsonProcessingException, ApiException {
        return getSimilarTermsBulk(null, null, models);
    }
    
    /** {@inheritDoc} */
    @Override
    public List<List<Term>> getSimilarTermsBulk(PosType posType, String jsonModel) throws JsonProcessingException,
            ApiException {
        return getSimilarTermsBulk(null, posType, jsonModel);
    }
    
    /** {@inheritDoc} */
    @Override
    public List<List<Term>> getSimilarTermsBulk(Integer contextId, String jsonModel) throws JsonProcessingException,
            ApiException {
        return getSimilarTermsBulk(contextId, null, jsonModel);
    }
    
    /** {@inheritDoc} */
    @Override
    public List<List<Term>> getSimilarTermsBulk(String jsonModel) throws JsonProcessingException, ApiException {
        return getSimilarTermsBulk(null, null, jsonModel);
    }
    
    /** {@inheritDoc} */
    @Override
    public List<Term> getSimilarTerms(PosType posType, Model model) throws JsonProcessingException, ApiException {
        return getSimilarTerms(null, posType, model);
    }
    
    /** {@inheritDoc} */
    @Override
    public List<Term> getSimilarTerms(Integer contextId, Model model) throws JsonProcessingException, ApiException {
        return getSimilarTerms(contextId, null, model);
    }
    
    /** {@inheritDoc} */
    @Override
    public List<Term> getSimilarTerms(Model model) throws JsonProcessingException, ApiException {
        return getSimilarTerms(null, null, model);
    }
    
    /** {@inheritDoc} */
    @Override
    public List<Term> getSimilarTerms(PosType posType, String jsonModel) throws JsonProcessingException, ApiException {
        return getSimilarTerms(null, posType, jsonModel);
    }
    
    /** {@inheritDoc} */
    @Override
    public List<Term> getSimilarTerms(Integer contextId, String jsonModel) throws JsonProcessingException, ApiException {
        return getSimilarTerms(contextId, null, jsonModel);
    }
    
    /** {@inheritDoc} */
    @Override
    public List<Term> getSimilarTerms(String jsonModel) throws JsonProcessingException, ApiException {
        return getSimilarTerms(null, null, jsonModel);
    }
    
    
}
