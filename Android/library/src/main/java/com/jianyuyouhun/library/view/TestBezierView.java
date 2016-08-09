package com.jianyuyouhun.library.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by 王宇 on 2016/8/9.
 */
public class TestBezierView extends View {
    private int height;
    private int width;
    private final float SCALE_RATE = 0.6f;
    private float handle_len_rate = 2f;//还没弄懂这个参数对图像的影响
    private float[] current_position = new float[]{0,0};//触摸位置
    private Paint paint = new Paint();//画笔
    private Circle circle_large;//大圆
    private Circle circle_small;//小圆
    private int paint_color = 0xff4db9ff;//画笔颜色
    private float curvature = 0.8f;//变化曲率
    private float radius = 40f;//大圆半径
    private float radius_rate = 0.5f;//小圆半径占比
    private int maxDistanceMult = 6;
    public TestBezierView(Context context) {
        super(context);
        init();
    }
    public TestBezierView(Context context, AttributeSet attrs){
        super(context, attrs);
        init();
    }
    public TestBezierView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private class Circle{
        float[] center;
        float radius;
    }

    private float[] getVector(float radians, float length) {
        float x = (float) (Math.cos(radians) * length);
        float y = (float) (Math.sin(radians) * length);
        return new float[]{
                x, y
        };
    }

    private float getDistance(float[] b1, float[] b2) {
        float x = b1[0] - b2[0];
        float y = b1[1] - b2[1];
        float d = x * x + y * y;
        return (float) Math.sqrt(d);
    }

    private float getLength(float[] b) {
        return (float) Math.sqrt(b[0] * b[0] + b[1] * b[1]);
    }

    public void setPaintMode(int mode) {
        paint.setStyle(mode == 0 ? Paint.Style.STROKE : Paint.Style.FILL);
        invalidate();
    }

    /**
     * 设置拉伸曲率
     * @param curvature
     */
    public void setCurvature(float curvature) {
        if (curvature>1f){
            curvature = 1f;
        }else if (curvature<0.1f){
            curvature = 0.1f;
        }
        this.curvature = curvature;
        invalidate();
    }

    /**
     * 设置画笔颜色
     * @param paint_color
     */
    public void setPaint_color(int paint_color) {
        this.paint_color = paint_color;
        invalidate();
    }

    /**
     * 设置半径和比例
     * @param radius
     * @param rate
     */
    public void setRadius(float radius, float rate) {
        this.radius = radius;
        this.radius_rate = rate;
        invalidate();
    }

    /**
     * 设置最大切断距离
     * @param maxDistanceMult
     */
    public void setMaxDistanceMult(int maxDistanceMult) {
        this.maxDistanceMult = maxDistanceMult;
        invalidate();
    }

    /**
     * 初始化数据
     */
    public void init(){
        paint.setColor(paint_color);
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        current_position[0] = event.getX()-width/2;
        current_position[1] = event.getY()-height/2;
        invalidate();
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
        canvas.translate(width/2,height/2);

        circle_small = new Circle();
        circle_small.center = new float[]{0, 0};
        circle_small.radius = radius*radius_rate;

        circle_large = new Circle();
        circle_large.center = current_position;
        circle_large.radius = radius;

        RectF ball1 = new RectF();
        ball1.left = circle_large.center[0] - circle_large.radius;
        ball1.top = circle_large.center[1] - circle_large.radius;
        ball1.right = ball1.left + circle_large.radius * 2;
        ball1.bottom = ball1.top + circle_large.radius * 2;
        canvas.drawCircle(ball1.centerX(), ball1.centerY(), circle_large.radius, paint);

        metaBall(canvas, circle_large, circle_small, curvature, handle_len_rate, circle_large.radius*maxDistanceMult);
    }

