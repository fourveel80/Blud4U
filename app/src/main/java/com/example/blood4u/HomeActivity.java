package com.example.blood4u;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Button button = findViewById(R.id.dwb);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, MainActivity2.class);
                startActivity(intent);
            }
        });
    }
    //bottomnavigationbar copy start from this line
    BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.navigation_home:
                            //Intent value change Intent(CurrentActivity.this,HomeActivity.this)
                            Intent home_intent = new Intent(HomeActivity.this,HomeActivity.class);
                            startActivity(home_intent);
                            break;
                        case R.id.navigation_setting:
                            //Intent value change Intent(CurrentActivity.this,Activity.this)
                            Intent search_intent = new Intent(HomeActivity.this, HomeActivity.class);
                            startActivity(search_intent);
                            break;
                        case R.id.navigation_Profile:
                            Intent profile_intent = new Intent(HomeActivity.this, HomeActivity.class);
                            startActivity(profile_intent);
                            break;

                        // unfinished
                    }
                    return false;
                }
            };
    //bottomnavigationbar copy finish at this line

}