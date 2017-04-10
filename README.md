# LoopScrollView
## 描述
可以循环滚动的 ScrollView 。
## 效果
![LoopScrollView](https://github.com/MTAndroidDev/CoustomScrollView/blob/master/gif/LoopScrollView.gif)
## 如何用？
如系统控件 HorizontalScrollView 一样使用，需要 LineraLayout 作为子控件。
```
<com.mtdev.loopscrollview.view.LoopScrollView
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:id="@+id/id_root">
    <LinearLayout
        android:layout_width="wrap_content"
        android:layout_height="match_parent"
        android:gravity="center"
        android:orientation="horizontal">
    </LinearLayout>
</com.mtdev.loopscrollview.view.LoopScrollView>
```
代码中调用 addImageViewFromRes() 方法设置图片资源：
```
((LoopScrollView) findViewById(R.id.id_root)).
                  addImageViewFromRes(new int[]{R.mipmap.hero_donghuangtaiyi, R.mipmap.hero_chengjisihan, 
                  R.mipmap.hero_daqiao, R.mipmap.hero_huangzhong, R.mipmap.hero_neza, R.mipmap.hero_taiyizhenren, 
                  R.mipmap.hero_yangjian, R.mipmap.hero_zhugeliang});
```
