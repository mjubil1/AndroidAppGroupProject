package edu.towson.cosc431.jubilee.jubilee.projectapp431;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import edu.towson.cosc431.jubilee.jubilee.projectapp431.Expense;
import edu.towson.cosc431.jubilee.jubilee.projectapp431.R;

/**
 * Created by Rachael on 3/24/2018.
 */

public class ExpenseViewHolder extends RecyclerView.ViewHolder{

    Expense expense;
    TextView expenseNameTv;
    TextView expenseAmountTv;

    public ExpenseViewHolder(View view) {
        super(view);
        expenseNameTv = view.findViewById(R.id.expenseNameTv);
        expenseAmountTv = view.findViewById(R.id.expenseAmountTv);
    }

    public void bindExpenseToView(Expense expense) {
        this.expense = expense;
        expenseNameTv.setText(expense.getName());
        expenseAmountTv.setText(expense.getAmount());
    }
}
