package javaVariant;

import java.sql.SQLException;
import java.util.Locale;

class Conditions {
    public Locale getDefaultLocale(String deliveryArea){
        if (deliveryArea.equals("germany") || deliveryArea.equals("austria") || deliveryArea.equals("switzerland")) {
            return Locale.GERMAN;
        }
        if (deliveryArea.equals("usa") || deliveryArea.equals("great britain") || deliveryArea.equals("australia")) {
            return Locale.ENGLISH;
        }
        throw new IllegalArgumentException("Unsupported deliveryArea " + deliveryArea);
    }
    //or via switch and fall-through


    //verbose. annoying type cast.
    public String getExceptionMessage(Exception exception){
        if (exception instanceof MyLabeledException){
            return ((MyLabeledException) exception).getLabel();
        } else if (exception instanceof SQLException){
            return exception.getMessage() + ". state: " + ((SQLException) exception).getSQLState();
        } else {
            return exception.getMessage();
        }
    }
}

class MyLabeledException extends RuntimeException{
    private String label;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}