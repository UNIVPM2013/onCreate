package it.univpm.opencity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class CameraActivity extends Activity implements LocationListener {
	private static final int CAMERA_PIC_REQUEST = 1337; 
	
	Double latitudine;
	Double longitudine;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_camera);
		
		LocationManager locationManager = (LocationManager) 
				getSystemService(Context.LOCATION_SERVICE);
		
		//String locationProvider = LocationManager.NETWORK_PROVIDER;
		// Or, use GPS location data:
		// String locationProvider = LocationManager.GPS_PROVIDER;
		
		
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
		
		
	}
	
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {  
        if (requestCode == CAMERA_PIC_REQUEST) { 
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");  
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

}