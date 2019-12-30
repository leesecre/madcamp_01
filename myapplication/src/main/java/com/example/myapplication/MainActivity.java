package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.widget.GridView;
import android.widget.Toast;


import com.example.myapplication.fragments.PageOneFragment;
import com.example.myapplication.fragments.PageThreeFragment;
import com.example.myapplication.fragments.PageTwoFragment;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    Fragment pgoneFrag;
    Fragment pgtwoFrag;
    Fragment pgthrFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.tab_icon1));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.tab_icon2));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.tab_icon3));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        mViewPager = (ViewPager) findViewById(R.id.container);
        TabPagerAdapter pagerAdapter = new TabPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        mViewPager.setAdapter(pagerAdapter);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener(){
            @Override
            public void onTabSelected(TabLayout.Tab tab){
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab){

            }
            @Override
            public void onTabReselected(TabLayout.Tab tab){

            }
        });*/
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.getCurrentItem();

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        pgoneFrag = new PageOneFragment();
        pgtwoFrag = new PageTwoFragment();
        pgthrFrag = new PageThreeFragment();








    }


    public class SectionsPagerAdapter extends FragmentStatePagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return pgoneFrag;
            } else if (position == 1) {
                return pgtwoFrag;
            } else if (position == 2) {
                return pgthrFrag;
            }
            return null;
        }

        @Override
        public int getCount() {
            return 3;
        }
    }

}

