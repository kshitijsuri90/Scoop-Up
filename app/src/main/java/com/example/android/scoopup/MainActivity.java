package com.example.android.scoopup;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.content.Loader;
import android.app.LoaderManager.LoaderCallbacks;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderCallbacks<List<News>> {

    private static final String QUERY_ORDER_BY = "order-by";
    private static final String QUERY_FIELDS = "q";
    public static final String KEY_SHOW_FIELD = "show-fields";
    public static final String KEY_ALL = "all";
    private static final String QUERY_PAGES = "page-size";
    private static String SECTION_QUERY = "def";
    private static final String SECTION_NAME = "sectionName";
    private static final String SECTION_ID = "sectionId";
    private static final int LOADER_ID = 1;
    private TextView emptyTextView;
    private ProgressBar progressBar;
    private String title = "The Scoop";
    private Boolean state = Constant.STATE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (!state) {
            setTheme(R.style.NewsCategory);
        } else {
            setTheme(R.style.AppThemeNight);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("The Scoop");
        Toolbar toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        setTitle("");
        ImageView toolbar_bg = findViewById(R.id.toolbar_bg);
        if (getIntent().getStringExtra("section") != null) {
            SECTION_QUERY = getIntent().getStringExtra("section").trim();
            title = SECTION_QUERY;
            SECTION_QUERY = SECTION_QUERY.toLowerCase();
            switch (SECTION_QUERY) {
                case "highlights":
                    toolbar_bg.setImageResource(R.drawable.blue_toolbar);
                    title = "Highlights";
                    break;
                case "politics":
                    toolbar_bg.setImageResource(R.drawable.pink_news);
                    title = "Politics";
                    break;
                case "business":
                    toolbar_bg.setImageResource(R.drawable.yello_toolbar);
                    title = "Business";
                    break;
                default:
                    toolbar_bg.setImageResource(R.drawable.travel_toolbar);
                    title = "Travel";
                    break;
            }
        }
        toolbar_bg.setScaleType(ImageView.ScaleType.FIT_XY);
        emptyTextView = findViewById(R.id.fail_text);
        progressBar = findViewById(R.id.loading_spinner);
        TextView title1 = findViewById(R.id.category_title);
        title1.setText(title);
        ImageButton back = findViewById(R.id.back_pressed);
        back.setOnClickListener(v -> onBackPressed());


        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())

        {
            // Get a reference to the LoaderManager, in order to interact with loaders.
            LoaderManager loaderManager = getLoaderManager();
            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            loaderManager.initLoader(LOADER_ID, null, this);
        } else

        {
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
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        // getString retrieves a String value from the preferences. The second parameter is the default value for this preference.
        String category = sharedPreferences.getString(
                getString(R.string.settings_category_key),
                getString(R.string.settings_category_default));

        // parse breaks apart the URI string that's passed into its parameter
        String REQUEST_URL = "http://content.guardianapis.com/search?";
        Uri baseUri = Uri.parse(REQUEST_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();

        uriBuilder.appendQueryParameter("api-key", "test");
        uriBuilder.appendQueryParameter(KEY_SHOW_FIELD, KEY_ALL);
        uriBuilder.appendQueryParameter("show-tags", "contributor");
        uriBuilder.appendQueryParameter(QUERY_PAGES, "20");

        assert category != null;
        if (!category.equals(getString(R.string.settings_category_default)) || SECTION_QUERY.equals("highlights")) {
            uriBuilder.appendQueryParameter(QUERY_FIELDS, category);
            uriBuilder.appendQueryParameter(QUERY_ORDER_BY, getString(R.string.newest));
        } else {

            if (SECTION_QUERY.equals("def")) {
                uriBuilder.appendQueryParameter(SECTION_NAME, category);
                uriBuilder.appendQueryParameter(SECTION_ID, category);
                uriBuilder.appendQueryParameter(QUERY_FIELDS, category);
                uriBuilder.appendQueryParameter(QUERY_ORDER_BY, getString(R.string.relevance));

            } else {
                uriBuilder.appendQueryParameter(SECTION_NAME, SECTION_QUERY);
                uriBuilder.appendQueryParameter(SECTION_ID, SECTION_QUERY);
                uriBuilder.appendQueryParameter(QUERY_FIELDS, SECTION_QUERY);
                uriBuilder.appendQueryParameter(QUERY_ORDER_BY, getString(R.string.relevance));
            }

        }


        return new NewsLoader(this, uriBuilder.toString());

    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> data) {
        updateUi((ArrayList<News>) data);
        emptyTextView.setText(R.string.no_search_results);
        progressBar.setVisibility(View.GONE);

    }


    @Override
    public void onLoaderReset(Loader<List<News>> loader) {
        updateUi(new ArrayList<>());
    }

    private void updateUi(final ArrayList<News> news) {
        // Find a reference to the {@link ListView} in the layout
        RecyclerView recyclerView = findViewById(R.id.list);
        if (news.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            emptyTextView.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            emptyTextView.setVisibility(View.GONE);
        }
        RecyclerNewsAdapter adapter = new RecyclerNewsAdapter(this, news);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        final LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(this, R.anim.layout_animation_right);
        recyclerView.setLayoutAnimation(controller);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        updateMenuTitle(menu);
        return true;
    }

    private void updateMenuTitle(android.view.Menu menu) {
        MenuItem item = menu.getItem(1);
        if (state) {
            item.setTitle(getString(R.string.day_mode_title));
        } else {
            item.setTitle(getString(R.string.night_mode_title));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            settingsIntent.putExtra("state", state.toString());
            startActivity(settingsIntent);
            return true;
        } else if (id == R.id.action_night_mode) {
            if (!state) {
                Constant.STATE = true;
                item.setTitle(getString(R.string.night_mode_title));
                this.recreate();
            } else {
                Constant.STATE = false;
                item.setTitle(getString(R.string.day_mode_title));
                this.recreate();
            }
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
