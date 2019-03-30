package com.example.rishabh.unihub;


import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ChatActivity extends Fragment {
    EditText comment;
    Button send;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    ProgressBar progressBar;

    public ChatActivity() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_chat, container, false);
        progressBar = view.findViewById(R.id.progress);
        progressBar.setVisibility(View.VISIBLE);
        comment = view.findViewById(R.id.etcomment);
        send = view.findViewById(R.id.btnsend);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("Chat");
         firebaseAuth = FirebaseAuth.getInstance();

        final String user;

        if(firebaseAuth.getCurrentUser().getDisplayName() !=null)
        {



            Log.e("TAG", "onCreate: --------------------------------------------" );
            Log.e("TAG", "onCreate: "+firebaseAuth.getCurrentUser().getDisplayName() );
            Log.e("TAG", "onCreate: --------------------------------------------" );
            user = firebaseAuth.getCurrentUser().getDisplayName();

        }

        else
        {
            user = firebaseAuth.getCurrentUser().getPhoneNumber();
        }

         Log.e("TAG", "onCreate: "+user );


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String message = comment.getText().toString();

                if (!message.isEmpty())
                {
                    String time = DateFormat.getDateTimeInstance().format(new Date());

                    Chat chat = new Chat(user,message,time);
                    comment.setText("");
                    databaseReference.push().setValue(chat);
                }
                else
                {
                    Toast.makeText(getActivity().getBaseContext(),"ENTER TEXT TO SNED",Toast.LENGTH_SHORT).show();
                }
            }
        });


        databaseReference.addChildEventListener(new ChildEventListener() {

            final ArrayList<Chat> chatArrayList = new ArrayList<>();
            final RecyclerView rv = view.findViewById(R.id.rv);

            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                Chat chat = dataSnapshot.getValue(Chat.class);
                chatArrayList.add(0,chat);

                Adapter adapter = new Adapter(chatArrayList,getContext());
                rv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,true));

                rv.setAdapter(adapter);
                progressBar.setVisibility(View.GONE);

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(getContext(), "SOME ERROR OCCURED! TRY AGAIN", Toast.LENGTH_SHORT).show();
                Log.e("TAG", "onCancelled: "+databaseError );
            }
        });



        return view;
    }

}
