package com.diego.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

/**
 * Created by diego on 02/05/2016.
 */
public class OnItemClickListener implements AdapterView.OnItemClickListener
{
    private Context context;

    public OnItemClickListener(Context context)
    {
        this.context = context;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        switch (view.getId())
        {
            case R.id.movie_poster_list_item_image_view:

                //Movie movie = (Movie)view.getTag();

//                int currentHeight = view.getHeight();
//                int currentWidth = view.getWidth();

                Movie movie = (Movie)parent.getAdapter().getItem(position);

                Intent intent = new Intent(this.context, MovieDetailActivity.class)
                        .putExtra("movie", movie)
                        .putExtra("position", position);

                this.context.startActivity(intent);

//                String message = String.format(Locale.getDefault()
//                        , "Clicked on position %d for movie \"%s\"!", position, movie.getTitle());
//
//                Toast.makeText(this.context, message, Toast.LENGTH_SHORT).show();

                break;
        }
    }
}
