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

/**
 * Created by jpwen on 2017/6/12.
 */

public class TipProgressView1 extends View {

    private int proBarHeight;
    private int proBarColorBg;
    private int proBarCologFore;
    private Paint proBgPaint;
    private Paint forePaint;
    private float runProgress;
    private int tipRoundWidth;
    private int tipRoundHeight;
    private int tipSanHeight;
    float tipProgress = 0;
    private Paint textPaint;
    private float progress;
    public TipProgressView1(Context context) {
        this(context, null);
    }

    public TipProgressView1(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }


    public TipProgressView1(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttribute();
        initPaint();
    }

    /**
     * 初始化所需自定义属性
     */
    private void initAttribute() {
        proBarHeight = dp2px(5);
        proBarColorBg = Color.GRAY;
        proBarCologFore = Color.GREEN;
        tipRoundWidth = dp2px(40);
        tipRoundHeight = dp2px(20);
        tipSanHeight = dp2px(10);
    }

    /**
     * 初始化所需绘制Paint
     */
    private void initPaint() {
        proBgPaint = new Paint();
        proBgPaint.setAntiAlias(true);
        proBgPaint.setStyle(Paint.Style.FILL);
        proBgPaint.setColor(proBarColorBg);

        forePaint = new Paint();
        forePaint.setAntiAlias(true);
        forePaint.setStyle(Paint.Style.FILL);
        forePaint.setColor(proBarCologFore);

        textPaint = new Paint();
        textPaint.setColor(Color.RED);
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(sp2px(12));
        textPaint.setTextAlign(Paint.Align.CENTER);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制背景progerssbar
        RectF proBgRectf = new RectF();
        proBgRectf.left =getPaddingLeft();
        proBgRectf.top = tipRoundHeight+tipSanHeight;
        proBgRectf.right = getWidth();
        proBgRectf.bottom = tipRoundHeight+tipSanHeight+proBarHeight;
        canvas.drawRoundRect(proBgRectf,proBarHeight/2,proBarHeight/2,proBgPaint);
        //绘制进度
        RectF proForeRectf = new RectF();
        proForeRectf.left = getPaddingLeft();
        proForeRectf.top = tipRoundHeight+tipSanHeight;
        proForeRectf.right = runProgress;
        proForeRectf.bottom = tipRoundHeight+tipSanHeight+proBarHeight;
        canvas.drawRoundRect(proForeRectf,proBarHeight/2,proBarHeight/2,forePaint);
        //绘制Tip框
        RectF tipRoundRectf = new RectF();
        tipRoundRectf.left = tipProgress;
        tipRoundRectf.top = 0;
        tipRoundRectf.right = tipProgress+tipRoundWidth;
        tipRoundRectf.bottom = tipRoundHeight;
        canvas.drawRoundRect(tipRoundRectf,5,5,forePaint);

        //绘制文字
        RectF textRect = new RectF();
        textRect.left = (int) tipProgress;
        textRect.top = 0;
        textRect.right = (int) (tipRoundWidth + tipProgress);
        textRect.bottom = tipRoundHeight;
        Paint.FontMetricsInt fontMetrics = textPaint.getFontMetricsInt();
        int baseline = (int) ((textRect.bottom + textRect.top - fontMetrics.bottom - fontMetrics.top) / 2);
        //文字绘制到整个布局的中心位置
        canvas.drawText((int)(runProgress*100/getWidth())+"%", textRect.centerX(), baseline, textPaint);
        //绘制三角形
        Path path = new Path();
        path.moveTo(tipProgress+tipRoundWidth/2-tipSanHeight, tipRoundHeight);
        path.lineTo(tipProgress+tipRoundWidth/2, tipRoundHeight+tipSanHeight);
        path.lineTo(tipProgress+tipRoundWidth/2+tipSanHeight,tipRoundHeight);
        canvas.drawPath(path,forePaint);
        path.reset();


    }

    public void doAnim(float progress){
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0,progress);
        valueAnimator.setDuration(2000);
        valueAnimator.setStartDelay(500);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                runProgress = (float) valueAnimator.getAnimatedValue();
                runProgress = runProgress*getWidth()/100;

                if(runProgress>=tipRoundWidth/2 && runProgress<=getWidth()-tipRoundWidth/2){
                    tipProgress = runProgress - tipRoundWidth/2;
                }
                invalidate();
            }
        });
        valueAnimator.start();;

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
     */
    protected int sp2px(int spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spVal, getResources().getDisplayMetrics());

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = 0;
        int height = 0;

        int defaultHeight = proBarHeight+tipSanHeight+tipRoundHeight;
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        if(widthMode == MeasureSpec.EXACTLY){
            width = widthSize;
        }else if(widthMode == MeasureSpec.AT_MOST){
            width = widthSize;
        }else if(widthMode == MeasureSpec.UNSPECIFIED){
            width = widthSize;
        }

        if(heightMode == MeasureSpec.EXACTLY){
            height = heightSize;
        }else if(heightMode == MeasureSpec.AT_MOST){
            height = defaultHeight;
        }else if(heightMode == MeasureSpec.UNSPECIFIED){
            height = heightSize;
        }
        setMeasuredDimension(width,height);



    }
}
