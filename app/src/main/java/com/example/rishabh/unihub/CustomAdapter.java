package com.example.rishabh.unihub;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder>{

    ArrayList<Upload> filearrayList;
    Context ctx;

    public CustomAdapter(ArrayList<Upload> filearrayList, Context ctx) {
        this.filearrayList = filearrayList;
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.listlayout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final Upload currentfile = filearrayList.get(position);
        holder.filename.setText(currentfile.getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
//                i.setData(Uri.parse(currentfile.getUrl()));
                i.setDataAndType(Uri.parse(currentfile.getUrl()),"application/pdf");
                ctx.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return filearrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView filename;

        public ViewHolder(View itemView) {
            super(itemView);
            filename = itemView.findViewById(R.id.filename);
        }
    }
}
