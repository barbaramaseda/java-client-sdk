package io.cortical.retina.client;

import io.cortical.retina.core.Endpoints;
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
     * @throws JsonProcessingException */
    public List<String> getSimilarTerms(String string) throws JsonProcessingException, ApiException {
        boolean isText = string.split("[\\s]*").length > 1;
        List<Term> terms = delegate.getSimilarTermsForExpression(isText ? new Text(string) : new Term(string));
        List<String> retVal = new ArrayList<>();
        for(Term t : terms) {
            retVal.add(t.getTerm());
        }
        return retVal;
    }

    /** Returns the 20 most similar terms to this input fingerprint. */
    public List<String> getSimilarTerms(int[] fingerprint) {
        return null;
    }

    /** Returns the keywords of the input text. */
    public List<String> getKeywords(String text) {
        return null;
    }

    /** Returns the semantic fingerprint of the input string. */
    public int[] getFingerprint(String string) {
        return null;
    }

    /** Returns the semantic similarity of two input strings in the range [0,1]. */
    public double compare(String string1, String string2) {
        return -1;
    }

    /** Returns the semantic similarity of two fingerprints in the range [0,1]. */
    public double compare(int[] fingerprint1, int[] fingerprint2) {
        return -1.0;
    }

    /** Returns the semantic similarity of a string and a fingerprint in the range [0,1]. */
    public double compare(String string, int[] fingerprint) {
        return -1.0;
    }

    /** Create a fingerprint representing a list of sample texts. */
    public int[] createCategoryFilter(List<String> positiveExamples) {
        return null;
    }
}
