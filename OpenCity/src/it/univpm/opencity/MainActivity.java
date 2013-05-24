package it.univpm.opencity;

<<<<<<< HEAD
import java.io.IOException;
import java.io.InputStream;
=======
import it.univpm.opencity.Opdata.Farmacia;
import it.univpm.opencity.dummy.SondaggiContent;
import it.univpm.opencity.dummy.SondaggiContent.DummyItem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
>>>>>>> 92286ab773427861121001619e8409a157f4bca5
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
<<<<<<< HEAD
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
=======

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
>>>>>>> 92286ab773427861121001619e8409a157f4bca5
import org.json.JSONException;
import org.json.JSONObject;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.plus.PlusClient;
import com.google.android.gms.plus.model.people.Person;

<<<<<<< HEAD
import android.os.Bundle;
import android.os.Handler;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
=======
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
>>>>>>> 92286ab773427861121001619e8409a157f4bca5
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
<<<<<<< HEAD
=======
import android.widget.Button;
>>>>>>> 92286ab773427861121001619e8409a157f4bca5
import android.widget.ImageView;
import android.widget.Toast;

import android.content.res.Configuration;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class MainActivity extends FragmentActivity implements
		ConnectionCallbacks, OnConnectionFailedListener, OnClickListener,
		PlusClient.OnPersonLoadedListener,ItemListFragment.Callbacks {
	private static final int REQUEST_CODE_RESOLVE_ERR = 9000;
	private static String userId;

	private static ProgressDialog mConnectionProgressDialog;
	private static PlusClient mPlusClient;
	private ConnectionResult mConnectionResult;
<<<<<<< HEAD
	private boolean mTwoPane;

	private DrawerLayout mDrawerLayout;
	private ListView drawerList;
	private ActionBarDrawerToggle mDrawerToggle;
	private CharSequence title;
	private String[] menuTitles;
	ImageView imgProfilo;
	

	Drawable drawable;
	String imgUrlparsed;
	
	Handler handler = new Handler(){
	    @Override
	    public void handleMessage(android.os.Message msg) {
	    	//imgProfilo.setImageDrawable(drawable);
	        Log.i("System out","after set the image...");
	    }
	};


=======
	ImageView imgProfilo;
>>>>>>> 92286ab773427861121001619e8409a157f4bca5
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
<<<<<<< HEAD
		title = getTitle();
		menuTitles = getResources().getStringArray(R.array.menu_titles);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		drawerList = (ListView) findViewById(R.id.left_drawer);

			mPlusClient = new PlusClient.Builder(this, this, this)
			.setVisibleActivities("http://schemas.google.com/AddActivity",
					"http://schemas.google.com/BuyActivity").build();
	// Progress bar to be displayed if the connection failure is not
	// resolved.
	mConnectionProgressDialog = new ProgressDialog(this);
	mConnectionProgressDialog.setMessage("Signing in...");
	
	//findViewById(R.id.sign_in_button).setOnClickListener(this);
	
	//imgProfilo = (ImageView) findViewById(R.id.imgProfilo);
		
		
		
		final String[] classes = getResources().getStringArray(
				R.array.nav_classes);
=======
		
		
		mPlusClient = new PlusClient.Builder(this, this, this)
				.setVisibleActivities("http://schemas.google.com/AddActivity",
						"http://schemas.google.com/BuyActivity").build();
		// Progress bar to be displayed if the connection failure is not
		// resolved.
		mConnectionProgressDialog = new ProgressDialog(this);
		mConnectionProgressDialog.setMessage("Signing in...");
>>>>>>> 92286ab773427861121001619e8409a157f4bca5

		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
				GravityCompat.START);

