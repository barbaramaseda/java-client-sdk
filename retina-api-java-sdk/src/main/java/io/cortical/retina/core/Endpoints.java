/*******************************************************************************
 * Copyright (c) cortical.io GmbH. All rights reserved.
 *  
 * This software is confidential and proprietary information.
 * You shall use it only in accordance with the terms of the
 * license agreement you entered into with cortical.io GmbH.
 ******************************************************************************/
package io.cortical.retina.core;

import static io.cortical.retina.rest.RestServiceConstants.NULL_API_KEY_MSG;
import static io.cortical.retina.rest.RestServiceConstants.NULL_RETINA_MSG;
import static io.cortical.retina.rest.RestServiceConstants.NULL_SERVER_IP_MSG;
import static org.apache.commons.lang3.StringUtils.isEmpty;
import io.cortical.retina.model.Retina;
import io.cortical.retina.rest.ApiException;
import io.cortical.retina.rest.RetinasApi;

import java.util.List;


/**
 * RetinaApis factory.
 * 
 */
public class Endpoints {
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
     * Creates a new instance of {@link Endpoints}.
     * 
     * @param retinaName
     * @param url
     * @param apiKey
     */
    public Endpoints(final String retinaName, String url, String apiKey) {
        String basePath = generateBasepath("http://", url, 80, "/rest");
        
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
     * Creates a new instance of {@link Endpoints}.
     * 
     * @param retinaName    the name of the retina (i.e. en_associative, en_synonymous)
     * @param protocol      the access protocol (i.e. http, ftp, sftp, https etc.)
     * @param ip            the ip address or domain
     * @param port          the port
     * @param apiKey        your user api key
     */
    public Endpoints(final String retinaName, String protocol, String ip, int port, String path, String apiKey) {
        String basePath = generateBasepath(protocol, ip, port, path);
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
    public Compare compareApi() {
        return compareApi;
    }
    
    /**
     * Returns the request proxy for the {@link Compare} endpoint
     * @return
     */
    public Expressions expressionsApi() {
        return expressionsApi;
    }
    
    /**
     * Returns the request proxy for the {@link Images} endpoint
     * @return
     */
    public Images imageApi() {
        return imageApi;
    }
    
    /**
     * Returns the request proxy for the {@link Terms} endpoint
     * @return
     */
    public Terms termsApi() {
        return termsApi;
    }
    
    /**
     * Returns the request proxy for the {@link Texts} endpoint
     * @return
     */
    public Texts textApi() {
        return textApi;
    }
    
    /**
     * Returns the request proxy for the {@link Classify} endpoint
     * @return
     */
    public Classify classifyApi() {
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
    
    /**
     * Generate the base path for the retina.
     * 
     * @param ip : retina server ip.
     * @param port : retina service port. 
     * @return : the retina's API base path.
     */
    public static String generateBasepath(String protocol, String ip, int port, String path) {
        if (isEmpty(ip)) {
            throw new IllegalArgumentException(NULL_SERVER_IP_MSG);
        }
        if (port == -1) {
            port = 80;
        }
        StringBuilder basePath = new StringBuilder();
        basePath.append(protocol).append(ip).append(":").append(port).append(path);
        return basePath.toString();
    }
    
    //////////////////////////////////
    //      Test Code Only          //
    //////////////////////////////////
    /*
     * Non-client code for testing only. Do not use!
     * @return
     */
    static Endpoints makeTestRetinas(RetinasApi api) {
        return new Endpoints(api);
    }
    
    /* For testing */
    private Endpoints(RetinasApi api) {
        compareApi = null;
        expressionsApi = null;
        imageApi = null;
        termsApi = null;
        textApi = null;
        classifyApi = null;
        retinasApi = api;
    }
}
