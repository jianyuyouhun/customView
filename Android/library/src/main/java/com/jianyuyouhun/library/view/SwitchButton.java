package com.jianyuyouhun.library.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import com.jianyuyouhun.library.R;

/**
 * Created by jianyuyouhun on 2016/9/23.
 */
public class SwitchButton extends View implements View.OnClickListener {
    /** 线条颜色 */
    private int mPaintColor;
    /** 填充颜色 */
    private int mColor;
    /** 阴影值 */
    private int mShadow;
    /** 是否有阴影 */
    private boolean hasShdow;
    /** 状态 */
    private boolean isOpen = false;
    /** 画笔 */
    private Paint mPaint;
    /** 线条宽度 */
    private int mBorderWidth;
    /** 矩形 */
    private Rect mRect;
    /** 圆半径 */
    private int mRadius;

    private Circle offCircle;
    private Circle onCircle;

    public SwitchButton(Context context){
        this(context, null);
    }

    public SwitchButton(Context context, AttributeSet attrs){
        this(context, attrs, 0);
    }

    public SwitchButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initData(context, attrs, defStyleAttr);
        initTools();
        this.setOnClickListener(this);
    }

    /**
     * 初始化数据
     */
    public void initData(Context context, AttributeSet attrs, int defStyleAttr){
        TypedArray array = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.SwitchButton, defStyleAttr, 0);
        int n = array.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = array.getIndex(i);
            if (attr == R.styleable.SwitchButton_mColor){
                mColor = array.getColor(attr, Color.GRAY);
            }else if (attr == R.styleable.SwitchButton_mShadow){
                mShadow = array.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics()));
            }else if (attr == R.styleable.SwitchButton_hasShadow){
                hasShdow = array.getBoolean(attr, false);
            }else if (attr == R.styleable.SwitchButton_mBorderWidth){
                mBorderWidth = array.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP, 6, getResources().getDisplayMetrics()));
            }else if (attr == R.styleable.SwitchButton_mPaintColor){
                mPaintColor = array.getColor(attr, Color.argb(255, 155, 155, 155));
            }
        }
        array.recycle();
    }

    /**
     * 初始化工具组件
     */
    public void initTools(){
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mRadius = getHeight()/2;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width;
        int height;

        int minWidth;
        if (heightMode == MeasureSpec.EXACTLY){
            height = heightSize;
            minWidth = (int) (height*1.5);
        } else {
            height = 96;
            minWidth = (int) (height*1.5);
        }

        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
            if (width<minWidth){
                width = minWidth;
            }
        } else {
            width = minWidth;
        }
        setMeasuredDimension(width, height);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        int width = getWidth();
        int height = getHeight();
        initCircle(width, height);
        mRadius = getHeight()/2;
        mPaint.setColor(Color.argb(155, 155, 155, 155));
        mRect = new Rect(0, 0, width, height);
        RectF mRectf = new RectF(mRect);
        canvas.drawRoundRect(mRectf, mRadius, mRadius, mPaint);
//        mPaint.setColor(Color.WHITE);

        RectF rectf = new RectF(mBorderWidth, mBorderWidth, width-mBorderWidth, height-mBorderWidth);
        if (isOpen){
            mPaint.setStyle(Paint.Style.FILL);
            mPaint.setColor(mColor);
        }else {
            mPaint.setStyle(Paint.Style.FILL);
            mPaint.setColor(Color.WHITE);
        }
        canvas.drawRoundRect(rectf, mRadius-mBorderWidth, mRadius-mBorderWidth, mPaint);


        Paint circlePaint = new Paint();
        circlePaint.setShadowLayer(68,100,100,Color.GRAY);
        circlePaint.setStrokeWidth(mBorderWidth);
        circlePaint.setColor(Color.argb(155, 155, 155, 155));
        circlePaint.setAntiAlias(true);
        circlePaint.setStyle(Paint.Style.STROKE);
        if (isOpen){
            canvas.drawCircle(onCircle.centre[0], onCircle.centre[1], onCircle.radius-mBorderWidth, circlePaint);
            circlePaint.setColor(Color.WHITE);
            circlePaint.setStyle(Paint.Style.FILL);
            canvas.drawCircle(onCircle.centre[0], onCircle.centre[1], (float) (onCircle.radius-mBorderWidth), circlePaint);
        }else {
            canvas.drawCircle(offCircle.centre[0], offCircle.centre[1], offCircle.radius-mBorderWidth, circlePaint);
            circlePaint.setColor(Color.WHITE);
            circlePaint.setStyle(Paint.Style.FILL);
            canvas.drawCircle(offCircle.centre[0], offCircle.centre[1], (float) (offCircle.radius-mBorderWidth), circlePaint);
        }
    }

    @Override
    public void onClick(View v) {
        if (isOpen){
            isOpen = false;
        }else {
            isOpen = true;
        }
        invalidate();
    }

    public void initCircle(int width, int height){
        int r = height/2;
        offCircle = new Circle();
        offCircle.radius = r;
        offCircle.centre = new float[]{r, r};
        onCircle = new Circle();
        onCircle.radius = r;
        onCircle.centre = new float[]{width-r, r};
    }

    private class Circle{
        int radius;
        float[] centre;
    }
}
