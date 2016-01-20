/*******************************************************************************
 * Copyright (c) cortical.io GmbH. All rights reserved.
 *  
 * This software is confidential and proprietary information.
 * You shall use it only in accordance with the terms of the
 * license agreement you entered into with cortical.io GmbH.
 ******************************************************************************/
package io.cortical.retina.client.core;

import static io.cortical.retina.model.TestDataHarness.createImages;
import static io.cortical.retina.client.core.ApiTestUtils.NOT_NULL_RETINA;
import io.cortical.retina.model.Image;
import io.cortical.retina.model.Term;
import io.cortical.retina.client.core.ImageEncoding;
import io.cortical.retina.client.core.ImagePlotShape;
import io.cortical.retina.client.core.Images;
import io.cortical.retina.service.ApiException;
import io.cortical.retina.service.ImageApi;
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
 * {@link Images} test class.
 */
public class TestImages {
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
    private Images images;
    
    /**
     * Initialize.
     */
    @Before
    public void init() {
        initMocks(this);
        images = new Images(api, NOT_NULL_RETINA);
    }
    /**
     * {@link Images#compare(io.cortical.retina.model.Model, io.cortical.retina.model.Model)} test method.
     * 
     * @throws JsonProcessingException : should never be thrown.
     * @throws ApiException : should never be thrown.
     */
    @Test
    public void compareModelTest() throws JsonProcessingException, ApiException {
        when(api.getOverlayImage(eq(TERM_1_TERM_2_JSON), eq(NOT_NULL_RETINA), isNull(String.class), isNull(Integer.class), isNull(String.class))).thenReturn(new ByteArrayInputStream(new byte[1]));
        images.compare(TERM_1, TERM_2);
        verify(api, times(1)).getOverlayImage(eq(TERM_1_TERM_2_JSON), eq(NOT_NULL_RETINA), isNull(String.class), isNull(Integer.class), isNull(String.class));
    }
    
    /**
     * {@link Images#compare(ImagePlotShape, ImageEncoding, io.cortical.retina.model.Model, io.cortical.retina.model.Model)} test method.
     * 
     * @throws JsonProcessingException : should never be thrown.
     * @throws ApiException : should never be thrown.
     */
    @Test
    public void compareModelTest_shapeAndEncoding() throws JsonProcessingException, ApiException {
        ImagePlotShape shape =  ImagePlotShape.CIRCLE;
        ImageEncoding encoding = ImageEncoding.BASE64_PNG;
        when(api.getOverlayImage(eq(TERM_1_TERM_2_JSON), eq(NOT_NULL_RETINA), eq(shape.name().toLowerCase()), isNull(Integer.class), eq(encoding.machineRepresentation()))).thenReturn(new ByteArrayInputStream(new byte[1]));
        images.compare(shape, encoding, TERM_1, TERM_2);
        verify(api, times(1)).getOverlayImage(eq(TERM_1_TERM_2_JSON), eq(NOT_NULL_RETINA), eq(shape.name().toLowerCase()), isNull(Integer.class), eq(encoding.machineRepresentation()));
    }
    
    /**
     * {@link Images#compare(ImagePlotShape, ImageEncoding, io.cortical.retina.model.Model, io.cortical.retina.model.Model)} test method.
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
        images.compare(scalar, shape, encoding, TERM_1, TERM_2);
        verify(api, times(1)).getOverlayImage(eq(TERM_1_TERM_2_JSON), eq(NOT_NULL_RETINA), eq(shape.name().toLowerCase()), eq(scalar), eq(encoding.machineRepresentation()));
    }
    
    /**
     * {@link Images#compare(String)} test method.
     * 
     * @throws JsonProcessingException : should never be thrown.
     * @throws ApiException : should never be thrown.
     */
    @Test
    public void compareModelTest_json() throws JsonProcessingException, ApiException {
        when(api.getOverlayImage(eq(TERM_1_TERM_2_JSON), eq(NOT_NULL_RETINA), isNull(String.class), isNull(Integer.class), isNull(String.class))).thenReturn(new ByteArrayInputStream(new byte[1]));
        images.compare(TERM_1_TERM_2_JSON);
        verify(api, times(1)).getOverlayImage(eq(TERM_1_TERM_2_JSON), eq(NOT_NULL_RETINA), isNull(String.class), isNull(Integer.class), isNull(String.class));
    }
    
