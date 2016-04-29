package com.diego.popularmovies;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by diego on 29/04/2016.
 */
public class FetchMoviePosterPathTask extends AsyncTask<Void, Void, List<String>>
{
    private final String LOG_TAG = FetchMoviePosterPathTask.class.getSimpleName();

    private String getMoviesSortOrderPreference()
    {
        return "popular";
    }

    @Override
    protected List<String> doInBackground(Void... params)
    {
        List<String> posterPaths = new ArrayList<>();

        Uri.Builder builder = new Uri.Builder();

        builder.scheme("http")
                .authority("api.themoviedb.org")
                .appendPath("3")
                .appendPath("movie")
                .appendPath(this.getMoviesSortOrderPreference())
                .appendQueryParameter("api_key", BuildConfig.THEMOVIEDB_API_KEY);

        URL url = null;

        try
        {
            url = new URL(builder.build().toString());
        }
        catch (MalformedURLException e)
        {
            Log.e(this.LOG_TAG, "Malformed URL", e);
        }
        
        if (url == null)
        {
            return posterPaths;
        }

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String line;
        String response = null;

        try
        {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream stream = urlConnection.getInputStream();
            StringBuilder buffer = new StringBuilder();

            if (stream == null)
            {
                return posterPaths;
            }

            reader = new BufferedReader(new InputStreamReader(stream));

            while ((line = reader.readLine()) != null)
            {
                buffer.append(line).append("\r\n");
            }

            if (buffer.length() == 0)
            {
                return posterPaths;
            }

            response = buffer.toString();
        }
        catch (Exception e)
        {
            Log.e(this.LOG_TAG, "On retrieving movie posters from server", e);
        }
        finally
        {
            if (urlConnection != null)
            {
                urlConnection.disconnect();
            }

            if (reader != null)
            {
                try
                {
                    reader.close();
                }
                catch (Exception e)
                {
                    Log.e(this.LOG_TAG, "On closing the buffered reader", e);
                }
            }
        }

        try
        {
            posterPaths = MovieDataParser.getMoviePosterPathsFromJSON(response);
        }
        catch (JSONException e)
        {
            Log.e(this.LOG_TAG, "On parsing server's JSON response", e);
        }

        return posterPaths;
    }
}
