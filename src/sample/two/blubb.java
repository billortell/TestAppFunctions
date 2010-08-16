package sample.two;
ss
import android.app.Activity;
import android.app.WallpaperManager;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.view.View.OnClickListener;

public class blubb extends Activity {

    final static private int[] mColors =
    {Color.BLUE, Color.GREEN, Color.RED, Color.LTGRAY, Color.MAGENTA, Color.CYAN,
            Color.YELLOW, Color.WHITE};

	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.forblubb);

		final WallpaperManager wallpaper = WallpaperManager.getInstance(this);
		final Drawable drawwallaper = wallpaper.getDrawable();
		final ImageView img = (ImageView) findViewById(R.id.img1);

		img.setDrawingCacheEnabled(true);
		img.setImageDrawable(drawwallaper);

		img.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				int mColor = (int) Math.floor(Math.random() * mColors.length);
				drawwallaper.setColorFilter(mColors[mColor], PorterDuff.Mode.MULTIPLY);
				img.setImageDrawable(drawwallaper);
				img.invalidate();
			}
		});

	}
}
