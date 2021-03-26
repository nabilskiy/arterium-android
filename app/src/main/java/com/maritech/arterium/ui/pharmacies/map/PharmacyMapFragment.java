package com.maritech.arterium.ui.pharmacies.map;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.maritech.arterium.R;
import com.maritech.arterium.data.models.PharmacyModel;
import com.maritech.arterium.databinding.FragmentMapBinding;
import com.maritech.arterium.ui.base.BaseFragment;
import com.maritech.arterium.ui.pharmacies.PharmaciesViewModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class PharmacyMapFragment
        extends BaseFragment<FragmentMapBinding> implements OnMapReadyCallback {

    private GoogleMap googleMap;
    ViewPagerAdapter mViewPagerAdapter;

    private final ArrayList<PharmacyModel> models = new ArrayList<>();

    private PharmaciesViewModel pharmaciesViewModel;
    Drawable markerDrawable;

    private final CompositeDisposable disposable = new CompositeDisposable();

    @Override
    protected int getContentView() {
        return R.layout.fragment_map;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        pharmaciesViewModel =
                new ViewModelProvider(requireActivity()).get(PharmaciesViewModel.class);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        markerDrawable = ContextCompat.getDrawable(requireContext(), R.drawable.marker);

        binding.viewPager.setClipToPadding(false);
        binding.viewPager.setPadding(0, 0, 0, 0);

        observeViewModel();
    }

    private void initViewPagerAdapter() {
        mViewPagerAdapter = new ViewPagerAdapter(
                requireContext(), models, position -> animateCamera(models.get(position))
        );
        binding.viewPager.setAdapter(mViewPagerAdapter);
    }

    private void observeViewModel() {
        pharmaciesViewModel.modelList.observe(getViewLifecycleOwner(),
                list -> {
                    models.clear();
                    models.addAll(list);

                    initViewPagerAdapter();

                    if (googleMap != null) {
                        googleMap.clear();

                        initMarkersOnThread();
                    }
                });

        pharmaciesViewModel.selectedPharmacyPosition.observe(
                getViewLifecycleOwner(), position -> animateCamera(models.get(position))
        );
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        LatLng latLng = new LatLng(49.0577374, 30.5431185);

        this.googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 5));

        this.googleMap.getUiSettings().setMyLocationButtonEnabled(true);

        if (markers.size() != 0) {
            initMarkersOnThread();
        }
    }

    List<MarkerOptions> markers = new ArrayList<>();

    private void addMarkers() {
        if (googleMap != null) {
            for (MarkerOptions options : markers) {
                googleMap.addMarker(options);
            }

            binding.progressBar.setVisibility(View.GONE);
        }
    }

    private void initMarkersOnThread() {
        binding.progressBar.setVisibility(View.VISIBLE);

        disposable.add(Observable.just(models)
                .subscribeOn(Schedulers.io())
                .flatMap((Function<ArrayList<PharmacyModel>, ObservableSource<ArrayList<MarkerOptions>>>) pharmacyModels -> {
                    ArrayList<MarkerOptions> markers = new ArrayList<>();
                    for (int i = 0; i < models.size(); i++) {
                        LatLng latLng = new LatLng(
                                Double.parseDouble(models.get(i).getLat()), Double.parseDouble(models.get(i).getLon())
                        );
                        MarkerOptions markerOptions = new MarkerOptions()
                                .position(latLng)
                                .title(models.get(i).getName())
                                .icon(getMarkerIconFromDrawable(markerDrawable));

                        markers.add(markerOptions);
                    }

                    return Observable.just(markers);
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(markerOptions -> {
                    markers = markerOptions;
                    addMarkers();
                }));
    }

    private void animateCamera(PharmacyModel model) {
        LatLng latLng = new LatLng(
                Double.parseDouble(model.getLat()), Double.parseDouble(model.getLon())
        );
        CameraPosition cameraPosition =
                new CameraPosition.Builder().target(latLng).zoom(15).build();

        if (googleMap != null) {
            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        }
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        disposable.dispose();
    }
}