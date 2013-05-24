package it.univpm.opencity;



import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;


import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

public class DetSegnActivity extends Activity {

	private TextView txtId;
	private ImageView imgSegnalazione;
	private TextView txtDesc;
	String id,desc;
	String foto="";
	Intent dettaglio;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
		setContentView(R.layout.activity_det_segn);
		dettaglio = getIntent();
		id = dettaglio.getStringExtra("Segnala.id");
		desc = dettaglio.getStringExtra("Segnala.desc");
		foto = dettaglio.getStringExtra("Segnala.foto");
		txtId = (TextView)findViewById(R.id.id_segna);
		imgSegnalazione = (ImageView)findViewById(R.id.imgSegnalazione);
		txtDesc = (TextView) findViewById(R.id.textDesc);
		txtId.setText("Id_Segnalazione : "+id);
		txtDesc.setText(desc);
		if(!foto.equals("")){
			new SetFoto().execute();
		}else{
			imgSegnalazione.setImageDrawable(getResources().getDrawable(R.drawable.default_image));
		}
	}


	public class SetFoto extends AsyncTask<Void, Void, Bitmap> {


		@Override
		protected void onPreExecute() {
			// telefono = random_Phone();
			

		};

		@Override
		protected void onPostExecute(Bitmap bitmap) {

			if(bitmap!=null){
				imgSegnalazione.setImageBitmap(bitmap); 
			}
		};

		@Override
		protected Bitmap doInBackground(Void... arg0) {
			Bitmap bitmap = null;
			try{

				try {
					  bitmap = BitmapFactory.decodeStream((InputStream)new URL(foto).getContent());
				} catch (MalformedURLException e) {
				  e.printStackTrace();
				} catch (IOException e) {
				  e.printStackTrace();
				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return bitmap;
		}
	}
}
