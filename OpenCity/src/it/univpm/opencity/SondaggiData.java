package it.univpm.opencity;

<<<<<<< HEAD
=======
import it.univpm.opencity.Opdata.Farmacia;
>>>>>>> 92286ab773427861121001619e8409a157f4bca5
import it.univpm.opencity.dummy.SondaggiContent;
import it.univpm.opencity.dummy.SondaggiContent.DummyItem;

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

public class SondaggiData {

	public static ArrayList<DummyItem> getSondaggi() {
		ArrayList<DummyItem> sondaggio_list = new ArrayList<DummyItem>();
		HttpClient httpclient = new DefaultHttpClient();
		HttpGet httpget = new HttpGet(
<<<<<<< HEAD
				"http://www.unifacile.it/napolitano/public/sondaggio");
=======
				"http://opendatasalutedata.cloudapp.net/v1/datacatalog/Farmacie/?$filter=descrizionecomune%20eq%20%27"
						+ "ANCONA" + "%27&format=json");
>>>>>>> 92286ab773427861121001619e8409a157f4bca5
		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		try {
			JSONObject farm = new JSONObject(httpclient.execute(httpget,
					responseHandler));
			JSONArray farm_array = farm.getJSONArray("d");
			for (int i = 0; i < farm_array.length(); i++) {
<<<<<<< HEAD
				String idSondaggio = farm_array.getJSONObject(i).getString("id");				
				String titolo = farm_array.getJSONObject(i).getString("titolo");
				String testo = farm_array.getJSONObject(i).getString("testo");				
				String si = farm_array.getJSONObject(i).getString("si");						
				String no = farm_array.getJSONObject(i).getString("no");				
				String data = farm_array.getJSONObject(i).getString("data");
				
				SondaggiContent.addItem(new DummyItem(""+i, titolo,testo,idSondaggio,si,no,data));
				sondaggio_list.add(new DummyItem(""+i, titolo,testo,idSondaggio,si,no,data));
=======
				String nome = farm_array.getJSONObject(i).getString(
						"descrizionefarmacia");
				String indirizzo = farm_array.getJSONObject(i).getString(
						"indirizzo");
				
				SondaggiContent.addItem(new DummyItem(""+i, nome,indirizzo));
				sondaggio_list.add(new DummyItem(""+i, nome,indirizzo));
>>>>>>> 92286ab773427861121001619e8409a157f4bca5
				
				
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
		
		return sondaggio_list;
	}
}
