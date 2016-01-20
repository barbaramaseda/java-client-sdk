/*******************************************************************************
 * Copyright (c) cortical.io GmbH. All rights reserved.
 *  
 * This software is confidential and proprietary information.
 * You shall use it only in accordance with the terms of the
 * license agreement you entered into with cortical.io GmbH.
 ******************************************************************************/
package io.cortical.retina.service;

import io.cortical.retina.model.Fingerprint;
import io.cortical.retina.model.Retina;
import io.cortical.retina.model.Text;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** Generated. **/
public class TextApi {
    private String basePath = "http://api.cortical.io/rest";
    /* replace the value of key with your api-key */
    @SuppressWarnings("unused")
    private String key = "replaceWithRetinaAPIKey";
    private ApiInvoker apiInvoker;

    /** Generated. **/
    public TextApi(String apiKey) {
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
    *@return List<Fingerprint> **/
    public List<Fingerprint> getRepresentationForText (String body, String retina_name) throws ApiException {
        // verify required params are set
        if(retina_name == null || body == null ) {
             throw new ApiException(400, "missing required params");
        }
        // create path and map variables
        String path = "/text".replaceAll("\\{format\\}","json");

        // query params
        Map<String, String> queryParams = new HashMap<String, String>();
        Map<String, String> headerParams = new HashMap<String, String>();

        if(!"null".equals(String.valueOf(retina_name)))
            queryParams.put("retina_name", String.valueOf(retina_name));
        String contentType = "application/json";

        try {
            Object response = apiInvoker.invokeAPI(basePath, path, "POST", queryParams
                , body, headerParams, contentType);
            if(response != null) {
                if (response instanceof String) {
                        @SuppressWarnings("unchecked")
                        List<Fingerprint> result = (List<Fingerprint>) ApiInvoker.deserialize( (String) response, "Array"
                                , Fingerprint.class, null);return result;
                }
                else if (response instanceof java.io.ByteArrayInputStream) {
                        @SuppressWarnings("unchecked")
                        List<Fingerprint> result =    (List<Fingerprint>) response;return result;
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
    *@return List<String> **/
    public List<String> getKeywordsForText (String body, String retina_name) throws ApiException {
        // verify required params are set
        if(retina_name == null || body == null ) {
             throw new ApiException(400, "missing required params");
        }
        // create path and map variables
        String path = "/text/keywords".replaceAll("\\{format\\}","json");

        // query params
        Map<String, String> queryParams = new HashMap<String, String>();
        Map<String, String> headerParams = new HashMap<String, String>();

        if(!"null".equals(String.valueOf(retina_name)))
            queryParams.put("retina_name", String.valueOf(retina_name));
        String contentType = "application/json";

        try {
            Object response = apiInvoker.invokeAPI(basePath, path, "POST", queryParams
                , body, headerParams, contentType);
            if(response != null) {
                if (response instanceof String) {
                        @SuppressWarnings("unchecked")
                        List<String> result = (List<String>) ApiInvoker.deserialize( (String) response, "Array"
                                , String.class, null);return result;
                }
                else if (response instanceof java.io.ByteArrayInputStream) {
                        @SuppressWarnings("unchecked")
                        List<String> result =    (List<String>) response;return result;
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
    *@return List<String> **/
    public List<String> getTokensForText (String body, String POStags, String retina_name) throws ApiException {
        // verify required params are set
        if(retina_name == null || body == null ) {
             throw new ApiException(400, "missing required params");
        }
        // create path and map variables
        String path = "/text/tokenize".replaceAll("\\{format\\}","json");

        // query params
        Map<String, String> queryParams = new HashMap<String, String>();
        Map<String, String> headerParams = new HashMap<String, String>();

        if(!"null".equals(String.valueOf(retina_name)))
            queryParams.put("retina_name", String.valueOf(retina_name));
        if(!"null".equals(String.valueOf(POStags)))
            queryParams.put("POStags", String.valueOf(POStags));
        String contentType = "application/json";

        try {
            Object response = apiInvoker.invokeAPI(basePath, path, "POST", queryParams
                , body, headerParams, contentType);
            if(response != null) {
                if (response instanceof String) {
                        @SuppressWarnings("unchecked")
                        List<String> result = (List<String>) ApiInvoker.deserialize( (String) response, "Array"
                                , String.class, null);return result;
                }
                else if (response instanceof java.io.ByteArrayInputStream) {
                        @SuppressWarnings("unchecked")
                        List<String> result =    (List<String>) response;return result;
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
    *@return List<Text> **/
    public List<Text> getSlicesForText (String body, Boolean get_fingerprint, String retina_name, Integer start_index, Integer max_results) throws ApiException {
        // verify required params are set
        if(retina_name == null || body == null ) {
             throw new ApiException(400, "missing required params");
        }
        // create path and map variables
        String path = "/text/slices".replaceAll("\\{format\\}","json");

        // query params
        Map<String, String> queryParams = new HashMap<String, String>();
        Map<String, String> headerParams = new HashMap<String, String>();

        if(!"null".equals(String.valueOf(retina_name)))
            queryParams.put("retina_name", String.valueOf(retina_name));
        if(!"null".equals(String.valueOf(start_index)))
            queryParams.put("start_index", String.valueOf(start_index));
        if(!"null".equals(String.valueOf(max_results)))
            queryParams.put("max_results", String.valueOf(max_results));
        if(!"null".equals(String.valueOf(get_fingerprint)))
            queryParams.put("get_fingerprint", String.valueOf(get_fingerprint));
        String contentType = "application/json";

        try {
            Object response = apiInvoker.invokeAPI(basePath, path, "POST", queryParams
                , body, headerParams, contentType);
            if(response != null) {
                if (response instanceof String) {
                        @SuppressWarnings("unchecked")
                        List<Text> result = (List<Text>) ApiInvoker.deserialize( (String) response, "Array"
                                , Text.class, null);return result;
                }
                else if (response instanceof java.io.ByteArrayInputStream) {
                        @SuppressWarnings("unchecked")
                        List<Text> result =    (List<Text>) response;return result;
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
    *@return List<Fingerprint> **/
    public List<Fingerprint> getRepresentationsForBulkText (String body, String retina_name, Double sparsity) throws ApiException {
        // verify required params are set
        if(retina_name == null || body == null ) {
             throw new ApiException(400, "missing required params");
        }
        // create path and map variables
        String path = "/text/bulk".replaceAll("\\{format\\}","json");

        // query params
        Map<String, String> queryParams = new HashMap<String, String>();
        Map<String, String> headerParams = new HashMap<String, String>();

        if(!"null".equals(String.valueOf(retina_name)))
            queryParams.put("retina_name", String.valueOf(retina_name));
        if(!"null".equals(String.valueOf(sparsity)))
            queryParams.put("sparsity", String.valueOf(sparsity));
        String contentType = "application/json";

        try {
            Object response = apiInvoker.invokeAPI(basePath, path, "POST", queryParams
                , body, headerParams, contentType);
            if(response != null) {
                if (response instanceof String) {
                        @SuppressWarnings("unchecked")
                        List<Fingerprint> result = (List<Fingerprint>) ApiInvoker.deserialize( (String) response, "Array"
                                , Fingerprint.class, null);return result;
                }
                else if (response instanceof java.io.ByteArrayInputStream) {
                        @SuppressWarnings("unchecked")
                        List<Fingerprint> result =    (List<Fingerprint>) response;return result;
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
     *@return Retina **/
     public Retina getLanguage (String body) throws ApiException {
         // verify required params are set
         if(body == null ) {
              throw new ApiException(400, "missing required params");
         }
         // create path and map variables
         String path = "/text/detect_language".replaceAll("\\{format\\}","json");

         // query params
         Map<String, String> queryParams = new HashMap<String, String>();
         Map<String, String> headerParams = new HashMap<String, String>();

         String contentType = "application/json";

         try {
             Object response = apiInvoker.invokeAPI(basePath, path, "POST", queryParams
                 , body, headerParams, contentType);
             if(response != null) {
                 if (response instanceof String) {
                     Retina result = (Retina) ApiInvoker.deserialize((String) response, "",
                         Retina.class, null);
                     return result;
                 }
                 else if (response instanceof java.io.ByteArrayInputStream) {
                     Retina result = (Retina) response;
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

