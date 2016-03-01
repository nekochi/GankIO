package com.nekomimi.gankio.activities;

import android.os.Bundle;

import com.nekomimi.gankio.R;

/**
 * Created by hongchi on 2016-3-1.
 * File description :
 */
public class HomeActivity extends BaseActivity
{
    private static final String TAG = "HomeActivity";


    private UiHandler mUiHandler = new UiHandler(this);

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }
}
