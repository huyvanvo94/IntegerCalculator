package com.huyvo.cmpe277.integercalculator;


import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import com.huyvo.cmpe277.integercalculator.util.Logger;

/*
 * A UI class that sends what ever the users has clicks to the listener
 */
public class IntegerCalculatorFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "IntegerCalculatorFragment";
    // A listener variable
    private IntegerCalculatorListener mListener;
    // Instance member of the view
    private View view;
    // Set the listener
    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        Logger.d(IntegerCalculatorListener.class.getSimpleName(), "onAttach");
        // Once on attach, Fragment will set the listener
        mListener = (IntegerCalculatorListener) context;
    }

    public void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        Logger.d(TAG, "onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Logger.d("IntegerCalculatorFragment", "onCreateView");
        view = inflater.inflate(R.layout.fragment_calculator_integer, container, false);
        // Loops through the ViewGroup for buttons.
        //If is button implement OnClickListener.
        GridLayout gridLayout = (GridLayout) view.findViewById(R.id.grid_calc);
        for(int i = 0; i < gridLayout.getChildCount(); i++){
            View v = gridLayout.getChildAt(i);
            if(v instanceof Button){
                v.setOnClickListener(this);
            }
        }
        return view;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        Logger.d(TAG, "onActivityCreated");
    }
    // Lifecycle
    @Override
    public void onStart(){
        super.onStart();
        Logger.d(TAG, "onStart");
    }
    // Lifecycle
    @Override
    public void onResume(){
        super.onResume();
        Logger.d(TAG, "onResume");
    }

    /**
     * Lifecycle class for debugging
     */
    @Override
    public void onPause(){
        super.onPause();
        Logger.d(TAG, "onPause");
    }

    @Override
    public void onStop(){
        super.onStop();
        Logger.d(TAG, "onStop");
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
        Logger.d(TAG, "onDestroyView");
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Logger.d(TAG, "onDestroy");
    }
    // Lifecycle
    @Override
    public void onDetach(){
        super.onDetach();
        Logger.d(TAG, "onDetach");
        // Detach from MainActivity, now set listener to null
        mListener = null;
    }
    // What ever the user clicks, the fragment will tell listener what has been clicked
    @Override
    public void onClick(View v){
        final Button b = (Button) v;
        String text = b.getText().toString();
        Logger.d("IntegerCalculatorListener", text);
        onChangeButtonColor(b);
        mListener.onClick(text);

    }

    private void onChangeButtonColor(Button b){
        b.setBackgroundColor(Color.parseColor("#009688"));
        new ButtonColorTask().execute(b);
    }

    // Allows the listener to send value to the screen
    public void setCalculatorScreen(String value){
        final TextView textView = (TextView) view.findViewById(R.id.calc_result);

        textView.setText(value);
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            textView.setTextSize(getContext().getResources().getDimension(R.dimen.text_size));
        }

        if(value.equals(getString(R.string._error))){
            new ClearTask().execute();
        }
    }

    public void setDisplayOverFlow(){
        TextView textView = (TextView) view.findViewById(R.id.calc_result);
        textView.setText(getContext().getString(R.string._overflow));
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            textView.setTextSize(getContext().getResources().getDimension(R.dimen.overflow_size));
        }
        new ClearTask().execute();
    }

    // Methods listener must implement
    public interface IntegerCalculatorListener{
        void onClick(String value);
    }

    /*
     * Clear screen for error or overflow message.
     */
    private class ClearTask extends AsyncTask<Void, String, String>{

        @Override
        protected String doInBackground(Void ... voids) {
            try{
                Thread.sleep(350);
            }catch (Exception e){}

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            TextView textView = (TextView ) view.findViewById(R.id.calc_result);
            textView.setText(getString(R.string._0));
            if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                textView.setTextSize(getContext().getResources().getDimension(R.dimen.text_size));
            }
        }
    }

    /*
     * An AsyncClass to change UI button for 1/2 second.
     */
    private class ButtonColorTask extends AsyncTask<Button, Integer, Button> {
        @Override
        protected Button doInBackground(Button... buttons) {

            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return buttons[0];
        }

        @Override
        protected void onPostExecute(Button button) {
            button.setBackgroundColor(Color.parseColor("#DDDDDD"));
        }
    }
}
