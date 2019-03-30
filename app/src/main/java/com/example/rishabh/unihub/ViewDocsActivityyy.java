package com.example.rishabh.unihub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewDocsActivityyy extends AppCompatActivity {
//    ListView listView;
    RecyclerView recyclerView;
    DatabaseReference mDatabaseReference;

    ArrayList<Upload> uploadList;
ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_docs_activityyy);
        progressBar= findViewById(R.id.progressbar);
        progressBar.setVisibility(View.VISIBLE);
        uploadList = new ArrayList<>();
//        listView =  findViewById(R.id.listView);
        recyclerView = findViewById(R.id.recyclerView);

            Intent i = getIntent();
            String sub = i.getStringExtra("name");


//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Upload upload = uploadList.get(i);
//
//                //Opening the upload file in browser using the upload url
//                Intent intent = new Intent(Intent.ACTION_VIEW);
//                intent.setData(Uri.parse(upload.getUrl()));
//                startActivity(intent);
//            }
//        });


        mDatabaseReference = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_UPLOADS).child(sub);

        //retrieving upload data from firebase database
//        ArrayList<Upload> uploadArrayList = new ArrayList<>();

        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Upload upload = postSnapshot.getValue(Upload.class);
                    Log.e("TAG", "onDataChange: " + postSnapshot.getValue(Upload.class).getName()+"*****"+postSnapshot.getValue(Upload.class).getUrl());
                    uploadList.add(upload);


                }

                String[] uploads = new String[uploadList.size()];

                for (int i = 0; i < uploads.length; i++) {
                    Log.e("TAG", "onDataChange: "+uploadList.get(i).getName() );
                    uploads[i] = uploadList.get(i).getName();

                }

                //displaying it to list
//                ArrayAdapter<String> adapter = new ArrayAdapter<String>( getBaseContext() ,android.R.layout.simple_list_item_1, uploads);
//                listView.setAdapter(adapter);

                CustomAdapter customAdapter = new CustomAdapter(uploadList,getBaseContext());
                recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
                progressBar.setVisibility(View.GONE);
                recyclerView.setAdapter(customAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }
}
