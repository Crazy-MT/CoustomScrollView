package com.mtdev.linkscrollview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.HorizontalScrollView;

import com.mtdev.linkscrollview.ScrollViewListener;

/**
 * Created by yoush on 2017/4/8.
 */

public class BottomHorizontalScrollView extends HorizontalScrollView {

    private static final String TAG = "BottomHorizontalScrollV";
    private Context mContext;
    private ScrollViewListener mScrollViewListener;

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
    }
}
