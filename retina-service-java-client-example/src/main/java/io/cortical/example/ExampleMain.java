/*******************************************************************************
 * Copyright (c) cortical.io GmbH. All rights reserved.
 *  
 * This software is confidential and proprietary information.
 * You shall use it only in accordance with the terms of the
 * license agreement you entered into with cortical.io GmbH.
 ******************************************************************************/
package io.cortical.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.cortical.rest.model.Context;
import io.cortical.rest.model.Fingerprint;
import io.cortical.rest.model.Image;
import io.cortical.rest.model.Metric;
import io.cortical.rest.model.Retina;
import io.cortical.rest.model.Term;
import io.cortical.rest.model.Text;
import io.cortical.services.Compare;
import io.cortical.services.Compare.CompareModels;
import io.cortical.services.Expressions;
import io.cortical.services.Images;
import io.cortical.services.Pagination;
import io.cortical.services.PosTag;
import io.cortical.services.PosType;
import io.cortical.services.RetinaApis;
import io.cortical.services.Retinas;
import io.cortical.services.Terms;
import io.cortical.services.Texts;
import io.cortical.services.api.client.ApiException;
import io.cortical.services.api.client.api.ExpressionsApi;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import org.apache.commons.logging.Log;
import static io.cortical.rest.model.ExpressionFactory.and;
import static io.cortical.rest.model.ExpressionFactory.or;
import static io.cortical.rest.model.ExpressionFactory.sub;
import static io.cortical.rest.model.ExpressionFactory.term;
import static io.cortical.rest.model.ExpressionFactory.text;
import static io.cortical.rest.model.ExpressionFactory.xor;
import static io.cortical.services.RetinaApis.getInfo;
import static org.apache.commons.logging.LogFactory.getLog;


/**
 * Usage examples for cortical.io's Retina APIs
 *
 */

public class ExampleMain {
    /**
     * 
     */
    private static final Log LOG = getLog(ExampleMain.class);
    private static final String RETINA_NAME = "en_associative";    
    private static final String RETINA_IP = "api.cortical.io";
    private static final Short RETINA_PORT = 80;
    /* TODO: You will need to replace this api key with your api key. */
    private static final String API_KEY = "your API key";
    
    private static final Text TEXT_1 = new Text("the first text to use");
    private static final Text TEXT_2 = text("the second text to use");
    private static final Term TERM_1 = new Term("term");
    private static final Term TERM_2 = term("second");
    
    private final RetinaApis retinaApisInstance;
    
    public ExampleMain() {
        retinaApisInstance = new RetinaApis(RETINA_NAME, RETINA_IP, API_KEY);
    }
    
    /**
     * 
     * Start point.
     * @param args : no arguments are required.
     * @throws ApiException 
     */
    public static void main(String[] args) throws Exception {
        LOG.info("Start the example....");
        ExampleMain start = new ExampleMain();
        start.compareApiUsage();
        start.expressionsApiUsage();
        start.imageApiUsage();
        start.retinasApiUsage();
        start.termsApiUsage();
        start.textApiUsage();
    }
    
    /**
      * The Compare API usage. 
     * 
     * @throws ApiException
     * @throws JsonProcessingException
     */
    private void compareApiUsage() throws ApiException, JsonProcessingException {
        LOG.info("The Compare API usage.");
        Compare compareApiInstance = retinaApisInstance.compareApi();
        
        LOG.info("Compare API: compare");
        Metric metric = compareApiInstance.compare(new Term("apple"), new Term("banana"));
        logMetric(metric);
        
        String inputText =
                "Gustav Klimt (July 14, 1862 – February 6, 1918) was an Austrian symbolist painter and one "
                        + "of the most prominent members of the Vienna Secession movement. Klimt is noted for his paintings, "
                        + "murals, sketches, and other objets d'art. Klimt's primary subject was the female body;[1] "
                        + "his works are marked by a frank eroticism.[2]";
        metric = compareApiInstance.compare(new Term("painter"), new Text(inputText));
        
        logMetric(metric);
        
        
        LOG.info("Compare API: compareBulk");
        
        CompareModels toCompare1 = new CompareModels(new Term("apple"), new Term("banana"));
        CompareModels toCompare2 = new CompareModels(new Term("banana"), new Term("fruit"));
        CompareModels toCompare3 = new CompareModels(new Term("apple"), new Term("orange"));
        
        CompareModels[] toCompareBulk = {
                toCompare1,
                toCompare2,
                toCompare3
        };
        
        Metric[] metrics = compareApiInstance.compareBulk(toCompareBulk);
        for (Metric metricFromBulk: metrics) {
            logMetric(metricFromBulk);
        }
        
    }
    
