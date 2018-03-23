package todosapp.jubilee.cosc431.towson.edu.projectapp431;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;


public class ExpenseReport extends AppCompatActivity implements View.OnClickListener {

        private boolean permonth, peryear, perweek;
        private RadioButton weekly, monthly, yearly;
        private Button menu;
        private EditText et[];
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.expensereportlayout);
            weekly.findViewById(R.id.ExpenseWeekly);
            yearly.findViewById(R.id.ExpenseAnnual);
            monthly.findViewById(R.id.ExpenseMonthly);
            weekly.setOnClickListener(this);
            monthly.setOnClickListener(this);
            yearly.setOnClickListener(this);
            permonth = false;
            peryear = false;
            perweek = true;
            weekly.setChecked(true);
            yearly.setChecked(false);
            monthly.setChecked(false);
            menu.findViewById(R.id.ExpenseBacktohomescreen);
            menu.setOnClickListener(this);
            et[6].findViewById(R.id.ExpenseEditText6);
            et[5].findViewById(R.id.ExpenseEditText5);
            et[4].findViewById(R.id.ExpenseEditText4);
            et[3].findViewById(R.id.ExpenseEditText3);
            et[2].findViewById(R.id.ExpenseEditText2);
            et[1].findViewById(R.id.ExpenseEditText1);
            et[0].findViewById(R.id.ExpenseEditText);
            //et[0].isInEditMode(); is it editable?
            calculate();
        }
        private void calculate(){
            Intent intent=this.getIntent();




        }




        @Override
        public void onClick(View view) {
            if (view.getId() == monthly.getId()) {
                monthly.setChecked(true);
                yearly.setChecked(false);
                weekly.setChecked(false);
                permonth = true;
                perweek = false;
                peryear = false;
                calculate();
            } else if (view.getId() == yearly.getId()) {
                yearly.setChecked(true);
                weekly.setChecked(false);
                monthly.setChecked(false);
                permonth = false;
                perweek = false;
                peryear = true;
                calculate();
            } else if (view.getId() == weekly.getId()) {

                weekly.setChecked(true);
                yearly.setChecked(false);
                monthly.setChecked(false);
                permonth = false;
                perweek = true;
                peryear = false;
                calculate();
            } else {
                Intent intent1 = new Intent();
                setResult(0, intent1);
                finish();
            }
        }
    }