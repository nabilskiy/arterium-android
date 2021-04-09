package com.maritech.arterium.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.google.android.gms.maps.model.LatLng;
import com.maritech.arterium.data.models.PharmacyModel;

public class NavigateUtils {

    public static void navigateGoogleMaps(Context context, PharmacyModel model) {
        LatLng latLng = new LatLng(
                Double.parseDouble(model.getLat()), Double.parseDouble(model.getLon())
        );
        String url = "https://www.google.com/maps/dir/?api=1&destination=" +
                latLng.latitude + "," + latLng.longitude + "&travelmode=driving";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        context.startActivity(intent);
    }
}
