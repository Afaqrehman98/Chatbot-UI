package com.example.chatbotui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity implements View.OnClickListener {

    TextInputLayout mFirstName,mLastName,mContactNumber,mTextEmail,mTextPassword,mConfirmPassword;
    Button mButtonRegister;
    RadioButton mMaleButton,mFemaleButton;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Connection of our variables to XML Resources
        mFirstName = (TextInputLayout)findViewById(R.id.first_name);
        mLastName = (TextInputLayout)findViewById(R.id.last_name);
        mContactNumber = (TextInputLayout)findViewById(R.id.contact_number);
        mTextEmail = (TextInputLayout)findViewById(R.id.register_email);
        mTextPassword = (TextInputLayout)findViewById(R.id.register_pwd);
        mConfirmPassword = (TextInputLayout)findViewById(R.id.confirm_password);
        mMaleButton = (RadioButton)findViewById(R.id.male);
        mFemaleButton = (RadioButton)findViewById(R.id.female);


        //Initialization of Firebase
        mAuth = FirebaseAuth.getInstance();

        //On click Listener
        findViewById(R.id.register_btn).setOnClickListener(this);
        }

    //Registering User Method
    private void registerUser() {
        final String first_name = mFirstName.getEditText().getText().toString().trim();
        final String last_name = mLastName.getEditText().getText().toString().trim();
        final String contactNumber = mContactNumber.getEditText().getText().toString().trim();
        final String email = mTextEmail.getEditText().getText().toString().trim();
        final String pwd = mTextPassword.getEditText().getText().toString().trim();
        final String confirmPwd = mConfirmPassword.getEditText().getText().toString().trim();
        String Gender = "";
        if (mMaleButton.isChecked() == true) {
            Gender = "Male";
        } else if (mFemaleButton.isChecked() == true) {
            Gender = "Female";
        } else {
            Gender = "Not specified";
        }

        final String gender = Gender;


        if (first_name.isEmpty()) {
            mFirstName.setError("First name is reuired");
            mFirstName.requestFocus();
            return;
        }
        if (last_name.isEmpty()) {
            mLastName.setError("Last name is reuired");
            mLastName.requestFocus();
            return;
        }
        if (contactNumber.isEmpty()) {
            mContactNumber.setError("Contact Number is reuired");
            mContactNumber.requestFocus();
            return;
        }
        if (email.isEmpty()) {
            mTextEmail.setError("Email is reuired");
            mTextEmail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mTextEmail.setError("Email Address not valid");
            mTextEmail.requestFocus();
            return;
        }
        if (pwd.isEmpty()) {
            mTextPassword.setError("Password is reuired");
            mTextPassword.requestFocus();
            return;
        }
        if (pwd.length() <= 6) {
            mTextPassword.setError("Password length must be greater than 6");
            mTextPassword.requestFocus();
            return;
        }
        if (!pwd.equals(confirmPwd)) {
            Toast.makeText(Register.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
        } else {
            mAuth.createUserWithEmailAndPassword(email, pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    //If the task is successful the data will be added to the firebase
                    if (task.isSuccessful()) {
                        //Created an object of UserHelperClass to pass the custom fields
                        UserHelperClass userHelper = new UserHelperClass(first_name, last_name, contactNumber, email, pwd, gender);
                        FirebaseDatabase.getInstance().getReference("Users")
                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .setValue(userHelper);
                        Toast.makeText(Register.this, "Successfully Registered", Toast.LENGTH_SHORT).show();
                        Intent registerIntent = new Intent(Register.this, Login.class);
                        startActivity(registerIntent);
                    }
                    //if the user is already registered
                    else {
                        Toast.makeText(Register.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
    }
    //On click method for Button
    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.register_btn:
                registerUser();
                break;

        }
    }
}