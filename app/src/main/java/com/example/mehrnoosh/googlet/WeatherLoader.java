package com.example.mehrnoosh.googlet;

import android.content.AsyncTaskLoader;
import android.content.Context;

/**
 * Created by Mehrnoosh on 4/6/2018.
 */

public class WeatherLoader extends AsyncTaskLoader<String> {

    /** Query URL */
    private String mUrl;

    public WeatherLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    /**
     * This is on a background thread.
     */
    @Override
    protected void onStartLoading() {
        forceLoad();
    }


    @Override
    public String loadInBackground() {
        if (mUrl == null) {
            return null;
        }

        // Perform the network request, parse the response, and extract a list of books.
        String temprature = QueryUtil.fetchTemperature(mUrl);
        return temprature;
    }
}
