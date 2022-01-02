package com.example.internship;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.internship.config.Config;

public class Home2Activity extends AppCompatActivity {

    Integer id, access;
    Button logout, intern, project, addIntern, addProject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home2);

        Intent x = getIntent();
        id = x.getIntExtra("id", 0);
        access = x.getIntExtra("access", 0);

        logout = findViewById(R.id.logout);
        intern = findViewById(R.id.intern);
        project = findViewById(R.id.project);
        addIntern = findViewById(R.id.btn_addIntern);
        addProject = findViewById(R.id.btn_addProject);

        logout.setOnClickListener(view -> {
            Intent intent = new Intent(Home2Activity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });

        intern.setOnClickListener(view -> {
            Intent intent = new Intent(Home2Activity.this, Home1Activity.class);
            intent.putExtra("id", id);
            intent.putExtra("access", access);
            startActivity(intent);
            finish();
        });

        project.setOnClickListener(view -> {
            Intent intent = new Intent(Home2Activity.this, HomeActivity.class);
            intent.putExtra("id", id);
            intent.putExtra("access", access);
            startActivity(intent);
            finish();
        });

        addIntern.setOnClickListener(view -> {
            Intent intent = new Intent(Home2Activity.this, RegisterActivity.class);
            intent.putExtra("id", id);
            intent.putExtra("access", access);
            startActivity(intent);
        });

        addProject.setOnClickListener(view -> {
            Intent intent = new Intent(Home2Activity.this, RegisterProject.class);
            intent.putExtra("id", id);
            intent.putExtra("access", access);
            startActivity(intent);
        });
    }
}