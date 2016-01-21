package io.cortical.retina.client;

import static io.cortical.retina.service.RestServiceConstants.NULL_TEXT_MSG;
import static org.apache.commons.lang3.StringUtils.isEmpty;
import io.cortical.retina.core.PosTag;
import io.cortical.retina.core.PosType;
import io.cortical.retina.core.Retinas;
import io.cortical.retina.model.Context;
import io.cortical.retina.model.Fingerprint;
import io.cortical.retina.model.Retina;
import io.cortical.retina.model.Term;
import io.cortical.retina.model.Text;
import io.cortical.retina.service.ApiException;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;


public class CoreClient {
    /** The default server address */
    private static final String DEFAULT_SERVER = "api.cortical.io";
    /** The default retina type */
    private static final String DEFAULT_RETINA = "en_associative";
    
    /** Stores the user specified api key */
    private String apiKey;
    /** User specified server address */
    private String apiServer;
    /** User specified retina type */
    private String retinaName;
    
    /** Main server access proxy */
    private Retinas retinas;
    
    /** Stores the max results setting (default: 10) */
    private int maxResults = 10;
    
    
    /**
     * Constructs a new {@code CoreClient} using the specified api key
     * and configured with the default server address and {@link Retina}
     * type.
     * 
     * @param apiKey    the api key string
     */
    public CoreClient(String apiKey) {
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
    public CoreClient(String apiKey, String apiServer, String retinaName) {
        this.apiKey = apiKey;
        this.apiServer = apiServer;
        this.retinaName = retinaName;
        
        retinas = new Retinas(this.retinaName, this.apiServer, this.apiKey);
    }
    
    /**
     * Sets the aggregate container for the request proxy endpoints.
     * @param retinas   the {@link Retinas} providing all endpoint access.
     */
    public void setRetinas(Retinas retinas) {
        this.retinas = retinas;
    }
    
    /**
     * Returns the aggregate container for the request proxy endpoints.
     * @return retinas   the {@link Retinas} providing all endpoint access.
     */
    public Retinas getRetinas() {
        return retinas;
    }
    
    /**
     * Returns a List of all the {@link Term}s in the retina.
     * @return  a List of all the terms in the retina.
     * @throws ApiException
     */
    public List<Term> getTerms() throws ApiException {
        return getTerms(null, 0, maxResults, false);
    }
    
    /**
     * Retrieve a term with meta-data for an exact match, or a list of potential retina {@link Term}s. 
     * 
     * @param term                  the {@link Term} for which to retrieve a term or a list of potential terms.
     * @param startIndex            the index marking the beginning of a page of responses
     * @param maxResults            the number of results to return
     * @param includeFingerprint    true if the fingerprint should be provided in the response.
     * @return term with meta-data of potential terms.
     * @throws ApiException if there are server or connection issues.
     */
    public List<Term> getTerms(Term term, int startIndex, int maxResults, Boolean includeFingerprint) throws ApiException {
        return retinas.termsApi().getTerms(term, startIndex, maxResults, includeFingerprint);
    }
    
    /**
     * Retrieve contexts for the input {@link Term}.
     * 
     * @param term                  the input {@link Term}.
     * @return List of contexts for the input {@link Term}. 
     * @throws ApiException     if there are server or connection issues.
     */
    public List<Context> getContextsForTerm(Term term) throws ApiException {
        return getContextsForTerm(term, 0, maxResults, false);
    }
    
    /**
     * Retrieve contexts for the input {@link Term}.
     * 
     * @param term                  the input {@link Term}.
     * @param startIndex            the response item's start index.
     * @param maxResults            the number of results to return
     * @param includeFingerprint    true if the fingerprint should be provided in the response.
     * @return List of contexts for the input term. 
     * @throws ApiException     if there are server or connection issues.
     */
    public List<Context> getContextsForTerm(Term term, int startIndex, int maxResults, boolean includeFingerprint)
        throws ApiException {
        return retinas.termsApi().getContextsForTerm(term, startIndex, maxResults, includeFingerprint);
    }
    
    /**
     * Retrieve all similar {@link Term}s for the input.
     * <br>If any context is specified, only the similar terms related to this context are returned.
     * 
     * <ul>
     * <li> No input context: returns all similar terms without context filtering.
     * <li> 0..N-1 : returns all similar terms for the Nth context.
     * </ul>
     * 
     * <br>Uses pagination 
     * 
     * @param term                  the input {@link Term}
     * @param contextId             the context id
     * @param posType               the posType used for filtering
     * @param startIndex            the response item's start index.
     * @param maxResults            the number of results to return
     * @param includeFingerprint    true if the fingerprint should be provided in the response.
     * @return A list of similar terms.
     * @throws ApiException         if there are server or connection issues.
     */
    public List<Term> getSimilarTermsForTerm(Term term) throws ApiException {
        return getSimilarTermsForTerm(term, null, null, 0, maxResults, false);
    }
    
    /**
     * Retrieve all similar {@link Term}s for the input.
     * <br>If any context is specified, only the similar terms related to this context are returned.
     * 
     * <ul>
     * <li> No input context: returns all similar terms without context filtering.
     * <li> 0..N-1 : returns all similar terms for the Nth context.
     * </ul>
     * 
     * <br>Uses pagination 
     * 
     * @param term                  the input {@link Term}
     * @param contextId             the context id
     * @param posType               the posType used for filtering
     * @param startIndex            the response item's start index.
     * @param maxResults            the number of results to return
     * @param includeFingerprint    true if the fingerprint should be provided in the response.
     * @return A list of similar terms.
     * @throws ApiException         if there are server or connection issues.
     */
    public List<Term> getSimilarTermsForTerm(Term term, Integer contextId, PosType posType, int startIndex,
        int maxResults, Boolean includeFingerprint) throws ApiException {
        return retinas.termsApi().getSimilarTermsForTerm(term, null, null, 0, maxResults, false);
    }
    
    /**
     * Retrieve fingerprints for the input {@link Text} (text is split and for each item a fingerprint is generated).
     * 
     * @param model             model for which a fingerprint is generated.
     * @return fingerprints     generated for the input model.
     * @throws ApiException     if there are server or connection issues.
     */
    public List<Fingerprint> getFingerprintsForText(Text text) throws ApiException {
        return retinas.textApi().getFingerprintsForText(text);
    }
    
    /**
     * Retrieve a list of fingerprints obtained from input {@link Text}s (one fingerprint per text). 
     * 
     * @param texts             input {@link Text}s.
     * 
     * @return a list of fingerprints generated using the input model.
     * @throws JsonProcessingException if it is impossible to generate the request using the input model.
     * @throws ApiException     if there are server or connection issues.
     */
    public List<Fingerprint> getFingerprintsForTexts(List<Text> texts) throws ApiException,
        JsonProcessingException {
        return getFingerprintsForTexts(texts, null);
    }
    
    /**
     * Retrieve a list of fingerprints obtained from bulk input {@link Text}s (one fingerprint per text). 
     * 
     * @param texts             input {@link Text}s.
     * @param sparsity          the value used for re-sparsifying the expression. Not used here!
     * 
     * @return a list of fingerprints generated using the input model.
     * @throws JsonProcessingException if it is impossible to generate the request using the input model.
     * @throws ApiException     if there are server or connection issues.
     */
    public List<Fingerprint> getFingerprintsForTexts(List<Text> texts, Double sparsity) throws ApiException,
        JsonProcessingException {
        return retinas.textApi().getFingerprintsForTexts(texts, sparsity);
    }
    
    /**
     * Retrieve a list of keywords from the input text.
     * 
     * @param model   the input model {@link Text}.
     * @return an array of keywords
     * @throws ApiException     if there are some server or connection issues.
     */
    public List<String> getKeywordsForText(Text text) throws ApiException {
        return retinas.textApi().getKeywordsForText(text);
    }
    
    /**
     * Returns tokenized input {@link Text}.
     * 
     * (Retrieves a list of lists of tokens for the input model: a list of sentences containing lists of 
     * tokens).
     *  
     * @param text      input {@link Text}. 
     * @return : a list of tokens.
     * @throws ApiException : if there are server or connection issues.
     */
    public List<String> getTokensForText(Text text) throws ApiException {
        return retinas.textApi().getTokensForText(text, null);
    }
    
    /**
     * Returns tokenized input text, using the specified {@link PosTag}s.
     * 
     * (Retrieves a list of lists of tokens for the input model: a list of sentences containing lists of 
     * tokens).
     *  
     * @param text      input {@link Text}. 
     * @param posTags   array of pos (Part Of Speech), tags used in the token generation.
     * @return : a list of tokens.
     * @throws ApiException : if there are server or connection issues.
     */
    public List<String> getTokensForText(Text text, List<PosTag> tags) throws ApiException {
        return retinas.textApi().getTokensForText(text, tags);
    }
    
    /**
     * Slice the text.
     * 
     * @param text                  a {@link Text} to slice.
     * @param includeFingerprint    true if a fingerprint should  be provided with the response items.
     * @return list of slices in the {@link Text} representation.
     * @throws ApiException     if there are server or connection issues.
     */
    public List<Text> getSlicesForText(Text text) throws ApiException {
        return getSlicesForText(text, 0, maxResults, false);
    }
    
    /**
     * Slice the text, returning the results beginning at the startIndex 
     * specified; and the number of results - with or without {@link Fingerprint}s
     * included.
     * 
     * @param text                  a {@link Text} to slice.
     * @param startIndex            a pagination configuration. 
     * @param maxResults            the maximum number of results to return.
     * @param includeFingerprint    true if a fingerprint should  be provided with the response items.
     * @return list of slices in the {@link Text} representation.
     * @throws ApiException     if there are server or connection issues.
     */
    public List<Text> getSlicesForText(Text text, int startIndex, int maxResults, Boolean includeFingerprint) throws ApiException {
        return retinas.textApi().getSlicesForText(text, startIndex, maxResults, includeFingerprint);
    }
    
    /**
     * Identifies the language of the text and returns (if possible) a relevant {@link Retina} object.
     * 
     * @param text the input {@link Text}
     * @return a {@link Retina} object.
     * @throws ApiException if there are server or connection issues.
     */
    public Retina getLanguageForText(Text text) throws ApiException {
        return retinas.textApi().getLanguageForText(text);
    }
    
    /**
     * Sets the total number of results which will be returned from the 
     * server for any query.
     * 
     * @param max
     */
    public void setMaxResults(int max) {
        this.maxResults = max;
    }
    
    /**
     * Returns the total number of results which will be returned from the 
     * server for any query.
     * 
     * @return max
     */
    public int getMaxResults() {
        return maxResults;
    }
}
