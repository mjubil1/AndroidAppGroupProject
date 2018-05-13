package edu.towson.cosc431.jubilee.jubilee.projectapp431;

import android.content.Intent;
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

    private static final int UPDATE_PROFILE_CODE  = 100;
    private static final String TAG = ProfileSettingsActivity.class.getName();

    TextView fNameTv;
    TextView lNameTv;
    TextView emailTv;
    TextView addressTv;
    TextView cityTv;
    TextView stateTv;
    Button goHomeBtn;

    int requestCode = 0;

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

        goHomeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent profileSettingsIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(profileSettingsIntent);
            }
        });
    }

    @Override
   protected void onActivityResult(int requestCode, int resultCode, Intent data) {
       super.onActivityResult(requestCode, resultCode, data);
       Log.d(TAG,"In activity result");

       if(requestCode == UPDATE_PROFILE_CODE) {
           if(resultCode == RESULT_OK) {
                try {
                    String firstName = data.getStringExtra(EditProfileActivity.FIRST_NAME_KEY);
                    String lastName = data.getStringExtra(EditProfileActivity.LAST_NAME_KEY);
                    String email = data.getStringExtra(EditProfileActivity.EMAIL_KEY);
                    String address = data.getStringExtra(EditProfileActivity.ADDRESS_KEY);
                    String city = data.getStringExtra(EditProfileActivity.CITY_KEY);
                    String state = data.getStringExtra(EditProfileActivity.STATE_KEY);

                    fNameTv.setText(firstName);
                    lNameTv.setText(lastName);
                    emailTv.setText(email);
                    addressTv.setText(address);
                    cityTv.setText(city);
                    stateTv.setText(state);

                    setResult(requestCode);
                    finish();
                }catch(Exception e) {
                    e.printStackTrace();
                }
           }
       }
   }
}
