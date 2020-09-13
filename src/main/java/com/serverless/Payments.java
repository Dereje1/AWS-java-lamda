package com.serverless;

import java.text.NumberFormat;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Payments {
    int principal, periodInYears;
    float annualRate;

    static final byte NUMBER_OF_MONTHS_IN_YEAR = 12;
    static final byte PERCENT = 100;

    public Payments(int p, int N, float R){
        principal = p;
        periodInYears = N;
        annualRate = R;
    }

    public class FormatResponse {
        float monthlyPayment;
        float interestPaid;
       // int principal;

        public float getPayment() {
            return monthlyPayment;
        }
        public void setPayment(float payment) {
            this.monthlyPayment = payment;
        }
    
        public float getInterestPaid() {
            return interestPaid;
        }
        public void setInterestPaid(float interestPaid) {
            this.interestPaid = interestPaid;
        }

        public int getPrincipal(){
            return principal;
        }
        public float getRate(){
            return annualRate;
        }
        public int getPeriod(){
            return periodInYears;
        }
    }


    public String computePayment(){
        try {
            // rateExpression = (1+r)^n
            float monthlyRate = annualRate/PERCENT/NUMBER_OF_MONTHS_IN_YEAR;
            int periodInMonths = periodInYears * NUMBER_OF_MONTHS_IN_YEAR;
            float rateExpression = (float) Math.pow(1+monthlyRate, periodInMonths);
            float monthlyPayment = principal *((monthlyRate * rateExpression)/(rateExpression - 1));
            float interestPaid = (monthlyPayment * periodInMonths) - principal;

            FormatResponse formattedResponse = new FormatResponse();
            formattedResponse.setPayment(monthlyPayment);
            formattedResponse.setInterestPaid(interestPaid);
            //Creating the ObjectMapper object
            ObjectMapper mapper = new ObjectMapper();
            //Converting the Object to JSONString
            String jsonString = mapper.writeValueAsString(formattedResponse);
            return jsonString;
        } catch (Exception e) {
            System.out.println("Error >>>>>>>>>>>>>>" + e);
            return "Error";
        }

    }
}
