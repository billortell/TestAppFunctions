package sample.two;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.view.View.OnClickListener;

public class startUp extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Button btn = (Button) findViewById(R.id.btn1);
        btn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent myIntent = new Intent(startUp.this, blubb.class);
				startActivity(myIntent);
			}
		});

        Button btnsms = (Button) findViewById(R.id.btnsms);
        btnsms.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent myIntent = new Intent(startUp.this, smsmessage.class);
				startActivity(myIntent);
			}
		});



        TelephonyManager bla = (TelephonyManager) getSystemService(startUp.TELEPHONY_SERVICE);
        String coun = bla.getVoiceMailNumber();
        Toast.makeText(startUp.this, coun, Toast.LENGTH_LONG).show();
    }
}