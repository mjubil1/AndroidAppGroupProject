package edu.towson.cosc431.jubilee.jubilee.projectapp431;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Rachael on 4/16/2018.
 */

public class SavingsProfile extends AppCompatActivity{

    public static final String SAVING_GOAL_KEY = "SAVING_GOAL";
    public static final String INCOME_KEY = "INCOME";
    public static final String BILLS_KEY = "BILLS";
    //widgets
    EditText savingsGoal;
    EditText monthlyIncome;
    EditText monthlyBills;
    Button savingsHomeBtn;

    @Override
    protected void onCreate(Bundle SavedInstanceState) {
        super.onCreate(SavedInstanceState);
        setContentView(R.layout.savings_profile);

        savingsGoal = findViewById(R.id.savingsGoalEt);
        monthlyIncome = findViewById(R.id.incomeEt);
        monthlyBills = findViewById(R.id.billsEt);
        savingsHomeBtn = findViewById(R.id.savingsHomeBtn);
        savingsHomeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get values from edit text
                String savingsGoalTxt = savingsGoal.getText().toString();
                String monthlyIncomeTxt = monthlyIncome.getText().toString();
                String monthlyBillsTxt = monthlyBills.getText().toString();

                //make an intent with these values
                Intent resultIntent = new Intent();
                resultIntent.putExtra(SAVING_GOAL_KEY, savingsGoalTxt);
                resultIntent.putExtra(INCOME_KEY, monthlyIncomeTxt);
                resultIntent.putExtra(BILLS_KEY, monthlyBillsTxt);

                //set result and finish
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });
    }
}
