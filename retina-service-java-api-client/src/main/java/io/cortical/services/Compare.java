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


/**
 * 
 * The Compare Retina API.
 */
public interface Compare {
    /**
     * 
     * Compare 2 models.
     * @param model1 : model to be compared with model2 
     * @param model2 : model to be compared with model1
     * @return the result of the comparison as a @{link Metric} object.
     * @throws JsonProcessingException : if the models cannot be converted to JSON.
     * @throws ApiException : if the cortical.io's API isn't available/ or an internal error occurred.
     */
    Metric compare(Model model1, Model model2) throws JsonProcessingException, ApiException;
    
    /**
     * 
     * Compare 2 models.
     * @param jsonModel1 : json (string) model to be compared with model2 
     * @param model2 : model to be compared with jsonModel1
     * @return the result of the comparison as a @{link Metric} object.
     * @throws JsonProcessingException : if the models cannot be converted to JSON.
     * @throws ApiException : if the cortical.io's API isn't available/ or an internal error occurred.
     */
    Metric compare(String jsonModel1, Model model2) throws JsonProcessingException, ApiException;
    
    /**
     * 
     * Compare 2 models.
     * @param jsonModel1 : json model to be compared with model2 
     * @param jsonModel2 : json model to be compared with model1
     * @return the result of the comparison as a @{link Metric} object.
     * @throws JsonProcessingException : if the models cannot be converted to JSON.
     * @throws ApiException : if the cortical.io's API isn't available/ or an internal error occurred.
     */
    Metric compare(String jsonModel1, String jsonModel2) throws JsonProcessingException, ApiException;
}
