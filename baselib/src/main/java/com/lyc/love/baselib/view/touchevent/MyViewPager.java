package com.lyc.love.baselib.view.touchevent;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by liangyc
 * Time :2018/11/16
 * Des:从子View着手，父View 先不要拦截任何事件，所有的 事件传递给 子View，如果 子View 需要此事件就消费掉，不需要此事件的话就交给 父View 处理。
 * 实现思路 如下，重写 子View 的 dispatchTouchEvent 方法，在 Action_down 动作中通过方法 requestDisallowInterceptTouchEvent（true） 先请求 父View 不要拦截事件，
 * 这样保证 子View 能够接受到Action_move事件，再在Action_move动作中根据 自己的逻辑是否要拦截事件，不要的话再交给 父View 处理
 */
public class MyViewPager extends ViewPager {

    private static final String TAG = "yc";

    int lastX = -1;
    int lastY = -1;

    public MyViewPager(Context context) {
        super(context);
    }

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int x = (int) ev.getRawX();
        int y = (int) ev.getRawY();
        int dealtX = 0;
        int dealtY = 0;
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                dealtX = 0;
                dealtY = 0;
                // 保证子View能够接收到Action_move事件
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:
                dealtX += Math.abs(x - lastX);
                dealtY += Math.abs(y - lastY);
                // 这里是够拦截的判断依据是左右滑动，读者可根据自己的逻辑进行是否拦截
                if (dealtX >= dealtY) {
                    getParent().requestDisallowInterceptTouchEvent(true);
                } else {
                    getParent().requestDisallowInterceptTouchEvent(false);
                }
                lastX = x;
                lastY = y;
                break;
            case MotionEvent.ACTION_CANCEL:
                break;
            case MotionEvent.ACTION_UP:
                break;
            default:
                break;
        }
        return super.dispatchTouchEvent(ev);
    }
}
