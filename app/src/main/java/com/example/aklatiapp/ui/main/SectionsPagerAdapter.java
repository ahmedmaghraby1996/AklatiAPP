package com.example.aklatiapp.ui.main;

import android.app.Activity;
import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.aklatiapp.Meals;
import com.example.aklatiapp.Orders;
import com.example.aklatiapp.R;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2};
    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {

        if (position == 0) {
            Meals meals = new Meals();
            return meals;
        } else {
            Orders orders = new Orders();
            return orders;
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
  if(position==0)
      return "Meals";
  else
      return "My orders";
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return 2;
    }
}