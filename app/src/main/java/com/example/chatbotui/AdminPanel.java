package com.example.chatbotui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class AdminPanel extends AppCompatActivity implements View.OnClickListener {

    private ImageView registeredUsers,blockUsers,userFeedback,contactUs,logOut;
    private FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);

        //Defining ImageViews
        registeredUsers = (ImageView) findViewById(R.id.id_users);
        blockUsers = (ImageView) findViewById(R.id.id_block);
        userFeedback = (ImageView) findViewById(R.id.id_userfeedback);
        contactUs = (ImageView) findViewById(R.id.id_contactus);
        logOut = (ImageView) findViewById(R.id.id_logout);

        //Add Click Listener to the ImageViews
        registeredUsers.setOnClickListener(this);
        blockUsers.setOnClickListener(this);
        userFeedback.setOnClickListener(this);
        contactUs.setOnClickListener(this);
        logOut.setOnClickListener(this);

        mFirebaseAuth = FirebaseAuth.getInstance();

    }

    @Override
    public void onClick(View v) {
        Intent i;

        //Switch case because we have multiple options on dashboard screen
        switch (v.getId()) {
            case R.id.id_users : i = new Intent(this, RegisteredUsers.class); startActivity(i); break;
            case R.id.id_block : i = new Intent(this, MapsActivity.class); startActivity(i); break;
            case R.id.id_userfeedback: i = new Intent(this, FeedbackListActivity.class); startActivity(i); break;
            case R.id.id_contactus : i = new Intent(this, ContactUs.class); startActivity(i); break;
            case R.id.id_logout :
                logOut();
                break;
            default:break;
        }

    }

    private void logOut() {
        FirebaseAuth.getInstance().signOut();
        Toast.makeText(this, "You have been logged out.", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(AdminPanel.this,Login.class));
        finish();
    }
}
