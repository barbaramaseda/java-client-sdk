/*******************************************************************************
 * Copyright (c) cortical.io GmbH. All rights reserved.
 *  
 * This software is confidential and proprietary information.
 * You shall use it only in accordance with the terms of the
 * license agreement you entered into with cortical.io GmbH.
 ******************************************************************************/
package io.cortical.retina.core;

import static io.cortical.retina.service.RestServiceConstants.NULL_API_KEY_MSG;
import static io.cortical.retina.service.RestServiceConstants.NULL_RETINA_MSG;
import static org.apache.commons.lang3.StringUtils.isEmpty;
import io.cortical.retina.model.Retina;
import io.cortical.retina.service.ApiException;
import io.cortical.retina.service.RetinasApi;

import java.util.List;


/**
 * RetinaApis factory.
 * 
 */
public class Retinas {
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
        
        compareApi = new Compare(retinaName, basePath, apiKey);
        expressionsApi = new Expressions(apiKey, basePath, retinaName);
        imageApi = new Images(apiKey, basePath, retinaName);
        termsApi = new Terms(apiKey, basePath, retinaName);
        textApi = new Texts(apiKey, basePath, retinaName);
        classifyApi = new Classify(apiKey, basePath, retinaName);
        retinasApi = new RetinasApi(apiKey);
    }
    
    /**
     * Returns the request proxy for the {@link Compare} endpoint
     * @return
     */
    public final Compare compareApi() {
        return compareApi;
    }
    
    /**
     * Returns the request proxy for the {@link Compare} endpoint
     * @return
     */
    public final Expressions expressionsApi() {
        return expressionsApi;
    }
    
    /**
     * Returns the request proxy for the {@link Images} endpoint
     * @return
     */
    public final Images imageApi() {
        return imageApi;
    }
    
    /**
     * Returns the request proxy for the {@link Terms} endpoint
     * @return
     */
    public final Terms termsApi() {
        return termsApi;
    }
    
    /**
     * Returns the request proxy for the {@link Texts} endpoint
     * @return
     */
    public final Texts textApi() {
        return textApi;
    }
    
    /**
     * Returns the request proxy for the {@link Classify} endpoint
     * @return
     */
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
    
    
    //////////////////////////////////
    //      Test Code Only          //
    //////////////////////////////////
    /*
     * Non-client code for testing only. Do not use!
     * @return
     */
    static Retinas makeTestRetinas(RetinasApi api) {
        return new Retinas(api);
    }
    
    /* For testing */
    private Retinas(RetinasApi api) {
        compareApi = null;
        expressionsApi = null;
        imageApi = null;
        termsApi = null;
        textApi = null;
        classifyApi = null;
        retinasApi = api;
    }
}
