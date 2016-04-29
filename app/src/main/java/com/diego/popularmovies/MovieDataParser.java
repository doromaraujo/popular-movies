package com.diego.popularmovies;

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

        for (int i = 0; i < results.length(); ++i)
        {
            movieInfo = results.getJSONObject(i);

            moviePosterPaths.add(movieInfo.getString("poster_path"));
        }

        return moviePosterPaths;
    }
}
