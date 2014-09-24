package com.activities.wte;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class NewRestaurantNameActivity extends Activity {

	Bitmap bmp;
	Uri selectedUri;
	EditText nameEditText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_restaurant_name);

		ImageView previewView = (ImageView) findViewById(R.id.previewView);

		selectedUri = getIntent().getParcelableExtra("imageUri");

		Picasso.with(this)
				.load(selectedUri)
				.centerCrop()
				.resize(320, 320)
				.into(previewView);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.new_restaurant_name, menu);
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
	 * onClick function for the OK button When called, saves the name and then
	 * passes it along with photo to next activity
	 */
	public void acceptName(View view) {

		nameEditText = (EditText) findViewById(R.id.nameEditText);

		String selectedName = nameEditText.getText().toString();

		if (selectedName.matches("")) {
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
					this);
			alertDialogBuilder.setTitle("Invalid Name");
			alertDialogBuilder.setMessage("Please Enter a Valid Name");
			alertDialogBuilder.setPositiveButton("Ok",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
						}
					});
			AlertDialog alertDialog = alertDialogBuilder.create();
			alertDialog.show();
			return;
		}

		Intent anotherIntent = new Intent(this,
				NewRestaurantAddressActivity.class);
		// anotherIntent.putExtra("image", byteArray);
		anotherIntent.putExtra("imageUri", selectedUri);
		anotherIntent.putExtra("name", selectedName);
		startActivity(anotherIntent);

	}
}
