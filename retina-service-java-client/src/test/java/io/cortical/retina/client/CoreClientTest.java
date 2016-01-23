package io.cortical.retina.client;

import static io.cortical.retina.core.ApiTestUtils.NOT_NULL_API_KEY;
import static io.cortical.retina.core.ApiTestUtils.NOT_NULL_BASE_PATH;
import static io.cortical.retina.core.ApiTestUtils.NOT_NULL_RETINA;
import static io.cortical.retina.model.TestDataHarness.createTerms;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import io.cortical.retina.core.Endpoints;
import io.cortical.retina.core.Terms;
import io.cortical.retina.model.Term;
import io.cortical.retina.service.ApiException;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;


public class CoreClientTest {
    /**
     * 
     */
    private static final String TERM = "term";
    /**
     * 
     */
    @Mock
    private Terms terms;
    @Mock
    private Endpoints endpoints;
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

}
