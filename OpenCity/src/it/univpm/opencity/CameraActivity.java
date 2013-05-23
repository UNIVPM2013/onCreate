package it.univpm.opencity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class CameraActivity extends Activity {
	private static final int CAMERA_PIC_REQUEST = 1337; 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_camera);
		
		
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

}