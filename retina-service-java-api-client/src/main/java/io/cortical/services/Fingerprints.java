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


/**
 * 
 * 
 */
@Deprecated
public interface Fingerprints {
    
    void deleteFingerprint(String fingerprintId) throws ApiException;
    Fingerprint getFingerprint(String fingerprintId) throws ApiException;
    Fingerprint getLogicalFingerprint(String logicalName) throws ApiException;
    void updateFingerprint(String fingerprintId, String name) throws ApiException;
}
