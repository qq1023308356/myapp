package com.example.coolnews.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.coolnews.R;
import com.suke.widget.SwitchButton;

public class MySettings extends BaseActivity {
    private Toolbar toolbar;
    private TextView font,cache,listanim,carouselanim;
    private SwitchButton switchButton,listanimSwitchButton;
    @Override
    public void viewClick(View v) {
        switch (v.getId()){
            case R.id.font:
                break;
            case R.id.cache:
                break;
            case R.id.listanim:
                break;
            case R.id.carouselanim:
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
        return R.layout.activity_my_settings;
    }

    @Override
    public void initView(View view) {
        toolbar=$(R.id.toolbar);
        font=$(R.id.font);
        cache=$(R.id.cache);
        listanim=$(R.id.listanim);
        carouselanim=$(R.id.carouselanim);
        switchButton=$(R.id.switch_button);
        listanimSwitchButton=$(R.id.listanim_switch_button);
        setSupportActionBar(toolbar);
        //关键下面两句话，设置了回退按钮，及点击事件的效果
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void setListener() {
        font.setOnClickListener(this);
        cache.setOnClickListener(this);
        listanim.setOnClickListener(this);
        carouselanim.setOnClickListener(this);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        switchButton.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                if (isChecked){
                    Toast.makeText(MySettings.this, "666", Toast.LENGTH_SHORT).show();
                }
            }
        });
        listanimSwitchButton.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {

            }
        });
    }

    @Override
    public void doBusiness(Context mContext) {

    }
}
