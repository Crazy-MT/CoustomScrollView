package com.mtdev.linkscrollview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.HorizontalScrollView;

import com.mtdev.linkscrollview.view.BottomHorizontalScrollView;
import com.mtdev.linkscrollview.view.TopHorizontalScrollView;

public class MainActivity extends AppCompatActivity implements ScrollViewListener {

    private TopHorizontalScrollView mTopHSV;
    private BottomHorizontalScrollView mBottomHSV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTopHSV = (TopHorizontalScrollView) findViewById(R.id.id_top);
        mBottomHSV = (BottomHorizontalScrollView) findViewById(R.id.id_bottom);

        mTopHSV.setScrollViewListener(this);
        mBottomHSV.setScrollViewListener(this);
    }

    @Override
    public void onScrollStateChanged(HorizontalScrollView view, int scrollState, int index) {

    }

    @Override
    public void onScrollChanged(HorizontalScrollView scrollView, int x, int y, int oldx, int oldy) {
        if (scrollView == mTopHSV) {
            mBottomHSV.scrollTo(x, y);
        }

    }
}
