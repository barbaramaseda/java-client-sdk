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
import static io.cortical.retina.service.RestServiceConstants.NULL_TEXT_MSG;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isEmpty;
import io.cortical.retina.model.CategoryFilter;
import io.cortical.retina.service.ApiException;
import io.cortical.retina.service.ClassifyApi;


public class Classify extends AbstractRetinas {

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
     * @param name the name of the category filter
     * @param ftostring the json representation of a {@link FilterTrainingObject)
     * @return {@link CategoryFilter}
     * @throws ApiException 
     */
    public CategoryFilter createCategoryFilter(String filter_name, String body) throws ApiException {
        if (isEmpty(filter_name) || body ==null) {
            throw new IllegalArgumentException(NULL_TEXT_MSG);
        }
        
        return this.api.createCategoryFilter(filter_name, body, retinaName);
    }
}
