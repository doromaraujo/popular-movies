package com.diego.popularmovies;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements MoviePosterHandler
{
    private ImageAdapter imageAdapter = null;

    public MainActivityFragment()
    {
        new FetchMoviePosterTask(this).execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        this.imageAdapter = new ImageAdapter(this.getContext());

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        GridView moviePostersGridView =
                (GridView)rootView.findViewById(R.id.movie_poster_grid_view);

        moviePostersGridView.setAdapter(this.imageAdapter);

        moviePostersGridView.setOnItemClickListener(new OnItemClickListener(this.getContext()));

        return rootView;
    }

    @Override
    public void onResume()
    {
        super.onResume();

        new FetchMoviePosterTask(this).execute();
    }

    public void setMoviePosterData(List<String> data)
    {
        if (data != null)
        {
            this.imageAdapter.clear();

            this.imageAdapter.addAll(data);
        }
    }

    public String getMovieOrderPreference()
    {
        String movieOrderPreference = null;

        SharedPreferences settings = PreferenceManager
                .getDefaultSharedPreferences(this.getContext());

        movieOrderPreference = settings.getString(
                this.getString(R.string.preferences_movies_sort_order_key),
                this.getString(R.string.preferences_movies_value_popular));

        return movieOrderPreference;
    }
}
