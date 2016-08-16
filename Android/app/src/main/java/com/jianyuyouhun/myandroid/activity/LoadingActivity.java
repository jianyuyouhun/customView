package com.jianyuyouhun.myandroid.activity;

import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.jianyuyouhun.library.view.UCLoadingView;
import com.jianyuyouhun.library.view.ZoomFloatingActionBar;
import com.jianyuyouhun.myandroid.R;

public class LoadingActivity extends AppCompatActivity {
    boolean flag = true;
    boolean isHiden = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        final UCLoadingView loading = (UCLoadingView) findViewById(R.id.loading);

        final UCLoadingView loading1 = (UCLoadingView) findViewById(R.id.loading1);
        loading1.setLoadColor(getResources().getColor(R.color.colorPrimaryDark));
        loading1.setLoadBackgroundColor(Color.GRAY);
        loading1.setDuring(500);

        final ZoomFloatingActionBar fab = (ZoomFloatingActionBar) findViewById(R.id.fab);

        final Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag){
                    loading1.startLoading();
                    button.setText("stop");
                    flag = false;
                    fab.hide();
                }else {
                    loading1.stopLoading();
                    button.setText("start");
                    flag = true;
                    fab.show();
                }
            }
        });

    }
}
