package com.example.week5weekendhw;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    public static final String TAG = "FRANK: ";
    Intent passedIntent;
    String address;
    LatLng sentAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        passedIntent = getIntent();
        address = passedIntent.getStringExtra("address");

        sentAddress = getCurrentLatLngFromAddress(address);
        Log.d(TAG, "onCreate: got current location " + sentAddress.latitude + " " + sentAddress.longitude);


    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        moveToNewLocation(sentAddress, address);
    }

    private void moveToNewLocation(LatLng latLng, String locationName) {
        if (latLng != null) {
            Log.d(TAG, "moveToNewLocation: " + latLng.longitude + " " + latLng.latitude);
            Log.d(TAG, "moveToNewLocation: " + locationName);
            mMap.addMarker(new MarkerOptions().position(latLng).title(locationName));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        }
    }

    public LatLng getCurrentLatLngFromAddress(String address) {
        Geocoder geocoder = new Geocoder(this);
        try {
            List<Address> addressWithLatLng = geocoder.getFromLocationName(address, 1);
            LatLng newLatLng = new LatLng(addressWithLatLng.get(0).getLatitude(), addressWithLatLng.get(0).getLongitude());
            Log.d(TAG, "getCurrentLatLngFromAddress: " + newLatLng.toString());
            return newLatLng;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
