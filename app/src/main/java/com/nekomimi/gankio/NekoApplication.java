package com.nekomimi.gankio;

import android.app.Application;

import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;

/**
 * Created by hongchi on 2016-1-27.
 * File description :
 */
public class NekoApplication extends Application
{
    private static NekoApplication mNekoApplication = null;

    public static NekoApplication getInstance()
    {
        return mNekoApplication;
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        mNekoApplication = this;
        initUnitImageLoader();
    }

    private void initUnitImageLoader()
    {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
            .threadPoolSize(3)//线程池内加载的数量
            .threadPriority(Thread.NORM_PRIORITY - 2)
            .denyCacheImageMultipleSizesInMemory()
            .memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024)) // You can pass your own memory cache implementation/你可以通过自己的内存缓存实现
            .memoryCacheSize(2 * 1024 * 1024)
            .tasksProcessingOrder(QueueProcessingType.LIFO)
            .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
            .imageDownloader(new BaseImageDownloader(this, 5 * 1000, 30 * 1000)) // connectTimeout (5 s), readTimeout (30 s)超时时间
            .writeDebugLogs() // Remove for release app
            .build();//开始构建
        ImageLoader.getInstance().init(config);//全局初始化此配置
    }
}

