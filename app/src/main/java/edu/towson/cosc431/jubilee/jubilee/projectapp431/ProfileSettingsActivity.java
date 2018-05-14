package edu.towson.cosc431.jubilee.jubilee.projectapp431;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ProfileSettingsActivity extends AppCompatActivity {

    private static final String TAG = ProfileSettingsActivity.class.getName();

    TextView fNameTv;
    TextView lNameTv;
    TextView emailTv;
    TextView addressTv;
    TextView cityTv;
    TextView stateTv;
    Button goHomeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_settings);
        Log.d(TAG,"onCreate");

        getIntent();

        fNameTv = findViewById(R.id.fName);
        lNameTv = findViewById(R.id.lName);
        emailTv = findViewById(R.id.email);
        addressTv = findViewById(R.id.address);
        cityTv = findViewById(R.id.city);
        stateTv = findViewById(R.id.state);
        goHomeBtn = findViewById(R.id.goHomeBtn);

        SharedPreferences preferences = getSharedPreferences("User", MODE_PRIVATE);
        String firstName = preferences.getString("fName", "");
        String lastName = preferences.getString("lName", "");
        String email = preferences.getString("email", "");
        String address = preferences.getString("address", "");
        String city = preferences.getString("city", "");
        String state = preferences.getString("state", "");

        fNameTv.setText(firstName);
        lNameTv.setText(lastName);
        emailTv.setText(email);
        addressTv.setText(address);
        cityTv.setText(city);
        stateTv.setText(state);

        goHomeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profileSettingsIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(profileSettingsIntent);
            }
        });
    }
}
