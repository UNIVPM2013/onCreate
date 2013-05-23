package it.univpm.opencity;

<<<<<<< HEAD
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.UserRecoverableAuthException;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.plus.PlusClient;
import com.google.android.gms.plus.PlusClient.Builder;
import com.google.android.gms.plus.PlusClient.OnPersonLoadedListener;
import com.google.android.gms.plus.PlusShare;
import com.google.android.gms.plus.model.people.Person;
=======
>>>>>>> a3f419cb647a18a0a4501926b573895d9065ab40


import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
<<<<<<< HEAD
import android.widget.Toast;

public class MainActivity extends Activity implements ConnectionCallbacks,
		OnConnectionFailedListener,OnClickListener,PlusClient.OnPersonLoadedListener {
	private static final int REQUEST_CODE_RESOLVE_ERR = 9000;

	private ProgressDialog mConnectionProgressDialog;
	private PlusClient mPlusClient;
	private ConnectionResult mConnectionResult;
	
	HttpURLConnection urlConnection;
=======

public class MainActivity extends Activity {
>>>>>>> a3f419cb647a18a0a4501926b573895d9065ab40

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//DA ELIMINARE POI!!
		Button btnGotoCameraActivity = (Button) findViewById(R.id.btnGoToCamera);
		btnGotoCameraActivity.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {		
				Intent intent = new Intent(MainActivity.this,
						CameraActivity.class);
				startActivity(intent);
			}
		});
		
		Button btnGotoMapActivity = (Button) findViewById(R.id.btnGoToMap);
		btnGotoMapActivity.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {		
				Intent intent = new Intent(MainActivity.this,
						MapActivity.class);
				startActivity(intent);
			}
		});
		///
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

<<<<<<< HEAD
	@Override
	protected void onStart() {
		super.onStart();
		//mPlusClient.connect();
	}

	@Override
	protected void onStop() {
		super.onStop();
		mPlusClient.disconnect();
	}

	@Override
	public void onConnectionFailed(ConnectionResult result) {
		if (result.hasResolution()) {
			try {
				result.startResolutionForResult(this, REQUEST_CODE_RESOLVE_ERR);
			} catch (SendIntentException e) {
				mPlusClient.connect();
			}
		}
		// Save the result and resolve the connection failure upon a user click.
		mConnectionResult = result;
	}

	@Override
	protected void onActivityResult(int requestCode, int responseCode,
			Intent intent) {
		if (requestCode == REQUEST_CODE_RESOLVE_ERR
				&& responseCode == RESULT_OK) {
			mConnectionResult = null;
			mPlusClient.connect();
		}
	}

	@Override
	public void onConnected() {
		String accountName = mPlusClient.getAccountName();
		Toast.makeText(this, accountName + " is connected.", Toast.LENGTH_LONG)
				.show();
		mConnectionProgressDialog.dismiss();
		Toast.makeText(this, "User is connected!", Toast.LENGTH_LONG).show();
		
		
		mPlusClient.loadPerson(this, "me");
		
		

	}

	
	@Override
	public void onDisconnected() {
		Log.d("opencity", "disconnected");
	}
	@Override
	public void onPersonLoaded(ConnectionResult arg0, Person arg1) {
		// TODO Auto-generated method stub
		
	}

=======
>>>>>>> a3f419cb647a18a0a4501926b573895d9065ab40
}
