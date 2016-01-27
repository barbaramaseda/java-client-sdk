/*******************************************************************************
 * Copyright (c) cortical.io GmbH. All rights reserved.
 *
 * This software is confidential and proprietary information.
 * You shall use it only in accordance with the terms of the
 * license agreement you entered into with cortical.io GmbH.
 ******************************************************************************/
package io.cortical.retina.core;

import static io.cortical.retina.rest.RestServiceConstants.NULL_API_KEY_MSG;
import static io.cortical.retina.rest.RestServiceConstants.NULL_BASE_PATH_MSG;
import static io.cortical.retina.rest.RestServiceConstants.NULL_TEXT_MSG;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isEmpty;
import io.cortical.retina.model.CategoryFilter;
import io.cortical.retina.model.Sample;
import io.cortical.retina.rest.ApiException;
import io.cortical.retina.rest.ClassifyApi;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Encapsulates access to the Classify endpoint.
 */
public class Classify extends AbstractEndpoint {

    /** Rest Service access for the Classify end point */
    private final ClassifyApi api;

    Classify(String apiKey, String basePath, String retinaName) {
        super(retinaName);

        if (isBlank(apiKey)) {
            throw new IllegalArgumentException(NULL_API_KEY_MSG);
        }

        if (isBlank(basePath)) {
            throw new IllegalArgumentException(NULL_BASE_PATH_MSG);
        }
        
        this.api = new ClassifyApi(apiKey);
        this.api.setBasePath(basePath);
    }

    Classify(final ClassifyApi api, String retinaName) {
        super(retinaName);
        this.api = api;
    }
    
    /**
     * Endpoint for creating a {@link CategoryFilter} from text inputs.
     * 
     * @param filterName            the name of the category filter
     * @param positiveExamples      list of strings illustrating positive examples.
     * @param negativeExamples      list of strings illustrating negative examples.
     * @return {@link CategoryFilter}
     * @throws ApiException 
     */
    public CategoryFilter createCategoryFilter(String filterName, List<String> positiveExamples, 
        List<String> negativeExamples) throws ApiException {
        if (isEmpty(filterName) || positiveExamples == null || positiveExamples.isEmpty()) {
            throw new IllegalArgumentException(NULL_TEXT_MSG);
        }
        
        Sample sample = makeSample(positiveExamples, negativeExamples);
        
        String json = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.setSerializationInclusion(Include.NON_NULL);
            json = mapper.writeValueAsString(sample);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        
        return this.api.createCategoryFilter(filterName, json, retinaName);
    }
}
