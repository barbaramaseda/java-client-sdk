/*******************************************************************************
 * Copyright (c) cortical.io GmbH. All rights reserved.
 *  
 * This software is confidential and proprietary information.
 * You shall use it only in accordance with the terms of the
 * license agreement you entered into with cortical.io GmbH.
 ******************************************************************************/
package io.cortical.retina.client.core;

import com.fasterxml.jackson.core.JsonProcessingException;

import io.cortical.retina.rest.DefaultValues;
import io.cortical.retina.model.Image;
import io.cortical.retina.model.Model;
import io.cortical.retina.service.ApiException;
import io.cortical.retina.service.ImageApi;

import java.io.ByteArrayInputStream;
import java.util.List;

import org.apache.commons.logging.Log;

import static io.cortical.retina.rest.RestServiceConstants.NULL_API_KEY_MSG;
import static io.cortical.retina.rest.RestServiceConstants.NULL_BASE_PATH_MSG;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.logging.LogFactory.getLog;



/**
 * The Image Retina API implementation.
 * 
 */
class Images extends AbstractRetinas {
    /**
     * 
     */
    private static final Log LOG = getLog(Images.class);
    /**
     * 
     */
    private final ImageApi api;
    
    Images(String apiKey, String basePath, String retinaName) {
        super(retinaName);
        
        if (isBlank(apiKey)) {
            throw new IllegalArgumentException(NULL_API_KEY_MSG);
        }
        
        if (isBlank(basePath)) {
            throw new IllegalArgumentException(NULL_BASE_PATH_MSG);
        }
        LOG.info("Initialize Image Retina Api with retina: " + retinaName);
        this.api = new ImageApi(apiKey);
        this.api.setBasePath(basePath);
    }
    
    Images(ImageApi api, String retinaName) {
        super(retinaName);
        this.api = api;
    }
    
    /**
     * Returns a List of {@link Image}s for the input models.
     * 
     * @param includeFingerprint : true if the fingerprint should  be provided in the response.
     * @param scalar : scaling factor of the image to generate
     * @param shape : shape of the plots used in the overlay image
     * @param sparsity : a sparsity value which can be applied to the image
     * @param models : models for which the images are generated.
     * @return a list of images generated using the input models.
     * @throws JsonProcessingException if it is impossible to generate the request using the input model(s).
     * @throws ApiException : if there are some server or connection issues.
     */
    public List<Image> getImageBulk(Boolean includeFingerprint, Integer scalar, ImagePlotShape shape, Double sparsity,
            Model... models) throws JsonProcessingException, ApiException {
        validateRequiredModels(models);
        return getImageBulk(includeFingerprint, scalar, shape, sparsity, toJson(models));
    }
    
    /**
     * Generate an image for the for the model.
     * 
     * @param scalar : scaling factor of the image to generate
     * @param shape : shape of the plots used in the overlay image
     * @param imageEncoding :  the encoding of the image.
     * @param sparsity : a sparsity value which can be applied to the image
     * @param model : a model for which the fingerprint's image is generated.
     * @return a byte array holding the image data.
     * @throws JsonProcessingException if it is impossible to generate the request using the input model(s).
     * @throws ApiException : if there are some server or connection issues.
     */
    public ByteArrayInputStream getImage(Integer scalar, ImagePlotShape shape, ImageEncoding imageEncoding,
            Double sparsity, Model model) throws JsonProcessingException, ApiException {
        validateRequiredModels(model);
        return getImage(scalar, shape, imageEncoding, sparsity, model.toJson());
    }
    
