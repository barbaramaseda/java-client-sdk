package io.cortical.retina.client;

import static io.cortical.retina.core.ApiTestUtils.NOT_NULL_API_KEY;
import static io.cortical.retina.core.ApiTestUtils.NOT_NULL_BASE_PATH;
import static io.cortical.retina.core.ApiTestUtils.NOT_NULL_RETINA;
import static io.cortical.retina.core.PosTag.CC;
import static io.cortical.retina.core.PosTag.CD;
import static io.cortical.retina.core.PosTag.DT;
import static io.cortical.retina.core.PosTag.EX;
import static io.cortical.retina.core.PosTag.FW;
import static io.cortical.retina.core.PosTag.IN;
import static io.cortical.retina.core.PosTag.JJ;
import static io.cortical.retina.core.PosTag.JJR;
import static io.cortical.retina.core.PosTag.JJS;
import static io.cortical.retina.core.PosTag.JJSS;
import static io.cortical.retina.core.PosTag.LRB;
import static io.cortical.retina.core.PosTag.LS;
import static io.cortical.retina.core.PosTag.MD;
import static io.cortical.retina.core.PosTag.NN;
import static io.cortical.retina.core.PosTag.NNP;
import static io.cortical.retina.core.PosTag.NNPS;
import static io.cortical.retina.core.PosTag.NNS;
import static io.cortical.retina.core.PosTag.NP;
import static io.cortical.retina.core.PosTag.NPS;
import static io.cortical.retina.core.PosTag.PDT;
import static io.cortical.retina.core.PosTag.POS;
import static io.cortical.retina.core.PosTag.PP;
import static io.cortical.retina.core.PosTag.PRP;
import static io.cortical.retina.core.PosTag.PRP$;
import static io.cortical.retina.core.PosTag.PRPR$;
import static io.cortical.retina.core.PosTag.RB;
import static io.cortical.retina.core.PosTag.RBR;
import static io.cortical.retina.core.PosTag.RBS;
import static io.cortical.retina.core.PosTag.RP;
import static io.cortical.retina.core.PosTag.STAART;
import static io.cortical.retina.core.PosTag.SYM;
import static io.cortical.retina.core.PosTag.TO;
import static io.cortical.retina.core.PosTag.UH;
import static io.cortical.retina.core.PosTag.VB;
import static io.cortical.retina.core.PosTag.VBD;
import static io.cortical.retina.core.PosTag.VBG;
import static io.cortical.retina.core.PosTag.VBN;
import static io.cortical.retina.core.PosTag.VBP;
import static io.cortical.retina.core.PosTag.VBZ;
import static io.cortical.retina.core.PosTag.WDT;
import static io.cortical.retina.core.PosTag.WP;
import static io.cortical.retina.core.PosTag.WP$;
import static io.cortical.retina.core.PosTag.WRB;
import static io.cortical.retina.model.TestDataHarness.createContexts;
import static io.cortical.retina.model.TestDataHarness.createFingerprint;
import static io.cortical.retina.model.TestDataHarness.createFingerprints;
import static io.cortical.retina.model.TestDataHarness.createLanguage;
import static io.cortical.retina.model.TestDataHarness.createStrings;
import static io.cortical.retina.model.TestDataHarness.createTerms;
import static io.cortical.retina.model.TestDataHarness.createTexts;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import io.cortical.retina.core.Classify;
import io.cortical.retina.core.Compare;
import io.cortical.retina.core.Compare.CompareModel;
import io.cortical.retina.core.Endpoints;
import io.cortical.retina.core.Expressions;
import io.cortical.retina.core.ImageEncoding;
import io.cortical.retina.core.ImagePlotShape;
import io.cortical.retina.core.Images;
import io.cortical.retina.core.PosTag;
import io.cortical.retina.core.PosType;
import io.cortical.retina.core.Terms;
import io.cortical.retina.core.Texts;
import io.cortical.retina.model.CategoryFilter;
import io.cortical.retina.model.Context;
import io.cortical.retina.model.Fingerprint;
import io.cortical.retina.model.Image;
import io.cortical.retina.model.Language;
import io.cortical.retina.model.Metric;
import io.cortical.retina.model.Model;
import io.cortical.retina.model.Term;
import io.cortical.retina.model.Text;
import io.cortical.retina.rest.ApiException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import com.fasterxml.jackson.core.JsonProcessingException;


public class FullClientTest {
    /** Test Term */
    private static final String TERM = "term";
    /** Test Text */
    private static final String TEXT = "This is some text";
    /** Another test text */
    private static final String TEXT_2 = "test text 2";
    /** Another test text */
    private static final String TEXT_3 = "test text 3";
    /** Test Keywords array */
    private static final String[] KEYWORDS = { "KEY1", "KEY2", "KEY3" };
    
    private static final Term TERM_1 = new Term("term_1");
    private static final Term TERM_2 = new Term("term_2");
    private static final Text TEXT_1 = new Text("the short text");
    
    private static final Metric METRIC;
    
    private static final CategoryFilter cf = new CategoryFilter()
    {
        {
            setCategoryName("12");
            setPositions(new int[] { 3, 6, 7 });
        }
    };
    
