/*******************************************************************************
 * Copyright (c) cortical.io GmbH. All rights reserved.
 *  
 * This software is confidential and proprietary information.
 * You shall use it only in accordance with the terms of the
 * license agreement you entered into with cortical.io GmbH.
 ******************************************************************************/
package io.cortical.retina.service;

import static io.cortical.retina.client.core.ApiTestUtils.NOT_NULL_API_KEY;
import static io.cortical.retina.client.core.ApiTestUtils.NOT_NULL_BASE_PATH;
import static io.cortical.retina.client.core.ApiTestUtils.NOT_NULL_RETINA;
import static io.cortical.retina.client.core.ApiTestUtils.prepareApiGetMethod;
import static io.cortical.retina.client.core.ApiTestUtils.setApiInvoker;
import static io.cortical.retina.client.core.ApiTestUtils.verifyApiGetMethod;
import io.cortical.retina.model.Context;
import io.cortical.retina.model.Term;
import io.cortical.retina.service.ApiException;
import io.cortical.retina.service.ApiInvoker;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import static org.junit.Assert.assertNotNull;
import static org.mockito.MockitoAnnotations.initMocks;


/**
 * {@link TermsApi} test class.
 * 
 */
public class TestTermsApi {
    
    private static final String TERM = "apple";
    private static final String VALID_CONTEXS_JSON = "[{\"context_label\":\"software\",\"fingerprint\":{\"positions\":[]},\"context_id\":0},{\"context_label\":\"fruit\",\"fingerprint\":{\"positions\":[]},\"context_id\":1},{\"context_label\":\"hardware\",\"fingerprint\":{\"positions\":[]},\"context_id\":2},{\"context_label\":\"company\",\"fingerprint\":{\"positions\":[]},\"context_id\":3},{\"context_label\":\"tree\",\"fingerprint\":{\"positions\":[]},\"context_id\":4},{\"context_label\":\"iphone\",\"fingerprint\":{\"positions\":[]},\"context_id\":5}]";
    private static final String VALID_GET_SIMILAR_TERMS_JSON = "[{\"term\":\"apple\",\"df\":0.0013443430694127692,\"score\":133.0,\"pos_types\":[\"NOUN\",\"VERB\"],\"fingerprint\":{\"positions\":[]}},{\"term\":\"update\",\"df\":9.946693183472328E-4,\"score\":80.0,\"pos_types\":[\"NOUN\",\"VERB\"],\"fingerprint\":{\"positions\":[]}},{\"term\":\"google\",\"df\":7.76972472913294E-4,\"score\":73.0,\"pos_types\":[\"NOUN\",\"VERB\"],\"fingerprint\":{\"positions\":[]}},{\"term\":\"install\",\"df\":6.67545838123459E-4,\"score\":71.0,\"pos_types\":[\"VERB\"],\"fingerprint\":{\"positions\":[]}},{\"term\":\"console\",\"df\":0.001170156682461975,\"score\":67.0,\"pos_types\":[\"VERB\"],\"fingerprint\":{\"positions\":[]}},{\"term\":\"utilize\",\"df\":7.759606017857791E-4,\"score\":60.0,\"pos_types\":[\"NOUN\",\"VERB\"],\"fingerprint\":{\"positions\":[]}},{\"term\":\"stack\",\"df\":5.513252114774518E-4,\"score\":56.0,\"pos_types\":[\"NOUN\",\"VERB\"],\"fingerprint\":{\"positions\":[]}},{\"term\":\"support\",\"df\":0.02432957394298858,\"score\":55.0,\"pos_types\":[\"NOUN\",\"VERB\"],\"fingerprint\":{\"positions\":[]}},{\"term\":\"add\",\"df\":0.0026889752448619715,\"score\":55.0,\"pos_types\":[\"VERB\"],\"fingerprint\":{\"positions\":[]}},{\"term\":\"implement\",\"df\":0.0014106929047741092,\"score\":54.0,\"pos_types\":[\"VERB\"],\"fingerprint\":{\"positions\":[]}},{\"term\":\"enable\",\"df\":0.0016364847192281678,\"score\":52.0,\"pos_types\":[\"VERB\"],\"fingerprint\":{\"positions\":[]}},{\"term\":\"integrate\",\"df\":6.270709930228594E-4,\"score\":51.0,\"pos_types\":[\"VERB\"],\"fingerprint\":{\"positions\":[]}},{\"term\":\"allow\",\"df\":0.009736368541967428,\"score\":49.0,\"pos_types\":[\"VERB\"],\"fingerprint\":{\"positions\":[]}},{\"term\":\"emulate\",\"df\":2.713260151922331E-4,\"score\":49.0,\"pos_types\":[\"VERB\"],\"fingerprint\":{\"positions\":[]}},{\"term\":\"monitor\",\"df\":0.0012665735456123316,\"score\":49.0,\"pos_types\":[\"NOUN\",\"VERB\"],\"fingerprint\":{\"positions\":[]}},{\"term\":\"overlay\",\"df\":1.1578696759135787E-4,\"score\":49.0,\"pos_types\":[\"NOUN\",\"VERB\"],\"fingerprint\":{\"positions\":[]}},{\"term\":\"modify\",\"df\":5.767665426835429E-4,\"score\":48.0,\"pos_types\":[\"VERB\"],\"fingerprint\":{\"positions\":[]}},{\"term\":\"screen\",\"df\":0.0035788436250022947,\"score\":47.0,\"pos_types\":[\"NOUN\",\"VERB\"],\"fingerprint\":{\"positions\":[]}},{\"term\":\"testers\",\"df\":5.738754823192144E-5,\"score\":46.0,\"pos_types\":[\"NOUN\",\"VERB\"],\"fingerprint\":{\"positions\":[]}},{\"term\":\"switch\",\"df\":0.0018258491730916869,\"score\":46.0,\"pos_types\":[\"NOUN\",\"VERB\"],\"fingerprint\":{\"positions\":[]}},{\"term\":\"feature\",\"df\":0.00836282576287494,\"score\":46.0,\"pos_types\":[\"NOUN\",\"VERB\"],\"fingerprint\":{\"positions\":[]}},{\"term\":\"edit\",\"df\":3.9780990613160654E-4,\"score\":46.0,\"pos_types\":[\"NOUN\",\"VERB\"],\"fingerprint\":{\"positions\":[]}},{\"term\":\"formatting\",\"df\":9.742873427787166E-5,\"score\":46.0,\"pos_types\":[\"NOUN\",\"VERB\"],\"fingerprint\":{\"positions\":[]}},{\"term\":\"interrupt\",\"df\":2.1104740659598313E-4,\"score\":46.0,\"pos_types\":[\"VERB\"],\"fingerprint\":{\"positions\":[]}},{\"term\":\"simplify\",\"df\":2.4617379002257484E-4,\"score\":45.0,\"pos_types\":[\"VERB\"],\"fingerprint\":{\"positions\":[]}},{\"term\":\"distribute\",\"df\":6.741952769614146E-4,\"score\":45.0,\"pos_types\":[\"VERB\"],\"fingerprint\":{\"positions\":[]}},{\"term\":\"execute\",\"df\":6.740507239431982E-4,\"score\":45.0,\"pos_types\":[\"VERB\"],\"fingerprint\":{\"positions\":[]}},{\"term\":\"check\",\"df\":0.001467213134896732,\"score\":45.0,\"pos_types\":[\"NOUN\",\"VERB\"],\"fingerprint\":{\"positions\":[]}},{\"term\":\"handle\",\"df\":0.0018903198192162132,\"score\":45.0,\"pos_types\":[\"VERB\"],\"fingerprint\":{\"positions\":[]}},{\"term\":\"address\",\"df\":0.003608766099773095,\"score\":45.0,\"pos_types\":[\"NOUN\",\"VERB\"],\"fingerprint\":{\"positions\":[]}},{\"term\":\"specify\",\"df\":5.734418232645651E-4,\"score\":44.0,\"pos_types\":[\"VERB\"],\"fingerprint\":{\"positions\":[]}},{\"term\":\"upgrade\",\"df\":0.0011937188244312525,\"score\":44.0,\"pos_types\":[\"NOUN\",\"VERB\"],\"fingerprint\":{\"positions\":[]}},{\"term\":\"fix\",\"df\":6.539578544111148E-4,\"score\":44.0,\"pos_types\":[\"VERB\"],\"fingerprint\":{\"positions\":[]}},{\"term\":\"browse\",\"df\":9.540499202284169E-5,\"score\":44.0,\"pos_types\":[\"VERB\"],\"fingerprint\":{\"positions\":[]}},{\"term\":\"create\",\"df\":0.009376431526608525,\"score\":43.0,\"pos_types\":[\"VERB\"],\"fingerprint\":{\"positions\":[]}},{\"term\":\"access\",\"df\":0.009355760445003575,\"score\":43.0,\"pos_types\":[\"NOUN\",\"VERB\"],\"fingerprint\":{\"positions\":[]}},{\"term\":\"interconnect\",\"df\":7.921505398260188E-5,\"score\":43.0,\"pos_types\":[\"VERB\"],\"fingerprint\":{\"positions\":[]}},{\"term\":\"simulate\",\"df\":3.548776597213278E-4,\"score\":42.0,\"pos_types\":[\"VERB\"],\"fingerprint\":{\"positions\":[]}},{\"term\":\"delete\",\"df\":1.4715497254432248E-4,\"score\":42.0,\"pos_types\":[\"VERB\"],\"fingerprint\":{\"positions\":[]}},{\"term\":\"drive\",\"df\":0.004782536607690481,\"score\":42.0,\"pos_types\":[\"NOUN\",\"VERB\"],\"fingerprint\":{\"positions\":[]}},{\"term\":\"render\",\"df\":5.627448999165496E-4,\"score\":41.0,\"pos_types\":[\"VERB\"],\"fingerprint\":{\"positions\":[]}},{\"term\":\"communicate\",\"df\":0.0010824130004046038,\"score\":41.0,\"pos_types\":[\"VERB\"],\"fingerprint\":{\"positions\":[]}},{\"term\":\"reset\",\"df\":2.2477994332654368E-4,\"score\":40.0,\"pos_types\":[\"NOUN\",\"VERB\"],\"fingerprint\":{\"positions\":[]}},{\"term\":\"scan\",\"df\":4.257086386473769E-4,\"score\":40.0,\"pos_types\":[\"VERB\"],\"fingerprint\":{\"positions\":[]}},{\"term\":\"compile\",\"df\":2.1769684543393876E-4,\"score\":40.0,\"pos_types\":[\"VERB\"],\"fingerprint\":{\"positions\":[]}},{\"term\":\"hack\",\"df\":1.5394896440049455E-4,\"score\":39.0,\"pos_types\":[\"VERB\"],\"fingerprint\":{\"positions\":[]}},{\"term\":\"manage\",\"df\":0.0020389203219427,\"score\":39.0,\"pos_types\":[\"VERB\"],\"fingerprint\":{\"positions\":[]}},{\"term\":\"zoom\",\"df\":1.7230719771398076E-4,\"score\":38.0,\"pos_types\":[\"NOUN\",\"VERB\"],\"fingerprint\":{\"positions\":[]}},{\"term\":\"require\",\"df\":0.004839490496867753,\"score\":38.0,\"pos_types\":[\"VERB\"],\"fingerprint\":{\"positions\":[]}},{\"term\":\"connect\",\"df\":0.0018345223541846726,\"score\":38.0,\"pos_types\":[\"VERB\"],\"fingerprint\":{\"positions\":[]}},{\"term\":\"compute\",\"df\":2.961891343254585E-4,\"score\":38.0,\"pos_types\":[\"VERB\"],\"fingerprint\":{\"positions\":[]}},{\"term\":\"rewrite\",\"df\":1.8430509822594417E-4,\"score\":37.0,\"pos_types\":[\"VERB\"],\"fingerprint\":{\"positions\":[]}},{\"term\":\"verify\",\"df\":3.5516676575776066E-4,\"score\":37.0,\"pos_types\":[\"VERB\"],\"fingerprint\":{\"positions\":[]}},{\"term\":\"generate\",\"df\":0.001721192787902994,\"score\":37.0,\"pos_types\":[\"VERB\"],\"fingerprint\":{\"positions\":[]}},{\"term\":\"select\",\"df\":0.00186617946517407,\"score\":36.0,\"pos_types\":[\"VERB\"],\"fingerprint\":{\"positions\":[]}},{\"term\":\"run\",\"df\":0.014974825369112519,\"score\":36.0,\"pos_types\":[\"NOUN\",\"VERB\"],\"fingerprint\":{\"positions\":[]}},{\"term\":\"ease\",\"df\":0.0011633626906058028,\"score\":36.0,\"pos_types\":[\"NOUN\",\"VERB\"],\"fingerprint\":{\"positions\":[]}},{\"term\":\"fork\",\"df\":3.323273888795652E-4,\"score\":36.0,\"pos_types\":[\"NOUN\",\"VERB\"],\"fingerprint\":{\"positions\":[]}},{\"term\":\"pin\",\"df\":7.60348875818405E-4,\"score\":36.0,\"pos_types\":[\"NOUN\",\"VERB\"],\"fingerprint\":{\"positions\":[]}},{\"term\":\"incorporate\",\"df\":0.001095278219025866,\"score\":36.0,\"pos_types\":[\"VERB\"],\"fingerprint\":{\"positions\":[]}},{\"term\":\"manipulate\",\"df\":4.544746892724459E-4,\"score\":36.0,\"pos_types\":[\"VERB\"],\"fingerprint\":{\"positions\":[]}},{\"term\":\"search\",\"df\":0.004346709257767954,\"score\":35.0,\"pos_types\":[\"NOUN\",\"VERB\"],\"fingerprint\":{\"positions\":[]}},{\"term\":\"detect\",\"df\":0.001140667866745824,\"score\":35.0,\"pos_types\":[\"VERB\"],\"fingerprint\":{\"positions\":[]}},{\"term\":\"chat\",\"df\":3.8769119485645667E-4,\"score\":35.0,\"pos_types\":[\"VERB\"],\"fingerprint\":{\"positions\":[]}},{\"term\":\"log\",\"df\":8.27421476270827E-4,\"score\":35.0,\"pos_types\":[\"NOUN\",\"VERB\"],\"fingerprint\":{\"positions\":[]}},{\"term\":\"rely\",\"df\":0.0012720665603045559,\"score\":33.0,\"pos_types\":[\"VERB\"],\"fingerprint\":{\"positions\":[]}},{\"term\":\"fit\",\"df\":0.0026473439756156403,\"score\":33.0,\"pos_types\":[\"NOUN\",\"VERB\"],\"fingerprint\":{\"positions\":[]}},{\"term\":\"exploit\",\"df\":6.056771463268283E-4,\"score\":33.0,\"pos_types\":[\"NOUN\",\"VERB\"],\"fingerprint\":{\"positions\":[]}},{\"term\":\"analyze\",\"df\":4.2513042657451125E-4,\"score\":33.0,\"pos_types\":[\"VERB\"],\"fingerprint\":{\"positions\":[]}},{\"term\":\"provide\",\"df\":0.014008343889317489,\"score\":33.0,\"pos_types\":[\"VERB\"],\"fingerprint\":{\"positions\":[]}},{\"term\":\"set\",\"df\":0.02823915487367006,\"score\":32.0,\"pos_types\":[\"NOUN\",\"VERB\"],\"fingerprint\":{\"positions\":[]}},{\"term\":\"excel\",\"df\":1.86617946517407E-4,\"score\":32.0,\"pos_types\":[\"NOUN\",\"VERB\"],\"fingerprint\":{\"positions\":[]}},{\"term\":\"clustering\",\"df\":1.0480093820690943E-4,\"score\":32.0,\"pos_types\":[\"NOUN\",\"VERB\"],\"fingerprint\":{\"positions\":[]}},{\"term\":\"introduce\",\"df\":0.0014870168983923825,\"score\":32.0,\"pos_types\":[\"VERB\"],\"fingerprint\":{\"positions\":[]}},{\"term\":\"lets\",\"df\":4.822288687699998E-4,\"score\":32.0,\"pos_types\":[\"NOUN\",\"VERB\"],\"fingerprint\":{\"positions\":[]}},{\"term\":\"open\",\"df\":0.014059226551729672,\"score\":32.0,\"pos_types\":[\"VERB\",\"ADJECTIVE\"],\"fingerprint\":{\"positions\":[]}},{\"term\":\"transmit\",\"df\":5.75754671556028E-4,\"score\":31.0,\"pos_types\":[\"VERB\"],\"fingerprint\":{\"positions\":[]}},{\"term\":\"share\",\"df\":0.006405722449242738,\"score\":31.0,\"pos_types\":[\"NOUN\",\"VERB\"],\"fingerprint\":{\"positions\":[]}},{\"term\":\"deliver\",\"df\":0.0015675329295389323,\"score\":31.0,\"pos_types\":[\"VERB\"],\"fingerprint\":{\"positions\":[]}},{\"term\":\"contain\",\"df\":0.004833274717084447,\"score\":31.0,\"pos_types\":[\"VERB\"],\"fingerprint\":{\"positions\":[]}},{\"term\":\"read\",\"df\":0.004928246050052639,\"score\":30.0,\"pos_types\":[\"VERB\"],\"fingerprint\":{\"positions\":[]}},{\"term\":\"circumvent\",\"df\":1.6002019116558448E-4,\"score\":30.0,\"pos_types\":[\"VERB\"],\"fingerprint\":{\"positions\":[]}},{\"term\":\"advertise\",\"df\":2.2131067088934943E-4,\"score\":30.0,\"pos_types\":[\"VERB\"],\"fingerprint\":{\"positions\":[]}},{\"term\":\"standardize\",\"df\":1.0509004424334229E-4,\"score\":29.0,\"pos_types\":[\"VERB\"],\"fingerprint\":{\"positions\":[]}},{\"term\":\"enhance\",\"df\":0.0011757942501724155,\"score\":29.0,\"pos_types\":[\"VERB\"],\"fingerprint\":{\"positions\":[]}},{\"term\":\"launch\",\"df\":0.004947905260530073,\"score\":29.0,\"pos_types\":[\"NOUN\",\"VERB\"],\"fingerprint\":{\"positions\":[]}},{\"term\":\"maximize\",\"df\":3.769942715084411E-4,\"score\":29.0,\"pos_types\":[\"VERB\"],\"fingerprint\":{\"positions\":[]}},{\"term\":\"manufacture\",\"df\":0.0016059840323845017,\"score\":29.0,\"pos_types\":[\"NOUN\",\"VERB\"],\"fingerprint\":{\"positions\":[]}},{\"term\":\"send\",\"df\":0.0028482726709364737,\"score\":28.0,\"pos_types\":[\"VERB\"],\"fingerprint\":{\"positions\":[]}},{\"term\":\"unlock\",\"df\":2.750843936658602E-4,\"score\":28.0,\"pos_types\":[\"VERB\"],\"fingerprint\":{\"positions\":[]}},{\"term\":\"fail\",\"df\":0.0012258095944752993,\"score\":28.0,\"pos_types\":[\"VERB\"],\"fingerprint\":{\"positions\":[]}},{\"term\":\"facilitate\",\"df\":0.0013291650025000444,\"score\":28.0,\"pos_types\":[\"NOUN\",\"VERB\"],\"fingerprint\":{\"positions\":[]}},{\"term\":\"appletalk\",\"df\":1.5033513894508388E-5,\"score\":28.0,\"pos_types\":[\"NOUN\",\"VERB\"],\"fingerprint\":{\"positions\":[]}},{\"term\":\"duplicate\",\"df\":3.213413594951168E-4,\"score\":28.0,\"pos_types\":[\"NOUN\",\"VERB\"],\"fingerprint\":{\"positions\":[]}},{\"term\":\"include\",\"df\":0.03309888279308811,\"score\":28.0,\"pos_types\":[\"VERB\"],\"fingerprint\":{\"positions\":[]}},{\"term\":\"insert\",\"df\":3.8335460430996386E-4,\"score\":28.0,\"pos_types\":[\"NOUN\",\"VERB\"],\"fingerprint\":{\"positions\":[]}},{\"term\":\"look\",\"df\":0.0052477082203109425,\"score\":28.0,\"pos_types\":[\"NOUN\",\"VERB\"],\"fingerprint\":{\"positions\":[]}},{\"term\":\"offer\",\"df\":0.006300198745944746,\"score\":27.0,\"pos_types\":[\"NOUN\",\"VERB\"],\"fingerprint\":{\"positions\":[]}},{\"term\":\"notify\",\"df\":1.5337075232762883E-4,\"score\":27.0,\"pos_types\":[\"VERB\"],\"fingerprint\":{\"positions\":[]}},{\"term\":\"need\",\"df\":0.009409100508725437,\"score\":27.0,\"pos_types\":[\"NOUN\",\"VERB\"],\"fingerprint\":{\"positions\":[]}}]";
    private static final String VALID_GET_TERM_JSON = "[{\"term\":\"apple\",\"df\":0.0013443430694127692,\"score\":0.0,\"pos_types\":[\"NOUN\",\"VERB\"],\"fingerprint\":{\"positions\":[]}}]";
    
    
    @Mock
    private ApiInvoker apiInvoker;
    private TermsApi termsApi;
    
