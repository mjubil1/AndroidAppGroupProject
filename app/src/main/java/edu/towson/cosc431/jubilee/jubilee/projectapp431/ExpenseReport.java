package edu.towson.cosc431.jubilee.jubilee.projectapp431;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
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
    private double catamount=0.0;
    public ExpenseReport() { }
    private RadioButton exit, graph;
    private ArrayList<Float> amounts;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.expensereportlayout);
        graph=findViewById(R.id.graph);
        graph.setOnClickListener(this);
        exit=findViewById(R.id.exit);
        exit.setOnClickListener(this);
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
        graph.setVisibility(View.INVISIBLE);
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

//format : MM/DD/YY










        ArrayList<ArrayList<Expense>> categories=next(exp);
        amounts=new ArrayList<>();
        for(int x=0;x<categories.size();x++){
            ArrayList<Expense> cat=categories.get(x);
            //finds all items for that category

            String categoryname=cat.get(0).getCategory();



            //find specific time frame
            cat=narrowtospecifictime(cat);

            //find percentage
            double percentage=findpercentage(exp, cat);

            String text=categoryname+":\n";

            percentage*=100;
            int percent=(int)percentage;
            text+="Percentage Spent: "+percent+"%"+"\n";


            NumberFormat format = NumberFormat.getInstance();
            format.setRoundingMode(RoundingMode.DOWN);
            format.setMaximumFractionDigits(2);
            text+="Amount Spent: "+"$"+format.format(catamount);
            amounts.add((float)catamount);

            catamount=0.0;
            et[x].setText(text);
            et[x].setVisibility(View.VISIBLE);

        }

    }
    private ArrayList<Expense> narrowtospecifictime(ArrayList<Expense> cat) {
        ArrayList<Expense> time = new ArrayList<Expense>();
        //get date

        DateFormat df = new SimpleDateFormat("MM/dd/yy");
        Date today = new Date();
        String date = df.format(today);
        //disemble
        String monthdate = date.substring(0, 2);
        String daydate = date.substring(3, 5);
        String yeardate = date.substring(6, 8);
        Integer month = Integer.parseInt(monthdate);
        Integer year = Integer.parseInt(yeardate);
        Integer day = Integer.parseInt(daydate);

        LocalDate localtoday = LocalDate.of(year, month, day);

        //calculate last year

        Date lastyear = Date.from(localtoday.minusYears(1).atStartOfDay(ZoneId.systemDefault()).toInstant());
        //calculate last month

        Date lastmonth = Date.from(localtoday.minusMonths(1).atStartOfDay(ZoneId.systemDefault()).toInstant());
        //calculate last week
        //
        Date lastweek = Date.from(localtoday.minusWeeks(1).atStartOfDay(ZoneId.systemDefault()).toInstant());


        for (int x = 0; x < cat.size(); x++) {
            Expense e=cat.get(x);
            SimpleDateFormat f = new SimpleDateFormat("MM/dd/yyyy");
            Date d=new Date();
            try {
                d = f.parse(e.getDateSpent());
            } catch (ParseException e1) {
                e1.printStackTrace();
            }

            if (permonth && !(d.before(lastmonth) || d.after(today))) {
                time.add(cat.get(x));
            } else if (peryear&&!(d.before(lastyear) || d.after(today))) {

                time.add(cat.get(x));

            } else if (perweek && !(d.before(lastweek) || d.after(today))) {

                time.add(cat.get(x));
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
        }
        else if(view.getId() == graph.getId()) {
            graph.setChecked(false);
           /* Intent intent = new Intent(this, PietActivity.class);
            intent.putExtra("amounts", amounts.toArray());
            startActivityForResult(intent,0);*/




        } else {
            exit.setChecked(false);
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
