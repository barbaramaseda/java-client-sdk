package io.cortical.retina.client;

import static org.junit.Assert.*;
import static io.cortical.retina.core.ApiTestUtils.NOT_NULL_API_KEY;

import org.junit.Test;


public class CoreClientTest {

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
    public void testGetTerms() {
        
    }

}
