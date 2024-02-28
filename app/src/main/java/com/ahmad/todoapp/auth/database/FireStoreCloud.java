package com.ahmad.todoapp.auth.database;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

public class FireStoreCloud {
FirebaseFirestore FireStore;

    public FireStoreCloud() {
        FireStore=FirebaseFirestore.getInstance();
    }
    public Task<Void>Adduser(UserInformation userInformation,String Uid){
        return FireStore.collection("Users").document(Uid).set(userInformation);
    }

}