    static {
        Map<String, Double> m = new HashMap<>();
        m.put("Cosine-Similarity", 0.54321);
        m.put("Euclidean-Distance", 0.54321);
        m.put("Jaccard-Distance", 0.54321);
        m.put("Overlapping-all", 0.54321);
        m.put("Overlapping-left-right", 0.54321);
        m.put("Overlapping-right-left", 0.54321);
        m.put("Weighted-Scoring", 0.54321);
        m.put("Size-left", 0.54321);
        m.put("Size-right", 0.54321);
        METRIC = new Metric(m);
    }
    
    
    @Mock
    private Terms terms;
    @Mock
    private Endpoints endpoints;
    @Mock
    private Texts text;
    @Mock
    private Expressions expressions;
    @Mock
    private Compare compare;
    @Mock
    private Images images;
    @Mock
    private Classify classify;
    
    private FullClient client;
    
    /**
     * initialization.
     */
    @Before
    public void before() {
        initMocks(this);
        client = new FullClient(NOT_NULL_API_KEY, NOT_NULL_BASE_PATH, NOT_NULL_RETINA, endpoints);
    }

    @Test
    public void testClientConstruction() {
        // Test optimistic path for two options
        FullClient client = new FullClient(NOT_NULL_API_KEY);
        assertNotNull(client);
        
        client = new FullClient(NOT_NULL_API_KEY, "api.cortical.io", "en_associative");
        assertNotNull(client);
        
        // Test negative path for two options
        try {
            client = new FullClient(null);
            fail(); // Problem if reached
        }catch(Exception e) {
            assertEquals(IllegalArgumentException.class, e.getClass());
            assertEquals("The apiKey cannot be null.", e.getMessage());
        }
        
        try {
            client = new FullClient(NOT_NULL_API_KEY, null, "en_associative");
            fail(); // Problem if reached
        }catch(Exception e) {
            assertEquals(IllegalArgumentException.class, e.getClass());
            assertEquals("The retina server ip cannot be null.", e.getMessage());
        }
        
        try {
            client = new FullClient(NOT_NULL_API_KEY, "api.cortical.io", null);
            fail(); // Problem if reached
        }catch(Exception e) {
            assertEquals(IllegalArgumentException.class, e.getClass());
            assertEquals("The retinaName cannot be null.", e.getMessage());
        }
    }
    
    @Test
    public void testGetTerm() throws ApiException {
        int count = 5;
        when(endpoints.termsApi()).thenReturn(terms);
        when(terms.getTerms(eq(TERM), eq(0), eq(5), eq(false))).thenReturn(createTerms(count));
        List<Term> termsList = client.getTerms(TERM, 0, 5, false);
        assertEquals(count, termsList.size());
        verify(terms, times(1)).getTerms(eq(TERM), eq(0), eq(5), eq(false));
    }
    
    /**
     * {@link FullClient#getTerms(String, int, int, boolean)} test method.
     * 
     * @throws ApiException should never be thrown
     */
    @Test
    public void testGetTerm_withfingerprint() throws ApiException {
        int count = 5;
        boolean getFingerprint = true;
        when(endpoints.termsApi()).thenReturn(terms);
        when(terms.getTerms(eq(TERM), eq(0), eq(5), eq(getFingerprint))).thenReturn(createTerms(count));
        List<Term> termsList = client.getTerms(TERM, 0, 5, getFingerprint);
        assertEquals(count, termsList.size());
        verify(terms, times(1)).getTerms(eq(TERM), eq(0), eq(5), eq(getFingerprint));
    }
    
    /**
     * {@link FullClient#getTerms(String, int, int, boolean)} test method.
     * 
     * @throws ApiException should never be thrown
     */
    @Test
    public void testGetTerm_withFingerprintAndStartIndex() throws ApiException {
        int count = 5;
        boolean getFingerprint = true;
        int startIndex = 0;
        int maxResults = 100;
        when(endpoints.termsApi()).thenReturn(terms);
        when(terms.getTerms(eq(TERM), eq(startIndex), eq(maxResults), eq(getFingerprint))).thenReturn(
            createTerms(count));
        List<Term> termsList = client.getTerms(TERM, startIndex, maxResults, getFingerprint);
        assertEquals(count, termsList.size());
        verify(terms, times(1)).getTerms(eq(TERM), eq(startIndex), eq(maxResults), eq(getFingerprint));
    }
    
    /**
     * {@link FullClient#getKeywordsForText(String)} test method.
     * 
     * @throws ApiException     should never be thrown
     */
    @Test
    public void testGetKeywordsForText() throws ApiException {
        when(endpoints.textApi()).thenReturn(text);
        when(text.getKeywordsForText(eq(TEXT))).thenReturn(asList(KEYWORDS));
        List<String> keywords = client.getKeywordsForText(TEXT);
        assertEquals("[KEY1, KEY2, KEY3]", keywords.toString());
        verify(text, times(1)).getKeywordsForText(eq(TEXT));
    }
    
