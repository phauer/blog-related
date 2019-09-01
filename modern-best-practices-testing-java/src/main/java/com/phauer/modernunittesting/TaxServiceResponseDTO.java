package com.phauer.modernunittesting;

import java.util.Locale;

public class TaxServiceResponseDTO {

    private String locale;
    private double rate;

    public TaxServiceResponseDTO(Locale germany, double rate) {

    }

    public String getLocale() {
        return locale;
    }

    public TaxServiceResponseDTO setLocale(String locale) {
        this.locale = locale;
        return this;
    }

    public double getRate() {
        return rate;
    }

    public TaxServiceResponseDTO setRate(double rate) {
        this.rate = rate;
        return this;
    }
}
