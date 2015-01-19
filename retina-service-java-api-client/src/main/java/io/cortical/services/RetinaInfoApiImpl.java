/*******************************************************************************
 * Copyright (c) cortical.io GmbH. All rights reserved.
 *  
 * This software is confidential and proprietary information.
 * You shall use it only in accordance with the terms of the
 * license agreement you entered into with cortical.io GmbH.
 ******************************************************************************/
package io.cortical.services;

import io.cortical.rest.model.Retina;
import io.cortical.services.api.client.ApiException;
import io.cortical.services.api.client.api.RetinasApi;
import java.util.List;
import static io.cortical.rest.RestServiceConstants.NULL_API_KEY_MSG;
import static io.cortical.rest.RestServiceConstants.NULL_BASE_PATH_MSG;
import static io.cortical.services.RetinaApiUtils.generateBasepath;
import static org.apache.commons.lang3.StringUtils.isBlank;



/**
 * 
 * A Retina Info API implementation.  
 */
class RetinaInfoApiImpl implements Retinas {
    /**
     * 
     */
    private final RetinasApi api;
    /**
     * Creates a new instance of {@link RetinaInfoApiImpl}
     * 
     * @param apiKey
     * @param basePath
     */
    RetinaInfoApiImpl(final String ip, String apiKey){
        String basePath =  generateBasepath(ip, null);
        if (isBlank(apiKey)) {
            throw new IllegalArgumentException(NULL_API_KEY_MSG);
        }
        
        if (isBlank(basePath)) {
            throw new IllegalArgumentException(NULL_BASE_PATH_MSG);
        }
        this.api = new RetinasApi(apiKey);
        api.setBasePath(basePath);
        
    }
    
    RetinaInfoApiImpl(final String ip, final Short port, String apiKey) {
        String basePath =  generateBasepath(ip, port);
        if (isBlank(apiKey)) {
            throw new IllegalArgumentException(NULL_API_KEY_MSG);
        }
        
        if (isBlank(basePath)) {
            throw new IllegalArgumentException(NULL_BASE_PATH_MSG);
        }
        this.api = new RetinasApi(apiKey);
        api.setBasePath(basePath);
    }
    
    
    RetinaInfoApiImpl(RetinasApi api) {
        this.api = api;
    }
    
    
    /** {@inheritDoc} */
    @Override
    public List<Retina> getAllRetinas() throws ApiException {
        return api.getRetinas(null);
    }
    /** {@inheritDoc}*/
    @Override
    public Retina retinaByName(String name) throws ApiException {
        List<Retina> retinas = api.getRetinas(name);
        if (retinas == null || retinas.size() == 0) {
            return null;
        }
        return retinas.get(0);
    }
    
}
