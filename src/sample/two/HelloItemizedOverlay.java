package sample.two;
w
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.widget.Toast;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

@SuppressWarnings("rawtypes")
public class HelloItemizedOverlay extends ItemizedOverlay {

	private ArrayList<OverlayItem> mOverlays = new ArrayList<OverlayItem>();
	Geocoder geo;
	Context mContext;
	String buildAddress = "";

	public HelloItemizedOverlay(Drawable defaultMarker, Context context) {
		super(boundCenterBottom(defaultMarker));
		mContext = context;
	}

	public void addOverlay(OverlayItem overlay) {
		mOverlays.add(overlay);
		populate();
	}

	@Override
	protected OverlayItem createItem(int i) {
		return mOverlays.get(i);
	}

	@Override
	public int size() {
		return mOverlays.size();
	}

	public void removeOverlay()
	{
		mOverlays.clear();
	}

	protected boolean onTap(int index) {
		OverlayItem item = mOverlays.get(index);
		geo = new Geocoder(mContext);

		try {
			List<Address> addresses;
			addresses = geo.getFromLocation(item.getPoint().getLatitudeE6() / 1e6, item.getPoint().getLongitudeE6() / 1e6, 1);
			if (addresses.size() > 0) {
				buildAddress = "";
				for(int i=0; i<addresses.get(0).getMaxAddressLineIndex();i++)
				{
					buildAddress += addresses.get(0).getAddressLine(i) + "\n";
				}
			}
		} catch (IOException e) {
			Toast.makeText(mContext,"fehler", Toast.LENGTH_SHORT).show();
		}

		AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
		dialog.setTitle("Dein Standort");
		dialog.setMessage(buildAddress);
		dialog.show();
		return true;
	}





}
