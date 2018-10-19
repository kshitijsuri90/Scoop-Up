package com.example.android.scoopup;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.android.scoopup.adapter.MyPagerAdapter;

public class ViewPagerActivity extends AppCompatActivity {
    private ViewPager viewpager;
    private LinearLayout layout_dot;
    private Button btn_next;
    private Button btn_skip;
    private TextView[] dotstv;
    private int[] layouts;

    private MyPagerAdapter pagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(!isFirstTimeStartApp()){
            startMainActivity();
            finish();
        }

        setContentView(R.layout.activity_view_pager);

        viewpager = findViewById(R.id.view_pager);
        layout_dot = findViewById(R.id.dot_layout);
        btn_next = findViewById(R.id.next);
        btn_skip = findViewById(R.id.skip);

        btn_skip.setOnClickListener(v -> startMainActivity());

        btn_next.setOnClickListener(v -> {
            int currentpage = viewpager.getCurrentItem()+1;
            if(currentpage<layouts.length){
                viewpager.setCurrentItem(currentpage);
            }
            else {
                startMainActivity();
                finish();
            }
        });

        layouts = new int[] {R.layout.slider_1,R.layout.slider_2,R.layout.slider_3};
        pagerAdapter = new MyPagerAdapter(layouts,getApplicationContext());
        viewpager.setAdapter(pagerAdapter);
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position==layouts.length-1){
                    btn_next.setText(R.string.s);
                    btn_skip.setVisibility(View.GONE);
                }
                else {
                    btn_next.setText(R.string.next);
                    btn_skip.setVisibility(View.VISIBLE);
                }
                setDotStatus(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        setDotStatus(0);
    }

    private boolean isFirstTimeStartApp(){
        SharedPreferences ref = getApplicationContext().getSharedPreferences("IntroSlides", Context.MODE_PRIVATE);
        return ref.getBoolean("FirstTime",true);
    }

    private void setFirstTimeStatus(boolean status){
        SharedPreferences ref = getApplicationContext().getSharedPreferences("IntroSlides",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = ref.edit();
        editor.putBoolean("FirstTime",status);
        editor.apply();
    }

    private void setDotStatus(int page){
        layout_dot.removeAllViews();
        dotstv = new TextView[layouts.length];
        for (int i = 0;i<dotstv.length;i++){
            dotstv[i] = new TextView(this);
            dotstv[i].setText(Html.fromHtml("&#8226;"));
            dotstv[i].setTextSize(30);
            dotstv[i].setTextColor(Color.parseColor("#a9b4bb"));
            layout_dot.addView(dotstv[i]);
        }
        if(dotstv.length>0){
            dotstv[page].setTextColor(Color.parseColor("#ffffff"));
        }
    }

    private void startMainActivity(){
        setFirstTimeStatus(false);
        startActivity(new Intent(this, MenuActivity.class));
        finish();
    }
}
