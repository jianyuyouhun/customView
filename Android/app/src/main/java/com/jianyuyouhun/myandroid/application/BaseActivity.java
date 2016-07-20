package com.jianyuyouhun.myandroid.application;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by 王宇 on 2016/7/20.
 */
public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initActivity();
    }

    public void initActivity(){
        bindView();
        bindData();
        initData();
    }

    public void bindView(){}

    public void bindData(){}

    public void initData(){}

}
