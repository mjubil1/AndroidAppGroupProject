package edu.towson.cosc431.jubilee.jubilee.projectapp431.database;

import android.provider.BaseColumns;

/**
 * Created by Montrell on 4/16/2018.
 */

public class DatabaseContract implements BaseColumns {
    public static final String TABLE_NAME = "Expense";
    public static final String _ID = "id";
    public static final String NAME_COLUMN = "name";
    public static final String SPENT_COLUMN = "spent";
    public static final String CATEGORY_COLUMN = "category";
    public static final String DATE_COLUMN = "date";
    public static final String DELETED_COLUMN = "deleted";
}