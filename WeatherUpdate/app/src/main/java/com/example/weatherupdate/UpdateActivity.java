package com.example.weatherupdate;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateActivity extends AppCompatActivity {
    Activity activity;
    EditText etCityName2, etCityCode2;
    Button btnUpdateCity, btnDeleteCity, btnCheckWeather;
    String id, name, code;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        etCityName2 = findViewById(R.id.etCityName2);
        etCityCode2 = findViewById(R.id.etCityCode2);
        btnUpdateCity = findViewById(R.id.btnUpdateCity);
        btnDeleteCity = findViewById(R.id.btnDeleteCity);
        btnCheckWeather = findViewById(R.id.btnCheckWeather);
        getAndSetIntentData();
        btnUpdateCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHelper db = new DatabaseHelper(UpdateActivity.this);
                name = etCityName2.getText().toString().trim();
                code = etCityCode2.getText().toString().trim();
                db.UpdateData(id, name, code);
            }
        });

        btnDeleteCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDialog();
            }
        });

        btnCheckWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = etCityName2.getText().toString();
                String code = etCityCode2.getText().toString();
                Intent i = new Intent(getApplicationContext(), WeatherActivity.class);
                i.putExtra("name", name);
                i.putExtra("code", code);
                startActivity(i);
            }
        });
    }

    void getAndSetIntentData(){
        if (getIntent().hasExtra("id") && getIntent().hasExtra("name") && getIntent().hasExtra("code")){


            id = getIntent().getStringExtra("id");
            name = getIntent().getStringExtra("name");
            code = getIntent().getStringExtra("code");

            etCityName2.setText(name);
            etCityCode2.setText(code);

        }else {
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        }
    }


    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete " + name + " ?");
        builder.setMessage("Are you sure you want to delete " + name + " ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                DatabaseHelper db = new DatabaseHelper(UpdateActivity.this);
                db.DeleteData(id);
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }

}