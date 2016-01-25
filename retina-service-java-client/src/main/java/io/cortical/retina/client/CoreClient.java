package io.cortical.retina.client;

import io.cortical.retina.core.Compare.CompareModel;
import io.cortical.retina.core.Endpoints;
import io.cortical.retina.core.ImageEncoding;
import io.cortical.retina.core.ImagePlotShape;
import io.cortical.retina.core.PosTag;
import io.cortical.retina.core.PosType;
import io.cortical.retina.model.CategoryFilter;
import io.cortical.retina.model.Context;
import io.cortical.retina.model.ExpressionFactory;
import io.cortical.retina.model.Fingerprint;
import io.cortical.retina.model.Image;
import io.cortical.retina.model.LanguageRest;
import io.cortical.retina.model.Metric;
import io.cortical.retina.model.Model;
import io.cortical.retina.model.Retina;
import io.cortical.retina.model.Term;
import io.cortical.retina.model.Text;
import io.cortical.retina.service.ApiException;

import java.beans.Expression;
import java.io.ByteArrayInputStream;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.core.JsonProcessingException;


public class CoreClient {
    /** The default number of results to return */
    private static final int MAX_RESULTS = 10;
    /** Default scaling factor of images api */
    public static final int DEFAULT_SCALING_FACTOR = 1;
    /** Specifies max percentage of on bits out of the representational whole */
    public static final double DEFAULT_SPARSITY = 1.0;
    /** Helper list to specify all tags */
    public static final Set<PosTag> DEFAULT_TAGS = new LinkedHashSet<PosTag>(Arrays.asList(PosTag.any()));
    
    
    /** The default server address */
    private static final String DEFAULT_SERVER = "api.cortical.io";
    /** The default retina type */
    private static final String DEFAULT_RETINA = "en_associative";
    
    
    /** Main server access proxy */
    private Endpoints endpoints;
    
    
    
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
    public CoreClient(String apiKey, String apiServer, String retinaName, Endpoints ep) {
        this.endpoints = ep;
    }
    
    /**
     * Returns a List of all the {@link Term}s in the retina.
     * @return  a List of all the terms in the retina.
     * @throws ApiException
     */
    public List<Term> getTerms() throws ApiException {
        return getTerms(null, 0, MAX_RESULTS, false);
    }
    
    /**
     * Retrieve a term with meta-data for an exact match, or a list of potential retina {@link Term}s. 
     * 
     * @param term                  the term for which to retrieve a term or a list of potential terms.
     * @param startIndex            the index marking the beginning of a page of responses
     * @param maxResults            the number of results to return
     * @param includeFingerprint    true if the fingerprint should be provided in the response.
     * @return term with meta-data of potential terms.
     * @throws ApiException if there are server or connection issues.
     */
    public List<Term> getTerms(String term, int startIndex, int maxResults, boolean includeFingerprint) throws ApiException {
        return endpoints.termsApi().getTerms(term, startIndex, maxResults, includeFingerprint);
    }
    
    /**
     * Retrieve contexts for the input term.
     * 
     * @param term                  the input term.
     * @return List of contexts for the input term. 
     * @throws ApiException     if there are server or connection issues.
     */
    public List<Context> getContextsForTerm(String term) throws ApiException {
        return getContextsForTerm(term, 0, MAX_RESULTS, false);
    }
    
