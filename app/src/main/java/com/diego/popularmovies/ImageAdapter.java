package com.diego.popularmovies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by diego on 29/04/2016.
 */
public class ImageAdapter extends BaseAdapter
{
    private final String LOG_TAG = ImageAdapter.class.getSimpleName();

    private Context context;
    private List<String> data = new ArrayList<>();
    private LayoutInflater inflater = null;

    public ImageAdapter(Context context)
    {
        this.context = context;
        this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount()
    {
        return this.data.size();
    }

    @Override
    public Object getItem(int position)
    {
        return this.data.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    public void clear()
    {
        this.data.clear();
    }

    public void addAll(List<String> data)
    {
        this.data.addAll(data);
        this.notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ImageView imageView;

        if (convertView == null)
        {
            View rootView = this.inflater.inflate(R.layout.movie_poster_list_item, parent
                    , false);

            imageView = (ImageView)rootView.findViewById(R.id.movie_poster_list_item_image_view);

            //imageView = new ImageView(this.context);
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            imageView.setAdjustViewBounds(true);
        }
        else
        {
            imageView = (ImageView)convertView;
        }

        if (position < this.data.size())
        {
            String imageUrl = this.data.get(position);

            //Log.i(this.LOG_TAG, builder.build().toString());

            Picasso.with(this.context).load(imageUrl).into(imageView);
        }
        return imageView;
    }
}
