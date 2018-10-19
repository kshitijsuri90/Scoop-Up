package com.example.android.scoopup;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import com.example.android.scoopup.adapter.Category_Adapter;
import com.example.android.scoopup.model.Category;

import java.util.ArrayList;
import java.util.List;

public class MenuActivity extends AppCompatActivity {

    private List<Category> news = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        addData(news);
        // Find a reference to the {@link ListView} in the layout
        RecyclerView recyclerView = findViewById(R.id.recycler_list);
        Category_Adapter adapter = new Category_Adapter(this, news);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        final LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(this, R.anim.layout_animation_right);
        recyclerView.setLayoutAnimation(controller);
        recyclerView.setAdapter(adapter);



    }
    private void addData(List<Category> news) {
        news.add(new Category(getString(R.string.category1),R.drawable.blue_news));
        news.add(new Category(getString(R.string.category2),R.drawable.pink_news));
        news.add(new Category(getString(R.string.category3),R.drawable.yellowjpg));
        news.add(new Category(getString(R.string.category4),R.drawable.light_yellow_news));
    }

}
