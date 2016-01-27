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
import javax.xml.bind.annotation.XmlElement;


/**
 * Fingerprint : the Retina's semantic representation.
 * Can be used in the creation of expressions. {@see ExpressionFactory}
 */
@JsonRootName("fingerprint")
public class Fingerprint extends Model {
    private int[] positions;
    /**
     * 
     * Creates a new instance of {@link Fingerprint}
     *
     */
    public Fingerprint() {
        
    }
    /**
     * Creates a new instance of {@link FingerprintImpl}.
     * @param positions the positions of the fingerprint
     * 
     */
    public Fingerprint(int[] positions) {
        this.positions = positions;
    }

    /**
     * Gets the positions of this Fingerprint.
     *
     * @return the positions
     */
    @XmlElement(name = "positions")
    @JsonProperty("positions")
    public int[] getPositions() {
        return positions;
    }
    /**
     * Sets the positions of this Fingerprint.
     *
     * @param positions the positions to set
     */
    protected void setPositions(int[] positions) {
        this.positions = positions;
    }

    
}
