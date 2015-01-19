/*******************************************************************************
 * Copyright (c) cortical.io GmbH. All rights reserved.
 *  
 * This software is confidential and proprietary information.
 * You shall use it only in accordance with the terms of the
 * license agreement you entered into with cortical.io GmbH.
 ******************************************************************************/
package io.cortical.rest.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;
import javax.xml.bind.annotation.XmlElement;
import static io.cortical.rest.model.MetricConstants.COSINE_SIMILARITY;
import static io.cortical.rest.model.MetricConstants.EUCLIDEAN_DISTANCE;
import static io.cortical.rest.model.MetricConstants.JACCARD_DISTANCE;
import static io.cortical.rest.model.MetricConstants.OVERLAPPING_ALL;
import static io.cortical.rest.model.MetricConstants.OVERLAPPING_LEFT_RIGHT;
import static io.cortical.rest.model.MetricConstants.OVERLAPPING_RIGHT_LEFT;
import static io.cortical.rest.model.MetricConstants.SIZE_LEFT;
import static io.cortical.rest.model.MetricConstants.SIZE_RIGHT;
import static io.cortical.rest.model.MetricConstants.WEIGHTED_SCORING;


/**
 * 
 * A container for the various distance metrics.
 */
public class Metric {
    /**
     * 
     * Creates a new instance of {@link Metric}.
     *
     */
    public Metric() {
        
    }
    /**
     * 
     */
    public Metric(Map<String, Double> resultMap) {
        this.cosineSimilarity = resultMap.get(COSINE_SIMILARITY);
        this.euclideanDistance = resultMap.get(EUCLIDEAN_DISTANCE);
        this.jaccardDistance = resultMap.get(JACCARD_DISTANCE);
        this.overlappingAll = resultMap.get(OVERLAPPING_ALL).intValue();
        this.overlappingLeftRight = resultMap.get(OVERLAPPING_LEFT_RIGHT);
        this.overlappingRightLeft = resultMap.get(OVERLAPPING_RIGHT_LEFT);
        this.sizeLeft = resultMap.get(SIZE_LEFT).intValue();
        this.sizeRight = resultMap.get(SIZE_RIGHT).intValue();
        this.weightedScoring = resultMap.get(WEIGHTED_SCORING);

    }
    
    private double cosineSimilarity;
    private double euclideanDistance;
    private double jaccardDistance;
    private int overlappingAll;
    private double overlappingLeftRight;
    private double overlappingRightLeft;
    private int sizeLeft;
    private int sizeRight;
    private double weightedScoring;
    /**
     * Gets the cosineSimilarity.
     *
     * @return the cosineSimilarity
     */
    @XmlElement(name = "cosineSimilarity")
    @JsonProperty("cosineSimilarity")
    public double getCosineSimilarity() {
        return cosineSimilarity;
    }
    /**
     * Gets the euclideanDistance.
     *
     * @return the euclideanDistance
     */
    @XmlElement(name = "euclideanDistance")
    @JsonProperty("euclideanDistance")
    public double getEuclideanDistance() {
        return euclideanDistance;
    }
    /**
     * Gets the jaccardDistance.
     *
     * @return the jaccardDistance
     */
    @XmlElement(name = "jaccardDistance")
    @JsonProperty("jaccardDistance")
    public double getJaccardDistance() {
        return jaccardDistance;
    }
    /**
     * Gets the overlappingAll value.
     *
     * @return the overlappingAll
     */
    @XmlElement(name = "overlappingAll")
    @JsonProperty("overlappingAll")
    public int getOverlappingAll() {
        return overlappingAll;
    }
    /**
     * Gets the overlappingLeftRight value.
     *
     * @return the overlappingLeftRight
     */
    @XmlElement(name = "overlappingLeftRight")
    @JsonProperty("overlappingLeftRight")
    public double getOverlappingLeftRight() {
        return overlappingLeftRight;
    }
    /**
     * Gets the overlappingRightLeft value.
     *
     * @return the overlappingRightLeft
     */
    @XmlElement(name = "overlappingRightLeft")
    @JsonProperty("overlappingRightLeft")
    public double getOverlappingRightLeft() {
        return overlappingRightLeft;
    }
    /**
     * Gets the sizeLeft value.
     *
     * @return the sizeLeft
     */
    @XmlElement(name = "sizeLeft")
    @JsonProperty("sizeLeft")
    public int getSizeLeft() {
        return sizeLeft;
    }
    /**
     * Gets the sizeRight value.
     *
     * @return the sizeRight
     */
    @XmlElement(name = "sizeRight")
    @JsonProperty("sizeRight")
    public int getSizeRight() {
        return sizeRight;
    }
    /**
     * Gets the weightedScoring value.
     *
     * @return the weightedScoring
     */
    @XmlElement(name = "weightedScoring")
    @JsonProperty("weightedScoring")
    public double getWeightedScoring() {
        return weightedScoring;
    }
    /**
     * Sets the cosineSimilarity.
     *
     * @param cosineSimilarity the cosineSimilarity to set
     */
    protected void setCosineSimilarity(double cosineSimilarity) {
        this.cosineSimilarity = cosineSimilarity;
    }
    /**
     * Sets the euclideanDistance.
     *
     * @param euclideanDistance the euclideanDistance to set
     */
    protected void setEuclideanDistance(double euclideanDistance) {
        this.euclideanDistance = euclideanDistance;
    }
    /**
     * Sets the jaccardDistance.
     *
     * @param jaccardDistance the jaccardDistance to set
     */
    protected void setJaccardDistance(double jaccardDistance) {
        this.jaccardDistance = jaccardDistance;
    }
    /**
     * Sets the overlappingAll value.
     *
     * @param overlappingAll the overlappingAll to set
     */
    protected void setOverlappingAll(int overlappingAll) {
        this.overlappingAll = overlappingAll;
    }
    /**
     * Sets the overlappingLeftRight value.
     *
     * @param overlappingLeftRight the overlappingLeftRight to set
     */
    protected void setOverlappingLeftRight(double overlappingLeftRight) {
        this.overlappingLeftRight = overlappingLeftRight;
    }
    /**
     * Sets the overlappingRightLeft value.
     *
     * @param overlappingRightLeft the overlappingRightLeft to set
     */
    protected void setOverlappingRightLeft(double overlappingRightLeft) {
        this.overlappingRightLeft = overlappingRightLeft;
    }
    /**
     * Sets the sizeLeft value.
     *
     * @param sizeLeft the sizeLeft to set
     */
    protected void setSizeLeft(int sizeLeft) {
        this.sizeLeft = sizeLeft;
    }
    /**
     * Sets the sizeRight value.
     *
     * @param sizeRight the sizeRight to set
     */
    protected void setSizeRight(int sizeRight) {
        this.sizeRight = sizeRight;
    }
    /**
     * Sets the weightedScoring value.
     *
     * @param weightedScoring the weightedScoring to set
     */
    protected void setWeightedScoring(double weightedScoring) {
        this.weightedScoring = weightedScoring;
    }
    
    
}
