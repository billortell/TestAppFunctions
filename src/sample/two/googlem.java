package sample.two;

import java.util.ArrayList;
import java.util.List;

import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

import sample.two.HelloItemizedOverlay;

public class googlem extends MapActivity
{
	public static String lon;

	public static String lat;
	LocationManager locationManager;
	HelloItemizedOverlay itemizedoverlay;
	List<Overlay> mapOverlays;

	@SuppressWarnings("unused")
	private ArrayList<OverlayItem> mOverlays = new ArrayList<OverlayItem>();
	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
	    setContentView(R.layout.hellogoogle);

	    MapView mapView = (MapView) findViewById(R.id.mapview);
	    mapView.setBuiltInZoomControls(true);

	    mapOverlays = mapView.getOverlays();
	    Drawable drawable = this.getResources().getDrawable(R.drawable.icon);
	    itemizedoverlay = new HelloItemizedOverlay(drawable, googlem.this);

		locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

	}

	public void onDestroy()
	{
		super.onDestroy();
		locationManager.removeUpdates(locationListener);
	}

	private LocationListener locationListener = new LocationListener() {

		public void onStatusChanged(String provider, int status, Bundle extras) {
			//Toast.makeText(gpsstats.this, "en", Toast.LENGTH_LONG).show();

		}

		public void onProviderEnabled(String provider) {
			// Toast.makeText(gpsstats.this, "en", Toast.LENGTH_LONG).show();
		}

		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub

		}

		public void onLocationChanged(Location location) {

		    GeoPoint point = new GeoPoint((int) (location.getLatitude() * 1e6), (int) (location.getLongitude() * 1e6));
		    OverlayItem overlayitem = new OverlayItem(point, "Dein Standort", "öaöa");

		    if(mapOverlays.size() > 0)
		    {
		    	mapOverlays.remove(0);
		    }

		    itemizedoverlay.removeOverlay();

		    itemizedoverlay.addOverlay(overlayitem);
		    mapOverlays.add(itemizedoverlay);
		}

	};

}
