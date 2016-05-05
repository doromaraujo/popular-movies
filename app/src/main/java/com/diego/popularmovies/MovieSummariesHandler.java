package com.diego.popularmovies;

import java.util.List;

/**
 * Created by diego on 02/05/2016.
 */
public interface MovieSummariesHandler
{
    void setMovieSummaries(List<Movie> data);

    String getMovieOrderPreference();
}
