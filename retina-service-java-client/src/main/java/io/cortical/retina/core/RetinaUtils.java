/*******************************************************************************
 * Copyright (c) cortical.io GmbH. All rights reserved.
 *  
 * This software is confidential and proprietary information.
 * You shall use it only in accordance with the terms of the
 * license agreement you entered into with cortical.io GmbH.
 ******************************************************************************/
package io.cortical.retina.core;

import static io.cortical.retina.service.RestServiceConstants.NULL_SERVER_IP_MSG;
import static org.apache.commons.lang3.StringUtils.isEmpty;


/**
 * Utility methods for the RetinaApis.
 * 
 */
public abstract class RetinaUtils {
    /**
     * Generate the base path for the retina.
     * 
     * @param ip : retina server ip.
     * @param port : retina service port. 
     * @return : the retina's API base path.
     */
    public static String generateBasepath(final String ip, Short port) {
        if (isEmpty(ip)) {
            throw new IllegalArgumentException(NULL_SERVER_IP_MSG);
        }
        if (port == null) {
            port = 80;
        }
        StringBuilder basePath = new StringBuilder();
        basePath.append("http://").append(ip).append(":").append(port).append("/rest");
        return basePath.toString();
    }
}
