package com.example.icebear;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;

public class Register extends AppCompatActivity {

    private EditText firstNameInput;
    private EditText lastNameInput;
    private EditText emailInput;
    private EditText passwordInput;
    private EditText confirmPasswordInput;
    private Button createProfileButton;
    private UserDbHelper dbHelper;  // Database helper


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register); // Make sure this matches your XML file name

        dbHelper = new UserDbHelper(this);  // Initialize the database helper

        // Initialize UI components
        firstNameInput = findViewById(R.id.firstname_input);
        lastNameInput = findViewById(R.id.lastname_input);
        emailInput = findViewById(R.id.email);
        passwordInput = findViewById(R.id.password_input);
        confirmPasswordInput = findViewById(R.id.confirmpassword_input);
        createProfileButton = findViewById(R.id.createprofile_btn);

        createProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstName = firstNameInput.getText().toString();
                String lastName = lastNameInput.getText().toString();
                String email = emailInput.getText().toString();
                String password = passwordInput.getText().toString();
                String confirmPassword = confirmPasswordInput.getText().toString();
                if (validateInputs(firstName, lastName,email, password, confirmPassword)) {
                    registerAccount(firstName, lastName,email, password);
                }
            }
        });
    }

    private void registerAccount(String firstName, String lastName, String email, String password) {
        // Instead of using HashMap, now use SQLite database to store the user details
        long userId = dbHelper.addUser(firstName, lastName, email, password);
        if (userId == -1) {
            Toast.makeText(this, "Registration failed.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Registration successful!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Register.this, MainActivity.class);
            startActivity(intent);
            finish(); // Close the register activity
        }
    }

    private boolean validateInputs(String firstName, String lastName, String email, String password, String confirmPassword) {
        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(this, "All fields must be filled.", Toast.LENGTH_LONG).show();
            return false;
        }
        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Passwords do not match.", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }





}
