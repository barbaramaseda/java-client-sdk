/*******************************************************************************
 * Copyright (c) cortical.io GmbH. All rights reserved.
 *  
 * This software is confidential and proprietary information.
 * You shall use it only in accordance with the terms of the
 * license agreement you entered into with cortical.io GmbH.
 ******************************************************************************/
package io.cortical.rest.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;
import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;
import static com.fasterxml.jackson.annotation.PropertyAccessor.FIELD;
import static com.fasterxml.jackson.databind.SerializationFeature.INDENT_OUTPUT;


/**
 * The base Model class.
 * 
 */
public abstract class Model {
    /**
     * 
     */
    private static final ObjectMapper MAPPER = new ObjectMapper();
    
    static {
        MAPPER.setVisibility(FIELD, ANY);
        MAPPER.enable(INDENT_OUTPUT);
        MAPPER.setSerializationInclusion(NON_EMPTY);
    }

    /**
     * Returns the json representation of the Model.
     * 
     * @return the object in the json representation.
     * @throws JsonProcessingException
     */
    public String toJson() throws JsonProcessingException {
        return MAPPER.writeValueAsString(this);
    }
    
    /**
     * Returns the json representation of the input Model(s).
     * 
     * @param models : models to be converted to json.
     * @return the objects' array in the json representation.
     * @throws JsonProcessingException
     */
    public static String toJson(Model... models) throws JsonProcessingException {
        return MAPPER.writeValueAsString(models);
    }

    /**
     * Returns the json representation of the input Model(s).
     * 
     * @param modelsArrays : arrays of models to be converted to json.
     * @return the objects' array in the json representation.
     * @throws JsonProcessingException
     */
    public static String toJsonBulk(Model[]... modelsArrays) throws JsonProcessingException {
        return MAPPER.writeValueAsString(modelsArrays);
    }

}
