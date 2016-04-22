package com.nekomimi.gankio.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nekomimi.gankio.R;
import com.nekomimi.gankio.base.AppAction;
import com.nekomimi.gankio.bean.GankDate;
import com.nekomimi.gankio.bean.GankEntity;
import com.nekomimi.gankio.bean.GankItem;
import com.nekomimi.gankio.utils.NetUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by hongchi on 2016-3-1.
 * File description :
 */
public class HomeActivity extends BaseActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener
{
    private static final String TAG = "HomeActivity";
    private ActionBarDrawerToggle mActionBarDrawerToggle;
    private RAdapter mAdapter;
    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;
    private Button mSettingBt;
    private Button mQuitBt;
    private CircleImageView mFaceIv;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private AppBarLayout mAppBarLayout;
    private Button mTestBt;

    private boolean mFlag = false;
    private int mPage = 1;
    private int mPageNum = 10;
    private State mState;
    private boolean mIfQuit = false;
    private List<GankEntity> mData;
    private Calendar mCalendar;
    private Context mContext;
    private UiHandler mUiHandler = new UiHandler(this);

    protected ImageLoader mImageLoader = ImageLoader.getInstance();
    protected DisplayImageOptions mOptions;

    enum State{
        All,Android,Ios,App,休息视频,拓展资源,瞎推荐,前端,福利
    }
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initData();
        initView();
    }

    private void initView()
    {
        mToolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        if(getSupportActionBar() != null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawerLayout);
        mActionBarDrawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout,mToolbar,R.string.open,R.string.close)
        {
            @Override
            public void onDrawerClosed(View drawerView)
            {
                super.onDrawerClosed(drawerView);
                //mAppBarLayout.setExpanded(false);
                Log.d(TAG, "drawer close");
                mFlag = false;
            }
            @Override
            public void onDrawerOpened(View drawerView)
            {
                super.onDrawerOpened(drawerView);
                mAppBarLayout.setExpanded(true);
                Log.d(TAG,"drawer open");
                mFlag = true;
            }
        };
        mActionBarDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mActionBarDrawerToggle);
        final NavigationView mNavTab = (NavigationView)findViewById(R.id.nav_view);
        mNavTab.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener()
        {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                Toast.makeText(getContext(), menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                dataReset();
                switch (menuItem.getItemId())
                {
                    case R.id.nav_menu_all:
                        mState = State.All;
                        AppAction.getInstance().day(mCalendar,mUiHandler);
                        break;
                    case R.id.nav_menu_android:
                        mState = State.Android;
                        AppAction.getInstance().data(mUiHandler, "Android", mPageNum + "", mPage + "");
                        break;
                    case R.id.nav_menu_ios:
                        mState = State.Ios;
                        AppAction.getInstance().data(mUiHandler,"iOs",mPageNum+"",mPage+"");
                        break;
                    case R.id.nav_menu_app:
                        mState = State.App;
                        AppAction.getInstance().data(mUiHandler,"App",mPageNum+"",mPage+"");
                        break;
                    case R.id.nav_menu_休息视频:
                        mState = State.休息视频;
                        AppAction.getInstance().data(mUiHandler,"休息视频",mPageNum+"",mPage+"");
                        break;
                    case R.id.nav_menu_拓展资源:
                        mState = State.拓展资源;
                        AppAction.getInstance().data(mUiHandler,"拓展资源",mPageNum+"",mPage+"");
                        break;
                    case R.id.nav_menu_瞎推荐:
                        mState = State.瞎推荐;
                        AppAction.getInstance().data(mUiHandler,"瞎推荐",mPageNum+"",mPage+"");
                        break;
                    case R.id.nav_menu_前端:
                        mState = State.前端;
                        AppAction.getInstance().data(mUiHandler,"前端",mPageNum+"",mPage+"");
                        break;
//                    case R.id.nav_menu_福利:
//                        mState = State.福利;
//                        AppAction.getInstance().data(mUiHandler,"福利",mPageNum+"",mPage+"");
//                        break;
                    default:
                        break;
                }
                mDrawerLayout.closeDrawers();
                return false;
            }
        });
        mFaceIv = (CircleImageView)mNavTab.getHeaderView(0).findViewById(R.id.iv_face);

        if(mFaceIv == null)
        {
            Log.e(TAG,"noob");
        }
       // mFaceIv.setOnClickListener(this);
        mSettingBt = (Button)findViewById(R.id.drawer_setting);
        mSettingBt.setOnClickListener(this);
        mQuitBt = (Button)findViewById(R.id.drawer_quit);
        mQuitBt.setOnClickListener(this);
        mSwipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.refreshLayout);
        mSwipeRefreshLayout.setColorSchemeColors(R.color.colorPrimary, R.color.colorPrimaryDark, R.color.colorRipple);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mRecyclerView = (RecyclerView)findViewById(R.id.rv_datalist);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new RAdapter();
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            int lastVisibleItem;
            private static final float HIDE_THRESHOLD = 100;
            private static final float SHOW_THRESHOLD = 50;
            int scrollDist = 0;
            private boolean isVisible = true;
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition();
                //  Check scrolled distance against the minimum
                if (isVisible && scrollDist > HIDE_THRESHOLD) {
                    //  Hide fab & reset scrollDist
                    hideFloatBt();
                    scrollDist = 0;
                    isVisible = false;
                }
                //  -MINIMUM because scrolling up gives - dy values
                else if (!isVisible && scrollDist < -SHOW_THRESHOLD  )  {
                    //  Show fab & reset scrollDist
                    showFloatBt();
                    scrollDist = 0;
                    isVisible = true;
                }

                //  Whether we scroll up or down, calculate scroll distance
                if ((isVisible && dy > 0) || (!isVisible && dy < 0)) {
                    scrollDist += dy;
                }
            }
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItem + 1 == recyclerView.getAdapter().getItemCount()) {
                    loadmore();
                }
            }

