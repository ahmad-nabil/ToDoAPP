package com.ahmad.todoapp.adapter;

import android.content.Context;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ahmad.todoapp.dashboard.objectsMediaNotes;
import com.ahmad.todoapp.databinding.VoicenoteRecycleviewBinding;

import java.io.IOException;
import java.util.ArrayList;

public class adapterVoiceNotes extends RecyclerView.Adapter<adapterVoiceNotes.Holdervoices> {
    Context context;
    ArrayList <objectsMediaNotes>list;
    MediaPlayer player=new MediaPlayer();
    public adapterVoiceNotes(Context context, ArrayList<objectsMediaNotes> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public adapterVoiceNotes.Holdervoices onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        VoicenoteRecycleviewBinding voicenoteRecycleviewBinding=VoicenoteRecycleviewBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new Holdervoices(voicenoteRecycleviewBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull adapterVoiceNotes.Holdervoices holder, int position) {
       holder.voicenoteRecycleviewBinding.titlevoice.setText(list.get(position).getNote());
 holder.voicenoteRecycleviewBinding.playvoicenotes.setOnClickListener(v->{

     if (player != null) {

             player.release();
             player = null;

     }
     player = new MediaPlayer();

         try {
             player.setDataSource(list.get(position).getUri());
            player.prepare();
             player.start();
         } catch (IOException e) {
             throw new RuntimeException(e);
         }



 });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Holdervoices extends RecyclerView.ViewHolder {
        VoicenoteRecycleviewBinding voicenoteRecycleviewBinding;
        public Holdervoices(VoicenoteRecycleviewBinding voicenoteRecycleviewBinding) {
            super(voicenoteRecycleviewBinding.getRoot());
            this.voicenoteRecycleviewBinding=voicenoteRecycleviewBinding;
            player.release();
        }
    }
}
