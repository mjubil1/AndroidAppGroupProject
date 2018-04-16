package edu.towson.cosc431.jubilee.jubilee.projectapp431.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;
import java.util.UUID;

import edu.towson.cosc431.jubilee.jubilee.projectapp431.Expense;

/**
 * Created by Montrell on 4/16/2018.
 */

public class ExpenseDataStore implements IDataStore {

    private ExpenseDatabase helper;

    public ExpenseDataStore(Context ctx) {
        helper = new ExpenseDatabase(ctx);
    }

    @Override
    public List<Expense> getExpense() {
        return null;
    }

    @Override
    public void addExpense(Expense expense) {
        SQLiteDatabase db = this.helper.getWritableDatabase();
        ContentValues cv = intoContentValues(expense);
        db.insert(DatabaseContract.TABLE_NAME, null, cv);
    }

    @Override
    public void deleteExpense(Expense expense) {
        ContentValues contentValues = intoContentValues(expense);
        contentValues.put(DatabaseContract.DELETED_COLUMN, true); // deleted!
        SQLiteDatabase db = helper.getWritableDatabase();
        db.update(DatabaseContract.TABLE_NAME, contentValues,
                DatabaseContract._ID + " = ?", // the where clause
                new String[]{expense.getId()} // value of the where clause
        );
    }

    @Override
    public void updateExpense(Expense expense) {
        ContentValues contentValues = intoContentValues(expense);
        SQLiteDatabase db = helper.getWritableDatabase();
        db.update(DatabaseContract.TABLE_NAME, contentValues,
                DatabaseContract._ID + " = ?", // the where clause
                new String[]{expense.getId()} // value of the where clause
        );
    }

    @Override
    public Expense getExpenseById(UUID uuid) {
        SQLiteDatabase db = this.helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + DatabaseContract.TABLE_NAME + " where "+
                DatabaseContract.DELETED_COLUMN+" = 0", null);
        Expense expense = null;
        while(cursor.moveToNext()) {
            String id = cursor.getString(cursor.getColumnIndex(DatabaseContract._ID));

            if(id.equals(uuid.toString())) {
                expense = new Expense();
                String name = cursor.getString(cursor.getColumnIndex(DatabaseContract.NAME_COLUMN));
                String spent = cursor.getString(cursor.getColumnIndex(DatabaseContract.SPENT_COLUMN));
                String category = cursor.getString(cursor.getColumnIndex(DatabaseContract.CATEGORY_COLUMN));
                String date = cursor.getString(cursor.getColumnIndex(DatabaseContract.DATE_COLUMN));
                expense.setId(id);
                expense.setName(name);
                expense.setAmount(spent);
                expense.setCategory(category);
                expense.setDateSpent(date);
            }
        }
        return expense;
    }

    // Creates a ContentValue object from the properties of the Song object
    private ContentValues intoContentValues(Expense expense) {
        ContentValues cv = new ContentValues();
        cv.put(DatabaseContract._ID, expense.getId());
        cv.put(DatabaseContract.NAME_COLUMN, expense.getName());
        cv.put(DatabaseContract.SPENT_COLUMN, expense.getAmount());
        cv.put(DatabaseContract.CATEGORY_COLUMN, expense.getCategory());
        cv.put(DatabaseContract.DATE_COLUMN, expense.getDateSpent());
        return cv;
    }
}