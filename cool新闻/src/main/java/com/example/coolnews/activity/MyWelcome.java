package com.example.coolnews.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.example.coolnews.MyApplication;
import com.example.coolnews.R;
import com.example.coolnews.entity.NewsList;
import com.example.coolnews.tool.SharedPreferenceUtil;

import java.util.ArrayList;

import static com.example.coolnews.MyApplication.mOtherList;
import static com.example.coolnews.MyApplication.mUserList;

public class MyWelcome extends BaseActivity{

    @Override
    public void viewClick(View v) {

    }

    @Override
    public void initParams(Bundle bundle) {
        setAllowFullScreen(true);
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_my_welcome;
    }

    @Override
    public void initView(View view) {

    }

    @Override
    public void setListener() {

    }

    @Override
    public void doBusiness(Context mContext) {
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (SharedPreferenceUtil.getmOtherList(MyWelcome.this)==null){
            mUserList=new ArrayList<>();
            mOtherList=new ArrayList<>();
            NewsList newsList1=new NewsList();
            newsList1.setId("T1348647909107");
            newsList1.setName("热点");
            mUserList.add(newsList1);
            NewsList newsList2=new NewsList();
            newsList2.setId("T1348649580692");
            newsList2.setName("科技");
            mUserList.add(newsList2);
            NewsList newsList3=new NewsList();
            newsList3.setId("T1350383429665");
            newsList3.setName("轻松一刻");
            mUserList.add(newsList3);
            NewsList newsList4=new NewsList();
            newsList4.setId("T1348648517839");
            newsList4.setName("娱乐");
            mUserList.add(newsList4);
            NewsList newsList5=new NewsList();
            newsList5.setId("T1370583240249");
            newsList5.setName("网易");
            mUserList.add(newsList5);
            NewsList newsList6=new NewsList();
            newsList6.setId("T1348649079062");
            newsList6.setName("体育");
            mOtherList.add(newsList6);
            NewsList newsList7=new NewsList();
            newsList7.setId("T1414389941036");
            newsList7.setName("健康");
            mOtherList.add(newsList7);
            NewsList newsList8=new NewsList();
            newsList8.setId("T1348654151579");
            newsList8.setName("游戏");
            mOtherList.add(newsList8);
            NewsList newsList9=new NewsList();
            newsList9.setId("T1348649145984");
            newsList9.setName("NBA");
            mOtherList.add(newsList9);
            NewsList newsList10=new NewsList();
            newsList10.setId("T1348648756099");
            newsList10.setName("商业");
            mOtherList.add(newsList10);
            NewsList newsList11=new NewsList();
            newsList11.setId("T1348654225495");
            newsList11.setName("教育");
            mOtherList.add(newsList11);
            NewsList newsList12=new NewsList();
            newsList12.setId("T1467284926140");
            newsList12.setName("精选");
            mOtherList.add(newsList12);
            NewsList newsList14=new NewsList();
            newsList14.setId("T1397016069906");
            newsList14.setName("暴雪游戏");
            mOtherList.add(newsList14);
            NewsList newsList15=new NewsList();
            newsList15.setId("T1348649176279");
            newsList15.setName("足球");
            mOtherList.add(newsList15);
            NewsList newsList16=new NewsList();
            newsList16.setId("T1348649176279");
            newsList16.setName("手机");
            mOtherList.add(newsList16);
            NewsList newsList17=new NewsList();
            newsList17.setId("T1348649776727");
            newsList17.setName("数码");
            mOtherList.add(newsList17);
            NewsList newsList18=new NewsList();
            newsList18.setId("T1411113472760");
            newsList18.setName("跑步");
            mOtherList.add(newsList18);
            NewsList newsList19=new NewsList();
            newsList19.setId("T1368497029546");
            newsList19.setName("历史");
            mOtherList.add(newsList19);
            NewsList newsList20=new NewsList();
            newsList20.setId("T1473054348939");
            newsList20.setName("股票");
            mOtherList.add(newsList20);
            NewsList newsList21=new NewsList();
            newsList21.setId("T1356600029035");
            newsList21.setName("彩票");
            mOtherList.add(newsList21);
            NewsList newsList22=new NewsList();
            newsList22.setId("T1351233117091");
            newsList22.setName("智能");
            mOtherList.add(newsList22);
            NewsList newsList23=new NewsList();
            newsList23.setId("T1348649475931");
            newsList23.setName("CBA");
            mOtherList.add(newsList23);
            NewsList newsList24=new NewsList();
            newsList24.setId("T1348649503389");
            newsList24.setName("中国足球");
            mOtherList.add(newsList24);
            NewsList newsList25=new NewsList();
            newsList25.setId("T1348654204705");
            newsList25.setName("旅游");
            mOtherList.add(newsList25);
            NewsList newsList26=new NewsList();
            newsList26.setId("T1349837698345");
            newsList26.setName("网易客博");
            mOtherList.add(newsList26);
        }else {
            mUserList= SharedPreferenceUtil.getmUserList(MyWelcome.this);
            mOtherList=SharedPreferenceUtil.getmOtherList(MyWelcome.this);
            if(SharedPreferenceUtil.getFavoriteNewlsit(MyWelcome.this)!=null) {
                MyApplication.newsList = SharedPreferenceUtil.getFavoriteNewlsit(MyWelcome.this);
            }
        }
        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    sleep(300);
                    startActivity(MainActivity.class);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}