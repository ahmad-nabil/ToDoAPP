package com.ahmad.todoapp.dashboard;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ahmad.todoapp.adapter.adapterVoiceNotes;
import com.ahmad.todoapp.databinding.DialogRecordBinding;
import com.ahmad.todoapp.databinding.RecordavoicenoteBinding;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

public class recordavoicenote extends Fragment {
RecordavoicenoteBinding recordavoicenote;
MediaRecorder mediaRecorder;
String pathRec="";
String note="";
ArrayList<objectsMediaNotes>voices=new ArrayList<>();
adapterVoiceNotes adapterVoiceNotes;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       recordavoicenote=RecordavoicenoteBinding.inflate(inflater, container,false );
        recordavoicenote.floatingActionButton.setOnClickListener(v->{
            ShowDialogRecord();
        });
        adapterVoiceNotes=new adapterVoiceNotes(requireContext(),voices);
        recordavoicenote.rvVoicenotes.setLayoutManager(new LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false));
        recordavoicenote.rvVoicenotes.setAdapter(adapterVoiceNotes);
        return recordavoicenote.getRoot();
    }


    private void ShowDialogRecord() {

        Dialog dialog=new Dialog(requireContext());
        DialogRecordBinding recordBinding=DialogRecordBinding.inflate(getLayoutInflater());
        dialog.setContentView(recordBinding.getRoot());
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
       //set path for rec file and we select here download files
        pathRec= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath()+File.separator+ UUID.randomUUID().toString()+"_audio.3gp";
        //initialize media recorder
        setMediaRecorder();
        //btn recorder
        recordBinding.recordbtn.setOnClickListener(v->{
            if (TextUtils.isEmpty(recordBinding.notevoiceET.getText())){
                Toast.makeText(requireContext(), "add note or title first", Toast.LENGTH_SHORT).show();
                recordBinding.notevoiceET.setError("");
            }else {
                //prevent user from cancel
                dialog.setCancelable(false);
                recordBinding.recordbtn.setVisibility(View.GONE);
                recordBinding.stopbtn.setVisibility(View.VISIBLE);
                note=recordBinding.notevoiceET.getText().toString();


                try {
                    mediaRecorder.prepare();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                mediaRecorder.start();


            }


        });
        recordBinding.stopbtn.setOnClickListener(v->{

                mediaRecorder.stop();
                mediaRecorder.release();
                mediaRecorder=null;
         //to let user dismiss
            dialog.setCancelable(true);

            dialog.dismiss();

            voices.add(new objectsMediaNotes(note,pathRec));
            adapterVoiceNotes.notifyDataSetChanged();
        });
    }

    private void setMediaRecorder() {
        //initialize media recorder
        mediaRecorder=new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
mediaRecorder.setOutputFile(pathRec);
    }
}