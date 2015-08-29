package com.kpmc.accelrc.preferences;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.kpmc.accelrc.android.logging.LogHelper;
import com.kpmc.accelrc.android.logging.Logger;
import com.kpmc.accelrc.dagger.DaggerUtil;

import butterknife.ButterKnife;

/**
 * Created by matthijs on 29/08/15.
 */
public class PreferenceActivity extends AppCompatActivity {

    private final Logger log = LogHelper.getLogger(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        log.d("onCreate");
        ButterKnife.bind(this);
        DaggerUtil.component(this).inject(this);

        getFragmentManager()
                .beginTransaction()
                .replace(android.R.id.content, new PreferenceFragment())
                .commit();

    }



}
