/*******************************************************************************
 * Copyright (c) cortical.io GmbH. All rights reserved.
 *  
 * This software is confidential and proprietary information.
 * You shall use it only in accordance with the terms of the
 * license agreement you entered into with cortical.io GmbH.
 ******************************************************************************/
package io.cortical.retina.client.core;

import io.cortical.retina.model.Retina;
import io.cortical.retina.service.ApiException;
import io.cortical.retina.service.RetinasApi;

import java.util.List;

import org.apache.commons.logging.Log;

import static io.cortical.retina.rest.RestServiceConstants.NULL_API_KEY_MSG;
import static io.cortical.retina.rest.RestServiceConstants.NULL_RETINA_MSG;
import static org.apache.commons.lang3.StringUtils.isEmpty;
import static org.apache.commons.logging.LogFactory.getLog;


/**
 * RetinaApis factory.
 * 
 */
public class Retinas {
    /**
     * LOGGER.
     */
    private static final Log LOG = getLog(Retinas.class);
    
    /**
     * The APIs
     */
    private final Compare compareApi;
    private final Expressions expressionsApi;
    private final Images imageApi;
    private final Terms termsApi;
    private final Texts textApi;
    private final Classify classifyApi;
    
    private final RetinasApi retinasApi;
    
    /**
     * Creates a new instance of {@link Retinas}.
     * 
     * @param retinaName
     * @param ip
     * @param apiKey
     */
    public Retinas(final String retinaName, final String ip, final String apiKey) {
        String basePath = RetinaUtils.generateBasepath(ip, null);
        if (isEmpty(retinaName)) {
            throw new IllegalArgumentException(NULL_RETINA_MSG);
        }
        if (isEmpty(apiKey)) {
            throw new IllegalArgumentException(NULL_API_KEY_MSG);
        }
        LOG.info("Initialize retina API for retina name: " + retinaName);
        compareApi = new Compare(retinaName, basePath, apiKey);
        expressionsApi = new Expressions(apiKey, basePath, retinaName);
        imageApi = new Images(apiKey, basePath, retinaName);
        termsApi = new Terms(apiKey, basePath, retinaName);
        textApi = new Texts(apiKey, basePath, retinaName);
        classifyApi = new Classify(apiKey, basePath, retinaName);
        retinasApi = new RetinasApi(apiKey);
    }
    
    
    /**
     * Creates a new instance of {@link Retinas}.
     * 
     * @param retinaName
     * @param ip
     * @param port
     * @param apiKey
     */
    public Retinas(final String retinaName, final String ip, final Short port, final String apiKey) {
        String basePath = RetinaUtils.generateBasepath(ip, port);
        if (isEmpty(retinaName)) {
            throw new IllegalArgumentException(NULL_RETINA_MSG);
        }
        if (isEmpty(apiKey)) {
            throw new IllegalArgumentException(NULL_API_KEY_MSG);
        }
        LOG.info("Initialize retina API for retina name: " + retinaName);
        compareApi = new Compare(retinaName, basePath, apiKey);
        expressionsApi = new Expressions(apiKey, basePath, retinaName);
        imageApi = new Images(apiKey, basePath, retinaName);
        termsApi = new Terms(apiKey, basePath, retinaName);
        textApi = new Texts(apiKey, basePath, retinaName);
        classifyApi = new Classify(apiKey, basePath, retinaName);
        retinasApi = new RetinasApi(apiKey);
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
    
    public final Classify classifyApi() {
        return classifyApi;
    }
    
    /**
     * Retrieve all available retinas.
     * @return all available retinas.
     */
    public List<Retina> getAllRetinas() throws ApiException {
        return retinasApi.getRetinas(null);
    }
    
    /**
     * Find retina by name.
     * @param name : the retina's name.
     * 
     * @return retina found by name or null if there is no such retina.
     */
    public Retina retinaByName(String name) throws ApiException {
        List<Retina> retinas = retinasApi.getRetinas(name);
        if (retinas == null || retinas.size() == 0) {
            return null;
        }
        return retinas.get(0);
    }
}