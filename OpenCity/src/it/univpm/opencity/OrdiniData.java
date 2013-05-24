package it.univpm.opencity;

import it.univpm.opencity.Opdata.Farmacia;
import it.univpm.opencity.dummy.OrdiniContent;
import it.univpm.opencity.dummy.OrdiniContent.OrdiniItem;

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

public class OrdiniData {

	public static ArrayList<OrdiniItem> getOrdini() {
		ArrayList<OrdiniItem> ordini_list = new ArrayList<OrdiniItem>();
		HttpClient httpclient = new DefaultHttpClient();
		HttpGet httpget = new HttpGet(
				"http://opendatasalutedata.cloudapp.net/v1/datacatalog/Farmacie/?$filter=descrizionecomune%20eq%20%27"
						+ "ANCONA" + "%27&format=json");
		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		try {
			JSONObject farm = new JSONObject(httpclient.execute(httpget,
					responseHandler));
			JSONArray farm_array = farm.getJSONArray("d");
			for (int i = 0; i < farm_array.length(); i++) {
				String nome = farm_array.getJSONObject(i).getString(
						"descrizionefarmacia");
				
				
				OrdiniContent.addItem(new OrdiniItem(""+i, nome));
				ordini_list.add(new OrdiniItem(""+i, nome));
				
				
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
		
		return ordini_list;
	}
}
