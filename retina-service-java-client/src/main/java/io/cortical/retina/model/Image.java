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
 * Image.  A visual representation of a {@link Fingerprint}.
 * 
 * 
 */
public class Image {

    private byte[] imageData;
    private Fingerprint fingerprint;
    /**
     * 
     * Creates a new instance of {@link Image}.
     *
     */
    public Image() {
        
    }
    /**
     * 
     * Creates a new instance of {@link ImageImpl}.
     * 
     * @param imageData the base64 encoded image data
     * @param fingerprint the {@link Fingerprint}.
     */
    public Image(byte[] imageData, Fingerprint fingerprint) {
        this.fingerprint = fingerprint;
        this.imageData = imageData;

    }
    /**
     * Gets the imageData.
     *
     * @return the imageData
     */
    @JsonProperty(SerializationConstants.IMAGE_DATA)
    public byte[] getImageData() {
        return imageData;
    }
    /**
     * Gets the fingerprint associated with this {@link Image} object.
     *
     * @return the fingerprint
     */
    @JsonProperty(SerializationConstants.FINGERPRINT_PROPERTY_LABEL)
    public Fingerprint getFingerprint() {
        return fingerprint;
    }
    
    
}
