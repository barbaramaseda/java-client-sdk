/*******************************************************************************
 * Copyright (c) CEPT Systems GmbH. All rights reserved.
 *
 * This software is confidential and proprietary information.
 * You shall use it only in accordance with the terms of the
 * license agreement you entered into with CEPT Systems GmbH.
 ******************************************************************************/
package io.cortical.services;

import io.cortical.rest.model.CategoryFilter;
import io.cortical.rest.model.FilterTrainingObject;
import io.cortical.services.api.client.ApiException;

public interface Classify {

    /**
     * Endpoint for creating a category filter from text inputs.
     *
     * @param filter_name : the category name.
     * @param body : an object representing the positive and negative training samples.
     * @return : CategoryFilter an object representing the category to be filtered for.
     * @throws io.cortical.services.api.client.ApiException If the input is null.
     */
    public CategoryFilter createCategoryFilter (String filter_name, FilterTrainingObject body) throws ApiException;
}