//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                lastVisibleItem = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition();
//            }
        });
        mAppBarLayout = (AppBarLayout)findViewById(R.id.tabanim_appbar);
        mTestBt = (Button)findViewById(R.id.test_bt);
        mTestBt.setOnClickListener(this);
        dataReset();
        AppAction.getInstance().day(mCalendar, mUiHandler);
    }

    private void initData()
    {
        mContext = this;
        mData = new ArrayList<>();
        mCalendar = Calendar.getInstance();
        mState = State.All;

        mOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)//设置下载的图片是否缓存在内存中
                .considerExifParams(true)  //是否考虑JPEG图像EXIF参数（旋转，翻转）
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)//设置图片以如何的编码方式显示
                .bitmapConfig(Bitmap.Config.RGB_565)//设置图片的解码类型//
                .resetViewBeforeLoading(true)//设置图片在下载前是否重置，复位
                .displayer(new RoundedBitmapDisplayer(20))//是否设置为圆角，弧度为多少
                .displayer(new FadeInBitmapDisplayer(100))//是否图片加载好后渐入的动画时间
                .build();//构建完成
    }

    private void loadmore()
    {
        mSwipeRefreshLayout.setRefreshing(true);
        switch (mState)
        {
            case All:
                AppAction.getInstance().day(mCalendar, mUiHandler);
                break;
            case Android:
                AppAction.getInstance().data(mUiHandler, "Android", mPageNum + "", mPage + "");
                break;
            case Ios:
                AppAction.getInstance().data(mUiHandler, "iOs", mPageNum + "", mPage + "");
                break;
            case App:
                AppAction.getInstance().data(mUiHandler, "App", mPageNum + "", mPage + "");
                break;
            case 休息视频:
                AppAction.getInstance().data(mUiHandler, "休息视频", mPageNum + "", mPage + "");
                break;
            case 拓展资源:
                AppAction.getInstance().data(mUiHandler, "拓展资源", mPageNum + "", mPage + "");
                break;
            case 瞎推荐:
                AppAction.getInstance().data(mUiHandler, "瞎推荐", mPageNum + "", mPage + "");
                break;
            case 福利:
                AppAction.getInstance().data(mUiHandler, "福利", mPageNum + "", mPage + "");
                break;
            default:
                break;
        }


    }
    public void dataReset()
    {
        mData.clear();
        mPage = 1;
        mCalendar = Calendar.getInstance();
    }
    public void showFloatBt()
    {
        Log.d(TAG, "showFloatBt");
        mTestBt.animate().alpha(1);
        mTestBt.animate().scaleX(1).scaleY(1).setInterpolator(new DecelerateInterpolator(2)).start();
    }

    public void hideFloatBt()
    {
        Log.e(TAG,"hideFloatBt");
        mTestBt.animate().alpha(0);
        mTestBt.animate().scaleX(0).scaleY(0).setInterpolator(new AccelerateInterpolator(2)).start();
    }

    @Override
    public void onBackPressed() {
        if (mFlag) {
            mDrawerLayout.closeDrawers();
        }
        else if(mIfQuit)
        {
            finish();
        }
        else
        {
            Toast.makeText(this,"press back to quit",Toast.LENGTH_LONG).show();
            mIfQuit = true;
            mUiHandler.postDelayed(runnable,3000);
        }
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            mIfQuit = false;
        }
    };
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.test_bt:
               Log.d(TAG,"testButton Clicked");
                break;
            case R.id.drawer_setting:
                startActivity(new Intent(HomeActivity.this, SettingsActivity.class));
                break;
            default:
                break;
        }
    }

    @Override
    public void onRefresh() {
        dataReset();
        AppAction.getInstance().day(mCalendar, mUiHandler);
    }

    @Override
    public void handleMessage(Message message)
    {
        Log.d(TAG, "handleMessage");
        mSwipeRefreshLayout.setRefreshing(false);
        if(message.what == 0)
        {
            if(message.arg1 == AppAction.DAY_REQUEST)
            {
                mData.addAll(mData.size(), ((GankDate) message.obj).getResults().getAll());
                mCalendar.add(Calendar.DAY_OF_MONTH, -1);
                mAdapter.notifyDataSetChanged();
            }
            if(message.arg1 == AppAction.DATA_REQUEST)
            {
                mData.addAll(mData.size(),((GankItem)message.obj).getResults());
                mPage++;
                mAdapter.notifyDataSetChanged();
            }

        }
        else
        {
            mCalendar.add(Calendar.DAY_OF_MONTH, -1);
            AppAction.getInstance().day(mCalendar, mUiHandler);
        }

    }

    class RAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
    {
        private final int  NORMAL = 0;
        private final int  VIDEO = 1;
        private final int  IMAGE = 2;
        private final int  TITLE = 3;

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if(viewType == NORMAL)
                return new NormalCardHolder( LayoutInflater.from(mContext).inflate(R.layout.view_itemcard,parent,false));
            if(viewType == IMAGE)
                return new ImageHolder( LayoutInflater.from(mContext).inflate(R.layout.view_imagecard, parent, false));
            if(viewType == VIDEO)
                return new VideoHolder(LayoutInflater.from(mContext).inflate(R.layout.view_videocard, parent, false));
            return null;
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }

        @Override
        public int getItemViewType(int position)
        {
            if(mData.get(position).getType().equals("福利"))
            {
                return IMAGE;
            }
//            else if(mData.get(position).getType().equals("休息视频"))
//            {
//                return VIDEO;
//            }
            else
            {
                return NORMAL;
            }
        }

        @Override
        public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position)
        {
            if(holder == null) return;
            if(holder instanceof NormalCardHolder)
            {
                NormalCardHolder normalHolder = (NormalCardHolder)holder;
                normalHolder.itemView.setTag(holder);
                normalHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (v.getTag() instanceof ImageHolder) {
                            return;
                        }
                        String way = PreferenceManager.getDefaultSharedPreferences(HomeActivity.this).getString(getString(R.string.open_way),getString(R.string.this_app));
                        if(way.equals(getString(R.string.this_app)))
                        {
                            Intent intent = new Intent(HomeActivity.this, DetailActivity.class);
                            intent.putExtra("URL", mData.get(position).getUrl());
                            intent.putExtra("TITLE", mData.get(position).getDesc());
                            startActivity(intent);
                        }
                        if(way.equals(getString(R.string.browser)))
                        {
                            Intent it = new Intent(Intent.ACTION_VIEW, Uri.parse(mData.get(position).getUrl()));
                            startActivity(it);
                        }

                    }
                });
