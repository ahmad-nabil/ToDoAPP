package com.ahmad.todoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;

import com.ahmad.todoapp.adapter.FragmentsAdapter;
import com.ahmad.todoapp.dashboard.Dashboard;
import com.ahmad.todoapp.dashboard.fullViewVideos;
import com.ahmad.todoapp.dashboard.recordavoicenote;
import com.ahmad.todoapp.dashboard.videoView;
import com.ahmad.todoapp.databinding.HomeBinding;
import com.ahmad.todoapp.todo.FinishedList;
import com.ahmad.todoapp.todo.ToDoList;
import com.ahmad.todoapp.todo.ToDoManager;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayoutMediator;

public class Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

HomeBinding homeBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //inflate home layout
        homeBinding=HomeBinding.inflate(getLayoutInflater());
        setContentView(homeBinding.getRoot());
         //initialize navigation drawer component
            homeBinding.nav.bringToFront();
            ActionBarDrawerToggle drawerToggle=new ActionBarDrawerToggle(this,homeBinding.drawerHome,homeBinding.materialToolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
           homeBinding.drawerHome.addDrawerListener(drawerToggle);
           drawerToggle.syncState();
            homeBinding.nav.setNavigationItemSelectedListener(this);
//set to do list as default
             getSupportFragmentManager().beginTransaction().add(homeBinding.FramesHome.getId(),new ToDoManager(),"").commit();

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.homeMenu){
            getSupportFragmentManager().beginTransaction().replace(homeBinding.FramesHome.getId(),new ToDoManager(),"").commit();
        }else if (item.getItemId()==R.id.fullviewVideo){
            getSupportFragmentManager().beginTransaction().replace(homeBinding.FramesHome.getId(),new fullViewVideos(),"").commit();

        }else if (item.getItemId()==R.id.voiceNotes){
            getSupportFragmentManager().beginTransaction().replace(homeBinding.FramesHome.getId(),new recordavoicenote(),"").commit();

        }else if (item.getItemId()==R.id.Dashboard){
startActivity(new Intent(this, Dashboard.class));
        }
        homeBinding.drawerHome.closeDrawer(GravityCompat.START);

        return true;

    }
}