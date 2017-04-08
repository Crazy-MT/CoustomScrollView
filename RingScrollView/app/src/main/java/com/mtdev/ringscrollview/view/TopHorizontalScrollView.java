package com.mtdev.ringscrollview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.HorizontalScrollView;

import com.mtdev.ringscrollview.ScrollViewListener;

/**
 * Created by yoush on 2017/4/8.
 */

public class TopHorizontalScrollView extends LoopScrollView {

    private static final String TAG = "TopHorizontalScrollView";
    private Context mContext;
    private ScrollViewListener mScrollViewListener;

    public TopHorizontalScrollView(Context context) {
        this(context, null);
    }

    public TopHorizontalScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TopHorizontalScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (mScrollViewListener != null){
            mScrollViewListener.onScrollChanged(this, l, t, oldl, oldt);
        }
    }



    public ScrollViewListener getScrollViewListener() {
        return mScrollViewListener;
    }

    public void setScrollViewListener(ScrollViewListener scrollViewListener) {
        mScrollViewListener = scrollViewListener;
    }
}
