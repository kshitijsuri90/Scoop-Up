package com.example.android.scoopup;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

//custom loader class for fetching the news data
public class NewsLoader extends AsyncTaskLoader<List<News>> {
    private String url;

    //constructor initializing the request URL for the API call
    NewsLoader(Context context, String url){
        super(context);
        this.url = url;
    }

    /**
     * starts an asynchronous load of the loader's data
     */
    @Override
    protected void onStartLoading() {
        //forces an asynchronous load, ignoring any previously loaded data
        forceLoad();
    }


    /**
     * called on a worker thread to perform the loading of the data
     *
     * @return fetched data
     */

    @Override
    public List<News> loadInBackground() {
        if(url.length() < 1){
            return null;
        }
        return Utils.fetchNewsData(url);
    }


}