    /**
     * {@link Texts#getFingerprintForText(String)} test method.
     * 
     * @throws ApiException : should never be thrown
     */
    @Test
    public void testGetFingerprintForText() throws ApiException {
        int count = 1;
        when(endpoints.textApi()).thenReturn(text);
        when(text.getFingerprintForText(eq(TEXT))).thenReturn(createFingerprints(count).get(0));
        Fingerprint fingerprint = client.getFingerprintForText(TEXT);
        assertEquals("[181, 514, 612, 785, 861, 895, 1315, 1321, 1485, 1496, 2235, 2466, 2474, "
            + "2489, 2599, 2821, 2906, 2937, 3092, 3210, 3261, 3436, 3596, 4106, "
            + "4492, 4517, 4539, 4596, 4778, 5058, 5186, 5542, 5649, 5864, 5902, "
            + "5982, 6042, 6047, 6200, 6252, 6333, 6843, 6897, 7121, 7148, 7151, "
            + "7205, 7393, 7492, 7541, 7596, 7684, 7744, 7873, 7886, 7972, 8732, "
            + "8981, 8993, 9355, 9503, 9624, 9737, 9762, 10344, 10430, 10545, "
            + "10629, 10904, 11193, 11311, 11402, 11595, 11688, 11920, 12286, "
            + "12308, 12329, 12472, 12486, 12608, 12827, 12920, 13079, 13084, "
            + "13398, 13442, 13532, 13554, 13662, 14183, 14310, 14800, 15062, "
            + "15247, 15434, 15562, 15580, 15769, 15958, 16354]", 
            Arrays.toString(fingerprint.getPositions()));
        verify(text, times(1)).getFingerprintForText(eq(TEXT));
    }
    
    /**
     * {@link FullClient#getFingerprintsForTexts(String)} test method.
     * 
     * @throws ApiException : should never be thrown
     */
    @Test
    public void testGetFingerprintsForTexts() throws ApiException, JsonProcessingException {
        double sparsity = 0.02;
        int count = 4;
        when(endpoints.textApi()).thenReturn(text);
        when(text.getFingerprintsForTexts(eq(Arrays.asList(TEXT, TEXT_2, TEXT_3)), eq(sparsity)))
            .thenReturn(createFingerprints(count, sparsity));
        List<Fingerprint> fingerprints = client.getFingerprintsForTexts(Arrays.asList(TEXT, TEXT_2, TEXT_3), 
            sparsity);
        assertEquals(sparsity, ((double)fingerprints.get(0).getPositions().length)/16384.0d, 0.001);
        assertEquals(count, fingerprints.size());
        verify(text, times(1)).getFingerprintsForTexts(eq(Arrays.asList(TEXT, TEXT_2, TEXT_3)), eq(sparsity));
    }
    
    /**
     * {@link FullClient#getTokensForText(String)} test method.
     * 
     * @throws ApiException : should never be thrown
     * @throws JsonProcessingException 
     */
    @Test
    public void testGetTokensForText() throws ApiException, JsonProcessingException {
        int count = 4;
        Set<PosTag> expectedPosTags =  new LinkedHashSet<>(Arrays.asList(CC,CD,DT,EX,FW,IN,JJ,JJR,JJS,JJSS,LRB,LS,MD,
            NN,NNP,NNPS,NNS,NP,NPS,PDT,POS,PP,PRPR$,PRP,PRP$,RB,RBR,RBS,RP,STAART,SYM,TO,UH,VBD,VBG,VBN,VBP,VB,VBZ,
                WDT,WP$,WP,WRB));
        when(endpoints.textApi()).thenReturn(text);
        when(text.getTokensForText(eq(TEXT), eq(FullClient.DEFAULT_TAGS))).thenReturn(createStrings(count));
        List<String> tokens = client.getTokensForText(TEXT);
        assertEquals(count, tokens.size());
        verify(text, times(1)).getTokensForText(eq(TEXT), eq(expectedPosTags));
    }
    
    /**
     * {@link CoreClient#getTokensForText(String, Set<PosTag>)} test method.
     * 
     * @throws ApiException     should never be thrown
     * @throws JsonProcessingException 
     */
    @Test
    public void testGetTokensForText_withPos() throws ApiException, JsonProcessingException {
        Set<PosTag> tags = new LinkedHashSet<>(Arrays.asList(PosTag.DT, PosTag.JJS));
        int count = 4;
        when(endpoints.textApi()).thenReturn(text);
        when(text.getTokensForText(eq(TEXT), eq(tags))).thenReturn(createStrings(count));
        List<String> tokens = client.getTokensForText(TEXT, tags);
        assertEquals(count, tokens.size());
        verify(text, times(1)).getTokensForText(eq(TEXT), eq(tags));
    }
    
    /**
     * {@link FullClient#getSlicesForText(String, int, int, boolean)} test method.
     * 
     * @throws ApiException     should never be thrown
     * @throws JsonProcessingException 
     */
    @Test
    public void testGetSlicesForText() throws ApiException, JsonProcessingException {
        int count = 4;
        when(endpoints.textApi()).thenReturn(text);
        when(text.getSlicesForText(eq(TEXT), eq(0), eq(10), eq(false))).thenReturn(createTexts(count));
        List<Text> textsList = client.getSlicesForText(TEXT);
        assertEquals(count, textsList.size());
        verify(text, times(1)).getSlicesForText(eq(TEXT), eq(0), eq(10), eq(false));
    }
    
