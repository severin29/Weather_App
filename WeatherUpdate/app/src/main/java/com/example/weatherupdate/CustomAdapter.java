package com.example.weatherupdate;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    Context context;
    Activity activity;
    ArrayList city_id, city_name, city_code;

    CustomAdapter(Activity activity,Context context,
                  ArrayList city_id,
                  ArrayList city_name,
                  ArrayList city_code){
        this.activity = activity;
        this.context = context;
        this.city_id = city_id;
        this.city_name = city_name;
        this.city_code = city_code;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.txtCityId.setText(String.valueOf(city_id.get(position)));
        holder.txtCityName.setText(String.valueOf(city_name.get(position)));
        holder.txtCityCode.setText(String.valueOf(city_code.get(position)));
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UpdateActivity.class);
                intent.putExtra("id",String.valueOf(city_id.get(position)));
                intent.putExtra("name",String.valueOf(city_name.get(position)));
                intent.putExtra("code",String.valueOf(city_code.get(position)));
                activity.startActivityForResult(intent,1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return city_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txtCityId, txtCityName, txtCityCode;
        LinearLayout mainLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtCityId = itemView.findViewById(R.id.txtCityID);
            txtCityName = itemView.findViewById(R.id.txtCityName);
            txtCityCode = itemView.findViewById(R.id.txtCityCode);
            mainLayout = itemView.findViewById(R.id.mainLayout);
        }
    }
}
