package io.cortical.retina.client;

import static io.cortical.retina.core.ApiTestUtils.NOT_NULL_API_KEY;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import io.cortical.retina.rest.ApiException;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * To run test in Eclipse, go to > Run Configurations... > Arguments (tab) > VM Arguments > 
 * paste in:
 * -DapiKey=nnn-nnn-nnn  (where "nnn-nnn-nnn" is your api key) 
 */
public class LiteClientIntegrationTest {
    private LiteClient client;
    
    /**
     * initialization.
     */
    @Before
    public void before() {
        String key = System.getProperty("apiKey");
        client = new LiteClient(key, "api.cortical.io", "en_associative");
    }

    @Test
    public void testClientConstruction() {
        // Test optimistic path for two options
        LiteClient client = new LiteClient(NOT_NULL_API_KEY);
        assertNotNull(client);
        
        client = new LiteClient(NOT_NULL_API_KEY, "api.cortical.io", "en_associative");
        assertNotNull(client);
        
        // Test negative path for two options
        try {
            client = new LiteClient(null);
            fail(); // Problem if reached
        }catch(Exception e) {
            assertEquals(IllegalArgumentException.class, e.getClass());
            assertEquals("The apiKey cannot be null.", e.getMessage());
        }
        
        try {
            client = new LiteClient(NOT_NULL_API_KEY, null, "en_associative");
            fail(); // Problem if reached
        }catch(Exception e) {
            assertEquals(IllegalArgumentException.class, e.getClass());
            assertEquals("The retina server ip cannot be null.", e.getMessage());
        }
        
        try {
            client = new LiteClient(NOT_NULL_API_KEY, "api.cortical.io", null);
            fail(); // Problem if reached
        }catch(Exception e) {
            assertEquals(IllegalArgumentException.class, e.getClass());
            assertEquals("The retinaName cannot be null.", e.getMessage());
        }
    }

    @Test
    public void testGetSimilarTerms() throws JsonProcessingException, ApiException {
        List<String> similarTerms = client.getSimilarTerms("Jaguar");
        assertNotNull(similarTerms);
        assertTrue(similarTerms.size() > 1);
    }
    
    @Test
    public void testGetKeywords() throws ApiException {
        List<String> keywords = client.getKeywords("The retina server ip cannot be null.");
        assertNotNull(keywords);
        assertTrue(keywords.size() > 1);
    }
    
    @Test
    public void testGetFingerprint() throws JsonProcessingException, ApiException {
        int[] fingerprint = client.getFingerprint("Jaguar");
        assertNotNull(fingerprint);
        assertTrue(fingerprint.length > 1);
        
        fingerprint = client.getFingerprint("The retina server ip cannot be null.");
        assertNotNull(fingerprint);
        assertTrue(fingerprint.length > 1);
    }
    
    @Test
    public void testCompare() throws JsonProcessingException, ApiException {
        double similarity = client.compare("The retina server ip cannot be null.", "The retina server ip cannot be null.");
        assertTrue(similarity > 0);
        assertEquals(1.0, similarity, 0.0001);
    }
    
    @Test
    public void testCompare_1_Fingerprint() throws JsonProcessingException, ApiException {
        int[] fingerprint = client.getFingerprint("The retina server ip cannot be null.");
        double similarity = client.compare("The retina server ip cannot be null.", fingerprint);
        assertTrue(similarity > 0);
        assertEquals(1.0, similarity, 0.0001);
    }
    
    @Test
    public void testCompare_2_Fingerprints() throws JsonProcessingException, ApiException {
        int[] fingerprint = client.getFingerprint("The retina server ip cannot be null.");
        double similarity = client.compare(fingerprint, fingerprint);
        assertTrue(similarity > 0);
        assertEquals(1.0, similarity, 0.0001);
    }
    
    @Test
    public void testCreateCategoryFilter() throws JsonProcessingException, ApiException {
        List<String> pos = Arrays.asList(
            "Shoe with a lining to help keep your feet dry and comfortable on wet terrain.",
            "running shoes providing protective cushioning.");
        int[] fingerprint = client.createCategoryFilter(pos);
        assertNotNull(fingerprint);
        assertTrue(fingerprint.length > 10);
    }
}
