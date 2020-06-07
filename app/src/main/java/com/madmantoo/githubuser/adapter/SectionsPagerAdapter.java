package com.madmantoo.githubuser.adapter;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.madmantoo.githubuser.R;
import com.madmantoo.githubuser.activity.FollowersFragment;
import com.madmantoo.githubuser.activity.FollowingFragment;

public class SectionsPagerAdapter extends FragmentPagerAdapter {

    private final Context mContex;
    private String userName = "user";
    public static final String USERNAME = "username";

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        mContex = context;
    }


    @StringRes
    private final int[] TAB_TITLES = new int[]{
            R.string.following,
            R.string.followers
    };

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new FollowingFragment();
                Bundle bundle = new Bundle();
                bundle.putString(USERNAME, getData());
                fragment.setArguments(bundle);
                break;
            case 1:
                fragment = new FollowersFragment();
                Bundle mBundle = new Bundle();
                mBundle.putString(USERNAME, getData());
                fragment.setArguments(mBundle);
                break;
        }

        return fragment;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContex.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        return 2;
    }

    public void setData(String username) {
        userName = username;
    }

    public String getData() {
        return userName;
    }
}
