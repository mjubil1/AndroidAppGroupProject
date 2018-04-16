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
                String savingsGoalTxt = savingsGoal.getText().toString();
                String monthlyIncomeTxt = monthlyIncome.getText().toString();
                String monthlyBillsTxt = monthlyBills.getText().toString();

                // TODO: use this info to calculate budget
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