    private void logMetric(Metric metric) {
        LOG.info("Metric: Cosine Similarity: " + metric.getCosineSimilarity() + "  Euclidean Distance: "
                + metric.getEuclideanDistance() + "  Jaccard Distance: " + metric.getJaccardDistance()
                + "  Over lappingAll: " + metric.getOverlappingAll() + "  Over lapping Left Right: "
                + metric.getOverlappingLeftRight() + "  Over lapping Right Left: " + metric.getOverlappingRightLeft()
                + "  Size Left: " + metric.getSizeLeft() + "  Size Right: " + metric.getSizeRight()
                + "  Weighted Scoring: " + metric.getWeightedScoring());
    }
    
    /**
     * The Expressions API usage.  
     * 
     * @throws JsonProcessingException
     * @throws ApiException
     */
    private void expressionsApiUsage() throws JsonProcessingException, ApiException {
        
        LOG.info("The Expressions API usage.");
        Expressions api = retinaApisInstance.expressionsApi();
        ExpressionsApi expressionsApi = new ExpressionsApi(API_KEY);
        expressionsApi.setBasePath("http://api.cortical.io/rest");
        
        /**
         * 
         */
        LOG.info("Expressions API: getContextsForExpression");
        List<List<Context>> bulkContexts = api.getContextsBulk(TERM_1, TERM_2, TERM_2);
        logBulkContexts(bulkContexts);
        
        bulkContexts = api.getContextsBulk(true, TERM_1, or(TERM_2, TEXT_2).add(TERM_1), TEXT_1);
        logBulkContexts(bulkContexts);
        
        bulkContexts = api.getContextsBulk(1.0, TERM_1, TERM_2, or(TERM_2, TEXT_2).add(TERM_1));
        logBulkContexts(bulkContexts);
        
        bulkContexts = api.getContextsBulk(true, 1.0, TERM_1, or(TERM_2, TEXT_2).add(TERM_1), TEXT_1);
        logBulkContexts(bulkContexts);
        
        bulkContexts = api.getContextsBulk(new Pagination(0, 2), true, 1.0, TERM_1, TERM_2, or(TERM_2, TEXT_2));
        logBulkContexts(bulkContexts);
        /**
         * 
         */
        LOG.info("Expressions API: getContextsForExpression");
        List<Context> contexts = api.getContexts(TERM_1);
        logContext(contexts);
        
        contexts = api.getContexts(or(TERM_2, TEXT_2));
        logContext(contexts);
        
        contexts = api.getContexts(true, or(TERM_2, TEXT_2));
        logContext(contexts);
        
        contexts = api.getContexts(0.1, sub(TERM_2, TERM_1));
        logContext(contexts);
        
        contexts = api.getContexts(true, 1.0, and(TERM_2, TERM_1));
        logContext(contexts);
        
        contexts = api.getContexts(new Pagination(0, 2), true, 1.0, xor(TERM_2, TERM_1));
        logContext(contexts);
        /**
         * 
         */
        LOG.info("Expressions API: getSimilarTermsForBulkExpressionContext");
        List<List<Term>> bulkTerms =
                api.getSimilarTermsBulk(null, PosType.ADJECTIVE, TERM_1, and(TERM_2, TEXT_2), TEXT_1);
        logBulkTerms(bulkTerms);
        
        bulkTerms = api.getSimilarTermsBulk(1, PosType.ADJECTIVE, true, TERM_1, TERM_2, TEXT_1);
        logBulkTerms(bulkTerms);
        
        bulkTerms = api.getSimilarTermsBulk(1, PosType.ADJECTIVE, 1.0, TERM_1, and(TERM_2, TEXT_2).add(TERM_1), TEXT_1);
        logBulkTerms(bulkTerms);
        
        bulkTerms =
                api.getSimilarTermsBulk(1, PosType.ADJECTIVE, true, 1.0, TERM_1, and(TERM_2, TEXT_2).add(TERM_1),
                        TEXT_1);
        logBulkTerms(bulkTerms);
        
        bulkTerms =
                api.getSimilarTermsBulk(1, PosType.ADJECTIVE, new Pagination(1, 2), true, 1.0, TERM_1, TERM_2, TEXT_1);
        logBulkTerms(bulkTerms);
        /**
         * 
         */
        LOG.info("Expressions API: resolveExpression");
        Fingerprint fingerprint = api.resolve(TERM_1);
        logFingerprint(fingerprint);
        
        fingerprint = api.resolve(1.0, and(TERM_1, TEXT_1));
        logFingerprint(fingerprint);
        
        /**
         * 
         */
        LOG.info("Expressions API: getSimilarTermsForExpressionContext");
        List<Term> terms = api.getSimilarTerms(null, null, TEXT_1);
        logTerms(terms);
        
        terms = api.getSimilarTerms(TEXT_2);
        logTerms(terms);
        
        String bodyText = "{ \"text\": \"the first text to use\"}";
        List<Term> similarterms =
                expressionsApi.getSimilarTermsForExpressionContext(bodyText, null, null, false, "en_associative", null,
                        null, null);
        logTerms(similarterms);
        
        terms = api.getSimilarTerms(null, PosType.NOUN, and(TERM_2, TEXT_2).add(TERM_1));
        logTerms(terms);
        
    }
    
    
    /**
     * The Image API usage.  
     * 
     * @throws ApiException
     * @throws IOException
     */
    private void imageApiUsage() throws ApiException, IOException {
        LOG.info("The Image API usage.");
        Images api = retinaApisInstance.imageApi();
        
        
        LOG.info("Image API: getImageForExpression");
        try (ByteArrayInputStream inputStream = api.getImage(TEXT_1)) {
            //get image's stream;
            if (inputStream.read() == 0) {
                throw new IllegalStateException("A image stream cannot be received.");
            }
        }
        
        LOG.info("Image API: getOverlayImage");
        try (ByteArrayInputStream inputStream = api.compare(TEXT_1, TEXT_2)) {
            //get image's stream;
            if (inputStream.read() == 0) {
                throw new IllegalStateException("A image stream cannot be received.");
            }
        }
        
        LOG.info("Image API: getImageForBulkExpressions");
        List<Image> images = api.getImageBulk(TEXT_1, TERM_1);
        for (Image image : images) {
            LOG.info("Image: imageData size: " + image.getImageData().length);
        }
    }
    
