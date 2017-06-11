package com.example.coolnews.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.coolnews.R;
import com.example.coolnews.tool.SharedPreferenceUtil;
import com.suke.widget.SwitchButton;

public class MySettings extends BaseActivity {
    private Toolbar toolbar;
    private TextView font,cache,listanim;
    private SwitchButton switchButton,listanimSwitchButton;
    @Override
    public void viewClick(View v) {
        switch (v.getId()){
            case R.id.font:
                break;
            case R.id.cache:
                break;
            case R.id.listanim:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setSingleChoiceItems(R.array.listanim, SharedPreferenceUtil.getlistanim(MySettings.this), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        SharedPreferenceUtil.setlistanim(MySettings.this,which);
                        Snackbar.make(listanim,"设置成功,下拉刷新生效",Snackbar.LENGTH_SHORT).show();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
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
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        listanim.setText(listanim.getText()+"("+this.getResources().getStringArray(R.array.listanim)[SharedPreferenceUtil.getlistanim(MySettings.this)]+")");
        switchButton.setChecked(!SharedPreferenceUtil.getWifiBoolean(MySettings.this));
        listanimSwitchButton.setChecked(SharedPreferenceUtil.getanimBoolean(MySettings.this));
        switchButton.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                SharedPreferenceUtil.setWifiBoolean(MySettings.this,isChecked);
                Snackbar.make(listanim,"设置成功,下拉刷新生效",Snackbar.LENGTH_SHORT).show();
            }
        });
        listanimSwitchButton.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                SharedPreferenceUtil.setanimBoolean(MySettings.this,isChecked);
                Snackbar.make(listanim,"设置成功,下拉刷新生效",Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void doBusiness(Context mContext) {

    }
}
