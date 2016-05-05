package com.diego.popularmovies;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by diego on 05/05/2016.
 */
public class RequestHandler
{
    private final String LOG_TAG = RequestHandler.class.getSimpleName();

    public String getResponse(String uri)
    {
        URL url = null;

        String response = "";

        try
        {
            url = new URL(uri);
        }
        catch (MalformedURLException e)
        {
            Log.e(this.LOG_TAG, "Malformed URL", e);
        }

        if (url == null)
        {
            return response;
        }

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String line;

        try
        {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream stream = urlConnection.getInputStream();
            StringBuilder buffer = new StringBuilder();

            if (stream == null)
            {
                return response;
            }

            reader = new BufferedReader(new InputStreamReader(stream));

            while ((line = reader.readLine()) != null)
            {
                buffer.append(line).append("\r\n");
            }

            if (buffer.length() == 0)
            {
                return response;
            }

            response = buffer.toString();
        }
        catch (Exception e)
        {
            Log.e(this.LOG_TAG, "On retrieving data from server", e);
        }
        finally
        {
            if (urlConnection != null)
            {
                urlConnection.disconnect();
            }

            if (reader != null)
            {
                try
                {
                    reader.close();
                }
                catch (Exception e)
                {
                    Log.e(this.LOG_TAG, "On closing the buffered reader", e);
                }
            }
        }

        return response;
    }
}
