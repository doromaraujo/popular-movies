package com.diego.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.Calendar;

/**
 * A placeholder fragment containing a simple view.
 */
public class MovieDetailActivityFragment extends Fragment implements MovieDetailsHandler
{
    public MovieDetailActivityFragment()
    {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_movie_detail, container, false);

        Intent intent = this.getActivity().getIntent();

        if (intent != null && intent.hasExtra("movie"))
        {
            Movie movie = intent.getParcelableExtra("movie");

            int position = intent.getIntExtra("position", 0);

            if (movie.getRuntime() == 0)
            {
                new FetchMovieDetailsTask(this).execute(movie.getId(), position);
            }

            ((TextView) rootView.findViewById(R.id.detail_movie_title_text_view))
                    .setText(movie.getTitle());

            ImageView thumbnail =
                    (ImageView)rootView.findViewById(R.id.detail_movie_thumbnail_image_view);

            Picasso.with(this.getContext()).load(movie.getPosterPath())
                    .resize(225, 360)
                    .into(thumbnail);

            TextView releaseDate =
                    (TextView)rootView.findViewById(R.id.detail_movie_release_date_text_view);

            releaseDate.setText(String.format("%d", movie.getReleaseDate().get(Calendar.YEAR)));

            TextView rating =
                    (TextView)rootView.findViewById(R.id.detail_movie_rating_text_view);

            DecimalFormat decimalFormat = new DecimalFormat("#.0");

            rating.setText(String.format("%s/10", decimalFormat.format(movie.getVoteAverage())));

            ((TextView) rootView.findViewById(R.id.detail_movie_overview_text_view))
                    .setText(movie.getOverview());
        }

        return rootView;
    }

    @Override
    public void setMovieDetails(Movie movie)
    {
        TextView view = (TextView)
                this.getActivity().findViewById(R.id.detail_movie_duration_text_view);

        view.setText(String.format("%dmin", movie.getRuntime()));
    }
}
