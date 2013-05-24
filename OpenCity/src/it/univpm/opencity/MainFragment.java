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

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

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



public class MainFragment extends Fragment implements ConnectionCallbacks,
OnConnectionFailedListener,PlusClient.OnPersonLoadedListener {
	public static final String ITEM_MENU = "item_menu";
	
	private WebView WV;
	private String url = "http://www.comune.ancona.it/binaries/repository/ankonline/consiglio-comunale/lavori-del-consiglio/anno-2012/odg_consiglio_com_16012012.pdf";
	
	private static final int REQUEST_CODE_RESOLVE_ERR = 9000;

	private ProgressDialog mConnectionProgressDialog;
	private PlusClient mPlusClient;
	private ConnectionResult mConnectionResult;
	
	private static String userId;
	
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
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		

		
		View rootView = inflater.inflate(R.layout.fragment_layout,
				container, false);
		
		WV = (WebView)rootView.findViewById(R.id.webViewPDF);
		WV.getSettings().setJavaScriptEnabled(true);
		WV.getSettings().setBuiltInZoomControls(true);
		WV.setWebViewClient(new MyWebViewClient());		
		WV.loadUrl("https://docs.google.com/gview?embedded=true&url=" +url);
		//pb.setVisibility(View.GONE);

		
		rootView.findViewById(R.id.sign_in_button).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {	
				
				// Creiamo l'Intent				
					//mConnectionProgressDialog.show();
					//mPlusClient.connect();
				Toast.makeText(getActivity(), "Utente Connesso", Toast.LENGTH_SHORT).show();
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
			
		});
		
		
		
		
		Button btnStoricoOrdini = (Button)rootView.findViewById(R.id.btnStoricoOrdini);
		btnStoricoOrdini.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				// Creiamo l'Intent
				/*Intent intent = new Intent(MainActivity.this,
						OrdineGiornoListActivity.class);
				// La lanciamo
				startActivity(intent);*/
			}
		});
		
		

		return rootView;
	}
	
	
	

	

	@Override
	public void onActivityResult(int requestCode, int responseCode,
			Intent intent) {
		//if (requestCode == REQUEST_CODE_RESOLVE_ERR
			//	&& responseCode == RESULT_OK) {
			mConnectionResult = null;
			mPlusClient.connect();
		//}
	}

	@Override
	public void onConnected() {
		String accountName = mPlusClient.getAccountName();
		Toast.makeText(getActivity(), accountName + " is connected.", Toast.LENGTH_LONG)
				.show();
		//mConnectionProgressDialog.dismiss();
		
		mPlusClient.loadPerson(this, "me");
		
		

	}

	
	@Override
	public void onDisconnected() {
		Log.d("opencity", "disconnected");
	}
	@Override
	public void onPersonLoaded(ConnectionResult arg0, final Person person) {
		// TODO Auto-generated method stub
		
		try {
			JSONObject mainObject = new JSONObject(""+person.getName());
			String cognome = mainObject.getString("familyName");
			Toast.makeText(getActivity(), ""+cognome, Toast.LENGTH_LONG).show();
			
			
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

	
	private class MyWebViewClient extends WebViewClient {
		
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			// TODO Auto-generated method stub
			view.loadUrl(url);
			return true;
		}
	}

	

	/*@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}*/

	@Override
	public void onConnectionFailed(ConnectionResult arg0) {
		// TODO Auto-generated method stub
		
	}

	

	
	
	
}
