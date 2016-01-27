/*******************************************************************************
 * Copyright (c) cortical.io GmbH. All rights reserved.
 *
 * This software is confidential and proprietary information.
 * You shall use it only in accordance with the terms of the
 * license agreement you entered into with cortical.io GmbH.
 ******************************************************************************/
package io.cortical.retina.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.cortical.retina.core.Endpoints;
import io.cortical.retina.core.PosType;
import io.cortical.retina.model.Context;
import io.cortical.retina.model.ExpressionFactory;
import io.cortical.retina.model.Fingerprint;
import io.cortical.retina.model.Metric;
import io.cortical.retina.model.Model;
import io.cortical.retina.model.Retina;
import io.cortical.retina.model.Term;
import io.cortical.retina.model.Text;
import io.cortical.retina.rest.ApiException;
import java.util.ArrayList;
import java.util.List;
import static org.apache.commons.lang3.StringUtils.isEmpty;

/**
 * Simple client for accessing the Cortical.io APIs. 
 */
public class LiteClient {
    
    /** The default server address */
    private static final String DEFAULT_SERVER = "api.cortical.io";
    /** The default retina type */
    private static final String DEFAULT_RETINA = "en_associative";
    
    /** Main server access proxy */
    private Endpoints endpoints;
    
    /** Proxy calls to the api through the more complex {@link FullClient} */
    private FullClient delegate;
    
    /**
     * Constructs a new {@code CoreClient} using the specified api key
     * and configured with the default server address and {@link Retina}
     * type.
     * 
     * @param apiKey    the api key string
     */
    public LiteClient(String apiKey) {
        this(apiKey, DEFAULT_SERVER, DEFAULT_RETINA);
    }
    
    /**
     * Constructs a new {@code CoreClient} using the specified api key,
     * server address, and retina name/type.
     * 
     * @param apiKey        authorization key specific to each user
     * @param apiServer     http or ip address
     * @param retinaName    the type of retina to use (must be one of 
     *                      en_associatiave (default) or en_synonymous).
     */
    public LiteClient(String apiKey, String apiServer, String retinaName) {
        this(apiKey, apiServer, retinaName, new Endpoints(retinaName, apiServer, apiKey));
    }
    
    /**
     * Constructs a new {@code LiteClient} with the specified key, server location, retina
     * name and {@link Endpoints}
     * @param apiKey        authorization key specific to each user
     * @param apiServer     http or ip address
     * @param retinaName    the type of retina to use (must be one of 
     *                      en_associatiave (default) or en_synonymous).
     * @param ep            contains all the endpoints representing the various apis.
     */
    LiteClient(String apiKey, String apiServer, String retinaName, Endpoints ep) {
        this.endpoints = ep;
        
        this.delegate = new FullClient(apiKey, apiServer, retinaName, endpoints);
    }
    
    /** 
     * Returns the 20 most similar terms to this input string.
     * 
     * @param   string  the text to find similar terms for
     * 
     * @return  a list of similar terms
     * @throws ApiException 
     * @throws JsonProcessingException 
     */
    public List<String> getSimilarTerms(String string) throws JsonProcessingException, ApiException {
        List<Term> terms = delegate.getSimilarTermsForExpression(new Text(string), 0, 20, Context.ANY_ID, PosType.ANY, 
                false, 1.0);
        return termToString(terms);
    }
    
    /** 
     * Returns the 20 most similar terms to this input fingerprint.
     * 
     * @param fingerprint the int array to find similar terms for
     * @return  a list of similar terms
     * @throws ApiException 
     * @throws JsonProcessingException 
     */
    public List<String> getSimilarTerms(int[] fingerprint) throws JsonProcessingException, ApiException {
        List<Term> terms = delegate.getSimilarTermsForExpression(new Fingerprint(fingerprint), 0, 20, Context.ANY_ID,
                PosType.ANY, false, 1.0);
        return termToString(terms);
    }
    
