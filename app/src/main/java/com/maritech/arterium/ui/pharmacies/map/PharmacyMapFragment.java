package com.maritech.arterium.ui.pharmacies.map;

import android.os.Bundle;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.maps.android.clustering.ClusterManager;
import com.maritech.arterium.R;
import com.maritech.arterium.data.models.PharmacyModel;
import com.maritech.arterium.data.sharePref.Pref;
import com.maritech.arterium.databinding.FragmentMapBinding;
import com.maritech.arterium.ui.base.BaseFragment;
import com.maritech.arterium.ui.pharmacies.PharmaciesViewModel;
import com.maritech.arterium.ui.pharmacies.map.cluster.ClusterRenderer;
import java.util.ArrayList;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class PharmacyMapFragment
        extends BaseFragment<FragmentMapBinding> implements OnMapReadyCallback {

    private PharmaciesViewModel pharmaciesViewModel;

    private GoogleMap googleMap;
    private final ArrayList<PharmacyModel> models = new ArrayList<>();

    private ClusterManager<PharmacyModel> clusterManager;
    private Marker mSelectedMarker;

    private final CompositeDisposable disposable = new CompositeDisposable();

    private int programId;

    @Override
    protected int getContentView() {
        return R.layout.fragment_map;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        pharmaciesViewModel =
                new ViewModelProvider(requireActivity()).get(PharmaciesViewModel.class);

        programId = Pref.getInstance().getDrugProgramId(requireActivity());

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        binding.viewPager.setClipToPadding(false);
        binding.viewPager.setPadding(0, 0, 0, 0);

        observeViewModel();
    }

    private void initViewPagerAdapter() {
        ViewPagerAdapter mViewPagerAdapter = new ViewPagerAdapter(
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

                        addMarkers();
                    }
                });

//        pharmaciesViewModel.selectedPharmacyPosition.observe(
//                getViewLifecycleOwner(), position -> {
//                    animateCamera(models.get(position));
//                    setViewPagerByPosition(models.get(position));
//                }
//        );
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        LatLng latLng = new LatLng(49.0577374, 30.5431185);

        this.googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 5));

        this.googleMap.getUiSettings().setMyLocationButtonEnabled(true);

        setUpCluster();
    }

    ClusterRenderer clusterRenderer;

    private void setUpCluster() {
        clusterManager = new ClusterManager<>(requireContext(), googleMap);

        clusterRenderer = new ClusterRenderer(requireContext(), googleMap, clusterManager);
        clusterManager.setRenderer(clusterRenderer);

        clusterManager
                .setOnClusterClickListener(cluster -> {
                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                            cluster.getPosition(),
                            (float) Math.floor(googleMap.getCameraPosition().zoom + 3)
                            ), 300,
                            null
                    );
                    return true;
                });

        clusterManager.setOnClusterItemClickListener(item -> {
            setViewPagerByPosition(item);

            animateCamera(item);
            return true;
        });

        googleMap.setOnCameraIdleListener(clusterManager);

        addMarkers();
    }

    private void addMarkers() {
        if (googleMap != null) {
            binding.progressBar.setVisibility(View.VISIBLE);

            double minLat = Integer.MAX_VALUE;
            double maxLat = Integer.MIN_VALUE;
            double minLon = Integer.MAX_VALUE;
            double maxLon = Integer.MIN_VALUE;

            for (int i = 0; i < models.size(); i++) {
                clusterManager.addItem(models.get(i));

                maxLat = Math.max(Double.parseDouble(models.get(i).getLat()), maxLat);
                minLat = Math.min(Double.parseDouble(models.get(i).getLat()), minLat);
                maxLon = Math.max(Double.parseDouble(models.get(i).getLon()), maxLon);
                minLon = Math.min(Double.parseDouble(models.get(i).getLon()), minLon);
            }

            LatLngBounds bounds = new LatLngBounds.Builder()
                    .include(new LatLng(maxLat, maxLon))
                    .include(new LatLng(minLat, minLon))
                    .build();

            int width = getResources().getDisplayMetrics().widthPixels;
            int height = getResources().getDisplayMetrics().heightPixels;
            int padding = (int) (width * 0.25);

            CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding);

            googleMap.animateCamera(cu);

            binding.progressBar.setVisibility(View.GONE);
        }
    }

    private void setViewPagerByPosition(PharmacyModel model) {
        disposable.add(Observable.just(models)
                .subscribeOn(Schedulers.io())
                .flatMap((Function<ArrayList<PharmacyModel>, ObservableSource<Integer>>) pharmacyModels -> {
                    int position = -1;

                    for (int i = 0; i < pharmacyModels.size(); i++) {
                        position = i;

                        if (model.getId().equals(pharmacyModels.get(i).getId())) {
                            break;
                        }
                    }

                    return Observable.just(position);
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(position -> {
                    if (position != -1) {
                        binding.viewPager.setCurrentItem(position);
                    }
                }));
    }

    private void setSelectedMarker(PharmacyModel model) {
        Marker marker = clusterRenderer.getMarker(model);

        if (null != mSelectedMarker) {
            mSelectedMarker.setIcon(
                    BitmapDescriptorFactory.fromResource(R.drawable.marker)
            );
        }
        mSelectedMarker = marker;

        int markerDrawable;
        if (programId == 1) {
            markerDrawable = R.drawable.marker_purple;
        } else if (programId == 2) {
            markerDrawable = R.drawable.marker_blue;
        } else {
            markerDrawable = R.drawable.marker_red;
        }

        if (mSelectedMarker != null) {
            mSelectedMarker.setIcon(BitmapDescriptorFactory.fromResource(markerDrawable));
        }
    }

    private void animateCamera(PharmacyModel model) {
        LatLng latLng = new LatLng(
                Double.parseDouble(model.getLat()), Double.parseDouble(model.getLon())
        );
        CameraPosition cameraPosition =
                new CameraPosition.Builder().target(latLng).zoom(15).build();

        if (googleMap != null) {
            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition), new GoogleMap.CancelableCallback() {
                @Override
                public void onFinish() {
                    setSelectedMarker(model);
                }

                @Override
                public void onCancel() {
                }
            });
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        disposable.dispose();
    }
}