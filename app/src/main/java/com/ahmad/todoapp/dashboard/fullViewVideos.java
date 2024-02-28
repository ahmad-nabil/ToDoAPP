package com.ahmad.todoapp.dashboard;

import static android.app.Activity.RESULT_OK;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.widget.VideoView;

import com.ahmad.todoapp.R;
import com.ahmad.todoapp.adapter.adapterVideoNotes;
import com.ahmad.todoapp.databinding.DialogAddVideoNotesBinding;
import com.ahmad.todoapp.databinding.FullViewVideosBinding;

import java.net.URI;
import java.util.ArrayList;

public class fullViewVideos extends Fragment implements View.OnClickListener {
    FullViewVideosBinding viewVideosBinding;
    Uri VideoUri;
    String notes="" ;
    ArrayList<objectsMediaNotes>listVideo=new ArrayList<>();
    adapterVideoNotes adapterVideoNotes;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
     //inflate layout
        viewVideosBinding=FullViewVideosBinding.inflate(inflater,container,false);
        //add listener
        viewVideosBinding.addVideo.setOnClickListener(this);
        //initialize adapter and Recycle view
        adapterVideoNotes=new adapterVideoNotes(listVideo,requireContext(),requireActivity().getSupportFragmentManager());
        viewVideosBinding.rvVideosView.setLayoutManager(new LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false));
        viewVideosBinding.rvVideosView.setAdapter(adapterVideoNotes);
        return viewVideosBinding.getRoot();
    }


    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.addVideo){
         showDialogAddVideo();
        }
    }
//show dialog for record or upload gallery
    private void showDialogAddVideo() {
        Dialog AddVideo =new Dialog(requireContext());
        DialogAddVideoNotesBinding addVideoNotes=DialogAddVideoNotesBinding.inflate(getLayoutInflater());
        AddVideo.setContentView(addVideoNotes.getRoot());
AddVideo.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        AddVideo.show();
        addVideoNotes.RecVideo.setOnClickListener(v->{
            if(TextUtils.isEmpty(addVideoNotes.notesvideo.getText())){
                addVideoNotes.notesvideo.setError("add title or note");
            }else {
                notes=addVideoNotes.notesvideo.getText().toString();
                Intent intent=new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                pickVideo.launch(intent);
                AddVideo.dismiss();
            }
        });
        addVideoNotes.uploadvideo.setOnClickListener(v->{
            if(TextUtils.isEmpty(addVideoNotes.notesvideo.getText())){
                addVideoNotes.notesvideo.setError("add title or note");
            }else {
                notes=addVideoNotes.notesvideo.getText().toString();

                Intent intent=new Intent(Intent.ACTION_PICK);
                intent.setType("video/*");
pickVideo.launch(intent);
AddVideo.dismiss();
            }
        });

    }
    //get result
    ActivityResultLauncher<Intent>pickVideo=registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
if (result.getResultCode()==RESULT_OK){
VideoUri=result.getData().getData();
if (VideoUri!=null){
    Toast.makeText(requireContext(), "added notes", Toast.LENGTH_SHORT).show();
    listVideo.add(new objectsMediaNotes(notes,VideoUri.toString()));
    adapterVideoNotes.notifyDataSetChanged();
}
}
        }
    });
}