package com.jianyuyouhun.myandroid;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.jianyuyouhun.library.TestView;
import com.jianyuyouhun.library.view.SearchView;

/**
 * Created by 王宇 on 2016/7/22.
 */
public class TestActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TestView view = new TestView(this);
//        SearchView view = new SearchView(this);
        setContentView(view);
    }
}
