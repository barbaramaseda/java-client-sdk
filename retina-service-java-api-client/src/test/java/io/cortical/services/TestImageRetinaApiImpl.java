/*******************************************************************************
 * Copyright (c) cortical.io GmbH. All rights reserved.
 *  
 * This software is confidential and proprietary information.
 * You shall use it only in accordance with the terms of the
 * license agreement you entered into with cortical.io GmbH.
 ******************************************************************************/
package io.cortical.services;

import static io.cortical.rest.model.TestDataMother.createImages;
import static io.cortical.services.ApiTestUtils.NOT_NULL_RETINA;
import io.cortical.rest.model.Image;
import io.cortical.rest.model.Term;
import io.cortical.services.ImageEncoding;
import io.cortical.services.ImagePlotShape;
import io.cortical.services.ImageRetinaApiImpl;
import io.cortical.services.api.client.ApiException;
import io.cortical.services.api.client.api.ImageApi;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.ByteArrayInputStream;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;


/**
 * 
 * {@link ImageRetinaApiImpl} test class.
 */
public class TestImageRetinaApiImpl {
    /**
     * 
     */
    private static final Term TERM_1 = new Term("term_1");
    private static final Term TERM_2 = new Term("term_2");
    private static final String TERM_1_TERM_2_JSON;
    private static final String TERM_1_JSON;
    static {
        try {
            TERM_1_TERM_2_JSON = Term.toJson(TERM_1, TERM_2);
            TERM_1_JSON = TERM_1.toJson();
        }
        catch (JsonProcessingException e) {
            throw new IllegalStateException("Impossible to initialize test input data.");
        }
    }
    
    /**
     * 
     */
    @Mock
    private ImageApi api;
    private ImageRetinaApiImpl imageRetinaApiImpl;
    
    /**
     * Initialize.
     */
    @Before
    public void init() {
        initMocks(this);
        imageRetinaApiImpl = new ImageRetinaApiImpl(api, NOT_NULL_RETINA);
    }
    /**
     * {@link ImageRetinaApiImpl#compare(io.cortical.rest.model.Model, io.cortical.rest.model.Model)} test method.
     * 
     * @throws JsonProcessingException : should never be thrown.
     * @throws ApiException : should never be thrown.
     */
    @Test
    public void compareModelTest() throws JsonProcessingException, ApiException {
        when(api.getOverlayImage(eq(TERM_1_TERM_2_JSON), eq(NOT_NULL_RETINA), isNull(String.class), isNull(Integer.class), isNull(String.class))).thenReturn(new ByteArrayInputStream(new byte[1]));
        imageRetinaApiImpl.compare(TERM_1, TERM_2);
        verify(api, times(1)).getOverlayImage(eq(TERM_1_TERM_2_JSON), eq(NOT_NULL_RETINA), isNull(String.class), isNull(Integer.class), isNull(String.class));
    }
    
    /**
     * {@link ImageRetinaApiImpl#compare(ImagePlotShape, ImageEncoding, io.cortical.rest.model.Model, io.cortical.rest.model.Model)} test method.
     * 
     * @throws JsonProcessingException : should never be thrown.
     * @throws ApiException : should never be thrown.
     */
    @Test
    public void compareModelTest_shapeAndEncoding() throws JsonProcessingException, ApiException {
        ImagePlotShape shape =  ImagePlotShape.CIRCLE;
        ImageEncoding encoding = ImageEncoding.BASE64_PNG;
        when(api.getOverlayImage(eq(TERM_1_TERM_2_JSON), eq(NOT_NULL_RETINA), eq(shape.name().toLowerCase()), isNull(Integer.class), eq(encoding.machineRepresentation()))).thenReturn(new ByteArrayInputStream(new byte[1]));
        imageRetinaApiImpl.compare(shape, encoding, TERM_1, TERM_2);
        verify(api, times(1)).getOverlayImage(eq(TERM_1_TERM_2_JSON), eq(NOT_NULL_RETINA), eq(shape.name().toLowerCase()), isNull(Integer.class), eq(encoding.machineRepresentation()));
    }
    
