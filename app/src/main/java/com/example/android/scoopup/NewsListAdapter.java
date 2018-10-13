package com.example.android.scoopup;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class NewsListAdapter extends ArrayAdapter<News> {
    NewsListAdapter(@NonNull Context context, int resource, @NonNull List<News> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        if(view==null){
            view = LayoutInflater.from(getContext()).inflate(R.layout.list_item_template,parent,false);
        }
        View listView =  view;
        News current_news = getItem(position);

        TextView newsTitleTextView =  listView.findViewById(R.id.title_text_view);
        assert current_news != null;
        String title = current_news.getmTitle();
        newsTitleTextView.setText(title);

        TextView newsCategorytextView =  listView.findViewById(R.id.category_text_view);
        String category = current_news.getmCategory();
        newsCategorytextView.setText(category);

        TextView newsDatetextView =  listView.findViewById(R.id.date_text_view);
        String date = current_news.getmDate();
        newsDatetextView.setText(date);

        TextView newsAuthortextView =  listView.findViewById(R.id.author_text_view);
        String author = current_news.getmAuthor();

        //Check Author if empty show this message
        if(author.equals("")){
            author = getContext().getString(R.string.noauthor);
        }

        newsAuthortextView.setText(author);
        return view;
    }
}
