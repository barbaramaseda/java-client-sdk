/*******************************************************************************
 * Copyright (c) cortical.io GmbH. All rights reserved.
 *  
 * This software is confidential and proprietary information.
 * You shall use it only in accordance with the terms of the
 * license agreement you entered into with cortical.io GmbH.
 ******************************************************************************/
package io.cortical.retina.core;

import static io.cortical.retina.rest.RestServiceConstants.NULL_API_KEY_MSG;
import static io.cortical.retina.rest.RestServiceConstants.NULL_BASE_PATH_MSG;
import static org.apache.commons.lang3.StringUtils.isBlank;
import io.cortical.retina.model.Image;
import io.cortical.retina.model.Model;
import io.cortical.retina.rest.ApiException;
import io.cortical.retina.rest.ImageApi;

import java.io.ByteArrayInputStream;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;



/**
 * The Image Retina API implementation.
 * 
 */
public class Images extends AbstractEndpoint {
    /** Rest Service access for the Images endpoint */
    private final ImageApi api;
    
    Images(String apiKey, String basePath, String retinaName) {
        super(retinaName);
        
        if (isBlank(apiKey)) {
            throw new IllegalArgumentException(NULL_API_KEY_MSG);
        }
        
        if (isBlank(basePath)) {
            throw new IllegalArgumentException(NULL_BASE_PATH_MSG);
        }
        
        this.api = new ImageApi(apiKey);
        this.api.setBasePath(basePath);
    }
    
    Images(ImageApi api, String retinaName) {
        super(retinaName);
        this.api = api;
    }
    
    /**
     * Generate an image for the for the model.
     * 
     * @param model             the {@link Model} subtype for which an image is generated.
     * @param scalar            scaling factor of the image to generate
     * @param shape             shape of the plots used in the overlay image
     * @param imageEncoding     the encoding of the image.
     * @param sparsity          a sparsity value which can be applied to the image
     * 
     * @return a byte array holding the image data.
     * @throws JsonProcessingException if it is impossible to generate the request using the input model(s).
     * @throws ApiException : if there are some server or connection issues.
     */
    public ByteArrayInputStream getImage(Model model, int scalar, ImagePlotShape shape, ImageEncoding imageEncoding,
            double sparsity) throws JsonProcessingException, ApiException {
        
        String shapeString = null;
        if (shape != null) {
            shapeString = shape.name().toLowerCase();
        }
        String encodingString = null;
        if (imageEncoding != null) {
            encodingString = imageEncoding.machineRepresentation();
        }
        
        return api.getImageForExpression(model.toJson(), retinaName, scalar, shapeString, encodingString, sparsity);
    }
    
    /**
     * Returns a List of {@link Image}s for the input models.
     * 
     * @param models                List of {@link Model}s from which to produce fingerprint images.
     * @param includeFingerprint    identify if the fingerprint should  be present/provided in the images.
     * @param scalar                scaling factor of the image to generate
     * @param shape                 shape of the plots used in the overlay image
     * @param sparsity              a sparsity value which can be applied to the image
     * 
     * @return a list of images generated using the input models.
     * @throws JsonProcessingException if it is impossible to generate the request using the input model(s).
     * @throws ApiException     if there are some server or connection issues.
     */
    public <T extends Model> List<Image> getImages(List<T> models, boolean includeFingerprint, int scalar, ImagePlotShape shape, 
        double sparsity) throws JsonProcessingException, ApiException {
        
        String shapeString = null;
        if (shape != null) {
            shapeString = shape.name().toLowerCase();
        }
        return api.getImageForBulkExpressions(toJson(models), includeFingerprint, retinaName, scalar, shapeString, sparsity);
    }
    
    /**
     * Returns a visualization of the comparison of two fingerprints. 
     * <p> The returned image contains a visualization of the left and right fingerprint and the overlay of both
     *     fingerprints.
     * </p>
     * @param models            a List of {@link Model}s (list size = 2), for which the fingerprint's images are 
     *                          generated.
     * @param scalar            scaling factor of the image to generate
     * @param shape             the shape of the plots used in the overlay image
     * @param imageEncoding     the encoding of the image.
     * 
     * @return a byte array holding the image data.
     * @throws JsonProcessingException if it is impossible to generate the request using the input model(s).
     * @throws ApiException : if there are some server or connection issues.
     */
    public <T extends Model> ByteArrayInputStream compareImage(List<T> models, int scalar, ImagePlotShape shape,
        ImageEncoding imageEncoding) throws JsonProcessingException, ApiException {
        
        String shapeString = null;
        if (shape != null) {
            shapeString = shape.name().toLowerCase();
        }
        String encodingString = null;
        if (imageEncoding != null) {
            encodingString = imageEncoding.machineRepresentation();
        }
        return api.getOverlayImage(toJson(models), retinaName, shapeString, scalar, encodingString);
    }
}
