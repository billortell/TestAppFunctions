
package sample.two;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.view.View.OnClickListener;

public class startUp extends Activity {
    /** Called when the activity is first created. */

    public String we = "lala";
    PhoneStateListener phone = new PhoneStateListener(){
        @Override
        public void onCallStateChanged(int state, String incomingNumber)
        {
            if(state == TelephonyManager.CALL_STATE_RINGING)
            {
                Toast.makeText(startUp.this, incomingNumber, Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        TelephonyManager tm =
            (TelephonyManager)this.getSystemService(Context.TELEPHONY_SERVICE);
        tm.listen(phone, PhoneStateListener.LISTEN_CALL_STATE);

        Button btn = (Button)findViewById(R.id.btn1);
        btn.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(startUp.this, blubb.class);
                startActivity(myIntent);
            }
        });

        Button btnsms = (Button)findViewById(R.id.btnsms);
        btnsms.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(startUp.this, smsmessage.class);
                startActivity(myIntent);
            }
        });

        Button btnsensors = (Button)findViewById(R.id.btnsensor);
        btnsensors.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(startUp.this, sensortest.class);
                startActivity(myIntent);
            }
        });

        Button btngps = (Button)findViewById(R.id.btngps);
        btngps.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(startUp.this, gpsstats.class);
                startActivity(myIntent);
            }
        });

        Button btngoogle = (Button)findViewById(R.id.btngoogle);
        btngoogle.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(startUp.this, googlem.class);
                startActivity(myIntent);
            }
        });

        TelephonyManager bla = (TelephonyManager)getSystemService(startUp.TELEPHONY_SERVICE);
        @SuppressWarnings("unused")
        String coun = bla.getVoiceMailNumber();
        // Toast.makeText(startUp.this, "Deine Mailbox: " + coun,
        // Toast.LENGTH_LONG).show();
    }

    public void onResume() {
        super.onResume();

    }

}
