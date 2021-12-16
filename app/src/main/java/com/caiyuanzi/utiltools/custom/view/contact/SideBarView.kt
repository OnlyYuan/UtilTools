package com.caiyuanzi.utiltools.custom.view.contact

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Point
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.annotation.ColorInt
import kotlin.collections.ArrayList

/**
 * 通讯录的sideBar
 * @author cpf
 * @date 201/2/21
 */
class SideBarView : View {
    private val mContentDataList: MutableList<String> = ArrayList()
    private var mBackgroundColor = Color.TRANSPARENT
    private var mPaddingTop = 0
    private var mPaddingBottom = 0
    private var mPaddingLeft = 0
    private var mPaddingRight = 0
    private var mTextSize = 35f
    private var mTextColor = Color.GRAY
    private val mTextColor2 = Color.BLUE
    private var mItemSpace = 5 //自定义的item间隔
    private var mIsEqualItemSpace = true //是否按View的高度均分item的高度间隔
    private var mPaint: Paint? = null
    private var mPaint2: Paint? = null
    private var mWidth = 0
    private var mHeight = 0
    private val mOneItemPoint = Point() //第一个item的坐标值
    private var mItemHeightSize = 5 //单个Item的高度尺寸
    private var mListener: OnClickListener? = null
    private var mCount = 0

    constructor(context: Context?) : super(context) {
        initPaint()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        initPaint()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initPaint()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        mWidth = MeasureSpec.getSize(widthMeasureSpec)
        mHeight = MeasureSpec.getSize(heightMeasureSpec)
        mItemHeightSize = if (mIsEqualItemSpace) { //均分Item间距模式
            (mHeight - (mPaddingTop + mPaddingBottom)) / mContentDataList.size //高度 - 上下边距 / Item的数量 = 一个Item的高度
        } else {                //自定义Item间距模式
            (mHeight - (mPaddingTop + mPaddingBottom)) / mContentDataList.size + mItemSpace
        }
        mOneItemPoint.x = (mWidth - (mPaddingLeft + mPaddingRight)) / 2 //宽度 - 左右边距 / 2 = 第一个Item的X坐标值
        mOneItemPoint.y = mPaddingTop + mItemHeightSize //上边距 + Item高度 = 第一个Item的Y坐标值
        setMeasuredDimension(mWidth, mHeight)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val action = event.action
        val itemAllHeight = mHeight - (mPaddingTop + mPaddingBottom)
        when (action) {
            MotionEvent.ACTION_DOWN -> {
                //根据当前点击的位置与整体item的全部高度除比，在将除得后的数字四舍五入获得对应集合中的位置。
                var downPosition = Math.round(mContentDataList.size / (itemAllHeight / event.y))
                downPosition = Math.max(downPosition, 1) //不允许有小于1的值
                downPosition = Math.min(downPosition, mContentDataList.size) //不允许有大于集合长度的值
                downPosition = downPosition - 1
                if (mListener != null) {
                    mListener!!.onItemDown(downPosition, mContentDataList[downPosition])
                }
                mCount = downPosition
                postInvalidate()
                return true
            }
            MotionEvent.ACTION_MOVE -> {
                var movePosition = Math.round(mContentDataList.size / (itemAllHeight / event.y))
                movePosition = Math.max(movePosition, 1)
                movePosition = Math.min(movePosition, mContentDataList.size)
                movePosition = movePosition - 1
                if (mListener != null) {
                    mListener!!.onItemMove(movePosition, mContentDataList[movePosition])
                }
                mCount = movePosition
                postInvalidate()
                return true
            }
            MotionEvent.ACTION_UP -> {
                var upPosition = Math.round(mContentDataList.size / (itemAllHeight / event.y))
                upPosition = Math.max(upPosition, 1)
                upPosition = Math.min(upPosition, mContentDataList.size)
                upPosition = upPosition - 1
                if (mListener != null) {
                    mListener!!.onItemUp(upPosition, mContentDataList[upPosition])
                }
                return true
            }
        }
        return super.onTouchEvent(event)
    }

