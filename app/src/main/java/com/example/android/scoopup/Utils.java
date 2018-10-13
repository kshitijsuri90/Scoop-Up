package com.example.android.scoopup;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class Utils {

    private static final String TAG = "Utils";

    private Utils(){

    }

    public static ArrayList<News> fetchNewsData(String url_text){
        URL url = createUrl(url_text);
        String json = null;
        try {
            json = makeHttpRequest(url);

        } catch (IOException e) {
            e.printStackTrace();
        }

        ArrayList<News> news = (ArrayList<News>) extractNews(json);
        return news;

    }

    private static URL createUrl(String url_text){
        URL url = null;
        try {
            url = new URL(url_text);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    public static String makeHttpRequest(URL url) throws IOException{
        String jsonresponse = "";
        if(url==null){
            return jsonresponse;
        }
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonresponse = readFromStream(inputStream);
            } else {
                Log.e(TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        }
        catch (IOException e ){
            Log.e(TAG, "Problem retrieving the News JSON results.", e);
        }
        finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }

        return jsonresponse;

    }

    private static String readFromStream(InputStream inputStream) throws IOException{
        StringBuilder output= new StringBuilder();
        if(inputStream!=null){
            InputStreamReader reader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader1 = new BufferedReader(reader);
            String line = reader1.readLine();
            while (line != null) {
                output.append(line);
                line = reader1.readLine();
            }
        }
        return output.toString();
    }

    private static List<News> extractNews(String newsJSON) {
        if (TextUtils.isEmpty(newsJSON)) {
            return null;
        }

        List<News> newsList = new ArrayList<>();

        try {
            JSONObject baseJsonResponse = new JSONObject(newsJSON);
            JSONObject response = baseJsonResponse.getJSONObject("response");
            JSONArray resultsArray = response.getJSONArray("results");

            for (int i = 0; i < resultsArray.length(); i++) {

                JSONObject currentResults = resultsArray.getJSONObject(i);

                String Title = currentResults.getString("webTitle");
                String category = currentResults.getString("sectionName");
                String date = currentResults.getString("webPublicationDate");
                String url = currentResults.getString("webUrl");
                JSONArray tagsauthor = currentResults.getJSONArray("tags");
                String author = "";
                if (tagsauthor.length() != 0) {
                    JSONObject currenttagsauthor = tagsauthor.getJSONObject(0);
                    author = currenttagsauthor.getString("webTitle");
                }
                News news = new News(Title, category, date, url, author);
                newsList.add(news);
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Log.d(TAG,"JSON Exception");
        }
        return newsList;
    }
}
