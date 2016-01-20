/*******************************************************************************
 * Copyright (c) cortical.io GmbH. All rights reserved.
 *  
 * This software is confidential and proprietary information.
 * You shall use it only in accordance with the terms of the
 * license agreement you entered into with cortical.io GmbH.
 ******************************************************************************/
package io.cortical.retina.client.core;

import static io.cortical.retina.rest.RestServiceConstants.NULL_MODEL_MSG;

import com.fasterxml.jackson.core.JsonProcessingException;

import io.cortical.retina.model.Metric;
import io.cortical.retina.model.Model;
import io.cortical.retina.service.ApiException;
import io.cortical.retina.service.CompareApi;

import org.apache.commons.logging.Log;

import static io.cortical.retina.rest.RestServiceConstants.NULL_API_KEY_MSG;
import static io.cortical.retina.rest.RestServiceConstants.NULL_BASE_PATH_MSG;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.logging.LogFactory.getLog;



/**
 * 
 * The Compare Retina API implementation. 
 */
class Compare extends AbstractRetinas {
    /**
     * 
     */
    private static final Log LOG = getLog(Compare.class);
    /**
     * 
     */
    private final CompareApi compareApi;
    
    /**
     * 
     * Creates a new instance of {@link Compare}
     * 
     * @param retinaName
     * @param basePath
     * @param apiKey
     */
    Compare(String retinaName, String basePath, String apiKey) {
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
     * Creates a new instance of {@link Compare}
     * 
     * @param api
     * @param retinaName
     */
    Compare(CompareApi api, String retinaName) {
        super(retinaName);
        this.compareApi = api;
    }
    
    /**
     * Compares 2 models.
     * 
     * @param model1 : model to be compared with model2 
     * @param model2 : model to be compared with model1
     * @return the result of the comparison as a @{link Metric} object.
     * @throws JsonProcessingException : if the models cannot be converted to JSON.
     * @throws ApiException : if the cortical.io's API isn't available/ or an internal error occurred.
     */
    public Metric compare(Model model1, Model model2) throws JsonProcessingException, ApiException {
        validateRequiredModels(model1, model2);
        LOG.debug("Compare models: model1: " + model1.toJson() + "  model: " + model2.toJson());
        return compareApi.compare(toJson(model1, model2), this.retinaName);
    }
    
    /**
     * Compares 2 models.
     * 
     * @param jsonModel1 : json (string) model to be compared with model2 
     * @param model2 : model to be compared with jsonModel1
     * @return the result of the comparison as a @{link Metric} object.
     * @throws JsonProcessingException : if the models cannot be converted to JSON.
     * @throws ApiException : if the cortical.io's API isn't available/ or an internal error occurred.
     */
    public Metric compare(String jsonModel1, Model model2) throws JsonProcessingException, ApiException {
        validateRequiredModels(model2);
        LOG.debug("Compare models: model1: " + jsonModel1 + "  model: " + model2.toJson());
        return compareApi.compare("[ " + jsonModel1 + ", " + model2.toJson() + " ]", this.retinaName);
    }
    
    /**
     * Compares 2 models.
     *  d
     * @param jsonModel1 : json model to be compared with model2 
     * @param jsonModel2 : json model to be compared with model1
     * @return the result of the comparison as a @{link Metric} object.
     * @throws JsonProcessingException : if the models cannot be converted to JSON.
     * @throws ApiException : if the cortical.io's API isn't available/ or an internal error occurred.
     */
    public Metric compare(String jsonModel1, String jsonModel2) throws JsonProcessingException, ApiException {
        LOG.debug("Compare models: model1: " + jsonModel1 + "  model: " + jsonModel2);
        return compareApi.compare("[ " + jsonModel1 + ", " + jsonModel2 + " ]", this.retinaName);
    }

    /**
     * Compares bulk models.
     * 
     * @param compareModels : array of model to be compare holder. 
     * @return the result of the comparison as a array of @{link Metric} object.
     * @throws JsonProcessingException : if the models cannot be converted to JSON.
     * @throws ApiException : if the cortical.io's API isn't available/ or an internal error occurred.
     */
    public Metric[] compareBulk(CompareModels... compareModels) throws JsonProcessingException, ApiException {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Compare " + compareModels.length + " pairs.");
        }
        if (compareModels == null || compareModels.length == 0) {
            throw new IllegalArgumentException(NULL_MODEL_MSG);
        }
        Model[][] toCompare = new Model[compareModels.length][2];
        int i = 0;
        for (CompareModels pair: compareModels) {
            toCompare[i++] = pair.getModels();
        }
        return compareApi.compareBulk(toJsonBulk(toCompare), this.retinaName);
    }

    /**
     * Compares 2 models.
     * 
     * @param json : models in a json format to be compared.
     * @return the result of the comparison as a @{link Metric} object.
     * @throws JsonProcessingException : if the models cannot be converted to JSON.
     * @throws ApiException : if the cortical.io's API isn't available/ or an internal error occurred.
     */
    public Metric[] compareBulk(String json) throws JsonProcessingException, ApiException {
        LOG.debug("Compare models: " + json);
        if (json == null) {
            throw new IllegalArgumentException(NULL_MODEL_MSG);
        }
        return compareApi.compareBulk(json, this.retinaName);
    }
    
    /**
     * 
     * Models to be compare holder.
     * 
     *
     */
    public static class CompareModels {
        /**
         * 
         * Creates a new instance of {@link CompareModels}.
         * 
         *
         */
        public CompareModels() {
            
        }
        
        /**
         * 
         * Creates a new instance of {@link CompareModels}.
         * @param model1 : first model to be compare.
         * @param model2 : second model to be compare.
         */
        public CompareModels(Model model1, Model model2) {
            setModel1(model1);
            setModel2(model2);
        }
        
        
        private Model[] models = new Model[2];
        /**
         * Gets the model1.
         *
         * @return the model1
         */
        public Model getModel1() {
            return models[0];
        }
        /**
         * Sets the model1.
         *
         * @param model1 the model1 to set
         */
        public void setModel1(Model model1) {
            models[0] = model1;
        }
        /**
         * Gets the model2.
         *
         * @return the model2
         */
        public Model getModel2() {
            return models[1];
        }
        /**
         * Sets the model2.
         *
         * @param model2 the model2 to set
         */
        public void setModel2(Model model2) {
            models[1] = model2;
        }
        
        /**
         * Gets the models.
         *
         * @return the models
         */
        public Model[] getModels() {
            return models;
        }
    }
}
