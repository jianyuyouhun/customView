package com.jianyuyouhun.library;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;

import com.gcssloop.view.CustomView;
import com.gcssloop.view.utils.CanvasAidUtils;

/**
 * Created by 王宇 on 2016/7/23.
 */
public class TestView extends CustomView{
    private Paint paint;
    private Paint paint2;
    private int x0;
    private int y0;
    private float[] mCurrentPosition;
    private PathMeasure measure;
    private Path path;
    private Path locus;
    private boolean flag = false;
    public TestView(Context context) {
        this(context, null);
    }
    public TestView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // 初始化画笔
        initPaint();
        initPath();
    }
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    if (!flag){
                        startAni();
                        flag=true;
                    }
                    break;
            }
        }
    };
    public void initPaint(){
        mDefaultTextPaint.setColor(Color.GRAY);
        paint = new Paint();
        paint.setColor(Color.GRAY);
        paint.setStrokeWidth(6);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint2 = new Paint();
        paint2.setColor(Color.WHITE);
        paint2.setStrokeWidth(6);
        paint2.setAntiAlias(true);
        paint2.setStyle(Paint.Style.STROKE);
    }

    public void initPath(){
        path = new Path();
        locus = new Path();
        mCurrentPosition = new float[2];
    }

    public void startAni(){
        ValueAnimator animator = ValueAnimator.ofFloat(0, measure.getLength());
        animator.setDuration(12000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (Float) animation.getAnimatedValue();
                // 获取当前点坐标封装到mCurrentPosition
                measure.getPosTan(value, mCurrentPosition, null);
                invalidate();
            }
        });
        animator.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 获取View宽高与画笔并根据此绘制内容

        x0 = mViewWidth/360;
        y0 = mViewHeight/16;

        canvas.translate(mViewWidth / 2, mViewHeight / 2);
        canvas.scale(1,-1);

        CanvasAidUtils.setLineColor(Color.RED);
        CanvasAidUtils.drawCoordinateSystem(canvas);

        int x;
        int y;

        path.moveTo(0,0);
        for (int i = 0; i < 160; i++){
            x = i*x0;
            y = (int) (Math.sin(0.1*i)*y0);
            path.lineTo(x,y);
        }

        measure = new PathMeasure(path, false);

        handler.sendEmptyMessage(1);
        locus.lineTo(mCurrentPosition[0], mCurrentPosition[1]);

        canvas.drawPath(locus, paint);

    }
}