    /**
     * {@link FullClient#getSlicesForText(String, int, int, boolean)} test method.
     * 
     * @throws ApiException     should never be thrown
     * @throws JsonProcessingException 
     */
    @Test
    public void testGetSlicesForText_wParams() throws ApiException, JsonProcessingException {
        int count = 4;
        when(endpoints.textApi()).thenReturn(text);
        when(text.getSlicesForText(eq(TEXT), eq(0), eq(10), eq(false))).thenReturn(createTexts(count));
        List<Text> textsList = client.getSlicesForText(TEXT, 0, 10, false);
        assertEquals(count, textsList.size());
        verify(text, times(1)).getSlicesForText(eq(TEXT), eq(0), eq(10), eq(false));
    }
    
    /**
     * Tests {@link FullClient#getLanguageForText(String)}
     * @throws ApiException     should never be thrown
     * @throws JsonProcessingException
     */
    @Test
    public void testGetLanguageForText() throws ApiException, JsonProcessingException {
        String testText = "Identifies the language of the text and returns it";
        when(endpoints.textApi()).thenReturn(text);
        when(text.getLanguageForText(eq(testText))).thenReturn(createLanguage());
        Language lr = client.getLanguageForText(testText);
        assertEquals("English", lr.getLanguage());
        assertEquals("en", lr.getIso_tag());
        assertEquals("http://en.wikipedia.org/wiki/English_language", lr.getWiki_url());
        verify(text, times(1)).getLanguageForText(eq(testText));
    }
    
    /**
     * {@link FullClient#getFingerprintForExpression(io.cortical.retina.model.Model, double)}
     * @throws JsonProcessingException      should never be thrown
     * @throws ApiException     should never be thrown
     */
    @Test
    public void testGetFingerprintForExpression() throws ApiException, JsonProcessingException {
        double sparsity = 0.02;
        
        when(endpoints.expressionsApi()).thenReturn(expressions);
        when(expressions.getFingerprintForExpression(eq(TERM_1), eq(FullClient.DEFAULT_SPARSITY))).thenReturn(
            createFingerprint(sparsity));
        Fingerprint fingerprint = client.getFingerprintForExpression(TERM_1);
        assertEquals(Math.rint(16384.* 0.02), fingerprint.getPositions().length, 0.001);
        verify(expressions, times(1)).getFingerprintForExpression(eq(TERM_1), eq(FullClient.DEFAULT_SPARSITY));
    }
    
    /**
     * {@link FullClient#getFingerprintForExpression(io.cortical.retina.model.Model, double)}
     * @throws JsonProcessingException      should never be thrown
     * @throws ApiException     should never be thrown
     */
    @Test
    public void testGetFingerprintForExpression_withSparsity() throws ApiException, JsonProcessingException {
        double sparsity = 0.02;
        
        when(endpoints.expressionsApi()).thenReturn(expressions);
        when(expressions.getFingerprintForExpression(eq(TERM_1), eq(0.02))).thenReturn(createFingerprint(sparsity));
        Fingerprint fingerprint = client.getFingerprintForExpression(TERM_1, sparsity);
        assertEquals("[124, 133, 146, 181, 192, 230, 249, 279, 442, 447, 514, 597, 612, "
            + "659, 785, 858, 861, 895, 1150, 1247, 1262, 1315, 1321, 1485, "
            + "1496, 1518, 1522, 1535, 1580, 1685, 1701, 1882, 1896, 2054, "
            + "2068, 2097, 2108, 2115, 2231, 2235, 2290, 2404, 2405, 2432, "
            + "2466, 2474, 2489, 2502, 2520, 2534, 2599, 2623, 2799, 2800, "
            + "2821, 2838, 2906, 2937, 2963, 3033, 3092, 3210, 3213, 3261, "
            + "3286, 3401, 3436, 3596, 3987, 4106, 4123, 4160, 4229, 4263, "
            + "4352, 4492, 4517, 4539, 4546, 4568, 4596, 4623, 4651, 4666, "
            + "4752, 4763, 4777, 4778, 4871, 4965, 5006, 5058, 5090, 5163, "
            + "5166, 5186, 5383, 5444, 5513, 5542, 5566, 5627, 5635, 5649, "
            + "5864, 5902, 5904, 5922, 5982, 6005, 6042, 6047, 6078, 6124, "
            + "6133, 6161, 6200, 6252, 6268, 6290, 6301, 6333, 6353, 6429, "
            + "6467, 6484, 6496, 6513, 6586, 6635, 6843, 6862, 6897, 6933, "
            + "6938, 6955, 7066, 7090, 7121, 7126, 7148, 7151, 7205, 7236, "
            + "7253, 7302, 7393, 7492, 7501, 7516, 7526, 7541, 7592, 7596, "
            + "7678, 7684, 7729, 7744, 7869, 7873, 7886, 7927, 7972, 7998, "
            + "8148, 8274, 8332, 8335, 8505, 8514, 8544, 8732, 8756, 8758, "
            + "8845, 8894, 8981, 8983, 8993, 8994, 9115, 9172, 9355, 9365, "
            + "9396, 9503, 9559, 9624, 9642, 9676, 9737, 9762, 9791, 9811, "
            + "9877, 10061, 10078, 10096, 10264, 10288, 10313, 10338, 10344, "
            + "10368, 10405, 10430, 10495, 10527, 10545, 10587, 10629, 10732, "
            + "10766, 10782, 10800, 10822, 10830, 10904, 10986, 11193, 11235, "
            + "11276, 11286, 11311, 11371, 11402, 11421, 11423, 11466, 11502, "
            + "11570, 11595, 11688, 11798, 11885, 11896, 11920, 11953, 12091, "
            + "12208, 12218, 12286, 12308, 12329, 12342, 12413, 12419, 12472, "
            + "12486, 12530, 12608, 12623, 12633, 12699, 12704, 12792, 12827, "
            + "12920, 12954, 13023, 13040, 13042, 13079, 13084, 13108, 13140, "
            + "13195, 13201, 13256, 13264, 13391, 13398, 13442, 13463, 13487, "
            + "13532, 13554, 13584, 13659, 13662, 13683, 13884, 13931, 14014, "
            + "14018, 14136, 14183, 14194, 14283, 14310, 14515, 14559, 14603, "
            + "14647, 14666, 14706, 14722, 14732, 14800, 14804, 14819, 14820, "
            + "14886, 14953, 15062, 15081, 15247, 15380, 15403, 15434, 15471, "
            + "15562, 15580, 15765, 15769, 15835, 15851, 15878, 15889, 15958, "
            + "15991, 16016, 16032, 16137, 16143, 16318, 16354, 16366]", 
            Arrays.toString(fingerprint.getPositions()));
        assertEquals(Math.rint(16384.* 0.02), fingerprint.getPositions().length, 0.001);
        verify(expressions, times(1)).getFingerprintForExpression(eq(TERM_1), eq(sparsity));
    }
    
