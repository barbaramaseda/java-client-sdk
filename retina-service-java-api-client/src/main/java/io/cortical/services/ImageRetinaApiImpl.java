/*******************************************************************************
 * Copyright (c) cortical.io GmbH. All rights reserved.
 *  
 * This software is confidential and proprietary information.
 * You shall use it only in accordance with the terms of the
 * license agreement you entered into with cortical.io GmbH.
 ******************************************************************************/
package io.cortical.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.cortical.rest.model.Image;
import io.cortical.rest.model.Model;
import io.cortical.services.api.client.ApiException;
import io.cortical.services.api.client.api.ImageApi;
import java.io.ByteArrayInputStream;
import java.util.List;
import org.apache.commons.logging.Log;
import static io.cortical.rest.RestServiceConstants.NULL_API_KEY_MSG;
import static io.cortical.rest.RestServiceConstants.NULL_BASE_PATH_MSG;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.logging.LogFactory.getLog;



/**
 * The Image Retina API implementation.
 * 
 */
class ImageRetinaApiImpl extends BaseRetinaApi implements Images {
    /**
     * 
     */
    private static final Log LOG = getLog(ImageRetinaApiImpl.class);
    /**
     * 
     */
    private final ImageApi api;
    
    ImageRetinaApiImpl(String apiKey, String basePath, String retinaName) {
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
    
    ImageRetinaApiImpl(ImageApi api, String retinaName) {
        super(retinaName);
        this.api = api;
    }
    
    /** {@inheritDoc} 
     * @throws ApiException 
     * @throws JsonProcessingException */
    @Override
    public List<Image> getImageBulk(Boolean includeFingerprint, Integer scalar, ImagePlotShape shape, Double sparsity,
            Model... models) throws JsonProcessingException, ApiException {
        validateRequiredModels(models);
        return getImageBulk(includeFingerprint, scalar, shape, sparsity, toJson(models));
    }
    
    /** {@inheritDoc} */
    @Override
    public ByteArrayInputStream getImage(Integer scalar, ImagePlotShape shape, ImageEncoding imageEncoding,
            Double sparsity, Model model) throws JsonProcessingException, ApiException {
        validateRequiredModels(model);
        return getImage(scalar, shape, imageEncoding, sparsity, model.toJson());
    }
    
    /** {@inheritDoc} */
    @Override
    public ByteArrayInputStream compare(Integer scalar, ImagePlotShape shape, ImageEncoding imageEncoding,
            Model model1, Model model2) throws JsonProcessingException, ApiException {
        Model[] models = { model1, model2 };
        validateRequiredModels(models);
        return compare(scalar, shape, imageEncoding, toJson(models));
    }
    
    /** {@inheritDoc} */
    @Override
    public List<Image> getImageBulk(ImagePlotShape shape, Double sparsity, Model... models)
            throws JsonProcessingException, ApiException {
        return getImageBulk(null, null, shape, sparsity, models);
    }
    
    /** {@inheritDoc} */
    @Override
    public List<Image> getImageBulk(Model... models) throws JsonProcessingException, ApiException {
        return getImageBulk(null, null, models);
    }
    
    /** {@inheritDoc} */
    @Override
    public ByteArrayInputStream getImage(ImagePlotShape shape, Double sparsity, Model model)
            throws JsonProcessingException, ApiException {
        return getImage(null, shape, null, sparsity, model);
    }
    
    /** {@inheritDoc} */
    @Override
    public ByteArrayInputStream getImage(Model model) throws JsonProcessingException, ApiException {
        return getImage(null, null, model);
    }
    
    /** {@inheritDoc} */
    @Override
    public ByteArrayInputStream compare(ImagePlotShape shape, ImageEncoding imageEncoding, Model model1, Model model2)
            throws JsonProcessingException, ApiException {
        return compare(null, shape, imageEncoding, model1, model2);
    }
    
    /** {@inheritDoc} */
    @Override
    public ByteArrayInputStream compare(Model model1, Model model2) throws JsonProcessingException, ApiException {
        return compare(null, null, model1, model2);
    }
    
    /** {@inheritDoc} 
     * @throws ApiException 
     * @throws JsonProcessingException */
    @Override
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
    
    /** {@inheritDoc} */
    @Override
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
    
    /** {@inheritDoc} */
    @Override
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
    
    /** {@inheritDoc} */
    @Override
    public List<Image> getImageBulk(ImagePlotShape shape, Double sparsity, String jsonModels)
            throws JsonProcessingException, ApiException {
        return getImageBulk(null, null, shape, sparsity, jsonModels);
    }
    
    /** {@inheritDoc} */
    @Override
    public List<Image> getImageBulk(String jsonModels) throws JsonProcessingException, ApiException {
        return getImageBulk(null, null, jsonModels);
    }
    
    /** {@inheritDoc} */
    @Override
    public ByteArrayInputStream getImage(ImagePlotShape shape, Double sparsity, String jsonModels)
            throws JsonProcessingException, ApiException {
        return getImage(null, shape, null, sparsity, jsonModels);
    }
    
    /** {@inheritDoc} */
    @Override
    public ByteArrayInputStream getImage(String jsonModels) throws JsonProcessingException, ApiException {
        return getImage(null, null, jsonModels);
    }
    
    /** {@inheritDoc} */
    @Override
    public ByteArrayInputStream compare(ImagePlotShape shape, ImageEncoding imageEncoding, String jsonModels)
            throws JsonProcessingException, ApiException {
        return compare(null, shape, imageEncoding, jsonModels);
    }
    
    /** {@inheritDoc} */
    @Override
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
