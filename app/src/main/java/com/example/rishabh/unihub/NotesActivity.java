package com.example.rishabh.unihub;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TimePicker;

import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.Target;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class NotesActivity extends Fragment {

    static int flag_check_note=0;
    ShowcaseView showcaseView;
    EditText TTitle, des;
    Button add;
    FloatingActionButton fabAdd;
    AlertDialog alertDialog;
    ProgressBar progressBar;
    public NotesActivity() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view= inflater.inflate(R.layout.fragment_notes, container, false);
progressBar = view.findViewById(R.id.progress);
progressBar.setVisibility(View.VISIBLE);

        add = view.findViewById(R.id.add);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference = firebaseDatabase.getReference().child("note");
        final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        fabAdd = view.findViewById(R.id.fabAdd);

//**********************************************************************

        SharedPreferences sRef = getActivity().getPreferences(Context.MODE_PRIVATE);
        int value = sRef.getInt("Note_sref",0);

        Log.e("TAG", "onCreateView: "+value );
        if (value == 0)
        {
            SharedPreferences.Editor editor = sRef.edit();
            editor.putInt("Note_sref",1).apply();
            Log.e("TAG", "onCreateView: /*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*" );
            new ShowcaseView.Builder(getActivity())

                    .setTarget(Target.NONE)
                    .setContentTitle("ADD NOTES")
                    .setContentText("Press (+) to add notes")
                    .withNewStyleShowcase()
                    .build();
        }


//  ************************************************************************
//        if (flag_check_note==0)
//        {
//            new ShowcaseView.Builder(getActivity())
//
//                    .setTarget(Target.NONE)
//                    .setContentTitle("ADD NOTES")
//                    .setContentText("Press (+) to add notes")
//                    .withNewStyleShowcase()
//                    .build();
//            flag_check_note=1;
//
//        }


//        final String[] title = new String[1];
//        title[0]="hh";
//        final String[] desc = new String[1];
//        desc[0] = "";


        final View v = LayoutInflater.from(getActivity()).inflate(R.layout.alertdialog_layout, null, true);
        final Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(SystemClock.elapsedRealtime());
        final int hour = calendar.get(Calendar.HOUR_OF_DAY);
        final int min = calendar.get(Calendar.MINUTE);
        final Date d = new Date();
        final CharSequence s  = DateFormat.format("MMM dd, yyyy ", d.getTime());
        CharSequence day = DateFormat.format("dd",d.getTime());
        CharSequence mont = DateFormat.format("MM",d.getTime());
        Log.e("TAG", "onTimeSet:--- --- ---- ---- ---- ---- "+mont );
        CharSequence yea = DateFormat.format("yyyy",d.getTime());
        int date = Integer.parseInt(day.toString());
        int month = Integer.parseInt(mont.toString());
        int year = Integer.parseInt(yea.toString());
        calendar.set(year,month-1,date);

//        final String note_title ;
//
//        final String desc;
//


        alertDialog = new AlertDialog.Builder(getContext())
                .setTitle("Enter the NOTE")
                .setView(v)
                .setPositiveButton("SET TIME", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        TTitle = v.findViewById(R.id.tittle);
                        des = v.findViewById(R.id.description);
//                        note_title[0] = Title.getText().toString();
//                        desc[0] = des.getText().toString();
                       final String  note_title = TTitle.getText().toString();
                        final String desc = des.getText().toString();
                            TTitle.setText("");
                            des.setText("");

                        final TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                calendar.set(Calendar.MINUTE, minute);
                                //add the note here
//                                String note_title = Title.getText().toString();
//                                String desc = des.getText().toString();

//                                 Log.e("TAG", "onTimeSet: "+DateFormat.format("dd",d.getTime()) );
                                DatabaseReference databaseReference1 = databaseReference.child(firebaseAuth.getCurrentUser().getUid()).push();
                                String temp = databaseReference1.toString();
                                String[] array = temp.split("/");
                                int length = array.length;
                                Log.e("0TAG", "onTimeSet: " + calendar.getTime());
//                                Note note = new Note(note_title[0], desc[0], array[length - 1], calendar.getTimeInMillis());
                                Note note = new Note(note_title, desc, array[length - 1], calendar.getTimeInMillis());
                                Log.e("TAG", "onTimeSet: "+calendar.getTimeInMillis());
//                                Note note = new Note("hiii", "okkk", array[length - 1], calendar.getTimeInMillis());
//
                                databaseReference1.setValue(note);


                            }
                        }, hour, min, false);
                        Log.e("TAG", "onClick: CURRENT TIME ------- " + SystemClock.elapsedRealtime());
                        timePickerDialog.show();

                    }

                    }).create();


        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.show();
            }
        });




        databaseReference.child(firebaseAuth.getCurrentUser().getUid()).addChildEventListener(new ChildEventListener() {

            final ArrayList<Note> noteArrayList = new ArrayList<>();
            final RecyclerView rv = view.findViewById(R.id.listTodo);

            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                Note note = dataSnapshot.getValue(Note.class);
                noteArrayList.add(note);

                NoteAdapter noteAdapter = new NoteAdapter(noteArrayList,getActivity());
                rv.setLayoutManager(new StaggeredGridLayoutManager(2,1));
                rv.setAdapter(noteAdapter);
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

            }
        });

        return view;
    }


}
