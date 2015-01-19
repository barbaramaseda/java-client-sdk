/*******************************************************************************
 * Copyright (c) cortical.io GmbH. All rights reserved.
 *  
 * This software is confidential and proprietary information.
 * You shall use it only in accordance with the terms of the
 * license agreement you entered into with cortical.io GmbH.
 ******************************************************************************/
package io.cortical.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.cortical.rest.model.Fingerprint;
import io.cortical.rest.model.Retina;
import io.cortical.rest.model.Text;
import io.cortical.services.api.client.ApiException;
import io.cortical.services.api.client.api.TextApi;
import java.util.List;
import org.apache.commons.logging.Log;
import static io.cortical.rest.RestServiceConstants.NULL_API_KEY_MSG;
import static io.cortical.rest.RestServiceConstants.NULL_BASE_PATH_MSG;
import static io.cortical.rest.RestServiceConstants.NULL_TEXT_MSG;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isEmpty;
import static org.apache.commons.logging.LogFactory.getLog;



/**
 * 
 * The Retina's Texts API implementation. 
 */
class TextRetinaApiImpl extends BaseRetinaApi implements Texts {
    /**
     * 
     */
    private static final Log LOG = getLog(TextRetinaApiImpl.class);
    /**
     * 
     */
    private final TextApi api;
    
    TextRetinaApiImpl(String apiKey, String basePath, String retinaName) {
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
    
    TextRetinaApiImpl(final TextApi api, String retinaName) {
        super(retinaName);
        this.api = api;
    }
    
    /** {@inheritDoc}  */
    @Override
    public List<String> getKeywords(String text) throws ApiException {
        if (isEmpty(text)) {
            throw new IllegalArgumentException(NULL_TEXT_MSG);
        }
        LOG.debug("Retrieve keywords for the text: " + text);
        return this.api.getKeywordsForText(text, retinaName);
    }
    
    /** {@inheritDoc} */
    @Override
    public List<Fingerprint> getFingerprints(String text) throws ApiException {
        if (isEmpty(text)) {
            throw new IllegalArgumentException(NULL_TEXT_MSG);
        }
        LOG.debug("Retrieve representation for the text: " + text);
        return this.api.getRepresentationForText(text, retinaName);
    }
    
    /** {@inheritDoc} */
    @Override
    public List<Fingerprint> getFingerprintBulk(Double sparsity, Text... texts) throws JsonProcessingException,
            ApiException {
        validateRequiredModels(texts);
        LOG.debug("Retrieve representation for the bulk Text: " + toJson(texts) + "  sparsity: " + sparsity);
        return this.api.getRepresentationsForBulkText(toJson(texts), retinaName, sparsity);
    }
    
    /** {@inheritDoc} */
    @Override
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
    
    /** {@inheritDoc} */
    @Override
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
    
    /** {@inheritDoc} */
    @Override
    public List<Text> getSlices(String text, Boolean includeFingerprint) throws ApiException {
        return getSlices(text, null, includeFingerprint);
    }
    
    /** {@inheritDoc} */
    @Override
    public List<Text> getSlices(String text) throws ApiException {
        return getSlices(text, null);
    }
    
    /** {@inheritDoc} */
    @Override
    public Retina identifyRetinaByText(String text) throws ApiException {
        if (isEmpty(text)) {
            throw new IllegalArgumentException(NULL_TEXT_MSG);
        }
        return this.api.getLanguage(text);
    }
}
