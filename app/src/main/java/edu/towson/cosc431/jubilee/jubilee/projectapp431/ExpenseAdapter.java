package edu.towson.cosc431.jubilee.jubilee.projectapp431;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import edu.towson.cosc431.jubilee.jubilee.projectapp431.R;
import edu.towson.cosc431.jubilee.jubilee.projectapp431.database.IDataStore;

/**
 * Created by Rachael on 3/24/2018.
 */

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseViewHolder> {

    List<Expense> expenseList;
    IDataStore dataStore;
    String dateFormat = "MM/dd/yyyy";
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat, Locale.US);
    String today = simpleDateFormat.format(Calendar.getInstance().getTime());

    public ExpenseAdapter(IDataStore dataStore)
    {
        this.expenseList = dataStore.getTodayExpenses(today);
        this.dataStore = dataStore;
    }

    @Override
    public ExpenseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.expense_view, parent, false);
        ExpenseViewHolder expenseViewHolder = new ExpenseViewHolder(view);
        return expenseViewHolder;
    }

    @Override
    public void onBindViewHolder(ExpenseViewHolder holder, int position) {
        Expense expense = this.expenseList.get(position);
        holder.bindExpenseToView(expense);
    }

    @Override
    public int getItemCount() {
        return this.expenseList.size();
    }

    public void deleteExpense(Expense expense) {
        dataStore.deleteExpense(expense); //delete from db
        expenseList = dataStore.getTodayExpenses(today); // re-read all the expense from the db
        notifyDataSetChanged(); // recyclerview update yourself!
    }
}
