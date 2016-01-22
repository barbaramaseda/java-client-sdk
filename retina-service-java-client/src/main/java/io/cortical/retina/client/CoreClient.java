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
import io.cortical.retina.model.ExpressionFactory.ExpressionModel;
import io.cortical.retina.model.Fingerprint;
import io.cortical.retina.model.Image;
import io.cortical.retina.model.Metric;
import io.cortical.retina.model.Model;
import io.cortical.retina.model.Retina;
import io.cortical.retina.model.Term;
import io.cortical.retina.model.Text;
import io.cortical.retina.service.ApiException;

import java.beans.Expression;
import java.io.ByteArrayInputStream;
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
    private Endpoints endpoints;
    
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
        
        endpoints = new Endpoints(this.retinaName, this.apiServer, this.apiKey);
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
        return endpoints.termsApi().getTerms(term, startIndex, maxResults, includeFingerprint);
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
        return endpoints.termsApi().getSimilarTermsForTerm(term, null, null, 0, maxResults, false);
    }
    
    /**
     * Retrieve fingerprint for the input {@link Text} (text is split and for each item a fingerprint is generated).
     * 
     * @param model             model for which a fingerprint is generated.
     * @return fingerprints     generated for the input model.
     * @throws ApiException     if there are server or connection issues.
     */
    public Fingerprint getFingerprintForText(Text text) throws ApiException {
        return endpoints.textApi().getFingerprintForText(text);
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
        return endpoints.textApi().getFingerprintsForTexts(texts, sparsity);
    }
    
    /**
     * Retrieve a list of keywords from the input text.
     * 
     * @param model   the input model {@link Text}.
     * @return an array of keywords
     * @throws ApiException     if there are some server or connection issues.
     */
    public List<String> getKeywordsForText(Text text) throws ApiException {
        return endpoints.textApi().getKeywordsForText(text);
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
        return endpoints.textApi().getTokensForText(text, null);
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
        return endpoints.textApi().getTokensForText(text, tags);
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
        return endpoints.textApi().getSlicesForText(text, startIndex, maxResults, includeFingerprint);
    }
    
    /**
     * Identifies the language of the text and returns (if possible) a relevant {@link Retina} object.
     * 
     * @param text the input {@link Text}
     * @return a {@link Retina} object.
     * @throws ApiException if there are server or connection issues.
     */
    public Retina getLanguageForText(Text text) throws ApiException {
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
    public <T extends ExpressionModel> Fingerprint getFingerprintForExpression(T expressionModel) 
        throws JsonProcessingException, ApiException {
        return getFingerprintForExpression(expressionModel);
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
    public <T extends ExpressionModel> Fingerprint getFingerprintForExpression(T expressionModel, Double sparsity) 
        throws JsonProcessingException, ApiException {
        return endpoints.expressionsApi().getFingerprintForExpression(expressionModel, sparsity);
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
    public <T extends ExpressionModel> List<Fingerprint> getFingerprintsForExpressions(List<T> expressionModels) 
        throws JsonProcessingException, ApiException {
        return getFingerprintsForExpressions(expressionModels, null);
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
    public <T extends ExpressionModel> List<Fingerprint> getFingerprintsForExpressions(List<T> expressionModel, Double sparsity) 
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
    public <T extends ExpressionModel> List<Context> getContextsForExpression(T expressionModel)
        throws JsonProcessingException, ApiException {
        
        return getContextsForExpression(expressionModel, 0, maxResults, null, false);
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
    public <T extends ExpressionModel> List<Context> getContextsForExpression(
        T expressionModel, int startIndex, int maxResults, Double sparsity, Boolean includeFingerprint)
            throws JsonProcessingException, ApiException {
        
        return endpoints.expressionsApi().getContextsForExpression(expressionModel, startIndex, maxResults, sparsity, includeFingerprint);
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
    public <T extends ExpressionModel> List<List<Context>> getContextsForExpressions(List<T> expressionModels) 
        throws JsonProcessingException, ApiException {
        
        return getContextsForExpressions(expressionModels, 0, maxResults, false, null);
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
    public <T extends ExpressionModel> List<List<Context>> getContextsForExpressions(
        List<T> expressionModels, int startIndex, int maxResults, 
            Boolean includeFingerprint, Double sparsity) throws JsonProcessingException, ApiException {
        
        return endpoints.expressionsApi().getContextsForExpressions(
            expressionModels, startIndex, maxResults, includeFingerprint, sparsity);
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
     * @param expressionModel       {@link ExpressionModel} for which a list of terms is generated. 
     * 
     * @return a list of similar terms generated from the input model.
     * @throws JsonProcessingException if it is impossible to generate the request using the model(s).
     * @throws ApiException     if there are server or connection issues.      
     */
    public <T extends ExpressionModel> List<Term> getSimilarTermsForExpression(T expressionModel) 
        throws JsonProcessingException, ApiException {
        
        return endpoints.expressionsApi().getSimilarTermsForExpression(expressionModel, 0, maxResults, 
            null, null, false, null);
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
     * @param expressionModel       {@link ExpressionModel} for which a list of terms is generated. 
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
    public <T extends ExpressionModel> List<Term> getSimilarTermsForExpression(T expressionModel, int startIndex, 
        int maxResults, Integer contextId, PosType posType, Boolean includeFingerprint, Double sparsity) 
            throws JsonProcessingException, ApiException {
        
        return endpoints.expressionsApi().getSimilarTermsForExpression(expressionModel, startIndex, maxResults, 
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
     * @param expressionModels      an {@link ExpressionModel} for which a list of terms is generated. 
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
    public <T extends ExpressionModel> List<List<Term>> getSimilarTermsForExpressions(List<T> expressionModels) 
        throws JsonProcessingException, ApiException {
        
        return getSimilarTermsForExpressions(expressionModels, 0, maxResults, null, null, false, null);
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
     * @param expressionModels      an {@link ExpressionModel} for which a list of terms is generated. 
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
    public <T extends ExpressionModel> List<List<Term>> getSimilarTermsForExpressions(
        List<T> expressionModels, int startIndex, int maxResults, Integer contextId, PosType posType, 
            Boolean includeFingerprint, Double sparsity) throws JsonProcessingException, ApiException {
        
        return endpoints.expressionsApi().getSimilarTermsForExpressions(expressionModels, startIndex, maxResults, 
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
        
        return endpoints.imageApi().getImage(model, 1, ImagePlotShape.CIRCLE, ImageEncoding.BASE64_PNG, null);
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
            Double sparsity) throws JsonProcessingException, ApiException {
        
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
    public List<Image> getImages(List<Model> models) throws JsonProcessingException, ApiException {
        return endpoints.imageApi().getImages(models, false, 1, ImagePlotShape.CIRCLE, null);
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
    public List<Image> getImages(List<Model> models, Boolean includeFingerprint, Integer scalar, ImagePlotShape shape, 
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
    public ByteArrayInputStream compareImage(List<Model> models) throws JsonProcessingException, ApiException {
        return compareImage(models, 1, ImagePlotShape.CIRCLE, ImageEncoding.BASE64_PNG);
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
    public ByteArrayInputStream compareImage(List<Model> models, Integer scalar, ImagePlotShape shape,
        ImageEncoding imageEncoding) throws JsonProcessingException, ApiException {
        return endpoints.imageApi().compareImage(models, scalar, shape, imageEncoding);
    }
    
    /**
     * Creates a {@link CategoryFilter} to be later used with the compare api
     * to obtain {@link Metric}s which define the "proximity" of one {@link Text}
     * to another.
     * 
     * @param filterName            the identifier for the filter
     * @param positiveExamples      the list of {@link Text}s which represent similarity to the 
     *                              concepts closest to the filter.
     * @param negativeExamples      the list of {@link Text}s which represent dissimilarity to the 
     *                              concepts closest to the filter.
     * @return  a CategoryFilter containing positions which can be used to obtain metrics
     * @throws JsonProcessingException if it is impossible to generate the request using the input model(s).
     * @throws ApiException         if there are some server or connection issues.
     */
    public CategoryFilter createCategoryFilter(
        String filterName, List<Text> positiveExamples, List<Text> negativeExamples) 
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
     * @param name : the retina's name.
     * 
     * @return retina found by name or null if there is no such retina.
     */
    public Retina retinaByName(String name) throws ApiException {
        return endpoints.retinaByName(name);
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
