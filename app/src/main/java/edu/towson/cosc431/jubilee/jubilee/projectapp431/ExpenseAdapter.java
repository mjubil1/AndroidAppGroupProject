package edu.towson.cosc431.jubilee.jubilee.projectapp431;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import edu.towson.cosc431.jubilee.jubilee.projectapp431.R;

/**
 * Created by Rachael on 3/24/2018.
 */

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseViewHolder> {

    List<Expense> expenseList;

    public ExpenseAdapter(ArrayList<Expense> expenseList) {
        this.expenseList = expenseList;
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
}
