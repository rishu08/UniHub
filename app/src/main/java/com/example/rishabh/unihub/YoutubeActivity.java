package com.example.rishabh.unihub;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class YoutubeActivity extends Fragment {

RecyclerView recyclerView;
  ArrayList<YoutubeVideo> youtubeVideos = new ArrayList<>();

    public YoutubeActivity() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_youtube, container, false);

recyclerView = v.findViewById(R.id.recyclerView);
recyclerView.setHasFixedSize(true);
recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


youtubeVideos.add(new YoutubeVideo("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/IpsYI-Ww2S4?list=PLKHbJK52j_gAC0BmsFVEW_oBRF6fFUr18&amp;ecver=1\" frameborder=\"0\" allow=\"autoplay; encrypted-media\" allowfullscreen></iframe>"));
youtubeVideos.add(new YoutubeVideo("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/cp_U1QWyc6Y?list=PLKHbJK52j_gAC0BmsFVEW_oBRF6fFUr18&amp;ecver=1\" frameborder=\"0\" allow=\"autoplay; encrypted-media\" allowfullscreen></iframe>"));
youtubeVideos.add(new YoutubeVideo("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/_CaVo9D6aUk?list=PLKHbJK52j_gAC0BmsFVEW_oBRF6fFUr18&amp;ecver=1\" frameborder=\"0\" allow=\"autoplay; encrypted-media\" allowfullscreen></iframe>"));
youtubeVideos.add(new YoutubeVideo("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/dyOdHCkjxew?list=PLKHbJK52j_gAC0BmsFVEW_oBRF6fFUr18&amp;ecver=1\" frameborder=\"0\" allow=\"autoplay; encrypted-media\" allowfullscreen></iframe>"));
youtubeVideos.add(new YoutubeVideo("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/daoFoea2Q4s?list=PLKHbJK52j_gAC0BmsFVEW_oBRF6fFUr18&amp;ecver=1\" frameborder=\"0\" allow=\"autoplay; encrypted-media\" allowfullscreen></iframe>"));
youtubeVideos.add(new YoutubeVideo("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/74_2q8U48PA?list=PLKHbJK52j_gAC0BmsFVEW_oBRF6fFUr18&amp;ecver=1\" frameborder=\"0\" allow=\"autoplay; encrypted-media\" allowfullscreen></iframe>"));
youtubeVideos.add(new YoutubeVideo("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/MKUln8ZmaV4?list=PLKHbJK52j_gAC0BmsFVEW_oBRF6fFUr18&amp;ecver=1\" frameborder=\"0\" allow=\"autoplay; encrypted-media\" allowfullscreen></iframe>"));
youtubeVideos.add(new YoutubeVideo("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/gzTzmzWmuI8?list=PLKHbJK52j_gAC0BmsFVEW_oBRF6fFUr18&amp;ecver=1\" frameborder=\"0\" allow=\"autoplay; encrypted-media\" allowfullscreen></iframe>"));
youtubeVideos.add(new YoutubeVideo("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/L2iW5xyxxTk?list=PLKHbJK52j_gAC0BmsFVEW_oBRF6fFUr18&amp;ecver=1\" frameborder=\"0\" allow=\"autoplay; encrypted-media\" allowfullscreen></iframe>"));
youtubeVideos.add(new YoutubeVideo("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/-kbL65rRynk?list=PLKHbJK52j_gAC0BmsFVEW_oBRF6fFUr18&amp;ecver=1\" frameborder=\"0\" allow=\"autoplay; encrypted-media\" allowfullscreen></iframe>"));
youtubeVideos.add(new YoutubeVideo("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/LaaBuqIgnS0?list=PLKHbJK52j_gAC0BmsFVEW_oBRF6fFUr18&amp;ecver=1\" frameborder=\"0\" allow=\"autoplay; encrypted-media\" allowfullscreen></iframe>"));
youtubeVideos.add(new YoutubeVideo("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/esPjBaeAEI4?list=PLKHbJK52j_gAC0BmsFVEW_oBRF6fFUr18&amp;ecver=1\" frameborder=\"0\" allow=\"autoplay; encrypted-media\" allowfullscreen></iframe>"));
youtubeVideos.add(new YoutubeVideo("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/j3kR7TjMf1I?list=PLKHbJK52j_gAC0BmsFVEW_oBRF6fFUr18&amp;ecver=1\" frameborder=\"0\" allow=\"autoplay; encrypted-media\" allowfullscreen></iframe>"));
youtubeVideos.add(new YoutubeVideo("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/Qt0RUTqUN7w?list=PLKHbJK52j_gAC0BmsFVEW_oBRF6fFUr18&amp;ecver=1\" frameborder=\"0\" allow=\"autoplay; encrypted-media\" allowfullscreen></iframe>"));
youtubeVideos.add(new YoutubeVideo("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/zfBeoSyg4lE?list=PLKHbJK52j_gAC0BmsFVEW_oBRF6fFUr18&amp;ecver=1\" frameborder=\"0\" allow=\"autoplay; encrypted-media\" allowfullscreen></iframe>"));

        VideoAdapter videoAdapter = new VideoAdapter(youtubeVideos);
        recyclerView.setAdapter(videoAdapter);

        return v;
    }

}
