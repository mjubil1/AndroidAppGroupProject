package edu.towson.cosc431.jubilee.jubilee.projectapp431;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Rachael on 3/24/2018.
 */

public class NewExpenseActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    public static final String EXPENSE_NAME_KEY = "EXPENSE_NAME";
    public static final String EXPENSE_AMOUNT_KEY = "EXPENSE_AMOUNT";
    public static final String EXPENSE_CATEGORY_KEY = "EXPENSE_CATEGORY";
    public static final String EXPENSE_DATE_KEY = "EXPENSE_DATE";
    EditText expenseNameEt;
    EditText expenseAmountEt;
    Spinner categorySpinner;
    EditText expenseDateEt;
    Button expenseSaveBtn;
    Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);

        expenseNameEt =  findViewById(R.id.expenseNameEt);
        expenseAmountEt = findViewById(R.id.expenseAmountEt);
        categorySpinner = findViewById(R.id.categorySpinner);
        expenseDateEt = findViewById(R.id.expenseDateEt);
        expenseSaveBtn = findViewById(R.id.expenseSaveBtn);

        //from android developer page on using spinners
        ArrayAdapter<CharSequence> spinAdapter = ArrayAdapter.createFromResource(this,
                R.array.category_array, android.R.layout.simple_spinner_item);
        spinAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(spinAdapter);
        categorySpinner.setOnItemSelectedListener(this);

        //now for the calender
        calendar = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDateText();
            }
        };

        //when you click on the editText the calender pops up!
        expenseDateEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(v.getContext(), dateSetListener, calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        //and of course the save button
        expenseSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get the values
                String nameTxt = expenseNameEt.getText().toString();
                String amountTxt = expenseAmountEt.getText().toString();
                String categoryTxt = categorySpinner.getSelectedItem().toString();
                String dateTxt = expenseDateEt.getText().toString();

                //make new intent
                Intent expenseIntent = new Intent();
                expenseIntent.putExtra(EXPENSE_NAME_KEY, nameTxt);
                expenseIntent.putExtra(EXPENSE_AMOUNT_KEY, amountTxt);
                expenseIntent.putExtra(EXPENSE_CATEGORY_KEY, categoryTxt);
                expenseIntent.putExtra(EXPENSE_DATE_KEY, dateTxt);

                //set result and finish activity
                setResult(RESULT_OK, expenseIntent);
                finish();
            }
        });
    }

    private void updateDateText() {
        String dateFormat = "MM/dd/yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat, Locale.US);
        expenseDateEt.setText(simpleDateFormat.format(calendar.getTime()));
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        parent.getItemAtPosition(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
