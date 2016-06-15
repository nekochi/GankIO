package com.nekomimi.gankio.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.nekomimi.gankio.R;
import com.nekomimi.gankio.db.GankDdHelper;
import com.nekomimi.gankio.db.GankItem;

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

    private String mUrl;
    private String mCreateAt;
    private String mWho;
    private String mType;
    private String mPublishAt;
    private String mTitle;
    private String mId;
    private boolean mIsStared = false;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        mToolbar = (Toolbar)findViewById(R.id.toolbar_newsinfo);
        initWebView();

        mUrl = getIntent().getStringExtra(URL);
        loadurl(mUrl);
        mTitle = getIntent().getStringExtra(TITLE);
        mId = getIntent().getStringExtra(ID);
        mCreateAt = getIntent().getStringExtra(CREATEAT);
        mWho = getIntent().getStringExtra(WHO);
        mType = getIntent().getStringExtra(TYPE);
        mPublishAt = getIntent().getStringExtra(PUBLISHEDAT);
        Log.d("TITLE", mTitle);
        mToolbar.setTitle(mTitle);

//        mToolbar.inflateMenu(R.menu.detail_menu);
//        mToolbar.setOnMenuItemClickListener(onMenuItemClick);
        setSupportActionBar(mToolbar);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.detail_menu, menu);
        if(mId != null)
        {
            List result = GankDdHelper.getInstance(this).query(mId);
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
                    GankDdHelper.getInstance(this).delete(mId);
                    mIsStared = false;
                    mToolbar.getMenu().findItem(R.id.action_star).setIcon(R.mipmap.ic_star_border_white_24dp);
                }else {
                    GankItem gankItem = new GankItem(null,mId,mCreateAt,mTitle,mPublishAt,mType,mUrl,mWho,null);
                    GankDdHelper.getInstance(this).add(gankItem);
                    mIsStared = true;
                    mToolbar.getMenu().findItem(R.id.action_star).setIcon(R.mipmap.ic_star_white_24dp);
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
        mUrl = intent.getStringExtra("URL");
        loadurl(mUrl);
    }

    public void loadurl(String url)
    {
        if(mUrl!=null)
        {
            mWebView.loadUrl(mUrl);
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
