package com.ahmad.todoapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ahmad.todoapp.databinding.NotesRvBinding;
import com.ahmad.todoapp.todo.objectModel;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class adapterRvFinishedList extends RecyclerView.Adapter<adapterRvFinishedList.Finishedlist> {
    Context context;
    ArrayList<objectModel>list;

    public adapterRvFinishedList(Context context, ArrayList<objectModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public adapterRvFinishedList.Finishedlist onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        NotesRvBinding rvBinding=NotesRvBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new Finishedlist(rvBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull adapterRvFinishedList.Finishedlist holder, int position) {
        holder.finishedBinding.notes.setText(list.get(position).getNote());
        Glide.with(context).load(list.get(position).getImg()).into(holder.finishedBinding.imagenotes);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Finishedlist extends RecyclerView.ViewHolder {
        NotesRvBinding finishedBinding;
        public Finishedlist( NotesRvBinding rvBinding) {
            super(rvBinding.getRoot());
            this.finishedBinding=rvBinding;
        }
    }
}