    /**
     * {@link ImageRetinaApiImpl#compare(ImagePlotShape, ImageEncoding, io.cortical.rest.model.Model, io.cortical.rest.model.Model)} test method.
     * 
     * @throws JsonProcessingException : should never be thrown.
     * @throws ApiException : should never be thrown.
     */
    @Test
    public void compareModelTest_shapeAndEncodingScalar() throws JsonProcessingException, ApiException {
        ImagePlotShape shape =  ImagePlotShape.CIRCLE;
        int scalar = 2;
        ImageEncoding encoding = ImageEncoding.BASE64_PNG;
        when(api.getOverlayImage(eq(TERM_1_TERM_2_JSON), eq(NOT_NULL_RETINA), eq(shape.name().toLowerCase()), eq(scalar), eq(encoding.machineRepresentation()))).thenReturn(new ByteArrayInputStream(new byte[1]));
        imageRetinaApiImpl.compare(scalar, shape, encoding, TERM_1, TERM_2);
        verify(api, times(1)).getOverlayImage(eq(TERM_1_TERM_2_JSON), eq(NOT_NULL_RETINA), eq(shape.name().toLowerCase()), eq(scalar), eq(encoding.machineRepresentation()));
    }
    
    /**
     * {@link ImageRetinaApiImpl#compare(String)} test method.
     * 
     * @throws JsonProcessingException : should never be thrown.
     * @throws ApiException : should never be thrown.
     */
    @Test
    public void compareModelTest_json() throws JsonProcessingException, ApiException {
        when(api.getOverlayImage(eq(TERM_1_TERM_2_JSON), eq(NOT_NULL_RETINA), isNull(String.class), isNull(Integer.class), isNull(String.class))).thenReturn(new ByteArrayInputStream(new byte[1]));
        imageRetinaApiImpl.compare(TERM_1_TERM_2_JSON);
        verify(api, times(1)).getOverlayImage(eq(TERM_1_TERM_2_JSON), eq(NOT_NULL_RETINA), isNull(String.class), isNull(Integer.class), isNull(String.class));
    }
    
    /**
     * {@link ImageRetinaApiImpl#compare(ImagePlotShape, ImageEncoding, String)} test method.
     * 
     * @throws JsonProcessingException : should never be thrown.
     * @throws ApiException : should never be thrown.
     */
    @Test
    public void compareModelTest_shapeAndEncoding_json() throws JsonProcessingException, ApiException {
        ImagePlotShape shape =  ImagePlotShape.CIRCLE;
        ImageEncoding encoding = ImageEncoding.BASE64_PNG;
        when(api.getOverlayImage(eq(TERM_1_TERM_2_JSON), eq(NOT_NULL_RETINA), eq(shape.name().toLowerCase()), isNull(Integer.class), eq(encoding.machineRepresentation()))).thenReturn(new ByteArrayInputStream(new byte[1]));
        imageRetinaApiImpl.compare(shape, encoding, TERM_1_TERM_2_JSON);
        verify(api, times(1)).getOverlayImage(eq(TERM_1_TERM_2_JSON), eq(NOT_NULL_RETINA), eq(shape.name().toLowerCase()), isNull(Integer.class), eq(encoding.machineRepresentation()));
    }
    
    /**
     * {@link ImageRetinaApiImpl#compare(Integer, ImagePlotShape, ImageEncoding, String)} test method.
     * 
     * @throws JsonProcessingException : should never be thrown.
     * @throws ApiException : should never be thrown.
     */
    @Test
    public void compareModelTest_shapeAndEncodingScalar_json() throws JsonProcessingException, ApiException {
        ImagePlotShape shape =  ImagePlotShape.CIRCLE;
        int scalar = 2;
        ImageEncoding encoding = ImageEncoding.BASE64_PNG;
        when(api.getOverlayImage(eq(TERM_1_TERM_2_JSON), eq(NOT_NULL_RETINA), eq(shape.name().toLowerCase()), eq(scalar), eq(encoding.machineRepresentation()))).thenReturn(new ByteArrayInputStream(new byte[1]));
        imageRetinaApiImpl.compare(scalar, shape, encoding, TERM_1_TERM_2_JSON);
        verify(api, times(1)).getOverlayImage(eq(TERM_1_TERM_2_JSON), eq(NOT_NULL_RETINA), eq(shape.name().toLowerCase()), eq(scalar), eq(encoding.machineRepresentation()));
    }
    
