package com.techvg.los.service;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class EMICalculator {

    private final Logger log = LoggerFactory.getLogger(LoanEligibity.class);

    // using Compound Interest
    double emi_calculator_in_arrears(double principalAmount, double rateOfInterest, double timeInYear) {
        log.debug("In emi_calculator function");
        log.debug("principalAmount: " + principalAmount);
        log.debug("rateOfInterest: " + rateOfInterest);
        log.debug("timeInYear: " + timeInYear);

        double emi;

        rateOfInterest = rateOfInterest / (12 * 100); // one month interest
        //rateOfInterest=round(rateOfInterest);
        log.debug("one month interest: " + rateOfInterest);

        timeInYear = timeInYear * 12; // one month period
        log.debug("one month period: " + timeInYear);

        emi =
            (principalAmount * rateOfInterest * (double) Math.pow(1 + rateOfInterest, timeInYear)) /
            (double) (Math.pow(1 + rateOfInterest, timeInYear) - 1);
        log.debug("emi amount: " + emi);

        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
        System.out.println("EMI in Advance: " + currencyFormat.format(emi));
        return (emi);
    }

    double round(double value) {
        DecimalFormat df = new DecimalFormat("#.##");
        value = Double.valueOf(df.format(value));
        System.out.println(value); // 200.35
        return value;
    }

    double emi_calculator_in_advanced(double principalAmount, double rateOfInterest, double timeInYear) {
        log.debug("In emi_calculator function");
        log.debug("principalAmount: " + principalAmount);
        log.debug("rateOfInterest: " + rateOfInterest);
        log.debug("timeInYear: " + timeInYear);

        rateOfInterest = rateOfInterest / (12 * 100); // one month interest
        //rateOfInterest=round(rateOfInterest);
        log.debug("one month interest: " + rateOfInterest);

        timeInYear = timeInYear * 12; // one month period
        log.debug("one month period: " + timeInYear);

        double amount = principalAmount * Math.pow(1 + (rateOfInterest / 1), 1 * timeInYear);

        log.debug("emi amount: " + amount);
        return (amount);
    }
}
