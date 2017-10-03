package com.huyvo.cmpe277.integercalculator;

import android.content.Context;
import android.content.SharedPreferences;

import com.huyvo.cmpe277.integercalculator.model.IntegerCalculator;
import com.huyvo.cmpe277.integercalculator.model.TokenModel;
import com.huyvo.cmpe277.integercalculator.util.Logger;
import static com.huyvo.cmpe277.integercalculator.util.Constants.CalculatorToken.*;
/**
 * Created by Huy Vo on 9/21/17.
 */

public class IntegerCalculatorHandler {

    public static final String TAG = "IntegerCalculatorHandler";
    /*
     * A model for input value by the user
     */
    private TokenModel mTokenModelOne;
    private TokenModel mTokenModelTwo;
    // just Computed = true if user presses =, else set to false
    private boolean    mJustComputed;
    // The operator such as +, -, /, *
    private String     mOperator;
    /*
     * A listener class
     */
    private IntegerCalculatorHandlerListener mListener;

    public IntegerCalculatorHandler(IntegerCalculatorHandlerListener listener){
        mListener      = listener;
        mTokenModelOne = new TokenModel();
        mTokenModelTwo = new TokenModel();
        mOperator      = null;
        mJustComputed  = false;
    }

    /*
     * Does a logical clear and recall the message to listener for UI update
     */
    private void onClear(){
        Logger.d(TAG, "onClear");
        mJustComputed = false;
        clear();
        log();
        mListener.onClear();
    }
    // Logically clear
    public void clear(){
        mTokenModelTwo.clear();
        mTokenModelOne.clear();
        mOperator = null;
    }
    // Clear values
    // Then save
    public void clearPreferences(){
        Logger.d(TAG, "clearPreferences");
        clear();
        save();
    }
    /*
     * Saves state
     */
    public void save(){
        Logger.d(TAG, "save");
        final Context context = IntegerCalculatorApplication.getApplication().getApplicationContext();
        final String key = context.getString(R.string.preference_key);

        final SharedPreferences sharedPref = context.getSharedPreferences(key, Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPref.edit();

        editor.putString(context.getString(R.string.action), mOperator);
        editor.putString(context.getString(R.string.token_one), mTokenModelOne.toString());
        editor.putString(context.getString(R.string.token_two), mTokenModelTwo.toString());
        editor.putBoolean(context.getString(R.string.computed), mJustComputed);
        editor.commit();
    }
    /*
     *  Will load data from saved preferences.
     */
    public void load(){
        Logger.d(TAG, "load");
        final Context context = IntegerCalculatorApplication.getApplication().getApplicationContext();
        final String key = context.getString(R.string.preference_key);
        final SharedPreferences sharedPref =  context.getSharedPreferences(key, Context.MODE_PRIVATE);

        final String tokenOneValue = sharedPref.getString(context.getString(R.string.token_one), "");
        final String tokenTwoValue = sharedPref.getString(context.getString(R.string.token_two), "");
        final String operatorValue = sharedPref.getString(context.getString(R.string.action), null);
        final boolean computed = sharedPref.getBoolean(context.getString(R.string.computed), false);

        mTokenModelOne = new TokenModel(tokenOneValue);
        mTokenModelTwo = new TokenModel(tokenTwoValue);

        if(operatorValue != null){
            mOperator = operatorValue;
        }

        mJustComputed = computed;
        log();

        if(!mTokenModelTwo.isEmpty()) {
            Logger.d(TAG, "model two");
            mListener.onComputed(mTokenModelTwo.getValue());
        }
        else if(!mTokenModelOne.isEmpty()) {
            Logger.d(TAG, "model one");
            mListener.onComputed(mTokenModelOne.getValue());
        }
        else{
            Logger.d(TAG, "clear");
            mListener.onClear();
        }

    }
    /*
     * Algorithm determines what to do with buttons clicked from UI
     * The input value from UI such as operators and digits
     */
    public void onInput(String value){
        Logger.d(TAG, "value= " + value);

        if(isAction(value)){
            if(mTokenModelOne.isEmpty()){
                if(value.equals(ADD_TOKEN) || value.equals(SUB_TOKEN)){
                    mTokenModelOne.setSign(value);
                }
              //  return;
            }else{
                mOperator = value;
            }

            if(mOperator != null){
                onOperation();
            }

            log();
        }else{
            switch (value){
                case CLEAR_TOKEN:
                    onClear();
                    break;

                case EQUAL_TOKEN:
                    onEqual();
                    break;
                // A number was the input
                default:
                    onAppendValue(value);
                    break;
            }
        }

        log();
    }

    /*
     * Determine what to do with the appended value
     */
    private void onAppendValue(String value){
        Logger.d(TAG, "Append Value = " + value);
        // Determines if overflow will happen
        if(mTokenModelOne.length() > 6 || mTokenModelTwo.length() > 6){
            onClear();
            mListener.onOverFlow();
        }
        // the rest of if statements will determine how to append the value
        else if(mJustComputed && mTokenModelOne.isEmpty()){
            Logger.d(TAG, "1");
            mJustComputed = false;
            mTokenModelOne.clear();
            mTokenModelOne.append(value);
            mListener.onComputed(mTokenModelOne.getValue());

        }else if(mTokenModelOne.isEmpty() || mOperator == null){
            Logger.d(TAG, "2");
            if(mJustComputed){
                mTokenModelOne.clear();
                mJustComputed = false;
            }
            mTokenModelOne.append(value);
            mListener.onComputed(mTokenModelOne.getValue());
        }else{
            Logger.d(TAG, "3");
            mTokenModelTwo.append(value);
            mListener.onComputed(mTokenModelTwo.getValue());
        }

        log();
    }

    // Determines if value is +, /, * or -
    public boolean isAction(String value){
        return value.equals(ADD_TOKEN) || value.equals(SUB_TOKEN) || value.equals(DIV_TOKEN) || value.equals(MUL_TOKEN);
    }

    public void log() {

        Logger.d("IntegerCalculatorHandler", " token one: " + mTokenModelOne.getValue() + " mOperator: " + mOperator + " token two: " + mTokenModelTwo.getValue() + " justcomputed: " + mJustComputed);

    }

    //Determines if value is Overflow from TokenModel
    public boolean isOverFlow(TokenModel tokenModel){
        final int number = tokenModel.getIntegerValue();
        return (number > IntegerCalculator.MAX || number < IntegerCalculator.MIN);
    }

    /*
     * Will be called if user clicked an operator sign
     * Possible returns: error, computed, or overflow send to listener
     */
    private void onOperation(){
        Logger.d(TAG, "onOperation");
        if(mTokenModelOne.isEmpty() || mTokenModelTwo.isEmpty() || mOperator == null){

            return;
        }
        TokenModel result = computeCalculation();
        if(result != null){
            if(isOverFlow(result)){
                clear();
                mListener.onOverFlow();
            }else {
                mJustComputed = false;
                mTokenModelTwo.clear();
                mTokenModelOne = result;
                mListener.onComputed(mTokenModelOne.getValue());

            }
        }else{
            clear();
            mListener.onError();
        }
        log();
    }
    /*
     * Will be called if user clicks "=" sign
     * Possibilities of returns are error, computed value, or overflow
     */
    private void onEqual(){
        Logger.d(TAG, "onEqual");
        if(mTokenModelOne.isEmpty() || mTokenModelTwo.isEmpty() || mOperator == null){
            Logger.d(TAG, "error");
            mListener.onError();
            return;
        }
        TokenModel result = computeCalculation();
        if(result != null){
            if(isOverFlow(result)){
                clear();
                mListener.onOverFlow();
            }else{
                clear();
                mJustComputed = true;
                mTokenModelOne = result;
                mListener.onComputed(result.getValue());
            }
        }else{
            clear();
            mListener.onError();
        }

        log();
    }
    /*
     * Does +, -, *, and / calculations
     */
    private TokenModel computeCalculation(){
        if(mOperator == null || mTokenModelOne.isEmpty() || mTokenModelTwo.isEmpty()){
            return null;
        }

        TokenModel result;
        switch (mOperator){
            case ADD_TOKEN:
                result = IntegerCalculator.add(mTokenModelOne, mTokenModelTwo);
                break;
            case SUB_TOKEN:
                result = IntegerCalculator.subtract(mTokenModelOne, mTokenModelTwo);
                break;
            case MUL_TOKEN:
                result = IntegerCalculator.multi(mTokenModelOne, mTokenModelTwo);
                break;
            case DIV_TOKEN:
                result = IntegerCalculator.divid(mTokenModelOne, mTokenModelTwo);
                break;
            default:
                return null;
        }

        return result;
    }
    /*
     * methods that listeners must implement
     */
    public interface IntegerCalculatorHandlerListener{
        void onComputed(String value);
        void onError();
        void onOverFlow();
        void onClear();
    }
}
