package com.example.mhn.trailattendance;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.R.attr.version;

/**
 * Created by MHN on 12/8/2016.
 */

public class ContactHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME="Android_Bdjobs_Batch_3";
    public static final String CONTACT_TABLE="contacts";
    public static final int DATABASE_VERSION=1;
    Context context;

    public static final String COL_ID="id";
    public static final String COL_NAME="name";
    public static final String COL_ROLL="roll";
    public static final String COL_EMAIL="email";
    public static final String COL_PHONENO="phoneNo";
    public static final String COL_BATCHNO="batchNo";
    public static final String COL_USER_NAME="userName";
    public static final String COL_PASSWORD="password";
    public static final String COL_MAC_ADDRESS="macAddress";
    public static final String COL_PRESENCE="presence";

    private static final String CREATE_TABLE_NAME= " CREATE TABLE " + CONTACT_TABLE + " ( " + COL_ID +
            " INTEGER PRIMARY KEY," + COL_NAME + " TEXT," + COL_ROLL + " INTEGER UNIQUE,"
            + COL_EMAIL + " TEXT," + COL_PHONENO + " TEXT," + COL_BATCHNO + " TEXT,"
            + COL_MAC_ADDRESS + " TEXT UNIQUE," + COL_USER_NAME + " TEXT UNIQUE," + COL_PASSWORD + " TEXT, "
            +COL_PRESENCE+" TEXT) ";


    public ContactHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_NAME);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