    /**
     * The Retinas API usage.  
     * 
     * @throws ApiException
     */
    private void retinasApiUsage() throws ApiException {
        LOG.info("The Retinas API usage.");
        
        Retinas api = getInfo("api.cortical.io", API_KEY);
        
        LOG.info("Retinas API: getRetinas");
        List<Retina> retinas = api.getAllRetinas();
        
        for (Retina retina : retinas) {
            LOG.info("Retina:  Name: " + retina.getRetinaName() + "  Description: " + retina.getRetinaDescription()
                    + "  Terms in the retina:  " + retina.getNumberOfTermsInRetina());
        }
        Retina retina = api.retinaByName(RETINA_NAME);
        LOG.info("Retina:  Name: " + retina.getRetinaName() + "  Description: " + retina.getRetinaDescription()
                + "  Terms in the retina:  " + retina.getNumberOfTermsInRetina());
    }
    
    /**
     * The Terms API usage.  
     * 
     * @throws JsonProcessingException
     * @throws ApiException
     */
    private void termsApiUsage() throws JsonProcessingException, ApiException {
        /**
         * 
         */
        LOG.info("The Terms API usage.");
        Terms api = retinaApisInstance.termsApi();
        /**
         * 
         */
        LOG.info("Terms API: getContextsForTerm");
        List<Context> contexts = api.getContexts("apple");
        logContext(contexts);
        contexts = api.getContexts("apple", new Pagination(0, 2), true);
        
        logContext(contexts);
        /**
         * 
         */
        List<Term> terms;
        LOG.info("Terms API: getSimilarTerms");
        
        terms = api.getSimilarTerms("tiger");
        
        logTerms(terms);
        
        terms = api.getSimilarTerms("tiger", null, PosType.NOUN);
        
        logTerms(terms);
        
        //**** ERROR *****
        terms = api.getSimilarTerms("tiger", null, PosType.NOUN, true);
        logTerms(terms);
        /**
         * 
         */
        LOG.info("Terms API: getTerm");
        terms = api.getTerm("apple");
        logTerms(terms);
        System.out.println();
        
        terms = api.getTerm("banana", new Pagination(0, 2), true);
        logTerms(terms);
        System.out.println();
        
        LOG.info("Terms API: getTerm NULL TEST");
        terms = api.getTerm(null);
        logTerms(terms);
    }
    
