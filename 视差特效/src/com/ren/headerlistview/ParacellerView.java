package com.ren.headerlistview;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.ListView;

public class ParacellerView extends ListView {
	
	private int measuredHeight;

	private ImageView imageView;
	private int drawableHeight;
	public ParacellerView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public ParacellerView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ParacellerView(Context context) {
		super(context);
	}
	public void setImageView(final ImageView imageView){
		this.imageView = imageView;
		//获取图片设置到imageview后的高度
		drawableHeight = imageView.getDrawable().getIntrinsicHeight();
		imageView.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

			@Override
			public void onGlobalLayout() {
				measuredHeight = imageView.getMeasuredHeight();
				//如果测量之后不移除，那么就会不断的测量新的高度，最后导致startheight和endheight相同，值动画没有效果
				imageView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
				System.out.println("最初的高度："+measuredHeight);
				System.out.println("drable的高度："+imageView.getDrawable().getIntrinsicHeight());
			}
		});
	}
	@Override
	protected boolean overScrollBy(int deltaX, int deltaY, int scrollX,
			int scrollY, int scrollRangeX, int scrollRangeY,
			int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
		if (deltaY < 0 && isTouchEvent) {
			int newHeight = (int) (imageView.getHeight() + Math.abs(deltaY/3f));
			if (drawableHeight >= newHeight) {
				imageView.getLayoutParams().height = newHeight;
				//invalidate();无效
				//postInvalidate();
				imageView.requestLayout();
			}
			
		}
		
		return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX,
				scrollRangeY, maxOverScrollX, maxOverScrollY, isTouchEvent);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		if (ev.getAction() == MotionEvent.ACTION_UP) {
			int startHeight = imageView.getHeight();//松手时的高度
			int endHeight = measuredHeight;
			System.out.println("松手时的高度："+startHeight +" 最初的高度："+endHeight);
			//值动画
			ValueAnimator anim = ValueAnimator.ofInt(startHeight,endHeight);
			anim.addUpdateListener(new AnimatorUpdateListener() {
				
				@Override
				public void onAnimationUpdate(ValueAnimator animation) {
					int height = (Integer) animation.getAnimatedValue();
					imageView.getLayoutParams().height = height;
					imageView.requestLayout();
				}
			});
			
			anim.setInterpolator(new OvershootInterpolator(5));
			anim.setDuration(500);
			anim.start();
		}
		return super.onTouchEvent(ev);
	}

	
}
