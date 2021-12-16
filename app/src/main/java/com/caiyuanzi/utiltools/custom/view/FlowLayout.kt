package com.caiyuanzi.utiltools.custom.view

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.TextView
import com.caiyuanzi.utiltools.R

/**
 * 标签列表
 * @author cpf
 * @date 2021/9/9
 */
class FlowLayout : ViewGroup {

    constructor(context: Context):super(context)

    //constructor(context: Context， attrs:AttributeSet):super(context,attrs)
    constructor(context: Context,attributeSet: AttributeSet):super(context,attributeSet)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        var count = childCount

        for (i in 0 until count)
            measureChild(getChildAt(i),widthMeasureSpec,heightMeasureSpec)
    }


    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {

        var left = 10
        var right = 0
        var top = 10
        var maxWidth = r

        var count = childCount

        if(count!=0) {

            for (i in 0 until count) {
                var view = getChildAt(i)
                right = left + view.measuredWidth
                if (right > maxWidth) {//超过当前行
                    //距离左边的距离
                    left = 10
                   // right = left + view.measuredWidth
                    top += view.measuredHeight +10
                }

                right = left + view.measuredWidth

                getChildAt(i).layout(left, top, right, top + view.measuredHeight)
                left += (view.measuredWidth+20)

            }
        }
    }

    /**
     * 添加view
     */
     fun showUI(list: ArrayList<String>){
        for (i in 0 until list.size-1){
            var textView = TextView(context)
            textView.setText(list[i])
            textView.setTextColor(resources.getColor(R.color.flow_layout_text_color))
            textView.setPadding(15,10,15,10)
            textView.background=resources.getDrawable(R.drawable.flowlayout_textview_selector)
            addView(textView)
            var mStatus = false
            textView.setOnClickListener {
                mStatus=!mStatus
                textView.isSelected = mStatus
                if (mStatus){
                    textView.setTextColor(resources.getColor(R.color.flow_layout_selected_color))
                }else{
                    textView.setTextColor(resources.getColor(R.color.flow_layout_text_color))
                }
                listener.onClick(i)
            }
        }
        postInvalidate()
    }

    /**
     * 接口
     */
    interface OnChildItemListener {
            fun onClick(postion:Int)
    }

    lateinit var listener:OnChildItemListener
    /**
     * 暴露点击函数
     */
    fun setOnChildItemListener(listener:OnChildItemListener){

        this.listener =listener

    }

}