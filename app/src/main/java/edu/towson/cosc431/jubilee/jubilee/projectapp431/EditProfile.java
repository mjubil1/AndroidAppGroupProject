package edu.towson.cosc431.jubilee.jubilee.projectapp431;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Montrell on 3/25/2018.
 */

public class EditProfile extends Fragment implements AdapterView.OnItemSelectedListener {

    public static final String FIRST_NAME_KEY = "FIRST_NAME";
    public static final String LAST_NAME_KEY = "LAST_NAME";
    public static final String EMAIL_KEY = "EMAIL";
    public static final String ADDRESS_KEY = "ADDRESS";
    public static final String CITY_KEY = "CITY";

    //Widgets
    EditText firstNameEt;
    EditText lastNameEt;
    EditText emailEt;
    EditText addressEt;
    EditText cityEt;
    Spinner stateSpinner;
    Button updateBtn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.edit_profile,container,false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        firstNameEt = view.findViewById(R.id.firstNameEt);
        lastNameEt = view.findViewById(R.id.lastNameEt);
        emailEt = view.findViewById(R.id.emailEt);
        addressEt = view.findViewById(R.id.addressEt);
        cityEt = view.findViewById(R.id.cityEt);
        stateSpinner = view.findViewById(R.id.state_spinner);
        updateBtn = view.findViewById(R.id.updateBtn);

        //from android developer page on using spinners
        ArrayAdapter<CharSequence> spinAdapter = ArrayAdapter.createFromResource(getActivity(),
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
                String emailTxt = stateSpinner.getSelectedItem().toString();
                String addressTxt = addressEt.getText().toString();
                String cityTxt = cityEt.getText().toString();

                //make new intent
                Intent editProfileIntent = new Intent();
                editProfileIntent.putExtra(FIRST_NAME_KEY, fNameTxt);
                editProfileIntent.putExtra(LAST_NAME_KEY, lNameTxt);
                editProfileIntent.putExtra(EMAIL_KEY, emailTxt);
                editProfileIntent.putExtra(ADDRESS_KEY, addressTxt);
                editProfileIntent.putExtra(CITY_KEY, cityTxt);
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(getContext(),text,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
