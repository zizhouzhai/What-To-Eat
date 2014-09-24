package com.activities.wte;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class NewRestaurantPhotoActivity extends Activity {

	private final int REQUEST_CAMERA = 100;
	private final int SELECT_FILE = 200;
	ImageView previewView;
	Bitmap selectedBitmap;
	Uri selectedUri;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_restaurant_photo);

		previewView = (ImageView) findViewById(R.id.previewView);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.new_restaurant, menu);
		return true;
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

	/*
	 * onClick method for the imageView Opens up android image picker to select
	 * a photo.
	 */
	public void pickPhoto(View view) {

		// options for the alertdialog
		final CharSequence[] items = { "Take Photo", "Choose from Library",
				"Cancel" };

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Add Photo!");
		builder.setItems(items, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int item) {

				// if taking photo
				if (items[item].equals("Take Photo")) {
					Intent intent = new Intent(
							android.provider.MediaStore.ACTION_IMAGE_CAPTURE);

					try {
						selectedUri = getOutputMediaFileUri();
					} catch (IOException e) {
						e.printStackTrace();
					} // create a file to
						// save the image
					intent.putExtra(MediaStore.EXTRA_OUTPUT, selectedUri);

					startActivityForResult(intent, REQUEST_CAMERA);
				}
				// if choosing from library
				else if (items[item].equals("Choose from Library")) {
					Intent intent = new Intent(
							Intent.ACTION_PICK,
							android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
					intent.setType("image/*");
					startActivityForResult(
							Intent.createChooser(intent, "Select File"),
							SELECT_FILE);
				}
				// if cancel
				else if (items[item].equals("Cancel")) {
					dialog.dismiss();
				}
			}
		});
		builder.show();

	}

	/*
	 * onActivityResult for the photo picture.
	 */
	public void onActivityResult(int requestCode, int resultCode, Intent data) {

		switch (requestCode) {

		// if select from file
		case SELECT_FILE:
			if (resultCode == RESULT_OK) {
				Uri imageUri = data.getData();

				// load image
				Picasso.with(this).load(imageUri).resize(320, 320).centerCrop()
						.into(previewView);

				selectedUri = imageUri;
			}
			break;

		// if taking photo from camera
		case REQUEST_CAMERA:
			if (resultCode == RESULT_OK) {

				// photo saved to selectedUri
				Picasso.with(this).load(selectedUri).resize(320, 320)
						.centerCrop().into(previewView);

			} else if (resultCode == RESULT_CANCELED) {
				// User cancelled the image capture
			} else {
				// Image capture failed, advise user
			}
			break;

		}

	}

	/**
	 * onClick method for the next button. When clicked, save the photo move to
	 * the next.
	 */
	public void acceptPhoto(View view) {

		// check if there is a photo saved
		if (selectedUri == null) {
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
					this);
			alertDialogBuilder.setTitle("Invalid Photo");
			alertDialogBuilder.setMessage("Please Select a Valid Photo");
			alertDialogBuilder.setPositiveButton("Ok",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
						}
					});
			AlertDialog alertDialog = alertDialogBuilder.create();
			alertDialog.show();
			return;
		}

		Intent anotherIntent = new Intent(this, NewRestaurantNameActivity.class);
		anotherIntent.putExtra("imageUri", selectedUri);
		startActivity(anotherIntent);

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
