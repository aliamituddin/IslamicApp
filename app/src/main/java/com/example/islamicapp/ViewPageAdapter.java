package com.example.islamicapp;



import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewPageAdapter extends FragmentPagerAdapter {
    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mTitleList = new ArrayList<>();

    public ViewPageAdapter(FragmentManager fm) {

        super(fm);

    }

    @Override
    public Fragment getItem(int postion) {
        return mFragmentList.get(postion);
    }

    @Override
    public int getCount() {
        return  mTitleList.size();
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return mTitleList.get(position);

    }
    public void AddFragment(Fragment fragment, String title ){
        mFragmentList.add(fragment);
        mTitleList.add(title);

    }
}

