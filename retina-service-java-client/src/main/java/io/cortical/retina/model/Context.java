/*******************************************************************************
 * Copyright (c) cortical.io GmbH. All rights reserved.
 *  
 * This software is confidential and proprietary information.
 * You shall use it only in accordance with the terms of the
 * license agreement you entered into with cortical.io GmbH.
 ******************************************************************************/
package io.cortical.retina.model;

import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * Context model.
 * 
 * Represents the context for a word.
 */
public class Context {
    // The api equates "-1" with any or all...
    public static final int ANY_ID = -1;
    
    private int contextId;
    private String contextLabel;
    private Fingerprint fingerprint;
    
    /**
     * 
     * Creates a new instance of {@link Context}.
     *
     */
    public Context() {
        
    }
    
    /**
     * 
     * Creates a new instance of {@link Context}.
     * 
     * @param label the label for the context
     * @param id the id that identifies the context
     */
    public Context(String label, int id) {
        this.contextId = id;
        this.contextLabel = label;
    }
    
    /**
     * 
     * Creates a new instance of {@link ContextImpl}.
     * 
     * @param label the label of the context
     * @param id the id of the context
     * @param fingerprint the fingerprint representing the context
     */
    public Context(String label, int id, Fingerprint fingerprint) {
        this.contextId = id;
        this.contextLabel = label;
        this.fingerprint = fingerprint;
    }

    /**
     * Gets the contextId.
     *
     * @return the contextId
     */
    @JsonProperty(SerializationConstants.CONTEXT_ID_PROPERTYLABEL)
    public int getContextId() {
        return contextId;
    }

    /**
     * Gets the contextLabel.
     *
     * @return the contextLabel
     */
    @JsonProperty(SerializationConstants.CONTEXT_LABEL_PROPERYLABEL)
    public String getContextLabel() {
        return contextLabel;
    }

    /**
     * Gets the fingerprint.
     *
     * @return the fingerprint
     */
    @JsonProperty(SerializationConstants.FINGERPRINT_PROPERTY_LABEL)
    public Fingerprint getFingerprint() {
        return fingerprint;
    }

    /**
     * Sets the contextId.
     *
     * @param contextId the contextId to set
     */
    protected void setContextId(int contextId) {
        this.contextId = contextId;
    }

    /**
     * Sets the contextLabel.
     *
     * @param contextLabel the contextLabel to set
     */
    protected void setContextLabel(String contextLabel) {
        this.contextLabel = contextLabel;
    }

    /**
     * Sets the fingerprint.
     *
     * @param fingerprint the fingerprint to set
     */
    protected void setFingerprint(Fingerprint fingerprint) {
        this.fingerprint = fingerprint;
    }
    
    
    
}
