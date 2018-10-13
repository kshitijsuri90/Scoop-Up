package com.example.android.scoopup;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class RecyclerNewsAdapter extends RecyclerView.Adapter<RecyclerNewsAdapter.MyViewHolder> {

    private List<News> news;
    private LayoutInflater inflater;

    public RecyclerNewsAdapter(Context context, List<News> news){
        inflater = LayoutInflater.from(context);
        this.news = news;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view =  inflater.inflate(R.layout.list_item_template,parent,false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public int getItemCount() {
        return news.size();
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        News current_news = news.get(position);
        holder.setData(current_news,position);
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView title;
        private ImageView image;
        private TextView author;
        private TextView category;
        private TextView date;
        private int position;
        private News current_news;
        private Context context;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            this.title = itemView.findViewById(R.id.title);
            this.author = itemView.findViewById(R.id.author);
            this.image = itemView.findViewById(R.id.imageView);
            this.category = itemView.findViewById(R.id.section);
            this.date = itemView.findViewById(R.id.publishDate);
        }

        public void setData(News current, int position){
            this.title.setText(current.getmTitle());
            this.date.setText(current.getmDate());
            this.author.setText(current.getmAuthor());
            this.category.setText(current.getmCategory());
            Glide.with(context).load(current.getThumbnailUrl()).into(image);
            this.current_news = current;
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            String url = news.get(position).getmUrl();
            Intent browser_intent = new Intent(Intent.ACTION_VIEW);
            browser_intent.setData(Uri.parse(url));
            context.startActivity(browser_intent);
        }
    }
}
