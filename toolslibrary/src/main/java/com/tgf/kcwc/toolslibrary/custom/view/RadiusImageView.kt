package com.tgf.kcwc.toolslibrary.custom.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Path
import android.graphics.RectF
import android.util.AttributeSet
import com.tgf.kcwc.toolslibrary.R


/**
 * 设置 Radius 圆角的ImageView
 * @author cpf
 * @date 2021/9/10
 */
class RadiusImageView : androidx.appcompat.widget.AppCompatImageView {

    private var topLeftRadius = 0.0f
    private var topRightRadius = 0.0f
    private var bottomLeftRadius = 0.0f
    private var bottomRightRadius = 0.0f

    constructor(context: Context) : super(context)

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet){
        initData(attributeSet)
    }

    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr:Int) : super(context, attributeSet,defStyleAttr){
        initData(attributeSet)
    }

    private fun initData( attributeSet: AttributeSet){
        val array = context.obtainStyledAttributes(attributeSet, R.styleable.RadiusImageView)
        topLeftRadius = array.getDimensionPixelOffset(R.styleable.RadiusImageView_android_topLeftRadius,0)*1.0f
        topRightRadius = array.getDimensionPixelOffset(R.styleable.RadiusImageView_android_topRightRadius,0)*1.0f
        bottomLeftRadius = array.getDimensionPixelOffset(R.styleable.RadiusImageView_android_bottomLeftRadius,0)*1.0f
        bottomRightRadius = array.getDimensionPixelOffset(R.styleable.RadiusImageView_android_bottomRightRadius,0)*1.0f

    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        val path = Path()
        val rectF = RectF(0.0f,0.0f,this.width*1.0f,this.height*1.0f)
        var radii = floatArrayOf(topLeftRadius,topLeftRadius,topRightRadius,topRightRadius
            ,bottomLeftRadius,bottomLeftRadius,bottomRightRadius,bottomRightRadius)
        path.addRoundRect(rectF,radii,Path.Direction.CW)
        canvas?.clipPath(path)
        super.onDraw(canvas)
    }


}