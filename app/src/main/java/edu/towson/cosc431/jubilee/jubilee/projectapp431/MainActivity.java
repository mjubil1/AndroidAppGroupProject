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

import java.util.ArrayList;

import edu.towson.cosc431.jubilee.jubilee.projectapp431.R;
import edu.towson.cosc431.jubilee.jubilee.projectapp431.database.ExpenseDataStore;
import edu.towson.cosc431.jubilee.jubilee.projectapp431.database.IDataStore;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final int ADD_EXPENSE_CODE = 100;
    private RecyclerView recyclerView;
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

        switch (id){
            case R.id.nav_userProfile:
                //Intent intent = new Intent(getApplicationContext(), EditProfile.class);
                //startActivity(intent);

                //class extends fragment, not AppCompatActivity so not declared in manifest
                setContentView(R.layout.edit_profile);
                break;
            case R.id.nav_expenseReport:

                //ExpenseReport();
                //needs to be passed a list of the current expenses somehow?
                setContentView(R.layout.expensereportlayout);
                break;
            case R.id.nav_savingsProfile:
                Intent intent = new Intent(getApplicationContext(), SavingsProfile.class);
                startActivity(intent);
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
   /* private void ExpenseReport(){
        expenseList=new ArrayList<Expense>();
        expenseList.add(new Expense("LOL", "2.76", "Hi", "7/8/17"));
        Intent intent = new Intent(this, ExpenseReport.class);
        int expenses=expenseList.size();
        ArrayList<String> category=new ArrayList<String>();
        ArrayList<String>  amount=new ArrayList<String>();
        ArrayList<String> name=new ArrayList<String>();
        ArrayList<String> date=new ArrayList<String>();

        for(int x=0;x<expenses;x++){
            date.add(x,expenseList.get(x).getDateSpent());
            name.add(x,expenseList.get(x).getName());
            amount.add(x,expenseList.get(x).getAmount());
            category.add(x,expenseList.get(x).getCategory());
        }
        intent.putStringArrayListExtra("date",date);
        intent.putStringArrayListExtra("name",name);
        intent.putStringArrayListExtra("amount",amount);
        intent.putStringArrayListExtra("category",category);
        intent.putExtra("expenses", expenses);
        startActivityForResult(intent, 50);

    }*/
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
                dataStore.addExpense(expense);

                dataStore = new ExpenseDataStore(this);
                recyclerView.setAdapter(new ExpenseAdapter(dataStore));
                Log.d("did I get the expense????", expense.toString());
                //I did get the expense

            }
        }
    }
}
