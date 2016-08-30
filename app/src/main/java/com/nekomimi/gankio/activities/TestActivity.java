package com.nekomimi.gankio.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.nekomimi.gankio.base.BaseActivity;

/**
 * Created by hongchi on 2016-1-27.
 * File description :
 */
public class TestActivity extends BaseActivity
{
    String url = "";
    String filename = "";
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        Button b = new Button(this);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

}
