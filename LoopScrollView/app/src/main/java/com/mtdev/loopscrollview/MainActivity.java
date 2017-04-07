package com.mtdev.loopscrollview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mtdev.loopscrollview.view.LoopScrollView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LoopScrollView loopSV = (LoopScrollView) findViewById(R.id.id_root);

        loopSV.addImageViewFromRes(new int[]{R.mipmap.hero_donghuangtaiyi, R.mipmap.hero_chengjisihan, R.mipmap.hero_daqiao, R.mipmap.hero_huangzhong, R.mipmap.hero_neza, R.mipmap.hero_taiyizhenren, R.mipmap.hero_yangjian, R.mipmap.hero_zhugeliang});

    }
}
