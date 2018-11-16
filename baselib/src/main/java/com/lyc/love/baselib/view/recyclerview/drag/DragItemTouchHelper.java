package com.lyc.love.baselib.view.recyclerview.drag;

import android.graphics.Color;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.lyc.love.baselib.view.recyclerview.RecyclerAdapter;

import java.util.Collections;

/**
 * Created by wen on 2017/8/8.
 * ItemTouchHelper 一个帮助开发人员处理拖拽和滑动删除的实现类，它能够让你非常容易实现侧滑删除、拖拽的功能。
 */

public class DragItemTouchHelper extends ItemTouchHelper.Callback {

    RecyclerAdapter adapter;

    public DragItemTouchHelper(RecyclerAdapter adapter) {
        this.adapter = adapter;
    }

    //通过返回值来设置是否处理某次拖曳或者滑动事件
    //dragFlags 是拖拽标志，
    //swipeFlags 是滑动标志，
    //swipeFlags 都设置为0，暂时不考虑滑动相关操作。
    //getMovementFlags() 用于设置是否处理拖拽事件和滑动事件，以及拖拽和滑动操作的方向，有以下两种情况：
    //如果是列表类型的 RecyclerView，拖拽只有 UP、DOWN 两个方向
    //如果是网格类型的则有 UP、DOWN、LEFT、RIGHT 四个方向
    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
            int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
            int swipeFlags = 0;
            return makeMovementFlags(dragFlags, swipeFlags);
        } else {
            int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
            //注意：和拖曳的区别就是在这里
            int swipeFlags = ItemTouchHelper.START ;
//            | ItemTouchHelper.END
            return makeMovementFlags(dragFlags, swipeFlags);
        }

    }

    //当长按并进入拖曳状态时，拖曳的过程中不断的回调此方法
    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        //拖动的 item 的下标
        int fromPosition = viewHolder.getAdapterPosition();
        //目标 item 的下标，目标 item 就是当拖曳过程中，不断和拖动的 item 做位置交换的条目。
        int toPosition = target.getAdapterPosition();
        //对应某些需求，某一个item不能拖拽
        if (toPosition == 0) {
            return false;
        }
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                //通过你传入的adapter得到你的数据 并进行交换
                Collections.swap(adapter.getList(), i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(adapter.getList(), i, i - 1);
            }
        }
        adapter.notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    //滑动删除的回调
    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        int adapterPosition = viewHolder.getAdapterPosition();
        adapter.notifyItemRemoved(adapterPosition);
        adapter.getList().remove(adapterPosition);
    }

    //当长按 item 刚开始拖曳的时候调用
    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
            //给被拖曳的 item 设置一个深颜色背景
            viewHolder.itemView.setBackgroundColor(Color.LTGRAY);
        }
        super.onSelectedChanged(viewHolder, actionState);
    }

    //当完成拖曳手指松开的时候调用
    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        //给已经完成拖曳的 item 恢复开始的背景。
        // 这里我们设置的颜色尽量和你 item 在 xml 中设置的颜色保持一致
        viewHolder.itemView.setBackgroundColor(Color.RED);
    }

    //返回 false 让它控制所有的 item 都不能拖曳。
    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return true;
    }

    @Override
    public float getSwipeThreshold(RecyclerView.ViewHolder viewHolder) {
        return super.getSwipeThreshold(viewHolder);
    }

}

