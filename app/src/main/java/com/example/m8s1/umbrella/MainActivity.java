package com.example.m8s1.umbrella;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;

import static com.example.m8s1.umbrella.Umbrella.ImperialOrCelsius;

public class MainActivity extends AppCompatActivity {



    private EditText userInput;
    //TAG for some logs
    private static final String TAG = "MainActivity";
    //Static EXTRA_MESSAGE for the intent
    public final static String EXTRA_MESSAGE = "com.example.m8s1.umbrella.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Create andBind all Views with their correct ID
        userInput = (EditText) findViewById(R.id.editText);
        RadioButton radioButtonC = (RadioButton) findViewById(R.id.radioButtonC);
        RadioButton radioButtonF = (RadioButton) findViewById(R.id.radioButtonF);

        //When you click View.onClickListener most of the code gets auto generated
        // This is to set our own listener
        View.OnClickListener ourOnclickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //onClick Method with an Intent
                Intent intent = new Intent(MainActivity.this, Umbrella.class);
                EditText editText = (EditText) findViewById(R.id.editText);
                String message = editText.getText().toString();
                intent.putExtra(EXTRA_MESSAGE, message);
                startActivity(intent);
                switch(view.getId()){

                    case R.id.radioButtonC:
                        ImperialOrCelsius = 1;
                        break;
                    case R.id.radioButtonF:
                        ImperialOrCelsius = 2;
                        break;
                    //same for the rest
                }

            }

        };

     //Add ourListener to both RadioButtons
        radioButtonC.setOnClickListener(ourOnclickListener);
        radioButtonF.setOnClickListener(ourOnclickListener);



    }

    //Pending Implementations

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onRestart() {
        Log.d(TAG, "onRestart: in");
        super.onRestart();
        Log.d(TAG, "onRestart: out");
    }
}
