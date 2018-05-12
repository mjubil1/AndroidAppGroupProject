package edu.towson.cosc431.jubilee.jubilee.projectapp431;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import edu.towson.cosc431.jubilee.jubilee.projectapp431.R;
import edu.towson.cosc431.jubilee.jubilee.projectapp431.database.ExpenseDataStore;
import edu.towson.cosc431.jubilee.jubilee.projectapp431.database.IDataStore;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final int ADD_EXPENSE_CODE = 100;
    private static final int SAVING_PROFILE_CODE = 200;
    private RecyclerView recyclerView;
    private TextView allocationTv;
    ArrayList<Expense> expenseList;
    ExpenseDataStore dataStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), NewExpenseActivity.class);
                startActivityForResult(intent, ADD_EXPENSE_CODE);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        getIntent();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        dataStore = new ExpenseDataStore(this);

        //1. Instantiate adapter
        ExpenseAdapter adapter = new ExpenseAdapter(dataStore);
        //2. Set the adapter on the recyclerView
        recyclerView.setAdapter(adapter);
        //3. Set the layout manager
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        switch (id){
            case R.id.nav_userProfile:
                //Intent intent = new Intent(getApplicationContext(), EditProfile.class);
                //startActivity(intent);

                //class extends fragment, not AppCompatActivity so not declared in manifest
                setContentView(R.layout.edit_profile);
                break;
            case R.id.nav_expenseReport:
                //ExpenseReport er=new ExpenseReport();
                //er.setArguments(ExpenseReport());

                Fragment fragment = new Fragment();

                FragmentTransaction trans=getSupportFragmentManager().beginTransaction();
                trans.replace(R.id.frame, fragment, "expensereportlayout");
                trans.addToBackStack(null);
                trans.commit();
                //setContentView(R.layout.expensereportlayout);
                break;
            case R.id.nav_savingsProfile:
                Intent intent = new Intent(getApplicationContext(), SavingsProfile.class);
                startActivityForResult(intent, SAVING_PROFILE_CODE);
                drawer.closeDrawer(GravityCompat.START);
                break;
            case R.id.nav_home:
                setContentView(R.layout.activity_main);
                break;
        }

        /*if(id == R.id.nav_userProfile) {

            Fragment fragment = new Fragment();
            FragmentTransaction ft= getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.frame,fragment,"edit_profile");
            ft.commit();
        }

        /*DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);*/
        return true;
    }
    private Bundle ExpenseReport(){
        List<Expense> list;
        try {
            list = dataStore.getExpenses();
        }
        catch(Exception e){
            list=new ArrayList<Expense>();
            list.add(new Expense("hello", "Fries", "7.00", "09-25"));
        }
        Bundle bun=new Bundle();
        int expenses=list.size();
        ArrayList<String> category=new ArrayList<String>();
        ArrayList<String>  amount=new ArrayList<String>();
        ArrayList<String> name=new ArrayList<String>();
        ArrayList<String> date=new ArrayList<String>();

        for(int x=0;x<expenses;x++){
            date.add(x,list.get(x).getDateSpent());
            name.add(x,list.get(x).getName());
            amount.add(x,list.get(x).getAmount());
            category.add(x,list.get(x).getCategory());
        }

        bun.putStringArrayList("date",date);
        bun.putStringArrayList("name",name);
        bun.putStringArrayList("amount",amount);
        bun.putStringArrayList("category",category);
        bun.putInt("expenses", expenses);
        return bun;

    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_EXPENSE_CODE){
            if (resultCode == RESULT_OK){
                //handle expense
                String nameTxt = data.getStringExtra(NewExpenseActivity.EXPENSE_NAME_KEY);
                String amountTxt = "$" + data.getStringExtra(NewExpenseActivity.EXPENSE_AMOUNT_KEY);
                String categoryTxt = data.getStringExtra(NewExpenseActivity.EXPENSE_CATEGORY_KEY);
                String dateTxt = data.getStringExtra(NewExpenseActivity.EXPENSE_DATE_KEY);

                //make expense object
                Expense expense = new Expense(nameTxt,categoryTxt,amountTxt,dateTxt);

                //add expense to dataStore
                dataStore = new ExpenseDataStore(this);
                dataStore.addExpense(expense);
                ExpenseAdapter adapter = new ExpenseAdapter(dataStore);
                recyclerView.setAdapter(adapter);
                Log.d("did I get the expense????", expense.toString());
                //I did get the expense

                adapter.notifyDataSetChanged();
            }
        }
        if (requestCode == SAVING_PROFILE_CODE) {
            if (resultCode == RESULT_OK){
                //get data from intent
                Double savingsGoal = Double.parseDouble(data.getStringExtra(SavingsProfile.SAVING_GOAL_KEY));
                Double income = Double.parseDouble(data.getStringExtra(SavingsProfile.INCOME_KEY));
                Double bills = Double.parseDouble(data.getStringExtra(SavingsProfile.BILLS_KEY));

                //calculate daily allocation
                Double availableMoney = income - bills - savingsGoal;
                Calendar cal = Calendar.getInstance();
                int totalDays = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
                Double dailyAlloc = availableMoney/totalDays;

                //set daily alloc to textView
                allocationTv = findViewById(R.id.allocationTv);
                allocationTv.setText("$ " + String.format("%.2f", dailyAlloc));
            }
        }
    }
}
