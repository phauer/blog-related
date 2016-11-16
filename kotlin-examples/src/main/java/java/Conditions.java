package java;

import java.util.Locale;

public class Conditions {
    public Locale getDefaultLocale(String deliveryArea){
        if (deliveryArea.equals("germany") || deliveryArea.equals("austria") || deliveryArea.equals("switzerland")) {
            return Locale.GERMAN;
        }
        if (deliveryArea.equals("usa") || deliveryArea.equals("england") || deliveryArea.equals("australia")) {
            return Locale.ENGLISH;
        }
        throw new IllegalStateException("Unsupported deliveryArea " + deliveryArea);
    }
    //or via switch and fall-through
}