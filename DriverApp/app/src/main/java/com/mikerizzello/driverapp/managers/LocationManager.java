package com.mikerizzello.driverapp.managers;

import com.mikerizzello.driverapp.global.App;

/**
 * Created by michaelrizzello on 2016-12-12.
 */
public class LocationManager {
    private static LocationManager ourInstance = new LocationManager();

    private GPSTracker gpsTracker;

    public static LocationManager getInstance() {
        return ourInstance;
    }

    private LocationManager() {
        gpsTracker = new GPSTracker(App.getmContext());
    }

    public GPSTracker getGpsTracker(){
        return this.gpsTracker;
    }

}
