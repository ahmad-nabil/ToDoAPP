package com.ahmad.todoapp.auth;

import static android.app.Activity.RESULT_OK;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ahmad.todoapp.Home;
import com.ahmad.todoapp.R;
import com.ahmad.todoapp.auth.database.FireStoreCloud;
import com.ahmad.todoapp.auth.database.FirebaseAuthManager;
import com.ahmad.todoapp.auth.database.UserInformation;
import com.ahmad.todoapp.databinding.DialogupdateimgBinding;
import com.ahmad.todoapp.databinding.SignupBinding;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

public class signup extends Fragment implements View.OnClickListener {
    SignupBinding signupBinding;
    FirebaseAuthManager authManager;
    FireStoreCloud storeCloud;
    Uri imgUri;
    UserInformation userInformation;

String ImgUrl;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //initilize viewbinding
        signupBinding=SignupBinding.inflate(inflater,container,false);
signupBinding.updateAvatar.setOnClickListener(this);
signupBinding.signupbtn.setOnClickListener(this);
authManager=new FirebaseAuthManager();
storeCloud=new FireStoreCloud();
userInformation=new UserInformation();
        return signupBinding.getRoot();
    }


    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.updateAvatar){
            showDialog();
        }else if(v.getId()==R.id.signupbtn){
checkfileds();
        }

    }

    private void checkfileds() {
        if (imgUri==null){
            Toast.makeText(requireContext(), "upload image please", Toast.LENGTH_SHORT).show();
        }else {
            if(!TextUtils.isEmpty(signupBinding.Emailsignup.getText())
                    &&!TextUtils.isEmpty(signupBinding.phonesignup.getText())
                    &&!TextUtils.isEmpty(signupBinding.passwordsignup.getText())
                    &&!TextUtils.isEmpty(signupBinding.fullname.getText())){
              String Email=signupBinding.Emailsignup.getText().toString().trim();
              String password=signupBinding.passwordsignup.getText().toString().trim();
                authManager.signup(Email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        userInformation.full_name=signupBinding.fullname.getText().toString().trim();
                        userInformation.Email=Email;
                                userInformation.password=password;
                                String uid=authManager.user().getUid();
                              userInformation.phone=signupBinding.phonesignup.getText().toString().trim();
                                    String path="userAvtar"+"/"+System.currentTimeMillis()+uid+".png";
                              uploadImg(path);


                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(requireContext() , "check your Email", Toast.LENGTH_SHORT).show();
                    }
                });


            }
        }
    }

    private void uploadImg(String path) {
        StorageReference firebaseStorage=FirebaseStorage.getInstance().getReference().child(path);
        firebaseStorage.putFile(imgUri).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                firebaseStorage.getDownloadUrl().addOnCompleteListener(Task -> {
                    if (Task.isSuccessful()) {
                        ImgUrl = Task.getResult().toString();
                        userInformation.imgSrc = ImgUrl;
                        UploadtoFirestore(userInformation);
                    } else {
                        userInformation.imgSrc = imgUri.toString();
                        UploadtoFirestore(userInformation);
                    }
                });
            }
        });
    }

    private void UploadtoFirestore(UserInformation userInformation) {
        FireStoreCloud fireStoreCloud=new FireStoreCloud();
        String uid = authManager.user().getUid();
        fireStoreCloud.Adduser(userInformation,uid).addOnSuccessListener(unused -> requireActivity().startActivity(new Intent(requireContext(), Home.class)));
    }

    private void showDialog() {
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
        Intent intent=new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.INTERNAL_CONTENT_URI);
pickimg.launch(intent);
    }

    private void openCamera() {
Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
pickimg.launch(intent);
    }
    ActivityResultLauncher<Intent>pickimg=registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
if (result.getResultCode()==RESULT_OK){
    imgUri=result.getData().getData();
    if (imgUri!=null){
        Glide.with(requireContext()).load(imgUri).into(signupBinding.updateAvatar);
    }else {
        Bundle bundle=result.getData().getExtras();
        if (bundle!=null){
            Bitmap bitmap= (Bitmap) bundle.get("data");
            Glide.with(requireContext()).load(bitmap).into(signupBinding.updateAvatar);
       imgUri=ConverttoUri(requireContext(),bitmap);
        }
    }
}
        }
    });

    private Uri ConverttoUri(Context context, Bitmap bitmap) {
        ByteArrayOutputStream arrayOutputStream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,arrayOutputStream);
        String path=MediaStore.Images.Media.insertImage(context.getContentResolver(),bitmap,"img",null);
return Uri.parse(path);
    }

}