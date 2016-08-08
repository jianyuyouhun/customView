package com.jianyuyouhun.myandroid.activity;

import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;

import com.jianyuyouhun.library.TestView;
import com.jianyuyouhun.library.view.BezierView;
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
                showSearchView();
                break;
            case "testView":
                showContentView();
                break;
            case "taiji":
                showTaiji();
                break;
            case "bezierView":
                showBezier();
                break;
        }
    }

    public void showSearchView(){
        SearchView searchView = new SearchView(this);
        setContentView(searchView);
    }
    public void showContentView(){
        TestView view = new TestView(this);
        setContentView(view);
    }
    public void showTaiji(){
        TaijiView view = new TaijiView(this);
        setContentView(view);
    }
    public void showBezier(){
        BezierView view = new BezierView(this);
        view.setPaintMode(1);
        setContentView(view);
    }
}