    /**
     * Retrieve contexts for the input term.
     * 
     * @param term                  the input term.
     * @param startIndex            the response item's start index.
     * @param maxResults            the number of results to return
     * @param includeFingerprint    true if the fingerprint should be provided in the response.
     * @return List of contexts for the input term. 
     * @throws ApiException     if there are server or connection issues.
     */
    public List<Context> getContextsForTerm(String term, int startIndex, int maxResults, boolean includeFingerprint)
        throws ApiException {
        return endpoints.termsApi().getContextsForTerm(term, startIndex, maxResults, includeFingerprint);
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
     * @param term                  the input term
     * @param contextId             the context id
     * @param posType               the posType used for filtering
     * @param startIndex            the response item's start index.
     * @param maxResults            the number of results to return
     * @param includeFingerprint    true if the fingerprint should be provided in the response.
     * @return A list of similar terms.
     * @throws ApiException         if there are server or connection issues.
     */
    public List<Term> getSimilarTermsForTerm(String term) throws ApiException {
        return getSimilarTermsForTerm(term, Context.ANY_ID, PosType.ANY, 0, MAX_RESULTS, false);
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
     * @param term                  the input term
     * @param contextId             the context id
     * @param posType               the posType used for filtering
     * @param startIndex            the response item's start index.
     * @param maxResults            the number of results to return
     * @param includeFingerprint    true if the fingerprint should be provided in the response.
     * @return A list of similar terms.
     * @throws ApiException         if there are server or connection issues.
     */
    public List<Term> getSimilarTermsForTerm(String term, int contextId, PosType posType, int startIndex,
        int maxResults, boolean includeFingerprint) throws ApiException {
        return endpoints.termsApi().getSimilarTermsForTerm(term, contextId, posType, 0, maxResults, false);
    }
    
    /**
     * Retrieve fingerprint for the input term (text is split and for each item a fingerprint is generated).
     * 
     * @param model             model for which a fingerprint is generated.
     * @return fingerprints     generated for the input model.
     * @throws ApiException     if there are server or connection issues.
     */
    public Fingerprint getFingerprintForText(String text) throws ApiException {
        return endpoints.textApi().getFingerprintForText(text);
    }
    
    /**
     * Retrieve a list of fingerprints obtained from input texts (one fingerprint per text). 
     * 
     * @param texts             input texts.
     * 
     * @return a list of fingerprints generated using the input model.
     * @throws JsonProcessingException if it is impossible to generate the request using the input model.
     * @throws ApiException     if there are server or connection issues.
     */
    public List<Fingerprint> getFingerprintsForTexts(List<String> texts) throws ApiException,
        JsonProcessingException {
        return getFingerprintsForTexts(texts, DEFAULT_SPARSITY);
    }
    
    /**
     * Retrieve a list of fingerprints obtained from bulk input texts (one fingerprint per text). 
     * 
     * @param texts             input {@link Text}s.
     * @param sparsity          the value used for re-sparsifying the expression. Not used here!
     * 
     * @return a list of fingerprints generated using the input model.
     * @throws JsonProcessingException if it is impossible to generate the request using the input model.
     * @throws ApiException     if there are server or connection issues.
     */
    public List<Fingerprint> getFingerprintsForTexts(List<String> texts, double sparsity) throws ApiException,
        JsonProcessingException {
        return endpoints.textApi().getFingerprintsForTexts(texts, sparsity);
    }
    
    /**
     * Retrieve a list of keywords from the input text.
     * 
     * @param model   the input model text.
     * @return an array of keywords
     * @throws ApiException     if there are some server or connection issues.
     */
    public List<String> getKeywordsForText(String text) throws ApiException {
        return endpoints.textApi().getKeywordsForText(text);
    }
    
    /**
     * Returns tokenized input text.
     * 
     * (Retrieves a list of lists of tokens for the input model: a list of sentences containing lists of 
     * tokens).
     *  
     * @param text      input text. 
     * @return : a list of tokens.
     * @throws ApiException     if there are server or connection issues.
     */
    public List<String> getTokensForText(String text) throws ApiException {
        return getTokensForText(text, DEFAULT_TAGS);
    }
    
    /**
     * Returns tokenized input text, using the specified {@link PosTag}s.
     * 
     * (Retrieves a list of lists of tokens for the input model: a list of sentences containing lists of 
     * tokens).
     *  
     * @param text      input text. 
     * @param posTags   array of pos (Part Of Speech), tags used in the token generation.
     * @return  a list of tokens.
     * @throws ApiException if there are server or connection issues.
     */
    public List<String> getTokensForText(String text, Set<PosTag> tags) throws ApiException {
        return endpoints.textApi().getTokensForText(text, tags);
    }
    
    /**
     * Slice the text.
     * 
     * @param text                  a text to slice.
     * @param includeFingerprint    true if a fingerprint should  be provided with the response items.
     * @return list of slices in the text representation.
     * @throws ApiException     if there are server or connection issues.
     */
    public List<Text> getSlicesForText(String text) throws ApiException {
        return getSlicesForText(text, 0, MAX_RESULTS, false);
    }
    
    /**
     * Slice the text, returning the results beginning at the startIndex 
     * specified; and the number of results - with or without {@link Fingerprint}s
     * included.
     * 
     * @param text                  a text to slice.
     * @param startIndex            a pagination configuration. 
     * @param maxResults            the maximum number of results to return.
     * @param includeFingerprint    true if a fingerprint should  be provided with the response items.
     * @return list of slices in the {@link Text} representation.
     * @throws ApiException     if there are server or connection issues.
     */
    public List<Text> getSlicesForText(String text, int startIndex, int maxResults, boolean includeFingerprint) throws ApiException {
        return endpoints.textApi().getSlicesForText(text, startIndex, maxResults, includeFingerprint);
    }
    
    /**
     * Identifies the language of the text and returns (if possible) a relevant {@link Retina} object.
     * 
     * @param text the input text
     * @return a {@link Retina} object.
     * @throws ApiException if there are server or connection issues.
     */
    public LanguageRest getLanguageForText(String text) throws ApiException {
        return endpoints.textApi().getLanguageForText(text);
    }
    
    /**
     * <p>
     * Resolves an expression.
     * </p><p>
     * To create an {@link Expression}, use the {@link ExpressionFactory} as in:
     * <pre>
     * // Where "model" is a {@link Term}, or {@link Text} or array of each etc.
     * ExpressionModel model = ExpressionFactory.and(Model... model);
     * </pre>
     * 
     * <b>The above could be:</b>
     * <UL>
     *  <li> .and(Model... models);  // Use only features present in both</li> 
     *  <li> .or(Model... models);   // or (combine)</li> 
     *  <li> .sub(Model... models);  // Subtraction</li>
     *  <li> .xor(Model... models);  // Use only features not present in both (but in one).</li>
     * </UL>
     * 
     * @param expressionModel     a model for which a fingerprint is generated.
     *
     * @return a fingerprint for the input model.
     * @throws JsonProcessingException if it is impossible to generate the request using the model(s).
     * @throws ApiException     if there are server or connection issues.
     */
    public Fingerprint getFingerprintForExpression(Model model) 
        throws JsonProcessingException, ApiException {
        return getFingerprintForExpression(model, DEFAULT_SPARSITY);
    }
    
    /**
     * <p>
     * Resolves an expression.
     * </p><p>
     * To create an {@link Expression}, use the {@link ExpressionFactory} as in:
     * <pre>
     * // Where "model" is a {@link Term}, or {@link Text} or array of each etc.
     * ExpressionModel model = ExpressionFactory.and(Model... model);
     * </pre>
     * 
     * <b>The above could be:</b>
     * <UL>
     *  <li> .and(Model... models);  // Use only features present in both</li> 
     *  <li> .or(Model... models);   // or (combine)</li> 
     *  <li> .sub(Model... models);  // Subtraction</li>
     *  <li> .xor(Model... models);  // Use only features not present in both (but in one).</li>
     * </UL>
     *  
     * @param expressionModel       a model for which a fingerprint is generated. 
     * @param sparsity              a value used for re-sparsifying the evaluated expression.
     * 
     * @return a fingerprint for the input model.
     * @throws JsonProcessingException if it is impossible to generate the request using the model(s).
     * @throws ApiException     if there are server or connection issues.
     */
    public Fingerprint getFingerprintForExpression(Model model, double sparsity) 
        throws JsonProcessingException, ApiException {
        return endpoints.expressionsApi().getFingerprintForExpression(model, sparsity);
    }
    
    /**
     * <p>
     * Resolves a bulk expression call. 
     * </p><p>
     * To create an {@link Expression}, use the {@link ExpressionFactory} as in:
     * <pre>
     * // Where "model" is a {@link Term}, or {@link Text} or array of each etc.
     * ExpressionModel model = ExpressionFactory.and(Model... model);
     * </pre>
     * 
     * <b>The above could be:</b>
     * <UL>
     *  <li> .and(Model... models);  // Use only features present in both</li> 
     *  <li> .or(Model... models);   // or (combine)</li> 
     *  <li> .sub(Model... models);  // Subtraction</li>
     *  <li> .xor(Model... models);  // Use only features not present in both (but in one).</li>
     * </UL>
     * 
     * @param expressionModels        model(s) for which the list of fingerprints is generated.
     * 
     * @return a list of fingerprints generated for each of the input model(s).
     * @throws JsonProcessingException  if it is impossible to generate the request using the model(s).
     * @throws ApiException             if there are server or connection issues.
     */
    public <T extends Model> List<Fingerprint> getFingerprintsForExpressions(List<T> models) 
        throws JsonProcessingException, ApiException {
        return getFingerprintsForExpressions(models, DEFAULT_SPARSITY);
    }
    
    /**
     * <p>
     * Resolves a bulk expression call. 
     * </p><p>
     * To create an {@link Expression}, use the {@link ExpressionFactory} as in:
     * <pre>
     * // Where "model" is a {@link Term}, or {@link Text} or array of each etc.
     * ExpressionModel model = ExpressionFactory.and(Model... model);
     * </pre>
     * 
     * <b>The above could be:</b>
     * <UL>
     *  <li> .and(Model... models);  // Use only features present in both</li> 
     *  <li> .or(Model... models);   // or (combine)</li> 
     *  <li> .sub(Model... models);  // Subtraction</li>
     *  <li> .xor(Model... models);  // Use only features not present in both (but in one).</li>
     * </UL>
     *  
     * @param models        model(s) for which the list of fingerprints is generated.
     * @param sparsity      a value used for re-sparsifying the evaluated expression.
     * 
     * @return a list of fingerprints generated for each of the input model(s).
     * @throws JsonProcessingException  if it is impossible to generate the request using the model(s).
     * @throws ApiException             if there are server or connection issues.
     */
    public <T extends Model> List<Fingerprint> getFingerprintsForExpressions(List<T> expressionModel, double sparsity) 
        throws JsonProcessingException, ApiException {
        return endpoints.expressionsApi().getFingerprintsForExpressions(expressionModel, sparsity);
    }
    
    /**
     * <p>
     * Calculate contexts of the result of an expression.
     * </p><p> 
     * To create an {@link Expression}, use the {@link ExpressionFactory} as in:
     * <pre>
     * // Where "model" is a {@link Term}, or {@link Text} or array of each etc.
     * ExpressionModel model = ExpressionFactory.and(Model... model);
     * </pre>
     * 
     * <b>The above could be:</b>
     * <UL>
     *  <li> .and(Model... models);  // Use only features present in both</li> 
     *  <li> .or(Model... models);   // or (combine)</li> 
     *  <li> .sub(Model... models);  // Subtraction</li>
     *  <li> .xor(Model... models);  // Use only features not present in both (but in one).</li>
     * </UL>
     * 
     * @param expressionModel                 a model for which a list of contexts is generated.
     * @param startIndex            the response item's first result
     * @param 
     * @param includeFingerprint    true if a fingerprint field should  be provided for each of the response items.
     * @param sparsity              a value used for re-sparsifying the evaluated expression.
     *  
     * @return a list of contexts generated from the input model.
     * @throws JsonProcessingException if it is impossible to generate the request using the model(s).
     * @throws ApiException : if there are server or connection issues.
     */
    public List<Context> getContextsForExpression(Model model)
        throws JsonProcessingException, ApiException {
        
        return getContextsForExpression(model, 0, MAX_RESULTS, DEFAULT_SPARSITY, false);
    }
    
    /**
     * <p>
     * Calculate contexts of the result of an expression.
     * </p><p> 
     * To create an {@link Expression}, use the {@link ExpressionFactory} as in:
     * <pre>
     * // Where "model" is a {@link Term}, or {@link Text} or array of each etc.
     * ExpressionModel model = ExpressionFactory.and(Model... model);
     * </pre>
     * 
     * <b>The above could be:</b>
     * <UL>
     *  <li> .and(Model... models);  // Use only features present in both</li> 
     *  <li> .or(Model... models);   // or (combine)</li> 
     *  <li> .sub(Model... models);  // Subtraction</li>
     *  <li> .xor(Model... models);  // Use only features not present in both (but in one).</li>
     * </UL>
     * 
     * @param expressionModel                 a model for which a list of contexts is generated.
     * @param startIndex            the response item's first result
     * @param 
     * @param includeFingerprint    true if a fingerprint field should  be provided for each of the response items.
     * @param sparsity              a value used for re-sparsifying the evaluated expression.
     *  
     * @return a list of contexts generated from the input model.
     * @throws JsonProcessingException if it is impossible to generate the request using the model(s).
     * @throws ApiException     if there are server or connection issues.
     */
    public List<Context> getContextsForExpression(
        Model model, int startIndex, int maxResults, double sparsity, boolean includeFingerprint)
            throws JsonProcessingException, ApiException {
        
        return endpoints.expressionsApi().getContextsForExpression(model, startIndex, maxResults, sparsity, includeFingerprint);
    }
    
    /**
     * <p>
     * Calculate contexts for each model.
     * </p><p> 
     * To create an {@link Expression}, use the {@link ExpressionFactory} as in:
     * <pre>
     * // Where "model" is a {@link Term}, or {@link Text} or array of each etc.
     * ExpressionModel model = ExpressionFactory.and(Model... model);
     * </pre>
     * 
     * <b>The above could be:</b>
     * <UL>
     *  <li> .and(Model... models);  // Use only features present in both</li> 
     *  <li> .or(Model... models);   // or (combine)</li> 
     *  <li> .sub(Model... models);  // Subtraction</li>
     *  <li> .xor(Model... models);  // Use only features not present in both (but in one).</li>
     * </UL>
     * 
     * <br>Returns a list of {@link Context} for each one of the input expressions in the bulk, so the returned
     * Response object will contain a list of lists of Contexts.
     * 
     * @param jsonModel             json model(s) for which a list of contexts is generated. (for each model a list of {@link Context} is generated.) 
     * @param startIndex            the index of the first response required
     * @param maxResults            the maximum number of results to return
     * @param includeFingerprint    true if a fingerprint field should  be provided for each of the response items.
     * @param sparsity              a value used for re-sparsifying the evaluated expression.
     * 
     * @return a list of contexts lists generated from the input model(s).
     * @throws JsonProcessingException if it is impossible to generate the request using the model(s).
     * @throws ApiException     if there are server or connection issues.
     */
    public <T extends Model> List<List<Context>> getContextsForExpressions(List<T> models) 
        throws JsonProcessingException, ApiException {
        
        return getContextsForExpressions(models, 0, MAX_RESULTS, false, DEFAULT_SPARSITY);
    }
    
    /**
     * <p>
     * Calculate contexts for each model. 
     * </p><p> 
     * To create an {@link Expression}, use the {@link ExpressionFactory} as in:
     * <pre>
     * // Where "model" is a {@link Term}, or {@link Text} or array of each etc.
     * ExpressionModel model = ExpressionFactory.and(Model... model);
     * </pre>
     * 
     * <b>The above could be:</b>
     * <UL>
     *  <li> .and(Model... models);  // Use only features present in both</li> 
     *  <li> .or(Model... models);   // or (combine)</li> 
     *  <li> .sub(Model... models);  // Subtraction</li>
     *  <li> .xor(Model... models);  // Use only features not present in both (but in one).</li>
     * </UL>
     *  
     * <br>Returns a list of {@link Context} for each one of the input expressions in the bulk, so the returned
     * Response object will contain a list of lists of Contexts.
     * 
     * @param models                model for which a list of contexts is generated. 
     *                              (for each model a list of {@link Context} is generated.) 
     * @param startIndex            the index of the first response required
     * @param maxResults            the maximum number of results to return
     * @param includeFingerprint    true if a fingerprint field should  be provided for each of the response items.
     * @param sparsity              a value used for re-sparsifying the evaluated expression.
     * 
     * @return a list of contexts lists generated from the input model(s).
     * @throws JsonProcessingException if it is impossible to generate the request using the model(s).
     * @throws ApiException     if there are server or connection issues.
     */
    public <T extends Model> List<List<Context>> getContextsForExpressions(
        List<T> models, int startIndex, int maxResults, 
            boolean includeFingerprint, double sparsity) throws JsonProcessingException, ApiException {
        
        return endpoints.expressionsApi().getContextsForExpressions(
            models, startIndex, maxResults, includeFingerprint, sparsity);
    }
    
    /**
     * <p>
     * Gets similar terms for the expression.
     * </p><p> 
     * To create an {@link Expression}, use the {@link ExpressionFactory} as in:
     * <pre>
     * // Where "model" is a {@link Term}, or {@link Text} or array of each etc.
     * ExpressionModel model = ExpressionFactory.and(Model... model);
     * </pre>
     * 
     * <b>The above could be:</b>
     * <UL>
     *  <li> .and(Model... models);  // Use only features present in both</li> 
     *  <li> .or(Model... models);   // or (combine)</li> 
     *  <li> .sub(Model... models);  // Subtraction</li>
     *  <li> .xor(Model... models);  // Use only features not present in both (but in one).</li>
     * </UL>
     *  
     * @param expressionModel       {@link Model} for which a list of terms is generated. 
     * 
     * @return a list of similar terms generated from the input model.
     * @throws JsonProcessingException if it is impossible to generate the request using the model(s).
     * @throws ApiException     if there are server or connection issues.      
     */
    public List<Term> getSimilarTermsForExpression(Model model) 
        throws JsonProcessingException, ApiException {
        
        return endpoints.expressionsApi().getSimilarTermsForExpression(model, 0, MAX_RESULTS, 
            Context.ANY_ID, PosType.ANY, false, DEFAULT_SPARSITY);
    }
    
    /**
     * <p>
     * Gets similar terms for the expression.
     * </p><p> 
     * To create an {@link Expression}, use the {@link ExpressionFactory} as in:
     * <pre>
     * // Where "model" is a {@link Term}, or {@link Text} or array of each etc.
     * ExpressionModel model = ExpressionFactory.and(Model... model);
     * </pre>
     * 
     * <b>The above could be:</b>
     * <UL>
     *  <li> .and(Model... models);  // Use only features present in both</li> 
     *  <li> .or(Model... models);   // or (combine)</li> 
     *  <li> .sub(Model... models);  // Subtraction</li>
     *  <li> .xor(Model... models);  // Use only features not present in both (but in one).</li>
     * </UL>
     *  
     * @param model                 {@link Model} for which a list of terms is generated. 
     * @param startIndex            the index of the first {@link Term} to return
     * @param maxResults            the maximum number of results to return
     * @param contextId             a context id
     * @param posType               a part of speech type
     * @param includeFingerprint    true if a fingerprint field should  be provided for each of the response items.
     * @param sparsity              a value used for re-sparsifying the evaluated expression.
     * 
     * @return a list of similar terms generated from the input model.
     * @throws JsonProcessingException if it is impossible to generate the request using the model(s).
     * @throws ApiException     if there are server or connection issues.      
     */
    public List<Term> getSimilarTermsForExpression(Model model, int startIndex, 
        int maxResults, int contextId, PosType posType, boolean includeFingerprint, double sparsity) 
            throws JsonProcessingException, ApiException {
        
        return endpoints.expressionsApi().getSimilarTermsForExpression(model, startIndex, maxResults, 
            contextId, posType, includeFingerprint, sparsity);
    }
    
    /**
     * <p>
     * Retrieve similar terms for the each item in the model's array.
     * </p><p> 
     * To create an {@link Expression}, use the {@link ExpressionFactory} as in:
     * <pre>
     * // Where "model" is a {@link Term}, or {@link Text} or array of each etc.
     * ExpressionModel model = ExpressionFactory.and(Model... model);
     * </pre>
     * 
     * <b>The above could be:</b>
     * <UL>
     *  <li> .and(Model... models);  // Use only features present in both</li> 
     *  <li> .or(Model... models);   // or (combine)</li> 
     *  <li> .sub(Model... models);  // Subtraction</li>
     *  <li> .xor(Model... models);  // Use only features not present in both (but in one).</li>
     * </UL>
     * 
     * @param expressionModels      an {@link Model} for which a list of terms is generated. 
     *                              (for each model a list of {@link Term} is generated.) 
     * @param startIndex            the index of the first {@link Term} to return
     * @param maxResults            the total number of results to return
     * @param contextId             an id identifying a {@link Term}'s context
     * @param posType               a part of speech type.
     * @param includeFingerprint    true if a fingerprint field should  be provided for each of the response items.
     * @param sparsity              a value used for re-sparsifying the evaluated expression.
     * 
     * @return A list containing a list of terms generated for each item in the models.
     * @throws JsonProcessingException if it is impossible to generate the request using the model(s).
     * @throws ApiException if there are server or connection issues.
     */
    public <T extends Model> List<List<Term>> getSimilarTermsForExpressions(List<T> expressionModels) 
        throws JsonProcessingException, ApiException {
        
        return getSimilarTermsForExpressions(expressionModels, 0, MAX_RESULTS, Context.ANY_ID, PosType.ANY, false, DEFAULT_SPARSITY);
    }
    
    /**
     * <p>
     * Retrieve similar terms for the each item in the model's array.
     * </p><p> 
     * To create an {@link Expression}, use the {@link ExpressionFactory} as in:
     * <pre>
     * // Where "model" is a {@link Term}, or {@link Text} or array of each etc.
     * ExpressionModel model = ExpressionFactory.and(Model... model);
     * </pre>
     * 
     * <b>The above could be:</b>
     * <UL>
     *  <li> .and(Model... models);  // Use only features present in both</li> 
     *  <li> .or(Model... models);   // or (combine)</li> 
     *  <li> .sub(Model... models);  // Subtraction</li>
     *  <li> .xor(Model... models);  // Use only features not present in both (but in one).</li>
     * </UL>
     * 
     * @param expressionModels      an {@link Model} for which a list of terms is generated. 
     *                              (for each model a list of {@link Term} is generated.) 
     * @param startIndex            the index of the first {@link Term} to return
     * @param maxResults            the total number of results to return
     * @param contextId             an id identifying a {@link Term}'s context
     * @param posType               a part of speech type.
     * @param includeFingerprint    true if a fingerprint field should  be provided for each of the response items.
     * @param sparsity              a value used for re-sparsifying the evaluated expression.
     * 
     * @return A list containing a list of terms generated for each item in the models.
     * @throws JsonProcessingException if it is impossible to generate the request using the model(s).
     * @throws ApiException if there are server or connection issues.
     */
    public <T extends Model> List<List<Term>> getSimilarTermsForExpressions(
        List<T> models, int startIndex, int maxResults, int contextId, PosType posType, 
            boolean includeFingerprint, double sparsity) throws JsonProcessingException, ApiException {
        
        return endpoints.expressionsApi().getSimilarTermsForExpressions(models, startIndex, maxResults, 
            contextId, posType, includeFingerprint, sparsity);
    }
    
    /**
     * Compares 2 models.
     * 
     * @param model1    model to be compared with model2 
     * @param model2    model to be compared with model1
     * 
     * @return the result of the comparison as a @{link Metric} object.
     * @throws JsonProcessingException  if the models cannot be converted to JSON.
     * @throws ApiException if the cortical.io's API isn't available/ or an internal error occurred.
     */
    public Metric compare(Model model1, Model model2) throws JsonProcessingException, ApiException {
        return endpoints.compareApi().compare(model1, model2);
    }
    
    /**
     * <p>
     * Compares pairs of models in bulk.
     * </p><p>
     * <pre>
     * To create a CompareModel...
     * 
     * CompareModel model = new CompareModel(Model model1, Model model2);
     * </pre>
     * </p>
     * Where "Model" can be any subtype such as:
     * <UL>
     *  <li>Term</li>
     *  <li>Text</li>
     *  <li>etc.</li>
     * </UL>
     * 
     * @param compareModels     array of model to be compare holder. 
     * @return the result of the comparison as a array of @{link Metric} object.
     * @throws JsonProcessingException  if the models cannot be converted to JSON.
     * @throws ApiException     if the cortical.io's API isn't available/ or an internal error occurred.
     */
    public Metric[] compareBulk(List<CompareModel> compareModels) throws JsonProcessingException, ApiException {
        return endpoints.compareApi().compareBulk(compareModels);
    }
    
    /**
     * <p>
     * Generate an image for the for the model.
     * </p><p>
     * <b>Defaults are:</b>
     * <UL>
     *  <li> Scaling factor "1" (no scaling - uses original image size)</li>
     *  <li> Shape = {@link ImagePlotShape#CIRCLE}</li>
     *  <li> Image Encoding = {@link ImageEncoding#BASE64_PNG}</li>
     *  <li> sparsity = null (uses default Fingerprint sparsity)
     * </UL>
     * </p><p>
     * <b>To create image in memory:</b>
     * <pre>
     *  try {
     *      CoreClient client ...
     *      ByteArrayInputStream in = client.getImage(model);
     *      BufferedImage bImage = ImageIO.read(in);
     *  }catch(IOException e) {
     *      System.out.println(e.getMessage());
     *  }
     * 
     *  // write it to a file...
     *  try {
     *      ImageIO.write(bImage, "png", new File("/mydirectory/my-image.png"));
     *  }catch(IOException e) {
     *      System.out.println(e.getMessage());
     *  }
     * </pre>
     * </p>
     * 
     * @param model             the {@link Model} subtype for which an image is generated.
     * 
     * @return a byte array holding the image data.
     * @throws JsonProcessingException if it is impossible to generate the request using the input model(s).
     * @throws ApiException : if there are some server or connection issues.
     */
    public ByteArrayInputStream getImage(Model model) throws JsonProcessingException, ApiException {
        
        return endpoints.imageApi().getImage(model, DEFAULT_SCALING_FACTOR, ImagePlotShape.CIRCLE, ImageEncoding.BASE64_PNG, DEFAULT_SPARSITY);
    }
    
    /**
     * <p>
     * Generate an image for the for the model.
     * </p><p>
     * <b>Defaults are:</b>
     * <UL>
     *  <li> Scaling factor "1" (no scaling - uses original image size)</li>
     *  <li> Shape = {@link ImagePlotShape#CIRCLE}</li>
     *  <li> Image Encoding = {@link ImageEncoding#BASE64_PNG}</li>
     *  <li> sparsity = null (uses default Fingerprint sparsity)
     * </UL>
     * </p><p>
     * <b>To create image in memory:</b>
     * <pre>
     *  try {
     *      CoreClient client ...
     *      ByteArrayInputStream in = client.getImage(model);
     *      BufferedImage bImage = ImageIO.read(in);
     *  }catch(IOException e) {
     *      System.out.println(e.getMessage());
     *  }
     * 
     *  // write it to a file...
     *  try {
     *      ImageIO.write(bImage, "png", new File("/mydirectory/my-image.png"));
     *  }catch(IOException e) {
     *      System.out.println(e.getMessage());
     *  }
     * </pre>
     * </p>
     * 
     * @param model             the {@link Model} subtype for which an image is generated.
     * @param scalar            scaling factor of the image to generate
     * @param shape             shape of the plots used in the overlay image
     * @param imageEncoding     the encoding of the image.
     * @param sparsity          a sparsity value which can be applied to the image
     * 
     * @return a byte array holding the image data.
     * @throws JsonProcessingException if it is impossible to generate the request using the input model(s).
     * @throws ApiException : if there are some server or connection issues.
     */
    public ByteArrayInputStream getImage(Model model, Integer scalar, ImagePlotShape shape, ImageEncoding imageEncoding,
            double sparsity) throws JsonProcessingException, ApiException {
        
        return endpoints.imageApi().getImage(model, scalar, shape, imageEncoding, sparsity);
    }
    
    /**
     * <p>
     * Returns a List of {@link Image}s for the input models.
     * </p><p>
     * <b>For tips on image usage see:</b> {@link #getImage(Model)}
     * </p>
     * 
     * @param models                List of {@link Model}s from which to produce fingerprint images.
     * 
     * @return a list of images generated using the input models.
     * @throws JsonProcessingException if it is impossible to generate the request using the input model(s).
     * @throws ApiException     if there are some server or connection issues.
     * @see #getImage(Model)
     */
    public <T extends Model> List<Image> getImages(List<T> models) throws JsonProcessingException, ApiException {
        return endpoints.imageApi().getImages(models, false, DEFAULT_SCALING_FACTOR, ImagePlotShape.CIRCLE, DEFAULT_SPARSITY);
    }
    
    /**
     * Returns a List of {@link Image}s for the input models.
     * </p><p>
     * <b>For tips on image usage see:</b> {@link #getImage(Model)}
     * </p>
     * 
     * @param models                List of {@link Model}s from which to produce fingerprint images.
     * @param includeFingerprint    identify if the fingerprint should  be present/provided in the images.
     * @param scalar                scaling factor of the image to generate
     * @param shape                 shape of the plots used in the overlay image
     * @param sparsity              a sparsity value which can be applied to the image
     * 
     * @return a list of images generated using the input models.
     * @throws JsonProcessingException if it is impossible to generate the request using the input model(s).
     * @throws ApiException     if there are some server or connection issues.
     * @see #getImage(Model)
     */
    public <T extends Model> List<Image> getImages(List<T> models, Boolean includeFingerprint, Integer scalar, ImagePlotShape shape, 
        Double sparsity) throws JsonProcessingException, ApiException {
        return endpoints.imageApi().getImages(models, includeFingerprint, scalar, shape, sparsity);
    }
    
    /**
     * Returns a visualization of the comparison of two fingerprints. 
     * <p> The returned image contains a visualization of the left and right fingerprint and the overlay of both
     *     fingerprints.
     * </p>
     * <p>
     * <b>For tips on image usage see:</b> {@link #getImage(Model)}
     * </p>
     * @param models            a List of {@link Model}s (list size = 2), for which the fingerprint's images are 
     *                          generated.
     * @param scalar            scaling factor of the image to generate
     * @param shape             the shape of the plots used in the overlay image
     * @param imageEncoding     the encoding of the image.
     * 
     * @return a byte array holding the image data.
     * @throws JsonProcessingException if it is impossible to generate the request using the input model(s).
     * @throws ApiException     if there are some server or connection issues.
     * @see #getImage(Model)
     */
    public <T extends Model> ByteArrayInputStream compareImage(List<T> models) throws JsonProcessingException, ApiException {
        return compareImage(models, DEFAULT_SCALING_FACTOR, ImagePlotShape.CIRCLE, ImageEncoding.BASE64_PNG);
    }
    
    /**
     * Returns a visualization of the comparison of two fingerprints. 
     * <p> The returned image contains a visualization of the left and right fingerprint and the overlay of both
     *     fingerprints.
     * </p>
     * <p>
     * <b>For tips on image usage see:</b> {@link #getImage(Model)}
     * </p>
     * 
     * @param models            a List of {@link Model}s (list size = 2), for which the fingerprint's images are 
     *                          generated.
     * @param scalar            scaling factor of the image to generate
     * @param shape             the shape of the plots used in the overlay image
     * @param imageEncoding     the encoding of the image.
     * 
     * @return a byte array holding the image data.
     * @throws JsonProcessingException if it is impossible to generate the request using the input model(s).
     * @throws ApiException     if there are some server or connection issues.
     * @see #getImage(Model)
     */
    public <T extends Model> ByteArrayInputStream compareImage(List<T> models, int scalar, ImagePlotShape shape,
        ImageEncoding imageEncoding) throws JsonProcessingException, ApiException {
        return endpoints.imageApi().compareImage(models, scalar, shape, imageEncoding);
    }
    
    /**
     * Creates a {@link CategoryFilter} to be later used with the compare api
     * to obtain {@link Metric}s which define the "proximity" of one {@link Text}
     * to another.
     * 
     * @param filterName            the identifier for the filter
     * @param positiveExamples      the list of Strings which represent similarity to the 
     *                              concepts closest to the filter.
     * @param negativeExamples      the list of Strings which represent dissimilarity to the 
     *                              concepts closest to the filter.
     * @return  a CategoryFilter containing positions which can be used to obtain metrics
     * @throws JsonProcessingException if it is impossible to generate the request using the input model(s).
     * @throws ApiException         if there are some server or connection issues.
     */
    public CategoryFilter createCategoryFilter(
        String filterName, List<String> positiveExamples, List<String> negativeExamples) 
            throws JsonProcessingException, ApiException{
        
        return endpoints.classifyApi().createCategoryFilter(filterName, positiveExamples, negativeExamples);
    }
    
    /**
     * Retrieve all available retinas.
     * @return all available retinas.
     */
    public List<Retina> getRetinas() throws ApiException {
        return endpoints.getAllRetinas();
    }
    
    /**
     * Find retina by name.
     * @param name  the retina's name.
     * 
     * @return retina found by name or null if there is no such retina.
     */
    public Retina retinaByName(String name) throws ApiException {
        return endpoints.retinaByName(name);
    }
}
