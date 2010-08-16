package sample.two;


import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class sensortest extends Activity {

	SensorManager sensorManager;
	TextView txtsensx, txtsensy, txtsensz, txtsenaccx, txtsenaccy, txtsenaccz, txtgps = null;

	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sensortest);

		txtsensx = (TextView) findViewById(R.id.txtsensorx);
		txtsensy = (TextView) findViewById(R.id.txtsensory);
		txtsensz = (TextView) findViewById(R.id.txtsensorz);

		txtsenaccx = (TextView) findViewById(R.id.txtsensoraccx);
		txtsenaccy = (TextView) findViewById(R.id.txtsensoraccy);
		txtsenaccz = (TextView) findViewById(R.id.txtsensoraccz);

		txtgps = (TextView) findViewById(R.id.txtgps);

		sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

		sensorManager.registerListener(sensorListener, sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION) , SensorManager.SENSOR_DELAY_NORMAL);
		sensorManager.registerListener(sensorListener, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) , SensorManager.SENSOR_DELAY_NORMAL);
		sensorManager.registerListener(sensorListener, sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE) , SensorManager.SENSOR_DELAY_NORMAL);
	}

	public void onDestroy()
	{
		super.onDestroy();
		sensorManager.unregisterListener(sensorListener);
	}

	private SensorEventListener sensorListener = new SensorEventListener() {

		public void onSensorChanged(SensorEvent event) {

			if(event.sensor.getType() == Sensor.TYPE_ORIENTATION)
			{
				txtsensz.setText(Float.toString(event.values[0]));
				txtsensx.setText(Float.toString(event.values[1]));
				txtsensy.setText(Float.toString(event.values[2]));
			}
			else if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
			{
				txtsenaccz.setText(Float.toString(event.values[0]));
				txtsenaccx.setText(Float.toString(event.values[1]));
				txtsenaccy.setText(Float.toString(event.values[2]));
			}
		}

		public void onAccuracyChanged(Sensor sensor, int accuracy) {
			// TODO Auto-generated method stub

		}
	};
}