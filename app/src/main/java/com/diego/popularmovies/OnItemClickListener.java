package com.diego.popularmovies;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import java.util.Locale;

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

                String message = String.format(Locale.getDefault()
                        , "Clicked on position %d!", position);

                Toast.makeText(this.context, message, Toast.LENGTH_SHORT).show();

                break;
        }
    }
}
