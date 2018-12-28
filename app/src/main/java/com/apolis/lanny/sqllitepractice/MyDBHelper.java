package com.apolis.lanny.sqllitepractice;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "mydb";
    public static final String TABLE_NAME = "mytable";
    public static final String NAME = "name";
    public static final String PHONE = "phone";
    public static final String EMAIL = "email";
    public static final String ID = "id";

    public static final int VERSION = 2;

    public MyDBHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
//        String CREATE_TABLE = " CREATE TABLE " + TABLE_NAME + " (" + ID +
//                " INTEGER PRIMARY KEY AUTOINCREMENT, " + NAME + " TEXT, " + PHONE + " TEXT, " + EMAIL + " TEXT " + " );";

//        " CREATE TABLE " + TABLE_NAME + " ( " + ID + "INTEGER PRIMARY KEY AUTOINCREMENT, "
//                + NAME + " TEXT, " + PHONE + "  TEXT, " + EMAIL + " TEXT);";

        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + NAME + " TEXT,"
                + PHONE + " TEXT,"
                + EMAIL + " TEXT"+ ")";

        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void updateName(){

    }
}
