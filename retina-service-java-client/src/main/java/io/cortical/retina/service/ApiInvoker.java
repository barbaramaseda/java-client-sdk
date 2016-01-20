/*******************************************************************************
 * Copyright (c) cortical.io GmbH. All rights reserved.
 *  
 * This software is confidential and proprietary information.
 * You shall use it only in accordance with the terms of the
 * license agreement you entered into with cortical.io GmbH.
 ******************************************************************************/
package io.cortical.retina.service;

import com.fasterxml.jackson.databind.JavaType;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource.Builder;
import com.sun.jersey.api.client.filter.LoggingFilter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status.Family;
import org.apache.commons.httpclient.URIException;
import org.apache.commons.httpclient.util.URIUtil;

/** Generated. **/
public class ApiInvoker {
    private static ApiInvoker INSTANCE = new ApiInvoker();
    private Map<String, Client> hostMap = new HashMap<String, Client>();
    private Map<String, String> defaultHeaderMap = new HashMap<String, String>();
    
    /** Allows retrieving an instance of {@link ApiInvoker}.
    * @return a {@link ApiInvoker} object.
    **/
    public static ApiInvoker getInstance() {
        return INSTANCE;
    }
    
    /**
    * Allows adding default headers to requests.
    * @param key the key
    * @param value the value
    **/
    public void addDefaultHeader(String key, String value) {
         defaultHeaderMap.put(key, value);
    }

    /**
    * String escaping.
    * @param str the String to escape
    **/
    public String escapeString(String str) {
        return str;
    }

    public enum NestedContent{
        CONTEXT, TERM
    }
    
    /** 
    * Deserialize a received response String.
    * @param json the received json String
    * @param containerType the containerType
    * @param cls the class of the object 
    * @param nestedContent contains the name of the Pojo, contained in a List of Lists. <code>null</code> if no nested content is present. 
    * @throws APIException if an exception occurs during deserialization
    **/
    public static Object deserialize(String json, String containerType, Class<?> cls, NestedContent nestedContent) 
        throws ApiException {
        try{

            if(("List".equals(containerType) || "Array".equals(containerType)) && nestedContent != null){
                if(NestedContent.CONTEXT.equals(nestedContent)){
                    JavaType typeInfo = JsonUtil.getJsonMapper().getTypeFactory().constructFromCanonical("java.util.List<java.util.List<io.cortical.retina.model.Context>>");
                    Object response = (java.lang.Object) JsonUtil.getJsonMapper().readValue(json, typeInfo);
                    return response;
                }else if(NestedContent.TERM.equals(nestedContent)){
                    JavaType typeInfo = JsonUtil.getJsonMapper().getTypeFactory().constructFromCanonical("java.util.List<java.util.List<io.cortical.retina.model.Term>>");
                    Object response = (java.lang.Object) JsonUtil.getJsonMapper().readValue(json, typeInfo);
                    return response;
                }else{
                    return null;
                }                        
               
            }
            else if("List".equals(containerType) || "Array".equals(containerType)) {
                JavaType typeInfo = JsonUtil.getJsonMapper().getTypeFactory().constructCollectionType(List.class, cls);
                List<?> response = (List<?>) JsonUtil.getJsonMapper().readValue(json, typeInfo);
                return response;
            }
            else if(String.class.equals(cls)) {
                if(json != null && json.startsWith("\"") && json.endsWith("\"") && json.length() > 1)
                    return json.substring(1, json.length() - 2);
                else
                    return json;
            }
            else {
                return JsonUtil.getJsonMapper().readValue(json, cls);
            }
        }
        catch (IOException e) {
            throw new ApiException(500, e.getMessage());
        }
    }
    
    /** 
    * Serialize an Object.
    * @param obj the Object to serialize
    * @throws APIException if an exception occurs during serialization
    **/
    public static String serialize(Object obj) throws ApiException {
        try {
            if (obj != null)
                    if (obj instanceof String) {
                        // This assumes that we receive json as a string
                        return (String) obj;
                } else {
                        return JsonUtil.getJsonMapper().writeValueAsString(obj);
                }
            else
                return null;
        }
        catch (Exception e) {
            throw new ApiException(500, e.getMessage());
        }
    }

    /** 
    * Serialize an Object.
    * @param host the targeted host
    * @param path the targeted rest endpoint
    * @param method the HTTP method
    * @param queryParams the query parameters
    * @param body the obligatory body of a post
    * @param headerParams the HTTP header parameters
    * @param contentType the content type
    * @throws APIException if an exception occurs during querying of the API.
    **/
    public Object invokeAPI(String host, String path, String method, Map<String, String> queryParams, Object body
        , Map<String, String> headerParams, String contentType) throws ApiException {
        Client client = getClient(host);

        StringBuilder b = new StringBuilder();

        for(String key : queryParams.keySet()) {
            String value = queryParams.get(key);
            if (value != null){
                if(b.toString().length() == 0)
                    b.append("?");
                else
                    b.append("&");
                b.append(escapeString(key)).append("=").append(escapeString(value));
            }
        }
        String querystring = b.toString();


        try {
            querystring = URIUtil.encodeQuery(querystring);
        }
        catch (URIException e) {
            throw new ApiException(0, e.getStackTrace().toString());
        }
        
        Builder builder = client.resource(host + path + querystring).accept(new String[]{"application/json", "image/png"});
        for(String key : headerParams.keySet()) {
            builder.header(key, headerParams.get(key));
        }

        for(String key : defaultHeaderMap.keySet()) {
            if(!headerParams.containsKey(key)) {
                builder.header(key, defaultHeaderMap.get(key));
            }
        }
        ClientResponse response = null;

        if("GET".equals(method)) {
            response = (ClientResponse) builder.get(ClientResponse.class);
        }
        else if ("POST".equals(method)) {
            if(body == null)
                response = builder.post(ClientResponse.class, serialize(body));
            else
                response = builder.type("application/json").post(ClientResponse.class, serialize(body));
        }
        else if ("PUT".equals(method)) {
            if(body == null)
                response = builder.put(ClientResponse.class, serialize(body));
            else
                response = builder.type("application/json").put(ClientResponse.class, serialize(body));
        }
        else if ("DELETE".equals(method)) {
            if(body == null)
                response = builder.delete(ClientResponse.class, serialize(body));
            else
                response = builder.type("application/json").delete(ClientResponse.class, serialize(body));
        }
        else {
            throw new ApiException(500, "unknown method type " + method);
        }
        if(response.getClientResponseStatus() == ClientResponse.Status.NO_CONTENT) {
            return null;
        }
        else if(response.getClientResponseStatus().getFamily() == Family.SUCCESSFUL) {
                // Handle the casting of the response based on the type.
                if (!response.getHeaders().get("Content-Type").get(0).equals(MediaType.APPLICATION_JSON)) {
                            return response.getEntityInputStream();
                    }
                    return (String) response.getEntity(String.class);
        }
        else {
            throw new ApiException(
                                response.getClientResponseStatus().getStatusCode(),
                                response.getEntity(String.class));
        }
    }

    private Client getClient(String host) {
        if(!hostMap.containsKey(host)) {
            Client client = Client.create();
            client.addFilter(new LoggingFilter());
            hostMap.put(host, client);
        }
        return hostMap.get(host);
    }
}


