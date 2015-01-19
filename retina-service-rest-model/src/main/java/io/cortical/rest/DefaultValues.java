/*******************************************************************************
 * Copyright (c) cortical.io GmbH. All rights reserved.
 *  
 * This software is confidential and proprietary information.
 * You shall use it only in accordance with the terms of the
 * license agreement you entered into with cortical.io GmbH.
 ******************************************************************************/
package io.cortical.rest;

import static io.cortical.rest.RestServiceConstants.PARAM_IMAGE_ENCODING_BASE64;
import static io.cortical.rest.RestServiceConstants.PARAM_PLOT_SHAPE_CIRCLE;



/**
 * 
 * Default values.
 */
public class DefaultValues {
    /**
     * "Sparsity" default value
     */
    public static final String DEF_VALUE_SPARSITY = "1.0";
    /**
     * "Start from index" default value. 
     */
    public static final String DEF_VALUE_START_INDEX = "0";
    /**
     * "Max contexts' count in a response" default value.
     */
    public static final String DEF_VALUE_MAX_CONTEXTS_COUNT = "5";
    /**
     * "Provide a 'fingerprints' in a response" default value.
     */
    public static final String DEF_VALUE_PROVIDE_FINGERPRINT = "false";
    /**
     * "All available retinas."
     */
    //TODO this should be discussed. (is this correct. how to provide list of available retinas.) SERHIY
    public static final String DEF_VALUE_RETINAS = "en_associative,en_synonymous";
    /**
     * "Max count of items in a response" default value.
     */
    public static final String DEF_VALUE_MAX_ITEMS_COUNT = "10";
    /**
     * "Tokenizer" default parameter.
     */
    public static final String DEF_VALUE_TOKENIZER_POS = "";
    /**
     * "Image plot scalar" default value.
     */
    public static final String DEF_VALUE_PLOT_SCALAR = "2";
    /**
     * "Image encoding" default values.
     */
    public static final String DEF_VALUE_IMAGE_ENCODING = PARAM_IMAGE_ENCODING_BASE64;
    /**
     * "Image plot shape" default value.
     */
    public static final String DEF_VALUE_PLOT_SHAPE = PARAM_PLOT_SHAPE_CIRCLE;
}
