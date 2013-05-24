package it.univpm.opencity;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.google.android.gms.maps.model.MarkerOptions;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextUtils.TruncateAt;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.StringBody;

public class CameraActivity extends Activity implements LocationListener {
	private static final int CAMERA_PIC_REQUEST = 1337; 
	
	Double latitudine;
	Double longitudine;
	Bitmap thumbnail;
	int id_organo=1;
	private EditText segnalazione;

	private RadioButton radioAdmin;

	private RadioButton radioMuni;

	private RadioButton radioPublic;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_camera);
		
		LocationManager locationManager = (LocationManager) 
				getSystemService(Context.LOCATION_SERVICE);
		
		//String locationProvider = LocationManager.NETWORK_PROVIDER;
		// Or, use GPS location data:
		// String locationProvider = LocationManager.GPS_PROVIDER;
		radioAdmin = (RadioButton)findViewById(R.id.radio0);
		radioMuni = (RadioButton)findViewById(R.id.radio1);
		radioPublic = (RadioButton)findViewById(R.id.radio2);
		radioAdmin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				radioAdmin.setChecked(true);
				radioMuni.setChecked(false);
				radioPublic.setChecked(false);
				id_organo=1;
			}
		});
		radioMuni.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				radioAdmin.setChecked(false);
				radioMuni.setChecked(true);
				radioPublic.setChecked(false);
				id_organo=2;
			}
		});
		radioPublic.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				radioAdmin.setChecked(false);
				radioMuni.setChecked(false);
				radioPublic.setChecked(true);
				id_organo=3;
			}
		});
		segnalazione= (EditText)findViewById(R.id.editSegnalazione);
		LocationListener locationListener = new LocationListener() {
		    public void onLocationChanged(Location location) {
		      // Called when a new location is found by the network location provider.
		    	latitudine = location.getLatitude();
		    	longitudine = location.getLongitude();
		    }

			@Override
			public void onProviderDisabled(String provider) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onProviderEnabled(String provider) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onStatusChanged(String provider, int status,
					Bundle extras) {
				// TODO Auto-generated method stub
				
			}
		};

		locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);		
		
		Button btnScatta = (Button) findViewById(R.id.btnScatta);
		btnScatta.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {		
				Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE); 
				startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST); 
			}
		});
		Button btnInvia =(Button)findViewById(R.id.btnInviaSegnalazione);
		btnInvia.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new PutSegnalazioni().execute();
			}
		});
		
		
	}
	
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {  
        if (requestCode == CAMERA_PIC_REQUEST) { 
            thumbnail = (Bitmap) data.getExtras().get("data");  
            ImageView image = (ImageView) findViewById(R.id.photoResultView);  
            image.setImageBitmap(thumbnail);  
       
        }  
    }  

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}

public class PutSegnalazioni extends AsyncTask<Void, Void, String>{
	String risposta;
	Bitmap bm;
	@Override
	protected String doInBackground(Void... params) {
		// TODO Auto-generated method stub
		HttpClient httpclient = new DefaultHttpClient();
		bm = BitmapFactory.decodeResource(getResources(), R.drawable.default_image);
	    HttpPost httppost = new HttpPost("http://www.unifacile.it/napolitano/public/segnalazione");

	    try {
	        // Add your data
	    	ByteArrayBody bab ;
	    	if(thumbnail!=null){
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            thumbnail.compress(CompressFormat.JPEG, 75, bos);
	    	byte[] data = bos.toByteArray();
//	        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(8);
//	        nameValuePairs.add(new BasicNameValuePair("idutente", "00000000007"));
//	        nameValuePairs.add(new BasicNameValuePair("idorgano", "3"));
//	        nameValuePairs.add(new BasicNameValuePair("descrizione",segnalazione.getText().toString() ));
//	        nameValuePairs.add(new BasicNameValuePair("latitudine", Double.toString(latitudine)));
//	        nameValuePairs.add(new BasicNameValuePair("longitudine", Double.toString(longitudine)));
//	        nameValuePairs.add(new BasicNameValuePair("image", "provaprovaprova"));
            bab = new ByteArrayBody(data, "forest.jpg");
            // File file= new File("/mnt/sdcard/forest.png");
            // FileBody bin = new FileBody(file);
            MultipartEntity reqEntity = new MultipartEntity(
                    HttpMultipartMode.BROWSER_COMPATIBLE);
            reqEntity.addPart("idutente",new StringBody( "00000000001"));
            reqEntity.addPart("idorgano",new StringBody( Integer.toString(id_organo)));
            reqEntity.addPart("descrizione",new StringBody( segnalazione.getText().toString() ));
            reqEntity.addPart("latitudine",new StringBody( Double.toString(latitudine)));
            reqEntity.addPart("longitudine",new StringBody( Double.toString(longitudine)));
            reqEntity.addPart("image", bab);
	        httppost.setEntity(reqEntity);
	    	}else{ 
	        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(8);
	        nameValuePairs.add(new BasicNameValuePair("idutente", "00000000001"));
	        nameValuePairs.add(new BasicNameValuePair("idorgano", Integer.toString(id_organo)));
	        nameValuePairs.add(new BasicNameValuePair("descrizione",segnalazione.getText().toString() ));
	        nameValuePairs.add(new BasicNameValuePair("latitudine", Double.toString(latitudine)));
	        nameValuePairs.add(new BasicNameValuePair("longitudine", Double.toString(longitudine)));
	        nameValuePairs.add(new BasicNameValuePair("image", null));
	        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
	    	}
	        HttpResponse response = httpclient.execute(httppost);
	        HttpEntity entity = response.getEntity();
	        risposta = EntityUtils.toString(entity);
	        Log.e("PROVA",""+risposta);
	        // Execute HTTP Post Request
	       
	       
	        
	        
	        
	        
	    } catch (ClientProtocolException e) {
	        // TODO Auto-generated catch block
	    } catch (IOException e) {
	        // TODO Auto-generated catch block
	    } catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

	        

		return risposta;
		
	}
    @Override
    protected void onPostExecute(String response) {
    	Log.w("TAG",response);
    	if(response.contains("\"response\":1")){
    		Toast.makeText(getApplicationContext(), "La segnalazione è stata inviata", Toast.LENGTH_LONG).show();
    		segnalazione.setText("");
    		
    	}else{
    		Toast.makeText(getApplicationContext(), "La segnalazione è fallita", Toast.LENGTH_LONG).show();

    	}

    }
	
}
}