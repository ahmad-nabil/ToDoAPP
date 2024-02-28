package com.ahmad.todoapp.todo;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ahmad.todoapp.adapter.FragmentsAdapter;
import com.ahmad.todoapp.databinding.FragmentToDoManagerBinding;
import com.google.android.material.tabs.TabLayoutMediator;


public class ToDoManager extends Fragment {
    FragmentsAdapter adapter;
    FragmentToDoManagerBinding toDoManager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //inflate layout
        toDoManager=FragmentToDoManagerBinding.inflate(inflater, container, false);
       //initialize adapter tap
        adapter=new FragmentsAdapter(requireActivity().getSupportFragmentManager(),getLifecycle());
       //initialize fragments
        ToDoList toDoList=new ToDoList();
        FinishedList finishedList=new FinishedList();
        //add finished list as listener for interface in to do fragment
        toDoList.setFinishedTask(finishedList);
        //add fragment to adapter
        adapter.addFragmentsTab(toDoList,"To do List");
        adapter.addFragmentsTab(finishedList,"Finished List");
        //set adapter in viewpager
        toDoManager.vpagerhome.setAdapter(adapter);
        //set layout tap
        new TabLayoutMediator(toDoManager.tabHome,toDoManager.vpagerhome, (tab, position) -> {tab.setText(adapter.getPageTitle(position));}).attach();
        return toDoManager.getRoot();
    }
}