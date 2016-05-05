package com.diego.popularmovies;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by diego on 29/04/2016.
 */
public class FetchMovieSummariesTask extends AsyncTask<Void, Void, List<Movie>>
{
    private final String LOG_TAG = FetchMovieSummariesTask.class.getSimpleName();

    private String getMoviesSortOrderPreference()
    {
        return this.movieSummariesHandler.getMovieOrderPreference();
    }

    private MovieSummariesHandler movieSummariesHandler = null;

    public FetchMovieSummariesTask(MovieSummariesHandler movieSummariesHandler)
    {
        this.movieSummariesHandler = movieSummariesHandler;
    }

    @Override
    protected List<Movie> doInBackground(Void... params)
    {
        List<Movie> movies = new ArrayList<>();

        Uri.Builder builder = new Uri.Builder();

        builder.scheme("http")
                .authority("api.themoviedb.org")
                .appendPath("3")
                .appendPath("movie")
                .appendPath(this.getMoviesSortOrderPreference())
                .appendQueryParameter("api_key", BuildConfig.THEMOVIEDB_API_KEY);

        RequestHandler requestHandler = new RequestHandler();

        String response = requestHandler.getResponse(builder.build().toString());

        if (response == null)
        {
            return movies;
        }

        try
        {
            movies = MovieDataParser.createMoviesFromJSON(response);
        }
        catch (JSONException e)
        {
            Log.e(this.LOG_TAG, "On parsing server's JSON response", e);
        }
        catch (ParseException e)
        {
            Log.e(this.LOG_TAG, "On parsing server's JSON response 'DATE' format", e);
        }

        return movies;
    }

    @Override
    protected void onPostExecute(List<Movie> data)
    {
        this.movieSummariesHandler.setMovieSummaries(data);
    }
}
