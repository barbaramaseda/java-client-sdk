/*******************************************************************************
 * Copyright (c) cortical.io GmbH. All rights reserved.
 *  
 * This software is confidential and proprietary information.
 * You shall use it only in accordance with the terms of the
 * license agreement you entered into with cortical.io GmbH.
 ******************************************************************************/
package io.cortical.retina.core;

import static io.cortical.retina.core.ApiTestUtils.NOT_NULL_RETINA;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import io.cortical.retina.model.Image;
import io.cortical.retina.model.Term;
import io.cortical.retina.service.ApiException;
import io.cortical.retina.service.ImageApi;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import com.fasterxml.jackson.core.JsonProcessingException;


/**
 * 
 * {@link Images} test class.
 */
public class ImagesTest {
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
     * {@link Images#getImage(io.cortical.retina.model.Model, int, ImagePlotShape, ImageEncoding, double)}
     * 
     * @throws ApiException : should never be thrown.
     * @throws IOException 
     */
    @Test
    public void testGetImage() throws ApiException, IOException {
        ImagePlotShape shape =  ImagePlotShape.CIRCLE;
        ImageEncoding encoding = ImageEncoding.BASE64_PNG;
        double sparsity = 0.02;
        
        when(api.getImageForExpression(eq(TERM_1_JSON), eq(NOT_NULL_RETINA), eq(1), eq(shape.name().toLowerCase()),
            eq(encoding.machineRepresentation()), eq(sparsity))).thenReturn(new ByteArrayInputStream(new byte[] { "i".getBytes()[0] }));
        
        ByteArrayInputStream stream = images.getImage(TERM_1, 1, shape, encoding, sparsity);
        assertNotNull(stream);
        assertEquals(105, stream.read());
        stream.close();
        verify(api, times(1)).getImageForExpression(eq(TERM_1_JSON), eq(NOT_NULL_RETINA), eq(1), eq(shape.name().toLowerCase()),
            eq(encoding.machineRepresentation()), eq(sparsity));
    }
    
    /**
     * {@link Images#getImages(List, Boolean, Integer, ImagePlotShape, Double)}
     * 
     * @throws JsonProcessingException : should never be thrown.
     * @throws ApiException : should never be thrown.
     */
    @Test
    public void testGetImages() throws ApiException, JsonProcessingException {
        ImagePlotShape shape = ImagePlotShape.CIRCLE;
        double sparsity = 0.02;
        List<Term> terms = Arrays.asList(TERM_1, TERM_2);
        List<Image> expected = Arrays.asList(new Image("i".getBytes(), null), new Image("i".getBytes(), null));
        
        when(api.getImageForBulkExpressions(eq(TERM_1_TERM_2_JSON), eq(false), eq(NOT_NULL_RETINA), eq(1), eq(shape.name().toLowerCase()),
            eq(sparsity))).thenReturn(expected);
        List<Image> retImages = images.getImages(terms, false, 1, shape, sparsity);
        assertNotNull(retImages);
        assertEquals(2, retImages.size());
        verify(api, times(1)).getImageForBulkExpressions(eq(TERM_1_TERM_2_JSON), eq(false), eq(NOT_NULL_RETINA), eq(1), eq(shape.name().toLowerCase()),
            eq(sparsity));
    }
    
    /**
     * {@link Images#compareImage(List, Integer, ImagePlotShape, ImageEncoding)}
     * 
     * @throws ApiException : should never be thrown.
     * @throws IOException 
     */
    @Test
    public void compareModelTest() throws ApiException, IOException {
        ImagePlotShape shape =  ImagePlotShape.CIRCLE;
        ImageEncoding encoding = ImageEncoding.BASE64_PNG;
        List<Term> terms = Arrays.asList(TERM_1, TERM_2);
        
        when(api.getOverlayImage(eq(TERM_1_TERM_2_JSON), eq(NOT_NULL_RETINA), eq(shape.name().toLowerCase()), eq(1),
            eq(encoding.machineRepresentation()))).thenReturn(
                new ByteArrayInputStream(new byte[] { "i".getBytes()[0] }));
        ByteArrayInputStream stream = images.compareImage(terms, 1, shape, encoding);
        assertNotNull(stream);
        assertEquals(105, stream.read());
        stream.close();
        verify(api, times(1)).getOverlayImage(eq(TERM_1_TERM_2_JSON), eq(NOT_NULL_RETINA), 
            eq(shape.name().toLowerCase()), eq(1), eq(encoding.machineRepresentation()));
    }
}
