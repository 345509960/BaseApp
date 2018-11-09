package com.lyc.love.baselib.view.recyclerview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.blankj.utilcode.util.SizeUtils;
import com.lyc.love.baselib.R;

import java.util.ArrayList;

/**
 * Created by liangyc
 * Time :2018/11/8
 * Des:
 */
public class SuctionTopDecoration<T extends SuctionTopDecoration.SuctionTopDecorationGroup> extends RecyclerView.ItemDecoration {

    private ArrayList<T> mDataList;
    private TextPaint mTextPaint;
    private Paint mBgPaint;
    private int mHeight;
    private int mAlignLeft;
    private Paint.FontMetrics mFontMetrics;

    private int mBgColor;

    private float mTextSize = SizeUtils.dp2px(14);
    private int mTextColor;


    public SuctionTopDecoration(ArrayList<T> dataList, Context context) {
        this.mDataList = dataList;
        initResConfig(context);


    }

    private void initResConfig(Context context) {

        mBgColor = ContextCompat.getColor(context, R.color.default_suction_top_bg);
        mTextSize = context.getResources().getDimension(R.dimen.default_suction_top_text_size);
        mTextColor = ContextCompat.getColor(context, R.color.default_suction_top_text_color);

        //设置悬浮栏的画笔---mBgPaint
        mBgPaint = new Paint();
        mBgPaint.setColor(mBgColor);


        //设置悬浮栏中文本的画笔
        mTextPaint = new TextPaint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setColor(mTextColor);
        mTextPaint.setTextAlign(Paint.Align.LEFT);
        mFontMetrics = new Paint.FontMetrics();

        //决定悬浮栏的高度等
        mHeight = context.getResources().getDimensionPixelSize(R.dimen.default_suction_top_height);
        //决定文本的显示位置等
        mAlignLeft = context.getResources().getDimensionPixelSize(R.dimen.default_suction_top_text_align_left);
    }

    //图1：代表了getItemOffsets(),可以实现类似padding的效果
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int pos = parent.getChildAdapterPosition(view);

        String groupId = mDataList.get(pos).getSuctionTopDecorationGroup();
        if (TextUtils.isEmpty(groupId)) {
            return;
        }
        //只有是同一组的第一个才显示悬浮栏
        if (isFirstInGroup(pos)) {
            outRect.top = mHeight;
        } else {
            outRect.top = 0;
        }
    }


    //代表了onDrawOver()，可以绘制在内容的上面，覆盖内容
    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);

        int itemCount = state.getItemCount();
        int childCount = parent.getChildCount();
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();

        String preGroupName = "";
        String groupName = "";
        for (int i = 0; i < childCount; i++) {
            View view = parent.getChildAt(i);
            int position = parent.getChildAdapterPosition(view);

            preGroupName = groupName;
            groupName = mDataList.get(position).getSuctionTopDecorationGroup();
            if (TextUtils.isEmpty(groupName) || groupName.equals(preGroupName)) {
                continue;
            }

            String text = mDataList.get(position).getSuctionTopDecorationGroup().toUpperCase();
            int viewBottom = view.getBottom();
            float textY = Math.max(mHeight, view.getTop());
            //下一个和当前不一样移动当前
            if (position + 1 < itemCount) {
                String nextGroupName = mDataList.get(position + 1).getSuctionTopDecorationGroup();
                //组内最后一个view进入了header
                if (!nextGroupName.equals(groupName) && viewBottom < textY) {
                    textY = viewBottom;
                }
            }
            //textY -mHeight决定了悬浮栏绘制的高度和位置
            c.drawRect(left, textY - mHeight, right, textY, mBgPaint);

            //解决高度绘制不居中
            Paint.FontMetricsInt fm = mTextPaint.getFontMetricsInt();
            int startY = (int) (textY - mHeight / 2 - fm.descent + (fm.bottom - fm.top) / 2);

            c.drawText(text, left + mAlignLeft, startY, mTextPaint);
        }
    }


    /**
     * 判断是不是组中的第一个位置
     *
     * @param pos
     * @return
     */
    private boolean isFirstInGroup(int pos) {
        if (pos == 0) {
            return true;
        } else {
            // 因为是根据 字符串内容的相同与否 来判断是不是同意组的，所以此处的标记id 要是String类型
            // 如果你只是做联系人列表，悬浮框里显示的只是一个字母，则标记id直接用 int 类型就行了
            String prevGroupName = mDataList.get(pos - 1).getSuctionTopDecorationGroup();
            String groupName = mDataList.get(pos).getSuctionTopDecorationGroup();
            //判断前一个字符串 与 当前字符串 是否相同
            if (prevGroupName.equals(groupName)) {
                return false;
            } else {
                return true;
            }
        }
    }

    public interface SuctionTopDecorationGroup {
        String getSuctionTopDecorationGroup();
    }
}

