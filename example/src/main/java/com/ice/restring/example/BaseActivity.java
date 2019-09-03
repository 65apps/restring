package com.ice.restring.example;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import com.ice.restring.Restring;

/**
 * We should wrap the base context of our activities, which is better to put it
 * on BaseActivity, so that we don't have to repeat it for all activities one-by-one.
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(Restring.wrapContext(newBase));
    }
}
