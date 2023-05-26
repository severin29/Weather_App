package com.example.weatherupdate;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddActivity extends AppCompatActivity {
    EditText etCityName;
    EditText etCityCode;
    Button btnAddCity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        etCityName = findViewById(R.id.etCityName2);
        etCityCode = findViewById(R.id.etCityCode2);
        btnAddCity = findViewById(R.id.btnUpdateCity);

        btnAddCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHelper myDB = new DatabaseHelper(AddActivity.this);
                myDB.addCity(etCityName.getText().toString().trim(), etCityCode.getText().toString().trim());
            }
        });
    }
}