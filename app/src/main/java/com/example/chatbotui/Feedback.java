
package com.example.chatbotui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Feedback extends AppCompatActivity implements View.OnClickListener {

    TextInputLayout user_feedback;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        findViewById(R.id.back).setOnClickListener(this);
        user_feedback = (TextInputLayout)findViewById(R.id.feedback);
        findViewById(R.id.submit_feedback).setOnClickListener(this);

    }
    public void sendFeedback(){
        final String userFeedback = user_feedback.getEditText().getText().toString().trim();

        if((userFeedback != null)){
            FirebaseDatabase.getInstance().getReference("feedback")
                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .setValue(userFeedback);
            Toast.makeText(Feedback.this, "Feeback submitted",Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(Feedback.this, "Invalid feedback",Toast.LENGTH_SHORT).show();
        }
    }
    public void goBack(){
        Intent i = new Intent(this, Dashboard.class);
        startActivity(i);
        finish();
    }


    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.submit_feedback:
                sendFeedback();
                break;

            case R.id.back:
                goBack();
                break;
        }
    }
}

