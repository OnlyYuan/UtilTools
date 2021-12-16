package com.tgf.kcwc.toolslibrary.custom.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.FontMetricsInt;
import android.graphics.Paint.Style;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.tgf.kcwc.toolslibrary.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


/**
 * 时间选择器
 * 
 * @author cpf
 * 
 */
public class PickerView extends View {

	public static final String TAG = "PickerView";
	/**
	 * text之间间距和minTextSize之比
	 */
	public static final float MARGIN_ALPHA = 1.0f;
	/**
	 * 自动回滚到中间的速度
	 */
	public static final float SPEED = 4;

	private List<String> mDataList; // 时间的列表
	/**
	 * 选中的位置，这个位置是mDataList的中心位置，一直不变
	 */
	private int mCurrentSelected;
	private Paint mPaint;

	private float mMaxTextSize = 20;
	private float mMinTextSize = 20;

	private float mMaxTextAlpha = 255;
	private float mMinTextAlpha = 120;

	private int mColorText = 0x333333;

	private int mViewHeight;
	private int mViewWidth;

	private float mLastDownY;
	/**
	 * 滑动的距离
	 */
	private float mMoveLen = 0;
	private boolean isInit = false;
	private onSelectListener mSelectListener;
	private Timer timer;
	private MyTimerTask mTask;
	private float msize;
	private String timeType;// 区分时间的类型

