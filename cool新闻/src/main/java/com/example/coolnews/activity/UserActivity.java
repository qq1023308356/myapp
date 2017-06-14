package com.example.coolnews.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.coolnews.MyApplication;
import com.example.coolnews.R;
import com.example.coolnews.view.MyImageView;

public class UserActivity extends BaseActivity{
    private MyImageView myImageView;
    private TextView username,usersex,conent;
    @Override
    public void viewClick(View v) {
        switch (v.getId()){
            case R.id.userimg:
                startActivity(LoginActivity.class);
                break;
        }
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
        return R.layout.activity_user;
    }

    @Override
    public void initView(View view) {
        myImageView=$(R.id.userimg);
        username=$(R.id.username);
        usersex=$(R.id.usersex);
        conent=$(R.id.conent);
    }

    @Override
    public void setListener() {
        myImageView.setOnClickListener(this);
    }

    @Override
    public void doBusiness(Context mContext) {
        if (MyApplication.MyUser!=null){
            myImageView.setImageResource(R.mipmap.logo);
            username.setText(MyApplication.MyUser.getUsername());
            usersex.setText(MyApplication.MyUser.getSex());
        }else {
            conent.setText("未登录,点击头像登录");
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (MyApplication.MyUser!=null){
            myImageView.setImageResource(R.mipmap.logo);
            username.setText(MyApplication.MyUser.getUsername());
            usersex.setText(MyApplication.MyUser.getSex());
        }else {
            conent.setText("未登录,点击头像登录");
        }
    }
}
