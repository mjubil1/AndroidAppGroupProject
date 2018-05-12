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

public class ProfileSettingsActivity extends AppCompatActivity {

    private static final String TAG = ProfileSettingsActivity.class.getName();

    Button goHomeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_settings);
        Log.d(TAG,"onCreate");

        goHomeBtn = findViewById(R.id.goHomeBtn);

        goHomeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent profileSettingsIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(profileSettingsIntent);
            }
        });
    }
}
