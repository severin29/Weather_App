package com.example.weatherupdate;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    private Context context;

    private static final String DATABASE_NAME = "CitiesDB.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "cities";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_CITYNAME = "cityName";
    private static final String COLUMN_CITYCODE = "cityCode";




     DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String query = "CREATE TABLE " + TABLE_NAME +
            " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_CITYNAME + " TEXT, " + COLUMN_CITYCODE + " TEXT);";

        db.execSQL(query);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }


    void addCity(String cityName, String cityCode){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_CITYNAME, cityName);
        cv.put(COLUMN_CITYCODE, cityCode);

        long result = db.insert(TABLE_NAME, null, cv);
        if (result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
        }
    }

    Cursor readAllData(){
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null){
            cursor = db.rawQuery(query,null);

        }

        return cursor;
    }


    void UpdateData(String row_id,String cityName, String cityCode){
         SQLiteDatabase db = this.getWritableDatabase();
         ContentValues cv = new ContentValues();
         cv.put(COLUMN_CITYNAME, cityName);
         cv.put(COLUMN_CITYCODE, cityCode);

         long result = db.update(TABLE_NAME, cv,"_id=?", new String[]{row_id});

         if (result == -1){
             Toast.makeText(context, "Failed to update.", Toast.LENGTH_SHORT).show();
         }else {
             Toast.makeText(context, "Successfully updated.", Toast.LENGTH_SHORT).show();
         }
    }

    void DeleteData(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();

        long result = db.delete(TABLE_NAME,"_id=?", new String[]{row_id});

        if (result == -1){
            Toast.makeText(context, "Failed to delete.", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Successfully deleted.", Toast.LENGTH_SHORT).show();
        }
    }
}
