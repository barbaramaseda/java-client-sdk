/*******************************************************************************
 * Copyright (c) cortical.io GmbH. All rights reserved.
 *  
 * This software is confidential and proprietary information.
 * You shall use it only in accordance with the terms of the
 * license agreement you entered into with cortical.io GmbH.
 ******************************************************************************/
package io.cortical.retina.model;

import io.cortical.retina.model.Fingerprint;
import io.cortical.retina.model.Model;
import io.cortical.retina.model.Term;
import io.cortical.retina.model.Text;
import static io.cortical.retina.model.ExpressionFactory.and;
import static io.cortical.retina.model.ExpressionFactory.fingerprint;
import static io.cortical.retina.model.ExpressionFactory.or;
import static io.cortical.retina.model.ExpressionFactory.sub;
import static io.cortical.retina.model.ExpressionFactory.term;
import static io.cortical.retina.model.ExpressionFactory.text;
import static io.cortical.retina.model.ExpressionFactory.xor;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;


/**
 * Testing ExpressionFactory class .
 */
public class TestExpressionFactory {
    /**
     * 
     */
    private static final String CAR = "car";
    private static final String JAGUAR = "jaguar";
    private static final String CAR_STRING = "This is a sample text about cars";
    private static final String JAGUAT_STRING = "This is a sample text about jaguars";
    private static final int[] FINGERPRINT_ARRAY_1 = new int[] {1, 2, 3, 4, 5, 6, 8};
    private static final int[] FINGERPRINT_ARRAY_2 = new int[] {10, 20, 30, 40, 50, 60, 80};
    private static final String EXPECTED_JSON_CAR_TERM = prepareJson("{  \"term\" : \"car\",  \"df\" : 0.0,  \"score\" : 0.0}");
    private static final String EXPECTED_JSON_JAGUAR_TERM = "{  \"term\" : \"jaguar\",  \"df\" : 0.0,  \"score\" : 0.0}";
    private static final String EXPECTED_JSON_CAR_TEXT = "{  \"text\" : \"This is a sample text about cars\"}";
    private static final String EXPECTED_JSON_JAGUAR_TEXT = "{  \"text\" : \"This is a sample text about jaguars\"}";
    private static final String EXPECTED_JSON_FINGERPRINT_1 = "{  \"positions\" : [ 1, 2, 3, 4, 5, 6, 8 ]}";
    private static final String EXPECTED_JSON_FINGERPRINT_2 = "{  \"positions\" : [ 10, 20, 30, 40, 50, 60, 80 ]}";
    private static final String EXPECTED_JSON_AND_EXPRESSION_1 = "{  \"and\" : [ {    \"term\" : \"car\",    \"df\" : 0.0,    \"score\" : 0.0  }, {    \"text\" : \"This is a sample text about jaguars\"  } ]}";
    private static final String EXPECTED_JSON_AND_EXPRESSION_2 = "{  \"and\" : [ {    \"term\" : \"jaguar\",    \"df\" : 0.0,    \"score\" : 0.0  }, {    \"text\" : \"This is a sample text about cars\"  }, {    \"and\" : [ {      \"term\" : \"car\",      \"df\" : 0.0,      \"score\" : 0.0    }, {      \"text\" : \"This is a sample text about jaguars\"    } ]  } ]}";
    private static final String EXPECTED_JSON_OR_EXPRESSION_1 = "{  \"or\" : [ {    \"term\" : \"car\",    \"df\" : 0.0,    \"score\" : 0.0  }, {    \"text\" : \"This is a sample text about jaguars\"  } ]}";
    private static final String EXPECTED_JSON_OR_EXPRESSION_2 = "{  \"or\" : [ {    \"term\" : \"jaguar\",    \"df\" : 0.0,    \"score\" : 0.0  }, {    \"text\" : \"This is a sample text about cars\"  }, {    \"or\" : [ {      \"term\" : \"car\",      \"df\" : 0.0,      \"score\" : 0.0    }, {      \"text\" : \"This is a sample text about jaguars\"    } ]  } ]}";
    private static final String EXPECTED_JSON_SUB_EXPRESSION_1 = "{  \"sub\" : [ {    \"term\" : \"car\",    \"df\" : 0.0,    \"score\" : 0.0  }, {    \"text\" : \"This is a sample text about jaguars\"  } ]}";
    private static final String EXPECTED_JSON_SUB_EXPRESSION_2 = "{  \"sub\" : [ {    \"term\" : \"jaguar\",    \"df\" : 0.0,    \"score\" : 0.0  }, {    \"text\" : \"This is a sample text about cars\"  }, {    \"sub\" : [ {      \"term\" : \"car\",      \"df\" : 0.0,      \"score\" : 0.0    }, {      \"text\" : \"This is a sample text about jaguars\"    } ]  } ]}";
    private static final String EXPECTED_JSON_XOR_EXPRESSION_1 = "{  \"xor\" : [ {    \"term\" : \"car\",    \"df\" : 0.0,    \"score\" : 0.0  }, {    \"text\" : \"This is a sample text about jaguars\"  } ]}";
    private static final String EXPECTED_JSON_XOR_EXPRESSION_2 = "{  \"xor\" : [ {    \"term\" : \"jaguar\",    \"df\" : 0.0,    \"score\" : 0.0  }, {    \"text\" : \"This is a sample text about cars\"  }, {    \"xor\" : [ {      \"term\" : \"car\",      \"df\" : 0.0,      \"score\" : 0.0    }, {      \"text\" : \"This is a sample text about jaguars\"    } ]  } ]}";
    /**
     * 
     */
    private Term carTermExpected = new Term(CAR);
    private Term jaguarTermExpected = new Term(JAGUAR);
    
