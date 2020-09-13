package com.serverless;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class PaymentTest {

    @Test
    public void willComputeCorrectPayment() {
        Payments payment = new Payments(30000, 10, 3);
        String ans = payment.computePayment();
        // assert statements
        assertEquals(ans, "{\"interestPaid\":4761.1875,\"payment\":289.67657,\"principal\":30000,\"rate\":3.0,\"period\":10}");
    }
}