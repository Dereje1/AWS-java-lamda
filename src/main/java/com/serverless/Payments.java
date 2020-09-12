package com.serverless;

import java.text.NumberFormat;

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

    public void printOutput(float[] result){
        System.out.println(
        "\nPrincipal: " + NumberFormat.getCurrencyInstance().format(principal)
         + "; Annual Rate: " + annualRate + "%"
         + "; Loan Period: " + periodInYears + " years"
         );
        String formattedMonthlyPayment = NumberFormat.getCurrencyInstance().format(result[0]);
        String formattedInterestPaid = NumberFormat.getCurrencyInstance().format(result[1]);
        System.out.println("Monthly Payment: " + formattedMonthlyPayment);
        System.out.println("Total Interest Paid: " + formattedInterestPaid);
    }

    public float[] computePayment(){
        // rateExpression = (1+r)^n
        float monthlyRate = annualRate/PERCENT/NUMBER_OF_MONTHS_IN_YEAR;
        int periodInMonths = periodInYears * NUMBER_OF_MONTHS_IN_YEAR;
        float rateExpression = (float) Math.pow(1+monthlyRate, periodInMonths);
        float monthlyPayment = principal *((monthlyRate * rateExpression)/(rateExpression - 1));
        float interestPaid = (monthlyPayment * periodInMonths) - principal;

        float[] val = {monthlyPayment, interestPaid};
        return val;
    }
}
