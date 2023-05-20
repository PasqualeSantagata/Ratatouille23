package com.example.springclient.view;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.springclient.R;

public class PasswordDimenticata extends AppCompatActivity {

    private EditText editTextEmail;
    private Button buttonOk;
    private Button buttonIndietro;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_password_dimenticata);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        initializeComponents();

    }


    private void initializeComponents(){
        editTextEmail = findViewById(R.id.textInputLayoutEmailPassDimenticata);

    }
}
