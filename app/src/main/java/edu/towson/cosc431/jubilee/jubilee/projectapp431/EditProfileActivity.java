package edu.towson.cosc431.jubilee.jubilee.projectapp431;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Created by Montrell on 3/25/2018.
 */

public class EditProfileActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final String TAG = EditProfileActivity.class.getName();

    public static final String FIRST_NAME_KEY = "FIRST_NAME";
    public static final String LAST_NAME_KEY = "LAST_NAME";
    public static final String EMAIL_KEY = "EMAIL";
    public static final String ADDRESS_KEY = "ADDRESS";
    public static final String CITY_KEY = "CITY";
    public static final String STATE_KEY = "STATE";

    //Widgets
    EditText firstNameEt;
    EditText lastNameEt;
    EditText emailEt;
    EditText addressEt;
    EditText cityEt;
    Spinner stateSpinner;
    Button updateBtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        Log.d(TAG,"onCreate");

        firstNameEt = findViewById(R.id.firstNameEt);
        lastNameEt = findViewById(R.id.lastNameEt);
        emailEt = findViewById(R.id.emailEt);
        addressEt = findViewById(R.id.addressEt);
        cityEt = findViewById(R.id.cityEt);
        stateSpinner = findViewById(R.id.state_spinner);
        updateBtn = findViewById(R.id.updateBtn);

        //from android developer page on using spinners
        ArrayAdapter<CharSequence> spinAdapter = ArrayAdapter.createFromResource(this,
                R.array.state_array, android.R.layout.simple_spinner_item);
        spinAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        stateSpinner.setAdapter(spinAdapter);
        stateSpinner.setOnItemSelectedListener(this);

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //get the values
                String fNameTxt = firstNameEt.getText().toString();
                String lNameTxt = lastNameEt.getText().toString();
                String emailTxt = emailEt.getText().toString();
                String addressTxt = addressEt.getText().toString();
                String cityTxt = cityEt.getText().toString();
                String stateTxt = stateSpinner.getSelectedItem().toString();

                //make new intent
                Intent editProfileIntent = new Intent(getApplicationContext(),MainActivity.class);
                editProfileIntent.putExtra(FIRST_NAME_KEY, fNameTxt);
                editProfileIntent.putExtra(LAST_NAME_KEY, lNameTxt);
                editProfileIntent.putExtra(EMAIL_KEY, emailTxt);
                editProfileIntent.putExtra(ADDRESS_KEY, addressTxt);
                editProfileIntent.putExtra(CITY_KEY, cityTxt);
                editProfileIntent.putExtra(STATE_KEY,stateTxt);

             //   setResult(RESULT_OK,editProfileIntent);
                finish();
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
