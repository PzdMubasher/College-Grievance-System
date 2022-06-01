package com.pzd.collegegrievance;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class FragmentAdapter extends FragmentStateAdapter {
    public FragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        switch (position){
            case 1:
                return new HostelFragment();
        }
        switch (position){
            case 2:
                return new ResultFragment();
        }

        return new CollegeFragment();

    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
