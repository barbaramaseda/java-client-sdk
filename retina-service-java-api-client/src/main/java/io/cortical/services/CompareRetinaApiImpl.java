/*******************************************************************************
 * Copyright (c) cortical.io GmbH. All rights reserved.
 *  
 * This software is confidential and proprietary information.
 * You shall use it only in accordance with the terms of the
 * license agreement you entered into with cortical.io GmbH.
 ******************************************************************************/
package io.cortical.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.cortical.rest.model.Metric;
import io.cortical.rest.model.Model;
import io.cortical.services.api.client.ApiException;
import io.cortical.services.api.client.api.CompareApi;
import org.apache.commons.logging.Log;
import static io.cortical.rest.RestServiceConstants.NULL_API_KEY_MSG;
import static io.cortical.rest.RestServiceConstants.NULL_BASE_PATH_MSG;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.logging.LogFactory.getLog;



/**
 * 
 * The Compare Retina API implementation. 
 */
class CompareRetinaApiImpl extends BaseRetinaApi implements Compare {
    /**
     * 
     */
    private static final Log LOG = getLog(CompareRetinaApiImpl.class);
    /**
     * 
     */
    private final CompareApi compareApi;
    
    /**
     * 
     * Creates a new instance of {@link CompareRetinaApiImpl}
     * 
     * @param retinaName
     * @param basePath
     * @param apiKey
     */
    CompareRetinaApiImpl(String retinaName, String basePath, String apiKey) {
        super(retinaName);
        
        if (isBlank(apiKey)) {
            throw new IllegalArgumentException(NULL_API_KEY_MSG);
        }
        
        if (isBlank(basePath)) {
            throw new IllegalArgumentException(NULL_BASE_PATH_MSG);
        }
        LOG.info("Initialize Compare Retina Api with retina: " + retinaName);
        this.compareApi = new CompareApi(apiKey);
        this.compareApi.setBasePath(basePath);
    }
    
    /**
     * 
     * Creates a new instance of {@link CompareRetinaApiImpl}
     * 
     * @param api
     * @param retinaName
     */
    CompareRetinaApiImpl(CompareApi api, String retinaName) {
        super(retinaName);
        this.compareApi = api;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Metric compare(Model model1, Model model2) throws JsonProcessingException, ApiException {
        validateRequiredModels(model1, model2);
        LOG.debug("Compare models: model1: " + model1.toJson() + "  model: " + model2.toJson());
        return compareApi.compare(toJson(model1, model2), this.retinaName);
    }
    
    /** {@inheritDoc} */
    @Override
    public Metric compare(String jsonModel1, Model model2) throws JsonProcessingException, ApiException {
        validateRequiredModels(model2);
        LOG.debug("Compare models: model1: " + jsonModel1 + "  model: " + model2.toJson());
        return compareApi.compare("[ " + jsonModel1 + ", " + model2.toJson() + " ]", this.retinaName);
    }
    
    /** {@inheritDoc} */
    @Override
    public Metric compare(String jsonModel1, String jsonModel2) throws JsonProcessingException, ApiException {
        LOG.debug("Compare models: model1: " + jsonModel1 + "  model: " + jsonModel2);
        return compareApi.compare("[ " + jsonModel1 + ", " + jsonModel2 + " ]", this.retinaName);
    }
}
