package com.huyvo.cmpe277.integercalculator;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.huyvo.cmpe277.integercalculator.util.Logger;

public class MainActivity extends BaseActivityWithFragment implements
        IntegerCalculatorFragment.IntegerCalculatorListener,
        IntegerCalculatorHandler.IntegerCalculatorHandlerListener{

    private static final String TAG = "MainActivity";
    // Class used to complete and do calculations
    private IntegerCalculatorHandler mICHandler;
    // Init
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.d(TAG, "onCreate");

        setContentView(R.layout.activity_main);
        // tells IntegerCalculatorHandler MainActivity is the listener
        mICHandler = new IntegerCalculatorHandler(this);

    }

    /*
     * I wanted to call the lifecycle to understand the state of my application
     */

    @Override
    public void onStart(){
        super.onStart();
        Logger.d(TAG, "onStart");
    }

    // Get value from shared preferences and load it to models
    @Override
    public void onResume(){
        super.onResume();
        Logger.d(TAG, "onResume");

       if(mICHandler == null){
          mICHandler = new IntegerCalculatorHandler(this);
        }
        mICHandler.load();

    }

    // Save data before exiting
    @Override
    public void onPause(){
        super.onPause();
        Logger.d(TAG, "onPause");
        if(mICHandler != null) {
            mICHandler.save();
        }
    }
    @Override
    public void onStop(){
        super.onStop();
        Logger.d(TAG, "onStop");
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Logger.d(TAG, "onDestroy");
    }

    @Override
    public void onRestart(){
        super.onRestart();
        Logger.d(TAG, "onRestart");
    }

    // Tells Handler to compute this value
    @Override
    public void onClick(String value) {
        mICHandler.onInput(value);
    }

    public void updateScreen(String value){
        IntegerCalculatorFragment integerCalculatorFragment = getIntegerCalculatorFragment();
        if (null != integerCalculatorFragment) {
            integerCalculatorFragment.setCalculatorScreen(value);
        }
    }
    // Get the Fragment to update UI
    private IntegerCalculatorFragment getIntegerCalculatorFragment(){
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.calc_fragment);

        if(fragment != null){
            if(fragment instanceof IntegerCalculatorFragment){
               return (IntegerCalculatorFragment) fragment;
            }
        }

        return null;
    }


    // Listener Methods
    // Method names is self-descriptive
    @Override
    public void onComputed(String value) {
        updateScreen(value);
    }

    @Override
    public void onError(){
        updateScreen(getString(R.string._error));
    }

    @Override
    public void onOverFlow(){
        IntegerCalculatorFragment fragment = getIntegerCalculatorFragment();
        if(fragment != null){
            fragment.setDisplayOverFlow();
        }
    }

    @Override
    public void onClear(){
        updateScreen(getString(R.string._0));
    }
}