    /**
     * {@link Images#compare(ImagePlotShape, ImageEncoding, String)} test method.
     * 
     * @throws JsonProcessingException : should never be thrown.
     * @throws ApiException : should never be thrown.
     */
    @Test
    public void compareModelTest_shapeAndEncoding_json() throws JsonProcessingException, ApiException {
        ImagePlotShape shape =  ImagePlotShape.CIRCLE;
        ImageEncoding encoding = ImageEncoding.BASE64_PNG;
        when(api.getOverlayImage(eq(TERM_1_TERM_2_JSON), eq(NOT_NULL_RETINA), eq(shape.name().toLowerCase()), isNull(Integer.class), eq(encoding.machineRepresentation()))).thenReturn(new ByteArrayInputStream(new byte[1]));
        images.compare(shape, encoding, TERM_1_TERM_2_JSON);
        verify(api, times(1)).getOverlayImage(eq(TERM_1_TERM_2_JSON), eq(NOT_NULL_RETINA), eq(shape.name().toLowerCase()), isNull(Integer.class), eq(encoding.machineRepresentation()));
    }
    
    /**
     * {@link Images#compare(Integer, ImagePlotShape, ImageEncoding, String)} test method.
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
        images.compare(scalar, shape, encoding, TERM_1_TERM_2_JSON);
        verify(api, times(1)).getOverlayImage(eq(TERM_1_TERM_2_JSON), eq(NOT_NULL_RETINA), eq(shape.name().toLowerCase()), eq(scalar), eq(encoding.machineRepresentation()));
    }
    
    /**
     * {@link Images#getImageBulk(io.cortical.retina.model.Model...)} test method.
     * 
     * @throws JsonProcessingException : should never be thrown.
     * @throws ApiException : should never be thrown.
     */
    @Test
    public void getImageForBulkExpressionsTest() throws ApiException, JsonProcessingException {
        int count = 2;
        List<Image> imageList = createImages(count);
        when(api.getImageForBulkExpressions(eq(TERM_1_TERM_2_JSON), isNull(Boolean.class), eq(NOT_NULL_RETINA), isNull(Integer.class), isNull(String.class), isNull(Double.class))).thenReturn(imageList);
        images.getImageBulk(TERM_1, TERM_2);
        verify(api, times(1)).getImageForBulkExpressions(eq(TERM_1_TERM_2_JSON), isNull(Boolean.class), eq(NOT_NULL_RETINA), isNull(Integer.class), isNull(String.class), isNull(Double.class));
    }
    
    /**
     * {@link Images#getImageBulk(ImagePlotShape, Double, io.cortical.retina.model.Model...)} test method.
     * 
     * @throws JsonProcessingException : should never be thrown.
     * @throws ApiException : should never be thrown.
     */
    @Test
    public void getImageForBulkExpressionsTest_shapeAndSparsity() throws ApiException, JsonProcessingException {
        ImagePlotShape shape =  ImagePlotShape.CIRCLE;
        int count = 2;
        double sparsity = 0.5;
        List<Image> imageList = createImages(count);
        when(api.getImageForBulkExpressions(eq(TERM_1_TERM_2_JSON), isNull(Boolean.class), eq(NOT_NULL_RETINA), isNull(Integer.class), eq(shape.name().toLowerCase()),  eq(sparsity))).thenReturn(imageList);
        images.getImageBulk(shape, sparsity, TERM_1, TERM_2);
        verify(api, times(1)).getImageForBulkExpressions(eq(TERM_1_TERM_2_JSON), isNull(Boolean.class), eq(NOT_NULL_RETINA), isNull(Integer.class), eq(shape.name().toLowerCase()),  eq(sparsity));
    }
    
