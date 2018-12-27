/*
 * Created by Muhammad Utsman on 27/12/2018
 * Copyright (c) 2018 . All rights reserved.
 * Last modified 12/27/18 8:44 AM
 */

package com.utsman.sampleintroapp;

import android.animation.ArgbEvaluator;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class IntroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        // setup fullscreen
        int windowFlag = WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS;
        getWindow().setFlags(windowFlag, windowFlag);

        final MyPagerAdapter pagerAdapter = new MyPagerAdapter(this);
        final ViewPager viewPager = findViewById(R.id.my_viewpager);
        final Button btnNext = findViewById(R.id.next_btn);
        final Button btnSkip = findViewById(R.id.skip_btn);

        viewPager.setAdapter(pagerAdapter);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewPager.getCurrentItem() == pagerAdapter.getCount()-1) {
                    introComplete();
                } else {
                    viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
                }
            }
        });

        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                introComplete();
            }
        });

        // setup changing viewpager
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
                int[] colors = {
                        getResources().getColor(R.color.blue),
                        getResources().getColor(R.color.green),
                        getResources().getColor(R.color.red),
                        getResources().getColor(R.color.yellow)
                };

                ArgbEvaluator argbEvaluator = new ArgbEvaluator();

                if (i < pagerAdapter.getCount() -1 && i < colors.length -1) {
                    viewPager.setBackgroundColor((int) argbEvaluator.evaluate(v, colors[i], colors[i+1]));
                } else {
                    viewPager.setBackgroundColor(colors[colors.length-1]);
                }
            }

            @Override
            public void onPageSelected(int i) {
                if (i == pagerAdapter.getCount() -1) {
                    btnNext.setText("Finish");
                    btnSkip.setVisibility(View.GONE);
                } else {
                    btnNext.setText("Next");
                    btnSkip.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    private void introComplete() {
        SharedPreferences preferences = getSharedPreferences("intro", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit().putBoolean("intro_complete", true);
        editor.apply();
        finish();
    }

    private class MyPagerAdapter extends PagerAdapter {
        private Context context;

        public MyPagerAdapter(Context context) {
            this.context = context;
        }

        private int[] imgIntro = {
                R.drawable.cat_1,
                R.drawable.cat_2,
                R.drawable.cat_3,
                R.drawable.cat_4
        };

        private String[] line1 = {
                "kucing 1",
                "kucing 2",
                "kucing 3",
                "kucing 4"
        };

        private String[] line2 = {
                "Ini kucing 1",
                "Ini kucing 2",
                "Ini kucing 3",
                "Ini kucing 4"
        };

        @Override
        public int getCount() {
            return imgIntro.length;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
            return view == o;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(R.layout.intro_layout, container, false);

            ImageView img = view.findViewById(R.id.img_intro);
            TextView tvLine1 = view.findViewById(R.id.line1);
            TextView tvLine2 = view.findViewById(R.id.line2);

            img.setImageResource(imgIntro[position]);
            tvLine1.setText(line1[position]);
            tvLine2.setText(line2[position]);

            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((LinearLayout)object);
        }
    }
}
