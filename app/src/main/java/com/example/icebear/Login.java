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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        // Initialize the EditText fields
        email = (EditText) findViewById(R.id.email_input);
        pass = (EditText) findViewById(R.id.password_input);

        // Set padding based on system bars insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Set up the button click listener for login
        findViewById(R.id.login_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Call the method to handle login when button is clicked
                performLogin(email.getText().toString(), pass.getText().toString());
            }
        });

        // Set up the button click listener for sign up
        Button signUpButton = findViewById(R.id.signup_btn);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent to open the Register activity
                Intent intent = new Intent(Login.this, Register.class);
                startActivity(intent);
            }
        });

    }


    private void performLogin(String email, String password) {
        if (areCredentialsValid(email, password)) {
            // Navigate to MainActivity
            Intent intent = new Intent(Login.this, MainActivity.class);
            startActivity(intent);
            finish();  // Close the login activity so it's not accessible via the back button
        } else {
            // Show error message for incorrect credentials
            Toast.makeText(Login.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean areCredentialsValid(String email, String password) {
        // Hardcoded valid credentials
        return email.equals("tyrnblnks2@gmail.com") && password.equals("12345678");
    }
}
