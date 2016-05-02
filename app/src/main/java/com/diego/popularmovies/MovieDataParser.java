package com.diego.popularmovies;

import android.net.Uri;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by diego on 29/04/2016.
 */
public class MovieDataParser
{
    private static String formatMoviePosterPath(String moviePosterPath)
    {
        Uri.Builder builder = new Uri.Builder();

        builder.authority("image.tmdb.org")
                .scheme("http")
                .appendPath("t")
                .appendPath("p")
                .appendPath("w185");

        return String.format("%s/%s", builder.build().toString(), moviePosterPath);
    }

    public static List<String> getMoviePosterPathsFromJSON(String jsonData) throws JSONException
    {
        List<String> moviePosterPaths = new ArrayList<>();

        if (jsonData == null || jsonData.isEmpty())
        {
            return moviePosterPaths;
        }

        JSONObject root = new JSONObject(jsonData);
        JSONArray results = root.getJSONArray("results");
        JSONObject movieInfo;

        String moviePosterPath;

        for (int i = 0; i < results.length(); ++i)
        {
            movieInfo = results.getJSONObject(i);

            moviePosterPath = movieInfo.getString("poster_path");

            moviePosterPaths.add(formatMoviePosterPath(moviePosterPath));
        }

        return moviePosterPaths;
    }
}
