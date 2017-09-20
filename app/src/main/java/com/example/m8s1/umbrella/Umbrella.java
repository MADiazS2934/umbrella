package com.example.m8s1.umbrella;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Umbrella extends AppCompatActivity
{
    //Variables to show info to the user.
    TextView tempTextView;
    TextView dateTextView;
    TextView weatherDescTextView;
    TextView cityTextView;
    TextView relativeHumidityTextView;
    TextView uvFactor;
    TextView feelsLikeTextView;
    ImageView weatherImageView;

    //Static variable to chose between C or F, changes from the RadioButton Listener
    public static int ImperialOrCelsius;
    @Override
    protected void onCreate (Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);


        //Declaring and getting the Intent from the previous activity (MainActivity)
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        //Bind all Views with their correct ID
        tempTextView =          (TextView) findViewById(R.id.tempTextView);
        dateTextView =          (TextView) findViewById(R.id.dateTextView);
        weatherDescTextView =   (TextView) findViewById(R.id.weatherDescriptionText);
        cityTextView =          (TextView) findViewById(R.id.cityTextView);

        relativeHumidityTextView =      (TextView) findViewById(R.id.relativeHumidityTextView);
        uvFactor =              (TextView) findViewById(R.id.uvTextView);
        feelsLikeTextView =             (TextView) findViewById(R.id.feelsLikeTextView);
        weatherImageView = (ImageView) findViewById(R.id.weatherDesctextView);

        //Setting data to the views date / background
        dateTextView.setText(getCurrentDate());
        final    ConstraintLayout constraintLayout =
                (ConstraintLayout) findViewById(R.id.umbrellaLayout);

        //   You can implement an Image View in the design
        //   weatherImageView = (ImageView) findViewById(R.id.weatherImageView);


        //API URL using volley, in this logic I used a String variable to change the zipcode
        //The "message" gets populated from the previous intent via a textEdit on number format
        String url =
                "http://api.wunderground.com/api/a2f7b1dc2258dde8/conditions/q/"
                        +message+
                        ".json";
        //Pending implementation of hourly API, same zipcode parameter as the main url
        String urlHourly =
                "http://api.wunderground.com/api/a2f7b1dc2258dde8/hourly/q/"
                        +message+".json";


        //Start the JsonObjectRequest using volley

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject responseObject) {

                        //Just a log for debugging purposes, in order to get the full API response
                        Log.v("WEATHER", "Response: " + responseObject.toString());

                        //By default its gonna fetch both in a single string
                        String CelsiusOrImperial = "temperature_string";

                        //But the logic change this variable to show only C or F
                        if (ImperialOrCelsius == 1){
                            CelsiusOrImperial = "temp_c";
                        }

                        if (ImperialOrCelsius == 2){
                            CelsiusOrImperial = "temp_f";
                        }

                        try
                        {
                    //Main JSON Object that can fetch several TEMP data
                    JSONObject mainJSONObject = responseObject.getJSONObject("current_observation");
                    //Second JSON Object to get to a third JSON Object to access city string
                    JSONObject cityJSONObject = responseObject.getJSONObject("current_observation");
                    //Third JSON that contains strings about the location of the zipcode
                    JSONObject cityJSON = cityJSONObject.getJSONObject("display_location");
            //        JSON Array to move on the API array, PENDING
            //        JSONArray weatherArray = responseObject.getJSONArray("weather");
            //        JSONObject firstWeatherObject = weatherArray.getJSONObject(0);

                    //String Variable that gets the current temp, depending on the type C or F
                    String temp = Integer.toString
                            ((int) Math.round(mainJSONObject.getDouble(CelsiusOrImperial)));


                    String city = cityJSON.getString("full");
                    String weatherDescription = mainJSONObject.getString("weather");
                    String relativeHumidity = mainJSONObject.getString("relative_humidity");
                    String feelsLike =  mainJSONObject.getString("feelslike_string");
                    String uv = mainJSONObject.getString("UV");

                        // An Integer object that gets the value of the String temp as an int
                        //I use it to change the color depending if its warm or cold
                            Integer tempHotOrCold = Integer.valueOf(temp);
                            Integer uvChecker = Integer.valueOf(uv);

                        //This is the double if loop to change the background
                        //First I have to check if its C or F then I can check the warm or cold bg
                            if(ImperialOrCelsius ==2) {

                                if (tempHotOrCold < 65) {
                                    constraintLayout.setBackgroundResource(R.drawable.backgroundcolder2);

                                }
                                if (tempHotOrCold >= 66) {
                                    constraintLayout.setBackgroundResource(R.drawable.backgroundwarmer2);
                                }
                            }
                            if(ImperialOrCelsius== 1){
                                if (tempHotOrCold < 18) {
                                    constraintLayout.setBackgroundResource(R.drawable.backgroundcolder2);

                                }
                                if (tempHotOrCold >= 19) {
                                    constraintLayout.setBackgroundResource(R.drawable.backgroundwarmer2);
                                }
                            }

                                //Here I populate all the views
                                tempTextView.setText(temp);
                                weatherDescTextView.setText(weatherDescription);
                                cityTextView.setText(city);

                                relativeHumidityTextView.setText(relativeHumidity);
                                feelsLikeTextView.setText(feelsLike);
                                uvFactor.setText(uv);
                            if(uvChecker >= 2) {
                                weatherImageView.setImageResource(R.drawable.icon_orangesun);
                            }
                            if(uvChecker <= 1){
                                weatherImageView.setImageResource(R.drawable.icon_bluesun);
                            }
                                //TODO Populate hourly views using hourly api

                        }
                        catch (JSONException e )
                        {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub

                    }
                });

        // Access the RequestQueue through your class.
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jsObjRequest);
    }


        //Simple method to get the data as a string
    private String getCurrentDate ()
    {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, MMM dd");
        String formattedDate = dateFormat.format(calendar.getTime());

        return formattedDate;
    }
}