package sample.helper;

import android.content.Context;
import android.media.MediaPlayer;

public class generalhelper {

	protected static Context oContext = null;

	public generalhelper(Context context)
	{
		oContext = context;
	}

	public void playSomeSound(int soundId)
	{
		MediaPlayer mp = MediaPlayer.create(oContext, soundId);
		mp.start();
	}
}
