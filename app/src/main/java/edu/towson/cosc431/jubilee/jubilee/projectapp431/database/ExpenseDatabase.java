package edu.towson.cosc431.jubilee.jubilee.projectapp431.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Montrell on 4/16/2018.
 */

public class ExpenseDatabase extends SQLiteOpenHelper {


    private static final String DBNAME = "expense.db";
    private static final int VERSION = 1;

    private static final String CREATE_TABLE =
            "create table " + DatabaseContract.TABLE_NAME + " ( " +
                    DatabaseContract._ID + " text primary key, " +
                    DatabaseContract.NAME_COLUMN + " text, " +
                    DatabaseContract.SPENT_COLUMN + " real, " +
                    DatabaseContract.CATEGORY_COLUMN + " text," +
                    DatabaseContract.DATE_COLUMN + " text, " +
                    DatabaseContract.DELETED_COLUMN + " integer default 0);";

    public ExpenseDatabase(Context context) {
        super(context, DBNAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table " + DatabaseContract.TABLE_NAME);
        db.execSQL(CREATE_TABLE);
    }
}
