package it.univpm.opencity;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
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
import org.json.JSONException;
import org.json.JSONObject;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.plus.PlusClient;
import com.google.android.gms.plus.model.people.Person;

import android.os.Bundle;
import android.os.Handler;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
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


	HttpURLConnection urlConnection;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
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

		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
				GravityCompat.START);

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

			public void onDrawerOpened(View drawerView) {
				// getActionBar().setTitle(mDrawerTitle);
				invalidateOptionsMenu(); // creates call to
											// onPrepareOptionsMenu()
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		drawerList.setAdapter(new DrawerAdapter(this,
				R.layout.drawer_list_item, R.id.title, menuTitles));
		drawerList.setOnItemClickListener(new OnItemClickListener() {

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

		
		if (savedInstanceState == null) {
			FragmentTransaction tx = getSupportFragmentManager()
					.beginTransaction();
			tx.replace(R.id.content_frame,
					Fragment.instantiate(MainActivity.this, classes[0]));
			tx.commit();
		}
	}

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

			
			userId = person.getId();
			
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
	
	
 public static String getUserId(){
	 return userId;
 }

@Override
public void onItemSelected(String id) {
	// TODO Auto-generated method stub
	
}

	
}
