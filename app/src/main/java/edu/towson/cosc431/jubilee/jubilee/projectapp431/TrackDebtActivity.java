package edu.towson.cosc431.jubilee.jubilee.projectapp431;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Rachael on 5/14/2018.
 */

public class TrackDebtActivity extends AppCompatActivity{

    Double currentDebt;
    Double inputNum;

    //widgets
    TextView debtTv;
    EditText enterDebtEt;
    Button debtBtn;
    Button paymentBtn;
    Button debtHomeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_debt);

        //find widget views
        debtTv = findViewById(R.id.debtTv);
        enterDebtEt = findViewById(R.id.enterDebtEt);
        debtBtn = findViewById(R.id.debtBtn);
        paymentBtn = findViewById(R.id.paymentBtn);
        debtHomeBtn = findViewById(R.id.debtHomeBtn);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        currentDebt = Double.parseDouble(prefs.getString("currentDebt", "0.0"));
        debtTv.setText("$ " + currentDebt.toString());
        inputNum = 0.0;

        //set onClick listeners
        debtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputNum = Double.parseDouble(enterDebtEt.getText().toString());
                currentDebt += inputNum;
                debtTv.setText("$ " + currentDebt.toString());
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("currentDebt", currentDebt.toString()).apply();
            }
        });
        paymentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputNum = Double.parseDouble(enterDebtEt.getText().toString());
                currentDebt -= inputNum;
                debtTv.setText("$ " + currentDebt.toString());
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("currentDebt", currentDebt.toString()).apply();
            }
        });
        debtHomeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TrackDebtActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
