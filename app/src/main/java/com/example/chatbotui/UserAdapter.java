package com.example.chatbotui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static android.os.Build.ID;


public class UserAdapter extends FirebaseRecyclerAdapter<Users, UserAdapter.UserViewHolder> {

    FirebaseAuth firebaseAuth;
    String Uid;

    public UserAdapter(@NonNull FirebaseRecyclerOptions<Users> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull UserViewHolder userViewHolder, int i, @NonNull Users users) {

        firebaseAuth = FirebaseAuth.getInstance();
        Uid = firebaseAuth.getUid();


        userViewHolder.ID.setText(Uid);
        userViewHolder.firstName.setText(users.getFirstName());
        userViewHolder.lastName.setText(users.getLastName());
        userViewHolder.emailAddress.setText(users.getEmail());

    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user, parent, false);

        return new UserViewHolder(view);
    }

    class UserViewHolder extends RecyclerView.ViewHolder{

        TextView ID,firstName,lastName,emailAddress;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);

            ID = itemView.findViewById(R.id.user_ID);
            firstName = itemView.findViewById(R.id.firstname);
            lastName = itemView.findViewById(R.id.lastname);
            emailAddress = itemView.findViewById(R.id.email);
        }
    }
}
