/*******************************************************************************
 * Copyright (c) cortical.io GmbH. All rights reserved.
 *  
 * This software is confidential and proprietary information.
 * You shall use it only in accordance with the terms of the
 * license agreement you entered into with cortical.io GmbH.
 ******************************************************************************/
package io.cortical.retina.core;

import static io.cortical.retina.rest.RestServiceConstants.NULL_MODEL_MSG;
import static io.cortical.retina.rest.RestServiceConstants.NULL_RETINA_MSG;
import static org.apache.commons.lang3.StringUtils.isBlank;
import io.cortical.retina.model.Model;
import io.cortical.retina.model.Text;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;



/**
 * A base retina Api.
 * Contains validation and default initialization methods.  
 * 
 */
abstract class AbstractEndpoint {
    /**
     * 
     */
    protected final String retinaName;
    
    protected AbstractEndpoint(String retinaName) {
        if (isBlank(retinaName)) {
            throw new IllegalArgumentException(NULL_RETINA_MSG);
        }
        
        this.retinaName = retinaName;
    }
    
    protected void validateRequiredModels(Model... models) {
        if (models == null || models.length == 0) {
            throw new IllegalArgumentException(NULL_MODEL_MSG);
        }
        for (Model model : models) {
            if (model == null) {
                throw new IllegalArgumentException(NULL_MODEL_MSG);
            }
        }
    }
    
    protected <T extends Model> void validateRequiredModels(List<T> models) {
        if (models == null || models.size() == 0) {
            throw new IllegalArgumentException(NULL_MODEL_MSG);
        }
        for (Model model : models) {
            if (model == null) {
                throw new IllegalArgumentException(NULL_MODEL_MSG);
            }
        }
    }
    
    protected List<Text> convertToTextModel(List<String> l) {
        List<Text> retVal = new ArrayList<>();
        for(String s : l) {
            retVal.add(new Text(s));
        }
        return retVal;
    }
    
    
    /**
     * Returns a JSON representation of the input Models.
     * 
     * @param models    models to be converted to a json array.
     * @return the array in json format.
     * @throws JsonProcessingException
     */
    protected String toJson(Model... models) throws JsonProcessingException {
        return Model.toJson(models);
    }
    
    /**
     * Returns a JSON representation of the input Models.
     * 
     * @param models    models to be converted to a json array.
     * @return the array in json format.
     * @throws JsonProcessingException
     */
    protected <T extends Model> String toJson(List<T> models) throws JsonProcessingException {
        return Model.toJson(models);
    }
    
    /**
     * Returns the json representation of the input Model(s).
     * 
     * @param modelsArrays  arrays of models to be converted to json.
     * @return the array in json format.
     * @throws JsonProcessingException
     */
    public static String toJsonBulk(Model[]... modelsArrays) throws JsonProcessingException {
        return Model.toJsonBulk(modelsArrays);
    }
}
