package com.example.rishabh.unihub;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import java.util.ArrayList;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {

ArrayList<YoutubeVideo> youtubeVideoArrayList;

    public VideoAdapter() {
    }

    public VideoAdapter(ArrayList<YoutubeVideo> youtubeVideoArrayList) {
        this.youtubeVideoArrayList = youtubeVideoArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_video,parent,false);
         return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.videoWeb.loadData( youtubeVideoArrayList.get(position).getVideoUrl(), "text/html" , "utf-8");


    }

    @Override
    public int getItemCount() {
        return youtubeVideoArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

    WebView videoWeb;

    public ViewHolder(View itemView) {
        super(itemView);

    videoWeb = itemView.findViewById(R.id.WebVideoView);
    videoWeb.getSettings().setJavaScriptEnabled(true);
    videoWeb.setWebChromeClient(new WebChromeClient());

    }
}
}
