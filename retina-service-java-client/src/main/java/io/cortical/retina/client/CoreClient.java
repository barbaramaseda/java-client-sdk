package io.cortical.retina.client;

import io.cortical.retina.core.Retinas;


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
}
