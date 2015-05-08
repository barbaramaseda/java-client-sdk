/*******************************************************************************
 * Copyright (c) CEPT Systems GmbH. All rights reserved.
 *
 * This software is confidential and proprietary information.
 * You shall use it only in accordance with the terms of the
 * license agreement you entered into with CEPT Systems GmbH.
 ******************************************************************************/
package io.cortical.services;

import io.cortical.rest.model.CategoryFilter;
import io.cortical.services.api.client.ApiException;

public interface Classify {

    /**
     * Endpoint for creating a {@link CategoryFilter} from text inputs.
     * 
     * @param name the name of the category filter
     * @param ftostring the json representation of a {@link FilterTrainingObject)
     * @return {@link CategoryFilter}
     * @throws ApiException 
     */
    public CategoryFilter createCategoryFilter(String name, String ftostring) throws ApiException;
}
