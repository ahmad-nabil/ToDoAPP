package com.ahmad.todoapp.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.List;

public class FragmentsAdapter extends FragmentStateAdapter {
    List<Fragment> fragments =new ArrayList<>();
   List<String> titles =new ArrayList<>();



    public FragmentsAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }
public void addFragmentsBottom(Fragment fragment){
      fragments.add(fragment);
}
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragments.get(position);
    }

    @Override
    public int getItemCount() {
        return fragments.size();
    }
    public void addFragmentsTab(Fragment fragment,String title){
        fragments.add(fragment);
        titles.add(title);
    }
    @Nullable
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }
}