    /**
     * {@link FullClient#getContextsForExpression(Model, int, int, double, boolean)}
     * @throws JsonProcessingException      should never be thrown
     * @throws ApiException     should never be thrown
     */
    @Test
    public void testGetContextsForExpression() throws ApiException, JsonProcessingException {
        int count = 5;
        List<Context> contexts = createContexts(count);
        
        when(endpoints.expressionsApi()).thenReturn(expressions);
        when(expressions.getContextsForExpression(eq(TERM_1), eq(0), eq(10), eq(FullClient.DEFAULT_SPARSITY), 
            eq(false))).thenReturn(contexts); 
        List<Context> actualContexts = client.getContextsForExpression(TERM_1);
        assertEquals(contexts.size(), actualContexts.size());
        verify(expressions, times(1)).getContextsForExpression(eq(TERM_1), eq(0), eq(10), 
            eq(FullClient.DEFAULT_SPARSITY), eq(false));
    }

    /**
     * {@link FullClient#getContextsForExpression(Model, int, int, double, boolean)}
     * @throws JsonProcessingException      should never be thrown
     * @throws ApiException     should never be thrown
     */
    @Test
    public void testGetContextsForExpression_wStartStop() throws ApiException, JsonProcessingException {
        int count = 5;
        List<Context> contexts = createContexts(count);
        
        when(endpoints.expressionsApi()).thenReturn(expressions);
        when(expressions.getContextsForExpression(eq(TERM_1), eq(0), eq(10), eq(0.02), eq(false))).
            thenReturn(contexts); 
        List<Context> actualContexts = client.getContextsForExpression(TERM_1, 0, 10, 0.02, false);
        assertEquals(contexts.size(), actualContexts.size());
        verify(expressions, times(1)).getContextsForExpression(eq(TERM_1), eq(0), eq(10), eq(0.02), eq(false));
    }
    
    /**
     * 
     * {@link FullClient#getContextsForExpressions(List, int, int, boolean, double)}
     * @throws JsonProcessingException      should never be thrown.
     * @throws ApiException     should never be thrown.
     */
    @Test
    public void testGetContextsForExpressions() throws JsonProcessingException, ApiException {
        int count = 5;
        List<Term> listOfTerms = Arrays.asList(TERM_1, TERM_2);
        List<List<Context>> listOfContexts = new ArrayList<>();
        listOfContexts.add(createContexts(count));
        listOfContexts.add(createContexts(count));
        
        when(endpoints.expressionsApi()).thenReturn(expressions);
        when(expressions.getContextsForExpressions(eq(listOfTerms), eq(0), eq(10), eq(false), 
            eq(FullClient.DEFAULT_SPARSITY))).thenReturn(listOfContexts); 
        List<List<Context>> actualListOfContexts = client.getContextsForExpressions(listOfTerms);
        assertEquals(listOfContexts.size(), actualListOfContexts.size());
        verify(expressions, times(1)).getContextsForExpressions(eq(listOfTerms), eq(0), eq(10), eq(false), 
            eq(FullClient.DEFAULT_SPARSITY));
    }
    