    /**
     * set up.
     * @throws java.lang.Exception if it happens.
     */
    @Before
    public void setUp() throws Exception {
        initMocks(this);
        termsApi = new TermsApi(NOT_NULL_API_KEY);
        termsApi.setBasePath(NOT_NULL_BASE_PATH);
        setApiInvoker(apiInvoker, termsApi);
    }
     
    /**
     * {@link TermsApi#getContextsForTerm(String, Boolean, String, Integer, Integer)} failure method test.
     * 
     * @throws ApiException : expected error.
     */
    @Test(expected = ApiException.class)
    public void getContextsForTermTest_nullRetinaName() throws ApiException {
        termsApi.getContextsForTerm(TERM, false, null, 0, 100);
    }
    
    /**
     * {@link TermsApi#getContextsForTerm(String, Boolean, String, Integer, Integer)} failure method test.
     * 
     * @throws ApiException : expected error.
     */
    @Test(expected = ApiException.class)
    public void getContextsForTermTest_nullBody() throws ApiException {
        termsApi.getContextsForTerm(null, false, NOT_NULL_RETINA, 0, 100);
    }
    
    /**
     * {@link TermsApi#getContextsForTerm(String, Boolean, String, Integer, Integer)}  method test.
     * 
     * @throws ApiException : should never be thrown.
     */
    @Test
    public void getContextsForTermTest() throws ApiException {
        Map<String, String> requestArguments = defaultRequestParams(TERM);
        prepareApiGetMethod(requestArguments, VALID_CONTEXS_JSON, apiInvoker);
        List<Context> contexts = termsApi.getContextsForTerm(TERM, false, NOT_NULL_RETINA, 0, 100);
        
        assertNotNull(contexts);
        for (Context context: contexts) {
            assertNotNull(context.getFingerprint());
            assertNotNull(context.getContextLabel());
            assertNotNull(context.getContextId());
        }
        
        verifyApiGetMethod(requestArguments, apiInvoker);
    }
    /**
     * {@link TermsApi#getSimilarTerms(String, Integer, String, Boolean, String, Integer, Integer)} failure method test.
     * 
     * @throws ApiException : expected error.
     */
    @Test(expected = ApiException.class)
    public void getSimilarTermsTest_nullRetinaName() throws ApiException {
        termsApi.getSimilarTerms(TERM, 0, "NNS", false, null, 0, 100);
    }
    
