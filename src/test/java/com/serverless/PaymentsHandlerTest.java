package com.serverless;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.amazonaws.services.lambda.runtime.Context;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PaymentsHandlerTest {

    private Context createContext() {
        TestContext ctx = new TestContext();
        return ctx;
    }

    private Map<String,Object> responseMock() throws IOException {
        Map<String, Object> stubInput = new HashMap<String, Object>();
        stubInput.put("body","{\"principal\":\"30000\",\"rate\":\"3\",\"period\":\"10\"}");

        Context ctx = createContext();
        PaymentsHandler handler = new PaymentsHandler();
        ApiGatewayResponse mockResponse = handler.handleRequest(stubInput, ctx);
        
        String responseBody = mockResponse.getBody();
        Map<String, String> responseHeaders = mockResponse.getHeaders();
        int statusCode = mockResponse.getStatusCode();
        Boolean IsBase64Encoded = mockResponse.isIsBase64Encoded();
        
        responseBody = responseBody
                        .substring(1,responseBody
                        .length()-1).replace("\\", "");

        Map<String, Object> composite = new HashMap<String, Object>();
        composite.put("body", responseBody);
        composite.put("headers", responseHeaders);
        composite.put("status-code", statusCode);
        composite.put("IsBase64Encoded", IsBase64Encoded);
        return composite;
    }

    @DisplayName("Responds with the correct payments and interest paid")
    @Test

    public void testPayments() throws IOException {
        Map<String, Object>  response = responseMock();
        JsonNode parsedBodyOutput = new ObjectMapper().readTree((String) response.get("body"));
        assertEquals(4761.1875, parsedBodyOutput.get("interestPaid").asDouble());
        assertEquals(289.67657, parsedBodyOutput.get("payment").asDouble());
    }

    @DisplayName("Responds with the correct headers and status code")
    @Test

    public void testMetaData() throws IOException {
        Map<String, Object>  response = responseMock();
        assertEquals("{Access-Control-Allow-Origin=*}", response.get("headers").toString());
        assertEquals(200, response.get("status-code"));
        assertEquals(false, response.get("IsBase64Encoded"));
    }
}
