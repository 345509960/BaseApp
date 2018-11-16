package com.lyc.love.baselib.ui;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.lyc.love.baselib.R;

/**
 * @author lyc
 * create date: 2018/5/7 0007
 * des:基础Activity类
 **/
public abstract class AbstractActivity extends AppCompatActivity {

    private LinearLayout mRoot;

    private FrameLayout mContent;

    Toolbar mToolbar;

    public static final int SERVICE_EXCECTION = -1;
    public static final int UNNETWORK = -2;
    public static final int UNDATA = -3;
    public static final int OK = 1;
    public static final int RUNNING = 2;


    private View mView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abstact);
        configUiOption();
        addContentRootView();
        initToolBar();
        assert mView != null;
        initView(mView);
        initData();
        initEvent();
    }

    protected abstract void initEvent();

    protected abstract void initData();

    private void configUiOption() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            View decorView = getWindow().getDecorView();
        }
    }

    private void addContentRootView() {
        mRoot = findViewById(R.id.root);
        mContent = findViewById(R.id.content);
        mContent.removeAllViews();
        mView = LayoutInflater.from(this).inflate(getLayoutId(), mContent, false);
        mContent.addView(mView);
    }

    public void initView(View view) {

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if (NavUtils.getParentActivityName(this) != null) {
                NavUtils.navigateUpFromSameTask(this);
            } else {
                finish();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void initToolBar() {
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            //默认为null
            getSupportActionBar().setLogo(null);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            setStatusBarColor(R.color.colorPrimary);
        }
    }

    public void setSupportActionBarTitle(String title) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }
    }

    public void setSupportActionBarLogo(Integer resId) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setLogo(resId);
        }
    }

    @Deprecated
    public void setSupportActionBarLogoEmpty() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setLogo(null);
        }
    }

    public void setDisplayShowTitleEnabled(boolean show) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(show);
        }
    }

    /**
     * 设置左边图标
     * 默认不显示左边的图标
     * @param resId
     * @param showCloseActivity 控制是否finish
     */
    public void setToolbarNavigationIcon(@DrawableRes int resId, boolean showCloseActivity){
        if (mToolbar!=null&&resId!=-1){
            mToolbar.setNavigationIcon(resId);
            if (showCloseActivity){
                mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                    }
                });
            }else {
                mToolbar.setNavigationOnClickListener(null);
            }
        }
    }

    /**
     * 设置左边图标 并扩展点击事件
     * @param resId
     * @param onClickListener
     */
    public void setToolbarNavigationIcon(@DrawableRes int resId,View.OnClickListener onClickListener){
        if (mToolbar!=null&&resId!=-1){
            mToolbar.setNavigationIcon(resId);
            mToolbar.setNavigationOnClickListener(onClickListener);

        }
    }

    /**
     * 设置标题栏左侧的字体描述 为空不显示
     * @param title
     */
    public void setToolbarTitle( String title){
        if (mToolbar!=null){
            mToolbar.setTitle(title);
        }
    }


    public void setDisplayUseLogoEnabled(boolean show) {
        if (getSupportActionBar() != null) {

            getSupportActionBar().setDisplayUseLogoEnabled(show);

        }
    }

    public void setCustomToolBar(View view) {
        if (mToolbar != null) {
            mToolbar.addView(view);
            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayUseLogoEnabled(false);
                getSupportActionBar().setDisplayShowTitleEnabled(false);
                getSupportActionBar().setDisplayShowTitleEnabled(false);
            }
        }
    }

    public void setDisplayHomeAsUpEnabled(boolean show) {
        if (getSupportActionBar() != null) {

            getSupportActionBar().setDisplayHomeAsUpEnabled(show);

        }
    }

    public void setSupportActionBarBackgroundDrawable(@ColorRes int color) {
        if (getSupportActionBar() != null) {
            ColorDrawable colorDrawable = new ColorDrawable(ContextCompat.getColor(this, color));
            getSupportActionBar().setBackgroundDrawable(colorDrawable);
        }
    }


    public void setStatusBarColor(@ColorRes int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, color));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mRoot.setBackgroundColor(ContextCompat.getColor(this, color));
        }
    }


    protected abstract int getLayoutId();


    public Toolbar getToolbar() {
        return mToolbar;
    }
}