    /**
     * {@link TermsApi#getSimilarTerms(String, Integer, String, Boolean, String, Integer, Integer)} failure method test.
     * 
     * @throws ApiException : expected error.
     */
    @Test(expected = ApiException.class)
    public void getSimilarTermsTest_nullBody() throws ApiException {
        termsApi.getSimilarTerms(null, 0, "NNS", false, NOT_NULL_RETINA, 0, 100);
    }
    
    /**
     * {@link TermsApi#getSimilarTerms(String, Integer, String, Boolean, String, Integer, Integer)}  method test.
     * 
     * @throws ApiException : should never be thrown.
     */
    @Test
    public void getSimilarTermsTest() throws ApiException {
        Map<String, String> requestParams = defaultRequestParams(TERM);
        requestParams.put("pos_type", "VERB");
        requestParams.put("context_id", String.valueOf(0));
    
        prepareApiGetMethod(requestParams, VALID_GET_SIMILAR_TERMS_JSON, apiInvoker);
        List<Term> terms = termsApi.getSimilarTerms(TERM, 0, "VERB", false, NOT_NULL_RETINA, 0, 100);
        for (Term term: terms) {
            assertNotNull(term.getTerm());
            assertNotNull(term.getFingerprint()); 
        }
        verifyApiGetMethod(requestParams, apiInvoker);
    }
    /**
     * {@link TermsApi#getSimilarTerms(String, Integer, String, Boolean, String, Integer, Integer)} failure method test.
     * 
     * @throws ApiException : expected error.
     */
    @Test(expected = ApiException.class)
    public void getTermTest_nullRetinaName() throws ApiException {
        termsApi.getTerm(TERM, false, null, 0, 100);
    }
    
    
    /**
     * {@link TermsApi#getSimilarTerms(String, Integer, String, Boolean, String, Integer, Integer)}  method test.
     * 
     * @throws ApiException : should never be thrown.
     */
    @Test
    public void getTermTermsTest() throws ApiException {
        Map<String, String> requestParams = defaultRequestParams(TERM);

        prepareApiGetMethod(requestParams, VALID_GET_TERM_JSON, apiInvoker);
        List<Term> terms = termsApi.getTerm(TERM, false, NOT_NULL_RETINA, 0, 100);
        for (Term term: terms) {
            assertNotNull(term.getTerm());
            assertNotNull(term.getFingerprint()); 
        }
        verifyApiGetMethod(requestParams, apiInvoker);
    }
    
    private Map<String, String> defaultRequestParams(String term) {
        Map<String, String> requestParams = new HashMap<>();
        requestParams.put("retina_name", String.valueOf(NOT_NULL_RETINA));
        requestParams.put("term", term);
        requestParams.put("start_index", String.valueOf(0));
        requestParams.put("max_results", String.valueOf(100));
        requestParams.put("get_fingerprint", String.valueOf(false));
        return requestParams;
    }
}