<<<<<<< HEAD
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		mDrawerToggle = new ActionBarDrawerToggle(this, /* host Activity */
		mDrawerLayout, /* DrawerLayout object */
		R.drawable.ic_drawer, /* nav drawer image to replace 'Up' caret */
		R.string.drawer_open, /* "open drawer" description for accessibility */
		R.string.drawer_close /* "close drawer" description for accessibility */
		) {
			public void onDrawerClosed(View view) {
				getActionBar().setTitle(title);
				invalidateOptionsMenu(); // creates call to
											// onPrepareOptionsMenu()
			}
=======
		imgProfilo = (ImageView) findViewById(R.id.imgProfilo);
		
		// DA ELIMINARE POI!!
		Button btnGotoCameraActivity = (Button) findViewById(R.id.btnGoToCamera);
		btnGotoCameraActivity.setOnClickListener(new OnClickListener() {
>>>>>>> 92286ab773427861121001619e8409a157f4bca5

			public void onDrawerOpened(View drawerView) {
				// getActionBar().setTitle(mDrawerTitle);
				invalidateOptionsMenu(); // creates call to
											// onPrepareOptionsMenu()
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);

<<<<<<< HEAD
		drawerList.setAdapter(new DrawerAdapter(this,
				R.layout.drawer_list_item, R.id.title, menuTitles));
		drawerList.setOnItemClickListener(new OnItemClickListener() {
=======
		Button btnGotoMapActivity = (Button) findViewById(R.id.btnInviaSegnalazione);
		btnGotoMapActivity.setOnClickListener(new OnClickListener() {
>>>>>>> 92286ab773427861121001619e8409a157f4bca5

			@Override
			public void onItemClick(AdapterView parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				FragmentTransaction tx = getSupportFragmentManager()
						.beginTransaction();
				tx.replace(R.id.content_frame, Fragment.instantiate(
						MainActivity.this, classes[position]));

				drawerList.setItemChecked(position, true);
				// setTitle(mPlanetTitles[position]);
				mDrawerLayout.closeDrawer(drawerList);

				tx.commit();
			}
		});
		
<<<<<<< HEAD
		LayoutInflater inflater=getLayoutInflater();
		View v=inflater.inflate(R.layout.activity_item_twopane, null);
		if (v.findViewById(R.id.item_detail_container) != null) {
			// The detail container view will be present only in the
			// large-screen layouts (res/values-large and
			// res/values-sw600dp). If this view is present, then the
			// activity should be in two-pane mode.
			mTwoPane = true;

			// In two-pane mode, list items should be given the
			// 'activated' state when touched.
			((ItemListFragment) getSupportFragmentManager().findFragmentById(
					R.id.item_list)).setActivateOnItemClick(true);
		}
		//new GetSondaggioTask(this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
=======
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
>>>>>>> 92286ab773427861121001619e8409a157f4bca5

		
		if (savedInstanceState == null) {
			FragmentTransaction tx = getSupportFragmentManager()
					.beginTransaction();
			tx.replace(R.id.content_frame,
					Fragment.instantiate(MainActivity.this, classes[0]));
			tx.commit();
		}
	}
<<<<<<< HEAD

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// The action bar home/up action should open or close the drawer.
		// ActionBarDrawerToggle will take care of this.
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		return super.onOptionsItemSelected(item);

	}

	@Override
	public void setTitle(CharSequence title1) {
		title = title1;
		getActionBar().setTitle(title);
	}

	@Override
=======
	
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
>>>>>>> 92286ab773427861121001619e8409a157f4bca5
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
	protected void onStart() {
		super.onStart();
		// mPlusClient.connect();
	}

	@Override
	protected void onStop() {
		super.onStop();
		// mPlusClient.disconnect();
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
<<<<<<< HEAD

			
			userId = person.getId();
=======
>>>>>>> 92286ab773427861121001619e8409a157f4bca5
			
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
<<<<<<< HEAD
			new Thread(new Runnable() {
		        @Override
		        public void run() {
			HttpClient httpclient = new DefaultHttpClient();
		    HttpPost httppost = new HttpPost("http://www.unifacile.it/napolitano/public/utente");

		    try {
		        // Add your data
		        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(8);
		        nameValuePairs.add(new BasicNameValuePair("nome", "mattia"));
		        nameValuePairs.add(new BasicNameValuePair("cognome", "bugossi"));
		        nameValuePairs.add(new BasicNameValuePair("data_nascita", "1989-03-12"));
		        nameValuePairs.add(new BasicNameValuePair("sesso", "m"));
		        nameValuePairs.add(new BasicNameValuePair("idcitta", "1"));
		        nameValuePairs.add(new BasicNameValuePair("mail", "mattia@gmail.com"));
		        nameValuePairs.add(new BasicNameValuePair("idgoogle", "12345"));
		        nameValuePairs.add(new BasicNameValuePair("immagine", "wqfdwetryhtrewfwrtyjth"));
		        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		        
		        HttpResponse response = httpclient.execute(httppost);
		        HttpEntity entity = response.getEntity();		       
		        // Execute HTTP Post Request
		        userId = "" + EntityUtils.toString(entity);
		       
		        
		        
		        
		        
		    } catch (ClientProtocolException e) {
		        // TODO Auto-generated catch block
		    } catch (IOException e) {
		        // TODO Auto-generated catch block
		    } catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 

		        }
		    }).start();
=======
			
>>>>>>> 92286ab773427861121001619e8409a157f4bca5
			
		
			
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
	
	
<<<<<<< HEAD
 public static String getUserId(){
	 return userId;
 }
=======
>>>>>>> 92286ab773427861121001619e8409a157f4bca5

@Override
public void onItemSelected(String id) {
	// TODO Auto-generated method stub
	
}

	
}
