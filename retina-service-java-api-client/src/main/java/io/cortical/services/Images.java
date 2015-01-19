/*******************************************************************************
 * Copyright (c) cortical.io GmbH. All rights reserved.
 *  
 * This software is confidential and proprietary information.
 * You shall use it only in accordance with the terms of the
 * license agreement you entered into with cortical.io GmbH.
 ******************************************************************************/
package io.cortical.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.cortical.rest.DefaultValues;
import io.cortical.rest.model.Image;
import io.cortical.rest.model.Model;
import io.cortical.services.api.client.ApiException;
import java.io.ByteArrayInputStream;
import java.util.List;


/**
 * 
 * The Image Retina API.
 */
public interface Images {
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
    List<Image> getImageBulk(Boolean includeFingerprint, Integer scalar, ImagePlotShape shape, Double sparsity,
            Model... models) throws JsonProcessingException, ApiException;
    
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
    List<Image> getImageBulk(ImagePlotShape shape, Double sparsity, Model... models) throws JsonProcessingException,
            ApiException;
    
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
    List<Image> getImageBulk(Model... models) throws JsonProcessingException, ApiException;
    
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
    ByteArrayInputStream getImage(Integer scalar, ImagePlotShape shape, ImageEncoding imageEncoding, Double sparsity,
            Model model) throws JsonProcessingException, ApiException;
    
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
    ByteArrayInputStream getImage(ImagePlotShape shape, Double sparsity, Model model) throws JsonProcessingException,
            ApiException;
    
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
    ByteArrayInputStream getImage(Model model) throws JsonProcessingException, ApiException;
    
    
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
    ByteArrayInputStream compare(Integer scalar, ImagePlotShape shape, ImageEncoding imageEncoding, Model model1,
            Model model2) throws JsonProcessingException, ApiException;
    
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
    ByteArrayInputStream compare(ImagePlotShape shape, ImageEncoding imageEncoding, Model model1, Model model2)
            throws JsonProcessingException, ApiException;
    
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
    ByteArrayInputStream compare(Model model1, Model model2) throws JsonProcessingException, ApiException;
    
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
    List<Image> getImageBulk(Boolean includeFingerprint, Integer scalar, ImagePlotShape shape, Double sparsity,
            String jsonModel) throws JsonProcessingException, ApiException;
    
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
    List<Image> getImageBulk(ImagePlotShape shape, Double sparsity, String jsonModel) throws JsonProcessingException,
            ApiException;
    
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
    List<Image> getImageBulk(String jsonModel) throws JsonProcessingException, ApiException;
    
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
    ByteArrayInputStream getImage(Integer scalar, ImagePlotShape shape, ImageEncoding imageEncoding, Double sparsity,
            String jsonModel) throws JsonProcessingException, ApiException;
    
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
    ByteArrayInputStream getImage(ImagePlotShape shape, Double sparsity, String jsonModel)
            throws JsonProcessingException, ApiException;
    
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
    ByteArrayInputStream getImage(String jsonModel) throws JsonProcessingException, ApiException;
    
    
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
    ByteArrayInputStream compare(Integer scalar, ImagePlotShape shape, ImageEncoding imageEncoding, String jsonModel)
            throws JsonProcessingException, ApiException;
    
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
    ByteArrayInputStream compare(ImagePlotShape shape, ImageEncoding imageEncoding, String jsonModel)
            throws JsonProcessingException, ApiException;
    
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
    ByteArrayInputStream compare(String jsonModel) throws JsonProcessingException, ApiException;
}
