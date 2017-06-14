package com.example.coolnews.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.dd.processbutton.iml.ActionProcessButton;
import com.example.coolnews.Constant;
import com.example.coolnews.R;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;

public class RegisterActivity extends BaseActivity {

    private EditText user, password, password2;
    private ActionProcessButton btnSignIn;
    private LinearLayout loginLinearLayout;
    private Spinner spinner;


    @Override
    public void viewClick(View v) {
        switch (v.getId()) {
            case R.id.btnSignIn:
                if (user.getText().toString().isEmpty()) {
                    user.setError("用户名不能为空");
                } else if (password.getText().toString().isEmpty()) {
                    password.setError("密码不能为空");
                } else if (!password.getText().toString().equals(password2.getText().toString())) {
                    password2.setError("2次密码不一致");
                } else {
                    btnSignIn.setProgress(2);
                    btnSignIn.setClickable(false);
                    OkHttpUtils.get().url(Constant.host)
                            .addParams("username", user.getText().toString())
                            .addParams("userpassword", password.getText().toString())
                            .addParams("sex", spinner.getSelectedItem().toString())
                            .build().execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            btnSignIn.setProgress(-1);
                            btnSignIn.setClickable(true);
                        }

                        @Override
                        public void onResponse(String response, int id) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                if (jsonObject.getBoolean("state")) {
                                    btnSignIn.setProgress(100);
                                    startActivity(LoginActivity.class);
                                    finish();
                                } else {
                                    btnSignIn.setProgress(-1);
                                }
                                btnSignIn.setClickable(true);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    });
                }
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
        return R.layout.activity_register;
    }

    @Override
    public void initView(View view) {
        user = $(R.id.user);
        spinner = $(R.id.sex);
        password = $(R.id.password);
        password2 = $(R.id.password2);
        btnSignIn = $(R.id.btnSignIn);
    }

    @Override
    public void setListener() {
        btnSignIn.setOnClickListener(this);
    }

    @Override
    public void doBusiness(Context mContext) {

    }
}