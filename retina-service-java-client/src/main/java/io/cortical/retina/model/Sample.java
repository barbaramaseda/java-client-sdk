package io.cortical.retina.model;

import java.util.ArrayList;
import java.util.List;

/**
 * A classification sample containing lists of positive {@link Text}
 * examples (mandatory), and potentially negative examples (optional).
 * 
 * This container's fields are named such that the resulting json
 * fields match the required field naming of the classify endpoint.
 */
public class Sample {
    private List<Text> positiveExamples;
    private List<Text> negativeExamples;
    public Sample() {
        positiveExamples = new ArrayList<>();
        negativeExamples = new ArrayList<>();
    }
    
    /**
     * Sets the specified list as the list of positive
     * examples.
     * @param pos
     */
    public void setPositiveExamples(List<Text> pos) {
        this.positiveExamples = pos;
    }
    
    /**
     * Returns the list of positive examples.
     * @return
     */
    public List<Text> getPositiveExamples() {
        return positiveExamples;
    }
    
    /**
     * Sets the specified list as the list of negative
     * examples.
     * @param neg
     */
    public void setNegativeExamples(List<Text> neg) {
        this.negativeExamples = neg;
    }
    
    /**
     * Returns the list of negative examples.
     * @return
     */
    public List<Text> getNegativeExamples() {
        return negativeExamples;
    }
    
    /**
     * Adds a single positive {@link Text} to this {@code Sample}'s
     * list of positive examples.
     * @param text
     */
    public void addPositiveExample(Text text) {
        positiveExamples.add(text);
    }
    
    /**
     * Adds a single negative {@link Text} to this {@code Sample}'s
     * list of negative examples.
     * @param text
     */
    public void addNegativeExample(Text text) {
        negativeExamples.add(text);
    }
    
    /**
     * Adds a single positive String to this {@code Sample}'s
     * list of positive examples.
     * @param text
     */
    public void addPositiveExample(String text) {
        positiveExamples.add(new Text(text));
    }
    
    /**
     * Adds a single negative String to this {@code Sample}'s
     * list of negative examples.
     * @param text
     */
    public void addNegativeExample(String text) {
        negativeExamples.add(new Text(text));
    }
    
    /**
     * Adds the specified list to this {@code Sample}'s list 
     * of positive examples.
     * @param positiveTexts
     */
    public void addAllPositive(List<Text> positiveTexts) {
        if(positiveTexts == null) return;
        positiveExamples.addAll(positiveTexts);
    }
    
    /**
     * Adds the specified list to this {@code Sample}'s list 
     * of negative examples.
     * @param negativeTexts
     */
    public void addAllNegative(List<Text> negativeTexts) {
        if(negativeTexts == null) return;
        negativeExamples.addAll(negativeTexts);
    }
    
    /**
     * Adds the specified list to this {@code Sample}'s list 
     * of positive examples.
     * @param positiveTexts
     */
    public void addAllPositive(String... positiveTexts) {
        if(positiveTexts == null) return;
        for(String s : positiveTexts) {
            positiveExamples.add(new Text(s));
        }
    }
    
    /**
     * Adds the specified list to this {@code Sample}'s list 
     * of negative examples.
     * @param negativeTexts
     */
    public void addAllNegative(String... negativeTexts) {
        if(negativeTexts == null) return;
        for(String s : negativeTexts) {
            negativeExamples.add(new Text(s));
        }
    }
}