package com.example.chatbotui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.textfield.TextInputLayout;

public class ContactUs extends AppCompatActivity {

    TextInputLayout mEmail,mSubject,mMessage;
    Button mButton;
    ImageView backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        backBtn = (ImageView) findViewById(R.id.back);
        mEmail = (TextInputLayout) findViewById(R.id.MailID);
        mSubject = (TextInputLayout) findViewById(R.id.SubjectID);
        mMessage = (TextInputLayout) findViewById(R.id.MessageID);
        mButton = (Button) findViewById(R.id.sendBtn);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMail();
            }
        });
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ContactUs.this, AdminPanel.class);
                startActivity(i);
                finish();
            }
        });
    }

    private void sendMail() {
        String mail = mEmail.getEditText().getText().toString().trim();
        String subject = mSubject.getEditText().getText().toString().trim();
        String message = mMessage.getEditText().getText().toString().trim();

        //Send Mail
        JavaMailAPI javaMailAPI = new JavaMailAPI(this,mail,subject,message);
        javaMailAPI.execute();
    }
}
