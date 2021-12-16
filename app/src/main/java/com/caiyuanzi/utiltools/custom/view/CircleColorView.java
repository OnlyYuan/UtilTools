package com.caiyuanzi.utiltools.custom.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import java.util.ArrayList;

/**
 * 圆形颜色图标 :用于显示一个圆形被颜色横向切割成等分
 * @author cpf
 * @date 2021/8/29
 */
public class CircleColorView extends View {

    private int mWidth = 0;
    private Paint mPaint ;
    //图片的宿放比例
    private float mScale;
    private int mRadius;
    private int mHeight;

    ArrayList<String>  colorStringList = new ArrayList<String>();


    public CircleColorView(Context context) {
        super(context);
        initData();
    }

    public CircleColorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initData();
    }

    public CircleColorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initData();
    }

    private void initData() {
        colorStringList.add("#77787b");
        mPaint = new Paint();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        mWidth = MeasureSpec.getSize(widthMeasureSpec);
        setMeasuredDimension(mWidth,mWidth);
        mRadius = mWidth/2;
        mHeight = mWidth/colorStringList.size();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Path mPath = new Path();
        mPath.addCircle(mRadius,mRadius,mRadius,Path.Direction.CCW);
        canvas.clipPath(mPath);
        canvas.drawCircle(mHeight/2,mHeight/2,mRadius,mPaint);
        drawImg(canvas);
        }

        private void drawImg(Canvas canvas){

            for (int i = 0; i<colorStringList.size();i++){
                Paint paint = new Paint();
                int color;
                try {
                    color =  Color.parseColor(colorStringList.get(i));
                }catch (IllegalArgumentException e){
                    color = Color.parseColor("#77787b");
                }
                paint.setColor(color);

                canvas.drawRect(0.0f,i*mHeight,mWidth,(i+1)*mHeight,paint);
            }

        }

    /**
     * 设置颜色
     * @param colorStringsList 颜色的list
     */
    public void setColorString(ArrayList<String>  colorStringsList) {
        if (colorStringsList != null && colorStringsList.size() != 0) {
            colorStringList.clear();
            colorStringList.addAll(colorStringsList);
            for (String item : colorStringsList) {
                Log.i("11", "--->yanse: " + item);
            }
            invalidate();
        }
    }
}