    /**
     * Returns a visualization of the comparison of two fingerprints. 
     * <p> The returned image contains a visualization of the left and right fingerprint and the overlay of both
     *     fingerprints.
     * </p>
     * @param scalar : scaling factor of the image to generate
     * @param shape : shape of the plots used in the overlay image
     * @param imageEncoding :  the encoding of the image.
     * @param model1 : a model for which the fingerprint's image is generated.
     * @param model2 : a model for which the fingerprint's image is generated.
     * @return a byte array holding the image data.
     * @throws JsonProcessingException if it is impossible to generate the request using the input model(s).
     * @throws ApiException : if there are some server or connection issues.
     */
    public ByteArrayInputStream compare(Integer scalar, ImagePlotShape shape, ImageEncoding imageEncoding,
            Model model1, Model model2) throws JsonProcessingException, ApiException {
        Model[] models = { model1, model2 };
        validateRequiredModels(models);
        return compare(scalar, shape, imageEncoding, toJson(models));
    }
    
    /**
     * Returns a List of {@link Image}s for the input models.
     * 
     * <br/> Whether to include a fingerprint in the response is defined by {@link DefaultValues#DEF_VALUE_PROVIDE_FINGERPRINT}
     * <br/> The default image scalar is {@link DefaultValues#DEF_VALUE_PLOT_SCALAR}
     *  
     * @param shape : shape of the plots used in the overlay image
     * @param models : models for which the images are generated.
     * @param sparsity : a sparsity value which can be applied to the image
     * @return a list of images generated using the input models.
     * @throws JsonProcessingException if it is impossible to generate the request using the input model(s).
     * @throws ApiException : if there are some server or connection issues.
     */
    public List<Image> getImageBulk(ImagePlotShape shape, Double sparsity, Model... models)
            throws JsonProcessingException, ApiException {
        return getImageBulk(null, null, shape, sparsity, models);
    }
    
    /**
     * Returns a List of {@link Image}s for the input models.
     * <br/> The default image shape is {@link DefaultValues#DEF_VALUE_PLOT_SHAPE}
     * <br/> The default image scalar is {@link DefaultValues#DEF_VALUE_PLOT_SCALAR}
     * <br/> The default sparsity is {@link DefaultValues#DEF_VALUE_SPARSITY}
     * <br/> Whether to include a fingerprint in the response is defined by {@link DefaultValues#DEF_VALUE_PROVIDE_FINGERPRINT}
     * 
     * @param models : models for which the images are generated.
     * @return a list of images generated using the input models.
     * @throws JsonProcessingException if it is impossible to generate the request using the input model(s).
     * @throws ApiException : if there are some server or connection issues.
     */
    public List<Image> getImageBulk(Model... models) throws JsonProcessingException, ApiException {
        return getImageBulk(null, null, models);
    }
    
    /**
     * Generate an image for the for the model.
     * 
     * <br/> The default image scalar is {@link DefaultValues#DEF_VALUE_PLOT_SCALAR}
     * <br/> The default image encoding is {@link DefaultValues#DEF_VALUE_IMAGE_ENCODING}
     * 
     * @param shape : shape of the plots used in the overlay image
     * @param sparsity : a sparsity value which can be applied to the image
     * @param model : a model for which the fingerprint's image is generated.
     * @return a byte array holding the image data.
     * @throws JsonProcessingException if it is impossible to generate the request using the input model(s).
     * @throws ApiException : if there are some server or connection issues.
     */
    public ByteArrayInputStream getImage(ImagePlotShape shape, Double sparsity, Model model)
            throws JsonProcessingException, ApiException {
        return getImage(null, shape, null, sparsity, model);
    }
    
    /**
     * Generate an image for the for the model.
     * 
     * <br/> The default image shape is {@link DefaultValues#DEF_VALUE_PLOT_SHAPE}
     * <br/> The default image scalar is {@link DefaultValues#DEF_VALUE_PLOT_SCALAR}
     * <br/> The default sparsity is {@link DefaultValues#DEF_VALUE_SPARSITY}
     * <br/> The default image encoding is {@link DefaultValues#DEF_VALUE_IMAGE_ENCODING}
     * 
     * @param model : a model for which the fingerprint's image is generated.
     * @return a byte array holding the image data.
     * @throws JsonProcessingException if it is impossible to generate the request using the input model(s).
     * @throws ApiException : if there are some server or connection issues.
     */
    public ByteArrayInputStream getImage(Model model) throws JsonProcessingException, ApiException {
        return getImage(null, null, model);
    }
    
