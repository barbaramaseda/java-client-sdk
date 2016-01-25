/*******************************************************************************
 * Copyright (c) cortical.io GmbH. All rights reserved.
 *  
 * This software is confidential and proprietary information.
 * You shall use it only in accordance with the terms of the
 * license agreement you entered into with cortical.io GmbH.
 ******************************************************************************/
package io.cortical.retina.service;

import io.cortical.retina.model.Context;
import io.cortical.retina.model.Term;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** Generated. **/
public class TermsApi {
    private String basePath = "http://api.cortical.io/rest";
    /* replace the value of key with your api-key */
    @SuppressWarnings("unused")
    private String key = "replaceWithRetinaAPIKey";
    private ApiInvoker apiInvoker;

    /** Generated. **/
    public TermsApi(String apiKey) {
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
    *@return List<Term> **/
    public List<Term> getTerm (String term, boolean get_fingerprint, String retina_name, int start_index, int max_results) throws ApiException {
        // verify required params are set
        if(retina_name == null ) {
             throw new ApiException(400, "missing required params");
        }
        // create path and map variables
        String path = "/terms".replaceAll("\\{format\\}","json");

        // query params
        Map<String, String> queryParams = new HashMap<String, String>();
        Map<String, String> headerParams = new HashMap<String, String>();

        if(!"null".equals(String.valueOf(retina_name)))
            queryParams.put("retina_name", String.valueOf(retina_name));
        if(!"null".equals(String.valueOf(term)))
            queryParams.put("term", String.valueOf(term));
        if(!"null".equals(String.valueOf(start_index)))
            queryParams.put("start_index", String.valueOf(start_index));
        if(!"null".equals(String.valueOf(max_results)))
            queryParams.put("max_results", String.valueOf(max_results));
        if(!"null".equals(String.valueOf(get_fingerprint)))
            queryParams.put("get_fingerprint", String.valueOf(get_fingerprint));
        String contentType = "application/json";

        try {
            Object response = apiInvoker.invokeAPI(basePath, path, "GET", queryParams
                , null, headerParams, contentType);
            if(response != null) {
                if (response instanceof String) {
                        @SuppressWarnings("unchecked")
                        List<Term> result = (List<Term>) ApiInvoker.deserialize( (String) response, "Array"
                                , Term.class, null);return result;
                }
                else if (response instanceof java.io.ByteArrayInputStream) {
                        @SuppressWarnings("unchecked")
                        List<Term> result =    (List<Term>) response;return result;
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
    *@return List<Context> **/
    public List<Context> getContextsForTerm (String term, boolean get_fingerprint, String retina_name, int start_index, int max_results) throws ApiException {
        // verify required params are set
        if(retina_name == null || term == null ) {
             throw new ApiException(400, "missing required params");
        }
        // create path and map variables
        String path = "/terms/contexts".replaceAll("\\{format\\}","json");

        // query params
        Map<String, String> queryParams = new HashMap<String, String>();
        Map<String, String> headerParams = new HashMap<String, String>();

        if(!"null".equals(String.valueOf(retina_name)))
            queryParams.put("retina_name", String.valueOf(retina_name));
        if(!"null".equals(String.valueOf(term)))
            queryParams.put("term", String.valueOf(term));
        if(!"null".equals(String.valueOf(start_index)))
            queryParams.put("start_index", String.valueOf(start_index));
        if(!"null".equals(String.valueOf(max_results)))
            queryParams.put("max_results", String.valueOf(max_results));
        if(!"null".equals(String.valueOf(get_fingerprint)))
            queryParams.put("get_fingerprint", String.valueOf(get_fingerprint));
        String contentType = "application/json";

        try {
            Object response = apiInvoker.invokeAPI(basePath, path, "GET", queryParams
                , null, headerParams, contentType);
            if(response != null) {
                if (response instanceof String) {
                        @SuppressWarnings("unchecked")
                        List<Context> result = (List<Context>) ApiInvoker.deserialize( (String) response, "Array"
                                , Context.class, null);return result;
                }
                else if (response instanceof java.io.ByteArrayInputStream) {
                        @SuppressWarnings("unchecked")
                        List<Context> result =    (List<Context>) response;return result;
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
    *@return List<Term> **/
    public List<Term> getSimilarTerms (String term, int context_id, String pos_type, boolean get_fingerprint, String retina_name, int start_index, int max_results) throws ApiException {
        // verify required params are set
        if(retina_name == null || term == null ) {
             throw new ApiException(400, "missing required params");
        }
        // create path and map variables
        String path = "/terms/similar_terms".replaceAll("\\{format\\}","json");

        // query params
        Map<String, String> queryParams = new HashMap<String, String>();
        Map<String, String> headerParams = new HashMap<String, String>();

        if(!"null".equals(String.valueOf(retina_name)))
            queryParams.put("retina_name", String.valueOf(retina_name));
        if(!"null".equals(String.valueOf(term)))
            queryParams.put("term", String.valueOf(term));
        if(context_id != -1)
            queryParams.put("context_id", String.valueOf(context_id));
        if(!"null".equals(String.valueOf(start_index)))
            queryParams.put("start_index", String.valueOf(start_index));
        if(!"null".equals(String.valueOf(max_results)))
            queryParams.put("max_results", String.valueOf(max_results));
        if(!"null".equals(String.valueOf(pos_type)))
            queryParams.put("pos_type", String.valueOf(pos_type));
        if(!"null".equals(String.valueOf(get_fingerprint)))
            queryParams.put("get_fingerprint", String.valueOf(get_fingerprint));
        String contentType = "application/json";

        try {
            Object response = apiInvoker.invokeAPI(basePath, path, "GET", queryParams
                , null, headerParams, contentType);
            if(response != null) {
                if (response instanceof String) {
                        @SuppressWarnings("unchecked")
                        List<Term> result = (List<Term>) ApiInvoker.deserialize( (String) response, "Array"
                                , Term.class, null);return result;
                }
                else if (response instanceof java.io.ByteArrayInputStream) {
                        @SuppressWarnings("unchecked")
                        List<Term> result =    (List<Term>) response;return result;
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

