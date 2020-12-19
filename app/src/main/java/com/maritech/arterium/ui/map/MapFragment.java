package com.maritech.arterium.ui.map;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.maritech.arterium.R;
import static com.google.android.gms.maps.GoogleMap.*;

public class MapFragment extends Fragment {

    private final LatLng PERTH = new LatLng(-31.952854, 115.857342);
    private final LatLng SYDNEY = new LatLng(-33.87365, 151.20689);
    private final LatLng BRISBANE = new LatLng(-27.47093, 153.0235);

    private Marker markerPerth;
    private Marker markerSydney;
    private Marker markerBrisbane;

    ViewPager mViewPager;
    MapView mMapView;
    private GoogleMap googleMap;
    int[] pharmacyList = {R.string.good_day_pharmacy, R.string.wholesale_pharmacy, R.string.tas_pharmacy, R.string.monet_pharmacy, R.string.swiss_pharmacy};

    ViewPagerAdapter mViewPagerAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_map, container, false);

        mMapView = (MapView) root.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume();

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(mMap -> {
            googleMap = mMap;

            markerPerth = mMap.addMarker(new MarkerOptions()
                    .position(PERTH)
                    .title("Perth"));
            markerPerth.setTag(0);

            markerSydney = mMap.addMarker(new MarkerOptions()
                    .position(SYDNEY)
                    .title("Sydney"));
            markerSydney.setTag(0);

            markerBrisbane = mMap.addMarker(new MarkerOptions()
                    .position(BRISBANE)
                    .title("Brisbane"));
            markerBrisbane.setTag(0);

            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(SYDNEY, 10f));
            googleMap.setOnMarkerClickListener(new OnMarkerClickListener(){
                @Override
                public boolean onMarkerClick(Marker marker) {
                    Log.e("!!!!", marker.getTitle());
                    return false;
                }
            });
        });

        mViewPager = root.findViewById(R.id.viewPager);
        mViewPagerAdapter = new ViewPagerAdapter(getContext(), pharmacyList);
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
}
