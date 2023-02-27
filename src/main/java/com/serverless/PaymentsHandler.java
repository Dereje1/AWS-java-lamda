package com.serverless;

import java.util.Collections;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PaymentsHandler implements RequestHandler<Map<String, Object>, ApiGatewayResponse> {

	private static final Logger LOG = LogManager.getLogger(PaymentsHandler.class);

	@Override
	public ApiGatewayResponse handleRequest(Map<String, Object> input, Context context) {
        try {
            LOG.info("received: {}", input);
            // get the 'body' from input
            JsonNode body = new ObjectMapper().readTree((String) input.get("body"));
            int principal = body.get("principal").asInt();
            double rate = body.get("rate").asDouble();
            int period = body.get("period").asInt();

            Payments payment = new Payments(principal,period, (float) rate);
            return ApiGatewayResponse.builder()
                    .setStatusCode(200)
                    .setObjectBody(payment.computePayment())
                    .setHeaders(Collections.singletonMap("Access-Control-Allow-Origin", "*"))
                    .build();
        } catch (Exception e) {
            System.out.println(e);
            Response responseBody = new Response("Error in saving product: ", input);
            return ApiGatewayResponse.builder()
                    .setStatusCode(500)
                    .setObjectBody(responseBody)
                    .setHeaders(Collections.singletonMap("X-Powered-By", "AWS Lambda & Serverless"))
                    .build();
        }

	}
}
