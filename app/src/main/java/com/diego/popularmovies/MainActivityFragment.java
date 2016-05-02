package com.diego.popularmovies;

import android.os.Bundle;
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

        return rootView;
    }

    public void setMoviePosterData(List<String> data)
    {
        if (data != null)
        {
            this.imageAdapter.clear();

            this.imageAdapter.addAll(data);
        }
    }
}
