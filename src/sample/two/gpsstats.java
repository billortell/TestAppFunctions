package sample.two;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.GpsStatus;
import android.location.GpsStatus.Listener;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class gpsstats extends Activity {

	LocationManager locationManager;
	TextView longitude, altitude, latitude, geschw = null;
	Location loc;
	Double lon,lat,alt = null;
	Geocoder geo;
	Button btnaddr, btnggl;

	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gpsstats);
		geo =  new Geocoder(gpsstats.this, Locale.GERMANY);

		btnaddr = (Button) findViewById(R.id.btnadress);
		btnaddr.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					List<Address> addresses;
					addresses = geo.getFromLocation(lat, lon, 1);
					if(addresses.size() > 0)
					{
						String buildAddress = addresses.get(0).getLocality() + " " + addresses.get(0).getThoroughfare();
						Toast.makeText(gpsstats.this, buildAddress, Toast.LENGTH_LONG).show();
					}
				} catch (IOException e) {
					Toast.makeText(gpsstats.this, "Fehler bei der Abfrage... Versuchs nochmal.", Toast.LENGTH_SHORT).show();
				}



			}
		});

		btnggl = (Button) findViewById(R.id.btngglmaps);
		btnggl.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent myIntent = new Intent(gpsstats.this, googlem.class);
				startActivity(myIntent);
			}
		});

		btnaddr.setEnabled(false);

		longitude = (TextView) findViewById(R.id.txtlongitude);
		altitude = (TextView) findViewById(R.id.txtaltitude);
		latitude = (TextView) findViewById(R.id.txtlatitude);
		geschw = (TextView) findViewById(R.id.txtgeschw);

		locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

		locationManager.addGpsStatusListener(gpsstatus);
	}

	public void onDestroy() {
		super.onDestroy();
		locationManager.removeUpdates(locationListener);
		locationManager.removeGpsStatusListener(gpsstatus);
	}

	private Listener gpsstatus = new GpsStatus.Listener() {

		public void onGpsStatusChanged(int event) {
			if(event == GpsStatus.GPS_EVENT_FIRST_FIX)
			{
				GpsStatus gpsstate = (GpsStatus) locationManager.getGpsStatus(null);

				float bla = ((float) gpsstate.getTimeToFirstFix() / 1000);

				Toast.makeText(gpsstats.this, "Verbunden mit Sateliet... ("
						+ Float.toString(bla) + ") Sekunden", Toast.LENGTH_LONG).show();

			}

		}
	};
	private LocationListener locationListener = new LocationListener() {

		public void onStatusChanged(String provider, int status, Bundle extras) {
			Toast.makeText(gpsstats.this, "en", Toast.LENGTH_LONG).show();

		}

		public void onProviderEnabled(String provider) {
			// Toast.makeText(gpsstats.this, "en", Toast.LENGTH_LONG).show();
		}

		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub

		}

		public void onLocationChanged(Location location) {

			if(!btnaddr.isEnabled())
			{
				btnaddr.setEnabled(true);
			}

			float speed = location.getSpeed();

			lon = location.getLongitude();
			alt = location.getAltitude();
			lat = location.getLatitude();

			longitude.setText(Double.toString(lon));
			altitude.setText(Double.toString(alt));
			latitude.setText(Double.toString(lat));
			geschw.setText(Float.toString(location.getSpeed()) + " m/s (" + Float.toString((float) (speed / 0.278)) + " km/h)");
		}
	};
}
