/*******************************************************************************
 * Copyright (c) cortical.io GmbH. All rights reserved.
 *  
 * This software is confidential and proprietary information.
 * You shall use it only in accordance with the terms of the
 * license agreement you entered into with cortical.io GmbH.
 ******************************************************************************/
package io.cortical.services;

import io.cortical.rest.model.Retina;
import io.cortical.services.api.client.ApiException;
import java.util.List;


/**
 * 
 * A RetinaInfoApi . 
 */
public interface Retinas {
    /**
     * Retrieve all available retinas.
     * @return all available retinas.
     */
    List<Retina> getAllRetinas() throws ApiException;
    /**
     * Find retina by name.
     * @param name : the retina's name.
     * 
     * @return retina found by name or null if there is no such retina.
     */
    Retina retinaByName(String name) throws ApiException;
}