    /**
     * Returns a visualization of the comparison of two fingerprints. 
     * <p> The returned image contains a visualization of the left and right fingerprint and the overlay of both
     *     fingerprints.
     * </p>
     * <br/> The default image scalar is {@link DefaultValues#DEF_VALUE_PLOT_SCALAR}
     * 
     * @param shape : shape of the plots used in the overlay image
     * @param imageEncoding :  the encoding of the image.
     * @param model1 : a model for which the fingerprint's image is generated.
     * @param model2 : a model for which the fingerprint's image is generated.
     * @return a byte array holding the image data.
     * @throws JsonProcessingException if it is impossible to generate the request using the input model(s).
     * @throws ApiException : if there are some server or connection issues.
     */
    public ByteArrayInputStream compare(ImagePlotShape shape, ImageEncoding imageEncoding, Model model1, Model model2)
            throws JsonProcessingException, ApiException {
        return compare(null, shape, imageEncoding, model1, model2);
    }
    
    /**
     * Returns a visualization of the comparison of two fingerprints. 
     * <p> The returned image contains a visualization of the left and right fingerprint and the overlay of both
     *     fingerprints.
     * </p>
     * <br/> The default image shape is {@link DefaultValues#DEF_VALUE_PLOT_SHAPE}
     * <br/> The default image scalar is {@link DefaultValues#DEF_VALUE_PLOT_SCALAR}
     * <br/> The default image encoding is {@link DefaultValues#DEF_VALUE_IMAGE_ENCODING}\
     * 
     * @param model1 : a model for which the fingerprint's image is generated.
     * @param model2 : a model for which the fingerprint's image is generated.
     * @return a byte array holding the image data.
     * @throws JsonProcessingException if it is impossible to generate the request using the input model(s).
     * @throws ApiException : if there are some server or connection issues.
     */
    public ByteArrayInputStream compare(Model model1, Model model2) throws JsonProcessingException, ApiException {
        return compare(null, null, model1, model2);
    }
    
    /**
     * Returns a List of {@link Image}s for the input models.
     * 
     * @param includeFingerprint : identify if the fingerprint should  be present/provided in the images.
     * @param scalar : scaling factor of the image to generate
     * @param shape : shape of the plots used in the overlay image
     * @param sparsity : a sparsity value which can be applied to the image
     * @param jsonModel : json model for which the images are generated.
     * @return a list of images generated using the input models.
     * @throws JsonProcessingException if it is impossible to generate the request using the input model(s).
     * @throws ApiException : if there are some server or connection issues.
     */
    public List<Image> getImageBulk(Boolean includeFingerprint, Integer scalar, ImagePlotShape shape, Double sparsity,
            String jsonModel) throws JsonProcessingException, ApiException {
        
        LOG.debug("Retrieve images for bulk expressions: model: " + jsonModel + "  scalar: " + scalar + "  sparsity: "
                + sparsity + "  shape: " + name(shape) + "  include fingerprint: " + includeFingerprint);
        String shapeString = null;
        if (shape != null) {
            shapeString = shape.name().toLowerCase();
        }
        return api.getImageForBulkExpressions(jsonModel, includeFingerprint, retinaName, scalar, shapeString, sparsity);
    }
    
