package com.ahmad.todoapp.dashboard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.ahmad.todoapp.R;
import com.ahmad.todoapp.databinding.DashboardBinding;
import com.ahmad.todoapp.databinding.FullViewVideosBinding;
import com.ahmad.todoapp.databinding.RecordavoicenoteBinding;

public class Dashboard extends AppCompatActivity  implements View.OnClickListener {
DashboardBinding binding;
   private FullViewVideosBinding videosBinding;
    private RecordavoicenoteBinding recordavoicenoteBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //inflate layout
        binding=DashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
   //add fragments view video as default
        getSupportFragmentManager().beginTransaction().add(binding.frames.getId()
        ,new recordavoicenote(),null).commit();
      //set listener for buttons dashboard
        binding.videonotes.setOnClickListener(this);
        binding.voicenoteBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.videonotes){
                getSupportFragmentManager().beginTransaction().replace(binding.frames.getId()
                        ,new fullViewVideos(),null).commit();
        }else if (v.getId()==R.id.voicenoteBtn) {
            getSupportFragmentManager().beginTransaction().replace(binding.frames.getId()
                    , new recordavoicenote(), null).commit();
        }

    }
}