package it.univpm.opencity;



import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//DA ELIMINARE POI!!
		Button btnGotoCameraActivity = (Button) findViewById(R.id.btnGoToCamera);
		btnGotoCameraActivity.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {		
				Intent intent = new Intent(MainActivity.this,
						CameraActivity.class);
				startActivity(intent);
			}
		});
		
		Button btnGotoMapActivity = (Button) findViewById(R.id.btnGoToMap);
		btnGotoMapActivity.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {		
				Intent intent = new Intent(MainActivity.this,
						MapActivity.class);
				startActivity(intent);
			}
		});
		///
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
