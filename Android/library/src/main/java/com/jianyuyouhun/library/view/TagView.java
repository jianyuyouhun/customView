package com.jianyuyouhun.library.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import com.jianyuyouhun.library.R;

import java.util.Random;

/**
 * Created by 王宇 on 2016/7/21.
 */
public class TagView extends View {
    private int mCornerRadius;//圆角
    private float scale;//比例
    private int mTextContentSize;//文字大小
    private String mTextContent="";//文字内容
    private int mTextColor;//文字颜色
    private Paint paint;
    private Rect bound;
    private int[] mColors = {0xFF35B0DC, 0xFFF09925, 0xFF32BC98, 0xFFF05A49, 0xFFC95B99, 0xFF8577C2};

    public TagView(Context context) {
        this(context, null);
    }

    public TagView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TagView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.TagView, defStyleAttr, 0);
        int n = a.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = a.getIndex(i);
            if (attr == R.styleable.TagView_mCornerRadius) {
                mCornerRadius = a.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP, 6, getResources().getDisplayMetrics()));
            } else if (attr == R.styleable.TagView_mScale) {
                scale = a.getFloat(attr, 0.8f);
            } else if (attr == R.styleable.TagView_mTextContent) {
                mTextContent = String.valueOf(a.getString(attr).charAt(0));
            } else if (attr == R.styleable.TagView_mTextContentSize) {
                mTextContentSize = a.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_SP, 18, getResources().getDisplayMetrics()));
            } else if (attr == R.styleable.TagView_mTextColor) {
                mTextColor = a.getColor(attr, Color.WHITE);
            }
        }
        a.recycle();
        paint = new Paint();
        paint.setTextSize(mTextContentSize);
        bound = new Rect();
        paint.getTextBounds(mTextContent, 0, mTextContent.length(), bound);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width;
        int height;

        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else {
            paint.setTextSize(mTextContentSize);
            paint.getTextBounds(mTextContent, 0, mTextContent.length(), bound);
            float textWidth = bound.width();
            int desired = (int) (3*textWidth);
            width = desired;
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            paint.setTextSize(mTextContentSize);
            paint.getTextBounds(mTextContent, 0, mTextContent.length(), bound);
            float textHeight = bound.height();
            int desired = (int) (2.4*textHeight);
            height = desired;
        }
        setMeasuredDimension(width, height);
    }



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Random random = new Random();
        paint.setColor(mColors[random.nextInt(mColors.length)]);
        paint.setAntiAlias(true);
        RectF rectF = new RectF(0,0,getMeasuredWidth(), getMeasuredHeight());
        canvas.drawRoundRect(rectF, mCornerRadius, mCornerRadius, paint);

        paint.setColor(mTextColor);
        canvas.drawText(mTextContent, getWidth()/2-bound.width()/2-2, getHeight()/2+bound.height()/2, paint);

        paint.setColor(Color.WHITE);
        paint.setAntiAlias(true);

        Path path_top = new Path();
        path_top.moveTo(mCornerRadius, 0);
        path_top.lineTo(getWidth()/2, getHeight()*((1-scale)/2));
        path_top.lineTo(getWidth()-mCornerRadius, 0);
        canvas.drawPath(path_top, paint);

        Path path_down = new Path();
        path_down.moveTo(mCornerRadius, getHeight());
        path_down.lineTo(getWidth()/2, getHeight()*(scale+((1-scale)/2)));
        path_down.lineTo(getWidth()-mCornerRadius, getHeight());
        canvas.drawPath(path_down, paint);

    }

    public void setCornerRadius(int radius){
        this.mCornerRadius = radius;
        postInvalidate();
    }

    public void setTagText(String tag){
        this.mTextContent = String.valueOf(tag.charAt(0));
        postInvalidate();
    }
    public void setTagSize(int size){
        this.mTextContentSize = size;
        postInvalidate();
    }
    public void setTagColor(int color){
        this.mTextColor = color;
        postInvalidate();
    }
    public void setRandomColors(int[] colors){
        this.mColors = colors;
        postInvalidate();
    }
    public void setTagScale(float scale){
        this.scale = scale;
        postInvalidate();
    }

}