    /**
     * The Text API usage.  
     * 
     * @throws JsonProcessingException
     * @throws ApiException
     */
    private void textApiUsage() throws ApiException, JsonProcessingException {
        /**
         * 
         */
        String text =
                "Toll IPEC has been an industry leader in environmental sustainability through its purchase of compressed natural gas powered trucks";
        String text2 =
                "Shellshock can serve as a highway for worms and malware to hit your Unix, Linux, and Mac servers, but you can defend against it.";
        String text3 = "However, Shellshock is not as bad as HeartBleed. Not yet, anyway.";
        /**
         * 
         */
        LOG.info("The Text API usage.");
        Texts api = retinaApisInstance.textApi();
        /**
         * 
         */
        LOG.info("Text API: getKeywordsForText");
        List<String> keywords = api.getKeywords(text);
        logKeywords(keywords);
        /**
         * 
         */
        LOG.info("Text API: getRepresentationForText");
        List<Fingerprint> fingerprints = api.getFingerprints(text2);
        logFingerprints(fingerprints);
        /**
         * 
         */
        LOG.info("Text API: getRepresentationsForBulkText");
        fingerprints = api.getFingerprintBulk(0.1, TEXT_1, TEXT_2);
        logFingerprints(fingerprints);
        /**
         * 
         */
        LOG.info("Text API: getSlicesForText");
        List<Text> texts = api.getSlices(text);
        logTexts(texts);
        
        texts = api.getSlices(text3, new Pagination(0, 2), true);
        logTexts(texts);
        
        
        LOG.info("Text API: getTokensForText");
        List<String> tokens = api.getTokens(text2, new PosTag[] { PosTag.CC, PosTag.LRB });
        logTokens(tokens);
        
        tokens = api.getTokens(text2, new PosTag[] { });
        logTokens(tokens);
        
    }
    
    private void logBulkContexts(List<List<Context>> bulkContexts) {
        for (List<Context> list : bulkContexts) {
            logContext(list);
        }
    }
    
    private void logContext(Context context) {
        LOG.info("Context: context_label: "
                + context.getContextLabel()
                + "  Context Id: "
                + context.getContextId()
                + "  Context fingerprint isNullorEmpty: "
                + (context.getFingerprint() == null || context.getFingerprint().getPositions() == null || context
                        .getFingerprint().getPositions().length == 0));
    }
    
    private void logContext(List<Context> contexts) {
        for (Context context : contexts) {
            logContext(context);
        }
    }
    
    private void logFingerprint(Fingerprint fingerprint) {
        LOG.info("Fingerprint: " + fingerprint.getPositions().toString());
    }
    
    private void logFingerprints(List<Fingerprint> fingerprints) {
        for (Fingerprint fingerprint : fingerprints) {
            logFingerprint(fingerprint);
        }
    }
    
    private void logBulkTerms(List<List<Term>> bulkTerms) {
        for (List<Term> list : bulkTerms) {
            logTerms(list);
        }
    }
    
    private void logTerms(List<Term> terms) {
        for (Term term : terms) {
            logTerm(term);
        }
    }
    
    private void logTerm(Term term) {
        LOG.info("Term:  term: " + term.getTerm() + "  Score: " + term.getScore() + "  Score Types:   "
                + term.getPosTypes());
    }
    
    private void logKeyword(String keyword) {
        LOG.info("Keyword:  " + keyword);
    }
    
    private void logKeywords(List<String> keywords) {
        for (String keyword : keywords) {
            logKeyword(keyword);
        }
    }
    
    private void logToken(String token) {
        LOG.info("Keyword:  " + token);
    }
    
    private void logTokens(List<String> tokens) {
        for (String keyword : tokens) {
            logToken(keyword);
        }
    }
    
    private void logText(Text text) {
        LOG.info("Text: " + text.getText());
    }
    
    private void logTexts(List<Text> texts) {
        for (Text text : texts) {
            logText(text);
        }
    }
    
}
