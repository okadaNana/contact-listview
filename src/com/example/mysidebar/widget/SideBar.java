package com.example.mysidebar.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.example.mysidebar.R;

/**
 * 滚动导航栏控件
 * 
 * @author owen
 */
public class SideBar extends View {
	
	/**
	 * 字母索引数组
	 */
	private String[] alphabet = {
		"A", "B", "C", "D", "E", "F",
		"G", "H", "I", "J", "K", "L", 
		"M", "N", "O", "P", "Q", "R", 
		"S", "T", "U", "V", "W", "X", 
		"Y", "Z", "#"
	};
	
	/**
	 * 当前手指触摸到的字母在字母索引数组中的下标，默认为 -1，也就是没有触摸到任何字母
	 */
	private int currentChoosenAlphabetIndex = -1;
	
	/**
	 * 画笔
	 */
	private Paint paint = new Paint();
	
	/**
	 * 字母索引展示控件
	 */
	private TextView textViewDialog = null;
	
	public SideBar(Context context) {
		super(context);
	}

	public SideBar(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public SideBar(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}
	
	public void setTextViewDialog(TextView textViewDialog) {
		this.textViewDialog = textViewDialog;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		// 得到SideBar的高度
		int viewHeight = getHeight();  
		// 得到SideBar的宽度
		int viewWidth = getWidth();
		// 每一个字母索引的高度 = SideBar的高度 / 字母索引的总个数
		int heightPerAlphabet = viewHeight / alphabet.length;
		
		// 绘制每一个字母索引
		for (int i = 0; i < alphabet.length; i++) {
			paint.setColor(Color.rgb(34, 66, 99));  // 字体颜色
			paint.setTypeface(Typeface.DEFAULT_BOLD);  // 设置字体
			paint.setTextSize(20); // 字体大小
			paint.setAntiAlias(true); // 抗锯齿
			
			// 如果当前的字母索引被手指触摸到，那么字体颜色要进行区分
			if (currentChoosenAlphabetIndex == i) {
				paint.setColor(Color.parseColor("#3399ff"));  // 颜色进行区分
				paint.setFakeBoldText(true);  // 字体加粗
			}
			
			/* 
			 * 绘制字体，需要制定绘制的x、y轴坐标
			 * 
			 * x轴坐标 = 控件宽度的一半 - 字体宽度的一半
			 * y轴坐标 = heightPerAlphabet * i + heightPerAlphabet
			 */
			float xPos = viewWidth / 2 - paint.measureText(alphabet[i]) / 2;
			float yPos = heightPerAlphabet * i + heightPerAlphabet;
			canvas.drawText(alphabet[i], xPos, yPos, paint);
			
			// 重置画笔，准备绘制下一个字母索引
			paint.reset();
		}
	}
	
	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		// 触摸事件的代码
		int action = event.getAction();
		// 手指触摸点在屏幕的y轴坐标
		float touchYPos = event.getY();
		// 因为currentChoosenAlphabetIndex会不断发生变化，所以用一个变量存储起来
		int preChoosenAlphabetIndex = currentChoosenAlphabetIndex;  
		onLetterTouchedChangeListener listener = getOnLetterTouchedChangeListener();
		// 比例 = 手指触摸点在屏幕的y轴坐标 / SideBar的高度
		// 触摸点的索引 = 比例 * 字母索引数组的长度
		int currentTouchIndex = (int) (touchYPos / getHeight() * alphabet.length);
		
		if (MotionEvent.ACTION_UP == action) {
			// 如果手指没有触摸屏幕，SideBar的背景颜色为默认，索引字母提示控件不可见
			setBackgroundColor(Color.WHITE);
			currentChoosenAlphabetIndex = -1;
			invalidate();
			if (textViewDialog != null) {
				textViewDialog.setVisibility(View.GONE);
			}
		} else {
			// 其他情况，比如滑动屏幕、点击屏幕等等，SideBar会改变背景颜色，索引字母提示控件可见，同时需要设置内容
			setBackgroundResource(R.drawable.sidebar_bg);
			
			// 不是同一个字母索引
			if (currentTouchIndex != preChoosenAlphabetIndex) {
				// 如果触摸点没有超出控件范围
				if (currentTouchIndex >= 0 && currentTouchIndex < alphabet.length) {
					if (listener != null) {
						listener.onTouchedLetterChange(alphabet[currentTouchIndex]);
					}
					
					if (textViewDialog != null) {
						textViewDialog.setText(alphabet[currentTouchIndex]);
						textViewDialog.setVisibility(View.VISIBLE);
					}
					
					currentChoosenAlphabetIndex = currentTouchIndex;
					invalidate();
				}
			}
		}
		
		// 事件在这里消耗完毕，不继续向上传递
		return true;
	}

	/**
	 * 触摸字母索引发生变化的回调接口
	 */
	private onLetterTouchedChangeListener onLetterTouchedChangeListener = null;
	
	public void setOnLetterTouchedChangeListener(
			onLetterTouchedChangeListener onLetterTouchedChangeListener) {
		this.onLetterTouchedChangeListener = onLetterTouchedChangeListener;
	}

	private onLetterTouchedChangeListener getOnLetterTouchedChangeListener() {
		return onLetterTouchedChangeListener;
	}

	/**
	 * 当手指触摸的字母索引发生变化时，调用该回调接口
	 * 
	 * @author owen
	 */
	public interface onLetterTouchedChangeListener {
		public void onTouchedLetterChange(String letterTouched);
	}
	
}
