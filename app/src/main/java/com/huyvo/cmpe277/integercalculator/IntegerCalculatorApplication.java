package com.huyvo.cmpe277.integercalculator;

import android.app.Application;

import com.huyvo.cmpe277.integercalculator.util.Logger;

/**
 * Created by Huy Vo on 9/20/17.
 */

public class IntegerCalculatorApplication extends Application {
    private final static String TAG = "IntegerCalculatorApplication";

    private static Application mApplication;

    @Override
    public void onCreate(){
        super.onCreate();
        Logger.d("IntegerCalculatorApplication", "onCreate");
        mApplication = this;
    }

    public static Application getApplication(){
        Logger.d("IntegerApplication", "getApplication");
        return mApplication;
    }

    @Override
    public void onTerminate(){
        super.onTerminate();
        Logger.d(TAG, "onTerminate");
        mApplication = null;
    }
}
