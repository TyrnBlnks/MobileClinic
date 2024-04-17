package com.example.icebear;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UserDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Users.db";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + UserContract.UserEntry.TABLE_NAME + " (" +
                    UserContract.UserEntry._ID + " INTEGER PRIMARY KEY," +
                    UserContract.UserEntry.COLUMN_NAME_FIRST_NAME + " TEXT," +
                    UserContract.UserEntry.COLUMN_NAME_LAST_NAME + " TEXT," +
                    UserContract.UserEntry.COLUMN_NAME_EMAIL + " TEXT," +
                    UserContract.UserEntry.COLUMN_NAME_PASSWORD + " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + UserContract.UserEntry.TABLE_NAME;

    public UserDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public long addUser(String firstName, String lastName, String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UserContract.UserEntry.COLUMN_NAME_FIRST_NAME, firstName);
        values.put(UserContract.UserEntry.COLUMN_NAME_LAST_NAME, lastName);
        values.put(UserContract.UserEntry.COLUMN_NAME_EMAIL, email);
        values.put(UserContract.UserEntry.COLUMN_NAME_PASSWORD, password);  // Consider using hashing for storing passwords

        long newRowId = db.insert(UserContract.UserEntry.TABLE_NAME, null, values);
        return newRowId;  // Returns the row ID of the newly inserted row, or -1 if an error occurred
    }

    public Cursor getUser(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {
                UserContract.UserEntry._ID,
                UserContract.UserEntry.COLUMN_NAME_FIRST_NAME,
                UserContract.UserEntry.COLUMN_NAME_LAST_NAME,
                UserContract.UserEntry.COLUMN_NAME_EMAIL,
                UserContract.UserEntry.COLUMN_NAME_PASSWORD
        };
        String selection = UserContract.UserEntry.COLUMN_NAME_EMAIL + " = ?";
        String[] selectionArgs = { email };

        Cursor cursor = db.query(
                UserContract.UserEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
        return cursor;  // Remember to close the cursor after its use outside this method
    }

    public int updateUser(String email, String newFirstName, String newLastName, String newPassword) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UserContract.UserEntry.COLUMN_NAME_FIRST_NAME, newFirstName);
        values.put(UserContract.UserEntry.COLUMN_NAME_LAST_NAME, newLastName);
        values.put(UserContract.UserEntry.COLUMN_NAME_PASSWORD, newPassword);  // Again, consider security practices

        String selection = UserContract.UserEntry.COLUMN_NAME_EMAIL + " = ?";
        String[] selectionArgs = { email };

        int count = db.update(
                UserContract.UserEntry.TABLE_NAME,
                values,
                selection,
                selectionArgs
        );
        return count;  // Returns the number of rows affected
    }

    public int deleteUser(String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = UserContract.UserEntry.COLUMN_NAME_EMAIL + " = ?";
        String[] selectionArgs = { email };

        int deletedRows = db.delete(UserContract.UserEntry.TABLE_NAME, selection, selectionArgs);
        return deletedRows;  // Returns the number of rows deleted
    }

    public boolean checkUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = { UserContract.UserEntry.COLUMN_NAME_EMAIL };
        String selection = UserContract.UserEntry.COLUMN_NAME_EMAIL + " = ? AND " + UserContract.UserEntry.COLUMN_NAME_PASSWORD + " = ?";
        String[] selectionArgs = { email, password };
        Cursor cursor = db.query(
                UserContract.UserEntry.TABLE_NAME,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
        int count = cursor.getCount();
        cursor.close();
        db.close();
        return count > 0;
    }



}

