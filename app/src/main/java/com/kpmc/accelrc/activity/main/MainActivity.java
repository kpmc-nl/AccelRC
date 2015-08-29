package com.kpmc.accelrc.activity.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.kpmc.accelrc.R;
import com.kpmc.accelrc.android.logging.LogHelper;
import com.kpmc.accelrc.android.logging.Logger;
import com.kpmc.accelrc.dagger.DaggerUtil;
import com.kpmc.accelrc.preferences.PreferenceActivity;

import butterknife.ButterKnife;

/**
 * Created by matthijs on 29/08/15.
 */
public class MainActivity extends AppCompatActivity {

    private final Logger log = LogHelper.getLogger(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        DaggerUtil.component(this).inject(this);
        log.d("onCreate");
    }


    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        log.d("Inflated menu.");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            log.d("Settings selected.");
            Intent prefIntent = new Intent(getApplicationContext(), PreferenceActivity.class);
            startActivity(prefIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
