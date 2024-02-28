package com.ahmad.todoapp.auth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;

import com.ahmad.todoapp.Home;
import com.ahmad.todoapp.R;
import com.ahmad.todoapp.adapter.FragmentsAdapter;
import com.ahmad.todoapp.auth.database.FirebaseAuthManager;
import com.ahmad.todoapp.dashboard.Dashboard;
import com.ahmad.todoapp.databinding.AuthmanagerBinding;

public class Authmanager extends AppCompatActivity {
FragmentsAdapter adapter;
AuthmanagerBinding authmanagerBinding;

    @Override
    protected void onStart() {
        FirebaseAuthManager authManager=new FirebaseAuthManager();
        if (authManager.user()!=null){
            startActivity(new Intent(this, Home.class));
            super.onStart();
        }
        super.onStart();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        authmanagerBinding=AuthmanagerBinding.inflate(getLayoutInflater());
        setContentView(authmanagerBinding.getRoot());
        adapter=new FragmentsAdapter(getSupportFragmentManager(),getLifecycle());
        adapter.addFragmentsBottom(new login());
        adapter.addFragmentsBottom(new signup());
        authmanagerBinding.fragmentAuth.setAdapter(adapter);
        authmanagerBinding.fragmentAuth.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                swithLayout(position);
                super.onPageSelected(position);
            }
        });
authmanagerBinding.bottomNavigationView.setOnItemSelectedListener(item -> {
     switchPager(item.getItemId());
    return true;  });

    }

    private void switchPager(int itemId) {
if (itemId==R.id.signup){
    authmanagerBinding.fragmentAuth.setCurrentItem(1,true);
}else {
    authmanagerBinding.fragmentAuth.setCurrentItem(0,true);

}
    }

    private void swithLayout(int position) {
        switch (position){
            case 0:
             authmanagerBinding.bottomNavigationView.setSelectedItemId(R.id.login);
             break;
            case 1:
                authmanagerBinding.bottomNavigationView.setSelectedItemId(R.id.signup);
                break;
            default:
                authmanagerBinding.bottomNavigationView.setSelectedItemId(R.id.login);
break;

        }
    }

}