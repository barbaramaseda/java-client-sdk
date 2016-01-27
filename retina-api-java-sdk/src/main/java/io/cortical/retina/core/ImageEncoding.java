/*******************************************************************************
 * Copyright (c) cortical.io GmbH. All rights reserved.
 *  
 * This software is confidential and proprietary information.
 * You shall use it only in accordance with the terms of the
 * license agreement you entered into with cortical.io GmbH.
 ******************************************************************************/
package io.cortical.retina.core;


/**
 * Image encoding types.
 * 
 */
public enum ImageEncoding {
    
    BASE64_PNG("base64/png"),
    BINARY_PNG("binary/png");
    
    String machineRepresentation;
    
    ImageEncoding(String machineRepresentation) {
        this.machineRepresentation = machineRepresentation;
    }
    
    /**
     * Gets the machineRepresentation
     *
     * @return the machineRepresentation
     */
    public String machineRepresentation() {
        return machineRepresentation;
    }
}
