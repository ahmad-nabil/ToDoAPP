package com.ahmad.todoapp.todo;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ahmad.todoapp.R;
import com.ahmad.todoapp.adapter.adapterRvFinishedList;
import com.ahmad.todoapp.databinding.FinishedListBinding;

import java.util.ArrayList;


public class FinishedList extends Fragment  implements listenerFinishedTask{
ArrayList<objectModel>finishedList=new ArrayList<>();
FinishedListBinding finishedListBinding;
    adapterRvFinishedList adapterRvFinishedList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //inflate
finishedListBinding=FinishedListBinding.inflate(inflater,container,false);
//initilize recycle view and adapter
finishedListBinding.RvFinishedList.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false));
adapterRvFinishedList=new adapterRvFinishedList(requireContext(),finishedList);
finishedListBinding.RvFinishedList.setAdapter(adapterRvFinishedList);
        adapterRvFinishedList.notifyDataSetChanged();
return finishedListBinding.getRoot();
    }
//get value when finished task
    @Override
    public void onTaskComplete(objectModel completedTask) {

           finishedList.add(completedTask);




    }
}