    /**
     * 
     * {@link FullClient#getContextsForExpressions(List, int, int, boolean, double)}
     * @throws JsonProcessingException      should never be thrown.
     * @throws ApiException     should never be thrown.
     */
    @Test
    public void testGetContextsForExpressions_wStartStop() throws JsonProcessingException, ApiException {
        int count = 5;
        List<Term> listOfTerms = Arrays.asList(TERM_1, TERM_2);
        List<List<Context>> listOfContexts = new ArrayList<>();
        listOfContexts.add(createContexts(count));
        listOfContexts.add(createContexts(count));
        
        when(endpoints.expressionsApi()).thenReturn(expressions);
        when(expressions.getContextsForExpressions(eq(listOfTerms), eq(0), eq(10), eq(false), eq(0.02))).
            thenReturn(listOfContexts); 
        List<List<Context>> actualListOfContexts = expressions.getContextsForExpressions(listOfTerms, 0, 10, false, 0.02);
        assertEquals(listOfContexts.size(), actualListOfContexts.size());
        verify(expressions, times(1)).getContextsForExpressions(eq(listOfTerms), eq(0), eq(10), eq(false), eq(0.02));
    }
    
    /**
     * 
     * {@link FullClient#getSimilarTermsForExpression(Model, int, int, int, PosType, boolean, double)}
     * @throws JsonProcessingException      should never be thrown
     * @throws ApiException     should never be thrown
     */
    @Test
    public void testGetSimilarTermsForExpression() throws ApiException, JsonProcessingException {
        int count = 5;
        int contextId = Context.ANY_ID;
        PosType posType = PosType.ANY;
        
        when(endpoints.expressionsApi()).thenReturn(expressions);
        when(expressions.getSimilarTermsForExpression(eq(TERM_1), eq(0), eq(10), eq(contextId), eq(posType), eq(false),
            eq(FullClient.DEFAULT_SPARSITY))).thenReturn(createTerms(count));
        List<Term> actualTerms = client.getSimilarTermsForExpression(TERM_1);
        assertEquals(count, actualTerms.size());
        verify(expressions, times(1)).getSimilarTermsForExpression(eq(TERM_1), eq(0), eq(10), eq(contextId), 
            eq(posType), eq(false), eq(FullClient.DEFAULT_SPARSITY));
    }
    
    /**
     * 
     * {@link FullClient#getSimilarTermsForExpression(Model, int, int, int, PosType, boolean, double)}
     * @throws JsonProcessingException      should never be thrown
     * @throws ApiException     should never be thrown
     */
    @Test
    public void testGetSimilarTermsForExpression_wStartStop() throws ApiException, JsonProcessingException {
        int count = 5;
        int contextId = 0;
        PosType posType = PosType.NOUN;
        
        when(endpoints.expressionsApi()).thenReturn(expressions);
        when(expressions.getSimilarTermsForExpression(
            eq(TERM_1), eq(0), eq(10), eq(contextId), eq(posType), eq(false), eq(0.02))).
                thenReturn(createTerms(count));
        List<Term> actualTerms = client.getSimilarTermsForExpression(TERM_1, 0, 10, contextId, posType, false, 0.02);
        assertEquals(count, actualTerms.size());
        verify(expressions, times(1)).getSimilarTermsForExpression(eq(TERM_1), eq(0), eq(10), eq(contextId), eq(posType), eq(false), eq(0.02));
    }
    
    /**
     * 
     * {@link FullClient#getSimilarTermsForExpressions(List, int, int, int, PosType, boolean, double)}
     * @throws JsonProcessingException  should never be thrown
     * @throws ApiException     should never be thrown
     */
    @Test
    public void testGetSimilarTermsForExpressions() throws ApiException, JsonProcessingException {
        int count = 5;
        int contextId = Context.ANY_ID;
        double sparsity = FullClient.DEFAULT_SPARSITY;
        PosType posType = PosType.ANY;
        List<Term> listOfTerms = Arrays.asList(TERM_1, TERM_2);
        List<List<Term>> listOfSimTerms = new ArrayList<>();
        listOfSimTerms.add(createTerms(count));
        listOfSimTerms.add(createTerms(count));
        
        when(endpoints.expressionsApi()).thenReturn(expressions);
        when(expressions.getSimilarTermsForExpressions(
            eq(listOfTerms), eq(0), eq(10), eq(contextId), eq(posType), eq(false), eq(sparsity))).
                thenReturn(listOfSimTerms);
        List<List<Term>> actualTerms = client.getSimilarTermsForExpressions(listOfTerms);
        assertEquals(listOfSimTerms.size(), actualTerms.size());
        verify(expressions, times(1)).getSimilarTermsForExpressions(
            eq(listOfTerms), eq(0), eq(10), eq(contextId), eq(posType), eq(false), eq(sparsity));
    }
    
