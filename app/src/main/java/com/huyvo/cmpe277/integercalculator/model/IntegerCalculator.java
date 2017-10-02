package com.huyvo.cmpe277.integercalculator.model;

/**
 * Created by Huy Vo on 9/20/17.
 */

public class IntegerCalculator {
    // The max and min constant for the calculator
    public static final int MAX = 9999999;
    public static final int MIN = -9999999;
    // integer add
    public static TokenModel add(TokenModel one, TokenModel two){
        int value1 = one.getIntegerValue();
        int value2 = two.getIntegerValue();
        int result = value1 + value2;

        return new TokenModel(result);
    }
    // integer subtraction
    public static TokenModel subtract(TokenModel one, TokenModel two){
        int value1 = one.getIntegerValue();
        int value2 = two.getIntegerValue();
        int result = value1 - value2;
        return new TokenModel(result);

    }
    // integer multiplication
    public static TokenModel multi(TokenModel one, TokenModel two){
        int value1 = one.getIntegerValue();
        int value2 = two.getIntegerValue();
        int result = value1 * value2;
        return new TokenModel(result);
    }
    // integer divide
    // will round number up
    // Example:
    // -5/2 = -3
    // 5/2 = 3
    public static TokenModel divid(TokenModel one, TokenModel two){
        double value1 = one.getDoubleValue();
        double value2 = two.getDoubleValue();
        double dResult = value1/value2;
        if(value2 == 0.0){
            return null;
        }else if(dResult < 0){
            int result = (int) Math.round(-1 * (dResult));
            return new TokenModel(-1*result);

        }else {
            int result = (int) Math.round(value1 / value2);
            return new TokenModel(result);
        }

    }
}
