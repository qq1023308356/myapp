package com.example.coolnews.activity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.dd.processbutton.iml.ActionProcessButton;
import com.example.coolnews.Constant;
import com.example.coolnews.MyApplication;
import com.example.coolnews.R;
import com.example.coolnews.entity.User;
import com.example.coolnews.tool.SharedPreferenceUtil;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends BaseActivity{
    private EditText user,password;
    private ActionProcessButton btnSignIn;
    private TextView register;
    @Override
    public void viewClick(View v) {
        switch(v.getId()){
            case R.id.btnSignIn:
                if (user.getText().toString().isEmpty()){
                    user.setError("不能为空");
                }else if (password.getText().toString().isEmpty()){
                    password.setError("不能为空");
                }else {
                    btnSignIn.setProgress(2);
                    btnSignIn.setClickable(false);
                    OkHttpUtils.get().url(Constant.host)
                            .addParams("username",user.getText().toString())
                            .addParams("userpassword",password.getText().toString())
                            .build().execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            btnSignIn.setProgress(-1);
                            btnSignIn.setClickable(true);
                        }

                        @Override
                        public void onResponse(String response, int id) {
                            try {
                                JSONObject jsonObject=new JSONObject(response);
                                if (jsonObject.getBoolean("state")){
                                    btnSignIn.setProgress(100);
                                    Log.e("user",response);
                                    MyApplication.MyUser=new Gson().fromJson(jsonObject.getString("result"), User.class);
                                    SharedPreferenceUtil.setUser(LoginActivity.this);
                                    finish();
                                }else {
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
            case R.id.login_txtForgotPwd:
                startActivity(RegisterActivity.class);
                break;
        }
    }

    @Override
    public void initParams(Bundle bundle) {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        //getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_login;
    }

    @Override
    public void initView(View view) {
        user=$(R.id.user);
        password=$(R.id.password);
        btnSignIn=$(R.id.btnSignIn);
        register=$(R.id.login_txtForgotPwd);
    }

    @Override
    public void setListener() {
        btnSignIn.setOnClickListener(this);
        register.setOnClickListener(this);
    }

    @Override
    public void doBusiness(Context mContext) {
        if (MyApplication.MyUser!=null){
            user.setText(MyApplication.MyUser.getUsername());
            password.setText(MyApplication.MyUser.getUserpassword());
        }
    }
}

