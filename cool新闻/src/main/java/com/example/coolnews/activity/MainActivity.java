package com.example.coolnews.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.coolnews.R;
import com.example.coolnews.fragment.Main_a;
import com.github.florent37.materialviewpager.MaterialViewPager;
import com.github.florent37.materialviewpager.header.HeaderDesign;
import com.nightonke.boommenu.BoomButtons.BoomButton;
import com.nightonke.boommenu.BoomButtons.SimpleCircleButton;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.OnBoomListener;

import static com.example.coolnews.MyApplication.mUserList;
import static com.example.coolnews.MyApplication.mViewPager;

public class MainActivity extends BaseActivity{
    private BoomMenuButton bmb ;
    private int[] ints={R.drawable.ic_favorite_border_black_24dp,R.drawable.ic_settings_black_24dp,R.drawable.ic_person_outline_black_24dp};



    @Override
    public void viewClick(View v) {

    }

    @Override
    public void initParams(Bundle bundle) {
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initView(View view) {
        mViewPager=$(R.id.materialViewPager);
        bmb = $(R.id.bmb);
    }

    @Override
    public void setListener() {
        mViewPager.getViewPager().setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return Main_a.newInstance(mUserList.get(position).getId());
            }

            @Override
            public int getCount() {
                return mUserList.size();
            }
            @Override
            public CharSequence getPageTitle(int position) {
                return mUserList.get(position).getName();
            }
        });
        mViewPager.setMaterialViewPagerListener(new MaterialViewPager.Listener() {
            @Override
            public HeaderDesign getHeaderDesign(int page) {
                switch (page%3) {
                    case 0:
                        return HeaderDesign.fromColorResAndDrawable(R.color.red,getDrawable(R.mipmap.bg));
                    case 1:
                        return HeaderDesign.fromColorResAndDrawable(R.color.blue,getDrawable(R.mipmap.bg));
                    case 2:
                        return HeaderDesign.fromColorResAndDrawable(R.color.cyan,getDrawable(R.mipmap.bg));
                }

                return null;
            }
        });
        for (int i = 0; i < bmb.getPiecePlaceEnum().pieceNumber(); i++) {
            SimpleCircleButton.Builder builder = new SimpleCircleButton.Builder()
                    .normalImageRes(ints[i]);
            bmb.addBuilder(builder);
        }
        bmb.setOnBoomListener(new OnBoomListener() {
            @Override
            public void onClicked(int index, BoomButton boomButton) {
                switch (index){
                    case 0:
                        startActivity(MyFavorite.class);
                        break;
                    case 1:
                        startActivity(MySettings.class);
                        break;
                }
            }

            @Override
            public void onBackgroundClick() {

            }

            @Override
            public void onBoomWillHide() {

            }

            @Override
            public void onBoomDidHide() {

            }

            @Override
            public void onBoomWillShow() {

            }

            @Override
            public void onBoomDidShow() {

            }
        });
    }

    @Override
    public void doBusiness(Context mContext) {
        Toolbar toolbar = mViewPager.getToolbar();
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setDisplayUseLogoEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }
        mViewPager.getViewPager().setOffscreenPageLimit(mViewPager.getViewPager().getAdapter().getCount());
        mViewPager.getPagerTitleStrip().setViewPager(mViewPager.getViewPager());
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            startActivity(new Intent(MainActivity.this,MyAdd.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
