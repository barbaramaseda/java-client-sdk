/*******************************************************************************
 * Copyright (c) cortical.io GmbH. All rights reserved.
 *  
 * This software is confidential and proprietary information.
 * You shall use it only in accordance with the terms of the
 * license agreement you entered into with cortical.io GmbH.
 ******************************************************************************/
package io.cortical.services.api.client.api;

import io.cortical.rest.model.Image;
import io.cortical.services.api.client.ApiException;
import io.cortical.services.api.client.ApiInvoker;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** Generated. **/
public class ImageApi {
    private String basePath = "http://api.cortical.io/rest";
    /* replace the value of key with your api-key */
    private String key = "replaceWithRetinaAPIKey";
    private ApiInvoker apiInvoker;

    /** Generated. **/
    public ImageApi(String apiKey) {
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
    *@return java.io.ByteArrayInputStream **/
    public java.io.ByteArrayInputStream getImageForExpression (String body, String retina_name, Integer image_scalar, String plot_shape, String image_encoding, Double sparsity) throws ApiException {
        // verify required params are set
        if(retina_name == null || body == null ) {
             throw new ApiException(400, "missing required params");
        }
        // create path and map variables
        String path = "/image".replaceAll("\\{format\\}","json");

        // query params
        Map<String, String> queryParams = new HashMap<String, String>();
        Map<String, String> headerParams = new HashMap<String, String>();

        if(!"null".equals(String.valueOf(retina_name)))
            queryParams.put("retina_name", String.valueOf(retina_name));
        if(!"null".equals(String.valueOf(image_scalar)))
            queryParams.put("image_scalar", String.valueOf(image_scalar));
        if(!"null".equals(String.valueOf(plot_shape)))
            queryParams.put("plot_shape", String.valueOf(plot_shape));
        if(!"null".equals(String.valueOf(image_encoding)))
            queryParams.put("image_encoding", String.valueOf(image_encoding));
        if(!"null".equals(String.valueOf(sparsity)))
            queryParams.put("sparsity", String.valueOf(sparsity));
        String contentType = "application/json";

        try {
            Object response = apiInvoker.invokeAPI(basePath, path, "POST", queryParams
                , body, headerParams, contentType);
            if(response != null) {
                if (response instanceof String) {
                        @SuppressWarnings("unchecked")
                        java.io.ByteArrayInputStream result = (java.io.ByteArrayInputStream) ApiInvoker.deserialize( (String) response, ""
                                , java.io.ByteArrayInputStream.class, null);return result;
                }
                else if (response instanceof java.io.ByteArrayInputStream) {
                        @SuppressWarnings("unchecked")
                        java.io.ByteArrayInputStream result =    (java.io.ByteArrayInputStream) response;return result;
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
    *@return java.io.ByteArrayInputStream **/
    public java.io.ByteArrayInputStream getOverlayImage (String body, String retina_name, String plot_shape, Integer image_scalar, String image_encoding) throws ApiException {
        // verify required params are set
        if(retina_name == null || body == null ) {
             throw new ApiException(400, "missing required params");
        }
        // create path and map variables
        String path = "/image/compare".replaceAll("\\{format\\}","json");

        // query params
        Map<String, String> queryParams = new HashMap<String, String>();
        Map<String, String> headerParams = new HashMap<String, String>();

        if(!"null".equals(String.valueOf(retina_name)))
            queryParams.put("retina_name", String.valueOf(retina_name));
        if(!"null".equals(String.valueOf(plot_shape)))
            queryParams.put("plot_shape", String.valueOf(plot_shape));
        if(!"null".equals(String.valueOf(image_scalar)))
            queryParams.put("image_scalar", String.valueOf(image_scalar));
        if(!"null".equals(String.valueOf(image_encoding)))
            queryParams.put("image_encoding", String.valueOf(image_encoding));
        String contentType = "application/json";

        try {
            Object response = apiInvoker.invokeAPI(basePath, path, "POST", queryParams
                , body, headerParams, contentType);
            if(response != null) {
                if (response instanceof String) {
                        @SuppressWarnings("unchecked")
                        java.io.ByteArrayInputStream result = (java.io.ByteArrayInputStream) ApiInvoker.deserialize( (String) response, ""
                                , java.io.ByteArrayInputStream.class, null);return result;
                }
                else if (response instanceof java.io.ByteArrayInputStream) {
                        @SuppressWarnings("unchecked")
                        java.io.ByteArrayInputStream result =    (java.io.ByteArrayInputStream) response;return result;
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
    *@return List<Image> **/
    public List<Image> getImageForBulkExpressions (String body, Boolean get_fingerprint, String retina_name, Integer image_scalar, String plot_shape, Double sparsity) throws ApiException {
        // verify required params are set
        if(retina_name == null || body == null ) {
             throw new ApiException(400, "missing required params");
        }
        // create path and map variables
        String path = "/image/bulk".replaceAll("\\{format\\}","json");

        // query params
        Map<String, String> queryParams = new HashMap<String, String>();
        Map<String, String> headerParams = new HashMap<String, String>();

        if(!"null".equals(String.valueOf(retina_name)))
            queryParams.put("retina_name", String.valueOf(retina_name));
        if(!"null".equals(String.valueOf(image_scalar)))
            queryParams.put("image_scalar", String.valueOf(image_scalar));
        if(!"null".equals(String.valueOf(plot_shape)))
            queryParams.put("plot_shape", String.valueOf(plot_shape));
        if(!"null".equals(String.valueOf(sparsity)))
            queryParams.put("sparsity", String.valueOf(sparsity));
        if(!"null".equals(String.valueOf(get_fingerprint)))
            queryParams.put("get_fingerprint", String.valueOf(get_fingerprint));
        String contentType = "application/json";

        try {
            Object response = apiInvoker.invokeAPI(basePath, path, "POST", queryParams
                , body, headerParams, contentType);
            if(response != null) {
                if (response instanceof String) {
                        @SuppressWarnings("unchecked")
                        List<Image> result = (List<Image>) ApiInvoker.deserialize( (String) response, "Array"
                                , Image.class, null);return result;
                }
                else if (response instanceof java.io.ByteArrayInputStream) {
                        @SuppressWarnings("unchecked")
                        List<Image> result =    (List<Image>) response;return result;
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

