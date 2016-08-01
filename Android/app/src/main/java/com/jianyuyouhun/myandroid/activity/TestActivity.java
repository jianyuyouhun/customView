package com.jianyuyouhun.myandroid.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.jianyuyouhun.library.TestView;
import com.jianyuyouhun.library.view.SearchView;
import com.jianyuyouhun.library.view.TaijiView;

/**
 * Created by 王宇 on 2016/7/22.
 */
public class TestActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        switch (getIntent().getStringExtra("type")){
            case "searchView":
                setContentView(new SearchView(this));
                break;
            case "testView":
                setContentView(new TestView(this));
                break;
            case "taiji":
                setContentView(new TaijiView(this));
                break;
        }
    }
}
