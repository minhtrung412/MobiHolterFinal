package com.example.mobiholterfinal.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.fragment.app.Fragment;

import com.example.mobiholterfinal.model.UserData;

import java.util.ArrayList;
import java.util.List;

public class DBManager extends SQLiteOpenHelper {
    private final String TAG = "DBManager";
    private static final String DATABASE_NAME = "user_data";
    private static final String TABLE_NAME = "users";
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String TIME = "time";
    private static final String CHANNEL_1 = "ecg_channel_1";
    private static final String CHANNEL_2 = "ecg_channel_2";
    private static final String CHANNEL_3 = "ecg_channel_3";
    private static final String HEART_RATE = "heart_rate";
    private static final String URO_DATA = "uro_data";
    private static int VERSION = 1;

    private Context context;
    private Fragment fragment;
    private String SQLQuery = "CREATE TABLE " + TABLE_NAME + " (" +
            ID + " integer primary key, " +
            NAME + " TEXT, " +
            TIME + " TEXT, " +
            CHANNEL_1 + " TEXT, " +
            CHANNEL_2 + " TEXT, " +
            CHANNEL_3 + " TEXT, " +
            HEART_RATE + " TEXT, " +
            URO_DATA + " TEXT)";





    public DBManager(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
        this.context = context;
        Log.d(TAG, "DBManager: ");
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQLQuery);
        Log.d(TAG, "onCreate: ");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        Log.d(TAG, "onUpgrade: ");

    }

    public void addUserData(UserData userData) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME, userData.getmName());
        values.put(TIME, userData.getmTime());
        values.put(CHANNEL_1, userData.getEcg_channel_1());
        values.put(CHANNEL_2, userData.getEcg_channel_2());
        values.put(CHANNEL_3, userData.getEcg_channel_3());
        values.put(HEART_RATE, userData.getHeart_rate());
        values.put(URO_DATA, userData.getUro_data());
        db.insert(TABLE_NAME, null, values);
        db.close();
        Log.d(TAG, "addData Successfuly");
    }

    public List<UserData> getAllUserData() {
        List<UserData> listUserData = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                UserData userData = new UserData();
                userData.setmID(cursor.getInt(0));
                userData.setmName(cursor.getString(1) + "");
                userData.setmTime(cursor.getString(2));
                userData.setEcg_channel_1(cursor.getString(3));
                userData.setEcg_channel_2(cursor.getString(4));
                userData.setEcg_channel_3(cursor.getString(5));
                userData.setHeart_rate(cursor.getString(6));
                userData.setUro_data(cursor.getString(7));

                listUserData.add(userData);

            } while (cursor.moveToNext());
        }
        db.close();
        return listUserData;
    }

    public Cursor getData(int id, Context context){
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE ID = ?";
        Cursor cursor = db.rawQuery(selectQuery, new String[] {String.valueOf(id)});
        return cursor;

    }

    public int deleteUserData(int id, Context context){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, ID +"=?", new String[] {String.valueOf(id)});
    }
    public void removeAll()
    {
        // db.delete(String tableName, String whereClause, String[] whereArgs);
        // If whereClause is null, it will delete all rows.
        SQLiteDatabase db = this.getWritableDatabase(); // helper is object extends SQLiteOpenHelper
        db.delete(TABLE_NAME, null, null);
    }


}
