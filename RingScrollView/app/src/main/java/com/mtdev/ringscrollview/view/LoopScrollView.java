package com.mtdev.ringscrollview.view;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.v4.view.VelocityTrackerCompat;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.mtdev.ringscrollview.ScrollViewListener;
import com.mtdev.ringscrollview.util.Utils;


/**
 * Created by yoush on 2017/4/7.
 */

public class LoopScrollView extends HorizontalScrollView {

    private Context mContext;
    private static final String TAG = "LoopScrollView";
    private static final int MAX_SETTLE_DURATION = 600;

    private ObjectAnimator mAnimator;
    private VelocityTracker mVelocityTracker;

    private int mMaximumVelocity;
    private int mDownX = 0;
    private int mVelocityX;
    protected int mScrollState = ScrollViewListener.SCROLL_STATE_IDLE;
    protected boolean mIsTouched = false;
    protected int mShowChildCount;
    protected int mImageWidth;
    public LoopScrollView(Context context) {
        this(context, null);
    }

    public LoopScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoopScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        setHorizontalScrollBarEnabled(false);
        ViewConfiguration configuration = ViewConfiguration.get(context);
        mMaximumVelocity = configuration.getScaledMaximumFlingVelocity();
        mImageWidth = Utils.getWinWidth(context);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        setVelocityTracker(ev);

        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            mIsTouched = true;
            mDownX = (int) ev.getX();
            if (cancelAnimator()) {
                return true;
            }
        }

        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        setVelocityTracker(ev);
        int activePointerId = ev.getPointerId(0);
        int x = getScrollX();

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownX = (int) ev.getX();
                cancelAnimator();
                break;

            case MotionEvent.ACTION_MOVE:
                if (x <= mImageWidth * mShowChildCount/2) {
                    scrollBy(mImageWidth * mShowChildCount, 0);
                    mDownX += mImageWidth * mShowChildCount;
                } else if (x >= mImageWidth * (mShowChildCount / 2 + mShowChildCount)) {
                    scrollBy(-mImageWidth * mShowChildCount, 0);
                }
                break;

            case MotionEvent.ACTION_UP:

                VelocityTracker velocityTracker = mVelocityTracker;
                velocityTracker.computeCurrentVelocity(1000, mMaximumVelocity);
                mVelocityX = (int) VelocityTrackerCompat.getXVelocity(velocityTracker, activePointerId);

                int scrollTo = scrollDistance(ev);
                int duration = getDuration(scrollTo);

                mAnimator = ObjectAnimator.ofInt(this, "scrollX", getScrollX(), scrollTo).setDuration(duration);
                mAnimator.setInterpolator(new LinearOutSlowInInterpolator());
                mAnimator.start();
                mAnimator.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) { 
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mScrollState = ScrollViewListener.SCROLL_STATE_IDLE;
                        Log.e(TAG, "onAnimationEnd: " );
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });

            case MotionEvent.ACTION_CANCEL:
                mIsTouched = false;
                return true;
        }

        return super.onTouchEvent(ev);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (mIsTouched){
            mScrollState = ScrollViewListener.SCROLL_STATE_TOUCH_SCROLL;
        } else {
            mScrollState = ScrollViewListener.SCROLL_STATE_FLING;
        }
    }

    private void setVelocityTracker(MotionEvent ev) {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(ev);
    }

    private int getDuration(int scrollTo) {
        int duration;
        mVelocityX = Math.abs(mVelocityX);
        if (mVelocityX > 0) {
            duration = 4 * Math.round(1000 * Math.abs(Math.abs(getScrollX() - scrollTo) / mVelocityX));
        } else {
            duration = (int) ((0.5 + 1) * 100);
        }
        duration = Math.max(duration, MAX_SETTLE_DURATION);
        duration = duration > 5000 ? MAX_SETTLE_DURATION : duration;
        return duration;
    }

    private int scrollDistance(MotionEvent ev) {
        int childChangeNum = -mVelocityX / 2500;
        if (childChangeNum == 0) {
            if (Math.abs(ev.getX() - mDownX) > getWidth() / 2) {
                childChangeNum = (ev.getX() - mDownX > 0) ? childChangeNum : childChangeNum + 1;
            } else {
                if (ev.getX() - mDownX > 0) {
                    if (getScrollX() % mImageWidth > mImageWidth / 2) {
                        childChangeNum++;
                    }
                } else if (ev.getX() - mDownX == 0) {
                    if (getScrollX() % mImageWidth > mImageWidth / 2) {
                        childChangeNum++;
                    }
                }
            }
        }
        if (childChangeNum == -1){
            childChangeNum = 0;
        }

        return (getScrollX() - getScrollX() % mImageWidth) + mImageWidth * childChangeNum;
    }

    private boolean cancelAnimator() {
        if (mAnimator != null && mAnimator.isRunning()) {
            mAnimator.cancel();
            return true;
        }
        return false;
    }

    /**
     * @param heros
     */
    public void addImageViewFromRes(int[] heros) {
        LinearLayout imageLayout = (LinearLayout) getChildAt(0);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(Utils.getWinWidth(mContext), Utils.getWinHeight(mContext));
        mShowChildCount = heros.length;
        for (int i = 0; i < heros.length * 2; ++i) {
            ImageView iv = new ImageView(mContext);
            iv.setLayoutParams(params);
            iv.setImageResource(heros[i >= heros.length ? i - heros.length : i]);
            iv.setPadding(100, 0, 100, 0);
            imageLayout.addView(iv);
        }
    }
}
