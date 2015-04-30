/*******************************************************************************
 * Copyright (c) cortical.io GmbH. All rights reserved.
 *
 * This software is confidential and proprietary information.
 * You shall use it only in accordance with the terms of the
 * license agreement you entered into with cortical.io GmbH.
 ******************************************************************************/
package io.cortical.rest.model;

import java.util.ArrayList;
import java.util.List;


/** Generated. **/
public class CategoryFilter {
    /* The descriptive label for a CategoryFilter name */
    private String categoryName = null;
    /* The positions of a Fingerprint */
    private List<Integer> positions = new ArrayList<Integer>();
    
    public String getCategoryName() {
        return categoryName;
    }
    
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
    
    public List<Integer> getPositions() {
        return positions;
    }
    
    public void setPositions(List<Integer> positions) {
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

