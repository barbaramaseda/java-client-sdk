//CHECKSTYLE:OFF Generated Code.
/*******************************************************************************
 * Copyright (c) CEPT Systems GmbH. All rights reserved.
 *  
 * This software is confidential and proprietary information.
 * You shall use it only in accordance with the terms of the
 * license agreement you entered into with CEPT Systems GmbH.
 ******************************************************************************/
package io.cortical.services.api.client.api;

import io.cortical.rest.model.CategoryFilter;
import io.cortical.rest.model.FilterTrainingObject;
import io.cortical.services.api.client.ApiException;
import io.cortical.services.api.client.ApiInvoker;
import java.util.*;

/** Generated. **/
public class ClassifyApi {
    private String basePath = "http://api.cortical.io/rest";
    /* replace the value of key with your api-key */
    private String key = "replaceWithRetinaAPIKey";
    private ApiInvoker apiInvoker;

    /** Generated. **/
    public ClassifyApi(String apiKey) {
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
    *@return CategoryFilter **/
    public CategoryFilter createCategoryFilter (String filter_name, FilterTrainingObject body, String retina_name) throws ApiException {
        // verify required params are set
        if(retina_name == null || filter_name == null || body == null ) {
             throw new ApiException(400, "missing required params");
        }
        // create path and map variables
        String path = "/classify/create_category_filter".replaceAll("\\{format\\}","json");

        // query params
        Map<String, String> queryParams = new HashMap<String, String>();
        Map<String, String> headerParams = new HashMap<String, String>();

        if(!"null".equals(String.valueOf(retina_name)))
            queryParams.put("retina_name", String.valueOf(retina_name));
        if(!"null".equals(String.valueOf(filter_name)))
            queryParams.put("filter_name", String.valueOf(filter_name));
        String contentType = "application/json";

        try {
            Object response = apiInvoker.invokeAPI(basePath, path, "POST", queryParams
                , body, headerParams, contentType);
            if(response != null) {
                if (response instanceof String) {
                        @SuppressWarnings("unchecked")
                        CategoryFilter result = (CategoryFilter) ApiInvoker.deserialize( (String) response, ""
                                , CategoryFilter.class, null);return result;
                }
                else if (response instanceof java.io.ByteArrayInputStream) {
                        @SuppressWarnings("unchecked")
                        CategoryFilter result =    (CategoryFilter) response;return result;
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
//CHECKSTYLE:ON

