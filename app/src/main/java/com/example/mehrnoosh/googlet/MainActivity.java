package com.example.mehrnoosh.googlet;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {

    /**
     * Constant value for the Open Weather Map (OWM) loader ID. We can choose any integer.
     * This really only comes into play if you're using multiple loaders.
     */
    private static final int OWM_LOADER_ID = 1;

    /** URL for weather data from the Open Weather Map (OWM) dataset */
    private static final String OWM_REQUEST_URL =
            "http://api.openweathermap.org/data/2.5/weather";

    /** Customized URL for weather data from the Open Weather Map (OWM) dataset by the KEYAPI and location*/
    public String url;

    private static final String APIKEY = "bb2720d6986a0f5e76dd2dc1108381b8";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button searchButton = findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showWeatherInfo();
            }
        });

        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();


        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {
            // Get a reference to the LoaderManager, in order to interact with loaders.
            LoaderManager loaderManager = getLoaderManager();
            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).


            loaderManager.initLoader(OWM_LOADER_ID, null, this);
        }
        else {

        }

    }

    @Override
    public Loader onCreateLoader(int i, Bundle bundle) {
        String value = getCityName();

        Uri baseUri = Uri.parse(OWM_REQUEST_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();
        uriBuilder.appendQueryParameter("appid", APIKEY);
        uriBuilder.appendQueryParameter("q", value);
        // Create a new loader for the given URL
        return new WeatherLoader(this, uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String tempreture) {
        TextView tempTextView = findViewById(R.id.temp_text);
        tempTextView.setText(tempreture);
    }

    @Override
    public void onLoaderReset(Loader loader) {
        showWeatherInfo();

    }

    public String getCityName() {
        TextView locationEditText = findViewById(R.id.lication_text);
        String originalText = locationEditText.getText().toString();
        return originalText;
    }

    private void showWeatherInfo() {

        LoaderManager loaderManager = getLoaderManager();
        loaderManager.restartLoader(OWM_LOADER_ID, null, this);
    }
}