    private Text carTextExpected = new Text(CAR_STRING);
    private Text jaguarTextExpected = new Text(JAGUAT_STRING);
    
    private Fingerprint fingerprint1Expected = fingerprint(FINGERPRINT_ARRAY_1);
    private Fingerprint fingerprint2Expected = fingerprint(FINGERPRINT_ARRAY_2);
    
    @Test
    public void termTest() throws JsonProcessingException {
        Term termCarActual = term(CAR);
        assertEquals(carTermExpected.getTerm(), termCarActual.getTerm());
        assertEquals(new Double(0.0), new Double(termCarActual.getDf()));
        assertNull(termCarActual.getFingerprint());
        assertNull(termCarActual.getPosTypes());
        assertEquals(new Double(0.0), new Double(termCarActual.getScore()));
        assertEquals(EXPECTED_JSON_CAR_TERM, prepareJson(termCarActual.toJson()));
        
        Term termJaguarActual = term(JAGUAR);
        assertEquals(termJaguarActual.getTerm(), jaguarTermExpected.getTerm());
        assertEquals(new Double(0.0), new Double(termJaguarActual.getDf()));
        assertNull(termJaguarActual.getFingerprint());
        assertNull(termJaguarActual.getPosTypes());
        assertEquals(new Double(0.0), new Double(termJaguarActual.getScore()));
        assertEquals(EXPECTED_JSON_JAGUAR_TERM, prepareJson(termJaguarActual.toJson()));
    }
    
    
    @Test
    public void textTest() throws JsonProcessingException {
        Text textCarActual = text(CAR_STRING);
        assertEquals(carTextExpected.getText(), textCarActual.getText());
        assertNull(textCarActual.getFingerprint());
        assertEquals(EXPECTED_JSON_CAR_TEXT, prepareJson(textCarActual.toJson()));

        Text textJaguarActual = text(JAGUAT_STRING);
        assertEquals(jaguarTextExpected.getText(), textJaguarActual.getText());
        assertNull(textJaguarActual.getFingerprint());
        assertEquals(EXPECTED_JSON_JAGUAR_TEXT, prepareJson(textJaguarActual.toJson()));
    }
    
    @Test
    public void fingerprintTest() throws JsonProcessingException {
        Fingerprint fingerprint1Actual = fingerprint(FINGERPRINT_ARRAY_1);
        assertEquals(fingerprint1Expected.getPositions(), fingerprint1Actual.getPositions());
        assertEquals(EXPECTED_JSON_FINGERPRINT_1, prepareJson(fingerprint1Actual.toJson()));
        
        Fingerprint fingerprint2Actual = fingerprint(FINGERPRINT_ARRAY_2);
        assertEquals(fingerprint2Expected.getPositions(), fingerprint2Actual.getPositions());
        assertEquals(EXPECTED_JSON_FINGERPRINT_2, prepareJson(fingerprint2Actual.toJson()));
    }
    
    @Test
    public void andExpressionTest() throws JsonProcessingException {
        Model actualAndExpression1 = and(carTermExpected, jaguarTextExpected);
        assertEquals(EXPECTED_JSON_AND_EXPRESSION_1, prepareJson(actualAndExpression1.toJson()));
        
        Model actualAndExpression2 = and(jaguarTermExpected, carTextExpected, actualAndExpression1);
        assertEquals(EXPECTED_JSON_AND_EXPRESSION_2, prepareJson(actualAndExpression2.toJson()));
    }
    
    @Test
    public void orExpressionTest() throws JsonProcessingException {
        Model actualOrExpression1 = or(carTermExpected, jaguarTextExpected);
        assertEquals(EXPECTED_JSON_OR_EXPRESSION_1, prepareJson(actualOrExpression1.toJson()));
        
        Model actualOrExpression2 = or(jaguarTermExpected, carTextExpected, actualOrExpression1);
        assertEquals(EXPECTED_JSON_OR_EXPRESSION_2, prepareJson(actualOrExpression2.toJson()));
    }
    
    @Test
    public void subExpressionTest() throws JsonProcessingException {
        Model actualSubExpression1 = sub(carTermExpected, jaguarTextExpected);
        assertEquals(EXPECTED_JSON_SUB_EXPRESSION_1, prepareJson(actualSubExpression1.toJson()));
        
        Model actualSubExpression2 = sub(jaguarTermExpected, carTextExpected, actualSubExpression1);
        assertEquals(EXPECTED_JSON_SUB_EXPRESSION_2, prepareJson(actualSubExpression2.toJson()));
    }
    
    @Test
    public void xorExpressionTest() throws JsonProcessingException {
        Model actualXorExpression1 = xor(carTermExpected, jaguarTextExpected);
        assertEquals(EXPECTED_JSON_XOR_EXPRESSION_1, prepareJson(actualXorExpression1.toJson()));
        
        Model actualXorExpression2 = xor(jaguarTermExpected, carTextExpected, actualXorExpression1);
        assertEquals(EXPECTED_JSON_XOR_EXPRESSION_2, prepareJson(actualXorExpression2.toJson()));
    }
    
    private static String prepareJson(String json) {
        return json.replace("\n", "").replace("\r", "");
    }
    
}