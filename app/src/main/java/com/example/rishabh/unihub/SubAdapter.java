package com.example.rishabh.unihub;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class SubAdapter extends RecyclerView.Adapter<SubAdapter.Viewholder> {

    ArrayList<String> subarraylist;
    Context ctx;

    public SubAdapter(ArrayList<String> subarraylist, Context ctx) {
        this.subarraylist = subarraylist;
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.listlayout,parent,false);
       
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {

        final String currentSub = subarraylist.get(position);
        holder.name.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);
        holder.name.setAllCaps(false);
        holder.name.setText(currentSub);

        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(ctx, UploadSubDocs.class);
                i.putExtra("name",currentSub);
                ctx.startActivity(i);


            }
        });


    }

    @Override
    public int getItemCount() {
        return subarraylist.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder{

    TextView name;
    public Viewholder(View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.filename);
    }
}
}
