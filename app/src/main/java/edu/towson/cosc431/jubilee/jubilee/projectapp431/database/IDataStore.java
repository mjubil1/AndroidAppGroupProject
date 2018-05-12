package edu.towson.cosc431.jubilee.jubilee.projectapp431.database;

import java.util.List;
import java.util.UUID;

import edu.towson.cosc431.jubilee.jubilee.projectapp431.Expense;

/**
 * Created by Montrell on 4/16/2018.
 */

public interface IDataStore {
    List<Expense> getExpenses();
    void addExpense(Expense expense);
    void deleteExpense(Expense expense);
    void updateExpense(Expense expense);
    Expense getExpenseById(UUID uuid);
    List<Expense> getTodayExpenses(String today);
}
