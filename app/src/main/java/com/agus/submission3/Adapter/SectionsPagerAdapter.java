package com.agus.submission3.Adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.agus.submission3.FragmentFollowers;
import com.agus.submission3.FragmentFollowing;
import com.agus.submission3.R;


public class SectionsPagerAdapter extends FragmentPagerAdapter {

    private final Context mContext;
    private String username;
    public SectionsPagerAdapter(Context context, FragmentManager fm, String ArgUsername) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        mContext = context;
        username = ArgUsername;
    }

    @StringRes
    private final int[] TAB_TITLES = new int[]{
            R.string.followers,
            R.string.following
    };

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = FragmentFollowers.newInstance(username);
                break;
            case 1:
                fragment = FragmentFollowing.newInstance(username);
                break;
        }
        return fragment;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        return 2;
    }
}
