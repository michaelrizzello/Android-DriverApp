package com.mikerizzello.driverapp.api;

import android.content.Context;
import android.location.Location;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.mikerizzello.driverapp.global.App;


/**
 * Created by michaelrizzello on 2016-12-12.
 */

public class APIManager {

    private AsyncHttpClient client;
    private Context mContext;

    private static APIManager ourInstance = new APIManager(App.getmContext());

    public static APIManager getInstance() {
        return ourInstance;
    }

    private APIManager(Context context)
    {
        mContext = context;
        this.client = new AsyncHttpClient();
    }

    public void submitLocation(int orderID, Location location, JsonHttpResponseHandler handler)
    {
        String url = Constants.BASEURL + Constants.SUBMIT_LOCATION_ENDPOINT + "?orderId=" + orderID + "&latitude=" + location.getLatitude() + "&longitude=" + location.getLongitude();

        Log.e("URL", "URL " + url);
        client.get(mContext, url, null, handler);
    }
}
