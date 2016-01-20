/*******************************************************************************
 * Copyright (c) cortical.io GmbH. All rights reserved.
 *  
 * This software is confidential and proprietary information.
 * You shall use it only in accordance with the terms of the
 * license agreement you entered into with cortical.io GmbH.
 ******************************************************************************/
package io.cortical.retina.client.core;

import io.cortical.retina.model.Fingerprint;
import io.cortical.retina.service.ApiException;
import io.cortical.retina.service.FingerprintsApi;
import static org.apache.commons.lang3.StringUtils.isBlank;


/**
 * 
 * 
 */
class Fingerprints extends AbstractRetinas {
    /**
     * 
     */
    private final FingerprintsApi api;
    
    Fingerprints(String apiKey, String basePath, String retinaName) {
        super(retinaName);
        
        if (isBlank(apiKey)) {
            throw new IllegalArgumentException("The apiKey cannot be null.");
        }
        
        if (isBlank(basePath)) {
            throw new IllegalArgumentException("The basePath cannot be null.");
        }
        this.api = new FingerprintsApi(apiKey);
        this.api.setBasePath(basePath);
    }

    /**
     * ??? Can we really let clients delete a fingerprint?
     * @param fingerprintId
     * @throws ApiException
     */
    public void deleteFingerprint(String fingerprintId) throws ApiException {
        api.deleteFingerprint(retinaName, fingerprintId);
    }

    /**
     * Returns a {@link Fingerprint}
     * @param fingerprintId
     * @return
     * @throws ApiException
     */
    public Fingerprint getFingerprint(String fingerprintId) throws ApiException {
       return api.getFingerprint(retinaName, fingerprintId);
    }

    /**
     * Returns the {@link Fingerprint} associated with the specified name.
     * @param logicalName
     * @return
     * @throws ApiException
     */
    public Fingerprint getLogicalFingerprint(String logicalName) throws ApiException {
        return api.getLogicalFingerprint(retinaName, logicalName);
    }

    /**
     * ??? Can we really allow clients to update a {@link Fingerprint} ?
     * @param fingerprintId
     * @param name
     * @throws ApiException
     */
    public void updateFingerprint(String fingerprintId, String name) throws ApiException {
        api.updateFingerprint(retinaName, fingerprintId, name);
    }
    
}
