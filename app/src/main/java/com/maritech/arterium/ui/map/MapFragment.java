package com.maritech.arterium.ui.map;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.maritech.arterium.R;

public class MapFragment extends Fragment {

    MapView mMapView;
    private GoogleMap googleMap;
    ViewPager mViewPager;
    int[] pharmacyList = {
            R.string.good_day_pharmacy,
            R.string.wholesale_pharmacy,
            R.string.tas_pharmacy,
            R.string.monet_pharmacy,
            R.string.swiss_pharmacy
    };
    ViewPagerAdapter mViewPagerAdapter;

    CameraPosition cameraPositionOne;
    CameraPosition cameraPositionTwo;
    CameraPosition cameraPositionThree;
    CameraPosition cameraPositionFour;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_map, container, false);

        Drawable markerDrawable = ContextCompat.getDrawable(requireContext(), R.drawable.marker);

        mMapView = root.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume();

        try {
            MapsInitializer.initialize(requireContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(mMap -> {
            googleMap = mMap;
            LatLng one = new LatLng(-34, 151);
            LatLng two = new LatLng(-32, 151);
            LatLng three = new LatLng(-33, 151);
            LatLng four = new LatLng(-34, 151);
            cameraPositionOne = new CameraPosition.Builder().target(one).zoom(12).build();
            cameraPositionTwo = new CameraPosition.Builder().target(two).zoom(12).build();
            cameraPositionThree = new CameraPosition.Builder().target(three).zoom(12).build();
            cameraPositionFour = new CameraPosition.Builder().target(four).zoom(12).build();

            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPositionOne));

            googleMap.addMarker(new MarkerOptions()
                    .position(one)
                    .title("Marker in one"))
                    .setIcon(getMarkerIconFromDrawable(markerDrawable));

            googleMap.addMarker(new MarkerOptions()
                    .position(two)
                    .title("Marker in two"))
                    .setIcon(getMarkerIconFromDrawable(markerDrawable));

            googleMap.addMarker(new MarkerOptions()
                    .position(three)
                    .title("Marker in three"))
                    .setIcon(getMarkerIconFromDrawable(markerDrawable));

            googleMap.addMarker(new MarkerOptions()
                    .position(four)
                    .title("Marker in four"))
                    .setIcon(getMarkerIconFromDrawable(markerDrawable));

            if (ActivityCompat.checkSelfPermission(
                    requireContext(), Manifest.permission.ACCESS_FINE_LOCATION
            )
                    != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(
                    requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED) {
                return;
            }

            googleMap.setMyLocationEnabled(true);
        });


        mViewPager = root.findViewById(R.id.viewPager);
        mViewPager.setClipToPadding(false);
        mViewPager.setPadding(0, 0, 0, 0);
        mViewPagerAdapter = new ViewPagerAdapter(requireContext(), pharmacyList, position -> {
            if (position == 0) {
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPositionOne));
                Log.e("!!!", "1");
            } else if (position == 1) {
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPositionTwo));
                Log.e("!!!", "2");
            } else if (position == 2) {
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPositionThree));
                Log.e("!!!", "3");
            } else if (position == 3) {
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPositionFour));
                Log.e("!!!", "4");
            }
        });
        mViewPager.setAdapter(mViewPagerAdapter);

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    private BitmapDescriptor getMarkerIconFromDrawable(Drawable drawable) {
        Canvas canvas = new Canvas();
        Bitmap bitmap = Bitmap.createBitmap(
                drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(),
                Bitmap.Config.ARGB_8888
        );
        canvas.setBitmap(bitmap);
        drawable.setBounds(
                0,
                0,
                drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight()
        );
        drawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

}
