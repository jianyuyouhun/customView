package com.jianyuyouhun.library.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.jianyuyouhun.library.R;

/**
 * Created by 王宇 on 2016/7/21.
 */
public class TagView extends View {
    private int mCornerRadius;//圆角
    private int mWidth;//宽度
    private int mHeight;//高度
    private float scale;//比例

    private int[] mColors = {0xFFCCFF00, 0xFF6495ED, 0xFFE32636, 0xFF800000, 0xFF808000, 0xFFFF8C69, 0xFF808080,
            0xFFE6B800, 0xFF7CFC00};

    public TagView(Context context){
        this(context, null);
    }
    public TagView(Context context, AttributeSet attrs){
        this(context, attrs, 0);
    }
    public TagView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.TagView, defStyleAttr, 0);
        int n = a.getIndexCount();
        for (int i = 0; i < n; i++){
            int attr = a.getIndex(n);
            if (attr == R.styleable.TagView_mCornerRadius){
                mCornerRadius = a.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics()));
            }else if (attr == R.styleable.TagView_mScale){
                scale = a.getFloat(attr,0.8f);
            }
        }
    }


}