    /**
     * {@link ImageRetinaApiImpl#getImageBulk(io.cortical.rest.model.Model...)} test method.
     * 
     * @throws JsonProcessingException : should never be thrown.
     * @throws ApiException : should never be thrown.
     */
    @Test
    public void getImageForBulkExpressionsTest() throws ApiException, JsonProcessingException {
        int count = 2;
        List<Image> images = createImages(count);
        when(api.getImageForBulkExpressions(eq(TERM_1_TERM_2_JSON), isNull(Boolean.class), eq(NOT_NULL_RETINA), isNull(Integer.class), isNull(String.class), isNull(Double.class))).thenReturn(images);
        imageRetinaApiImpl.getImageBulk(TERM_1, TERM_2);
        verify(api, times(1)).getImageForBulkExpressions(eq(TERM_1_TERM_2_JSON), isNull(Boolean.class), eq(NOT_NULL_RETINA), isNull(Integer.class), isNull(String.class), isNull(Double.class));
    }
    
    /**
     * {@link ImageRetinaApiImpl#getImageBulk(ImagePlotShape, Double, io.cortical.rest.model.Model...)} test method.
     * 
     * @throws JsonProcessingException : should never be thrown.
     * @throws ApiException : should never be thrown.
     */
    @Test
    public void getImageForBulkExpressionsTest_shapeAndSparsity() throws ApiException, JsonProcessingException {
        ImagePlotShape shape =  ImagePlotShape.CIRCLE;
        int count = 2;
        double sparsity = 0.5;
        List<Image> images = createImages(count);
        when(api.getImageForBulkExpressions(eq(TERM_1_TERM_2_JSON), isNull(Boolean.class), eq(NOT_NULL_RETINA), isNull(Integer.class), eq(shape.name().toLowerCase()),  eq(sparsity))).thenReturn(images);
        imageRetinaApiImpl.getImageBulk(shape, sparsity, TERM_1, TERM_2);
        verify(api, times(1)).getImageForBulkExpressions(eq(TERM_1_TERM_2_JSON), isNull(Boolean.class), eq(NOT_NULL_RETINA), isNull(Integer.class), eq(shape.name().toLowerCase()),  eq(sparsity));
    }
    
    /**
     * {@link ImageRetinaApiImpl#getImageBulk(Boolean, Integer, ImagePlotShape, Double, io.cortical.rest.model.Model...)} test method.
     * 
     * @throws JsonProcessingException : should never be thrown.
     * @throws ApiException : should never be thrown.
     */
    @Test
    public void getImageForBulkExpressionsTest_shapeAndSparsityFingerprintScalar() throws ApiException, JsonProcessingException {
        ImagePlotShape shape =  ImagePlotShape.CIRCLE;
        boolean includeFingerprint = false;
        int scalar = 2;
        int count = 2;
        double sparsity = 0.5;
        List<Image> images = createImages(count);
        when(api.getImageForBulkExpressions(eq(TERM_1_TERM_2_JSON), eq(includeFingerprint), eq(NOT_NULL_RETINA), eq(scalar), eq(shape.name().toLowerCase()), eq(sparsity))).thenReturn(images);
        imageRetinaApiImpl.getImageBulk(includeFingerprint, scalar, shape, sparsity, TERM_1, TERM_2);
        verify(api, times(1)).getImageForBulkExpressions(eq(TERM_1_TERM_2_JSON), eq(includeFingerprint), eq(NOT_NULL_RETINA), eq(scalar), eq(shape.name().toLowerCase()),  eq(sparsity));
    }
    
    /**
     * {@link ImageRetinaApiImpl#getImageBulk(String)} test method.
     * 
     * @throws JsonProcessingException : should never be thrown.
     * @throws ApiException : should never be thrown.
     */
    @Test
    public void getImageForBulkExpressionsTest_json() throws ApiException, JsonProcessingException {
        int count = 2;
        List<Image> images = createImages(count);
        when(api.getImageForBulkExpressions(eq(TERM_1_TERM_2_JSON), isNull(Boolean.class), eq(NOT_NULL_RETINA), isNull(Integer.class), isNull(String.class), isNull(Double.class))).thenReturn(images);
        imageRetinaApiImpl.getImageBulk(TERM_1_TERM_2_JSON);
        verify(api, times(1)).getImageForBulkExpressions(eq(TERM_1_TERM_2_JSON), isNull(Boolean.class), eq(NOT_NULL_RETINA), isNull(Integer.class), isNull(String.class), isNull(Double.class));
    }
    