//                normalHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
//                    @Override
//                    public boolean onLongClick(View v) {
//                        if (v.getTag() instanceof ImageHolder) {
//                            return false;
//                        }
//                        Intent it = new Intent(Intent.ACTION_VIEW, Uri.parse(mData.get(position).getUrl()));
////                        it.setClassName("com.android.browser", "com.android.browser.BrowserActivity");
//                        startActivity(it);
//                        return false;
//                    }
//                });
                normalHolder.mWho.setText(mData.get(position).getWho());
                normalHolder.mTitle.setText(mData.get(position).getDesc());
                normalHolder.mTime.setText(mData.get(position).getCreatedAt());
                if("android".equals(mData.get(position).getType().toLowerCase()))
                {
                    normalHolder.mIcon.setImageResource(R.mipmap.ic_android_black_24dp);
                }
                else if("ios".equals(mData.get(position).getType().toLowerCase()))
                {
                    normalHolder.mIcon.setImageResource(R.mipmap.ic_ios_black_24dp);
                }
                else if("瞎推荐".equals(mData.get(position).getType()))
                {
                    normalHolder.mIcon.setImageResource(R.mipmap.ic_mood_black_24dp);
                }
                else if("前端".equals(mData.get(position).getType()))
                {
                    normalHolder.mIcon.setImageResource(R.mipmap.ic_http_black_24dp);
                }
                else if("休息视频".equals(mData.get(position).getType()))
                {
                    normalHolder.mIcon.setImageResource(R.mipmap.ic_video_library_black_24dp);
                }
                else if("拓展资源".equals(mData.get(position).getType()))
                {
                    normalHolder.mIcon.setImageResource(R.mipmap.ic_more_vert_black_24dp);
                }
                else if("App".equals(mData.get(position).getType()))
                {
                    normalHolder.mIcon.setImageResource(R.mipmap.ic_apps_black_24dp);
                }else
                {
                    normalHolder.mIcon.setImageDrawable(null);
                }
            }
            if(holder instanceof ImageHolder)
            {
                ImageHolder imageHolder = (ImageHolder)holder;
                if(NetUtil.getNetworkState(HomeActivity.this) == NetUtil.WifiState &&
                        PreferenceManager.getDefaultSharedPreferences(HomeActivity.this).getBoolean(getString(R.string.auto_load_pic_at_wifi),true))
                {
                    mImageLoader.displayImage(mData.get(position).getUrl(), imageHolder.mImageView, mOptions);
                }
            }
            if(holder instanceof VideoHolder)
            {
                VideoHolder videoHolder = (VideoHolder)holder;
                videoHolder.itemView.setTag(videoHolder);
                videoHolder.mTitle.setText(mData.get(position).getDesc());
                videoHolder.mWebView.setWebViewClient(new WebViewClient() {
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        view.loadUrl(url); //在当前的webview中跳转到新的url
                        return true;
                    }
                });
                videoHolder.mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
                videoHolder.mWebView.getSettings().setSupportZoom(true);
                videoHolder.mWebView.getSettings().setJavaScriptEnabled(true);
                videoHolder.mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
                videoHolder.mWebView.getSettings().setDomStorageEnabled(true);
                videoHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        VideoHolder holder = (VideoHolder)v.getTag();
                        Log.d(TAG,  holder.mWebView.getUrl() + "-----------");
                        Intent intent = new Intent(HomeActivity.this, DetailActivity.class);
                        intent.putExtra("URL",mData.get(position).getUrl());
                        intent.putExtra("TITLE", mData.get(position).getDesc());
                        startActivity(intent);

                    }
                });
                videoHolder.mWebView.loadUrl(mData.get(position).getUrl().split(".htm")[0]);
            }
        }

        class NormalCardHolder extends RecyclerView.ViewHolder
        {
            ImageView mIcon;
            TextView mTitle;
            TextView mWho;
            TextView mTime;
            public NormalCardHolder(View itemView) {
                super(itemView);
                mIcon = (ImageView)itemView.findViewById(R.id.iv_icon);
                mTitle = (TextView)itemView.findViewById(R.id.tv_title);
                mWho = (TextView)itemView.findViewById(R.id.tv_who);
                mTime = (TextView)itemView.findViewById(R.id.tv_time);
            }
        }

        class ImageHolder extends RecyclerView.ViewHolder
        {
            ImageView mImageView;
            public ImageHolder(View itemView)
            {
                super(itemView);
                mImageView = (ImageView)itemView.findViewById(R.id.iv_gankEntity);
            }
        }

        class VideoHolder extends RecyclerView.ViewHolder
        {
            WebView mWebView;
            TextView mTitle;
            public VideoHolder(View itemView)
            {
                super(itemView);
                mWebView = (WebView)itemView.findViewById(R.id.wv_video);
                mTitle = (TextView)itemView.findViewById(R.id.tv_video_title);
            }
        }
    }

}
