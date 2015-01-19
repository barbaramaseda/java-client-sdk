/*******************************************************************************
 * Copyright (c) cortical.io GmbH. All rights reserved.
 *  
 * This software is confidential and proprietary information.
 * You shall use it only in accordance with the terms of the
 * license agreement you entered into with cortical.io GmbH.
 ******************************************************************************/
package io.cortical.services;

import org.apache.commons.logging.Log;
import static io.cortical.rest.RestServiceConstants.NULL_API_KEY_MSG;
import static io.cortical.rest.RestServiceConstants.NULL_RETINA_MSG;
import static io.cortical.services.RetinaApiUtils.generateBasepath;
import static org.apache.commons.lang3.StringUtils.isEmpty;
import static org.apache.commons.logging.LogFactory.getLog;


/**
 * RetinaApis factory.
 * 
 */
public class RetinaApis {
    /**
     * LOGGER.
     */
    private static final Log LOG = getLog(RetinaApis.class);
    
    /**
     * The APIs
     */
    private final Compare compareApi;
    private final Expressions expressionsApi;
    private final Images imageApi;
    private final Terms termsApi;
    private final Texts textApi;
    
    /**
     * Creates a new instance of {@link RetinaApis}.
     * 
     * @param retinaName
     * @param ip
     * @param apiKey
     */
    public RetinaApis(final String retinaName, final String ip, final String apiKey) {
        String basePath = generateBasepath(ip, null);
        if (isEmpty(retinaName)) {
            throw new IllegalArgumentException(NULL_RETINA_MSG);
        }
        if (isEmpty(apiKey)) {
            throw new IllegalArgumentException(NULL_API_KEY_MSG);
        }
        LOG.info("Initialize retina API for retina name: " + retinaName);
        compareApi = new CompareRetinaApiImpl(retinaName, basePath, apiKey);
        expressionsApi = new ExpressionsRetinaApiImpl(apiKey, basePath, retinaName);
        imageApi = new ImageRetinaApiImpl(apiKey, basePath, retinaName);
        termsApi = new TermsRetinaApiImpl(apiKey, basePath, retinaName);
        textApi = new TextRetinaApiImpl(apiKey, basePath, retinaName);
    }
    
    
    /**
     * Creates a new instance of {@link RetinaApis}.
     * 
     * @param retinaName
     * @param ip
     * @param port
     * @param apiKey
     */
    public RetinaApis(final String retinaName, final String ip, final Short port, final String apiKey) {
        String basePath = generateBasepath(ip, port);
        if (isEmpty(retinaName)) {
            throw new IllegalArgumentException(NULL_RETINA_MSG);
        }
        if (isEmpty(apiKey)) {
            throw new IllegalArgumentException(NULL_API_KEY_MSG);
        }
        LOG.info("Initialize retina API for retina name: " + retinaName);
        compareApi = new CompareRetinaApiImpl(retinaName, basePath, apiKey);
        expressionsApi = new ExpressionsRetinaApiImpl(apiKey, basePath, retinaName);
        imageApi = new ImageRetinaApiImpl(apiKey, basePath, retinaName);
        termsApi = new TermsRetinaApiImpl(apiKey, basePath, retinaName);
        textApi = new TextRetinaApiImpl(apiKey, basePath, retinaName);
    }
    
    
    public final Compare compareApi() {
        return compareApi;
    }
    
    public final Expressions expressionsApi() {
        return expressionsApi;
    }
    
    public final Images imageApi() {
        return imageApi;
    }
    
    public final Terms termsApi() {
        return termsApi;
    }
    
    public final Texts textApi() {
        return textApi;
    }
    
    public static Retinas getInfo(final String ip, final String apiKey) {
        return new RetinaInfoApiImpl(ip, apiKey);
    }
    
    public static Retinas getInfo(final String ip, final Short port, final String apiKey) {
        return new RetinaInfoApiImpl(ip, port, apiKey);
    }
}
