package com.example.weatherupdate;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;

public class WeatherActivity extends AppCompatActivity {

    TextView tvResult, tvCityName, tvCityCode;
    private final String url = "https://api.openweathermap.org/data/2.5/weather";
    private final String appid = "e109c74e0addd3416212626493c013ee";
    DecimalFormat df = new DecimalFormat("#.##");




    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        tvResult = findViewById(R.id.tvResult);
        tvCityName = findViewById(R.id.tvCityName);
        tvCityCode = findViewById(R.id.tvCityCode);

        Intent intent = getIntent();

        String name = intent.getStringExtra("name");
        String code = intent.getStringExtra("code");

        tvCityName.setText(name);
        tvCityCode.setText(code);
    }

    public void getWeatherDetails(View view) {
        String tempUrl = "";
        String name = String.valueOf(tvCityName.getText());
        String code = String.valueOf(tvCityCode.getText());
        if(name.equals("")){
            tvResult.setText("City field can not be empty!");
        }else{
            if(!code.equals("")){
                tempUrl = url + "?q=" + name + "," + code + "&appid=" + appid;
            }else{
                tempUrl = url + "?q=" + name + "&appid=" + appid;
            }
            StringRequest stringRequest = new StringRequest(Request.Method.POST, tempUrl, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    String output = "";
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        JSONArray jsonArray = jsonResponse.getJSONArray("weather");
                        JSONObject jsonObjectWeather = jsonArray.getJSONObject(0);
                        String description = jsonObjectWeather.getString("description");
                        JSONObject jsonObjectMain = jsonResponse.getJSONObject("main");
                        double temp = jsonObjectMain.getDouble("temp") - 273.15;
                        double feelsLike = jsonObjectMain.getDouble("feels_like") - 273.15;
                        float pressure = jsonObjectMain.getInt("pressure");
                        int humidity = jsonObjectMain.getInt("humidity");
                        JSONObject jsonObjectWind = jsonResponse.getJSONObject("wind");
                        String wind = jsonObjectWind.getString("speed");
                        JSONObject jsonObjectClouds = jsonResponse.getJSONObject("clouds");
                        String clouds = jsonObjectClouds.getString("all");
                        JSONObject jsonObjectSys = jsonResponse.getJSONObject("sys");
                        String countryName = jsonObjectSys.getString("country");
                        String cityName = jsonResponse.getString("name");
                        tvResult.setTextColor(Color.rgb(68,134,199));
                        output += "Current weather of " + cityName + " (" + countryName + ")"
                                + "\n Temp: " + df.format(temp) + " °C"
                                + "\n Feels Like: " + df.format(feelsLike) + " °C"
                                + "\n Humidity: " + humidity + "%"
                                + "\n Description: " + description
                                + "\n Wind Speed: " + wind + "m/s (meters per second)"
                                + "\n Cloudiness: " + clouds + "%"
                                + "\n Pressure: " + pressure + " hPa";
                        tvResult.setText(output);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener(){

                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), error.toString().trim(), Toast.LENGTH_SHORT).show();
                }
            });
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);
        }
    }
}