    /**
     * 绘制两个圆，计算贝塞尔变换之后的边界，存入path进行绘制
     * @param canvas
     * @param circle_large
     * @param circle_small
     * @param v
     * @param handle_len_rate
     * @param maxDistance
     */
    private void metaBall(Canvas canvas, final Circle circle_large, final Circle circle_small, float v, float handle_len_rate, float maxDistance){
        RectF ball1 = new RectF();
        ball1.left = circle_large.center[0] - circle_large.radius;
        ball1.top = circle_large.center[1] - circle_large.radius;
        ball1.right = ball1.left + circle_large.radius * 2;
        ball1.bottom = ball1.top + circle_large.radius * 2;

        RectF ball2 = new RectF();
        ball2.left = circle_small.center[0] - circle_small.radius;
        ball2.top = circle_small.center[1] - circle_small.radius;
        ball2.right = ball2.left + circle_small.radius * 2;
        ball2.bottom = ball2.top + circle_small.radius * 2;

        float[] center1 = new float[]{
                ball1.centerX(),
                ball1.centerY()
        };
        float[] center2 = new float[]{
                ball2.centerX(),
                ball2.centerY()
        };

        float d = getDistance(center1, center2);

        float radius1 = ball1.width() / 2;
        float radius2 = ball2.width() / 2;
        float pi2 = (float) (Math.PI / 2);
        float u1, u2;

        if (d > maxDistance) {//切断后留下的圆
//            canvas.drawCircle(ball1.centerX(), ball1.centerY(), circle1.radius, paint);
//            canvas.drawCircle(ball2.centerX(), ball2.centerY(), circle_small.radius, paint);
        } else {
            float scale2 = 1 + SCALE_RATE * (1 - d / maxDistance);
            float scale1 = 1 - SCALE_RATE * (1 - d / maxDistance);
            radius2 *= scale2;
//            radius1 *= scale1;
//            canvas.drawCircle(ball1.centerX(), ball1.centerY(), radius1, paint);
            canvas.drawCircle(ball2.centerX(), ball2.centerY(), radius2, paint);

        }

        if (radius1 == 0 || radius2 == 0) {
            return;
        }

        if (d > maxDistance || d <= Math.abs(radius1 - radius2)) {
            return;
        } else if (d < radius1 + radius2) {
            u1 = (float) Math.acos((radius1 * radius1 + d * d - radius2 * radius2) /
                    (2 * radius1 * d));
            u2 = (float) Math.acos((radius2 * radius2 + d * d - radius1 * radius1) /
                    (2 * radius2 * d));
        } else {
            u1 = 0;
            u2 = 0;
        }
//
        float[] centermin = new float[]{center2[0] - center1[0], center2[1] - center1[1]};

        float angle1 = (float) Math.atan2(centermin[1], centermin[0]);
        float angle2 = (float) Math.acos((radius1 - radius2) / d);
        float angle1a = angle1 + u1 + (angle2 - u1) * v;
        float angle1b = angle1 - u1 - (angle2 - u1) * v;
        float angle2a = (float) (angle1 + Math.PI - u2 - (Math.PI - u2 - angle2) * v);
        float angle2b = (float) (angle1 - Math.PI + u2 + (Math.PI - u2 - angle2) * v);
//
        float[] p1a1 = getVector(angle1a, radius1);
        float[] p1b1 = getVector(angle1b, radius1);
        float[] p2a1 = getVector(angle2a, radius2);
        float[] p2b1 = getVector(angle2b, radius2);

        float[] p1a = new float[]{p1a1[0] + center1[0], p1a1[1] + center1[1]};
        float[] p1b = new float[]{p1b1[0] + center1[0], p1b1[1] + center1[1]};
        float[] p2a = new float[]{p2a1[0] + center2[0], p2a1[1] + center2[1]};
        float[] p2b = new float[]{p2b1[0] + center2[0], p2b1[1] + center2[1]};

        float[] p1_p2 = new float[]{p1a[0] - p2a[0], p1a[1] - p2a[1]};

        float totalRadius = (radius1 + radius2);
        float d2 = Math.min(v * handle_len_rate, getLength(p1_p2) / totalRadius);
        d2 *= Math.min(1, d * 2 / (radius1 + radius2));
//        Log.d("Metaball", "d2:" + d2);
        radius1 *= d2;
        radius2 *= d2;

        float[] sp1 = getVector(angle1a - pi2, radius1);
        float[] sp2 = getVector(angle2a + pi2, radius2);
        float[] sp3 = getVector(angle2b - pi2, radius2);
        float[] sp4 = getVector(angle1b + pi2, radius1);


        /**
         * 三阶贝塞尔变换
         */
        Path path1 = new Path();
        path1.moveTo(p1a[0], p1a[1]);
        path1.cubicTo(p1a[0] + sp1[0], p1a[1] + sp1[1], p2a[0] + sp2[0], p2a[1] + sp2[1], p2a[0], p2a[1]);
        path1.lineTo(p2b[0], p2b[1]);
        path1.cubicTo(p2b[0] + sp3[0], p2b[1] + sp3[1], p1b[0] + sp4[0], p1b[1] + sp4[1], p1b[0], p1b[1]);
        path1.lineTo(p1a[0], p1a[1]);
        path1.close();
        /**
         * 绘制path
         */
        canvas.drawPath(path1, paint);
    }
}