    /**
     * 
     * {@link FullClient#getSimilarTermsForExpressions(List, int, int, int, PosType, boolean, double)}
     * @throws JsonProcessingException  should never be thrown
     * @throws ApiException     should never be thrown
     */
    @Test
    public void testGetSimilarTermsForEnewxpressions_wStartStop() throws ApiException, JsonProcessingException {
        int count = 5;
        int contextId = 0;
        PosType posType = PosType.NOUN;
        List<Term> listOfTerms = Arrays.asList(TERM_1, TERM_2);
        List<List<Term>> listOfSimTerms = new ArrayList<>();
        listOfSimTerms.add(createTerms(count));
        listOfSimTerms.add(createTerms(count));
        
        when(endpoints.expressionsApi()).thenReturn(expressions);
        when(expressions.getSimilarTermsForExpressions(
            eq(listOfTerms), eq(0), eq(10), eq(contextId), eq(posType), eq(false), eq(0.02))).
                thenReturn(listOfSimTerms);
        List<List<Term>> actualTerms = client.getSimilarTermsForExpressions(listOfTerms, 0, 10, contextId,
            posType, false, 0.02);
        assertEquals(listOfSimTerms.size(), actualTerms.size());
        verify(expressions, times(1)).getSimilarTermsForExpressions(
            eq(listOfTerms), eq(0), eq(10), eq(contextId), eq(posType), eq(false), eq(0.02));
    }
    
    /**
     * 
     * {@link FullClient#compare(io.cortical.retina.model.Model, io.cortical.retina.model.Model)} method test.
     * @throws JsonProcessingException  should never be thrown.
     * @throws ApiException     should never be thrown.
     */
    @Test
    public void compareTest_javaModels() throws JsonProcessingException, ApiException {
        when(endpoints.compareApi()).thenReturn(compare);
        when(compare.compare(eq(TERM_1), eq(TEXT_1))).thenReturn(METRIC);
        Metric metric = client.compare(TERM_1, TEXT_1);
        assertEquals(METRIC, metric);
        verify(compare, times(1)).compare(eq(TERM_1), eq(TEXT_1));
    }
    
    /**
     * 
     * {@link FullClient#compareBulk(List)}
     * @throws JsonProcessingException : should never be thrown.
     * @throws ApiException : should never be thrown.
     */
    @Test
    public void compareTest_bulk() throws JsonProcessingException, ApiException {
        List<CompareModel> compareModels = Arrays.asList(
            new CompareModel(TERM_1, TEXT_1), new CompareModel(TERM_1, TEXT_1), new CompareModel(TERM_1, TEXT_1));
        
        Model[][] toCompare = new Model[compareModels.size()][2];
        int i = 0;
        for (CompareModel pair: compareModels) {
            toCompare[i++] = pair.getModels();
        }
        
        Metric[] metrics = new Metric[] { METRIC, METRIC, METRIC };
        when(endpoints.compareApi()).thenReturn(compare);
        when(compare.compareBulk(eq(compareModels))).thenReturn(metrics);
        Metric[] retVal = client.compareBulk(compareModels);
        assertTrue(Arrays.equals(metrics, retVal));
        verify(compare, times(1)).compareBulk(eq(compareModels));
    }
    
    /**
     * {@link FullClient#getImage(Model)}
     * 
     * @throws ApiException : should never be thrown.
     * @throws IOException 
     */
    @Test
    public void testGetImage() throws ApiException, IOException {
        ImagePlotShape shape =  ImagePlotShape.CIRCLE;
        ImageEncoding encoding = ImageEncoding.BASE64_PNG;
        double sparsity = FullClient.DEFAULT_SPARSITY;
        
        when(endpoints.imageApi()).thenReturn(images);
        when(images.getImage(eq(TERM_1), eq(1), eq(shape),
            eq(encoding), eq(sparsity))).thenReturn(new ByteArrayInputStream(new byte[] { "i".getBytes()[0] }));
        
        ByteArrayInputStream stream = client.getImage(TERM_1);
        assertNotNull(stream);
        assertEquals(105, stream.read());
        stream.close();
        verify(images, times(1)).getImage(eq(TERM_1), eq(1), eq(shape), eq(encoding), eq(sparsity));
    }
    
    /**
     * {@link FullClient#getImage(Model, Integer, ImagePlotShape, ImageEncoding, double)}
     * 
     * @throws ApiException     should never be thrown.
     * @throws IOException 
     */
    @Test
    public void testGetImage_nonSimple() throws ApiException, IOException {
        ImagePlotShape shape =  ImagePlotShape.CIRCLE;
        ImageEncoding encoding = ImageEncoding.BASE64_PNG;
        double sparsity = 0.02;
        
        when(endpoints.imageApi()).thenReturn(images);
        when(images.getImage(eq(TERM_1), eq(1), eq(shape),
            eq(encoding), eq(sparsity))).thenReturn(new ByteArrayInputStream(new byte[] { "i".getBytes()[0] }));
        
        ByteArrayInputStream stream = images.getImage(TERM_1, 1, shape, encoding, sparsity);
        assertNotNull(stream);
        assertEquals(105, stream.read());
        stream.close();
        verify(images, times(1)).getImage(eq(TERM_1), eq(1), eq(shape), eq(encoding), eq(sparsity));
    }
    
