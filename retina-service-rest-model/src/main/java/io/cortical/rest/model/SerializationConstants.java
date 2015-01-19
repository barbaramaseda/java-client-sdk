/*******************************************************************************
 * Copyright (c) cortical.io GmbH. All rights reserved.
 *  
 * This software is confidential and proprietary information.
 * You shall use it only in accordance with the terms of the
 * license agreement you entered into with cortical.io GmbH.
 ******************************************************************************/
package io.cortical.rest.model;


/**
 * Constants for configuring serialization.
 * 
 */
abstract class SerializationConstants {
    
    /** Context id. */
    public static final String CONTEXT_ID_PROPERTYLABEL = "context_id";
    /** Context descriptive label. */
    public static final String CONTEXT_LABEL_PROPERYLABEL = "context_label";
    /** fingerprint. */
    public static final String FINGERPRINT_PROPERTY_LABEL = "fingerprint";
    /** A score. */
    public static final String SCORE_PROPRETY_LABEL = "score";
    /** A listing of pos types. */
    public static final String POS_TYPES_PROPRETY_LABEL = "pos_types";
    /** The string of a term. */
    public static final String TERM_STRING_PROPERTYLABEL = "term";
    
    /** The string of a term. */
    public static final String TEXT_STRING_PROPERTYLABEL = "text";
    /** The df. */
    public static final String DF_STRING_PROPERTYLABEL = "df";
    /** Image. **/
    public static final String IMAGE_DATA = "image_data";
    
    /**
     * List of terms.
     */
    public static final String LIST_OF_TERMS_PROPERTY_LABEL = "list_of_terms";
    
    /**
     * List of Contexts.
     */
    public static final String LIST_OF_CONTEXTS_PROPERTY_LABEL = "list_of_contexts";
    
    
}