    /**
     * Generate an image for the for the model.
     * 
     * @param scalar : scaling factor of the image to generate
     * @param shape : shape of the plots used in the overlay image
     * @param imageEncoding :  the encoding of the image.
     * @param sparsity : a sparsity value which can be applied to the image
     * @param jsonModel : json model for which the fingerprint's image is generated.
     * @return a byte array holding the image data.
     * @throws JsonProcessingException if it is impossible to generate the request using the input model(s).
     * @throws ApiException : if there are some server or connection issues.
     */
    public ByteArrayInputStream getImage(Integer scalar, ImagePlotShape shape, ImageEncoding imageEncoding,
            Double sparsity, String jsonModel) throws JsonProcessingException, ApiException {
        
        LOG.debug("Retrieve image for expression: model: " + jsonModel + "  scalar: " + scalar + "  sparsity: "
                + sparsity + "  shape: " + name(shape) + "  image encoding: " + name(imageEncoding));
        String shapeString = null;
        if (shape != null) {
            shapeString = shape.name().toLowerCase();
        }
        String encodingString = null;
        if (imageEncoding != null) {
            encodingString = imageEncoding.machineRepresentation();
        }
        
        return api.getImageForExpression(jsonModel, retinaName, scalar, shapeString, encodingString, sparsity);
    }
    
    /**
     * Returns a visualization of the comparison of two fingerprints. 
     * <p> The returned image contains a visualization of the left and right fingerprint and the overlay of both
     *     fingerprints.
     * </p>
     * @param scalar : scaling factor of the image to generate
     * @param shape : the shape of the plots used in the overlay image
     * @param imageEncoding :  the encoding of the image.
     * @param jsonModel : a json model for which the fingerprint's images are generated.
     * @return a byte array holding the image data.
     * @throws JsonProcessingException if it is impossible to generate the request using the input model(s).
     * @throws ApiException : if there are some server or connection issues.
     */
    public ByteArrayInputStream compare(Integer scalar, ImagePlotShape shape, ImageEncoding imageEncoding,
            String jsonModel) throws JsonProcessingException, ApiException {
        
        LOG.debug("Retrieve image for expression: model: " + jsonModel + "  scalar: " + scalar + "  shape: "
                + name(shape) + "  image encoding: " + name(imageEncoding));
        String shapeString = null;
        if (shape != null) {
            shapeString = shape.name().toLowerCase();
        }
        String encodingString = null;
        if (imageEncoding != null) {
            encodingString = imageEncoding.machineRepresentation();
        }
        return api.getOverlayImage(jsonModel, retinaName, shapeString, scalar, encodingString);
    }
    
    /**
     * Returns a List of {@link Image}s for the input models.
     * 
     * <br/> Whether to include a fingerprint in the response is defined by {@link DefaultValues#DEF_VALUE_PROVIDE_FINGERPRINT}
     * <br/> The default image scalar is {@link DefaultValues#DEF_VALUE_PLOT_SCALAR}
     *  
     * @param shape : shape of the plots used in the overlay image
     * @param jsonModel : json model for which the images are generated.
     * @param sparsity : a sparsity value which can be applied to the image
     * @return a list of images generated using the input models.
     * @throws JsonProcessingException if it is impossible to generate the request using the input model(s).
     * @throws ApiException : if there are some server or connection issues.
     */
    public List<Image> getImageBulk(ImagePlotShape shape, Double sparsity, String jsonModels)
            throws JsonProcessingException, ApiException {
        return getImageBulk(null, null, shape, sparsity, jsonModels);
    }
    
    /**
     * Returns a List of {@link Image}s for the input models.
     * <br/> The default image shape is {@link DefaultValues#DEF_VALUE_PLOT_SHAPE}
     * <br/> The default image scalar is {@link DefaultValues#DEF_VALUE_PLOT_SCALAR}
     * <br/> The default sparsity is {@link DefaultValues#DEF_VALUE_SPARSITY}
     * <br/> Whether to include a fingerprint in the response is defined by {@link DefaultValues#DEF_VALUE_PROVIDE_FINGERPRINT}
     * 
     * @param jsonModel : json model for which the images are generated.
     * @return a list of images generated using the input models.
     * @throws JsonProcessingException if it is impossible to generate the request using the input model(s).
     * @throws ApiException : if there are some server or connection issues.
     */
    public List<Image> getImageBulk(String jsonModels) throws JsonProcessingException, ApiException {
        return getImageBulk(null, null, jsonModels);
    }
    
