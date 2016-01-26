package io.cortical.retina.client;

import static org.apache.commons.lang3.StringUtils.isEmpty;
import io.cortical.retina.core.Endpoints;
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

import com.fasterxml.jackson.core.JsonProcessingException;


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
        this(apiKey, apiServer, retinaName, 
            new Endpoints(retinaName, apiServer, apiKey));
    }
    
    /**
     * 
     * @param apiKey        authorization key specific to each user
     * @param apiServer     http or ip address
     * @param retinaName    the type of retina to use (must be one of 
     *                      en_associatiave (default) or en_synonymous).
     * @param ep            contains all the endpoints representing the various apis.
     */
    public LiteClient(String apiKey, String apiServer, String retinaName, Endpoints ep) {
        this.endpoints = ep;
        
        this.delegate = new FullClient(apiKey, apiServer, retinaName, endpoints);
    }
    
    /** 
     * Returns the 20 most similar terms to this input string (text or term).
     * 
     * @param   string  the term or text to find similar {@link Term}s for.
     *  
     * @throws ApiException 
     * @throws JsonProcessingException 
     */
    public List<String> getSimilarTerms(String string) throws JsonProcessingException, ApiException {
       List<Term> terms = delegate.getSimilarTermsForTerm(string);
       return termToString(terms);
    }

    /** 
     * Returns the 20 most similar terms to this input fingerprint.
     *  
     * @throws ApiException 
     * @throws JsonProcessingException 
     */
    public List<String> getSimilarTerms(int[] fingerprint) throws JsonProcessingException, ApiException {
        List<Term> terms = delegate.getSimilarTermsForExpression(new Fingerprint(fingerprint));
        return termToString(terms);
    }

    /** 
     * Returns the keywords of the input text. 
     * @throws ApiException 
     */
    public List<String> getKeywords(String text) throws ApiException {
        return delegate.getKeywordsForText(text);
    }

    /** 
     * Returns the semantic fingerprint of the input string. 
     * @throws ApiException 
     * @throws JsonProcessingException 
     */
    public int[] getFingerprint(String string) throws JsonProcessingException, ApiException {
        if(isEmpty(string)) {
            throw new ApiException(400, "Cannot get fingerprint from a null or empty string.");
        }
        
        Fingerprint fingerPrint = null;
        if(string.split("[\\s]+").length > 1) {
            fingerPrint = delegate.getFingerprintForExpression(ExpressionFactory.text(string));
        }else{
            fingerPrint = delegate.getFingerprintForExpression(ExpressionFactory.term(string));
        }
        return fingerPrint == null ? null : fingerPrint.getPositions();
    }

    /** 
     * Returns the semantic similarity of two input strings in the range [0,1]. 
     * @throws ApiException 
     * @throws JsonProcessingException
     */
    public double compare(String string1, String string2) throws JsonProcessingException, ApiException {
        if(isEmpty(string1) || isEmpty(string2)) {
            throw new ApiException(400, "Cannot get fingerprint from a null or empty string.");
        }
        
        Metric metric = delegate.compare(new Text(string1), new Text(string2));
        if(metric == null) {
            return 0.0;
        }
        
        return metric.getCosineSimilarity();
    }

    /** 
     * Returns the semantic similarity of two fingerprints in the range [0,1]. 
     * @throws ApiException 
     * @throws JsonProcessingException 
     */
    public double compare(int[] fingerprint1, int[] fingerprint2) throws ApiException, JsonProcessingException {
        if(fingerprint1 == null || fingerprint2 == null || fingerprint1.length < 10 || fingerprint2.length < 10) {
            throw new ApiException(400, "Cannot get fingerprint from a null or empty fingerprint.");
        }
        
        Metric metric = delegate.compare(new Fingerprint(fingerprint1), new Fingerprint(fingerprint2));
        if(metric == null) {
            return 0.0;
        }
        
        return metric.getCosineSimilarity();
    }

    /** 
     * Returns the semantic similarity of a string and a fingerprint in the range [0,1]. 
     * @throws ApiException 
     * @throws JsonProcessingException 
     */
    public double compare(String string, int[] fingerprint) throws ApiException, JsonProcessingException {
        if(isEmpty(string) || fingerprint == null || fingerprint.length < 10) {
            throw new ApiException(400, "Cannot get fingerprint from a null or empty fingerprint.");
        }
        
        Model model = null;
        if(string.split("[\\s]+").length > 1) {
            model = delegate.getFingerprintForExpression(ExpressionFactory.text(string));
        }else{
            model = delegate.getFingerprintForExpression(ExpressionFactory.term(string));
        }
        
        Metric metric = delegate.compare(model, new Fingerprint(fingerprint));
        if(metric == null) {
            return 0.0;
        }
        
        return metric.getCosineSimilarity();
    }

    /** 
     * Create a fingerprint representing a list of sample texts. 
     * @throws ApiException 
     * @throws JsonProcessingException 
     */
    public int[] createCategoryFilter(List<String> positiveExamples) throws JsonProcessingException, ApiException {
        return delegate.createCategoryFilter("anonymous", positiveExamples, null).getPositions();
    }
    
    private List<String> termToString(List<Term> terms) {
        List<String> retVal = new ArrayList<>();
        if(terms != null) {
            for(Term t : terms) {
                retVal.add(t.getTerm());
            }
        }
        return retVal;
    }
    
}
