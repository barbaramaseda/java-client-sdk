/*******************************************************************************
 * Copyright (c) cortical.io GmbH. All rights reserved.
 *  
 * This software is confidential and proprietary information.
 * You shall use it only in accordance with the terms of the
 * license agreement you entered into with cortical.io GmbH.
 ******************************************************************************/
package io.cortical.retina.rest;

import io.cortical.retina.model.Context;
import io.cortical.retina.model.Fingerprint;
import io.cortical.retina.model.Term;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** Generated. **/
public class ExpressionsApi {
    private String basePath = "http://api.cortical.io/rest";
    /* replace the value of key with your api-key */
    @SuppressWarnings("unused")
    private String key = "replaceWithRetinaAPIKey";
    private ApiInvoker apiInvoker;

    /** Generated. **/
    public ExpressionsApi(String apiKey) {
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
    public Fingerprint resolveExpression(String body, String retina_name, double sparsity) throws ApiException {
        // verify required params are set
        if(retina_name == null || body == null ) {
             throw new ApiException(400, "missing required params");
        }
        // create path and map variables
        String path = "/expressions".replaceAll("\\{format\\}","json");

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
                    Fingerprint result = (Fingerprint) ApiInvoker.deserialize( (String) response, 
                        "", Fingerprint.class, null);return result;
                }
                else if (response instanceof java.io.ByteArrayInputStream) {
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
    *@return List<Context> **/
    public List<Context> getContextsForExpression (String body, boolean get_fingerprint, String retina_name, int start_index, int max_results, double sparsity) throws ApiException {
        // verify required params are set
        if(retina_name == null || body == null ) {
             throw new ApiException(400, "missing required params");
        }
        // create path and map variables
        String path = "/expressions/contexts".replaceAll("\\{format\\}","json");

        // query params
        Map<String, String> queryParams = new HashMap<String, String>();
        Map<String, String> headerParams = new HashMap<String, String>();

        if(!"null".equals(String.valueOf(retina_name)))
            queryParams.put("retina_name", String.valueOf(retina_name));
        if(!"null".equals(String.valueOf(start_index)))
            queryParams.put("start_index", String.valueOf(start_index));
        if(!"null".equals(String.valueOf(max_results)))
            queryParams.put("max_results", String.valueOf(max_results));
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
    public List<Term> getSimilarTermsForExpressionContext (String body, int context_id, String pos_type, boolean get_fingerprint, String retina_name, int start_index, int max_results, double sparsity) throws ApiException {
        // verify required params are set
        if(retina_name == null || body == null ) {
             throw new ApiException(400, "missing required params");
        }
        // create path and map variables
        String path = "/expressions/similar_terms".replaceAll("\\{format\\}","json");

        // query params
        Map<String, String> queryParams = new HashMap<String, String>();
        Map<String, String> headerParams = new HashMap<String, String>();

        if(!"null".equals(String.valueOf(retina_name)))
            queryParams.put("retina_name", String.valueOf(retina_name));
        if(context_id != -1)
            queryParams.put("context_id", String.valueOf(context_id));
        if(!"null".equals(String.valueOf(start_index)))
            queryParams.put("start_index", String.valueOf(start_index));
        if(!"null".equals(String.valueOf(max_results)))
            queryParams.put("max_results", String.valueOf(max_results));
        if(!"null".equals(String.valueOf(pos_type)))
            queryParams.put("pos_type", String.valueOf(pos_type));
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
    *@return List<Fingerprint> **/
    public List<Fingerprint> resolveBulkExpression (String body, String retina_name, double sparsity) throws ApiException {
        // verify required params are set
        if(retina_name == null || body == null ) {
             throw new ApiException(400, "missing required params");
        }
        // create path and map variables
        String path = "/expressions/bulk".replaceAll("\\{format\\}","json");

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
    *@return List<List<Context>> **/
    public List<List<Context>> getContextsForBulkExpression (String body, boolean get_fingerprint, String retina_name, int start_index, int max_results, double sparsity) throws ApiException {
        // verify required params are set
        if(retina_name == null || body == null ) {
             throw new ApiException(400, "missing required params");
        }
        // create path and map variables
        String path = "/expressions/contexts/bulk".replaceAll("\\{format\\}","json");

        // query params
        Map<String, String> queryParams = new HashMap<String, String>();
        Map<String, String> headerParams = new HashMap<String, String>();

        if(!"null".equals(String.valueOf(retina_name)))
            queryParams.put("retina_name", String.valueOf(retina_name));
        if(!"null".equals(String.valueOf(start_index)))
            queryParams.put("start_index", String.valueOf(start_index));
        if(!"null".equals(String.valueOf(max_results)))
            queryParams.put("max_results", String.valueOf(max_results));
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
                        List<List<Context>> result = (List<List<Context>>) ApiInvoker.deserialize( (String) response, "Array"
                                , java.lang.Object.class, ApiInvoker.NestedContent.CONTEXT);return result;
                }
                else if (response instanceof java.io.ByteArrayInputStream) {
                        @SuppressWarnings("unchecked")
                        List<List<Context>> result =    (List<List<Context>>) response;return result;
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
    *@return List<List<Term>> **/
    public List<List<Term>> getSimilarTermsForBulkExpressionContext (String body, int context_id, String pos_type, boolean get_fingerprint, String retina_name, int start_index, int max_results, double sparsity) throws ApiException {
        // verify required params are set
        if(retina_name == null || body == null ) {
             throw new ApiException(400, "missing required params");
        }
        // create path and map variables
        String path = "/expressions/similar_terms/bulk".replaceAll("\\{format\\}","json");

        // query params
        Map<String, String> queryParams = new HashMap<String, String>();
        Map<String, String> headerParams = new HashMap<String, String>();

        if(!"null".equals(String.valueOf(retina_name)))
            queryParams.put("retina_name", String.valueOf(retina_name));
        if(context_id != -1)
            queryParams.put("context_id", String.valueOf(context_id));
        if(!"null".equals(String.valueOf(start_index)))
            queryParams.put("start_index", String.valueOf(start_index));
        if(!"null".equals(String.valueOf(max_results)))
            queryParams.put("max_results", String.valueOf(max_results));
        if(!"null".equals(String.valueOf(pos_type)))
            queryParams.put("pos_type", String.valueOf(pos_type));
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
                        List<List<Term>> result = (List<List<Term>>) ApiInvoker.deserialize( (String) response, "Array"
                                , java.lang.Object.class, ApiInvoker.NestedContent.TERM);return result;
                }
                else if (response instanceof java.io.ByteArrayInputStream) {
                        @SuppressWarnings("unchecked")
                        List<List<Term>> result =    (List<List<Term>>) response;return result;
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

