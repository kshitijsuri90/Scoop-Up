package com.example.android.scoopup;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.content.Loader;
import android.app.LoaderManager.LoaderCallbacks;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderCallbacks<List<News>>{

    private static final String TAG = "MainActivity";
    private static final String URL = "https://content.guardianapis.com/search?api-key=8fd73672-a55a-409b-8192-9ebad467ec7c";
    private static final int LOADER_ID = 1;
    private TextView emptyTextView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        emptyTextView = findViewById(R.id.fail_text);
        progressBar = findViewById(R.id.loading_spinner);
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            // Get a reference to the LoaderManager, in order to interact with loaders.
            LoaderManager loaderManager = getLoaderManager();
            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            loaderManager.initLoader(LOADER_ID, null, this);
        }
        else {
            // Otherwise, display error
            // First, hide loading indicator so error message will be visible

            progressBar.setVisibility(View.GONE);
            // Update empty state with no connection error message

            emptyTextView.setText(R.string.no_internet);
        }
    }


    @NonNull
    @Override
    public android.content.Loader<List<News>> onCreateLoader(int i, @Nullable Bundle bundle) {
        return new NewsLoader(this,URL);
    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> news) {
        Log.d(TAG, "onLoadFinished: executes load finished");
        updateUi((ArrayList<News>) news);
        emptyTextView.setText(R.string.no_search_results);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {
        updateUi(new ArrayList<News>());
    }

    private void updateUi(final ArrayList<News> news){
        // Find a reference to the {@link ListView} in the layout
        ListView listView = (ListView) findViewById(R.id.list);
        assert listView != null;
        listView.setEmptyView(emptyTextView);

        // Create a new {@link ArrayAdapter} of earthquakes
        NewsListAdapter adapter = new NewsListAdapter(this,android.R.layout.simple_list_item_1,news);

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String url = news.get(position).getUrl();
                Intent browser_intent = new Intent(Intent.ACTION_VIEW);
                browser_intent.setData(Uri.parse(url));
                startActivity(browser_intent);
            }
        });
    }
}
