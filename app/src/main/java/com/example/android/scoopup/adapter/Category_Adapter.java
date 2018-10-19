package com.example.android.scoopup.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.scoopup.model.Category;
import com.example.android.scoopup.MainActivity;
import com.example.android.scoopup.R;

import java.util.List;

public class Category_Adapter extends RecyclerView.Adapter<Category_Adapter.MyViewHolder> {
    private List<Category> news;
    private LayoutInflater inflater;
    private Context context;

    public Category_Adapter(Context context, List<Category> news){
        inflater = LayoutInflater.from(context);
        this.news = news;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view =  inflater.inflate(R.layout.category_template,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        Category current_news = news.get(i);
        myViewHolder.setData(current_news,i);
        myViewHolder.parent.setOnClickListener(v -> {
            Intent intent = new Intent(context,MainActivity.class);
            intent.putExtra("section",myViewHolder.title.getText().toString().trim());
            intent.putExtra("activity","menu");
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return news.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView title;
        private ImageView image;
        private ConstraintLayout parent;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            this.title = itemView.findViewById(R.id.title);
            this.image = itemView.findViewById(R.id.imageView);
            this.parent = itemView.findViewById(R.id.menu_options);
        }

        @Override
        public void onClick(View v) {

        }

        public void setData(Category current_news, int i) {
            this.title.setText(current_news.getTitle());
            this.image.setImageResource(current_news.getImageId());

        }


    }
}
