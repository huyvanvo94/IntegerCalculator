package com.huyvo.cmpe277.integercalculator.util;

import android.util.Log;

import com.huyvo.cmpe277.integercalculator.IntegerCalculatorHandler;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Huy Vo on 9/20/17.
 */

public final class Logger {

    public final static List<String> LIST = Arrays.asList(
            IntegerCalculatorHandler.TAG,
            "MainActivity",
            "IntegerCalculatorFragment"
    );

    /*
     * A method of logging in which I can control what is able to print out.
     */
    public static synchronized void d(String clazz, String descrip){
        if(LIST.contains(clazz)) {
            Log.d(clazz, descrip);
        }
    }
}
