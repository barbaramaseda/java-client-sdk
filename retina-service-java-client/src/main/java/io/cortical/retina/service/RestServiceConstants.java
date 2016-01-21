/*******************************************************************************
 * Copyright (c) cortical.io GmbH. All rights reserved.
 *  
 * This software is confidential and proprietary information.
 * You shall use it only in accordance with the terms of the
 * license agreement you entered into with cortical.io GmbH.
 ******************************************************************************/
package io.cortical.retina.service;

import java.awt.Color;
/**
 * 
 * An abstract class for defining the constants of the rest service.
 */
public abstract class RestServiceConstants {
    
    //CHECKSTYLE:OFF
    /** NOTE: Constants to use and reference from all the Swagger related code.
     * The variable values follow the naming conventions for the www world using underscores instead of camelCase. 
     */
    
    /**
     * Text constants.
     */
    public static final String NULL_RETINA_MSG = "The retinaName cannot be null.";
    public static final String NULL_MODEL_MSG = "The model cannot be null or empty.";
    public static final String NULL_API_KEY_MSG = "The apiKey cannot be null.";
    public static final String NULL_BASE_PATH_MSG = "The base path cannot be null.";
    public static final String NULL_SERVER_IP_MSG = "The retina server ip cannot be null.";
    public static final String NULL_TERM_MSG = "The term cannot be null.";
    public static final String NULL_TEXT_MSG = "The text cannot be null.";
    
    
    /**
     * The path parameter referring to the name of a retina.
     */
    public static final String PARAM_RETINA_NAME = "retina_name";
    
    public static final String PARAM_GET_FINGERPRINT = "get_fingerprint";
    
    public static final String PARAM_TERM = "term";
    public static final String PARAM_CONTEXT_ID = "context_id";
    
    public static final String PARAM_START_INDEX = "start_index";

    public static final String PARAM_MAX_RESULTS = "max_results";
    
    public static final String PARAM_PLOT_SHAPE = "plot_shape";
    public static final String PARAM_PLOT_SHAPE_SQUARE = "square";
    public static final String PARAM_PLOT_SHAPE_CIRCLE = "circle";
    

    public static final String PARAM_PLOT_SCALAR = "image_scalar";
    
    public static final String PARAM_IMAGE_ENCODING = "image_encoding";
    public static final String PARAM_IMAGE_ENCODING_BASE64 = "base64/png";
    public static final String PARAM_IMAGE_ENCODING_BINARY = "binary/png";

    public static final String PARAM_IMAGE_ENDODING_ALLOWABLE_VALUES = "base64/png, binary/png";
    
    public static final String PARAM_SPARSITY = "sparsity";
    
    
    public static final String PARAM_FINGERPRINT_ID = "fingerprint_id";
    public static final String PARAM_LOGICAL_NAME = "logical_name";

    
    public final static String PARAM_PATH_SLICE = "slice";
    public final static String PARAM_PATH_SIMILARITY_METRIC = "metric";
    public final static String PARAM_POS_TYPE = "pos_type";
    public final static String PARAM_FINGERPRINT_NAME = "name";
   
    public final static int PARAM_IMAGE_SCALAR_DEFAULT = 5;
    public final static String PARAM_INTERNAL_IMAGE_SCALAR_CHOOSE = "2,5";
    public final static Color PARAM_INTERNAL_IMAGE_COLOR = new Color(255, 102, 0); 
    public final static Color PARAM_INTERNAL_IMAGE_COLOR_OVERLAY_TERM1 = new Color(16, 97, 134);
    public final static Color PARAM_INTERNAL_IMAGE_COLOR_OVERLAY_TERM2 = new Color(23, 132, 189).brighter();
    public final static String PARAM_PLOT_SHAPE_ALLOWABLE_VALUES = "circle,square";
    public static final String TAG_IMAGE_PNG = "image/png";

    public final static String PARAM_POS_TYPES_ALLOWABLE_VALUES = "NOUN,ADJECTIVE,VERB";

    /** Constant for specifying the maximum limit of returning images. **/
    public static final int MAX_RESULTS_LIMIT_IMAGES = 10;
    /** Constant for specifying the maximum limit of returned results in generic methods (bitmaps). **/
    public static final int MAX_RESULTS_LIMIT = 100;
    /** Constant for specifying the maximum limit of returned results in get all terms and wildcards and FP_IDs  . **/
    public static final int MAX_RESULTS_LIMIT_ALLTERMS = 1000;
    //CHECKSTYLE:ON
    
    /** NOTE: This is the expected general order **/
    /** Term string to identify term return type. **/
    public static final String TERM = "term";
    /** Context string to identify context return type. **/
    public static final String CONTEXT = "context";
    /** Fingerprint_id string to identify Fingerprint_id return type. **/
    public static final String FINGERPRINT_ID = "fingerprint_id";
    /** Fingerprint string to identify fingerprint return type. **/
    public static final String FINGERPRINT = "fingerprint";
    /** Metric string to identify metric return type. **/
    public static final String METRIC = "metric";
    /** Image string to identify image return type. **/
    public static final String IMAGE = "image";
    /** Word cloud string to identify the word cloud return type. **/
    public static final String WORDCLOUD = "word_cloud";
    /** Text, containing a Fingerprint and the actual String. **/
    public static final String TEXT = "text";
    
}
