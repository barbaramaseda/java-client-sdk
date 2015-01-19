/*******************************************************************************
 * Copyright (c) cortical.io GmbH. All rights reserved.
 *  
 * This software is confidential and proprietary information.
 * You shall use it only in accordance with the terms of the
 * license agreement you entered into with cortical.io GmbH.
 ******************************************************************************/
package io.cortical.rest.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * 
 * A Term model.
 */
@XmlRootElement(name = "Term")
public class Term extends Model {
    
    private String term;
    private double df;
    private double score;
    private String[] posTypes;
    private Fingerprint fingerprint;
    
    /**
     * 
     * Creates a new instance of {@link Term}
     *
     */
    public Term() {
        
    }
    /**
     * Creates a new instance of {@link Term}.
     * 
     * @param term a {@link String} containing the term
     */
    public Term(String term) {
        this.term = term;
    }
    
    /**
     * Creates a new instance of {@link Term}.
     * 
     * @param term a {@link String} containing the term
     * @param positions the positions representing the <code>term</code>
     */
    public Term(String term, int[] positions) {
        this.term = term;
        this.fingerprint = new Fingerprint(positions);
    }

    /**
     * Gets the term.
     *
     * @return the term
     */
    @JsonProperty(SerializationConstants.TERM_STRING_PROPERTYLABEL)
    public String getTerm() {
        return term;
    }

    /**
     * Gets the document frequency.
     *
     * @return the df
     */
    @JsonProperty(SerializationConstants.DF_STRING_PROPERTYLABEL)
    public double getDf() {
        return df;
    }

    /**
     * Gets the score.
     *
     * @return the score
     */
    @JsonProperty(SerializationConstants.SCORE_PROPRETY_LABEL)
    public double getScore() {
        return score;
    }

    /**
     * Gets the posTypes.
     *
     * @return the posTypes
     */
    @JsonProperty(SerializationConstants.POS_TYPES_PROPRETY_LABEL)
    public String[] getPosTypes() {
        return posTypes;
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
     * Sets the document frequency.
     *
     * @param df the df to set
     */
    protected void setDf(double df) {
        this.df = df;
    }

    /**
     * Sets the score.
     *
     * @param score the score to set
     */
    protected void setScore(double score) {
        this.score = score;
    }

    /**
     * Sets the posTypes.
     *
     * @param posTypes the posTypes to set
     */
    protected void setPosTypes(String[] posTypes) {
        this.posTypes = posTypes;
    }

    /**
     * Sets the term.
     *
     * @param term the term to set
     */
    public void setTerm(String term) {
        this.term = term;
    }

    /**
     * Sets the fingerprint.
     *
     * @param fingerprint the fingerprint to set
     */
    public void setFingerprint(Fingerprint fingerprint) {
        this.fingerprint = fingerprint;
    }
    
}
