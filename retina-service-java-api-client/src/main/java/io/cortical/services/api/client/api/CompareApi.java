/*******************************************************************************
 * Copyright (c) cortical.io GmbH. All rights reserved.
 *  
 * This software is confidential and proprietary information.
 * You shall use it only in accordance with the terms of the
 * license agreement you entered into with cortical.io GmbH.
 ******************************************************************************/
package io.cortical.services.api.client.api;

import io.cortical.rest.model.Metric;
import io.cortical.services.api.client.ApiException;
import io.cortical.services.api.client.ApiInvoker;
import java.util.HashMap;
import java.util.Map;


/** Generated. **/
public class CompareApi {
    private String basePath = "http://api.cortical.io/rest";
    /* replace the value of key with your api-key */
    private String key = "replaceWithRetinaAPIKey";
    private ApiInvoker apiInvoker;
    
    /** Generated. **/
    public CompareApi(String apiKey) {
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
    *@return Metric **/
    public Metric compare(String body, String retina_name) throws ApiException {
        // verify required params are set
        if (retina_name == null || body == null) {
            throw new ApiException(400, "missing required params");
        }
        // create path and map variables
        String path = "/compare".replaceAll("\\{format\\}", "json");
        
        // query params
        Map<String, String> queryParams = new HashMap<String, String>();
        Map<String, String> headerParams = new HashMap<String, String>();
        
        if (!"null".equals(String.valueOf(retina_name)))
            queryParams.put("retina_name", String.valueOf(retina_name));
        String contentType = "application/json";
        
        try {
            Object response =
                    apiInvoker.invokeAPI(basePath, path, "POST", queryParams, body, headerParams, contentType);
            if (response != null) {
                if (response instanceof String) {
                    Metric result =
                            (Metric) ApiInvoker.deserialize((String) response, "", Metric.class, null);
                    return result;
                }
                else if (response instanceof java.io.ByteArrayInputStream) {
                     Metric result = (Metric) response;
                    return result;
                }
                
            }
            else {
                return null;
            }
        }
        catch (ApiException ex) {
            if (ex.getCode() == 404) {
                return null;
            }
            else {
                throw ex;
            }
        }
        return null;
    }
    
    /** Generated.
     * @param retina_name : name of retina.
     *@throws ApiException if an error occurs during querying of the API.
     *@return Metric **/
    public Metric[] compareBulk(String body, String retina_name) throws ApiException {
        // verify required params are set
        if (retina_name == null || body == null) {
            throw new ApiException(400, "missing required params");
        }
        // create path and map variables
        String path = "/compare/bulk".replaceAll("\\{format\\}", "json");
        
        // query params
        Map<String, String> queryParams = new HashMap<String, String>();
        Map<String, String> headerParams = new HashMap<String, String>();
        
        if (!"null".equals(String.valueOf(retina_name)))
            queryParams.put("retina_name", String.valueOf(retina_name));
        String contentType = "application/json";
        
        try {
            Object response =
                    apiInvoker.invokeAPI(basePath, path, "POST", queryParams, body, headerParams, contentType);
            if (response != null) {
                if (response instanceof String) {
                    Metric[] result =
                             (Metric[]) ApiInvoker.deserialize((String) response, "", Metric[].class, null);
                    return result;
                }
                else if (response instanceof java.io.ByteArrayInputStream) {
                    Metric[] result = ( Metric[]) response;
                    return result;
                }
                
            }
            else {
                return null;
            }
        }
        catch (ApiException ex) {
            if (ex.getCode() == 404) {
                return null;
            }
            else {
                throw ex;
            }
        }
        return null;
    }
}
