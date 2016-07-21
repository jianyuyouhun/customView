package com.jianyuyouhun.library.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.jianyuyouhun.library.R;

import java.util.Random;

/**
 * Created by 王宇 on 2016/7/21.
 */
public class TagView extends View {
    private int mCornerRadius;//圆角
    private int mWidth;//宽度
    private int mHeight;//高度
    private float scale;//比例
    private int mTextContentSize;//文字大小
    private String mTextContent;//文字内容
    private int mTextColor;//文字颜色
    private Paint paint;
    private Rect bound;
    private int[] mColors = {0xFFCCFF00, 0xFF6495ED, 0xFFE32636, 0xFF800000, 0xFF808000, 0xFFFF8C69, 0xFF808080,
            0xFFE6B800, 0xFF7CFC00};

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
            int attr = a.getIndex(n);
            if (attr == R.styleable.TagView_mCornerRadius) {
                mCornerRadius = a.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics()));
            } else if (attr == R.styleable.TagView_mScale) {
                scale = a.getFloat(attr, 0.8f);
            } else if (attr == R.styleable.TagView_mTextContent) {
                mTextContent = a.getString(attr);
            } else if (attr == R.styleable.TagView_mTextContentSize) {
                mTextContentSize = a.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP, 18, getResources().getDisplayMetrics()));
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
            int desired = (int) (getPaddingLeft() + textWidth + getPaddingRight());
            width = desired;
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            paint.setTextSize(mTextContentSize);
            paint.getTextBounds(mTextContent, 0, mTextContent.length(), bound);
            float textHeight = bound.height();
            int desired = (int) (getPaddingTop() + textHeight + getPaddingBottom());
            height = desired;
        }
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Random random = new Random();
        paint.setColor(mColors[random.nextInt(9)]);
        paint.setAntiAlias(true);
        canvas.drawRect(0,0,getMeasuredWidth(), getMeasuredHeight(), paint);

        paint.setColor(mTextColor);
        canvas.drawText(mTextContent, getWidth()/2-bound.width()/2, getHeight()/2+bound.height()/2, paint);

    }

}
