package com.yuanyuan.baseapp.myapp;

import android.app.Application;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.yuanyuan.baseapp.R;

/**
 * Created by Aron on 2016/2/24.
 */
public class MyApplication extends Application {
    /**
     * 在需要Context对象地方，可以使用MyApp对象
     * mInstance为静态的，近似采用单例模式（因为这是安卓中的内容，不能将MyApp构造器写成私有）
     */
    private static MyApplication mMyApplication;
    //主线程
    private static Thread mCurrentMainThread;
    //主线程ID
    private static long mMainThreadId;
    //主线程Looper
    private static Looper mMianLooper;
    //主线程 Hanlder
    private static Handler mHandler;
    @Override
    public void onCreate() {
        super.onCreate();
        mMyApplication=this;
        mCurrentMainThread= Thread.currentThread();
        mMainThreadId=android.os.Process.myTid();
        mHandler=new Handler();
        initImagerLoader();
    }

    /**
     * 获取单列的APP
     * @return
     */
    public static MyApplication getInstance() {
        return mMyApplication;
    }

    /**
     * 初始化ImageLoader框架的对象,并设置
     */
    private void initImagerLoader() {
        //默认的Options
        DisplayImageOptions dconfig = null;
        dconfig = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.drawable.zhengfangxingweijiazai)//图片加载过程中显示的默认图片
                .showImageOnFail(R.drawable.zhengfangxingweijiazai)//加载图片出错显示的图片
                .cacheInMemory(true)//是否加载缓存
                .cacheOnDisk(true)//是否存储本地
                .bitmapConfig(Bitmap.Config.RGB_565)
                .imageScaleType(ImageScaleType.EXACTLY)
                .displayer(new FadeInBitmapDisplayer(100))//设置图片渐显的时间
                //.FadeInBitmapDisplayer()//设置图片渐显的时间
                // .SimpleBitmapDisplayer()//正常显示一张图片
                // .displayer(new SimpleBitmapDisplayer())//设置正常显示一张图片
                .build();
        ImageLoaderConfiguration config =new ImageLoaderConfiguration.Builder(getApplicationContext())
                .threadPoolSize(5)//设置线程数量一般1-5最好
                .denyCacheImageMultipleSizesInMemory()
                .threadPriority(Thread.MAX_PRIORITY-2)//线程的优先级
                .tasksProcessingOrder(QueueProcessingType.FIFO) // default
                .memoryCacheExtraOptions(480, 800)//缓存的最大宽高
                .memoryCache(new UsingFreqLimitedMemoryCache(2*1024*1024))//自定义内存缓存，如果出现内存溢出可以不添加
                .memoryCacheSize(2*1024*1024)//最大的缓存数量
                .discCacheSize(50 * 1024 * 1024) // 50 Mb sd卡(本地)缓存的最大值
                // .diskCacheFileCount(100)  // 可以缓存的文件数量
                .discCacheFileNameGenerator(new Md5FileNameGenerator())//加密
                .defaultDisplayImageOptions(dconfig)//设置默认情况下的Option
                .imageDownloader(new BaseImageDownloader(getApplicationContext(), 5 * 1000, 30 * 1000)).build(); // connectTimeout (5 s),
        ImageLoader.getInstance().init(config);
    }


    public static Thread getmCurrentMainThread() {
        return mCurrentMainThread;
    }

    public static long getmMainThreadId() {
        return mMainThreadId;
    }

    public static Looper getmMianLooper() {
        return mMianLooper;
    }

    public static Handler getmHandler() {
        return mHandler;
    }
}
