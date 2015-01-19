/*******************************************************************************
 * Copyright (c) cortical.io GmbH. All rights reserved.
 *  
 * This software is confidential and proprietary information.
 * You shall use it only in accordance with the terms of the
 * license agreement you entered into with cortical.io GmbH.
 ******************************************************************************/
package io.cortical.services;

import io.cortical.rest.model.Context;
import io.cortical.rest.model.Term;
import io.cortical.services.api.client.ApiException;
import io.cortical.services.api.client.api.TermsApi;
import java.util.List;
import org.apache.commons.logging.Log;
import static io.cortical.rest.RestServiceConstants.NULL_API_KEY_MSG;
import static io.cortical.rest.RestServiceConstants.NULL_BASE_PATH_MSG;
import static io.cortical.rest.RestServiceConstants.NULL_TERM_MSG;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.logging.LogFactory.getLog;



/**
 * 
 * The Retina's Terms API implementation. 
 */
class TermsRetinaApiImpl extends BaseRetinaApi implements Terms {
    /**
     * 
     */
    private static final Log LOG = getLog(TermsRetinaApiImpl.class);
    /**
     * 
     */
    private final TermsApi api;
    
    TermsRetinaApiImpl(String apiKey, String basePath, String retinaName) {
        super(retinaName);
        
        if (isBlank(apiKey)) {
            throw new IllegalArgumentException(NULL_API_KEY_MSG);
        }
        
        if (isBlank(basePath)) {
            throw new IllegalArgumentException(NULL_BASE_PATH_MSG);
        }
        LOG.info("Initialize Terms Retina Api with retina: " + retinaName);
        this.api = new TermsApi(apiKey);
        this.api.setBasePath(basePath);
    }
    
    TermsRetinaApiImpl(TermsApi api, String retinaName) {
        super(retinaName);
        this.api = api;
    }
    
    /** {@inheritDoc}  */
    @Override
    public List<Context> getContexts(String term, Pagination pagination, Boolean includeFingerprint)
            throws ApiException {
        pagination = initPagination(pagination);
        validateTerm(term);
        
        LOG.debug("Retrieve contexts for the term: " + term + " pagination: " + pagination.toString()
                + "  include fingerprint: " + includeFingerprint);
        
        return api.getContextsForTerm(term, includeFingerprint, retinaName, pagination.getStartIndex(),
                pagination.getMaxResults());
    }
    
    /** {@inheritDoc} */
    @Override
    public List<Term> getSimilarTerms(String term, Integer contextId, PosType posType, Pagination pagination,
            Boolean includeFingerprint) throws ApiException {
        
        pagination = initPagination(pagination);
        validateTerm(term);
        
        String posTypeName = null;
        if (posType != null) {
            posTypeName = posType.name();
        }
        
        LOG.debug("Retrieve Similar for the term: " + term + " pagination: " + pagination.toString()
                + "  include fingerprint: " + includeFingerprint);
        
        return api.getSimilarTerms(term, contextId, posTypeName, includeFingerprint, retinaName,
                pagination.getStartIndex(), pagination.getMaxResults());
    }
    
    /** {@inheritDoc} */
    @Override
    public List<Term> getTerm(String term, Pagination pagination, Boolean includeFingerprint) throws ApiException {
        
        pagination = initPagination(pagination);
        
        LOG.debug("Retrieve terms: " + term + " pagination: " + pagination.toString() + "  include fingerprint: "
                + includeFingerprint);
        
        return api
                .getTerm(term, includeFingerprint, retinaName, pagination.getStartIndex(), pagination.getMaxResults());
    }
    
    private void validateTerm(String term) {
        if (term == null || term.trim().length() == 0) {
            throw new IllegalArgumentException(NULL_TERM_MSG);
        }
    }
    
    /** {@inheritDoc} */
    @Override
    public List<Context> getContexts(String term, Boolean includeFingerprint) throws ApiException {
        return getContexts(term, null, includeFingerprint);
    }
    
    /** {@inheritDoc} */
    @Override
    public List<Context> getContexts(String term) throws ApiException {
        return getContexts(term, null);
    }
    
    /** {@inheritDoc} */
    @Override
    public List<Term> getSimilarTerms(String term, Integer contextId, PosType posType, Boolean includeFingerprint)
            throws ApiException {
        return getSimilarTerms(term, contextId, posType, null, includeFingerprint);
    }
    
    /** {@inheritDoc} */
    @Override
    public List<Term> getSimilarTerms(String term, Integer contextId, PosType posType) throws ApiException {
        return getSimilarTerms(term, contextId, posType, null);
    }
    
    /** {@inheritDoc} */
    @Override
    public List<Term> getTerm(String term, Boolean includeFingerprint) throws ApiException {
        return getTerm(term, null, includeFingerprint);
    }
    
    /** {@inheritDoc} */
    @Override
    public List<Term> getTerm(String term) throws ApiException {
        return getTerm(term, null);
    }
    
    /** {@inheritDoc}  */
    @Override
    public List<Term> getAllTerms(Pagination pagination) throws ApiException {
        return getTerm(null, pagination, null);
    }
    
    /** {@inheritDoc} */
    @Override
    public List<Term> getAllTerms(Pagination pagination, Boolean includeFingerprint) throws ApiException {
        return getTerm(null, pagination, includeFingerprint);
    }

    /** {@inheritDoc} */
    @Override
    public List<Term> getSimilarTerms(String term) throws ApiException {
        return getSimilarTerms(term, null, null);
    }
}
