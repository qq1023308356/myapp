package com.example.coolnews;

import android.app.Application;

import com.example.coolnews.entity.NewsList;
import com.example.coolnews.entity.news;
import com.example.coolnews.entity.User;
import com.github.florent37.materialviewpager.MaterialViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiong on 17-5-26.
 */

public class MyApplication extends Application {
    public static User MyUser;
    public static MaterialViewPager mViewPager;
    public static List<NewsList> mUserList;
    public static List<NewsList> mOtherList;
    public static news newsweb;
    public static List<news> newsList=new ArrayList<>();
    @Override
    public void onCreate() {
        super.onCreate();

    }
}
