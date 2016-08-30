package com.nekomimi.gankio.activities;

import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceCategory;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;
import android.preference.SwitchPreference;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.nekomimi.gankio.R;
import com.nekomimi.gankio.base.BaseActivity;

/**
 * Created by hongchi on 2016-4-20.
 * File description :
 */
public class SettingsActivity extends BaseActivity
{
    private static final String TAG = "SettingsActivity";
    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_settings);
        setupActionbar();
        getFragmentManager().beginTransaction().replace(R.id.content,new SettingFragment()).commit();
    }

    private void setupActionbar()
    {
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
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
        }

        if(!msg.equals("")) {
            Log.d(TAG, "onMenuItemClick: " + msg);
        }
        return super.onOptionsItemSelected(item);
    }

    public static class SettingFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener,Preference.OnPreferenceClickListener
    {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_settings);
            initAllPreference(getPreferenceScreen());
        }

        private void initAllPreference(Preference preference)
        {
            if(preference instanceof PreferenceScreen)
            {
                PreferenceScreen preferenceScreen = (PreferenceScreen)preference;
                for(int i = 0 ; i < preferenceScreen.getPreferenceCount() ; i++)
                {
                    initAllPreference(preferenceScreen.getPreference(i));
                }
            }else if (preference instanceof PreferenceCategory)
            {
                PreferenceCategory preferenceCategory = (PreferenceCategory)preference;
                for (int i = 0 ; i <preferenceCategory.getPreferenceCount() ; i++)
                {
                    initAllPreference(preferenceCategory.getPreference(i));
                }
            }else
            {
                initPreference(preference);
            }
        }

        private void initPreference(Preference preference)
        {
            if(getString(R.string.open_way).equals(preference.getKey()))
            {
                ListPreference openWay = (ListPreference)preference;
                openWay.setOnPreferenceChangeListener(this);
                openWay.setOnPreferenceClickListener(this);
            }
            else if(getString(R.string.auto_load_pic_at_wifi).equals(preference.getKey()))
            {
                SwitchPreference autoLoad = (SwitchPreference)preference;
                autoLoad.setOnPreferenceChangeListener(this);
            }
            else {
                preference.setOnPreferenceChangeListener(this);
                preference.setOnPreferenceClickListener(this);
            }
        }

        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
            Log.d(TAG, "onPreferenceChange: " + preference.getKey() + ";" + newValue);
            if(preference.getKey().equals(getString(R.string.open_way)))
            {
                ((ListPreference)preference).setValue((String) newValue);
            }
            else if(preference.getKey().equals(getString(R.string.auto_load_pic_at_wifi)))
            {
                Log.d(TAG, "onPreferenceChange: " + newValue);
            }else
            {
                Toast.makeText(getActivity(),getString(R.string.sb)+preference.getTitle(),Toast.LENGTH_LONG).show();
            }

            return true;
        }

        @Override
        public boolean onPreferenceClick(Preference preference) {
            if(preference.getKey() == null)
            {
                Toast.makeText(getActivity(),getString(R.string.sb)+preference.getTitle(),Toast.LENGTH_LONG).show();
            }
            return true;
        }
    }


}
