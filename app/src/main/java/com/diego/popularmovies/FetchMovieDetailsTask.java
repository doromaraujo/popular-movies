package com.diego.popularmovies;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;

import java.text.ParseException;

/**
 * Created by diego on 05/05/2016.
 */
public class FetchMovieDetailsTask extends AsyncTask<Integer, Void, Movie>
{
    private final String LOG_TAG = FetchMovieDetailsTask.class.getSimpleName();

    private MovieDetailsHandler movieDetailsHandler;

    public FetchMovieDetailsTask(MovieDetailsHandler movieDetailsHandler)
    {
        this.movieDetailsHandler = movieDetailsHandler;
    }

    @Override
    protected Movie doInBackground(Integer... params)
    {
        Movie movie = null;

        int movieId = params[0];

        int position = params[1];

        Uri.Builder builder = new Uri.Builder();

        builder.scheme("http")
                .authority("api.themoviedb.org")
                .appendPath("3")
                .appendPath("movie")
                .appendPath(Integer.toString(movieId))
                .appendQueryParameter("api_key", BuildConfig.THEMOVIEDB_API_KEY);

        RequestHandler requestHandler = new RequestHandler();

        String response = requestHandler.getResponse(builder.build().toString());

        if (response == null)
        {
            return null;
        }

        try
        {
            movie = MovieDataParser.createMovieFromJSON(response);
        }
        catch (JSONException e)
        {
            Log.e(this.LOG_TAG, "On parsing server's JSON response", e);
        }
        catch (ParseException e)
        {
            Log.e(this.LOG_TAG, "On parsing server's JSON response 'DATE' format", e);
        }

        return movie;
    }

    @Override
    protected void onPostExecute(Movie movie)
    {
        if (movie == null)
        {
            return;
        }

        this.movieDetailsHandler.setMovieDetails(movie);
    }
}
