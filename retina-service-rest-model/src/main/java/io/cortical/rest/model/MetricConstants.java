/*******************************************************************************
 * Copyright (c) cortical.io GmbH. All rights reserved.
 *  
 * This software is confidential and proprietary information.
 * You shall use it only in accordance with the terms of the
 * license agreement you entered into with cortical.io GmbH.
 ******************************************************************************/
package io.cortical.rest.model;


/**
 * Contains the the names of the various distance metrics as constants.
 * 
 */
abstract class MetricConstants {
    
    /** Cosine Similarity. */
    public static final String COSINE_SIMILARITY = "Cosine-Similarity";
    /** Euclidean Distance. */
    public static final String EUCLIDEAN_DISTANCE = "Euclidean-Distance";
    /** Jaccard Distance. */
    public static final String JACCARD_DISTANCE = "Jaccard-Distance";
    /** The total number of overlapping bits of the two fingerprints. **/
    public static final String OVERLAPPING_ALL = "Overlapping-all";
    /** The percentage of bits of the left-hand (first) fingerprint which overlap with the right-hand (second) fingerprint. */
    public static final String OVERLAPPING_LEFT_RIGHT = "Overlapping-left-right";
    /** The percentage of bits of the right-hand (second) fingerprint which overlap with the left-hand (first) fingerprint. */
    public static final String OVERLAPPING_RIGHT_LEFT = "Overlapping-right-left";
    /** Weighted scoring which takes the topology into account. */
    public static final String WEIGHTED_SCORING = "Weighted-Scoring";
    /** Size in bits of left fingerprint. */
    public static final String SIZE_LEFT = "Size-left";
    /** Size in bits of right fingerprint. */
    public static final String SIZE_RIGHT = "Size-right";
}
