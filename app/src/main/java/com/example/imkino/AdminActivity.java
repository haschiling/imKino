package com.example.imkino;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AdminActivity extends AppCompatActivity {
    private Button list;
    private Button addFilm;
    private Button permission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        list = (Button) findViewById(R.id.buttonOpenAdminList);
        list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAdminListActivity();
            }
        });

        addFilm = (Button) findViewById(R.id.buttonAddFilm);
        addFilm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddFilmActivity();
            }
        });

        permission = (Button) findViewById(R.id.buttonPermission);
        permission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPermissionActivity();
            }
        });

    }
    public void openAdminListActivity(){
        Intent intent = new Intent(this,AdminListActivity.class);
        startActivity(intent);
    }
    public void openAddFilmActivity(){
        Intent intent = new Intent(this,AddFilmAdminActivity.class);
        startActivity(intent);
    }
    public void openPermissionActivity(){
        Intent intent = new Intent(this,PermissionActivity.class);
        startActivity(intent);
    }

}