/*******************************************************************************
 * Copyright (c) cortical.io GmbH. All rights reserved.
 *  
 * This software is confidential and proprietary information.
 * You shall use it only in accordance with the terms of the
 * license agreement you entered into with cortical.io GmbH.
 ******************************************************************************/
package io.cortical.services;

import io.cortical.rest.model.Fingerprint;
import io.cortical.services.api.client.ApiException;
import io.cortical.services.api.client.api.FingerprintsApi;
import static org.apache.commons.lang3.StringUtils.isBlank;


/**
 * 
 * 
 */
@Deprecated
class FingerprintsRetinaApiImpl extends BaseRetinaApi implements Fingerprints {
    /**
     * 
     */
    private final FingerprintsApi api;
    
    FingerprintsRetinaApiImpl(String apiKey, String basePath, String retinaName) {
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

    /** {@inheritDoc} 
     * @throws ApiException */
    @Override
    public void deleteFingerprint(String fingerprintId) throws ApiException {
        api.deleteFingerprint(retinaName, fingerprintId);
    }

    /** {@inheritDoc} */
    @Override
    public Fingerprint getFingerprint(String fingerprintId) throws ApiException {
       return api.getFingerprint(retinaName, fingerprintId);
    }

    /** {@inheritDoc} */
    @Override
    public Fingerprint getLogicalFingerprint(String logicalName) throws ApiException {
        return api.getLogicalFingerprint(retinaName, logicalName);
    }

    /** {@inheritDoc} */
    @Override
    public void updateFingerprint(String fingerprintId, String name) throws ApiException {
        api.updateFingerprint(retinaName, fingerprintId, name);
    }
    
}
