package it.univpm.opencity;

import it.univpm.opencity.Opdata.Farmacia;
import it.univpm.opencity.Opdata.Museo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RadioButton;

public class FragmentMap extends Fragment {
	public GoogleMap mMap;
	public RadioButton radioAll, radioHealth, radioSbirri, radioMusei;
	public GetHealthTask healthTask;
	public GetMuseumTask museumTask;
	public GetPosteTask posteTalk;
	private static View rootView;

	static final CameraPosition ANCONA = new CameraPosition.Builder()
			.target(new LatLng(43.614827, 13.519707)).zoom(10.5f).bearing(0)
			.tilt(0).build();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (rootView != null) {
			ViewGroup parent = (ViewGroup) rootView.getParent();
			if (parent != null)
				parent.removeView(rootView);
		}
		try {
			rootView = inflater.inflate(R.layout.activity_map, container, false);
			
		} catch (InflateException e) {
			/* map is already there, just return view as it is */
		}

		radioAll = (RadioButton) rootView.findViewById(R.id.radioAll);
		radioHealth = (RadioButton) rootView.findViewById(R.id.radioHealt);
		radioMusei = (RadioButton) rootView.findViewById(R.id.radioMusei);
		radioSbirri = (RadioButton) rootView.findViewById(R.id.radioSbirri);
		radioAll.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				radioAll.setChecked(true);
				radioHealth.setChecked(false);
				radioMusei.setChecked(false);
				radioSbirri.setChecked(false);
				mMap.clear();
				posteTalk = new GetPosteTask(getActivity());
				posteTalk.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
				museumTask = new GetMuseumTask(getActivity());
				museumTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
				healthTask = new GetHealthTask(getActivity());
				healthTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
			}
		});
		radioHealth.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				radioAll.setChecked(false);
				radioHealth.setChecked(true);
				radioMusei.setChecked(false);
				radioSbirri.setChecked(false);
				mMap.clear();
				healthTask = new GetHealthTask(getActivity());
				healthTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

			}
		});
		radioMusei.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				radioAll.setChecked(false);
				radioHealth.setChecked(false);
				radioMusei.setChecked(true);
				radioSbirri.setChecked(false);
				mMap.clear();
				museumTask = new GetMuseumTask(getActivity());
				museumTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
			}
		});
		radioSbirri.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				radioAll.setChecked(false);
				radioHealth.setChecked(false);
				radioMusei.setChecked(false);
				radioSbirri.setChecked(true);
				mMap.clear();
				posteTalk = new GetPosteTask(getActivity());
				posteTalk.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
			}
		});
		setUpMapIfNeeded();

		return rootView;
	}

	private void setUpMapIfNeeded() {
		// Do a null check to confirm that we have not already instantiated the
		// map.
		if (mMap == null) {
			// Try to obtain the map from the SupportMapFragment.
			mMap = ((SupportMapFragment) getFragmentManager().findFragmentById(
					R.id.map)).getMap();
			// Check if we were successful in obtaining the map.
			if (mMap != null) {
				setUpMap();
				posteTalk = new GetPosteTask(getActivity());
				posteTalk.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
				museumTask = new GetMuseumTask(getActivity());
				museumTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
				healthTask = new GetHealthTask(getActivity());
				healthTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

			}
		}
	}

	private void setUpMap() {
		mMap.addMarker(new MarkerOptions()
				.position(new LatLng(42.614827, 12.519707))
				.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
						.decodeResource(getResources(), R.drawable.pharma_icon))));
		mMap.addMarker(new MarkerOptions().position(
				new LatLng(44.614827, 14.519707)).icon(
				BitmapDescriptorFactory.fromBitmap(BitmapFactory
						.decodeResource(getResources(),
								R.drawable.hospital_icon))));
		mMap.moveCamera(CameraUpdateFactory.newCameraPosition(ANCONA));
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		posteTalk.cancel(true);
		museumTask.cancel(true);
		healthTask.cancel(true);
		super.onPause();
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		posteTalk.cancel(true);
		museumTask.cancel(true);
		healthTask.cancel(true);
		super.onDestroy();
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
			// Location loc = params[0];
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
			 * Format the first line of address (if available), city, and
			 * country name.
			 */
			String addressText = "";
			// Return the text
			return addressText;

		}

		@Override
		protected void onPostExecute(String address) {
			// Set activity indicator visibility to "gone"
			Log.w("Musei", " " + addresses.size());
			if (addresses != null && addresses.size() > 0) {
				// Get the first address

				for (Museo addres : addresses) {
					mMap.addMarker(new MarkerOptions()
							.title(addres.getNome())
							.position(
									new LatLng(addres.getLat(), addres.getLon()))
							.icon(BitmapDescriptorFactory
									.fromBitmap(BitmapFactory.decodeResource(
											getResources(),
											R.drawable.museo_icon))));

				}
			}

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
			Geocoder geocoder = new Geocoder(mContext, Locale.getDefault());
			// Get the current location from the input parameter list
			// Location loc = params[0];
			// Create a list to contain the result address

			try {

				addresses = geocoder.getFromLocationName(
						"poste italiane ancona", 10);
				caramba = geocoder
						.getFromLocationName("carabinieri ancona", 10);
				polizia = geocoder.getFromLocationName("questura ancona", 5);
				fuoco = geocoder.getFromLocationName(
						"Vigili del Fuoco, Ancona", 5);

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
			 * Format the first line of address (if available), city, and
			 * country name.
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
				for (Address addres : addresses) {
					mMap.addMarker(new MarkerOptions()
							.position(
									new LatLng(addres.getLatitude(), addres
											.getLongitude()))
							.icon(BitmapDescriptorFactory
									.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));

				}
			}
			if (caramba != null && caramba.size() > 0) {
				// Get the first address
				for (Address cara : caramba) {
					mMap.addMarker(new MarkerOptions()
							.position(
									new LatLng(cara.getLatitude(), cara
											.getLongitude()))
							.icon(BitmapDescriptorFactory
									.fromBitmap(BitmapFactory.decodeResource(
											getResources(),
											R.drawable.carabinieri_icon))));

				}
			}

			if (polizia != null && polizia.size() > 0) {
				// Get the first address
				for (Address pol : polizia) {
					mMap.addMarker(new MarkerOptions().position(
							new LatLng(pol.getLatitude(), pol.getLongitude()))
							.icon(BitmapDescriptorFactory
									.fromBitmap(BitmapFactory.decodeResource(
											getResources(),
											R.drawable.polizia_icon))));

				}
			}
			if (fuoco != null && fuoco.size() > 0) {
				// Get the first address
				for (Address fuo : fuoco) {
					mMap.addMarker(new MarkerOptions().position(
							new LatLng(fuo.getLatitude(), fuo.getLongitude()))
							.icon(BitmapDescriptorFactory
									.fromBitmap(BitmapFactory.decodeResource(
											getResources(),
											R.drawable.vigilifuoco_icon))));

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
			Geocoder geocoder = new Geocoder(mContext, Locale.getDefault());
			// Get the current location from the input parameter list
			// Location loc = params[0];
			// Create a list to contain the result address

			try {

				farmacie = Opdata.getFarmacie();
				parafarmacie = Opdata.getParafarmacie();
			} catch (IllegalArgumentException e2) {
				// Error message to post in the log
				String errorString = "Illegal arguments ";
				Log.e("LocationSampleActivity", errorString);
				e2.printStackTrace();
				return errorString;
			}
			// If the reverse geocode returned an address

			/*
			 * Format the first line of address (if available), city, and
			 * country name.
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
				for (Farmacia farm : farmacie) {
					mMap.addMarker(new MarkerOptions()
							.title("Farmacia, " + farm.getIndirizzo())
							.position(new LatLng(farm.getLat(), farm.getLon()))
							.icon(BitmapDescriptorFactory
									.fromBitmap(BitmapFactory.decodeResource(
											getResources(),
											R.drawable.farma_icon))));

				}
			}
			if (parafarmacie != null && parafarmacie.size() > 0) {
				// Get the first address
				for (Farmacia para : parafarmacie) {
					mMap.addMarker(new MarkerOptions()
							.title("Parafarmacia, " + para.getIndirizzo())
							.position(new LatLng(para.getLat(), para.getLon()))
							.icon(BitmapDescriptorFactory
									.fromBitmap(BitmapFactory.decodeResource(
											getResources(),
											R.drawable.para_icon))));

				}
			}
		}
	}
}
