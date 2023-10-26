package com.maxsella.fatmuscle.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.viewpager.widget.ViewPager;

public class CustomerViewPager extends ViewPager {

    private boolean isCanScroll = true;
    public CustomerViewPager(Context context) {
        super(context);
    }
    public CustomerViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public void setNoScroll(boolean noScroll) {
        this.isCanScroll = noScroll;
    }

    @Override
    public void scrollTo(int x, int y) {
        super.scrollTo(x, y);
    }

    @Override
    public boolean onTouchEvent(MotionEvent arg0) {

        if (isCanScroll) {
            return false;
        } else {
            return super.onTouchEvent(arg0);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        if (isCanScroll) {
            return false;
        } else {
            return super.onInterceptTouchEvent(arg0);
        }

    }

}

