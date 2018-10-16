package com.example.android.scoopup;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class NewsListAdapter extends ArrayAdapter<News> {

    private Context context;
    NewsListAdapter(@NonNull Context context, int resource, @NonNull List<News> objects) {
        super(context, resource, objects);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        if(view==null){
            view = LayoutInflater.from(getContext()).inflate(R.layout.news_item_template,parent,false);
        }
        View listView =  view;
        News current_news = getItem(position);
        ImageView image = listView.findViewById(R.id.imageView);
        assert current_news != null;
        String thumbnail= current_news.getThumbnailUrl();
        if(thumbnail==null){
            image.setImageResource(R.drawable.ic_launcher_background);
        }
        else {
            Glide.with(context).load(thumbnail).into(image);
        }

        TextView newsTitleTextView =  listView.findViewById(R.id.title);
        String title = current_news.getmTitle();
        newsTitleTextView.setText(title);

        TextView newsCategorytextView =  listView.findViewById(R.id.section);
        String category = current_news.getmCategory();
        newsCategorytextView.setText(category);

        TextView newsDatetextView =  listView.findViewById(R.id.publishDate);
        String date = Utils.formatDate(current_news.getmDate());
        newsDatetextView.setText(date);

        TextView newsAuthortextView =  listView.findViewById(R.id.author);
        String author = current_news.getmAuthor();

        //Check Author if empty show this message
        if(author.equals("")){
            author = getContext().getString(R.string.noauthor);
        }

        newsAuthortextView.setText(author);
        return view;
    }
}
