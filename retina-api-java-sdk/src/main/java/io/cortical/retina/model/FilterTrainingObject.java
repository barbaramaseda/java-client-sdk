/*******************************************************************************
 * Copyright (c) cortical.io GmbH. All rights reserved.
 *
 * This software is confidential and proprietary information.
 * You shall use it only in accordance with the terms of the
 * license agreement you entered into with cortical.io GmbH.
 ******************************************************************************/
package io.cortical.retina.model;

import java.util.ArrayList;
import java.util.List;


/** Generated. **/
public class FilterTrainingObject {
    /* Get positive examples */
    private List<Text> positiveExamples = new ArrayList<Text>();
    /* Get negative examples */
    private List<Text> negativeExamples = new ArrayList<Text>();
    
    public List<Text> getPositiveExamples() {
        return positiveExamples;
    }
    
    public void setPositiveExamples(List<Text> positiveExamples) {
        this.positiveExamples = positiveExamples;
    }
    
    public List<Text> getNegativeExamples() {
        return negativeExamples;
    }
    
    public void setNegativeExamples(List<Text> negativeExamples) {
        this.negativeExamples = negativeExamples;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class FilterTrainingObject {\n");
        sb.append("    positiveExamples: ").append(positiveExamples).append("\n");
        sb.append("    negativeExamples: ").append(negativeExamples).append("\n");
        sb.append("}\n");
        return sb.toString();
    }
}
