package com.maritech.arterium.ui.pharmacies.map.cluster;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import androidx.core.content.ContextCompat;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;
import com.maritech.arterium.R;
import com.maritech.arterium.data.models.PharmacyModel;

public class ClusterRenderer extends DefaultClusterRenderer<PharmacyModel> {

    Drawable markerDrawable;

    public ClusterRenderer(Context context, GoogleMap map,
                           ClusterManager<PharmacyModel> clusterManager) {
        super(context, map, clusterManager);

        markerDrawable = ContextCompat.getDrawable(context, R.drawable.marker);
    }

    @Override
    protected void onBeforeClusterItemRendered(PharmacyModel item, MarkerOptions markerOptions) {
        markerOptions.icon( getMarkerIconFromDrawable(markerDrawable));
        markerOptions.title(item.getName());
        markerOptions.snippet(item.getName());
        super.onBeforeClusterItemRendered(item, markerOptions);
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