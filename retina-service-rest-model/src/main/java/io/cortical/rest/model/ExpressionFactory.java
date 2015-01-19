/*******************************************************************************
 * Copyright (c) cortical.io GmbH. All rights reserved.
 *  
 * This software is confidential and proprietary information.
 * You shall use it only in accordance with the terms of the
 * license agreement you entered into with cortical.io GmbH.
 ******************************************************************************/
package io.cortical.rest.model;


import com.fasterxml.jackson.core.JsonProcessingException;
import static org.apache.commons.lang3.ArrayUtils.addAll;


/**
 * The Retinas Expression factory.
 * Contains methods to construct all available expressions supported by the retina.
 * 
 */
public class ExpressionFactory {
    
    /**
     * 
     * The base expression model.
     * Expression - a type of retina model which enables the creation of more complicated requests.
     *
     */
    public static abstract class ExpressionModel extends Model {
        
        /**
         * Add a new model to the expression.
         * 
         * @param models : models to add.
         * @return an expression model with items added to it.
         */
        public abstract ExpressionModel add(Model... models);
        
        /** {@inheritDoc} */
        @Override
        public String toJson() throws JsonProcessingException {
            return super.toJson();
        }
        
    }
    
    
    private static class AndExpression extends ExpressionModel {
        
        private Model[] and = { };
        
        /** {@inheritDoc} */
        @Override
        public ExpressionModel add(Model... models) {
            and = addAll(and, models);
            return this;
        }
    }
    
    
    private static class OrExpression extends ExpressionModel {
        private Model[] or = { };
        
        /** {@inheritDoc} */
        @Override
        public ExpressionModel add(Model... models) {
            or = addAll(or, models);
            return this;
        }
        
    }
    
    
    private static class XorExpression extends ExpressionModel {
        private Model[] xor = { };
        
        /** {@inheritDoc} */
        @Override
        public ExpressionModel add(Model... models) {
            xor = addAll(xor, models);
            return this;
        }
    }
    
    
    private static class SubExpression extends ExpressionModel {
        private Model[] sub = { };
        
        /** {@inheritDoc} */
        @Override
        public ExpressionModel add(Model... models) {
            sub = addAll(sub, models);
            return this;
        }
    }
    
    /**
     * 
     * Create new And expression.
     * 
     * @param models : models included in the And expression.
     * @return newly created And expression.
     */
    public static ExpressionModel and(Model... models) {
        return new AndExpression().add(models);
    }
    
    /**
     * 
     * Create new Or expression.
     * 
     * @param models : models included in the Or expression.
     * @return newly created Or expression.
     */
    public static ExpressionModel or(Model... models) {
        return new OrExpression().add(models);
    }
    
    /**
     * 
     * Create new Xor expression.
     * 
     * @param models : models included in the Xor expression.
     * @return newly created Xor expression.
     */
    public static ExpressionModel xor(Model... models) {
        return new XorExpression().add(models);
    }
    
    /**
     * 
     * Create the Sub expression.
     * @param models : models included in the Sub expression.
     * @return newly created Sub expression.
     */
    public static ExpressionModel sub(Model... models) {
        return new SubExpression().add(models);
    }
    
    /**
     * 
     * Create new {@link Text} object with the input text.
     * 
     * @param text : A text model. 
     * @return newly created text retina model.
     */
    public static Text text(String text) {
        return new Text(text);
    }
    
    /**
     * 
     * Create new {@link Term} object with the input term.
     * 
     * @param term : A new term model content.
     * @return new created term retina's model.
     */
    public static Term term(String term) {
        return new Term(term);
    }
    
    /**
     * 
     * Create new {@link FingerPrint} object with the input positions.
     * 
     * @param items : fingerprint positions.
     * @return new fingerprint containing the input positions.
     */
    public static Fingerprint fingerprint(int... items) {
        Fingerprint fingerprint = new Fingerprint(items);
        return fingerprint;
    }
}
