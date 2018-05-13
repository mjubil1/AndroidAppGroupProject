package edu.towson.cosc431.jubilee.jubilee.projectapp431;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import edu.towson.cosc431.jubilee.jubilee.projectapp431.database.ExpenseDataStore;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = MainActivity.class.getName();

    private static final int USER_CODE = 100;
    private static final int ADD_EXPENSE_CODE = 100;
    private static final int SAVING_PROFILE_CODE = 200;
    private RecyclerView recyclerView;
    private TextView allocationTv;
    ArrayList<Expense> expenseList;
    ExpenseDataStore dataStore;
    String allocation;
    Double dailyAlloc;
    Double userLimitInput;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Log.d(TAG,"OnCreate");

        allocationTv = (TextView) findViewById(R.id.allocationTv);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        allocation = prefs.getString("allocation", "Update Savings Profile");
        allocationTv.setText(allocation);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), NewExpenseActivity.class);
                startActivityForResult(intent, USER_CODE);
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
        int id = item.getItemId();

        //let user set their own spending limit
        if (id == R.id.action_settings) {
            final AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
            dialog.setTitle("Set Spending Limit");
            dialog.setMessage("This will override any calculated spending allocations and you may " +
                    "not reach your savings goals. Do you want to continue?");

            //user agrees to enter limit
            dialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    //create another dialog with editText
                    AlertDialog.Builder limit = new AlertDialog.Builder(MainActivity.this);
                    limit.setTitle("Enter Your Spending Limit");
                    final EditText input = new EditText(limit.getContext());
                    input.setInputType(InputType.TYPE_CLASS_NUMBER);
                    limit.setView(input);

                    //accept user input
                    limit.setPositiveButton("SAVE", new DialogInterface.OnClickListener() {
                        @TargetApi(Build.VERSION_CODES.GINGERBREAD)
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            userLimitInput = Double.parseDouble(input.getText().toString());

                            //subtract today's expenses if needed
                            String dateFormat = "MM/dd/yyyy";
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat, Locale.US);
                            String today = simpleDateFormat.format(Calendar.getInstance().getTime());
                            userLimitInput -= dataStore.sumTodayExpenses(today);

                            //notify if remaining allocation drops below zero
                            if (userLimitInput < 0) {
                                Toast.makeText(MainActivity.this, "You've exceeded your daily spending limit!",
                                        Toast.LENGTH_LONG).show();
                                //put intentService showNotification() here!!!!!
                            }

                            //set user spending limit to textView
                            allocation = "$ " + String.format("%.2f", userLimitInput);
                            allocationTv.setText(allocation);

                            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                            SharedPreferences.Editor editor = prefs.edit();
                            editor.putString("allocation", allocation).apply();

                            dialogInterface.dismiss();
                        }
                    });

                    //cancel user input
                    limit.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
                    limit.show();
                    dialogInterface.dismiss();
                }
            });

            //user chooses not to enter their own limit
            dialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
            dialog.show();
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
            case R.id.nav_editProfile:
                intent = new Intent(MainActivity.this, EditProfileActivity.class);
                startActivityForResult(intent,USER_CODE);
                break;
            case R.id.nav_expenseReport:
                intent=ExpenseReport();
                startActivity(intent);
                break;
            case R.id.nav_savingsProfile:
                intent = new Intent(getApplicationContext(), SavingsProfile.class);
                startActivityForResult(intent, SAVING_PROFILE_CODE);
                drawer.closeDrawer(GravityCompat.START);
                break;
            case R.id.nav_userProfile:
                intent = new Intent(MainActivity.this, ProfileSettingsActivity.class);
                startActivityForResult(intent,USER_CODE);
                break;
            case R.id.nav_linkCard:
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://play.google.com/store/search?q=google%20pay&c=apps&hl=en"));
                startActivity(intent);
                break;
        }
        return true;
    }
    private Intent ExpenseReport(){
        intent = new Intent(MainActivity.this, ExpenseReport.class);
        List<Expense> list;

            list = dataStore.getExpenses();




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

        intent.putStringArrayListExtra("date",date);
        intent.putStringArrayListExtra("name",name);
        intent.putStringArrayListExtra("amount",amount);
        intent.putStringArrayListExtra("category",category);
        intent.putExtra("expenses", expenses);
        return intent;

    }

    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG,"In activity result");

        if (requestCode == USER_CODE){
            if (resultCode == RESULT_OK){
                //handle expense
                String nameTxt = data.getStringExtra(NewExpenseActivity.EXPENSE_NAME_KEY);
                String amountTxt = data.getStringExtra(NewExpenseActivity.EXPENSE_AMOUNT_KEY);
                String categoryTxt = data.getStringExtra(NewExpenseActivity.EXPENSE_CATEGORY_KEY);
                String dateTxt = data.getStringExtra(NewExpenseActivity.EXPENSE_DATE_KEY);

                String firstName = data.getStringExtra(EditProfileActivity.FIRST_NAME_KEY);
                String lastName = data.getStringExtra(EditProfileActivity.LAST_NAME_KEY);
                String email = data.getStringExtra(EditProfileActivity.EMAIL_KEY);
                String address = data.getStringExtra(EditProfileActivity.ADDRESS_KEY);
                String city = data.getStringExtra(EditProfileActivity.CITY_KEY);
                String state = data.getStringExtra(EditProfileActivity.STATE_KEY);

                //make expense object
                Expense expense = new Expense(nameTxt,categoryTxt,amountTxt,dateTxt);

                //add expense to dataStore
                dataStore = new ExpenseDataStore(this);
                dataStore.addExpense(expense);
                ExpenseAdapter adapter = new ExpenseAdapter(dataStore);
                recyclerView.setAdapter(adapter);
                Log.d(TAG,"did I get the expense???? " + expense.toString());
                //I did get the expense

                adapter.notifyDataSetChanged();

                //subtract today's expenses from allocation
                String alloc = allocationTv.getText().toString().substring(2);
                if (alloc.contentEquals("date Savings Profile")) {
                    Toast.makeText(MainActivity.this, "Please update savings profile!",
                            Toast.LENGTH_LONG).show();
                } else {
                    dailyAlloc = Double.parseDouble(alloc);
                    dailyAlloc -= Double.parseDouble(amountTxt);

                    //notify if dailyAlloc drops below zero
                    if (dailyAlloc < 0) {
                        Toast.makeText(MainActivity.this, "You've exceeded your daily spending limit!",
                                Toast.LENGTH_LONG).show();
                        //put intentService showNotification() here!!!!!!
                    }

                    //update allocationTV after new expense is added
                    allocation = "$ " + String.format("%.2f", dailyAlloc);
                    allocationTv.setText(allocation);

                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("allocation", allocation).apply();
                }
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
                dailyAlloc = availableMoney/totalDays;

                //subtract today's expenses if savings goals are edited
                String dateFormat = "MM/dd/yyyy";
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat, Locale.US);
                String today = simpleDateFormat.format(Calendar.getInstance().getTime());
                dailyAlloc -= dataStore.sumTodayExpenses(today);

                //notify if dailyAlloc drops below zero
                if (dailyAlloc < 0) {
                    Toast.makeText(MainActivity.this, "You've exceeded your daily spending limit!",
                            Toast.LENGTH_LONG).show();
                    //put intentService showNotification() here!!!!!
                }

                //set daily alloc to textView
                allocationTv = findViewById(R.id.allocationTv);
                allocation = "$ " + String.format("%.2f", dailyAlloc);
                allocationTv.setText(allocation);

                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("allocation", allocation).apply();
            }
        }
    }

}
