/*******************************************************************************
 * Copyright (c) cortical.io GmbH. All rights reserved.
 *
 * This software is confidential and proprietary information.
 * You shall use it only in accordance with the terms of the
 * license agreement you entered into with cortical.io GmbH.
 ******************************************************************************/
package io.cortical.services;

import io.cortical.rest.model.CategoryFilter;
import io.cortical.rest.model.FilterTrainingObject;
import io.cortical.services.api.client.ApiException;
import io.cortical.services.api.client.api.ClassifyApi;
import org.apache.commons.logging.Log;
import static io.cortical.rest.RestServiceConstants.NULL_API_KEY_MSG;
import static io.cortical.rest.RestServiceConstants.NULL_BASE_PATH_MSG;
import static io.cortical.rest.RestServiceConstants.NULL_TEXT_MSG;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isEmpty;
import static org.apache.commons.logging.LogFactory.getLog;


public class ClassifyRetinaApiImpl extends BaseRetinaApi implements Classify {

    /**
     *
     */
    private static final Log LOG = getLog(ClassifyRetinaApiImpl.class);
    /**
     *
     */
    private final ClassifyApi api;

    ClassifyRetinaApiImpl(String apiKey, String basePath, String retinaName) {
        super(retinaName);

        if (isBlank(apiKey)) {
            throw new IllegalArgumentException(NULL_API_KEY_MSG);
        }

        if (isBlank(basePath)) {
            throw new IllegalArgumentException(NULL_BASE_PATH_MSG);
        }
        LOG.info("Initialize Classify Retina Api with retina: " + retinaName);
        this.api = new ClassifyApi(apiKey);
        this.api.setBasePath(basePath);
    }

    ClassifyRetinaApiImpl(final ClassifyApi api, String retinaName) {
        super(retinaName);
        this.api = api;
    }

    /** {@inheritDoc}  */
    @Override
    public CategoryFilter createCategoryFilter(String filter_name, FilterTrainingObject body) throws ApiException {
        if (isEmpty(filter_name) || body ==null || (body.getNegativeExamples() == null && body.getPositiveExamples()==null)) {
            throw new IllegalArgumentException(NULL_TEXT_MSG);
        }
        LOG.debug("Retrieve CategoryFilter: " + body);
        return this.api.createCategoryFilter(filter_name, body, retinaName);
    }
}
