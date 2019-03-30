package com.example.rishabh.unihub;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class SubjectSelect extends AppCompatActivity {

    RecyclerView subRv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_select);

        subRv = findViewById(R.id.rv_sub);

        ArrayList<String> subject = new ArrayList<>();

        subject.add("Year2 : Maths");
        subject.add("Year2 : COA");
        subject.add("Year2 : Data Structure");
        subject.add("Year2 : EVS");
        subject.add("Year2 : Physics: LASER");
        subject.add("Year2 : DLD");


        SubAdapter subAdapter = new SubAdapter(subject , SubjectSelect.this);
        subRv.setLayoutManager(new LinearLayoutManager(this));
        subRv.setAdapter(subAdapter);



    }
}
