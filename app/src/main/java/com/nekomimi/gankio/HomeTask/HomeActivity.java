package com.nekomimi.gankio.HomeTask;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
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
import com.nekomimi.gankio.activities.DetailActivity;
import com.nekomimi.gankio.activities.SettingsActivity;
import com.nekomimi.gankio.base.BaseActivity;
import com.nekomimi.gankio.bean.GankEntity;
import com.nekomimi.gankio.utils.AnimationUtil;
import com.nekomimi.gankio.utils.NetUtil;
import com.squareup.picasso.Picasso;
import com.umeng.message.PushAgent;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by hongchi on 2016-3-1.
 * File description :
 */
public class HomeActivity extends BaseActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener, HomeContract.View
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
    private int mTag = 0;


    private HomeContract.Presenter mPresenter;
    @Override
    public void updata(List<GankEntity> dataList) {
        if(mData == null || mAdapter == null){
            return;
        }
        mData.clear();
        mData.addAll(dataList);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void loadmore(List<GankEntity> dataList) {
        mData.addAll(mData.size(), dataList);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void changeDate(int day) {
        mCalendar.add(Calendar.DAY_OF_MONTH, day);
    }

    @Override
    public void changePage(int num) {
        mPage = mPage + num;
    }

    @Override
    public void setFresh(boolean flag) {
        mSwipeRefreshLayout.setRefreshing(flag);
    }

    @Override
    public void setPresenter(HomeContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    enum State{
        All,Android,Ios,App,休息视频,拓展资源,瞎推荐,前端,福利,收藏
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        PushAgent.getInstance(this).onAppStart();
        setContentView(R.layout.activity_home);
        new HomePresenter(this);
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
                        mPresenter.day(mCalendar);
                        break;
                    case R.id.nav_menu_android:
                        mState = State.Android;
                        mPresenter.data(State.Android, mPageNum , mPage );
                        break;
                    case R.id.nav_menu_ios:
                        mState = State.Ios;
                        mPresenter.data(State.Ios, mPageNum , mPage );
                        break;
                    case R.id.nav_menu_app:
                        mState = State.App;
                        mPresenter.data(State.App, mPageNum, mPage);
                        break;
                    case R.id.nav_menu_休息视频:
                        mState = State.休息视频;
                        mPresenter.data(State.休息视频, mPageNum , mPage );
                        break;
                    case R.id.nav_menu_拓展资源:
                        mState = State.拓展资源;
                        mPresenter.data(State.拓展资源, mPageNum , mPage );
                        break;
                    case R.id.nav_menu_瞎推荐:
                        mState = State.瞎推荐;
                        mPresenter.data(State.瞎推荐, mPageNum , mPage );
                        break;
                    case R.id.nav_menu_前端:
                        mState = State.前端;
                        mPresenter.data(State.前端, mPageNum , mPage );
                        break;
                    case R.id.nav_menu_star:
                        mState = State.收藏;
                        mPresenter.star(HomeActivity.this, mPageNum, mPage);
                        break;
//                    case R.id.nav_menu_福利:
//                        mState = State.福利;
//                        AppGetAction.getInstance().data(mUiHandler,"福利",mPageNum+"",mPage+"");
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
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        mAdapter = new RAdapter();
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            int []lastVisibleItem;
            private static final float HIDE_THRESHOLD = 100;
            private static final float SHOW_THRESHOLD = 50;
            int scrollDist = 0;
            private boolean isVisible = true;
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = ((StaggeredGridLayoutManager) recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPositions(null);
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
//                if (newState == RecyclerView.SCROLL_STATE_IDLE
//                        && lastVisibleItem + 1 == recyclerView.getAdapter().getItemCount()) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    for (int i : lastVisibleItem){
                        if(i+1>=recyclerView.getAdapter().getItemCount()){
                            loadmore();
                        }
                    }
                }
                if(newState == RecyclerView.SCROLL_STATE_DRAGGING){
                    Picasso.with(mContext).pauseTag(mTag);
                }else {
                    Picasso.with(mContext).resumeTag(mTag);
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
        mTestBt.setTag("add");
        dataReset();
        mPresenter.day(mCalendar);
    }

    private void initData()
    {
        mContext = this;
        mData = new ArrayList<>();
        mCalendar = Calendar.getInstance();
        mState = State.All;

    }

    private void loadmore()
    {
        mSwipeRefreshLayout.setRefreshing(true);
        switch (mState)
        {
            case All:
                mPresenter.day(mCalendar);
                break;
            case Android:
            case Ios:
            case App:
            case 休息视频:
            case 拓展资源:
            case 瞎推荐:
            case 福利:
                mPresenter.data(mState, mPageNum , mPage );
            case 收藏:
                mPresenter.star(HomeActivity.this, mPageNum, mPage);
                break;
            default:
                break;
        }


    }
    @Override
    public void dataReset()
    {
        mData.clear();
        mAdapter.notifyDataSetChanged();
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
                if(mTestBt.getTag().equals("up")){
                    AnimationUtil.rotationYView(mTestBt, "+", "add");
                }else {
                    AnimationUtil.rotationYView(mTestBt, "∧", "up");
                }
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
        mPresenter.day(mCalendar);
    }

    @Override
    public void handleMessage(Message message)
    {

    }

    private void rotation(){

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
                return new NormalCardHolder( LayoutInflater.from(mContext).inflate(R.layout.view_staggridcard,parent,false));
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
                            Bundle bundle = new Bundle();
                            bundle.putParcelable("data", mData.get(position));
                            intent.putExtras(bundle);
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
                if (mData.get(position).getImages()!=null&&mData.get(position).getImages().size()>0){
                    StringBuilder picUrl = new StringBuilder(mData.get(position).getImages().get(0)).append("?imageView2/0/w/250");
                    Picasso.with(mContext).load(picUrl.toString()).tag(mTag).placeholder(R.mipmap.ic_launcher).into(normalHolder.mPic);
                }else {
                    normalHolder.mPic.setImageDrawable(null);
                }
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
                if(PreferenceManager.getDefaultSharedPreferences(HomeActivity.this).getBoolean(getString(R.string.auto_load_pic_at_wifi),true))
                {
                    if (NetUtil.getNetworkState(HomeActivity.this) == NetUtil.WifiState){
                        Picasso.with(mContext).load(mData.get(position).getUrl()).tag(mTag).into(imageHolder.mImageView);
                    }else {
                        holder.itemView.setVisibility(View.GONE);
                    }
                }else {
                    Picasso.with(mContext).load(mData.get(position).getUrl()).tag(mTag).into(imageHolder.mImageView);
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
                        VideoHolder holder = (VideoHolder) v.getTag();
                        Log.d(TAG, holder.mWebView.getUrl() + "-----------");
                        Intent intent = new Intent(HomeActivity.this, DetailActivity.class);
                        intent.putExtra(DetailActivity.URL,mData.get(position).getUrl());
                        intent.putExtra(DetailActivity.ID, mData.get(position).get_id());
                        intent.putExtra(DetailActivity.TITLE, mData.get(position).getDesc());
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
            ImageView mPic;
            public NormalCardHolder(View itemView) {
                super(itemView);
                mIcon = (ImageView)itemView.findViewById(R.id.iv_icon);
                mTitle = (TextView)itemView.findViewById(R.id.tv_title);
                mWho = (TextView)itemView.findViewById(R.id.tv_who);
                mTime = (TextView)itemView.findViewById(R.id.tv_time);
                mPic = (ImageView)itemView.findViewById(R.id.iv_pic);
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
