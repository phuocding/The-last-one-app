package com.ndp.thelastone;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {

    public static String DB_NAME = "USER";
    public static int DB_VERSION = 1;

    public static String DB_TABLE = "TBL_USER";
    public static String ID = "_id";
    public static String NAME = "name";
    public static String GENDER = "gender";
    public static String DES = "des";


    public DbHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String sql = "CREATE TABLE "+ DB_TABLE + " ( " +
                ID + " INTEGER PRIMARY KEY, " +
                NAME + " TEXT, " +
                GENDER + " TEXT, " +
                DES + " TEXT)";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        String sql = "DROP TABLE IF EXISTS " + DB_TABLE;
        sqLiteDatabase.execSQL(sql);
        onCreate(sqLiteDatabase);
    }

    public String addUser(String user, String gender, String des) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name", user);
        cv.put("gender", gender);
        cv.put("des", des);
        long isAdd = db.insert(DB_TABLE,null,cv);
        if (isAdd == -1) {
            return "Register failed";
        }
        db.close();
        return "Register Success";
    }

    public String updateUser(int id, String user, String gender, String des) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name", user);
        cv.put("gender", gender);
        cv.put("des", des);
        long isUpdate = db.update(DB_TABLE, cv,ID + " = ?",new String[] {id+""});
        if (isUpdate > 0) {
            return "Update success!";
        }
        db.close();
        return "Update fail";
    }

    public String deleteUser(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        long isDelete = db.delete(DB_TABLE,ID + " = ?",new String[] {id+""});
        if (isDelete > 0) {
            return "Delete success!";
        }
        db.close();
        return "Delete fail";
    }

    public Cursor getAllUser() {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String sql = "SELECT * FROM " + DB_TABLE;
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        return cursor;
    }

}
