package com.example.android.scoopup;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.ProxyInfo;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.content.Loader;
import android.app.LoaderManager.LoaderCallbacks;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderCallbacks<List<News>>{

    private static final String TAG = "MainActivity";
    private static final String API_KEY = "&api-key=8fd73672-a55a-409b-8192-9ebad467ec7c";
    private static final String URL = "http://content.guardianapis.com/search?&show-tags=all";
    private static final String QUERY_FROM_DATE = "&from-date=";
    private static final String QUERY_ORDER_BY = "&order-by=";
    private static final String QUERY_FIELDS = "&q" +
            "=";
    private static final String QUERY_PAGES = "&page-size=";
    private static final int LOADER_ID = 1;
    private static String strDate ="";
    private TextView emptyTextView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        emptyTextView = findViewById(R.id.fail_text);
        progressBar = findViewById(R.id.loading_spinner);
        Date current_time  = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        //to convert Date to String, use format method of SimpleDateFormat class.
        strDate = dateFormat.format(current_time);
        strDate = strDate.substring(0,10);

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
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);

        // getString retrieves a String value from the preferences. The second parameter is the default value for this preference.
        String category = sharedPrefs.getString(
                getString(R.string.settings_category_key),
                getString(R.string.settings_category_default));

        // parse breaks apart the URI string that's passed into its parameter
        Uri baseUri = Uri.parse(URL);

        // buildUpon prepares the baseUri that we just parsed so we can add query parameters to it
        Uri.Builder uriBuilder = baseUri.buildUpon();

        // Append query parameter and its value. For example, the `format=geojson`
        uriBuilder.appendQueryParameter(QUERY_FROM_DATE, strDate);
        uriBuilder.appendQueryParameter(QUERY_ORDER_BY, "newest");
        uriBuilder.appendQueryParameter(QUERY_FIELDS, category);
        uriBuilder.appendQueryParameter(QUERY_PAGES, "200");

        // Return the completed uri `http://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&limit=10&minmag=minMagnitude&orderby=time
        return new NewsLoader(this, uriBuilder.toString()+ API_KEY);

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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
