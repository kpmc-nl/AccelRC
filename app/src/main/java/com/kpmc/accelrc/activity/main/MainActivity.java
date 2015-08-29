package com.kpmc.accelrc.activity.main;

import android.app.Activity;
import android.os.Bundle;

import com.kpmc.accelrc.R;
import com.kpmc.accelrc.dagger.DaggerUtil;

import butterknife.ButterKnife;

/**
 * Created by matthijs on 29/08/15.
 */
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        DaggerUtil.component(this).inject(this);
    }


    @Override
    protected void onResume() {
        super.onResume();
    }


}
