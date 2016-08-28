/*
 *
 *  Copyright (c) 2016 Speakgame
 *  All rights reserved.
 *
 *  This software is a confidential and proprietary information of Speakgame.
 *  ("Confidential Information").  You shall not disclose such
 *  Confidential Information and shall use it only in accordance with the terms
 *  of the license agreement you entered into with Speakgame.
 * /
 *
 */

package br.com.marvel;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vinitius on 8/28/16.
 */
public class LocationActivity extends AppCompatActivity
        implements OnMapReadyCallback{


    private MapFragment mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_activity);

        mapFragment = (MapFragment)getFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        Geocoder geocoder = new Geocoder(this);
        List<Address> addresses = new ArrayList<>();
        LatLng location = new LatLng(0,0);

        try {
            addresses = geocoder
                    .getFromLocationName("Teatro Amazonas Manaus", 5);
            location = addresses.isEmpty()
                    ? new LatLng(0,0)
                    : new LatLng(addresses.get(0).getLatitude(),
                    addresses.get(0).getLongitude());
        } catch (IOException e) {
            e.printStackTrace();
        }

        googleMap.setMyLocationEnabled(true);
        googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        googleMap.moveCamera(CameraUpdateFactory
                .newLatLngZoom(location,15));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(15));
        googleMap.addMarker(new MarkerOptions()
                .title("Teatro Amazonas")
                .position(location));

    }
}