    /**
     * {@link ImageRetinaApiImpl#getImageBulk(ImagePlotShape, Double, String)} test method.
     * 
     * @throws JsonProcessingException : should never be thrown.
     * @throws ApiException : should never be thrown.
     */
    @Test
    public void getImageForBulkExpressionsTest_shapeAndSparsity_json() throws ApiException, JsonProcessingException {
        ImagePlotShape shape =  ImagePlotShape.CIRCLE;
        int count = 2;
        double sparsity = 0.5;
        List<Image> images = createImages(count);
        when(api.getImageForBulkExpressions(eq(TERM_1_TERM_2_JSON), isNull(Boolean.class), eq(NOT_NULL_RETINA), isNull(Integer.class), eq(shape.name().toLowerCase()),  eq(sparsity))).thenReturn(images);
        imageRetinaApiImpl.getImageBulk(shape, sparsity, TERM_1_TERM_2_JSON);
        verify(api, times(1)).getImageForBulkExpressions(eq(TERM_1_TERM_2_JSON), isNull(Boolean.class), eq(NOT_NULL_RETINA), isNull(Integer.class), eq(shape.name().toLowerCase()),  eq(sparsity));
    }
    
    /**
     * {@link ImageRetinaApiImpl#getImageBulk(Boolean, Integer, ImagePlotShape, Double, String)} test method.
     * 
     * @throws JsonProcessingException : should never be thrown.
     * @throws ApiException : should never be thrown.
     */
    @Test
    public void getImageForBulkExpressionsTest_shapeAndSparsityFingerprintScalar_json() throws ApiException, JsonProcessingException {
        ImagePlotShape shape =  ImagePlotShape.CIRCLE;
        boolean includeFingerprint = false;
        int scalar = 2;
        int count = 2;
        double sparsity = 0.5;
        when(api.getImageForBulkExpressions(eq(TERM_1_TERM_2_JSON), eq(includeFingerprint), eq(NOT_NULL_RETINA), eq(scalar), eq(shape.name().toLowerCase()), eq(sparsity))).thenReturn(createImages(count));
        List<Image> images = imageRetinaApiImpl.getImageBulk(includeFingerprint, scalar, shape, sparsity, TERM_1_TERM_2_JSON);
        Assert.assertEquals(count, images.size());
        verify(api, times(1)).getImageForBulkExpressions(eq(TERM_1_TERM_2_JSON), eq(includeFingerprint), eq(NOT_NULL_RETINA), eq(scalar), eq(shape.name().toLowerCase()),  eq(sparsity));
    }
    
    
    /**
     * {@link ImageRetinaApiImpl#getImage(io.cortical.rest.model.Model)} test method.
     * 
     * @throws JsonProcessingException : should never be thrown.
     * @throws ApiException : should never be thrown.
     */
    @Test
    public void getImageForExpressionTest() throws ApiException, JsonProcessingException {
        when(api.getImageForExpression(eq(TERM_1_JSON), eq(NOT_NULL_RETINA), isNull(Integer.class), isNull(String.class), isNull(String.class), isNull(Double.class))).thenReturn(new ByteArrayInputStream(new byte[1]));
        imageRetinaApiImpl.getImage(TERM_1);
        verify(api, times(1)).getImageForExpression(eq(TERM_1_JSON), eq(NOT_NULL_RETINA), isNull(Integer.class), isNull(String.class), isNull(String.class), isNull(Double.class));
    }
    
    /**
     * {@link ImageRetinaApiImpl#getImage(ImagePlotShape, Double, io.cortical.rest.model.Model)} test method.
     * 
     * @throws JsonProcessingException : should never be thrown.
     * @throws ApiException : should never be thrown.
     */
    @Test
    public void getImageForExpressionTest_shapeSparsity() throws ApiException, JsonProcessingException {
        ImagePlotShape shape =  ImagePlotShape.CIRCLE;
        double sparsity = 0.5;
        when(api.getImageForExpression(eq(TERM_1_JSON), eq(NOT_NULL_RETINA), isNull(Integer.class), eq(shape.name().toLowerCase()), isNull(String.class), eq(sparsity))).thenReturn(new ByteArrayInputStream(new byte[1]));
        imageRetinaApiImpl.getImage(shape, sparsity, TERM_1);
        verify(api, times(1)).getImageForExpression(eq(TERM_1_JSON), eq(NOT_NULL_RETINA), isNull(Integer.class), eq(shape.name().toLowerCase()), isNull(String.class), eq(sparsity));
    }
    
