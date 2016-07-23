package com.jianyuyouhun.library.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import com.gcssloop.view.utils.CanvasAidUtils;

/**
 * Created by 王宇 on 2016/7/20.
 */
public class TaijiView extends View{
    private int mWidth;
    private int mHeight;
    private Paint mPaint;

    private float progress = 0;
    private float speed = 0;
    private boolean flag = true;
    public TaijiView(Context context) {
        this(context, null);
    }
    public TaijiView(Context context, AttributeSet attrs){
        super(context, attrs);
        this.mPaint = new Paint();
        mPaint.setStrokeWidth(6);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.GRAY);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(mWidth / 2, mHeight / 2);  // 移动坐标系到屏幕中心(宽高数据在onSizeChanged中获取)

        progress+=speed;

        if (speed>=64){
            flag=false;
        }else if (speed<=0){
            flag=true;
        }
        if (flag){
            speed+=0.3;
        }else {
            speed-=0.3;
        }
        int a;

        if (progress>=360){
            progress=0;
        }
        canvas.rotate(progress);
        CanvasAidUtils.setLineColor(Color.RED);
        CanvasAidUtils.setLineWidth(4);
        CanvasAidUtils.drawCoordinateSystem(canvas);

        Path path_left = new Path();
        Path path_left1 = new Path();
        Path path_left2 = new Path();
        Path path_left3 = new Path();
        Path path_left4 = new Path();

        path_left.addCircle(0, -100, 50, Path.Direction.CW);
        path_left1.addCircle(0, 0, 200, Path.Direction.CW);
        path_left2.addRect(0, -200, 200, 200, Path.Direction.CW);
        path_left3.addCircle(0, -100, 100, Path.Direction.CW);
        path_left4.addCircle(0, 100, 100, Path.Direction.CCW);

        path_left1.op(path_left2, Path.Op.DIFFERENCE);
        path_left1.op(path_left3, Path.Op.UNION);
        path_left1.op(path_left4, Path.Op.DIFFERENCE);
        path_left1.op(path_left, Path.Op.DIFFERENCE);

        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawPath(path_left1, mPaint);


        Path path_right = new Path();
        Path path_right1 = new Path();
        Path path_right2 = new Path();
        Path path_right3 = new Path();
        Path path_right4 = new Path();

        path_right.addCircle(0, 100, 50, Path.Direction.CW);
        path_right1.addCircle(0, 0, 200, Path.Direction.CW);
        path_right2.addRect(0, -200, 200, -200, Path.Direction.CW);
        path_right3.addCircle(0, 100, 100, Path.Direction.CW);
        path_right4.addCircle(0, -100, 100, Path.Direction.CCW);

        path_right1.op(path_right2, Path.Op.DIFFERENCE);
        path_right1.op(path_right3, Path.Op.UNION);
        path_right1.op(path_right4, Path.Op.DIFFERENCE);
        path_right1.op(path_right, Path.Op.DIFFERENCE);

        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawPath(path_right1, mPaint);

        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawPath(path_right, mPaint);
        CanvasAidUtils.setDrawAid(false);
        invalidate();
    }

}