	Handler updateHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			if (Math.abs(mMoveLen) < SPEED) {
				mMoveLen = 0;
				if (mTask != null) {
					mTask.cancel();
					mTask = null;
					performSelect();
				}
			} else
				// 这里mMoveLen / Math.abs(mMoveLen)是为了保有mMoveLen的正负号，以实现上滚或下滚
				mMoveLen = mMoveLen - mMoveLen / Math.abs(mMoveLen) * SPEED;
			invalidate();
		}

	};

	public PickerView(Context context) {
		super(context);
		init();
	}

	public PickerView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public void setOnSelectListener(onSelectListener listener) {
		mSelectListener = listener;
	}

	private void performSelect() {
		if (mSelectListener != null)
			mSelectListener.onSelect(mDataList.get(mCurrentSelected));
	}

	public void setData(List<String> datas, String string) {
		timeType = string;
		mDataList = datas;
		mCurrentSelected = datas.size() / 2;
		invalidate();
	}
	
	public String getTime(){
		
		return mDataList.get(mCurrentSelected);
	}

	public void setSelected(int selected) {
		mCurrentSelected = selected;
	}

	// 移动到list的头
	private void moveHeadToTail() {
		String head = mDataList.get(0);
		mDataList.remove(0);
		mDataList.add(head);
	}

	// 移动到list的尾部
	private void moveTailToHead() {
		String tail = mDataList.get(mDataList.size() - 1);
		mDataList.remove(mDataList.size() - 1);
		mDataList.add(0, tail);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		mViewHeight = getMeasuredHeight();
		mViewWidth = getMeasuredWidth();
		// 按照View的高度计算字体大小
		mMaxTextSize = mViewHeight / 6.0f;
		//mMinTextSize = mMaxTextSize / 3f;
		isInit = true;
		setMeasuredDimension(mViewWidth,mViewHeight);
		//invalidate();
	}

	private void init() {
		timer = new Timer();
		mDataList = new ArrayList<String>();
		mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mPaint.setStyle(Style.FILL);
		mPaint.setTextAlign(Align.CENTER);
		mPaint.setColor(mColorText);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		// 根据index绘制view
		if (isInit)
			drawData(canvas);
	}

	private void drawData(Canvas canvas) {
		// 先绘制选中的text再往上往下绘制其余的text
		//float size = (mMaxTextSize - mMinTextSize) + mMinTextSize;
		float size = (mMaxTextSize - mMinTextSize) + mMinTextSize;
		msize = size;
		mPaint.setTextSize(msize);// 设置文字大小
		mPaint.setColor(getResources().getColor(R.color.picker_selected_color,null));
		// text居中绘制，注意baseline的计算才能达到居中，y值是text中心坐标
		float x = (float) (mViewWidth / 2.0);
		float y = (float) (mViewHeight / 2.0 + mMoveLen);
		FontMetricsInt fmi = mPaint.getFontMetricsInt(); // 绘制的基准线
		float baseline = (float) (y - (fmi.bottom / 2.0 + fmi.top/ 2.0));
		canvas.drawText(mDataList.get(mCurrentSelected)+timeType, x, baseline, mPaint);
	
		// 绘制上方data
		if (mCurrentSelected==0) {
			drawOtherText(canvas, -1, -1);
			System.out.println("------zhixing l e");
		}else{
			for (int i = 1; (mCurrentSelected - i) >= 0; i++) {
					drawOtherText(canvas, i, -1);
			}			
		}
		// 绘制下方data
		if (mCurrentSelected == mDataList.size()-1) {
			drawOtherText(canvas, -2, 1);
			System.out.println("------11112323zhixing l e");
		}else{
			for (int i = 1; (mCurrentSelected + i) < mDataList.size(); i++) {
				drawOtherText(canvas, i, 1);
			}
		}
	}

	/**
	 * @param canvas
	 * @param position
	 *            距离mCurrentSelected的差值
	 * @param type
	 *            1表示向下绘制，-1表示向上绘制
	 */
	private void drawOtherText(Canvas canvas, int position, int type) {
		float d = 0;
		int mpostion =0;
		if (position == -1) {
		    d = (float) (MARGIN_ALPHA * mMinTextSize  + type * mMoveLen + mViewHeight / 5);
		    mpostion = 1;
		}else if(position == -2){
			mpostion=1;
		}else {
			 d = (float) (MARGIN_ALPHA * mMinTextSize  + type * mMoveLen + mViewHeight / 5)*position;
			 mpostion= position;
		}

		float size = (mMaxTextSize - mMinTextSize) + mMinTextSize;
		mPaint.setTextSize(size);
		mPaint.setColor(Color.GRAY);
		float y = (float) (mViewHeight / 2.0 + type * d);
		FontMetricsInt fmi = mPaint.getFontMetricsInt();
		float baseline = (float) (y - (fmi.bottom / 2.0 + fmi.top ));
		if (position == -1) {
			canvas.drawText(mDataList.get(mDataList.size()-1)+timeType, (float) (mViewWidth / 2.0), baseline,
					mPaint);
		}else if(position == -2){
			canvas.drawText(mDataList.get(0)+timeType, (float) (mViewWidth / 2.0), baseline,
					mPaint);
		}else {
			canvas.drawText(mDataList.get(mCurrentSelected + type * position)+timeType, (float) (mViewWidth / 2.0), baseline,
					mPaint);
		    
		}
		
	}


	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		 int action = event.getAction();
		int actionMasked = action & MotionEvent.ACTION_MASK;
		switch (actionMasked) {
		case MotionEvent.ACTION_DOWN:
			doDown(event);
			break;
		case MotionEvent.ACTION_MOVE:
			doMove(event);
			break;
		case MotionEvent.ACTION_UP:
			doUp(event);
			break;
		}
		return true;
	}

	private void doDown(MotionEvent event) {
		if (mTask != null) {
			mTask.cancel();
			mTask = null;
		}
		mLastDownY = event.getY();
	}

	private void doMove(MotionEvent event) {

		mMoveLen += (event.getY() - mLastDownY);

		if (mMoveLen > MARGIN_ALPHA * mMinTextSize / 2) {
			// 往下滑超过离开距离
			moveTailToHead();
			mMoveLen = mMoveLen - MARGIN_ALPHA * mMinTextSize;
		} else if (mMoveLen < -MARGIN_ALPHA * mMinTextSize / 2) {
			// 往上滑超过离开距离
			moveHeadToTail();
			mMoveLen = mMoveLen + MARGIN_ALPHA * mMinTextSize;
		}

		mLastDownY = event.getY();
		invalidate();
	}

	private void doUp(MotionEvent event) {
		// 抬起手后mCurrentSelected的位置由当前位置move到中间选中位置
		if (Math.abs(mMoveLen) < 0.0001) {
			mMoveLen = 0;
			return;
		}
		if (mTask != null) {
			mTask.cancel();
			mTask = null;
		}
		mTask = new MyTimerTask(updateHandler);
		timer.schedule(mTask, 0, 10);
	}

	class MyTimerTask extends TimerTask {
		Handler handler;

		public MyTimerTask(Handler handler) {
			this.handler = handler;
		}

		@Override
		public void run() {
			handler.sendMessage(handler.obtainMessage());
		}

	}

	public interface onSelectListener {
		void onSelect(String text);
	}
}
