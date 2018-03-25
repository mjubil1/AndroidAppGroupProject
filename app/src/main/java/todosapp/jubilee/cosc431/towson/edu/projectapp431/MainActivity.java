package todosapp.jubilee.cosc431.towson.edu.projectapp431;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final int ADD_EXPENSE_CODE = 100;
    private RecyclerView recyclerView;
    ArrayList<Expense> expenseList;

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
        expenseList = new ArrayList<>();

        //1. Instantiate adapter
        ExpenseAdapter adapter = new ExpenseAdapter(expenseList);
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
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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

                //add expense to list
                expenseList.add(expense);

                ExpenseAdapter expenseAdapter = new ExpenseAdapter(expenseList);
                recyclerView.setAdapter(expenseAdapter);
                expenseAdapter.notifyItemInserted(expenseList.size());

                //todo: check expense for today's date and only display those with today's date
            }
        }
    }
}
