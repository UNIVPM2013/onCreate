package it.univpm.opencity;


import it.univpm.opencity.Opdata.Farmacia;
import it.univpm.opencity.Opdata.Museo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import 	com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;

public class MapActivity extends FragmentActivity {
	public GoogleMap mMap ;

	
	
	
    static final CameraPosition ANCONA =
            new CameraPosition.Builder().target(new LatLng(43.614827,13.519707))
                    .zoom(10.5f)
                    .bearing(0)
                    .tilt(0)
                    .build();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);
		setUpMapIfNeeded();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.map, menu);
		return true;
	}
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
                new GetPosteTask(this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                
                new GetMuseumTask(this).execute();
            }
        }
    }
    private void setUpMap() {
        mMap.addMarker(new MarkerOptions().position(new LatLng(43.614827,13.519707)).title("Ancona"));
        mMap.addMarker(new MarkerOptions().position(new LatLng(42.614827,12.519707)).icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.pharma_icon))));
        mMap.addMarker(new MarkerOptions().position(new LatLng(44.614827,14.519707)).icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.hospital_icon))));
        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(ANCONA));
    }
    private class GetMuseumTask extends AsyncTask<Location, Void, String> {
    	Context mContext;
    	ArrayList<Museo> addresses = null;
    	public GetMuseumTask(Context context) {
    	    super();
    	    mContext = context;
    	}
    	
    	
    	@Override
    	protected String doInBackground(Location... params) {
    	 
    	    // Get the current location from the input parameter list
//    	    Location loc = params[0];
    	    // Create a list to contain the result address
    	    
    	    try {

    	        addresses = Opdata.getMusei();

    	    } catch (IllegalArgumentException e2) {
    	    // Error message to post in the log
    	    String errorString = "Illegal arguments ";
    	    Log.e("LocationSampleActivity", errorString);
    	    e2.printStackTrace();
    	    return errorString;
    	    }
    	    // If the reverse geocode returned an address

    	        /*
    	         * Format the first line of address (if available),
    	         * city, and country name.
    	         */
    	        String addressText = "";
    	        // Return the text
    	        return addressText;

    	}
        @Override
        protected void onPostExecute(String address) {
            // Set activity indicator visibility to "gone"
        	Log.w("Musei"," "+addresses.size());
    	    if (addresses != null && addresses.size() > 0) {
    	        // Get the first address
    	    	
    	        for (Museo addres: addresses){
    	            mMap.addMarker(new MarkerOptions().title(addres.getNome()).position(new LatLng(addres.getLat(),addres.getLon())).icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.museo_icon))));

    	        }	   
    	    }
    	    new GetHealthTask(MapActivity.this).execute();
 
        }
    }
    private class GetPosteTask extends AsyncTask<Location, Void, String> {
	Context mContext;
	List<Address> addresses = null;
	List<Address> caramba = null;
	List<Address> polizia = null;
	List<Address> fuoco = null;
	public GetPosteTask(Context context) {
	    super();
	    mContext = context;
	}
	
	
	@Override
	protected String doInBackground(Location... params) {
	    Geocoder geocoder =
	            new Geocoder(mContext, Locale.getDefault());
	    // Get the current location from the input parameter list
//	    Location loc = params[0];
	    // Create a list to contain the result address
	    
	    try {

	        addresses = geocoder.getFromLocationName("poste italiane ancona", 10);
	        caramba = geocoder.getFromLocationName("carabinieri ancona", 10);
	        polizia = geocoder.getFromLocationName("questura ancona", 5);
	        fuoco = geocoder.getFromLocationName("Vigili del Fuoco, Ancona", 5);

	    } catch (IOException e1) {
	    Log.e("LocationSampleActivity",
	            "IO Exception in getFromLocation()");
	    e1.printStackTrace();
	    return ("IO Exception trying to get address");
	    } catch (IllegalArgumentException e2) {
	    // Error message to post in the log
	    String errorString = "Illegal arguments ";
	    Log.e("LocationSampleActivity", errorString);
	    e2.printStackTrace();
	    return errorString;
	    }
	    // If the reverse geocode returned an address

	        /*
	         * Format the first line of address (if available),
	         * city, and country name.
	         */
	        String addressText = "";
	        // Return the text
	        return addressText;

	}
    @Override
    protected void onPostExecute(String address) {
        // Set activity indicator visibility to "gone"
	    if (addresses != null && addresses.size() > 0) {
	        // Get the first address
	        for (Address addres: addresses){
	            mMap.addMarker(new MarkerOptions().title("Ufficio Postale, "+addres.getThoroughfare()).position(new LatLng(addres.getLatitude(),addres.getLongitude())).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));

	        }	   
	    } 
	    if (caramba != null && caramba.size() > 0) {
	        // Get the first address
	        for (Address cara: caramba){
	            mMap.addMarker(new MarkerOptions().title("Carabinieri, "+cara.getThoroughfare()).position(new LatLng(cara.getLatitude(),cara.getLongitude())).icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.carabinieri_icon))));

	        }	   
	    } 

	    if (polizia != null && polizia.size() > 0) {
	        // Get the first address
	        for (Address pol: polizia){
	            mMap.addMarker(new MarkerOptions().title("Polizia Di Stato, "+pol.getThoroughfare()).position(new LatLng(pol.getLatitude(),pol.getLongitude())).icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.polizia_icon))));

	        }	   
	    } 
	    if (fuoco != null && fuoco.size() > 0) {
	        // Get the first address
	        for (Address fuo: fuoco){
	            mMap.addMarker(new MarkerOptions().title("Vigili del fuoco, "+fuo.getThoroughfare()).position(new LatLng(fuo.getLatitude(),fuo.getLongitude())).icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.vigilifuoco_icon))));

	        }	   
	    }
    }
}
    private class GetHealthTask extends AsyncTask<Location, Void, String> {
	Context mContext;
	List<Address> addresses = null;
	List<Address> caramba = null;
	public ArrayList<Farmacia> farmacie = null;
	public ArrayList<Farmacia> parafarmacie = null;
	List<Address> polizia = null;
	public GetHealthTask(Context context) {
	    super();
	    mContext = context;
	}
	
	
	@Override
	protected String doInBackground(Location... params) {
	    Geocoder geocoder =
	            new Geocoder(mContext, Locale.getDefault());
	    // Get the current location from the input parameter list
//	    Location loc = params[0];
	    // Create a list to contain the result address
	    
	    try {

	        farmacie = Opdata.getFarmacie();
	        parafarmacie  = Opdata.getParafarmacie();
	    }catch (IllegalArgumentException e2) {
	    // Error message to post in the log
	    String errorString = "Illegal arguments ";
	    Log.e("LocationSampleActivity", errorString);
	    e2.printStackTrace();
	    return errorString;
	    }
	    // If the reverse geocode returned an address

	        /*
	         * Format the first line of address (if available),
	         * city, and country name.
	         */
	        String addressText = "";
	        // Return the text
	        return addressText;

	}
    @Override
    protected void onPostExecute(String address) {
        // Set activity indicator visibility to "gone"

	    if (farmacie != null && farmacie.size() > 0) {
	        // Get the first address
	        for (Farmacia farm: farmacie){
	            mMap.addMarker(new MarkerOptions().title("Farmacia, "+farm.getIndirizzo()).position(new LatLng(farm.getLat(),farm.getLon())).icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.farma_icon))));

	        }	   
	    }
	    if (parafarmacie != null && parafarmacie.size() > 0) {
	        // Get the first address
	        for (Farmacia para: parafarmacie){
	            mMap.addMarker(new MarkerOptions().title("Parafarmacia, "+para.getIndirizzo()).position(new LatLng(para.getLat(),para.getLon())).icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.para_icon))));

	        }	   
	    }
    }
}


}