    /**
     * {@link ImageRetinaApiImpl#getImage(Integer, ImagePlotShape, ImageEncoding, Double, io.cortical.rest.model.Model)} test method.
     * 
     * @throws JsonProcessingException : should never be thrown.
     * @throws ApiException : should never be thrown.
     */
    @Test
    public void getImageForExpressionTest_shapeSparsityScalarEncoding() throws ApiException, JsonProcessingException {
        ImagePlotShape shape =  ImagePlotShape.CIRCLE;
        int scalar = 2;
        double sparsity = 0.5;
        ImageEncoding encoding = ImageEncoding.BASE64_PNG;
        when(api.getImageForExpression(eq(TERM_1_JSON), eq(NOT_NULL_RETINA), eq(scalar), eq(shape.name().toLowerCase()), eq(encoding.machineRepresentation()), eq(sparsity))).thenReturn(new ByteArrayInputStream(new byte[1]));
        imageRetinaApiImpl.getImage(scalar, shape, encoding, sparsity, TERM_1);
        verify(api, times(1)).getImageForExpression(eq(TERM_1_JSON), eq(NOT_NULL_RETINA), eq(scalar), eq(shape.name().toLowerCase()), eq(encoding.machineRepresentation()), eq(sparsity));
    }
    
    /**
     * {@link ImageRetinaApiImpl#getImage(String)} test method.
     * 
     * @throws JsonProcessingException : should never be thrown.
     * @throws ApiException : should never be thrown.
     */
    @Test
    public void getImageForExpressionTest_json() throws ApiException, JsonProcessingException {
        when(api.getImageForExpression(eq(TERM_1_JSON), eq(NOT_NULL_RETINA), isNull(Integer.class), isNull(String.class), isNull(String.class), isNull(Double.class))).thenReturn(new ByteArrayInputStream(new byte[1]));
        imageRetinaApiImpl.getImage(TERM_1_JSON);
        verify(api, times(1)).getImageForExpression(eq(TERM_1_JSON), eq(NOT_NULL_RETINA), isNull(Integer.class), isNull(String.class), isNull(String.class), isNull(Double.class));
    }
    
    /**
     * {@link ImageRetinaApiImpl#getImage(ImagePlotShape, Double, String)} test method.
     * 
     * @throws JsonProcessingException : should never be thrown.
     * @throws ApiException : should never be thrown.
     */
    @Test
    public void getImageForExpressionTest_shapeSparsity_json() throws ApiException, JsonProcessingException {
        ImagePlotShape shape =  ImagePlotShape.CIRCLE;
        double sparsity = 0.5;
        when(api.getImageForExpression(eq(TERM_1_JSON), eq(NOT_NULL_RETINA), isNull(Integer.class), eq(shape.name().toLowerCase()), isNull(String.class), eq(sparsity))).thenReturn(new ByteArrayInputStream(new byte[1]));
        imageRetinaApiImpl.getImage(shape, sparsity, TERM_1_JSON);
        verify(api, times(1)).getImageForExpression(eq(TERM_1_JSON), eq(NOT_NULL_RETINA), isNull(Integer.class), eq(shape.name().toLowerCase()), isNull(String.class), eq(sparsity));
    }
    
    /**
     * {@link ImageRetinaApiImpl#getImage(Integer, ImagePlotShape, ImageEncoding, Double, String)} test method.
     * 
     * @throws JsonProcessingException : should never be thrown.
     * @throws ApiException : should never be thrown.
     */
    @Test
    public void getImageForExpressionTest_shapeSparsityScalarEncoding_json() throws ApiException, JsonProcessingException {
        ImagePlotShape shape =  ImagePlotShape.CIRCLE;
        int scalar = 2;
        double sparsity = 0.5;
        ImageEncoding encoding = ImageEncoding.BASE64_PNG;
        when(api.getImageForExpression(eq(TERM_1_JSON), eq(NOT_NULL_RETINA), eq(scalar), eq(shape.name().toLowerCase()), eq(encoding.machineRepresentation()), eq(sparsity))).thenReturn(new ByteArrayInputStream(new byte[1]));
        imageRetinaApiImpl.getImage(scalar, shape, encoding, sparsity, TERM_1_JSON);
        verify(api, times(1)).getImageForExpression(eq(TERM_1_JSON), eq(NOT_NULL_RETINA), eq(scalar), eq(shape.name().toLowerCase()), eq(encoding.machineRepresentation()), eq(sparsity));
    }
}
