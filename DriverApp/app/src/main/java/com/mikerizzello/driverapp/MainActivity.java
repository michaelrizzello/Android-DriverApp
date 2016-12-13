package com.mikerizzello.driverapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.mikerizzello.driverapp.api.APIManager;
import com.mikerizzello.driverapp.managers.LocationManager;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private SupportMapFragment mMapFragment;
    private EditText orderIdField;
    private GoogleMap googleMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {
        if (mMapFragment == null) {
            mMapFragment = SupportMapFragment.newInstance();
            mMapFragment.getMapAsync(this);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.map, mMapFragment)
                    .commit();
        }

        final Button updateLocationButton = (Button)findViewById(R.id.update_location);
        Button myLocationButton = (Button)findViewById(R.id.my_location_button);

        this.orderIdField = (EditText) findViewById(R.id.order_id_field);
        this.orderIdField.setImeOptions(EditorInfo.IME_ACTION_DONE);
        this.orderIdField.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    // do your stuff here
                    updateLocation();
                }
                return false;
            }
        });


        updateLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateLocation();
            }
        });

        myLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showUserLocation();
            }
        });
    }

    private void updateLocation()
    {
        if (this.orderIdField.getText().toString().length() == 0) return;

        int orderId = Integer.parseInt(this.orderIdField.getText().toString());


        APIManager.getInstance().submitLocation(orderId, LocationManager.getInstance().getGpsTracker().getLocation(), new JsonHttpResponseHandler()
        {
            @Override
            public void onSuccess(int statusCode, Header[] headers, org.json.JSONObject response) {
                super.onSuccess(statusCode, headers, response);

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, java.lang.String responseString, java.lang.Throwable throwable) {

                super.onFailure(statusCode, headers, responseString, throwable);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, java.lang.Throwable throwable, org.json.JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);

            }

        });
    }

    private void showUserLocation()
    {
        LatLng userPosition = new LatLng(LocationManager.getInstance().getGpsTracker().getLatitude(), LocationManager.getInstance().getGpsTracker().getLongitude());
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(userPosition, 16);
        this.googleMap.animateCamera(cameraUpdate);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        this.googleMap = googleMap;
        Log.e("In Here", "In Here");

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.


            return;
        }

        Log.e("In Here", "In Here");
        this.googleMap.setMyLocationEnabled(true);
        this.showUserLocation();



    }
}
