package com.yuanyuan.baseapp.base;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;

import com.yuanyuan.baseapp.R;
import com.yuanyuan.baseapp.utils.L;
import com.yuanyuan.baseapp.utils.UIUtils;

/**
 * Created by Administrator on 2016/4/7.
 */
public abstract  class LoadingPager extends FrameLayout {
    /**
     //页面显示分析
     //Fragment共性-->页面共性-->视图的展示
     /**
     任何应用其实就只有4种页面类型
     ① 加载页面
     ② 错误页面
     ③ 空页面
     ④ 成功页面

     ①②③三种页面一个应用基本是固定的
     每一个fragment/activity对应的页面④就不一样
     进入应用的时候显示①,②③④需要加载数据之后才知道显示哪个
     */
    public static final int	STATE_NONE		= -1;			// 默认情况
    public static final int	STATE_LOADING	= 0;			// 正在请求网络
    public static final int	STATE_EMPTY		= 1;			// 空状态
    public static final int	STATE_ERROR		= 2;			// 错误状态
    public static final int	STATE_SUCCESS	= 3;			// 成功状态

    public int mCurState=STATE_NONE;
    private View mLoadingView;
    private View mErrorView;
    private View mEmptyView;
    private View mSuccessView;
    public LoadingPager(Context context) {
        super(context);
        initCommonView();
    }
    /**
     * @des 初始化常规视图
     * @call LoadingPager初始化的时候
     */
    private void initCommonView() {
        // ① 加载页面
        mLoadingView=View.inflate(UIUtils.getContext(), R.layout.pager_loading,null);
        this.addView(mLoadingView);

        // ② 错误页面
        mErrorView=View.inflate(UIUtils.getContext(),R.layout.pager_error,null);
        this.addView(mErrorView);
        mErrorView.findViewById(R.id.error_btn_retry).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // 从先触发加载数据
                loadData();
            }
        });
        this.addView(mErrorView);

        // ③ 空页面
        mEmptyView=View.inflate(UIUtils.getContext(),R.layout.pager_empty,null);
        this.addView(mEmptyView);

        refreshUI();
    }
    /**
     * @des 根据当前状态显示不同的view
     * @call 1. LoadingPager初始化的时候
     * @call 2.	显示正在加载数据
     * @call 3. 正在加载数据执行完成的时候
     */
    private void refreshUI() {
        // 控制loading视图显示隐藏
        mLoadingView.setVisibility((mCurState==STATE_LOADING||mCurState==STATE_NONE)?View.VISIBLE:View.GONE);

        // 控制emptyView视图显示隐藏
        mEmptyView.setVisibility(mCurState==STATE_EMPTY?View.VISIBLE:View.GONE);

        // 控制errorView视图显示隐藏
        mErrorView.setVisibility(mCurState==STATE_ERROR?View.VISIBLE:View.GONE);

        if (mSuccessView==null&&mCurState==STATE_SUCCESS){
            mSuccessView=initSuccessView();
            this.addView(mSuccessView);
        }

        if(mSuccessView!=null){
            // 控制success视图显示隐藏
            mSuccessView.setVisibility(mCurState==STATE_SUCCESS?View.VISIBLE:View.GONE);
        }
    }


    // 数据加载的流程
    /**
     ① 触发加载  	进入页面开始加载/点击某一个按钮的时候加载
     ② 异步加载数据  -->显示加载视图
     ③ 处理加载结果
     ① 成功-->显示成功视图
     ② 失败
     ① 数据为空-->显示空视图
     ② 数据加载失败-->显示加载失败的视图
     */
    /**
     * @des 触发加载数据
     * @call 暴露给外界调用,其实就是外界  触发加载数据
     */
    private void loadData() {
        // 如果加载成功,那就无需再次加载
        if(mCurState!=STATE_SUCCESS&&mCurState!=STATE_LOADING){
            L.d("数据加载中.......");
            // 保证每次执行的时候一定是加载中视图,而不是上次的mCurState
            int state=STATE_LOADING;
            mCurState=state;
            refreshUI();// 2. 显示正在加载数据
            //加载数据
            LoadedResult tempState=initData();
        }
    }
    /**
     * @des 正在加载数据,必须实现,但是不知道具体实现,定义成为抽象方法,让子类具体实现
     * @call loadData()方法被调用的时候
     */
    public abstract LoadedResult initData();

    /**
     * @des 返回成功视图
     * @call 正在加载数据完成之后,并且数据加载成功,我们必须告知具体的成功视图
     */
    public abstract View initSuccessView();

    /**
     * 枚举限定结果
     */
    public enum LoadedResult{
        SUCCESS(STATE_SUCCESS),ERROR(STATE_ERROR),EMPTY(STATE_EMPTY);
        int state;
        public int getState(){
            return state;
        }
        private LoadedResult(int state){
            this.state=state;
        }
    }

    public void setmCurState(int mCurState) {
        this.mCurState = mCurState;
    }
}
