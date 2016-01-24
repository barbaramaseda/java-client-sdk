package io.cortical.retina.client;

import static io.cortical.retina.core.PosTag.*;
import static io.cortical.retina.core.ApiTestUtils.NOT_NULL_API_KEY;
import static io.cortical.retina.core.ApiTestUtils.NOT_NULL_BASE_PATH;
import static io.cortical.retina.core.ApiTestUtils.NOT_NULL_RETINA;
import static io.cortical.retina.model.TestDataHarness.createFingerprints;
import static io.cortical.retina.model.TestDataHarness.createLanguage;
import static io.cortical.retina.model.TestDataHarness.createStrings;
import static io.cortical.retina.model.TestDataHarness.createTerms;
import static io.cortical.retina.model.TestDataHarness.createTexts;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import io.cortical.retina.core.Endpoints;
import io.cortical.retina.core.PosTag;
import io.cortical.retina.core.Terms;
import io.cortical.retina.core.Texts;
import io.cortical.retina.model.Fingerprint;
import io.cortical.retina.model.LanguageRest;
import io.cortical.retina.model.Term;
import io.cortical.retina.model.Text;
import io.cortical.retina.service.ApiException;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import com.fasterxml.jackson.core.JsonProcessingException;


public class CoreClientTest {
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
    
    
    @Mock
    private Terms terms;
    @Mock
    private Endpoints endpoints;
    @Mock
    private Texts text;
    
    private CoreClient client;
    
    /**
     * initialization.
     */
    @Before
    public void before() {
        initMocks(this);
        client = new CoreClient(NOT_NULL_API_KEY, NOT_NULL_BASE_PATH, NOT_NULL_RETINA, endpoints);
    }

    @Test
    public void testClientConstruction() {
        // Test optimistic path for two options
        CoreClient client = new CoreClient(NOT_NULL_API_KEY);
        assertNotNull(client);
        
        client = new CoreClient(NOT_NULL_API_KEY, "api.cortical.io", "en_associative");
        assertNotNull(client);
        
        // Test negative path for two options
        try {
            client = new CoreClient(null);
            fail(); // Problem if reached
        }catch(Exception e) {
            assertEquals(IllegalArgumentException.class, e.getClass());
            assertEquals("The apiKey cannot be null.", e.getMessage());
        }
        
        try {
            client = new CoreClient(NOT_NULL_API_KEY, null, "en_associative");
            fail(); // Problem if reached
        }catch(Exception e) {
            assertEquals(IllegalArgumentException.class, e.getClass());
            assertEquals("The retina server ip cannot be null.", e.getMessage());
        }
        
        try {
            client = new CoreClient(NOT_NULL_API_KEY, "api.cortical.io", null);
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
     * {@link CoreClient#getTerms(String, int, int, boolean)} test method.
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
     * {@link CoreClient#getTerms(String, int, int, boolean)} test method.
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
        when(terms.getTerms(eq(TERM), eq(startIndex), eq(maxResults), eq(getFingerprint))).thenReturn(createTerms(count));
        List<Term> termsList = client.getTerms(TERM, startIndex, maxResults, getFingerprint);
        assertEquals(count, termsList.size());
        verify(terms, times(1)).getTerms(eq(TERM), eq(startIndex), eq(maxResults), eq(getFingerprint));
    }
    
    /**
     * {@link CoreClient#getKeywordsForText(String)} test method.
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
     * {@link CoreClient#getFingerprintsForTexts(String)} test method.
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
     * {@link CoreClient#getTokensForText(String)} test method.
     * 
     * @throws ApiException : should never be thrown
     * @throws JsonProcessingException 
     */
    @Test
    public void testGetTokensForText() throws ApiException, JsonProcessingException {
        int count = 4;
        Set<PosTag> expectedPosTags =  new LinkedHashSet<>(Arrays.asList(CC,CD,DT,EX,FW,IN,JJ,JJR,JJS,JJSS,LRB,LS,MD,NN,NNP,NNPS,NNS,NP,NPS,PDT,POS,PP,PRPR$,
            PRP,PRP$,RB,RBR,RBS,RP,STAART,SYM,TO,UH,VBD,VBG,VBN,VBP,VB,VBZ,WDT,WP$,WP,WRB));
        when(endpoints.textApi()).thenReturn(text);
        when(text.getTokensForText(eq(TEXT), eq(CoreClient.DEFAULT_TAGS))).thenReturn(createStrings(count));
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
     * {@link CoreClient#getSlicesForText(String, int, int, boolean)} test method.
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
     * {@link CoreClient#getSlicesForText(String, int, int, boolean)} test method.
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
     * Tests {@link CoreClient#getLanguageForText(String)}
     * @throws ApiException     should never be thrown
     * @throws JsonProcessingException
     */
    @Test
    public void testGetLanguageForText() throws ApiException, JsonProcessingException {
        String testText = "Identifies the language of the text and returns it";
        when(endpoints.textApi()).thenReturn(text);
        when(text.getLanguageForText(eq(testText))).thenReturn(createLanguage());
        LanguageRest lr = client.getLanguageForText(testText);
        assertEquals("English", lr.getLanguage());
        assertEquals("en", lr.getIso_tag());
        assertEquals("http://en.wikipedia.org/wiki/English_language", lr.getWiki_url());
        verify(text, times(1)).getLanguageForText(eq(testText));
    }

}
