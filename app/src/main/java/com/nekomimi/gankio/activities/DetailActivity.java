package com.nekomimi.gankio.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.nekomimi.gankio.R;
import com.nekomimi.gankio.base.BaseActivity;
import com.nekomimi.gankio.bean.GankEntity;
import com.nekomimi.gankio.db.GankDdHelper;
import com.nekomimi.gankio.utils.PreferenceUtil;
import com.nekomimi.gankio.utils.RemindUtil;

import java.util.List;

/**
 * Created by hongchi on 2016-3-16.
 * File description :
 */
public class DetailActivity extends BaseActivity
{
    private static final String TAG = "DetailActivity";

    public static final String TITLE = "TITLE";
    public static final String ID = "ID";
    public static final String URL = "URL";
    public static final String CREATEAT = "CREATEAT";
    public static final String USED = "USED";
    public static final String WHO = "WHO";
    public static final String TYPE = "TYPE";
    public static final String PUBLISHEDAT = "PUBLISHEDAT";

    private WebView mWebView;
    private Toolbar mToolbar;
    private TextView mTitleTxt;
    private GankEntity mData;
    private boolean mIsStared = false;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        mTitleTxt = (TextView)findViewById(R.id.toolbar_title);
        mToolbar = (Toolbar)findViewById(R.id.toolbar_newsinfo);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        initWebView();
        mData = getIntent().getParcelableExtra("data");
        loadurl(mData.getUrl());
        updateTitle(mData.getDesc());
    }

    public void updateTitle(String title){
        mTitleTxt.setText(title);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.detail_menu, menu);
        if(mData.get_id() != null)
        {
            List result = GankDdHelper.getInstance(this).query(mData.get_id());
            if(result != null && result.size() != 0)
            {
                menu.findItem(R.id.action_star).setIcon(R.mipmap.ic_star_white_24dp);
                mIsStared = true;
            }
        }
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        String msg = "";
        switch (item.getItemId())
        {
            case android.R.id.home:
                finish();
               break;
            case R.id.action_share:
                msg += "Click share";
                break;
            case R.id.action_star:
                if(mIsStared)
                {
                    GankDdHelper.getInstance(this).delete(mData.get_id());
                    mIsStared = false;
                    mToolbar.getMenu().findItem(R.id.action_star).setIcon(R.mipmap.ic_star_border_white_24dp);
                }else {
                    GankDdHelper.getInstance(this).add(mData);
                    mIsStared = true;
                    mToolbar.getMenu().findItem(R.id.action_star).setIcon(R.mipmap.ic_star_white_24dp);
                    if (PreferenceUtil.getShouldShowTip(DetailActivity.this)){
                        Log.d(TAG, "onOptionsItemSelected: show bar" );
                        RemindUtil.snackBar(mToolbar, "收藏功能目前仅支持本地存储，而且清理数据还会消失哦=。=", "不再提醒", Snackbar.LENGTH_LONG,
                                new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        PreferenceUtil.setShouldshowtip(DetailActivity.this, false);
                                    }
                                });
                    }
                }
                break;
        }

        if(!msg.equals("")) {
            Log.d(TAG, "onMenuItemClick: " + msg);
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onNewIntent(Intent intent)
    {
        loadurl(mData.getUrl());
    }

    public void loadurl(String url)
    {
        if(mData.getUrl()!=null)
        {
            mWebView.loadUrl(mData.getUrl());
        }
    }

    public void initWebView()
    {
        mWebView = (WebView)findViewById(R.id.wv_news);
        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url); //在当前的webview中跳转到新的url
                return true;
            }
        });
        mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        mWebView.getSettings().setSupportZoom(true);
        mWebView.getSettings().setBuiltInZoomControls(true);
        mWebView.getSettings().setJavaScriptEnabled(true);
    }


    @Override
    public void onBackPressed()
    {
        if(mWebView.canGoBack())
        {
            mWebView.goBack();
        }else {
            super.onBackPressed();
        }
    }
}
