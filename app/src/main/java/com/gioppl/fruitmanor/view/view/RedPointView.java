package com.gioppl.fruitmanor.view.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.gioppl.fruitmanor.tool.SharedPreferencesUtils;

import java.nio.file.Path;

import androidx.annotation.Nullable;

public class RedPointView extends View {
    private Path mPath;
    private Paint mPointPaint;
    private Paint mTextPaint;
    private float CIRCLE_RADIUS = 25F;
    private float circleX = 500.0F;
    private float circleY = 500.0F;
    private int fruit = 0;

    private Context context;

    public RedPointView(Context context) {
        super(context);
        init();
    }

    public RedPointView(Context context, int position[], int weight, int height) {
        super(context);
        circleX = position[0] + weight - CIRCLE_RADIUS * 3;
        circleY = position[1];
        init();
    }

    public RedPointView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RedPointView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public RedPointView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        mPointPaint = new Paint();
        mPointPaint.setColor(Color.RED);
        mPointPaint.setStyle(Paint.Style.FILL);
        mPointPaint.setAntiAlias(true);
        mPointPaint.setDither(true);
        mPointPaint.setStyle(Paint.Style.FILL);
        mPointPaint.setStrokeWidth(5);

        mTextPaint = new Paint();
        mTextPaint.setColor(Color.WHITE);
        mTextPaint.setAntiAlias(true);
        mTextPaint.setDither(true);
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setStrokeWidth(1);
        mTextPaint.setTextSize(35f);
        mTextPaint.setPathEffect(new CornerPathEffect(10f));
    }

    public void redPointParamsAdd(int num) {//每次点击加入购物车的时候会在红点加一个
        fruit=num+fruit;
        if (fruit>=10){
            if (CIRCLE_RADIUS<=25)
                CIRCLE_RADIUS+=5.0f;
        }else if (fruit>=100){
            if (CIRCLE_RADIUS<=30)
                CIRCLE_RADIUS+=5.0f;
        }
        invalidate();
    }
    public void redPointParams(int num) {//初始化的时候红点中的数字准确值，不是加减
        fruit=num;
        if (fruit>=10){
            if (CIRCLE_RADIUS<=25)
                CIRCLE_RADIUS+=5.0f;
        }else if (fruit>=100){
            if (CIRCLE_RADIUS<=30)
                CIRCLE_RADIUS+=5.0f;
        }
        invalidate();
    }
    public void refreshRedPoint(){//刷新红点
        boolean isLogin = (boolean) SharedPreferencesUtils.getInstance().getData("loginStatus", false);
        if (isLogin) {//已经登陆了
            int shopHaveGoods= (int) SharedPreferencesUtils.getInstance().getData("redPointNum", 0);
            redPointParams(shopHaveGoods);
        }else{
            redPointParams(0);
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        canvas.drawCircle(circleX, circleY, CIRCLE_RADIUS, mPointPaint);
        canvas.drawText("" + fruit, circleX-CIRCLE_RADIUS/2, circleY+CIRCLE_RADIUS/2, mTextPaint);
        invalidate();
    }
}
