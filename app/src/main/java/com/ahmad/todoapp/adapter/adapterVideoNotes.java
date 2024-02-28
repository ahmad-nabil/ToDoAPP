package com.ahmad.todoapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ahmad.todoapp.dashboard.objectsMediaNotes;
import com.ahmad.todoapp.dashboard.videoView;
import com.ahmad.todoapp.databinding.VideoviewsRvBinding;

import java.util.ArrayList;

public class adapterVideoNotes extends RecyclerView.Adapter<adapterVideoNotes.holderVideoNotes> {
    ArrayList<objectsMediaNotes>mediaNotesArrayList;
    Context context;
    FragmentManager fragmentManager;

    public adapterVideoNotes(ArrayList<objectsMediaNotes> mediaNotesArrayList, Context context, FragmentManager fragmentManager) {
        this.mediaNotesArrayList = mediaNotesArrayList;
        this.context = context;
        this.fragmentManager = fragmentManager;
    }

    @NonNull
    @Override
    public adapterVideoNotes.holderVideoNotes onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        VideoviewsRvBinding videoviewsRvBinding=VideoviewsRvBinding.inflate(LayoutInflater.from(context),parent,false);
        return new holderVideoNotes(videoviewsRvBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull adapterVideoNotes.holderVideoNotes holder, int position) {
holder.videoviewsRvBinding.titlenotevideo.setText(mediaNotesArrayList.get(position).getNote());
holder.videoviewsRvBinding.playvideo.setOnClickListener(v->{
    videoView video_view=new videoView(mediaNotesArrayList.get(position).getUri());
    video_view.show(fragmentManager,"full view");
});
    }

    @Override
    public int getItemCount() {
        return mediaNotesArrayList.size();
    }

    public class holderVideoNotes extends RecyclerView.ViewHolder {
        VideoviewsRvBinding videoviewsRvBinding;
        public holderVideoNotes(@NonNull VideoviewsRvBinding binding) {
            super(binding.getRoot());
            videoviewsRvBinding=binding;
        }
    }
}
