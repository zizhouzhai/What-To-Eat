package com.activities.wte;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

public class NewRestaurantPhotoActivity extends Activity {

	private final int REQUEST_CAMERA = 100;
	private final int SELECT_FILE = 200;
	ImageView previewView;

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
				try {
					final Uri imageUri = data.getData();
					final InputStream imageStream = getContentResolver()
							.openInputStream(imageUri);
					final Bitmap selectedImage = BitmapFactory
							.decodeStream(imageStream);
					previewView.setImageBitmap(Bitmap.createScaledBitmap(selectedImage,
							160, 160, false));
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
			break;

		// if taking photo from camera
		case REQUEST_CAMERA:
			if (resultCode == RESULT_OK) {
				Bitmap bp = (Bitmap) data.getExtras().get("data");
				previewView.setImageBitmap(Bitmap.createScaledBitmap(bp,
						160, 160, false));
			}
			break;

		}

	}

}