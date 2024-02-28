package com.ahmad.todoapp.auth.database;

import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FirebaseAuthManager {
    FirebaseAuth auth;

    public FirebaseAuthManager() {
        auth=FirebaseAuth.getInstance();
    }
    public Task<AuthResult>Login(String Email, String password){
        return auth.signInWithEmailAndPassword(Email,password);
    }
    public Task<AuthResult>signup(String Email, String password){
        return auth.createUserWithEmailAndPassword(Email,password);
    }
    public Task<Void>restPassword(String Email){
        return auth.sendPasswordResetEmail(Email);
    }
public FirebaseUser user(){
     return auth.getCurrentUser();
    }
}
