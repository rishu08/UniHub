package com.example.rishabh.unihub;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {

    ArrayList<Note> noteArrayList;
    Context ctx;

    public NoteAdapter(ArrayList<Note> noteArrayList, Context ctx) {
        this.noteArrayList = noteArrayList;
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(ctx).inflate(R.layout.list_item,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        final Note currentNote = noteArrayList.get(position);
        holder.title.setText(currentNote.getTitle());
        holder.desc.setText(currentNote.getDescription());
        Long time = currentNote.getTime();

        Log.e("TAG", "onBindViewHolder: "+currentNote.getTime() );

        AlarmManager alarmManager = (AlarmManager) ctx.getSystemService(Context.ALARM_SERVICE);


        final Intent intent = new Intent(ctx,Alarm.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(ctx,12345,intent,PendingIntent.FLAG_ONE_SHOT);

//        alarmManager.setAndAllowWhileIdle(AlarmManager.ELAPSED_REALTIME_WAKEUP,
//                time,
//                pendingIntent);


        alarmManager.set(AlarmManager.RTC_WAKEUP,time,pendingIntent);



        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                final DatabaseReference databaseReference = firebaseDatabase.getReference().child("note").child(firebaseAuth.getCurrentUser().getUid());

                final String temp2 = currentNote.getGetKeyvalue();
//                Log.e("TAG", "onLongClick:-------------"+databaseReference.getKey() );


                        AlertDialog alertDialog = new AlertDialog.Builder(ctx)
                        .setTitle("Are you sure to Delete this Note")
                        .setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                databaseReference.child(temp2).removeValue();

                                noteArrayList.remove(position);
                                notifyDataSetChanged();

                                Toast.makeText(ctx, "Note Deleted", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(ctx, "Delete Canceled", Toast.LENGTH_SHORT).show();
                            }
                        }).create();


                alertDialog.show();
            return false;
            }
        });

    }

    @Override
    public int getItemCount() {
        return noteArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView title,desc;

        public ViewHolder(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.item_title);
            desc = itemView.findViewById(R.id.item_description);
        }
    }
}
