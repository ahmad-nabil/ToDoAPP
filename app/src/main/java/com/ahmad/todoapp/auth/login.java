package com.ahmad.todoapp.auth;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ahmad.todoapp.Home;
import com.ahmad.todoapp.R;
import com.ahmad.todoapp.auth.database.FirebaseAuthManager;
import com.ahmad.todoapp.databinding.LoginBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class login extends Fragment {
    LoginBinding loginBinding;
    FirebaseAuthManager authManager;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        loginBinding=LoginBinding.inflate(inflater,container,false);
       authManager=new FirebaseAuthManager();
       loginBinding.loginBtn.setOnClickListener(this::login);
        return loginBinding.getRoot();
    }

    private void login(View loginBtn) {
        if (!TextUtils.isEmpty(loginBinding.EmailLogin.getText())&&
        !TextUtils.isEmpty(loginBinding.passwordLogin.getText()))
        {
            String Email=loginBinding.EmailLogin.getText().toString().trim();
            String password=loginBinding.passwordLogin.getText().toString().trim();
            authManager.Login(Email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    Toast.makeText(requireContext(), "signed in", Toast.LENGTH_SHORT).show();
               requireActivity().startActivity(new Intent(requireContext(), Home.class));
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(requireContext(), "failed ", Toast.LENGTH_SHORT).show();
                    loginBinding.EmailLogin.setError("");
                    loginBinding.passwordLogin.setError("");
                }
            });
        }else {
            if (TextUtils.isEmpty(loginBinding.EmailLogin.getText())){
                loginBinding.EmailLogin.setError("");
            }
            if (TextUtils.isEmpty(loginBinding.passwordLogin.getText())){
                loginBinding.passwordLogin.setError("");
            }
        }


    }
}