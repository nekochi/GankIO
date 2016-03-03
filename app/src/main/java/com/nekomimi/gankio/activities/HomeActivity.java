package com.nekomimi.gankio.activities;

import android.content.Context;
import android.os.Bundle;
import android.os.Message;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nekomimi.gankio.R;
import com.nekomimi.gankio.base.AppAction;
import com.nekomimi.gankio.bean.GankDate;
import com.nekomimi.gankio.bean.GankEntity;

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

    private boolean mFlag = false;
    private int mPage = 0;
    private boolean mIfQuit = false;
    private List<GankEntity> mData;
    private Calendar mCalendar;
    private Context mContext;
    private UiHandler mUiHandler = new UiHandler(this);

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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawerLayout);
        mActionBarDrawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout,mToolbar,R.string.open,R.string.close)
        {
            @Override
            public void onDrawerClosed(View drawerView)
            {
                super.onDrawerClosed(drawerView);
                mAppBarLayout.setExpanded(false);
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
        NavigationView mNavTab = (NavigationView)findViewById(R.id.nav_view);
        mNavTab.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                Toast.makeText(getContext(), menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                switch (menuItem.getItemId()) {

                    default:
                        break;
                }
                return false;
            }
        });
        mFaceIv = (CircleImageView)findViewById(R.id.iv_face);
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

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItem + 1 == recyclerView.getAdapter().getItemCount()) {
                    mSwipeRefreshLayout.setRefreshing(true);
                    mPage++;
                    mCalendar.add(Calendar.DAY_OF_MONTH, -1);
                    AppAction.getInstance().day(mCalendar, mUiHandler);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition();
            }
        });
        mAppBarLayout = (AppBarLayout)findViewById(R.id.tabanim_appbar);
    }

    private void initData()
    {
        mContext = this;
        mData = new ArrayList<>();
        mCalendar = Calendar.getInstance();
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
            case R.id.drawer_setting:
                AppAction.getInstance().day(mCalendar, mUiHandler);
                break;
            default:
                break;
        }
    }

    @Override
    public void onRefresh() {
        mData.clear();
        mPage = 0;
        mCalendar = Calendar.getInstance();
        AppAction.getInstance().day(mCalendar, mUiHandler);
    }

    @Override
    public void handleMessage(Message message)
    {
        Log.d(TAG,"handleMessage");
        if(message.what == 0)
        {
            mData.addAll(mData.size(), ((GankDate) message.obj).getResults().getAll());
            mAdapter.notifyDataSetChanged();
        }
        mSwipeRefreshLayout.setRefreshing(false);
    }

    class RAdapter extends RecyclerView.Adapter<RAdapter.CardViewHolder>
    {
        @Override
        public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new CardViewHolder( LayoutInflater.from(mContext).inflate(R.layout.view_itemcard,parent,false));
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }

        @Override
        public void onBindViewHolder(CardViewHolder holder, int position)
        {
            if(holder != null)
            {
                holder.itemView.setTag(holder);
                holder.mWho.setText(mData.get(position).getWho());
                holder.mTitle.setText(mData.get(position).getDesc());
                holder.mTime.setText(mData.get(position).getPublishedAt());
            }
        }

        class CardViewHolder extends RecyclerView.ViewHolder
        {
            ImageView mIcon;
            TextView mTitle;
            TextView mWho;
            TextView mTime;
            public CardViewHolder(View itemView) {
                super(itemView);
                mIcon = (ImageView)itemView.findViewById(R.id.iv_icon);
                mTitle = (TextView)itemView.findViewById(R.id.tv_title);
                mWho = (TextView)itemView.findViewById(R.id.tv_who);
                mTime = (TextView)itemView.findViewById(R.id.tv_time);
            }
        }
    }


}
