/*
 * Created by Muhammad Utsman on 27/12/2018
 * Copyright (c) 2018 . All rights reserved.
 * Last modified 12/27/18 8:41 AM
 */

package com.utsman.sampleintroapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences preferences = getSharedPreferences("intro", Context.MODE_PRIVATE);
        Boolean introComplete = preferences.getBoolean("intro_complete", false);

        if (!introComplete) {
            startActivity(new Intent(this, IntroActivity.class));
        }
    }
}
