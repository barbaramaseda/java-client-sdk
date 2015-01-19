/*******************************************************************************
 * Copyright (c) cortical.io GmbH. All rights reserved.
 *  
 * This software is confidential and proprietary information.
 * You shall use it only in accordance with the terms of the
 * license agreement you entered into with cortical.io GmbH.
 ******************************************************************************/
package io.cortical.rest.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * The Retina model.
 * 
 */
@XmlRootElement(name = "Retina")
public class Retina {
    private String retinaName;
    private String retinaDescription;
    private long numberOfTermsInRetina;
    private int numberOfRows;
    private int numberOfColumns;
    /**
     * 
     * Creates a new instance of {@link Retina}.
     *
     */
    public Retina() {
        
    }
    /**
     * 
     * Creates a new instance of {@link Retina}.
     * 
     * @param retinaName : the name of the retina.
     * @param retinaDescription : a description of the retina.
     * @param dimensions : the dimensions of the retina.
     * @param numTerms : number of terms in this retina.
     */
    public Retina(String retinaName, String retinaDescription, long numTerms, int numberOfRows, int numberOfColumns) {
        this.retinaName = retinaName;
        this.retinaDescription = retinaDescription;
        this.numberOfTermsInRetina = numTerms;
        this.numberOfRows = numberOfRows;
        this.numberOfColumns = numberOfColumns;

    }
    
    
    /**
     * 
     * @return the name of the retina, as a String.
     */
    @XmlElement(name = "retinaName")
    @JsonProperty("retinaName")
    public String getRetinaName() {
        return retinaName;
    }
    
    
    /**
     * 
     * @return the number of terms in the retina, as a long.
     */
    @XmlElement(name = "numberOfTermsInRetina")
    @JsonProperty("numberOfTermsInRetina")
    public long getNumberOfTermsInRetina() {
        return numberOfTermsInRetina;
    }
    
    
    /**
     * 
     * @return a description of the retina, as a String.
     */
    @XmlElement(name = "description")
    @JsonProperty("description")
    public String getRetinaDescription() {
        return retinaDescription;
    }
    
    
    /**
     * 
     * @return the number of rows in the retina, as an int.
     */
    @XmlElement(name = "numberOfRows")
    @JsonProperty("numberOfRows")
    public int getNumberOfRows() {
        return numberOfRows;
    }
    
    
    /**
     * 
     * @return the number of columns in the retina, as an int.
     */
    @XmlElement(name = "numberOfColumns")
    @JsonProperty("numberOfColumns")
    public int getNumberOfColumns() {
        return numberOfColumns;
    }
}
