/*******************************************************************************
 * Copyright (c) cortical.io GmbH. All rights reserved.
 *  
 * This software is confidential and proprietary information.
 * You shall use it only in accordance with the terms of the
 * license agreement you entered into with cortical.io GmbH.
 ******************************************************************************/
package io.cortical.retina.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;



/**
 * 
 *  Text model.
 */
@JsonRootName("text")
public class Text extends Model {

    private String text;
    private Fingerprint fingerprint;
    /**
     * 
     * Creates a new instance of {@link Text}.
     *
     */
    public Text() {
        
    }
    /**
     * Creates a new instance of {@link Text}.
     * 
     * @param text the actual text of the element
     * @param positions the positions of the fingerprint representation of the text
     */
    public Text(String text, int[] positions) {
        this.text = text;
        this.fingerprint = new Fingerprint(positions);
    }
    
    /**
     * Creates a new instance of {@link Text}.
     * 
     * @param text the actual text of the element
     */
    public Text(String text) {
        this.text = text;
    }

    /**
     * Gets the text.
     *
     * @return the text
     */
    @JsonProperty(SerializationConstants.TEXT_STRING_PROPERTYLABEL)
    public String getText() {
        return text;
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
    
    
}
