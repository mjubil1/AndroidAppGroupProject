package edu.towson.cosc431.jubilee.jubilee.projectapp431;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import edu.towson.cosc431.jubilee.jubilee.projectapp431.R;
import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the

 * to handle interaction events.
 */
public class ExpenseReport extends AppCompatActivity implements View.OnClickListener{


    private boolean permonth, peryear, perweek;
    private RadioButton weekly, monthly, yearly;

    private TextView et[]=new TextView[7];
    private Double catamount=0.0;
    public ExpenseReport() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.expensereportlayout);

        weekly= findViewById(R.id.ExpenseWeekly);
        yearly= findViewById(R.id.ExpenseAnnual);
        monthly= findViewById(R.id.ExpenseMonthly);
        weekly.setOnClickListener(this);
        monthly.setOnClickListener(this);
        yearly.setOnClickListener(this);
        permonth = false;
        peryear = false;
        perweek = true;
        weekly.setChecked(false);
        yearly.setChecked(true);
        monthly.setChecked(false);
        //menu.findViewById(R.id.ExpenseBacktohomescreen);
        // menu.setOnClickListener(this);
        et[6]= findViewById(R.id.ExpenseEditText6);
        et[5]= findViewById(R.id.ExpenseEditText5);
        et[4]= findViewById(R.id.ExpenseEditText4);
        et[3]= findViewById(R.id.ExpenseEditText3);
        et[2]= findViewById(R.id.ExpenseEditText2);
        et[1]= findViewById(R.id.ExpenseEditText1);
        et[0]= findViewById(R.id.ExpenseEditText);
        et[0].setClickable(false);
        et[0].setVisibility(View.INVISIBLE);
        et[1].setClickable(false);
        et[1].setVisibility(View.INVISIBLE);
        et[2].setClickable(false);
        et[2].setVisibility(View.INVISIBLE);
        et[3].setClickable(false);
        et[3].setVisibility(View.INVISIBLE);
        et[4].setClickable(false);
        et[4].setVisibility(View.INVISIBLE);
        et[5].setClickable(false);
        et[5].setVisibility(View.INVISIBLE);
        et[6].setClickable(false);
        et[6].setVisibility(View.INVISIBLE);



        //et[0].isInEditMode(); is it editable?
        calculate();
        monthly.callOnClick();
    }


    private ArrayList<ArrayList<Expense>> next(ArrayList<Expense> exp){
        //find next category
        //looks for all categories
        ArrayList<ArrayList<Expense>> lists=new ArrayList<ArrayList<Expense>>();
        ArrayList<String> categories =new ArrayList<>();
        for(int x=0;x<exp.size();x++) {
            Expense e = exp.get(x);
            boolean isnew = true;
            for (int y = 0; y < categories.size(); y++) {
                String cat = categories.get(y);
                if (e.getCategory().equals(cat)) {
                    isnew = false;
                }
            }
            if (isnew) {
                categories.add(e.getCategory());
            }
        }
        //takes categories and separates expenses between them
        for(int x=0;x<categories.size();x++){
            String cat=categories.get(x);
            ArrayList<Expense> catlist=new ArrayList<Expense>();
            for(int y=0;y<exp.size();y++){
                Expense e=exp.get(y);
                if(cat.equals(e.getCategory())) {
                    catlist.add(e);
                }
            }
            lists.add(catlist);
        }

        return lists;
    }

    private void calculate(){
        //converts from intent to expense
        ArrayList<Expense> exp=converttoexpense();

//test
        exp.add(new Expense("Farting", "Fries", "1000.00", "09/18/15"));
        exp.add(new Expense("Fish", "Fries", "60.00", "09/08/11"));
        exp.add(new Expense("Float", "Fries", "7.07", "03/04/18"));
        exp.add(new Expense("On", "LALA", "100.00", "05/09/17"));
        exp.add(new Expense("Birthdays", "LALA", "10.00", "09/26/14"));
        exp.add(new Expense("Like", "Basketball", "9.00", "08/25/17"));
        exp.add(new Expense("Every", "Basketball", "42.00", "05/10/18"));
        exp.add(new Expense("day", "Personal Care", "11.00", "12/03/17"));
        exp.add(new Expense("hello?", "Personal Care", "86.50", "04/04/16"));
//

        ArrayList<ArrayList<Expense>> categories=next(exp);

        for(int x=0;x<categories.size();x++){
            ArrayList<Expense> cat=categories.get(x);
            //finds all items for that category

            String categoryname=cat.get(0).getCategory();



            //find specific time frame
            cat=narrowtospecifictime(cat);

            //find percentage
            double percentage=findpercentage(exp, cat);

            String text=categoryname+":\n";



            text+="Percentage Spent: "+percentage+"\n";
            text+="Amount Spent: "+catamount;
            et[x].setText(text);
            et[x].setVisibility(View.VISIBLE);

        }

    }
    private ArrayList<Expense> narrowtospecifictime(ArrayList<Expense> cat) {
        ArrayList<Expense> time = new ArrayList<Expense>();
        //get date

        DateFormat df = new SimpleDateFormat("MM/dd/yy");
        Date dateobj = new Date();
        String date = df.format(dateobj);
        String monthdate = date.substring(0, 2);
        String daydate = date.substring(3, 5);
        String yeardate = date.substring(6, 8);


        int month = Integer.parseInt(monthdate);
        int year = Integer.parseInt(yeardate);
        int day = Integer.parseInt(daydate);

        //convert to numbers

        for (int x = 0; x < cat.size(); x++) {
            Expense e=cat.get(x);
            int catmonth = Integer.parseInt(e.getDateSpent().substring(0, 2));
            int catyear = Integer.parseInt(e.getDateSpent().substring(6, 8));
            int catday = Integer.parseInt(e.getDateSpent().substring(3, 5));

            if (permonth && catmonth == month && catyear == year) {
                time.add(cat.get(x));
            } else if (peryear) {
                if (year == catyear || catmonth > month && catyear == year - 1) {
                    time.add(cat.get(x));
                }
            } else if (perweek && catyear == year) {
                //add weeks code!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

            }
        }
        return time;
    }

    private double findpercentage( ArrayList<Expense> exp, ArrayList<Expense> cat){
        Double expamount=0.0;//amount spent
        //exp
        for(int x=0;x<exp.size();x++){
            expamount+=Double.parseDouble(exp.get(x).getAmount());
        }
        //cat
        for(int x=0;x<cat.size();x++){
            catamount+=Double.parseDouble(cat.get(x).getAmount());
        }
        Log.d("CatAMOUNT LOL",catamount.toString() );
        Log.d("EXPAMOUNT LOL",expamount.toString());
        return catamount/expamount;
    }



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
            finish();
        }
    }



    //needs to somehow return arraylist of current expenses
    private ArrayList<Expense> converttoexpense(){

        Intent intent=getIntent();
        ArrayList<String> category=intent.getStringArrayListExtra("category");
        ArrayList<String> name=intent.getStringArrayListExtra("name");
        ArrayList<String> amount=intent.getStringArrayListExtra("amount");
        ArrayList<String> date=intent.getStringArrayListExtra("date");
        ArrayList <Expense> exp=new ArrayList<Expense>();
        int expenses=intent.getIntExtra("expenses",0);
        for(int x=0;x<expenses;x++){
            //String name, String category, String amount, String dateSpent
            Expense e=new Expense(name.get(x),category.get(x),amount.get(x),date.get(x));

            exp.add(e);
        }
        return exp;

    }


}
