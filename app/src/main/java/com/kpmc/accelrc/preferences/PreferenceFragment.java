package com.kpmc.accelrc.preferences;

import android.os.Bundle;

import com.kpmc.accelrc.R;

/**
 * Created by matthijs on 29/08/15.
 */
public class PreferenceFragment extends android.preference.PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }
}
