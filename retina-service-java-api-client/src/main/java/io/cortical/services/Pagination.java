/*******************************************************************************
 * Copyright (c) cortical.io GmbH. All rights reserved.
 *  
 * This software is confidential and proprietary information.
 * You shall use it only in accordance with the terms of the
 * license agreement you entered into with cortical.io GmbH.
 ******************************************************************************/
package io.cortical.services;



/**
 * The Pagination configuration.
 * 
 */
public class Pagination {
    /**
     * 
     */
    private Integer startIndex;
    private Integer maxResults;
    

    /**
     * Creates a new instance of {@link Pagination}.
     * 
     */
    public Pagination() {
        
    }
    
    
    /**
     * Creates a new instance of {@link Pagination}.
     * 
     * @param startIndex
     * @param maxResults
     */
    public Pagination(Integer startIndex, Integer maxResults) {
        this.startIndex = startIndex;
        this.maxResults = maxResults;
    }
    
    /**
     * Gets the startIndex.
     *
     * @return the startIndex
     */
    public Integer getStartIndex() {
        return startIndex;
    }
    
    /**
     * Sets the startIndex.
     *
     * @param startIndex the startIndex to set
     */
    public void setStartIndex(Integer startIndex) {
        this.startIndex = startIndex;
    }
    
    /**
     * Gets the maxResults (per page).
     *
     * @return the maxResults
     */
    public Integer getMaxResults() {
        return maxResults;
    }
    
    /**
     * Sets the maxResults (per page).
     *
     * @param maxResults the maxResults to set
     */
    public void setMaxResults(Integer maxResults) {
        this.maxResults = maxResults;
    }
    
    /** {@inheritDoc} */
    @Override
    public String toString() {
        return "startIndex : " + startIndex + "  maxResults: " + maxResults;
    }
}
