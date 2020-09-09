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

 public class Dashboard extends AppCompatActivity implements View.OnClickListener {

    private ImageView atmLocator,branchLocator,chatBot,feedBack,logOut;
    private FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        //Defining ImageViews
        atmLocator = (ImageView) findViewById(R.id.id_atm);
        branchLocator = (ImageView) findViewById(R.id.id_branch);
        chatBot = (ImageView) findViewById(R.id.id_chatbot);
        feedBack = (ImageView) findViewById(R.id.id_feedback);
        logOut = (ImageView) findViewById(R.id.id_logout);

        //Add Click Listener to the ImageViews
        atmLocator.setOnClickListener(this);
        branchLocator.setOnClickListener(this);
        chatBot.setOnClickListener(this);
        feedBack.setOnClickListener(this);
        logOut.setOnClickListener(this);

        mFirebaseAuth = FirebaseAuth.getInstance();

    }

     @Override
     public void onClick(View v) {
        Intent i;

        //Switch case because we have multiple options on dashboard screen
         switch (v.getId()) {
             case R.id.id_atm : i = new Intent(this, ATMLocator.class); startActivity(i); break;
             case R.id.id_branch : i = new Intent(this, MapsActivity.class); startActivity(i); break;
             case R.id.id_chatbot: i = new Intent(this, MainActivity.class); startActivity(i); break;
             case R.id.id_feedback : i = new Intent(this, Feedback.class); startActivity(i); break;
             case R.id.id_logout :
                 logOut();
                 break;
             default:break;
         }

     }

     private void logOut() {
         FirebaseAuth.getInstance().signOut();
         Toast.makeText(this, "You have been logged out.", Toast.LENGTH_SHORT).show();
         startActivity(new Intent(Dashboard.this,Login.class));
         finish();
     }
 }