    /** 
     * Returns the keywords of the input text.
     * @param text  the text out of which keywords will be returned 
     * @return a list of keywords
     * @throws ApiException 
     */
    public List<String> getKeywords(String text) throws ApiException {
        return delegate.getKeywordsForText(text);
    }
    
    /** 
     * Returns the semantic fingerprint of the input string.
     * @param string    the text for which to return a fingerprint. 
     * @return fingerprint the positions of the semantic fingerprint
     * @throws ApiException 
     * @throws JsonProcessingException 
     */
    public int[] getFingerprint(String string) throws JsonProcessingException, ApiException {
        if (isEmpty(string)) {
            throw new ApiException(400, "Cannot get fingerprint from a null or empty string.");
        }
        Fingerprint fingerPrint = delegate.getFingerprintForExpression(ExpressionFactory.text(string));
        return fingerPrint == null ? null : fingerPrint.getPositions();
    }
    
    /** 
     * Returns the semantic similarity of two input strings in the range [0,1].
     * @param string1      first element of comparison
     * @param string2      second element of comparison
     * @return similarity metric of comparison
     * @throws ApiException 
     * @throws JsonProcessingException
     */
    public double compare(String string1, String string2) throws JsonProcessingException, ApiException {
        if (isEmpty(string1) || isEmpty(string2)) {
            throw new ApiException(400, "Cannot get fingerprint from a null or empty string.");
        }
        
        Metric metric = delegate.compare(new Text(string1), new Text(string2));
        if (metric == null) {
            return 0.0;
        }
        
        return metric.getCosineSimilarity();
    }
    
    /** 
     * Returns the semantic similarity of two fingerprints in the range [0,1].
     * @param fingerprint1      first element of comparison
     * @param fingerprint2      second element of comparison
     * @return similarity metric of comparison
     * @throws ApiException 
     * @throws JsonProcessingException 
     */
    public double compare(int[] fingerprint1, int[] fingerprint2) throws ApiException, JsonProcessingException {
        if (fingerprint1 == null || fingerprint2 == null) {
            throw new ApiException(400, "Cannot get fingerprint from a null or empty fingerprint.");
        }
        
        Metric metric = delegate.compare(new Fingerprint(fingerprint1), new Fingerprint(fingerprint2));
        if (metric == null) {
            return 0.0;
        }
        
        return metric.getCosineSimilarity();
    }
    
    /** 
     * Returns the semantic similarity of a string and a fingerprint in the range [0,1].
     * @param string        first element of comparison
     * @param fingerprint   second element of comparison
     * @return similarity metric of comparison
     * @throws ApiException 
     * @throws JsonProcessingException 
     */
    public double compare(String string, int[] fingerprint) throws ApiException, JsonProcessingException {
        if (isEmpty(string) || fingerprint == null) {
            throw new ApiException(400, "Cannot get fingerprint from a null or empty fingerprint.");
        }
        Model model = delegate.getFingerprintForExpression(ExpressionFactory.text(string));
        
        Metric metric = delegate.compare(model, new Fingerprint(fingerprint));
        if (metric == null) {
            return 0.0;
        }
        
        return metric.getCosineSimilarity();
    }
    
    /** 
     * Create a fingerprint representing a list of sample texts.
     * @param positiveExamples  text strings containing positive examples.
     * @return a fingerprint containing semantic filter positions
     * @throws ApiException 
     * @throws JsonProcessingException 
     */
    public int[] createCategoryFilter(List<String> positiveExamples) throws JsonProcessingException, ApiException {
        return delegate.createCategoryFilter("anonymous", positiveExamples, null).getPositions();
    }
    
    private List<String> termToString(List<Term> terms) {
        List<String> retVal = new ArrayList<>();
        if (terms != null) {
            for (Term t : terms) {
                retVal.add(t.getTerm());
            }
        }
        return retVal;
    }
    
}
