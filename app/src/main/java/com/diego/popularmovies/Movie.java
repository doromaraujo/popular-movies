package com.diego.popularmovies;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by diego on 03/05/2016.
 */
public class Movie implements Parcelable
{
    private final String DEFAULT_POSTER_SIZE = "w342";
    private final String DEFAULT_THUMBNAIL_SIZE = "w92";

    private int id;
    private int runtime;

    private String posterPath;
    private String overview;
    private String title;

    private Calendar releaseDate;

    private Double voteAverage;

    private Movie(Parcel source)
    {
        this.id = source.readInt();
        this.runtime = source.readInt();

        this.posterPath = source.readString();
        this.overview = source.readString();
        this.title = source.readString();

        this.releaseDate = new GregorianCalendar();
        this.releaseDate.setTimeInMillis(source.readLong());

        this.voteAverage = source.readDouble();
    }

    public Movie(int id, String posterPath, String overview, Calendar releaseDate, String title
            , Double voteAverage)
    {
        this.id = id;
        this.posterPath = posterPath;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.title = title;
        this.voteAverage = voteAverage;
    }

    private String formatPosterPath(String posterPath)
    {
        return this.formatPosterPath(posterPath, this.DEFAULT_POSTER_SIZE);
    }

    private String formatPosterPath(String posterPath, String size)
    {
        Uri.Builder builder = new Uri.Builder();

        builder.authority("image.tmdb.org")
                .scheme("http")
                .appendPath("t")
                .appendPath("p")
                .appendPath(size);

        return String.format("%s/%s", builder.build().toString(), posterPath);
    }

    public void setRuntime(int runtime)
    {
        this.runtime = runtime;
    }

    public Calendar getReleaseDate()
    {
        return releaseDate;
    }

    public Double getVoteAverage()
    {
        return voteAverage;
    }

    public String getOverview()
    {
        return overview;
    }

    public String getPosterPath()
    {
        return this.formatPosterPath(this.posterPath);
    }

    public String getPosterThumbnailPath()
    {
        return this.formatPosterPath(this.posterPath, this.DEFAULT_THUMBNAIL_SIZE);
    }

    public String getTitle()
    {
        return title;
    }

    public int getId()
    {
        return this.id;
    }

    public int getRuntime()
    {
        return this.runtime;
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel destination, int flags)
    {
        destination.writeInt(this.id);
        destination.writeInt(this.runtime);

        destination.writeString(this.posterPath);
        destination.writeString(this.overview);
        destination.writeString(this.title);

        destination.writeLong(this.releaseDate.getTimeInMillis());
        destination.writeDouble(this.voteAverage);
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>()
    {
        @Override
        public Movie createFromParcel(Parcel source)
        {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size)
        {
            return new Movie[0];
        }
    };
}
