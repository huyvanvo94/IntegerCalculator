package com.huyvo.cmpe277.integercalculator.model;

/**
 * This represents one number
 */

import static com.huyvo.cmpe277.integercalculator.util.Constants.CalculatorToken.ADD_TOKEN;
import static com.huyvo.cmpe277.integercalculator.util.Constants.CalculatorToken.EMPTY_TOKEN;
import static com.huyvo.cmpe277.integercalculator.util.Constants.CalculatorToken.SUB_TOKEN;

public class TokenModel implements Model {
    // Variable if number is negative or positive
    private String mSign;
    // The number
    private String mValue;
    // constructors
    public TokenModel(){
        this(EMPTY_TOKEN);
    }
    public TokenModel(String value){
        setValue(value);
    }
    public TokenModel(double value){
        setValue(String.valueOf(value));
    }

    public TokenModel(int value){
        setValue(String.valueOf(value));
    }
    public int length(){
        return mValue.length();
    }

    public boolean isEmpty(){
        return mValue.length() == 0;
    }

    public void append(String v){
        mValue += v;
        checkForZero();
    }

    public void setSign(String sign){
        mSign = sign;
    }
    // Will remove the beginning zero
    // if number = 04, the output will be 4
    private void checkForZero(){
        if(mValue.length()>1 && mValue.charAt(0) == '0'){
            int begin = 1;
            int end = mValue.length();
            mValue = mValue.substring(begin, end);

        }
    }

    public void setValue(String value){
        if(value.equals(EMPTY_TOKEN) || Integer.valueOf(value) >= 0){
            mSign = ADD_TOKEN;
            mValue = value;
        }else{
            mValue = String.valueOf(-1*Integer.valueOf(value));
            mSign = SUB_TOKEN;
        }

    }

    public int getIntegerValue(){
        return Integer.valueOf(getValue());
    }

    public double getDoubleValue(){

        return Double.valueOf(getValue());
    }
    // if is negative return -number
    // else if positive return number
    public String getValue(){
        if(mSign.equals(SUB_TOKEN)){
            return mSign + mValue;
        }
        return mValue;
    }
    // set value to "" and mSign = "+"
    public void clear(){
        mValue = EMPTY_TOKEN;
        mSign  = ADD_TOKEN;
    }

    @Override
    public String toString(){

        return getValue();
    }
}