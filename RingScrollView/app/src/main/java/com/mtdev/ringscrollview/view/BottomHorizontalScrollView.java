package com.mtdev.ringscrollview.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.mtdev.ringscrollview.ScrollViewListener;
import com.mtdev.ringscrollview.util.Utils;

import jp.wasabeef.blurry.Blurry;


/**
 * Created by yoush on 2017/4/8.
 */

public class BottomHorizontalScrollView extends LoopScrollView {

    private static final String TAG = "BottomHorizontalScrollV";
    private Context mContext;
    private ScrollViewListener mScrollViewListener;
    public static int BOTTOM_PARAM_WIDTH_FACTOR = 3;

    public BottomHorizontalScrollView(Context context) {
        this(context, null);
    }

    public BottomHorizontalScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BottomHorizontalScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
    }

    @Override
    public void addImageViewFromRes(int[] heros) {
        LinearLayout imageLayout = (LinearLayout) getChildAt(0);

        //Blurry.with(mContext).radius(25).sampling(2).onto(imageLayout);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(Utils.getWinWidth(mContext) / BOTTOM_PARAM_WIDTH_FACTOR, ViewGroup.LayoutParams.WRAP_CONTENT);
        mShowChildCount = heros.length;
        mImageWidth = params.width;
        for (int i = 0; i < heros.length * 2; ++i) {
            final ImageView iv = new ImageView(mContext);
            iv.setLayoutParams(params);
            iv.setImageResource(heros[i >= heros.length ? i - heros.length : i]);
            iv.setPadding(10, 0, 10, 0);
            imageLayout.addView(iv);

            iv.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    iv.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    Blurry.with(mContext)
                            .radius(25)
                            .sampling(1)
                            .color(Color.argb(66, 255, 255, 0))
                            .async()
                            .capture(iv)
                            .into(iv);
                }
            });
        }
    }

    public ScrollViewListener getScrollViewListener() {
        return mScrollViewListener;
    }

    public void setScrollViewListener(ScrollViewListener scrollViewListener) {
        mScrollViewListener = scrollViewListener;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (mScrollViewListener != null) {
            mScrollViewListener.onScrollChanged(this, l, t, oldl, oldt);
        }

        if (l <= mImageWidth * mShowChildCount / 2) {
            scrollBy(mImageWidth * mShowChildCount, 0); 
        } else if (l >= mImageWidth * (mShowChildCount / 2 + mShowChildCount)) {
            scrollBy(-mImageWidth * mShowChildCount, 0);
        }
    }
}
