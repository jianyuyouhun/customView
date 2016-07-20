package com.jianyuyouhun.library.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.jianyuyouhun.library.R;

/**
 * Created by 王宇 on 2016/7/20.
 */
public class UCloading extends View {
    private Paint paint;//画笔
    private int mColor;//填充色
    private int mBackgroundColor;//背景色
    private int mDuring;//周期
    private int speed;//切换速度-由周期计算得到
    private int leftRadius;//左圆半径
    private int rightRadius;//右圆半径
    private int maxRadius;
    private int minRadius;
    private int centre;//画布中心
    private int circle_centre;//左圆心到左边距
    private Handler handler;
    private boolean flag = false;//切换标志
    private boolean handlerFlag = false;//第一次开启动画时给各个半径赋值
    private boolean autoStart = true;//当代码中修改了参数时就靠这个停止动画了，然后手动开启。
    public UCloading(Context context){
        this(context, null);
    }

    public UCloading(Context context, AttributeSet attrs){
        this(context, attrs, 0);
    }

    public UCloading(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.UCloading, defStyleAttr, 0);
        int n = a.getIndexCount();
        for (int i = 0; i < n; i++){
            int attr = a.getIndex(i);
            if (attr == R.styleable.UCloading_mColor) {
                mColor = a.getColor(attr, Color.BLUE);
            }else if (attr == R.styleable.UCloading_mBackgroundColor){
                mBackgroundColor = a.getColor(attr, Color.GRAY);
            }else if (attr == R.styleable.UCloading_mDuring){
                mDuring = a.getInt(attr, 300);
            }
        }
        a.recycle();
        paint = new Paint();
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case 1:
                        if (!handlerFlag){
                            leftRadius = minRadius;
                            rightRadius = maxRadius;
                            handlerFlag=true;
                            start();
                        }else {
                            start();
                        }
                        break;
                }
            }
        };
    }

    public void setLoadColor(int color){
        this.mColor = color;
        autoStart = false;
    }
    public void setLoadBackgroundColor(int color){
        this.mBackgroundColor = color;
        autoStart = false;
    }
    public void setDuring(int during){
        this.mDuring = during;
        autoStart = false;
    }
    public void startLoading(){
        autoStart = true;
        postInvalidate();
    }
    public void stopLoading(){
        autoStart = false;
    }
    private void start(){
        new Thread(){
            @Override
            public void run() {
                if (flag){
                    leftRadius--;
                    rightRadius++;
                    if (leftRadius==minRadius){
                        flag = false;
                    }
                }else {
                    leftRadius++;
                    rightRadius--;
                    if (leftRadius==maxRadius){
                        flag = true;
                    }
                }
                try {
                    Thread.sleep(speed);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                postInvalidate();
            }
        }.start();
    }

    private void initData(){
        centre = getWidth()/2;
        circle_centre = centre/2;//左边圆心到左边距离
        maxRadius = (int) (circle_centre * 0.8f);
        minRadius = (int) (circle_centre * 0.5f);
        int range = maxRadius-minRadius;
        speed = mDuring / range;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        initData();
        if (autoStart){
            handler.sendEmptyMessage(1);

            paint.setStrokeWidth(1);//画笔宽度
            paint.setColor(mColor);//填充颜色
            paint.setAntiAlias(true);//消除锯齿
            paint.setStyle(Paint.Style.FILL);//填充

            canvas.drawCircle(circle_centre, centre, leftRadius, paint);

            paint.setColor(mBackgroundColor);
            canvas.drawCircle(3 * circle_centre, centre, rightRadius, paint);

        }
    }
}
