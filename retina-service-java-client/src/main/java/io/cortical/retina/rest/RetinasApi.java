/*******************************************************************************
 * Copyright (c) cortical.io GmbH. All rights reserved.
 *  
 * This software is confidential and proprietary information.
 * You shall use it only in accordance with the terms of the
 * license agreement you entered into with cortical.io GmbH.
 ******************************************************************************/
package io.cortical.retina.rest;

import io.cortical.retina.model.Retina;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** Generated. **/
public class RetinasApi {
    private String basePath = "http://api.cortical.io/rest";
    /* replace the value of key with your api-key */
    @SuppressWarnings("unused")
    private String key = "replaceWithRetinaAPIKey";
    private ApiInvoker apiInvoker;

    /** Generated. **/
    public RetinasApi(String apiKey) {
        apiInvoker = ApiInvoker.getInstance();
        this.key = apiKey;
        apiInvoker.addDefaultHeader("api-key", apiKey);    
    }

    /** Generated. 
    *@return {@link ApiInvoker}
    **/
    public ApiInvoker getInvoker() {
        return apiInvoker;
    }
    
    /** Generated.
    *@param basePath the path to set
    **/
    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }
    
    /** Generated.
    *@return String
    **/
    public String getBasePath() {
        return basePath;
    }

    /** Generated. 
    *@throws ApiException if an error occurs during querying of the API.
    *@return List<Retina> **/
    public List<Retina> getRetinas (String retina_name) throws ApiException {
        // create path and map variables
        String path = "/retinas".replaceAll("\\{format\\}","json");

        // query params
        Map<String, String> queryParams = new HashMap<String, String>();
        Map<String, String> headerParams = new HashMap<String, String>();

        if(!"null".equals(String.valueOf(retina_name)))
            queryParams.put("retina_name", String.valueOf(retina_name));
        String contentType = "application/json";

        try {
            Object response = apiInvoker.invokeAPI(basePath, path, "GET", queryParams
                , null, headerParams, contentType);
            if(response != null) {
                if (response instanceof String) {
                    @SuppressWarnings("unchecked")
                    List<Retina> result = (List<Retina>) ApiInvoker.deserialize((String) response,
                        "Array", Retina.class, null);
                    return result;
                }
                else if (response instanceof java.io.ByteArrayInputStream) {
                    @SuppressWarnings("unchecked")
                    List<Retina> result = (List<Retina>)response;
                    return result;
                }
                
            }
            else {
                return null;
            }
        } catch (ApiException ex) {
            if(ex.getCode() == 404) {
            	return null;
            }
            else {
                throw ex;
            }
        }
        return null;}
    }