    /**
     * {@link FullClient#getImages(List)}
     * 
     * @throws JsonProcessingException : should never be thrown.
     * @throws ApiException : should never be thrown.
     */
    @Test
    public void testGetImages() throws ApiException, JsonProcessingException {
        ImagePlotShape shape = ImagePlotShape.CIRCLE;
        double sparsity = FullClient.DEFAULT_SPARSITY;
        List<Term> terms = Arrays.asList(TERM_1, TERM_2);
        List<Image> expected = Arrays.asList(new Image("i".getBytes(), null), new Image("i".getBytes(), null));
        
        when(endpoints.imageApi()).thenReturn(images);
        when(images.getImages(eq(terms), eq(false), eq(1), eq(shape), eq(sparsity))).thenReturn(expected);
        List<Image> retImages = client.getImages(terms);
        assertNotNull(retImages);
        assertEquals(2, retImages.size());
        verify(images, times(1)).getImages(eq(terms), eq(false), eq(1), eq(shape), eq(sparsity));
    }
    
    /**
     * {@link FullClient#getImages(List, boolean, int, ImagePlotShape, double)}
     * 
     * @throws JsonProcessingException : should never be thrown.
     * @throws ApiException : should never be thrown.
     */
    @Test
    public void testGetImages_nonSimple() throws ApiException, JsonProcessingException {
        ImagePlotShape shape = ImagePlotShape.CIRCLE;
        double sparsity = 0.02;
        List<Term> terms = Arrays.asList(TERM_1, TERM_2);
        List<Image> expected = Arrays.asList(new Image("i".getBytes(), null), new Image("i".getBytes(), null));
        
        when(endpoints.imageApi()).thenReturn(images);
        when(images.getImages(eq(terms), eq(false), eq(1), eq(shape),
            eq(sparsity))).thenReturn(expected);
        List<Image> retImages = client.getImages(terms, false, 1, shape, sparsity);
        assertNotNull(retImages);
        assertEquals(2, retImages.size());
        verify(images, times(1)).getImages(eq(terms), eq(false), eq(1), eq(shape), eq(sparsity));
    }
    
    /**
     * {@link FullClient#compareImage(List)}
     * 
     * @throws ApiException : should never be thrown.
     * @throws IOException 
     */
    @Test
    public void compareModelTest() throws ApiException, IOException {
        ImagePlotShape shape =  ImagePlotShape.CIRCLE;
        ImageEncoding encoding = ImageEncoding.BASE64_PNG;
        List<Term> terms = Arrays.asList(TERM_1, TERM_2);
        
        when(endpoints.imageApi()).thenReturn(images);
        when(images.compareImage(eq(terms), eq(1), eq(shape), eq(encoding))).thenReturn(
                new ByteArrayInputStream(new byte[] { "i".getBytes()[0] }));
        ByteArrayInputStream stream = client.compareImage(terms);
        assertNotNull(stream);
        assertEquals(105, stream.read());
        stream.close();
        verify(images, times(1)).compareImage(eq(terms), eq(1), eq(shape), eq(encoding));
    }
    
    /**
     * {@link FullClient#compareImage(List, int, ImagePlotShape, ImageEncoding)}
     * 
     * @throws ApiException : should never be thrown.
     * @throws IOException 
     */
    @Test
    public void compareModelTest_nonSimple() throws ApiException, IOException {
        ImagePlotShape shape =  ImagePlotShape.CIRCLE;
        ImageEncoding encoding = ImageEncoding.BASE64_PNG;
        List<Term> terms = Arrays.asList(TERM_1, TERM_2);
        
        when(endpoints.imageApi()).thenReturn(images);
        when(images.compareImage(eq(terms), eq(1), eq(shape), eq(encoding))).thenReturn(
                new ByteArrayInputStream(new byte[] { "i".getBytes()[0] }));
        ByteArrayInputStream stream = client.compareImage(terms, 1, shape, encoding);
        assertNotNull(stream);
        assertEquals(105, stream.read());
        stream.close();
        verify(images, times(1)).compareImage(eq(terms), eq(1), eq(shape), eq(encoding));
    }
    
    /**
     * {@link FullClient#createCategoryFilter(String, List, List)}
     *
     * @throws io.cortical.services.api.client.ApiException : should never be thrown
     * @throws JsonProcessingException 
     */
    @Test
    public void testCreateCategoryFilter() throws ApiException, JsonProcessingException {
        List<String> pos = Arrays.asList(
            "Shoe with a lining to help keep your feet dry and comfortable on wet terrain.",
            "running shoes providing protective cushioning.");
        List<String> neg = Arrays.asList(
            "The most comfortable socks for your feet.",
            "6 feet USB cable basic white");
        
        when(endpoints.classifyApi()).thenReturn(classify);
        when(classify.createCategoryFilter(eq("12"), eq(pos), eq(neg))).thenReturn(cf);
        CategoryFilter result = client.createCategoryFilter("12", pos, neg);
        assertTrue(result.getCategoryName().equals("12"));
        assertTrue(result.getPositions().length == 3);
    }
}
