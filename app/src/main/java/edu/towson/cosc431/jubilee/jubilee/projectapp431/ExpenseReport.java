package edu.towson.cosc431.jubilee.jubilee.projectapp431;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
public class ExpenseReport extends Fragment implements View.OnClickListener{


    private boolean permonth, peryear, perweek;
    private RadioButton weekly, monthly, yearly;
    private Button menu;
    private TextView et[]=new TextView[7];
    private double catamount=0;
    public ExpenseReport() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.expensereportlayout, container, false);
        weekly=view.findViewById(R.id.ExpenseWeekly);
        yearly=view.findViewById(R.id.ExpenseAnnual);
        monthly=view.findViewById(R.id.ExpenseMonthly);
        weekly.setOnClickListener(this);
        monthly.setOnClickListener(this);
        yearly.setOnClickListener(this);
        permonth = false;
        peryear = false;
        perweek = true;
        weekly.setChecked(true);
        yearly.setChecked(false);
        monthly.setChecked(false);
        //menu.findViewById(R.id.ExpenseBacktohomescreen);
        // menu.setOnClickListener(this);
        et[6]=view.findViewById(R.id.ExpenseEditText6);
        et[5]=view.findViewById(R.id.ExpenseEditText5);
        et[4]=view.findViewById(R.id.ExpenseEditText4);
        et[3]=view.findViewById(R.id.ExpenseEditText3);
        et[2]=view.findViewById(R.id.ExpenseEditText2);
        et[1]=view.findViewById(R.id.ExpenseEditText1);
        et[0]=view.findViewById(R.id.ExpenseEditText);
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

        return view;
    }

//needs to somehow return arraylist of current expenses
    private ArrayList<Expense> converttoexpense(){
        /*
        Intent intent=this.getIntent();
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
        */
    return new ArrayList<Expense>();}

    private ArrayList<Expense> next(ArrayList<Expense> exp){
        //find next category
        String cat="";
        ArrayList<String> categoriesused=new ArrayList<String>();
        for(int x=0;x<exp.size();x++){
            for(int y=0;y<categoriesused.size();y++){ if(!categoriesused.get(y).equals(exp.get(x).getCategory())){
                cat= exp.get(x).getCategory();
            }
            }
        }
        if(cat.equals("")){
            cat="!!!";
        }
        categoriesused.add(cat);
        ArrayList<Expense> stuffreturning=new ArrayList<Expense>();
        for(int x=0;x<exp.size();x++){
            if(exp.get(x).getCategory().equals(cat)){
                stuffreturning.add(exp.get(x));
            }
        }
        return stuffreturning;
    }

    private void calculate(){
        //converts from intent to expense
        ArrayList<Expense> exp=converttoexpense();

        //which textview is being used
        int tvusing=-1;
        boolean z=true;
        while(z){
            //finds all items for that category
            ArrayList<Expense> cat=next(exp);
            tvusing+=1;

            if(cat.isEmpty()==true||tvusing>=7){
                z=false;
                return;
            }

            else{
                //find specific time frame
                cat=narrowtospecifictime(cat);

                //find percentage
                double percentage=findpercentage(exp, cat);
                String text=cat.get(0).getCategory()+":\n";
                text+="Percentage Spent: "+percentage+"\n";
                text+="Amount Spent: "+catamount;
                et[tvusing].setText(text);
                et[tvusing].setVisibility(View.VISIBLE);
            }}
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
        //convert to numbers
        int month = Integer.parseInt(monthdate);
        int year = Integer.parseInt(yeardate);
        int day = Integer.parseInt(daydate);
        for (int x = 0; x < cat.size(); x++) {
            int catmonth = Integer.parseInt(date.substring(0, 2));
            int catyear = Integer.parseInt(date.substring(6, 8));
            int catday = Integer.parseInt(date.substring(3, 5));
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
        double expamount=0;//amount spent
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
        } else {
            Intent intent1 = new Intent();
           //should return to main
        }
    }




    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

}