    /**
     * Generate an image for the for the model.
     * 
     * <br/> The default image scalar is {@link DefaultValues#DEF_VALUE_PLOT_SCALAR}
     * <br/> The default image encoding is {@link DefaultValues#DEF_VALUE_IMAGE_ENCODING}
     * 
     * @param shape : the shape of the plots used in the overlay image
     * @param sparsity : the sparsity that should be applied to the image
     * @param jsonModel : a json model for which the fingerprint's image is generated.
     * @return a byte array holding the image data.
     * @throws JsonProcessingException if it is impossible to generate the request using the input model(s).
     * @throws ApiException : if there are some server or connection issues.
     */
    public ByteArrayInputStream getImage(ImagePlotShape shape, Double sparsity, String jsonModels)
            throws JsonProcessingException, ApiException {
        return getImage(null, shape, null, sparsity, jsonModels);
    }
    
    /**
     * Generate an image for the for the model.
     * 
     * <br/> The default image shape is {@link DefaultValues#DEF_VALUE_PLOT_SHAPE}
     * <br/> The default image scalar is {@link DefaultValues#DEF_VALUE_PLOT_SCALAR}
     * <br/> The default sparsity is {@link DefaultValues#DEF_VALUE_SPARSITY}
     * <br/> The default image encoding is {@link DefaultValues#DEF_VALUE_IMAGE_ENCODING}
     * 
     * @param jsonModel : a json model for which the fingerprint's image is generated.
     * @return a byte array holding the image data.
     * @throws JsonProcessingException if it is impossible to generate the request using the input model(s).
     * @throws ApiException : if there are some server or connection issues.
     */
    public ByteArrayInputStream getImage(String jsonModels) throws JsonProcessingException, ApiException {
        return getImage(null, null, jsonModels);
    }
    
    /**
     * Returns a visualization of the comparison of two fingerprints. 
     * <p> The returned image contains a visualization of the left and right fingerprint and the overlay of both
     *     fingerprints.
     * </p>
     * <br/> The default image scalar is {@link DefaultValues#DEF_VALUE_PLOT_SCALAR}
     * 
     * @param shape : the shape of the plots used in the overlay image
     * @param imageEncoding :  the encoding of the image.
     * @param  jsonModel : a json model for which the fingerprint's images are generated.
     * @return a byte array holding the image data.
     * @throws JsonProcessingException if it is impossible to generate the request using the input model(s).
     * @throws ApiException : if there are some server or connection issues.
     */
    public ByteArrayInputStream compare(ImagePlotShape shape, ImageEncoding imageEncoding, String jsonModels)
            throws JsonProcessingException, ApiException {
        return compare(null, shape, imageEncoding, jsonModels);
    }
    
    /**
     * Returns a visualization of the comparison of two fingerprints. 
     * <p> The returned image contains a visualization of the left and right fingerprint and the overlay of both
     *     fingerprints.
     * </p>
     * <br/> The default image shape is {@link DefaultValues#DEF_VALUE_PLOT_SHAPE}
     * <br/> The default image scalar is {@link DefaultValues#DEF_VALUE_PLOT_SCALAR}
     * <br/> The default image encoding is {@link DefaultValues#DEF_VALUE_IMAGE_ENCODING}\
     * 
     * @param jsonModel : a json model for which the fingerprint's images are generated.
     * @return a byte array holding the image data.
     * @throws JsonProcessingException if it is impossible to generate the request using the input model(s).
     * @throws ApiException : if there are some server or connection issues.
     */
    public ByteArrayInputStream compare(String jsonModels) throws JsonProcessingException, ApiException {
        return compare(null, null, jsonModels);
    }
    
    private String name(Enum<?> enumItem) {
        if (enumItem != null) {
            return enumItem.name();
        }
        return null;
    }
    
}
