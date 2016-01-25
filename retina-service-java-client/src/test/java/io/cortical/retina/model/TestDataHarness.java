/*******************************************************************************
 * Copyright (c) cortical.io GmbH. All rights reserved.
 *  
 * This software is confidential and proprietary information.
 * You shall use it only in accordance with the terms of the
 * license agreement you entered into with cortical.io GmbH.
 ******************************************************************************/
package io.cortical.retina.model;

import static java.util.Arrays.sort;
import io.cortical.retina.core.PosType;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.RandomStringUtils;


/**
 * 
 * Test data Generator.
 */
public abstract class TestDataHarness {
    /**
     * 
     */
    private static final int FINGERPRINT_LENGTH = 100;
    private static final int MAX_POSITION = 16384;
    private static final int SEED = 42;
    
    /**
     * Create dummy  {@link Fingerprint}.
     * 
     * @return dummy fingerprint.
     */
    public static Fingerprint createFingerprint() {
        Random random = new Random(SEED);
        Set<Integer> positionSet = new LinkedHashSet<>();
        while (positionSet.size() <= FINGERPRINT_LENGTH) {
            positionSet.add(random.nextInt(MAX_POSITION));
        }
        
        Integer[] positionsInteger = new Integer[FINGERPRINT_LENGTH];
        positionsInteger = positionSet.toArray(positionsInteger);
        sort(positionsInteger);
        return new Fingerprint(ArrayUtils.toPrimitive(positionsInteger));
    }
    
    /**
     * Create dummy list of {@link Fingerprint}. 
     * 
     * @param count     count of dummy fingerprints.
     * @return new generated dummy fingerprints.
     */
    public static List<Fingerprint> createFingerprints(int count) {
        List<Fingerprint> fingerprints = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            fingerprints.add(createFingerprint());
        }
        return fingerprints;
    }
    
    /**
     * Create dummy  {@link Fingerprint}.
     * @param sparsity      percentage of on bits
     * @return dummy fingerprint.
     */
    public static Fingerprint createFingerprint(double sparsity) {
        Random random = new Random(SEED);
        Set<Integer> positionSet = new LinkedHashSet<>();
        while (positionSet.size() <= ((double)(MAX_POSITION)) * sparsity) {
            positionSet.add(random.nextInt(MAX_POSITION));
        }
        
        Integer[] positionsInteger = new Integer[FINGERPRINT_LENGTH];
        positionsInteger = positionSet.toArray(positionsInteger);
        sort(positionsInteger);
        return new Fingerprint(ArrayUtils.toPrimitive(positionsInteger));
    }
    
    /**
     * Create dummy list of {@link Fingerprint}. 
     * 
     * @param count     count of dummy fingerprints.
     * @param sparsity  percentage of on bits
     * @return new generated dummy fingerprints.
     */
    public static List<Fingerprint> createFingerprints(int count, double sparsity) {
        List<Fingerprint> fingerprints = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            fingerprints.add(createFingerprint(sparsity));
        }
        return fingerprints;
    }
    
    /**
     * Create dummy {@link Text}. 
     * 
     * @return newly created dummy {@link Text}.
     */
    public static Text createText() {
        String randomString = RandomStringUtils.random(FINGERPRINT_LENGTH);
        return new Text(randomString);
    }
    
    /**
     * Create dummy list of {@link Text}. 
     * 
     * @param count     count of dummy {@link Text}.
     * @return list of {@link Text}.
     */
    public static List<Text> createTexts(int count) {
        List<Text> texts = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            texts.add(createText());
        }
        return texts;
    }
    
    /**
     * 
     * Create random strings.
     * @param count : count of items in a result.
     * @return list of random strings.
     */
    public static List<String> createStrings(int count) {
        List<String> strings = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            strings.add(RandomStringUtils.random(FINGERPRINT_LENGTH));
        }
        return strings;
    }
    
    /**
     * Create dummy {@link Context}.
     * 
     * @param countextId : identifier of the context.
     * @return dummy {@link Context}
     */
    public static Context createContext(int countextId) {
        Context context = new Context();
        context.setContextId(countextId);
        context.setContextLabel(RandomStringUtils.random(10));
        context.setFingerprint(createFingerprint());
        return context;
    }
    
    /**
     * Create list of dummy {@link Context}s.
     * 
     * @param count : count of contexts.
     * @return list of dummy {@link Context}s
     */
    public static List<Context> createContexts(int count) {
        List<Context> contexts = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            contexts.add(createContext(i));
        }
        return contexts;
    }
    
    /**
     * Create dummy {@link Term}.
     * 
     * @return dummy {@link Term}.
     */
    public static Term createTerm() {
        Term term = new Term();
        term.setFingerprint(createFingerprint());
        term.setDf(0.4);
        String[] posTypes = { PosType.ADJECTIVE.name(), PosType.NOUN.name() };
        term.setPosTypes(posTypes);
        term.setScore(0.2);
        term.setTerm(RandomStringUtils.random(10));
        return term;
    }
    
    /**
     * 
     * Create list of dummy {@link Term}s.
     * @param count : count of items in a result list.
     * @return dummy {@link Term}s.
     */
    public static List<Term> createTerms(int count) {
        List<Term> terms = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            terms.add(createTerm());
        }
        return terms;
    }
    
    /**
     * Create new dummy retina.
     * 
     * @param name : name of new dummy retina.
     * @return dummy retina.
     */
    public static Retina createRetina(String name) {
        Retina retina = new Retina(name, name + "small description", 1000L, 1000, 1000);
        return retina;
    }
    
    /**
     * Create list of new dummy retinas.
     * 
     * @param count : count of new items.
     * @return list of dummy retinas.
     */
    public static List<Retina> createRetinas(int count) {
        List<Retina> retinas = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            retinas.add(createRetina(RandomStringUtils.random(10)));
        }
        return retinas;
    }
    
    /**
     * 
     * Create dummy image. 
     * @return image.
     */
    public static Image createImage() {
        Image image = new Image(new byte[1], createFingerprint());
        return image;
    }
    
    
    /**
     * Create list of new dummy images.
     * 
     * @param count : count of new items.
     * @return list of dummy images.
     */
    public static List<Image> createImages(int count) {
        List<Image> images = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            images.add(createImage());
        }
        return images;
    }
    
    /**
     * Creates and returns a {@link Language} object.
     * @return
     */
    public static Language createLanguage() {
        Language lr = new Language();
        lr.setLanguage("English");
        lr.setIso_tag("en");
        lr.setWiki_url("http://en.wikipedia.org/wiki/English_language");
        return lr;
    }
}
