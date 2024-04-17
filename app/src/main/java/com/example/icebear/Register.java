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

    // Temporary storage simulation. Consider replacing this with a database solution.
    private static final HashMap<String, HashMap<String, String>> userAccounts = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register); // Make sure this matches your XML file name

        firstNameInput = findViewById(R.id.firstname_input);
        lastNameInput = findViewById(R.id.lastname_input);
        emailInput = findViewById(R.id.email);
        passwordInput = findViewById(R.id.password_input);
        confirmPasswordInput = findViewById(R.id.confirmpassword_input);
        createProfileButton = findViewById(R.id.createprofile_btn)

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
        // Storing the first name and last name along with the email and password
        HashMap<String, String> userDetails = new HashMap<>();
        userDetails.put("firstName", firstName);
        userDetails.put("lastName", lastName);
        userDetails.put("password", password);

        userAccounts.put(email, userDetails);

        // Notify user of success
        Toast.makeText(this, "Registration successful!", Toast.LENGTH_SHORT).show();

        // Start HomeActivity
        Intent intent = new Intent(Register.this, MainActivity.class);
        startActivity(intent);
        finish(); // Close the register activity so it's not on the back stack
    }


    private boolean validateInputs(String firstName, String lastName, String email, String password, String confirmPassword) {
        // Check for empty fields
        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(this, "All fields must be filled.", Toast.LENGTH_LONG).show();
            return false;
        }

        // Check if passwords match
        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Passwords do not match.", Toast.LENGTH_LONG).show();
            return false;
        }

        // Additional validation can be added here (e.g., email format, password strength)
        return true;
    }

}
