package it.univpm.opencity;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
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
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Xml;

public class Opdata {
	private static String CITY = "ANCONA";

	public static ArrayList<Farmacia> getFarmacie() {
		ArrayList<Farmacia> farm_list = new ArrayList<Farmacia>();
		HttpClient httpclient = new DefaultHttpClient();
		HttpGet httpget = new HttpGet(
				"http://opendatasalutedata.cloudapp.net/v1/datacatalog/Farmacie/?$filter=descrizionecomune%20eq%20%27"
						+ CITY + "%27&format=json");
		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		try {
			JSONObject farm = new JSONObject(httpclient.execute(httpget,
					responseHandler));
			JSONArray farm_array = farm.getJSONArray("d");
			for (int i = 0; i < farm_array.length(); i++) {
				String nome = farm_array.getJSONObject(i).getString(
						"descrizionefarmacia");
				String indirizzo = farm_array.getJSONObject(i).getString(
						"indirizzo");
				String iva = farm_array.getJSONObject(i).getString("indirizzo");

				String cap = farm_array.getJSONObject(i).getString("cap");

				String slat = farm_array.getJSONObject(i).getString(
						"latitudine");

				String slon = farm_array.getJSONObject(i).getString(
						"longitudine");
				double lat = Double.parseDouble(slat.replaceAll(",", "."));
				double lon = Double.parseDouble(slon.replaceAll(",", "."));

				farm_list
						.add(new Farmacia(nome, indirizzo, iva, cap, lat, lon));

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

		return farm_list;
	}

	public static ArrayList<Farmacia> getParafarmacie() {
		ArrayList<Farmacia> pfarm_list = new ArrayList<Farmacia>();
		HttpClient httpclient = new DefaultHttpClient();
		HttpGet httpget = new HttpGet(
				"http://opendatasalutedata.cloudapp.net/v1/datacatalog/Parafarmacie/?$filter=descrizionecomune%20eq%20%27"
						+ CITY + "%27&format=json");
		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		try {
			JSONObject pfarm = new JSONObject(httpclient.execute(httpget,
					responseHandler));
			JSONArray farm_array = pfarm.getJSONArray("d");
			for (int i = 0; i < farm_array.length(); i++) {
				String nome = farm_array.getJSONObject(i).getString(
						"denominazionesitologistico");
				String indirizzo = farm_array.getJSONObject(i).getString(
						"indirizzo");
				String iva = farm_array.getJSONObject(i).getString("indirizzo");

				String cap = farm_array.getJSONObject(i).getString("cap");

				String slat = farm_array.getJSONObject(i).getString(
						"latitudine");

				String slon = farm_array.getJSONObject(i).getString(
						"longitudine");
				double lat = Double.parseDouble(slat.replaceAll(",", "."));
				double lon = Double.parseDouble(slon.replaceAll(",", "."));
				pfarm_list
						.add(new Farmacia(nome, indirizzo, iva, cap, lat, lon));

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

		return pfarm_list;

	}

	public static ArrayList<Museo> getMusei() {
		ArrayList<Museo> musei_list = new ArrayList<Museo>();

		URL url;
		try {
			url = new URL(
					"http://151.12.58.204:8080/DBUnicoManagerWeb/dbunicomanager/searchPlace?comune="
							+ CITY + "&%20tipologiaLuogo=1");
			URLConnection conn = url.openConnection();
			XmlPullParser parser = Xml.newPullParser();
			parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
			parser.setInput(conn.getInputStream(), null);
			parser.nextTag();
			parser.require(XmlPullParser.START_TAG, null, "mibac-list");
			while (parser.next() != XmlPullParser.END_TAG) {
				if (parser.getEventType() != XmlPullParser.START_TAG) {
					continue;
				}
				String name = parser.getName();
				// Starts by looking for the entry tag
				if (name.equals("mibac")) {
//					entries.add(readEntry(parser));
				} else {
					skip(parser);
				}
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return musei_list;
	}

	private static void skip(XmlPullParser parser)
			throws XmlPullParserException, IOException {
		if (parser.getEventType() != XmlPullParser.START_TAG) {
			throw new IllegalStateException();
		}
		int depth = 1;
		while (depth != 0) {
			switch (parser.next()) {
			case XmlPullParser.END_TAG:
				depth--;
				break;
			case XmlPullParser.START_TAG:
				depth++;
				break;
			}
		}
	}

	class Museo {

	}

	static class Farmacia {
		public String getNome() {
			return nome;
		}

		public void setNome(String nome) {
			this.nome = nome;
		}

		public String getIndirizzo() {
			return indirizzo;
		}

		public void setIndirizzo(String indirizzo) {
			this.indirizzo = indirizzo;
		}

		public String getPartitaiva() {
			return partitaiva;
		}

		public void setPartitaiva(String partitaiva) {
			this.partitaiva = partitaiva;
		}

		public String getCap() {
			return cap;
		}

		public void setCap(String cap) {
			this.cap = cap;
		}

		public double getLat() {
			return lat;
		}

		public void setLat(double lat) {
			this.lat = lat;
		}

		public double getLon() {
			return lon;
		}

		public void setLon(double lon) {
			this.lon = lon;
		}

		String nome;
		String indirizzo;
		String partitaiva;
		String cap;
		double lat;
		double lon;

		public Farmacia(String nome, String ind, String iva, String cap,
				double lat, double lon) {
			// TODO Auto-generated constructor stub
			this.nome = nome;
			this.indirizzo = ind;
			this.partitaiva = iva;
			this.cap = cap;
			this.lat = lat;
			this.lon = lon;
		}
	}
}