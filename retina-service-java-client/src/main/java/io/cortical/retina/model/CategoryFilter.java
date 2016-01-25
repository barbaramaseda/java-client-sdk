/*******************************************************************************
 * Copyright (c) cortical.io GmbH. All rights reserved.
 *
 * This software is confidential and proprietary information.
 * You shall use it only in accordance with the terms of the
 * license agreement you entered into with cortical.io GmbH.
 ******************************************************************************/
package io.cortical.retina.model;



/** Generated. **/
public class CategoryFilter {
    /* The descriptive label for a CategoryFilter name */
    private String categoryName = null;
    /* The positions of a Fingerprint */
    private int[] positions = new int[] {};
    
    public String getCategoryName() {
        return categoryName;
    }
    
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
    
    public int[] getPositions() {
        return positions;
    }
    
    public void setPositions(int[] positions) {
        this.positions = positions;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class CategoryFilter {\n");
        sb.append("    categoryName: ").append(categoryName).append("\n");
        sb.append("    positions: ").append(positions).append("\n");
        sb.append("}\n");
        return sb.toString();
    }
}