    private fun initPaint() {
        mPaint = Paint()
        mPaint!!.isAntiAlias = true
        mPaint!!.color = mTextColor
        mPaint!!.textSize = mTextSize
        mPaint!!.textAlign = Paint.Align.CENTER
        mPaint2 = Paint()
        mPaint2!!.isAntiAlias = true
        mPaint2!!.color = mTextColor2
        mPaint2!!.textSize = mTextSize
        mPaint2!!.textAlign = Paint.Align.CENTER
    }

    fun setContentDataList(list: ArrayList<String>) {
        mContentDataList.clear()
        mContentDataList.addAll(list)
        postInvalidate()
    }

    /**
     * 设置文字大小
     *
     * @param spValue 单位sp
     */
    fun setTextSize(spValue: Float) {
        mTextSize = sp2px(spValue).toFloat()
        mPaint!!.textSize = mTextSize
        postInvalidate()
    }

    fun setTextColor(@ColorInt color: Int) {
        mTextColor = color
        mPaint!!.color = mTextColor
        postInvalidate()
    }

    override fun setBackgroundColor(@ColorInt color: Int) {
        mBackgroundColor = color
        postInvalidate()
    }

    /**
     * 设置是否根据View的高度均分item的间距
     *
     * @param isEqualItemSpace true=使用均分  false=不使用均分
     */
    fun setEqualItemSpace(isEqualItemSpace: Boolean) {
        mIsEqualItemSpace = isEqualItemSpace
        postInvalidate()
    }

    /**
     * 设置当前位置
     * @param mFirstWord 当前的首字母
     */
    fun setCurrentWordPosition( mFirstWord:String ){

        for(i in 0 until mContentDataList.size){
           if(mFirstWord == mContentDataList[i] ){
               mCount = i
               break
               }
        }

        postInvalidate()
    }

    /**
     * 设置当前位置\
     * @param count 位置
     */
    fun setCurrentPosition( count:Int ){

        mCount = count
        postInvalidate()
    }

    /**
     * item的间距
     *
     * @param itemSpace 单位dp
     */
    fun itemSpace(itemSpace: Int) {
        mItemSpace = dip2px(itemSpace.toFloat())
        mIsEqualItemSpace = false
        postInvalidate()
    }

    /**
     * 设置内边距
     *
     * @param top    上边距,单位dp
     * @param bottom 下边距,单位dp
     * @param left   左边距,单位dp
     * @param right  右边距,单位dp
     */
    override fun setPadding(top: Int, bottom: Int, left: Int, right: Int) {
        mPaddingTop = dip2px(top.toFloat())
        mPaddingBottom = dip2px(bottom.toFloat())
        mPaddingLeft = dip2px(left.toFloat())
        mPaddingRight = dip2px(right.toFloat())
        postInvalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawColor(mBackgroundColor)
        drawContent(canvas)
    }

    private fun drawContent(canvas: Canvas) {
        if (mContentDataList.isEmpty()) {
            return
        }
        for (i in mContentDataList.indices) {
            val itemContent = mContentDataList[i]
//            if (i == 0) {
//                canvas.drawText(itemContent, mOneItemPoint.x.toFloat(), mOneItemPoint.y.toFloat(), mPaint!!)
//                continue
//            }
            val y = mOneItemPoint.y + mItemHeightSize * i
            if (i == mCount) {
                Log.i("3333", "------------->qwqeq")
                canvas.drawText(itemContent, mOneItemPoint.x.toFloat(), y.toFloat(), mPaint2!!)
            } else {
                canvas.drawText(itemContent, mOneItemPoint.x.toFloat(), y.toFloat(), mPaint!!)
            }
        }
    }

    private fun sp2px(spValue: Float): Int {
        val fontScale = resources.displayMetrics.scaledDensity
        return (spValue * fontScale + 0.5f).toInt()
    }

    private fun dip2px(dpValue: Float): Int {
        val scale = resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

    fun setOnClickListener(listener: OnClickListener?) {
        mListener = listener
    }

    interface OnClickListener {
        fun onItemDown(position: Int, itemContent: String?)
        fun onItemMove(position: Int, itemContent: String?)
        fun onItemUp(position: Int, itemContent: String?)
    }
}