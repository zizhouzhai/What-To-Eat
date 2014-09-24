package com.activities.wte;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.squareup.picasso.Picasso;

import PicassoTransforms.CircleTransform;
import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader.TileMode;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

public class NewPhotoActivity extends ActionBarActivity {

	ImageView imageView1;
	Boolean cameraLoaded = false;
	private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
	Uri selectedUri;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);


		setContentView(R.layout.activity_new_photo);
		imageView1 = (ImageView) findViewById(R.id.imageView1);

		// check the savedInstanceState
		if (savedInstanceState == null) {

			// see if camera has loaded already.
			if (cameraLoaded != true) {

				cameraLoaded = true;
				Intent intent = new Intent(
						android.provider.MediaStore.ACTION_IMAGE_CAPTURE);

				try {
					selectedUri = getOutputMediaFileUri();
				} catch (IOException e) {
					e.printStackTrace();
				} // create a file to
					// save the image
				intent.putExtra(MediaStore.EXTRA_OUTPUT, selectedUri);

				startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
			} else if (cameraLoaded == true) {

			}
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.new_photo, menu);
		return true;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		// see if the capture image intent was called.
		if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {

			// if photo taken correctly.
			if (resultCode == RESULT_OK) {
				// imageView1.setImageBitmap(Bitmap.createScaledBitmap(bp, 150,
				// 150,
				// false));

				cameraLoaded = true;

				// Create the new bitmap
				/*(Bitmap circleBitmap = Bitmap.createBitmap(bp.getWidth() + 10,
						bp.getHeight() + 10, Bitmap.Config.ARGB_8888);
				BitmapShader shader = new BitmapShader(bp, TileMode.CLAMP,
						TileMode.CLAMP);

				// set up the paint
				Paint paint = new Paint();
				paint.setAntiAlias(true);
				paint.setShader(shader);

				// create new canvas
				Canvas c = new Canvas(circleBitmap);

				// draw the circle
				c.drawCircle(bp.getWidth() / 2, bp.getHeight() / 2,
						bp.getWidth() / 2, paint);
				imageView1.setImageBitmap(circleBitmap);*/
				
				Picasso.with(getApplicationContext()).load(selectedUri).transform(new CircleTransform()).into(imageView1);
			}

			// photo was cancelled.
			else if (resultCode == RESULT_CANCELED) {

				// go back to main menu
				Intent backIntent = new Intent(this,MainActivity.class);
		    	startActivity(backIntent);
				
			}
		}

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	/**
	 * Create a file Uri for saving an image or video
	 * 
	 * @throws IOException
	 */
	private static Uri getOutputMediaFileUri() throws IOException {
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US)
				.format(new Date());
		String imageFileName = "JPEG_" + timeStamp + "_";
		File storageDir = Environment
				.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
		File image = File.createTempFile(imageFileName, /* prefix */
				".jpg", /* suffix */
				storageDir /* directory */
		);

		// Save a file: path for use with ACTION_VIEW intents
		return Uri.fromFile(image);
	}

}
