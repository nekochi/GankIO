package com.nekomimi.gankio.view;

import android.content.Context;
import android.preference.ListPreference;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.nekomimi.gankio.R;

/**
 * Created by hongchi on 2016-4-21.
 * File description :
 */
public class MListPreference extends ListPreference
{
    private static final String TAG = "MListPreference";

    private String mTitle;
    private String mValue;

    private TextView mValueTv;

    public MListPreference(Context context, AttributeSet attrs) {
        super(context, attrs);

        Log.d(TAG, "MListPreference: " + this.getDependency());
    }



    @Override
      protected void onBindView(View view) {
          super.onBindView(view);
        mValue = getValue();
        mValueTv = (TextView)view.findViewById(R.id.value);
        Log.d(TAG, "onBindView: " + mValue);
        mValueTv.setText(mValue);
      }

}
