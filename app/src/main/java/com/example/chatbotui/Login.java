package com.example.chatbotui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.HashMap;

public class Login extends AppCompatActivity implements View.OnClickListener {

    //Declaration
    FirebaseAuth mAuth;
    TextInputLayout mTextEmail,mTextPassword;
    TextInputEditText edit_email,edit_password;
    Button mButtonLogin,mButtonRegister,mButtonGuest;
    CheckBox rememberMe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Initialization
        mAuth = FirebaseAuth.getInstance();

        //Connection of our variables to XML Resources
        mTextEmail = (TextInputLayout) findViewById(R.id.login_email);
        mTextPassword = (TextInputLayout) findViewById(R.id.login_pwd);
        rememberMe = (CheckBox)findViewById(R.id.remember_me);
        edit_email = (TextInputEditText)findViewById(R.id.login_email_edit);
        edit_password = (TextInputEditText)findViewById(R.id.login_pwd_edit);

        findViewById(R.id.login_btn).setOnClickListener(this);
        findViewById(R.id.register_btn).setOnClickListener(this);
        findViewById(R.id.forgetPassword_btn).setOnClickListener(this);

        //Check weather the email and password are already stored in Shared preferences
        SessionManager sessionManager = new SessionManager(Login.this,SessionManager.SESSION_REMEMBERME);
        if(sessionManager.checkRememberMe()){
            HashMap<String,String> rememberMeDetails =  sessionManager.getRememberMeDetailFromSession();
            edit_email.setText(rememberMeDetails.get(SessionManager.KEY_SESSIONEMAIL));
            edit_password.setText(rememberMeDetails.get(SessionManager.KEY_SESSIONPASSWORD));
        }


    }
    private void userLogin(){

        String email = mTextEmail.getEditText().getText().toString().trim();
        String pwd = mTextPassword.getEditText().getText().toString().trim();

        //Create Session Manager

        if(email.equals("admin@gmail.com") && pwd.equals("admin321")){
            Toast.makeText(Login.this,"Welcome Admin", Toast.LENGTH_SHORT).show();
            Intent adminIntent = new Intent(Login.this,AdminPanel.class);
            adminIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(adminIntent);
        }
        if(email.isEmpty()){
            mTextEmail.setError("Email is required");
            mTextEmail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            mTextEmail.setError("Email Address not valid");
            mTextEmail.requestFocus();
            return;
        }
        if(pwd.isEmpty()){
            mTextPassword.setError("Password is required");
            mTextPassword.requestFocus();
            return;
        }
        if(pwd.length()<=6){
            mTextPassword.setError("Password length must be greater than 6");
            mTextPassword.requestFocus();
            return;
        }

        if(rememberMe.isChecked() == true){
            SessionManager sessionManager = new SessionManager(Login.this,SessionManager.SESSION_REMEMBERME);
            sessionManager.creatRememberMeSession(email,pwd);
        }

        //Firebase sign-in method
        mAuth.signInWithEmailAndPassword(email,pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    Toast.makeText(Login.this,"Logged in Successfully", Toast.LENGTH_SHORT).show();
                    Intent dashboardIntent = new Intent(Login.this,Dashboard.class);
                    dashboardIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(dashboardIntent);
                }
                else{
                    //if the user is not registered
                    Toast.makeText(Login.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.login_btn:
                userLogin();
                break;

            case R.id.register_btn:
                Intent registerIntent = new Intent(Login.this,Register.class);
                startActivity(registerIntent);
                break;

            case R.id.forgetPassword_btn:
                Intent forgetPasswordIntent = new Intent(Login.this,ForgetPassword.class);
                startActivity(forgetPasswordIntent);
                break;
        }
    }
}
