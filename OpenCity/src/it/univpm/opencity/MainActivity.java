package it.univpm.opencity;

import it.univpm.opencity.Opdata.Farmacia;
import it.univpm.opencity.dummy.SondaggiContent;
import it.univpm.opencity.dummy.SondaggiContent.DummyItem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity implements ConnectionCallbacks,
		OnConnectionFailedListener,OnClickListener,PlusClient.OnPersonLoadedListener {
	private static final int REQUEST_CODE_RESOLVE_ERR = 9000;

	private ProgressDialog mConnectionProgressDialog;
	private PlusClient mPlusClient;
	private ConnectionResult mConnectionResult;
	ImageView imgProfilo;
	HttpURLConnection urlConnection;
	Drawable drawable;
	String imgUrlparsed;
	
	Handler handler = new Handler(){
	    @Override
	    public void handleMessage(android.os.Message msg) {
	    	imgProfilo.setImageDrawable(drawable);
	        Log.i("System out","after set the image...");
	    }
	};
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		mPlusClient = new PlusClient.Builder(this, this, this)
				.setVisibleActivities("http://schemas.google.com/AddActivity",
						"http://schemas.google.com/BuyActivity").build();
		// Progress bar to be displayed if the connection failure is not
		// resolved.
		mConnectionProgressDialog = new ProgressDialog(this);
		mConnectionProgressDialog.setMessage("Signing in...");

		findViewById(R.id.sign_in_button).setOnClickListener(this);

		imgProfilo = (ImageView) findViewById(R.id.imgProfilo);
		
		// DA ELIMINARE POI!!
		Button btnGotoCameraActivity = (Button) findViewById(R.id.btnGoToCamera);
		btnGotoCameraActivity.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this,
						CameraActivity.class);
				startActivity(intent);
			}
		});

		Button btnGotoMapActivity = (Button) findViewById(R.id.btnInviaSegnalazione);
		btnGotoMapActivity.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, MapActivity.class);
				startActivity(intent);
			}
		});

		Button shareButton = (Button) findViewById(R.id.share_button);
		shareButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// Launch the Google+ share dialog with attribution to your app.
				Intent shareIntent = new PlusShare.Builder()
						.setType("text/plain")
						.setText("Welcome to the Google+ platform.")
						.setContentUrl(
								Uri.parse("https://developers.google.com/+/"))
						.getIntent();

				startActivityForResult(shareIntent, 0);
			}
		});
		
		Button gotoSondaggi = (Button) findViewById(R.id.btnSondaggi);
		gotoSondaggi.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// Launch the Google+ share dialog with attribution to your app.
				Intent intent = new Intent(MainActivity.this, ItemListActivity.class);
				startActivity(intent);
			}
		});
		
		Button gotoStoricoOrdini = (Button) findViewById(R.id.btnStoricoOrdini);
		gotoStoricoOrdini.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// Launch the Google+ share dialog with attribution to your app.
				Intent intent = new Intent(MainActivity.this, OrdineListActivity.class);
				startActivity(intent);
			}
		});

		// /
	}
	
	public static InputStream getInputStreamFromUrl(String url) {
		  InputStream content = null;
		  try {
		    HttpClient httpclient = new DefaultHttpClient();
		    HttpResponse response = httpclient.execute(new HttpGet(url));
		    content = response.getEntity().getContent();
		  } catch (Exception e) {
		    //Log.("[GET REQUEST]", "Network exception", e);
		  }
		    return content;
		}
	
	
@Override
	public void onClick(View view) {
		
	
	if (view.getId() == R.id.sign_in_button && !mPlusClient.isConnected()) {
		mConnectionProgressDialog.show();
		mPlusClient.connect();
		
		/*if (mConnectionResult == null) {
				mConnectionProgressDialog.show();
			} else {
				try {
					mConnectionResult.startResolutionForResult(this,
							REQUEST_CODE_RESOLVE_ERR);
				} catch (SendIntentException e) {
					// Try connecting again.
					mConnectionResult = null;
					mPlusClient.connect();
				}		}*/
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

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
	public void onPersonLoaded(ConnectionResult arg0, final Person person) {
		// TODO Auto-generated method stub
		Toast.makeText(this, ""+person.getName(), Toast.LENGTH_LONG).show();
		try {
			JSONObject mainObject = new JSONObject(""+person.getName());
			String cognome = mainObject.getString("familyName");
			Toast.makeText(this, ""+cognome, Toast.LENGTH_LONG).show();
			
			Toast.makeText(this, ""+person.getImage().getUrl(), Toast.LENGTH_LONG).show();
			
			String imgUrl = person.getImage().getUrl();
			String[] imgUrlparse = imgUrl.split("sz=");
			imgUrlparsed = imgUrlparse[0]+"sz=100";
			new Thread(new Runnable() {
		        @Override
		        public void run() {
		            // TODO Auto-generated method stub        	
		        	
		        	
		           drawable = LoadImageFromWebOperations(imgUrlparsed);
		            handler.sendEmptyMessage(0);
		        }
		    }).start();
			
			
		
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}
	
	private Drawable LoadImageFromWebOperations(String url) {
	    try
	      {
	       InputStream is = (InputStream) new URL(url).getContent();
	       Drawable d = Drawable.createFromStream(is, "src name");
	       return d;
	      }catch (Exception e) {
	       System.out.println("Exc="+e);
	       return null;
	      }
	}
	
	

}
