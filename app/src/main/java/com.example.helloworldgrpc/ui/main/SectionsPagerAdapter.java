package com.example.helloworldgrpc.ui.main;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.helloworldgrpc.MainActivity2;
import com.example.helloworldgrpc.R;
import com.example.helloworldgrpc.ui.main.PlaceholderFragment;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2, R.string.tab_text_2, R.string.tab_text_2};
    private final Context mContext;
    private MainActivity2 mainActivity2;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
/*        switch (position) {
            //case 0 : return (Fragment) MainActivity2;
            case 0 : return new HomeFragment();
            case 1 : return new HomeFragment();
            case 2 : return new HomeFragment();
            case 3 : return new HomeFragment();
            default: return new Fragment();
        }*/
        return PlaceholderFragment.newInstance(position + 1);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0: return "C";
            case 1: return "C++";
            case 2: return "Java";
            case 3 : return "Python";
        }
        return "";
        //return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        return 4;
    }
}