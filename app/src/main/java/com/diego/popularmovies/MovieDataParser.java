package com.diego.popularmovies;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by diego on 29/04/2016.
 */
public class MovieDataParser
{
    private static Movie createMovieFromJSONObject(JSONObject movieInfo) throws JSONException
            , ParseException
    {
        final String ID = "id"
                , POSTER_PATH = "poster_path"
                , OVERVIEW = "overview"
                , TITLE = "title"
                , RELEASE_DATE = "release_date"
                , VOTE_AVERAGE = "vote_average"
                , RUNTIME = "runtime";


        int id;
        String posterPath;
        String title;
        String overview;

        Calendar releaseDate;
        Double voteAverage;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        id = movieInfo.getInt(ID);
        posterPath = movieInfo.getString(POSTER_PATH);
        overview = movieInfo.getString(OVERVIEW);
        title = movieInfo.getString(TITLE);

        releaseDate = GregorianCalendar.getInstance();
        releaseDate.setTime(dateFormat.parse(movieInfo.getString(RELEASE_DATE)));

        voteAverage = movieInfo.getDouble(VOTE_AVERAGE);

        Movie movie = new Movie(id, posterPath, overview, releaseDate, title, voteAverage);

        if (movieInfo.has(RUNTIME))
        {
            movie.setRuntime(movieInfo.getInt(RUNTIME));
        }

        return movie;
    }

    public static Movie createMovieFromJSON(String jsonData)  throws JSONException, ParseException
    {
        JSONObject movieInfo = new JSONObject(jsonData);

        return createMovieFromJSONObject(movieInfo);
    }

    public static List<Movie> createMoviesFromJSON(String jsonData) throws JSONException
            , ParseException
    {
        List<Movie> movies = new ArrayList<>();

        if (jsonData == null || jsonData.isEmpty())
        {
            return movies;
        }

        JSONObject root = new JSONObject(jsonData);
        JSONArray results = root.getJSONArray("results");
        JSONObject movieInfo;

        Movie movie;

        for (int i = 0; i < results.length(); ++i)
        {
            movieInfo = results.getJSONObject(i);

            movie = createMovieFromJSONObject(movieInfo);

            movies.add(movie);
        }

        return movies;
    }
}
