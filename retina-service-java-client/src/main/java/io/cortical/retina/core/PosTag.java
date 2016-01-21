/*******************************************************************************
 * Copyright (c) cortical.io GmbH. All rights reserved.
 *  
 * This software is confidential and proprietary information.
 * You shall use it only in accordance with the terms of the
 * license agreement you entered into with cortical.io GmbH.
 ******************************************************************************/
package io.cortical.retina.core;

/**
 * 
 * Available Part of Speech tags.
 */
public enum PosTag {
    CC, CD, DT, EX, FW, IN, JJ, JJR, JJS, JJSS, LRB, LS, MD, NN, NNP, NNPS, NNS, NP, NPS, PDT, POS, PP, PRPR$, PRP,
    PRP$, RB, RBR, RBS, RP, STAART, SYM, TO, UH, VBD, VBG, VBN, VBP, VB, VBZ, WDT, WP$, WP, WRB;
    
    /**
     * Gets the label
     *
     * @return the label
     */
    public String getLabel() {
        if (this.equals(LRB)) {
            return "-LRB-";
        }
        return name();
    }
}
