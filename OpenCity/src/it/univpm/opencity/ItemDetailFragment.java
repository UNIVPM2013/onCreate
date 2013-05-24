package it.univpm.opencity;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.TextView;

import it.univpm.opencity.dummy.SondaggiContent;

/**
 * A fragment representing a single Item detail screen. This fragment is either
 * contained in a {@link ItemListActivity} in two-pane mode (on tablets) or a
 * {@link ItemDetailActivity} on handsets.
 */
public class ItemDetailFragment extends Fragment {
	/**
	 * The fragment argument representing the item ID that this fragment
	 * represents.
	 */
	public static final String ARG_ITEM_ID = "item_id";

	/**
	 * The dummy content this fragment is presenting.
	 */
	private SondaggiContent.DummyItem mItem;

	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public ItemDetailFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (getArguments().containsKey(ARG_ITEM_ID)) {
			// Load the dummy content specified by the fragment
			// arguments. In a real-world scenario, use a Loader
			// to load content from a content provider.
			mItem = SondaggiContent.ITEM_MAP.get(getArguments().getString(
					ARG_ITEM_ID));
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View rootView = inflater.inflate(R.layout.fragment_item_detail,
				container, false);

		// Show the dummy content as text in a TextView.
		if (mItem != null) {
			//((TextView) rootView.findViewById(R.id.item_detail))
				//	.setText(mItem.description);
			
			
			
		String html="<html><head><script type='text/javascript' src='https://www.google.com/jsapi'></script><script type='text/javascript'>google.load('visualization', '1.0', {'packages':['corechart']});google.setOnLoadCallback(drawChart);function drawChart() {var data = new google.visualization.DataTable();data.addColumn('string', 'Topping');data.addColumn('number', 'Slices');data.addRows([['Si', 3],['No', 1]]);var options = {'title':'Il parere dei cittadini','width':350,'height':300,'backgroundColor': 'none'};var chart = new google.visualization.PieChart(document.getElementById('chart_div'));chart.draw(data, options);}</script></head><body leftmargin='-200px;' style='background:none;'><div  style='background:none;' id='chart_div'  ></div></body></html>";
		
		String mime = "text/html";
		String encoding = "utf-8";

		WebView myWebView = (WebView) rootView.findViewById(R.id.webView1);
		myWebView.getSettings().setJavaScriptEnabled(true);
		myWebView.setBackgroundColor(0x00000000);
		myWebView.loadDataWithBaseURL(null, html, mime, encoding, null);
		myWebView.setOnTouchListener(new View.OnTouchListener() {			   
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					// TODO Auto-generated method stub
					return  (event.getAction() == MotionEvent.ACTION_MOVE);
				}
			  });
		
		((TextView) rootView.findViewById(R.id.txtTotali))
		.setText(""+ (Integer.parseInt(mItem.si) +Integer.parseInt(mItem.no)));
		 
		ImageButton btnSi = ((ImageButton) rootView.findViewById(R.id.btnSi));
		btnSi.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				new Thread(new Runnable() {
			        @Override
			        public void run() {
			            // TODO Auto-generated method stub        	
			        	
			        	
			        	try {
							URL url;
							String idutente;
							HttpClient client = new DefaultHttpClient();
							HttpPut put= new HttpPut("http://www.unifacile.it/napolitano/public/sondaggio");

//							idutente = MainActivity.getUserId();
							idutente ="00000";
							List<NameValuePair> pairs = new ArrayList<NameValuePair>();
							pairs.add(new BasicNameValuePair("sondaggio", mItem.idSongaggio));
							pairs.add(new BasicNameValuePair("utente", idutente));
							pairs.add(new BasicNameValuePair("voto", "si"));
							
							put.setEntity(new UrlEncodedFormEntity(pairs));

							HttpResponse response = client.execute(put);
							
						} catch (ProtocolException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (UnsupportedEncodingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (ClientProtocolException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} 
			        }
			    }).start();
				
				
				
				
				
			}
		});
		
		
		ImageButton btnNo = ((ImageButton) rootView.findViewById(R.id.btnNo));
		btnNo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				new Thread(new Runnable() {
			        @Override
			        public void run() {
			            // TODO Auto-generated method stub        	
			        	
			        	
			        	try {
							URL url;
							String idutente;
							HttpClient client = new DefaultHttpClient();
							HttpPut put= new HttpPut("http://www.unifacile.it/napolitano/public/sondaggio");

//							idutente = MainActivity.getUserId();
							idutente ="00000";

							Log.e("PROVA", ""+idutente);
							List<NameValuePair> pairs = new ArrayList<NameValuePair>();
							pairs.add(new BasicNameValuePair("sondaggio", mItem.idSongaggio));
							pairs.add(new BasicNameValuePair("utente", idutente));
							pairs.add(new BasicNameValuePair("voto", "no"));
							
							put.setEntity(new UrlEncodedFormEntity(pairs));

							HttpResponse response = client.execute(put);
							
						} catch (ProtocolException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (UnsupportedEncodingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (ClientProtocolException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} 
			        }
			    }).start();
				
				
				
			}
		});
		}

		return rootView;
	}
}
