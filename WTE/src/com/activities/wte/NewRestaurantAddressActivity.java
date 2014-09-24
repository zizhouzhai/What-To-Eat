package com.activities.wte;

import android.app.Activity;
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

public class NewRestaurantAddressActivity extends Activity {
	
	Uri selectedUri;
	Bitmap bmp;
	String selectedName;
	
	EditText addressLookup;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_restaurant_address);
		
		ImageView previewView = (ImageView) findViewById(R.id.previewView);
		addressLookup = (EditText) findViewById(R.id.addressEditText);

		selectedUri = getIntent().getParcelableExtra("imageUri");
		selectedName = getIntent().getStringExtra("name");
		
		Picasso.with(this)
		.load(selectedUri)
		.centerCrop()
		.resize(320, 320)
		.into(previewView);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.new_restaurant_address, menu);
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
	
	/**
	 * onClick method for the find button.
	 * @param view
	 */
	public void findAddress(View view){
		
		String selectedAddress = addressLookup.getText().toString();
		Intent lookupIntent = new Intent(this,LookupAddress.class);
		lookupIntent.putExtra("lookupAddress", selectedAddress);
		lookupIntent.putExtra("name", selectedName);
		lookupIntent.putExtra("imageUri", selectedUri);
		startActivity(lookupIntent);
		
	}
}
