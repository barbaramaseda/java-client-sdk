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
import static io.cortical.retina.service.RestServiceConstants.NULL_MODEL_MSG;
import static org.apache.commons.lang3.StringUtils.isBlank;
import io.cortical.retina.model.Metric;
import io.cortical.retina.model.Model;
import io.cortical.retina.service.ApiException;
import io.cortical.retina.service.CompareApi;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;



/**
 * 
 * The Compare Retina API implementation. 
 */
public class Compare extends AbstractRetinas {
    /** Rest Service access for the Compare endpoint */
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
    
    //////////////////////////////////////////////////
    //                   New Methods                //
    //////////////////////////////////////////////////
    /**
     * Compares 2 models.
     * 
     * @param model1    model to be compared with model2 
     * @param model2    model to be compared with model1
     * 
     * @return the result of the comparison as a @{link Metric} object.
     * @throws JsonProcessingException : if the models cannot be converted to JSON.
     * @throws ApiException : if the cortical.io's API isn't available/ or an internal error occurred.
     */
    public Metric compare(Model model1, Model model2) throws JsonProcessingException, ApiException {
        validateRequiredModels(model1, model2);
        return compareApi.compare(toJson(model1, model2), this.retinaName);
    }
    
    /**
     * Compares pairs of models in bulk.
     * 
     * @param compareModels     list of model to be compare holder. 
     * @return the result of the comparison as a array of @{link Metric} object.
     * @throws JsonProcessingException  if the models cannot be converted to JSON.
     * @throws ApiException     if the cortical.io's API isn't available/ or an internal error occurred.
     */
    public Metric[] compareBulk(List<CompareModel> compareModels) throws JsonProcessingException, ApiException {
        if (compareModels == null || compareModels.size() == 0) {
            throw new IllegalArgumentException(NULL_MODEL_MSG);
        }
        Model[][] toCompare = new Model[compareModels.size()][2];
        int i = 0;
        for (CompareModel pair: compareModels) {
            toCompare[i++] = pair.getModels();
        }
        return compareApi.compareBulk(toJsonBulk(toCompare), this.retinaName);
    }

    /**
     * 
     * Models to be compare holder.
     * 
     *
     */
    public static class CompareModel {
        /**
         * 
         * Creates a new instance of {@link CompareModel}.
         * 
         *
         */
        public CompareModel() {
            
        }
        
        /**
         * 
         * Creates a new instance of {@link CompareModel}.
         * @param model1 : first model to be compare.
         * @param model2 : second model to be compare.
         */
        public CompareModel(Model model1, Model model2) {
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
