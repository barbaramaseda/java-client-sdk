/*******************************************************************************
 * Copyright (c) cortical.io GmbH. All rights reserved.
 *  
 * This software is confidential and proprietary information.
 * You shall use it only in accordance with the terms of the
 * license agreement you entered into with cortical.io GmbH.
 ******************************************************************************/
package io.cortical.services.api.client.api;

import io.cortical.rest.model.Fingerprint;
import io.cortical.services.api.client.ApiException;
import io.cortical.services.api.client.ApiInvoker;
import java.util.HashMap;
import java.util.Map;

/** Generated. **/
public class FingerprintsApi {
    private String basePath = "http://api.cortical.io/rest";
    /* replace the value of key with your api-key */
    private String key = "replaceWithRetinaAPIKey";
    private ApiInvoker apiInvoker;

    /** Generated. **/
    public FingerprintsApi(String apiKey) {
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
    *@return Fingerprint **/
    public Fingerprint getFingerprint (String retina_name, String fingerprint_id) throws ApiException {
        // verify required params are set
        if(retina_name == null || fingerprint_id == null ) {
             throw new ApiException(400, "missing required params");
        }
        // create path and map variables
        String path = "/fingerprints".replaceAll("\\{format\\}","json");

        // query params
        Map<String, String> queryParams = new HashMap<String, String>();
        Map<String, String> headerParams = new HashMap<String, String>();

        if(!"null".equals(String.valueOf(retina_name)))
            queryParams.put("retina_name", String.valueOf(retina_name));
        if(!"null".equals(String.valueOf(fingerprint_id)))
            queryParams.put("fingerprint_id", String.valueOf(fingerprint_id));
        String contentType = "application/json";

        try {
            Object response = apiInvoker.invokeAPI(basePath, path, "GET", queryParams
                , null, headerParams, contentType);
            if(response != null) {
                if (response instanceof String) {
                        @SuppressWarnings("unchecked")
                        Fingerprint result = (Fingerprint) ApiInvoker.deserialize( (String) response, ""
                                , Fingerprint.class, null);return result;
                }
                else if (response instanceof java.io.ByteArrayInputStream) {
                        @SuppressWarnings("unchecked")
                        Fingerprint result =    (Fingerprint) response;return result;
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
    /** Generated. 
    *@throws ApiException if an error occurs during querying of the API.
    *@return Fingerprint **/
    public Fingerprint getLogicalFingerprint (String retina_name, String logical_name) throws ApiException {
        // verify required params are set
        if(retina_name == null || logical_name == null ) {
             throw new ApiException(400, "missing required params");
        }
        // create path and map variables
        String path = "/fingerprints/byName".replaceAll("\\{format\\}","json");

        // query params
        Map<String, String> queryParams = new HashMap<String, String>();
        Map<String, String> headerParams = new HashMap<String, String>();

        if(!"null".equals(String.valueOf(retina_name)))
            queryParams.put("retina_name", String.valueOf(retina_name));
        if(!"null".equals(String.valueOf(logical_name)))
            queryParams.put("logical_name", String.valueOf(logical_name));
        String contentType = "application/json";

        try {
            Object response = apiInvoker.invokeAPI(basePath, path, "GET", queryParams
                , null, headerParams, contentType);
            if(response != null) {
                if (response instanceof String) {
                        @SuppressWarnings("unchecked")
                        Fingerprint result = (Fingerprint) ApiInvoker.deserialize( (String) response, ""
                                , Fingerprint.class, null);return result;
                }
                else if (response instanceof java.io.ByteArrayInputStream) {
                        @SuppressWarnings("unchecked")
                        Fingerprint result =    (Fingerprint) response;return result;
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
    /** Generated. 
    *@throws ApiException if an error occurs during querying of the API.
    **/
    public void updateFingerprint (String retina_name, String fingerprint_id, String name) throws ApiException {
        // verify required params are set
        if(retina_name == null || fingerprint_id == null || name == null ) {
             throw new ApiException(400, "missing required params");
        }
        // create path and map variables
        String path = "/fingerprints/update".replaceAll("\\{format\\}","json");

        // query params
        Map<String, String> queryParams = new HashMap<String, String>();
        Map<String, String> headerParams = new HashMap<String, String>();

        if(!"null".equals(String.valueOf(retina_name)))
            queryParams.put("retina_name", String.valueOf(retina_name));
        if(!"null".equals(String.valueOf(fingerprint_id)))
            queryParams.put("fingerprint_id", String.valueOf(fingerprint_id));
        if(!"null".equals(String.valueOf(name)))
            queryParams.put("name", String.valueOf(name));
        String contentType = "application/json";

        try {
            Object response = apiInvoker.invokeAPI(basePath, path, "PUT", queryParams
                , null, headerParams, contentType);
            if(response != null) {
                if (response instanceof String) {
                        return ;
                }
                else if (response instanceof java.io.ByteArrayInputStream) {
                        return ;
                }
                
            }
            else {
                return ;
            }
        } catch (ApiException ex) {
            if(ex.getCode() == 404) {
            	return ;
            }
            else {
                throw ex;
            }
        }
        }
    /** Generated. 
    *@throws ApiException if an error occurs during querying of the API.
    **/
    public void deleteFingerprint (String retina_name, String fingerprint_id) throws ApiException {
        // verify required params are set
        if(retina_name == null || fingerprint_id == null ) {
             throw new ApiException(400, "missing required params");
        }
        // create path and map variables
        String path = "/fingerprints/delete".replaceAll("\\{format\\}","json");

        // query params
        Map<String, String> queryParams = new HashMap<String, String>();
        Map<String, String> headerParams = new HashMap<String, String>();

        if(!"null".equals(String.valueOf(retina_name)))
            queryParams.put("retina_name", String.valueOf(retina_name));
        if(!"null".equals(String.valueOf(fingerprint_id)))
            queryParams.put("fingerprint_id", String.valueOf(fingerprint_id));
        String contentType = "application/json";

        try {
            Object response = apiInvoker.invokeAPI(basePath, path, "DELETE", queryParams
                , null, headerParams, contentType);
            if(response != null) {
                if (response instanceof String) {
                        return ;
                }
                else if (response instanceof java.io.ByteArrayInputStream) {
                        return ;
                }
                
            }
            else {
                return ;
            }
        } catch (ApiException ex) {
            if(ex.getCode() == 404) {
            	return ;
            }
            else {
                throw ex;
            }
        }
        }
    }


