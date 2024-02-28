package com.ahmad.todoapp.todo;

import static android.app.Activity.RESULT_OK;

import android.app.Dialog;
import android.app.admin.DeviceAdminReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.health.connect.datatypes.Device;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ahmad.todoapp.R;
import com.ahmad.todoapp.adapter.adapterRvToDo;
import com.ahmad.todoapp.auth.database.FirebaseAuthManager;
import com.ahmad.todoapp.databinding.DialogupdateimgBinding;
import com.ahmad.todoapp.databinding.ToDoListBinding;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class ToDoList extends Fragment implements View.OnClickListener {
ToDoListBinding toDoListBinding;
String imgUrl;
ArrayList<objectModel>listnotes=new ArrayList<>();
adapterRvToDo adapterToDo;
Uri imgUri;
    private listenerFinishedTask finishedTask;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
     //inflate layout
        toDoListBinding=ToDoListBinding.inflate(inflater,container,false);
       //add listener to button
        toDoListBinding.addNotes.setOnClickListener(this);
        toDoListBinding.uploadimg.setOnClickListener(this);

        return toDoListBinding.getRoot();
    }

    @Override
    public void onClick(View v) {
if (v.getId()==toDoListBinding.addNotes.getId()){
  //to check if we hab=ve same note or not
    boolean found_same_notes=false;
    //check if text filed empty or not
    if (TextUtils.isEmpty(toDoListBinding.notes.getText())){
        toDoListBinding.notes.setError("add note please");
    }else {

        String  note=toDoListBinding.notes.getText().toString();
        //if we have same note will set error
        for (int position = 0; position <listnotes.size();) {
            if(listnotes.get(position).note.equals(note)){
                toDoListBinding.notes.setError("we have same note");
                found_same_notes=true;
break;
            }
            position++;  }

if (!found_same_notes){
    ProcedureAddNote();
}
    }
}else if (v.getId()==toDoListBinding.uploadimg.getId()){
    ChooseImg();
}
    }
//initialize dialog upload img
    private void ChooseImg() {
        Dialog dialog=new Dialog(requireContext());
        DialogupdateimgBinding dialogupdateimgBinding=DialogupdateimgBinding.inflate(getLayoutInflater());
        dialog.setContentView(dialogupdateimgBinding.getRoot());
        dialog.show();
        dialogupdateimgBinding.cameraBtn.setOnClickListener(v->{
            openCamera();
            dialog.dismiss();
        });
        dialogupdateimgBinding.gallerybtn.setOnClickListener(v->{
            openGallery();
            dialog.dismiss();
        });

    }

    private void openGallery() {
        Intent opengal=new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        pick.launch(opengal);

    }

    private void openCamera() {
Intent openCam=new Intent(MediaStore.ACTION_IMAGE_CAPTURE) ;
pick.launch(openCam);

    }

    private void ProcedureAddNote() {
        String note=toDoListBinding.notes.getText().toString();
        if (imgUri==null){
            listnotes.add(new objectModel(note,"null"));
        }else {
            listnotes.add(new objectModel(note,imgUri.toString()));
        }
        adapterToDo=new adapterRvToDo(listnotes,requireContext(),   finishedTask);
toDoListBinding.recyclerView.setLayoutManager(new LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false));
toDoListBinding.recyclerView.setAdapter(adapterToDo);
    restState();
    }



    ActivityResultLauncher<Intent>pick=registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
         if (result.getResultCode()==RESULT_OK){
             imgUri=result.getData().getData();
             if (imgUri!=null){
                 Glide.with(requireContext()).load(imgUri).into(toDoListBinding.imgnote);
             }else {
                 Bundle bundle=result.getData().getExtras();
                 if (bundle!=null){
                     Bitmap bit= (Bitmap) bundle.get("data");
                     Glide.with(requireContext()).load(bit).into(toDoListBinding.imgnote);
                     imgUri=ConvertUri(requireContext(),bit);

                 }
             }
         }
        }
    }) ;
//convert img to uri if we choose cam not from gallery
    private Uri ConvertUri(Context context, Bitmap bit) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bit.compress(Bitmap.CompressFormat.PNG, 50, outputStream);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), bit, "imgtemp", null);
        return Uri.parse(path);
    }
//rest state values
    private void restState() {
        toDoListBinding.notes.setText("");
        toDoListBinding.notes.setHint("write your note");
        toDoListBinding.imgnote.setImageDrawable(new ColorDrawable(Color.TRANSPARENT));
        imgUri=null;
    }
    //initialize  interface
    public void setFinishedTask(listenerFinishedTask finishedTask){
        this.finishedTask=finishedTask;
    }


}