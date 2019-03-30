package com.example.rishabh.unihub;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

public class UserInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        ImageView photo =findViewById(R.id.image);
        TextView name =findViewById(R.id.name);
        TextView email = findViewById(R.id.email);

        Intent i = getIntent();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        String username =i.getStringExtra("name");
        String userid = i.getStringExtra("email");
        Picasso.get().load(firebaseAuth.getCurrentUser().getPhotoUrl()).into(photo);

        name.setText(username);
        email.setText("Email: "+userid);
    }
}
