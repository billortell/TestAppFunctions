package sample.two;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;
import sample.helper.generalhelper;

public class smsmessage extends Activity {

	public static final String ACTION_SMS_SENT = "sample.one.SMS_SENT_ACTION";
	public static final Integer PICK_CONTACT = 1;


	public static generalhelper oHelper = null;

	public void onCreate(Bundle SavedInstanceState) {
		super.onCreate(SavedInstanceState);
		setContentView(R.layout.smsmessage);


		// Den Empfänger und die Nachricht holen
		final EditText oEmpf = (EditText) findViewById(R.id.txtEmpf);
		final EditText oMessage = (EditText) findViewById(R.id.txtMessage);
		final TextView oState = (TextView) findViewById(R.id.txtstate);

		oEmpf.setText("Hier klicken für Kontake.");
		// Helper initialisieren
		oHelper = new generalhelper(smsmessage.this);

		Button btnSend = (Button) findViewById(R.id.btnsend);
		btnSend.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				if (TextUtils.isEmpty(oEmpf.getText())) {
					Toast.makeText(smsmessage.this,
							"Kein Empfänger angegeben!", Toast.LENGTH_SHORT)
							.show();
					return;
				}

				if (TextUtils.isEmpty(oMessage.getText())) {
					Toast.makeText(smsmessage.this,
							"Keine Nachricht eingegeben!", Toast.LENGTH_SHORT)
							.show();
					return;
				}

				oState.setText("Sende SMS...");

				SmsManager sms = SmsManager.getDefault();

				oEmpf.setEnabled(false);
				oMessage.setEnabled(false);

				String sEmpf = oEmpf.getText().toString();
				String sMessage = oMessage.getText().toString();

				List<String> messages = sms.divideMessage(sMessage);

				for (String message : messages) {
					sms.sendTextMessage(sEmpf, null, message, PendingIntent
							.getBroadcast(smsmessage.this, 0, new Intent(
									ACTION_SMS_SENT), 0), null);
				}
			}
		});

		oEmpf.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_PICK,
						ContactsContract.Contacts.CONTENT_URI);
				startActivityForResult(intent, PICK_CONTACT);
			}
		});

		// Register broadcast receivers for SMS sent and delivered intents
		registerReceiver(new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				String message = null;
				boolean error = true;
				switch (getResultCode()) {
				case Activity.RESULT_OK:
					message = "Nachricht verschickt.";
					error = false;
					break;
				case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
					message = "Fehler beim senden.";
					break;
				case SmsManager.RESULT_ERROR_NO_SERVICE:
					message = "Fehler beim senden.";
					break;
				case SmsManager.RESULT_ERROR_NULL_PDU:
					message = "Fehler beim senden.";
					break;
				case SmsManager.RESULT_ERROR_RADIO_OFF:
					message = "Fehler beim senden.";
					break;
				}

				oEmpf.setEnabled(true);
				oMessage.setEnabled(true);

				oState.setText(message);

				if (!error) {
					oState.setTextColor(Color.GREEN);
					oHelper.playSomeSound(R.raw.smssent);
				} else {
					oState.setTextColor(Color.RED);
					oHelper.playSomeSound(R.raw.smsfail);
				}
			}
		}, new IntentFilter(ACTION_SMS_SENT));


	}

	public void onActivityResult(int reqCode, int resultCode, Intent data) {
		super.onActivityResult(reqCode, resultCode, data);
		switch (reqCode) {
		case 1:
			ContentResolver cr = getContentResolver();
			Cursor cur = cr.query(data.getData(), null, null, null, null);

			if (cur.getCount() > 0) {
				while (cur.moveToNext()) {
					String contactID = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));

					Integer quatityPhones = Integer.parseInt(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)));

					if (quatityPhones > 0)
					{
						ArrayList<String> collectedPhones = new ArrayList<String>();
						Cursor phone = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactID, null, null);
						while(phone.moveToNext())
						{
							String phoneNumber = phone.getString(phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
							collectedPhones.add(phoneNumber);

						}
						final String[] num = new String[collectedPhones.size()];
						for(int a=0;a<collectedPhones.size();a++)
						{
							num[a] = collectedPhones.get(a);
						}
						phone.close();
						showPhoneNumbers(num);
					}
				}
			}
			cur.close();
			break;
		}

	}



	protected void showPhoneNumbers(final String[] numbers)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Bitte wählen Sie eine Nummer");
		builder.setItems(numbers, new DialogInterface.OnClickListener() {
		    public void onClick(DialogInterface dialog, int item) {
		        EditText oEmpf = (EditText) findViewById(R.id.txtEmpf);
		        oEmpf.setText(numbers[item]);
		    }
		});
		AlertDialog alert = builder.create();
		alert.show();
	}

}