    /**
     * {@link Images#getImageBulk(Boolean, Integer, ImagePlotShape, Double, io.cortical.retina.model.Model...)} test method.
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
        List<Image> imageList = createImages(count);
        when(api.getImageForBulkExpressions(eq(TERM_1_TERM_2_JSON), eq(includeFingerprint), eq(NOT_NULL_RETINA), eq(scalar), eq(shape.name().toLowerCase()), eq(sparsity))).thenReturn(imageList);
        images.getImageBulk(includeFingerprint, scalar, shape, sparsity, TERM_1, TERM_2);
        verify(api, times(1)).getImageForBulkExpressions(eq(TERM_1_TERM_2_JSON), eq(includeFingerprint), eq(NOT_NULL_RETINA), eq(scalar), eq(shape.name().toLowerCase()),  eq(sparsity));
    }
    
    /**
     * {@link Images#getImageBulk(String)} test method.
     * 
     * @throws JsonProcessingException : should never be thrown.
     * @throws ApiException : should never be thrown.
     */
    @Test
    public void getImageForBulkExpressionsTest_json() throws ApiException, JsonProcessingException {
        int count = 2;
        List<Image> imageList = createImages(count);
        when(api.getImageForBulkExpressions(eq(TERM_1_TERM_2_JSON), isNull(Boolean.class), eq(NOT_NULL_RETINA), isNull(Integer.class), isNull(String.class), isNull(Double.class))).thenReturn(imageList);
        images.getImageBulk(TERM_1_TERM_2_JSON);
        verify(api, times(1)).getImageForBulkExpressions(eq(TERM_1_TERM_2_JSON), isNull(Boolean.class), eq(NOT_NULL_RETINA), isNull(Integer.class), isNull(String.class), isNull(Double.class));
    }
    
    /**
     * {@link Images#getImageBulk(ImagePlotShape, Double, String)} test method.
     * 
     * @throws JsonProcessingException : should never be thrown.
     * @throws ApiException : should never be thrown.
     */
    @Test
    public void getImageForBulkExpressionsTest_shapeAndSparsity_json() throws ApiException, JsonProcessingException {
        ImagePlotShape shape =  ImagePlotShape.CIRCLE;
        int count = 2;
        double sparsity = 0.5;
        List<Image> imageList = createImages(count);
        when(api.getImageForBulkExpressions(eq(TERM_1_TERM_2_JSON), isNull(Boolean.class), eq(NOT_NULL_RETINA), isNull(Integer.class), eq(shape.name().toLowerCase()),  eq(sparsity))).thenReturn(imageList);
        images.getImageBulk(shape, sparsity, TERM_1_TERM_2_JSON);
        verify(api, times(1)).getImageForBulkExpressions(eq(TERM_1_TERM_2_JSON), isNull(Boolean.class), eq(NOT_NULL_RETINA), isNull(Integer.class), eq(shape.name().toLowerCase()),  eq(sparsity));
    }
    
    /**
     * {@link Images#getImageBulk(Boolean, Integer, ImagePlotShape, Double, String)} test method.
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
        List<Image> imageList = images.getImageBulk(includeFingerprint, scalar, shape, sparsity, TERM_1_TERM_2_JSON);
        Assert.assertEquals(count, imageList.size());
        verify(api, times(1)).getImageForBulkExpressions(eq(TERM_1_TERM_2_JSON), eq(includeFingerprint), eq(NOT_NULL_RETINA), eq(scalar), eq(shape.name().toLowerCase()),  eq(sparsity));
    }
    
    
    /**
     * {@link Images#getImage(io.cortical.retina.model.Model)} test method.
     * 
     * @throws JsonProcessingException : should never be thrown.
     * @throws ApiException : should never be thrown.
     */
    @Test
    public void getImageForExpressionTest() throws ApiException, JsonProcessingException {
        when(api.getImageForExpression(eq(TERM_1_JSON), eq(NOT_NULL_RETINA), isNull(Integer.class), isNull(String.class), isNull(String.class), isNull(Double.class))).thenReturn(new ByteArrayInputStream(new byte[1]));
        images.getImage(TERM_1);
        verify(api, times(1)).getImageForExpression(eq(TERM_1_JSON), eq(NOT_NULL_RETINA), isNull(Integer.class), isNull(String.class), isNull(String.class), isNull(Double.class));
    }
    
