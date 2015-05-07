/*******************************************************************************
 * Copyright (c) CEPT Systems GmbH. All rights reserved.
 *  
 * This software is confidential and proprietary information.
 * You shall use it only in accordance with the terms of the
 * license agreement you entered into with CEPT Systems GmbH.
 ******************************************************************************/
package io.cortical.services.api.client;

import static io.cortical.services.ApiTestUtils.prepareApiObjectPostMethod;
import io.cortical.rest.model.CategoryFilter;
import io.cortical.rest.model.FilterTrainingObject;
import io.cortical.rest.model.Text;
import io.cortical.services.api.client.api.ClassifyApi;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import static io.cortical.services.ApiTestUtils.NOT_NULL_API_KEY;
import static io.cortical.services.ApiTestUtils.NOT_NULL_BASE_PATH;
import static io.cortical.services.ApiTestUtils.NOT_NULL_RETINA;
import static io.cortical.services.ApiTestUtils.setApiInvoker;
import static org.mockito.MockitoAnnotations.initMocks;


/**
 * 
 * 
 */
public class TestClassifyApi {
    
    /**
    * Example {@link FilterTrainingObject}
    */
   private static final FilterTrainingObject fto = new FilterTrainingObject(){{
       getPositiveExamples().add(new Text("Shoe with a lining to help keep your feet dry and comfortable on wet terrain."));
       getPositiveExamples().add(new Text("running shoes providing protective cushioning."));
       getNegativeExamples().add(new Text("The most comfortable socks for your feet."));
       getNegativeExamples().add(new Text("6 feet USB cable basic white"));
   }};
    
    /** CategoryFilter to return **/
    private static final String POS_NEG_FILTER = "{ \"categoryName\": \"pos-neg\", \"positions\": [3,6,7,8,18,19,35,36,47,60,61,77,79,92]}";

    
    
    
    @Mock
    private ApiInvoker apiInvoker;
    private ClassifyApi classifyApi;
    private CategoryFilter cf;
    
    
    /**
     * 
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        initMocks(this);
        classifyApi = new ClassifyApi(NOT_NULL_API_KEY);
        classifyApi.setBasePath(NOT_NULL_BASE_PATH);
        setApiInvoker(apiInvoker, classifyApi);
    }
    
    /**
     * 
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
    }
    
    
    /**
     * {@link ClassifyApi#createCategoryFilter(String, FilterTrainingObject, String)} method test.
     * (positive and negative examples)
     * @throws ApiException
     */
    @Test
    public void testCreateCategoryFilterPosNeg() throws ApiException {
        prepareApiObjectPostMethod(fto, POS_NEG_FILTER, apiInvoker);
        cf = classifyApi.createCategoryFilter("pos-neg", fto, NOT_NULL_RETINA);
        Assert.assertNotNull(cf);
    }
    
    
    /**
     * {@link ClassifyApi#createCategoryFilter(String, FilterTrainingObject, String)} method test. 
     * (null body)
     * 
     * @throws ApiException : expected.
     */
    @Test(expected = ApiException.class)
    public void testCreateCategoryFilter_nullBody() throws ApiException {        
        classifyApi.createCategoryFilter("null-body", null, NOT_NULL_RETINA);
    }
    
    /**
     * {@link ClassifyApi#createCategoryFilter(String, FilterTrainingObject, String)} method test. 
     * (null filterName)
     * 
     * @throws ApiException : expected.
     */
    @Test(expected = ApiException.class)
    public void testCreateCategoryFilter_nullFilterName() throws ApiException {    
        classifyApi.createCategoryFilter(null, fto, NOT_NULL_RETINA);
    }
    
    /**
     * {@link ClassifyApi#createCategoryFilter(String, FilterTrainingObject, String)} method test. 
     * (null retinaName)
     * 
     * @throws ApiException : expected.
     */
    @Test(expected = ApiException.class)
    public void testCreateCategoryFilter_nullRetinaName() throws ApiException {        
        classifyApi.createCategoryFilter("null_retina", fto, null);
    }
    
}
