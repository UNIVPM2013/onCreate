package it.univpm.opencity;

import java.io.IOException;
import java.util.ArrayList;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v4.app.Fragment;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextUtils.TruncateAt;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.RadioButton;

public class SegnalazioniFragment extends Fragment {
	public GoogleMap mMap;
	public RadioButton radioPublic, radioAdmin, radioMunic;
	public ArrayList<Segnalazione> segnalazioni = null;
	public GetAdminSegnalazioni adminTask;
	public GetMuniSegnalazioni muniTask;
	public GetPublicSegnalazioni publTask;
	public GetSegnalazioni allTsk;
	private RadioButton radioAll;
	private View rootView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		if (rootView != null) {
			ViewGroup parent = (ViewGroup) rootView.getParent();
			if (parent != null)
				parent.removeView(rootView);
		}
		try {
			rootView = inflater.inflate(R.layout.activity_segnala, container,
					false);
		} catch (InflateException e) {
			/* map is already there, just return view as it is */
		}

		radioAll = (RadioButton) rootView.findViewById(R.id.radioAllSegnala);
		radioAdmin = (RadioButton) rootView.findViewById(R.id.radioAmmin);
		radioMunic = (RadioButton) rootView.findViewById(R.id.radioMunicipale);
		radioPublic = (RadioButton) rootView.findViewById(R.id.radioPublic);
		radioAll.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				radioAll.setChecked(true);
				radioAdmin.setChecked(false);
				radioMunic.setChecked(false);
				radioPublic.setChecked(false);
				mMap.clear();
				if (allTsk != null)
					allTsk.cancel(true);
				if (muniTask != null)
					muniTask.cancel(true);
				if (publTask != null)
					publTask.cancel(true);
				if (adminTask != null)
					adminTask.cancel(true);
				allTsk = new GetSegnalazioni();
				allTsk.execute();

			}
		});
		radioAdmin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				radioAll.setChecked(false);
				radioAdmin.setChecked(true);
				radioMunic.setChecked(false);
				radioPublic.setChecked(false);
				mMap.clear();
				if (allTsk != null)
					allTsk.cancel(true);
				if (muniTask != null)
					muniTask.cancel(true);
				if (publTask != null)
					publTask.cancel(true);
				if (adminTask != null)
					adminTask.cancel(true);
				adminTask = new GetAdminSegnalazioni();
				adminTask.execute();

			}
		});
		radioMunic.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				radioAll.setChecked(false);
				radioAdmin.setChecked(false);
				radioMunic.setChecked(true);
				radioPublic.setChecked(false);
				mMap.clear();
				if (allTsk != null)
					allTsk.cancel(true);
				if (muniTask != null)
					muniTask.cancel(true);
				if (publTask != null)
					publTask.cancel(true);
				if (adminTask != null)
					adminTask.cancel(true);
				muniTask = new GetMuniSegnalazioni();
				muniTask.execute();

			}
		});
		radioPublic.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				radioAll.setChecked(false);
				radioAdmin.setChecked(false);
				radioMunic.setChecked(false);
				radioPublic.setChecked(true);
				mMap.clear();
				if (allTsk != null)
					allTsk.cancel(true);
				if (muniTask != null)
					muniTask.cancel(true);
				if (publTask != null)
					publTask.cancel(true);
				if (adminTask != null)
					adminTask.cancel(true);
				publTask = new GetPublicSegnalazioni();
				publTask.execute();
			}
		});
		setUpMapIfNeeded();
		setHasOptionsMenu(true);

		return rootView;
	}

	static final CameraPosition ANCONA = new CameraPosition.Builder()
			.target(new LatLng(43.614827, 13.519707)).zoom(10.5f).bearing(0)
			.tilt(0).build();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		new GetSegnalazioni().execute();

	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// TODO Auto-generated method stub
		inflater.inflate(R.menu.menu_segnalazioni, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		if (item.getItemId() == R.id.nuova_segn) {

			Intent intent = new Intent(getActivity(), CameraActivity.class);
			startActivity(intent);

		}
		return true;
	}

	private void setUpMapIfNeeded() {
		// Do a null check to confirm that we have not already instantiated the
		// map.
		if (mMap == null) {
			// Try to obtain the map from the SupportMapFragment.
			mMap = ((SupportMapFragment) getFragmentManager().findFragmentById(
					R.id.map2)).getMap();
			// Check if we were successful in obtaining the map.
			if (mMap != null) {
				setUpMap();

			}
		}
	}

	private void setUpMap() {
		// mMap.addMarker(new MarkerOptions().position(new
		// LatLng(43.614827,13.519707)).title("Ancona"));
		mMap.moveCamera(CameraUpdateFactory.newCameraPosition(ANCONA));

	}

	public class GetSegnalazioni extends
			AsyncTask<Void, Void, ArrayList<Segnalazione>> {

		@Override
		protected ArrayList<Segnalazione> doInBackground(Void... params) {
			// TODO Auto-generated method stub
			ArrayList<Segnalazione> segn_list = new ArrayList<Segnalazione>();
			HttpClient httpclient = new DefaultHttpClient();
			HttpGet httpget = new HttpGet(
					"http://www.unifacile.it/napolitano/public/segnalazione");
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			try {
				JSONObject segn = new JSONObject(httpclient.execute(httpget,
						responseHandler));
				JSONArray segn_array = segn.getJSONArray("d");
				for (int i = 0; i < segn_array.length(); i++) {
					int id_segn = segn_array.getJSONObject(i).getInt(
							"idsegnalazione");

					int id_utente = segn_array.getJSONObject(i).getInt(
							"idutente");

					int id_organo = segn_array.getJSONObject(i).getInt(
							"idorgano");

					String foto = segn_array.getJSONObject(i).getString("foto");

					String desc = segn_array.getJSONObject(i).getString(
							"descrizione");

					double lat = segn_array.getJSONObject(i).getDouble(
							"latitudine");

					double lon = segn_array.getJSONObject(i).getDouble(
							"longitudine");

					segn_list.add(new Segnalazione(id_segn, id_utente,
							id_organo, foto, desc, lat, lon));

				}
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return segn_list;

		}

		@Override
		protected void onPostExecute(ArrayList<Segnalazione> segn_list) {
			segnalazioni = segn_list;
			TextPaint p = new TextPaint();
			p.setFlags(p.getFlags() | TextPaint.DEV_KERN_TEXT_FLAG);
			if (segnalazioni != null && segnalazioni.size() > 0) {
				for (Segnalazione segn : segnalazioni) {
					int id = segn.getIdOrgano();
					String descript = segn.getDesc();
					descript = TextUtils.ellipsize(descript, p, 200,
							TruncateAt.END).toString();
					switch (id) {
					case 1:
						mMap.addMarker(new MarkerOptions()
								.position(new LatLng(segn.lat, segn.lon))
								.title(Integer.toString(segn.id_segnalazione))
								.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
										.decodeResource(getResources(),
												R.drawable.cyan_marker))));
						break;
					case 2:
						mMap.addMarker(new MarkerOptions()
								.position(new LatLng(segn.lat, segn.lon))
								.title(Integer.toString(segn.id_segnalazione))
								.icon(BitmapDescriptorFactory
										.fromBitmap(BitmapFactory
												.decodeResource(getResources(),
														R.drawable.red_marker))));
						break;
					case 3:
						mMap.addMarker(new MarkerOptions()
								.position(new LatLng(segn.lat, segn.lon))
								.title(Integer.toString(segn.id_segnalazione))
								.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
										.decodeResource(getResources(),
												R.drawable.green_marker))));
						break;

					}
					mMap.setOnMarkerClickListener(new OnMarkerClickListener() {

						@Override
						public boolean onMarkerClick(Marker arg0) {
							// TODO Auto-generated method stub
							for (int i = 0; i < segnalazioni.size(); i++) {
								if (segnalazioni.get(i).id_segnalazione == Integer
										.parseInt(arg0.getTitle())) {
									Intent dettaglio = new Intent(
											getActivity(),
											DetSegnActivity.class);
									dettaglio.putExtra("Segnala.id",
											Integer.toString(segnalazioni
													.get(i).id_segnalazione));
									dettaglio.putExtra("Segnala.desc",
											segnalazioni.get(i).descrizione);
									dettaglio.putExtra("Segnala.foto",
											segnalazioni.get(i).foto);
									startActivity(dettaglio);
								}
							}
							return false;
						}
					});
				}
			}
		}

	}

	public class GetAdminSegnalazioni extends
			AsyncTask<Void, Void, ArrayList<Segnalazione>> {

		@Override
		protected ArrayList<Segnalazione> doInBackground(Void... params) {
			// TODO Auto-generated method stub
			ArrayList<Segnalazione> segn_list = new ArrayList<Segnalazione>();
			HttpClient httpclient = new DefaultHttpClient();
			HttpGet httpget = new HttpGet(
					"http://www.unifacile.it/napolitano/public/segnalazione");
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			try {
				JSONObject segn = new JSONObject(httpclient.execute(httpget,
						responseHandler));
				JSONArray segn_array = segn.getJSONArray("d");
				for (int i = 0; i < segn_array.length(); i++) {
					int id_segn = segn_array.getJSONObject(i).getInt(
							"idsegnalazione");

					int id_utente = segn_array.getJSONObject(i).getInt(
							"idutente");

					int id_organo = segn_array.getJSONObject(i).getInt(
							"idorgano");

					String foto = segn_array.getJSONObject(i).getString("foto");

					String desc = segn_array.getJSONObject(i).getString(
							"descrizione");

					double lat = segn_array.getJSONObject(i).getDouble(
							"latitudine");

					double lon = segn_array.getJSONObject(i).getDouble(
							"longitudine");

					segn_list.add(new Segnalazione(id_segn, id_utente,
							id_organo, foto, desc, lat, lon));

				}
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return segn_list;

		}

		@Override
		protected void onPostExecute(ArrayList<Segnalazione> segn_list) {
			segnalazioni = segn_list;
			TextPaint p = new TextPaint();
			p.setFlags(p.getFlags() | TextPaint.DEV_KERN_TEXT_FLAG);
			if (segnalazioni != null && segnalazioni.size() > 0) {
				for (Segnalazione segn : segnalazioni) {
					int id = segn.getIdOrgano();
					String descript = segn.getDesc();
					descript = TextUtils.ellipsize(descript, p, 200,
							TruncateAt.END).toString();
					switch (id) {
					case 1:
						mMap.addMarker(new MarkerOptions()
								.position(new LatLng(segn.lat, segn.lon))
								.title(Integer.toString(segn.id_segnalazione))
								.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
										.decodeResource(getResources(),
												R.drawable.cyan_marker))));
						break;
					default:
						break;
					}
					mMap.setOnMarkerClickListener(new OnMarkerClickListener() {

						@Override
						public boolean onMarkerClick(Marker arg0) {
							// TODO Auto-generated method stub
							for (int i = 0; i < segnalazioni.size(); i++) {
								if (segnalazioni.get(i).id_segnalazione == Integer
										.parseInt(arg0.getTitle())) {
									Intent dettaglio = new Intent(
											getActivity(),
											DetSegnActivity.class);
									dettaglio.putExtra("Segnala.id",
											Integer.toString(segnalazioni
													.get(i).id_segnalazione));
									dettaglio.putExtra("Segnala.desc",
											segnalazioni.get(i).descrizione);
									dettaglio.putExtra("Segnala.foto",
											segnalazioni.get(i).foto);
									startActivity(dettaglio);
								}
							}
							return false;
						}
					});
				}
			}
		}

	}

	public class GetMuniSegnalazioni extends
			AsyncTask<Void, Void, ArrayList<Segnalazione>> {

		@Override
		protected ArrayList<Segnalazione> doInBackground(Void... params) {
			// TODO Auto-generated method stub
			ArrayList<Segnalazione> segn_list = new ArrayList<Segnalazione>();
			HttpClient httpclient = new DefaultHttpClient();
			HttpGet httpget = new HttpGet(
					"http://www.unifacile.it/napolitano/public/segnalazione");
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			try {
				JSONObject segn = new JSONObject(httpclient.execute(httpget,
						responseHandler));
				JSONArray segn_array = segn.getJSONArray("d");
				for (int i = 0; i < segn_array.length(); i++) {
					int id_segn = segn_array.getJSONObject(i).getInt(
							"idsegnalazione");

					int id_utente = segn_array.getJSONObject(i).getInt(
							"idutente");

					int id_organo = segn_array.getJSONObject(i).getInt(
							"idorgano");

					String foto = segn_array.getJSONObject(i).getString("foto");

					String desc = segn_array.getJSONObject(i).getString(
							"descrizione");

					double lat = segn_array.getJSONObject(i).getDouble(
							"latitudine");

					double lon = segn_array.getJSONObject(i).getDouble(
							"longitudine");

					segn_list.add(new Segnalazione(id_segn, id_utente,
							id_organo, foto, desc, lat, lon));

				}
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return segn_list;

		}

		@Override
		protected void onPostExecute(ArrayList<Segnalazione> segn_list) {
			segnalazioni = segn_list;
			TextPaint p = new TextPaint();
			p.setFlags(p.getFlags() | TextPaint.DEV_KERN_TEXT_FLAG);
			if (segnalazioni != null && segnalazioni.size() > 0) {
				for (Segnalazione segn : segnalazioni) {
					int id = segn.getIdOrgano();
					String descript = segn.getDesc();
					descript = TextUtils.ellipsize(descript, p, 200,
							TruncateAt.END).toString();
					switch (id) {
					case 2:
						mMap.addMarker(new MarkerOptions()
								.title(Integer.toString(segn.id_segnalazione))
								.position(new LatLng(segn.lat, segn.lon))
								.icon(BitmapDescriptorFactory
										.fromBitmap(BitmapFactory
												.decodeResource(getResources(),
														R.drawable.red_marker))));
						break;

					default:
						break;
					}
					mMap.setOnMarkerClickListener(new OnMarkerClickListener() {

						@Override
						public boolean onMarkerClick(Marker arg0) {
							// TODO Auto-generated method stub
							for (int i = 0; i < segnalazioni.size(); i++) {
								if (segnalazioni.get(i).id_segnalazione == Integer
										.parseInt(arg0.getTitle())) {
									Intent dettaglio = new Intent(
											getActivity(),
											DetSegnActivity.class);
									dettaglio.putExtra("Segnala.id",
											Integer.toString(segnalazioni
													.get(i).id_segnalazione));
									dettaglio.putExtra("Segnala.desc",
											segnalazioni.get(i).descrizione);
									dettaglio.putExtra("Segnala.foto",
											segnalazioni.get(i).foto);
									startActivity(dettaglio);
								}
							}
							return false;
						}
					});
				}
			}
		}

	}

	public class GetPublicSegnalazioni extends
			AsyncTask<Void, Void, ArrayList<Segnalazione>> {

		@Override
		protected ArrayList<Segnalazione> doInBackground(Void... params) {
			// TODO Auto-generated method stub
			ArrayList<Segnalazione> segn_list = new ArrayList<Segnalazione>();
			HttpClient httpclient = new DefaultHttpClient();
			HttpGet httpget = new HttpGet(
					"http://www.unifacile.it/napolitano/public/segnalazione");
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			try {
				JSONObject segn = new JSONObject(httpclient.execute(httpget,
						responseHandler));
				JSONArray segn_array = segn.getJSONArray("d");
				for (int i = 0; i < segn_array.length(); i++) {
					int id_segn = segn_array.getJSONObject(i).getInt(
							"idsegnalazione");

					int id_utente = segn_array.getJSONObject(i).getInt(
							"idutente");

					int id_organo = segn_array.getJSONObject(i).getInt(
							"idorgano");

					String foto = segn_array.getJSONObject(i).getString("foto");

					String desc = segn_array.getJSONObject(i).getString(
							"descrizione");

					double lat = segn_array.getJSONObject(i).getDouble(
							"latitudine");

					double lon = segn_array.getJSONObject(i).getDouble(
							"longitudine");

					segn_list.add(new Segnalazione(id_segn, id_utente,
							id_organo, foto, desc, lat, lon));

				}
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return segn_list;

		}

		@Override
		protected void onPostExecute(ArrayList<Segnalazione> segn_list) {
			segnalazioni = segn_list;
			TextPaint p = new TextPaint();
			p.setFlags(p.getFlags() | TextPaint.DEV_KERN_TEXT_FLAG);
			if (segnalazioni != null && segnalazioni.size() > 0) {
				for (Segnalazione segn : segnalazioni) {
					int id = segn.getIdOrgano();
					String descript = segn.getDesc();
					descript = TextUtils.ellipsize(descript, p, 200,
							TruncateAt.END).toString();
					switch (id) {
					case 3:
						mMap.addMarker(new MarkerOptions()
								.title(Integer.toString(segn.id_segnalazione))
								.position(new LatLng(segn.lat, segn.lon))
								.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
										.decodeResource(getResources(),
												R.drawable.green_marker))));
						break;
					default:
						break;
					}
				}
				mMap.setOnMarkerClickListener(new OnMarkerClickListener() {

					@Override
					public boolean onMarkerClick(Marker arg0) {
						// TODO Auto-generated method stub
						for (int i = 0; i < segnalazioni.size(); i++) {
							if (segnalazioni.get(i).id_segnalazione == Integer
									.parseInt(arg0.getTitle())) {
								Intent dettaglio = new Intent(getActivity(),
										DetSegnActivity.class);
								dettaglio.putExtra(
										"Segnala.id",
										Integer.toString(segnalazioni.get(i).id_segnalazione));
								dettaglio.putExtra("Segnala.desc",
										segnalazioni.get(i).descrizione);
								dettaglio.putExtra("Segnala.foto",
										segnalazioni.get(i).foto);
								startActivity(dettaglio);
							}
						}
						return false;
					}
				});
			}
		}

	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		if (allTsk != null)
			allTsk.cancel(true);
		if (muniTask != null)
			muniTask.cancel(true);
		if (publTask != null)
			publTask.cancel(true);
		if (adminTask != null)
			adminTask.cancel(true);
		super.onDestroy();
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		if (allTsk != null)
			allTsk.cancel(true);
		if (muniTask != null)
			muniTask.cancel(true);
		if (publTask != null)
			publTask.cancel(true);
		if (adminTask != null)
			adminTask.cancel(true);
		super.onPause();
	}

	public class Segnalazione {
		int id_segnalazione = 0;
		int id_utente = 0;
		int id_organo = 0;
		String foto = "";
		String descrizione = "";
		double lat = 0;
		double lon = 0;

		public Segnalazione(int id_segn, int id_ute, int id_organo,
				String foto, String desc, double lat, double lon) {
			// TODO Auto-generated constructor stub
			this.id_segnalazione = id_segn;
			this.id_utente = id_ute;
			this.id_organo = id_organo;
			this.foto = foto;
			this.descrizione = desc;
			this.lat = lat;
			this.lon = lon;
		}

		public void setIdSegna(int id_S) {
			this.id_segnalazione = id_S;
		}

		public void setIdUtente(int id_U) {
			this.id_utente = id_U;
		}

		public void setIdOrgano(int id_O) {
			this.id_organo = id_O;
		}

		public void setFoto(String foto) {
			this.foto = foto;
		}

		public void setDesc(String desc) {
			this.descrizione = desc;
		}

		public void setLat(double lat) {
			this.lat = lat;
		}

		public void setLon(double lon) {
			this.lon = lon;
		}

		public int getIdSegna() {
			return id_segnalazione;
		}

		public int getIdUtente() {
			return id_utente;
		}

		public int getIdOrgano() {
			return id_organo;
		}

		public String getFoto() {
			return foto;
		}

		public String getDesc() {
			return descrizione;
		}

		public double getLat() {
			return lat;
		}

		public double getLon() {
			return lon;
		}

	}
}