    /**
     * {@link Images#getImage(ImagePlotShape, Double, io.cortical.retina.model.Model)} test method.
     * 
     * @throws JsonProcessingException : should never be thrown.
     * @throws ApiException : should never be thrown.
     */
    @Test
    public void getImageForExpressionTest_shapeSparsity() throws ApiException, JsonProcessingException {
        ImagePlotShape shape =  ImagePlotShape.CIRCLE;
        double sparsity = 0.5;
        when(api.getImageForExpression(eq(TERM_1_JSON), eq(NOT_NULL_RETINA), isNull(Integer.class), eq(shape.name().toLowerCase()), isNull(String.class), eq(sparsity))).thenReturn(new ByteArrayInputStream(new byte[1]));
        images.getImage(shape, sparsity, TERM_1);
        verify(api, times(1)).getImageForExpression(eq(TERM_1_JSON), eq(NOT_NULL_RETINA), isNull(Integer.class), eq(shape.name().toLowerCase()), isNull(String.class), eq(sparsity));
    }
    
    /**
     * {@link Images#getImage(Integer, ImagePlotShape, ImageEncoding, Double, io.cortical.retina.model.Model)} test method.
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
        images.getImage(scalar, shape, encoding, sparsity, TERM_1);
        verify(api, times(1)).getImageForExpression(eq(TERM_1_JSON), eq(NOT_NULL_RETINA), eq(scalar), eq(shape.name().toLowerCase()), eq(encoding.machineRepresentation()), eq(sparsity));
    }
    
    /**
     * {@link Images#getImage(String)} test method.
     * 
     * @throws JsonProcessingException : should never be thrown.
     * @throws ApiException : should never be thrown.
     */
    @Test
    public void getImageForExpressionTest_json() throws ApiException, JsonProcessingException {
        when(api.getImageForExpression(eq(TERM_1_JSON), eq(NOT_NULL_RETINA), isNull(Integer.class), isNull(String.class), isNull(String.class), isNull(Double.class))).thenReturn(new ByteArrayInputStream(new byte[1]));
        images.getImage(TERM_1_JSON);
        verify(api, times(1)).getImageForExpression(eq(TERM_1_JSON), eq(NOT_NULL_RETINA), isNull(Integer.class), isNull(String.class), isNull(String.class), isNull(Double.class));
    }
    
    /**
     * {@link Images#getImage(ImagePlotShape, Double, String)} test method.
     * 
     * @throws JsonProcessingException : should never be thrown.
     * @throws ApiException : should never be thrown.
     */
    @Test
    public void getImageForExpressionTest_shapeSparsity_json() throws ApiException, JsonProcessingException {
        ImagePlotShape shape =  ImagePlotShape.CIRCLE;
        double sparsity = 0.5;
        when(api.getImageForExpression(eq(TERM_1_JSON), eq(NOT_NULL_RETINA), isNull(Integer.class), eq(shape.name().toLowerCase()), isNull(String.class), eq(sparsity))).thenReturn(new ByteArrayInputStream(new byte[1]));
        images.getImage(shape, sparsity, TERM_1_JSON);
        verify(api, times(1)).getImageForExpression(eq(TERM_1_JSON), eq(NOT_NULL_RETINA), isNull(Integer.class), eq(shape.name().toLowerCase()), isNull(String.class), eq(sparsity));
    }
    
    /**
     * {@link Images#getImage(Integer, ImagePlotShape, ImageEncoding, Double, String)} test method.
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
        images.getImage(scalar, shape, encoding, sparsity, TERM_1_JSON);
        verify(api, times(1)).getImageForExpression(eq(TERM_1_JSON), eq(NOT_NULL_RETINA), eq(scalar), eq(shape.name().toLowerCase()), eq(encoding.machineRepresentation()), eq(sparsity));
    }
}
