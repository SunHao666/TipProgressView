package com.demo.tipprogressview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import java.text.DecimalFormat;

/**
 * 自定义水平进度条
 */

public class TipProgressView extends View{

    private Paint bgPaint;
    private Paint forePaint;
    private float curProgress;
    private float progress;
    private float roundRectWidth;
    private float roundRectHeight;
    private int progressHeight;
    private int tipHeight;
    float tipProgress = 0;
    public TipProgressView(Context context) {
        this(context,null);
    }

    public TipProgressView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
        initPaint();
    }

    private void init() {
        roundRectWidth = dp2px(30);
        roundRectHeight = dp2px(10);
        progressHeight = dp2px(5);
        tipHeight = dp2px(5);
    }

    private void initPaint() {
        bgPaint = new Paint();
        bgPaint.setColor(Color.GRAY);
        bgPaint.setStrokeWidth(dp2px(1));
        bgPaint.setAntiAlias(true);

        forePaint = new Paint();
        forePaint.setColor(Color.GREEN);
        forePaint.setAntiAlias(true);
        forePaint.setStrokeWidth(dp2px(1));
        forePaint.setStyle(Paint.Style.FILL);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        RectF rectF = new RectF();
        rectF.left = getLeft();
        rectF.top = roundRectHeight+tipHeight;
        rectF.right = getWidth();
        rectF.bottom = roundRectHeight+tipHeight+progressHeight;
        //绘制默认进度条
        canvas.drawRoundRect(rectF,10,10,bgPaint);

        RectF foreRect = new RectF();
        foreRect.left = getLeft();
        foreRect.top = roundRectHeight+tipHeight;
        foreRect.right = progress;
        foreRect.bottom = roundRectHeight+tipHeight+progressHeight;
        //绘制进度
        canvas.drawRoundRect(foreRect,10,10,forePaint);

        RectF roundRectF = new RectF();
        roundRectF.left = getLeft()+tipProgress;
        roundRectF.top = 0;
        roundRectF.right = tipProgress + roundRectWidth;
        roundRectF.bottom = roundRectHeight;

        canvas.drawRoundRect(roundRectF,5,5,forePaint);


        Path path = new Path();
        path.moveTo(tipProgress+roundRectWidth/2-tipHeight,roundRectHeight);
        path.lineTo(tipProgress+roundRectWidth/2,roundRectHeight+tipHeight);
        path.lineTo(tipProgress+roundRectWidth/2+tipHeight,roundRectHeight);
        canvas.drawPath(path,forePaint);
        path.reset();
    }


    public void doAnim(float curProgress){
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0,curProgress);
        valueAnimator.setDuration(2000);
        valueAnimator.setStartDelay(5000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                progress = (float) valueAnimator.getAnimatedValue();
                progress = progress*getWidth()/100;
                if(progress >= roundRectWidth/2 && progress <= getWidth()-roundRectWidth/2){
                    tipProgress = progress - roundRectWidth/2;
                }
                invalidate();
            }
        });
        valueAnimator.start();
    }
    /**
     * 格式化数字(保留两位小数)
     */
    public static String formatNumTwo(double money) {
        DecimalFormat format = new DecimalFormat("0.00");
        return format.format(money);
    }

    /**
     * 格式化数字(保留一位小数)
     */
    public static String formatNum(int money) {
        DecimalFormat format = new DecimalFormat("0");
        return format.format(money);
    }

    /**
     * dp 2 px
     */
    protected int dp2px(int dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, getResources().getDisplayMetrics());
    }

    /**
     * sp 2 px
     *
     * @param spVal
     * @return
     */
    protected int sp2px(int spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spVal, getResources().getDisplayMetrics());

    }

    public static int format2Int(double i) {
        return (int) i;
    }
}
