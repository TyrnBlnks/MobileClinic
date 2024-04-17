package com.example.icebear;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Intent;
import android.app.Activity;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Login extends AppCompatActivity {

    private EditText email;
    private EditText pass;
    private UserDbHelper dbHelper;  // Database helper

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        dbHelper = new UserDbHelper(this);  // Initialize the database helper

        // Initialize the EditText fields
        email = findViewById(R.id.email_input);
        pass = findViewById(R.id.password_input);

        // Setup click listeners and other initializations as before

        findViewById(R.id.login_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performLogin(email.getText().toString(), pass.getText().toString());
            }
        });
    }

    private void performLogin(String email, String password) {
        if (areCredentialsValid(email, password)) {
            Intent intent = new Intent(Login.this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(Login.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean areCredentialsValid(String email, String password) {
        return dbHelper.checkUser(email, password);
    }
}
