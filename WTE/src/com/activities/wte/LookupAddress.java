package com.activities.wte;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.classes.Restaurant;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.SaveCallback;
import com.squareup.picasso.Picasso;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

public class LookupAddress extends Activity {

	List<Address> addresses;
	String addressToSearch;
	Context context;
	ProgressBar loadingBar;
	ListView lookupResults;
	Address selectedAddress;

	String selectedName;
	Uri selectedUri;
	Bitmap bmp;
	ParseFile file;

	int selectedPosition;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lookup_address);

		// Loading up the views for future edit
		lookupResults = (ListView) findViewById(R.id.lookupResults);
		loadingBar = (ProgressBar) findViewById(R.id.progressBar1);
		context = this;

		// Loading information from the passedIntent
		Intent passedIntent = this.getIntent();
		addressToSearch = passedIntent.getStringExtra("lookupAddress");
		selectedUri = passedIntent.getParcelableExtra("imageUri");
		selectedName = passedIntent.getStringExtra("name");

		// execute asynctask to lookup address
		new AddressLookupTask().execute(addressToSearch);

		// set onclickListener
		lookupResults.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				selectedPosition = position;
				new SaveRestaurantTask().execute();
				Toast.makeText(getApplicationContext(),
						"New Restaurant is saving in background",
						Toast.LENGTH_LONG).show();
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.lookup_address, menu);
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
	 * Helper method. Converts address to readable form
	 * 
	 * @param address
	 *            the address to extract info from
	 * @return the address in readable form.
	 */
	private String getAddress(Address address) {
		String addressText = String.format(
				"%s, %s, %s, %s",
				// If there's a street address, add it
				address.getMaxAddressLineIndex() > 0 ? address
						.getAddressLine(0) : "",
				// Locality is usually a city
				address.getLocality() == null ? "" : address.getLocality(),

				// The postcode
				address.getPostalCode() == null ? "" : address.getPostalCode(),

				// The country of the address
				address.getCountryName() == null? "":address.getCountryName());
		return addressText;
	}

	private class AddressLookupTask extends
			AsyncTask<String, Void, List<String>> {

		@Override
		protected List<String> doInBackground(String... params) {

			// create geocoder
			Geocoder geocoder = new Geocoder(context, Locale.ENGLISH);

			// try to find addresses
			try {
				addresses = geocoder.getFromLocationName(params[0], 10);
			} catch (IOException e) {
				e.printStackTrace();
			}

			List<String> addressText = new ArrayList<String>();
			for (Address a : addresses) {
				addressText.add(getAddress(a));
			}

			return addressText;
		}

		protected void onPostExecute(List<String> addresses) {

			loadingBar.setVisibility(View.GONE);
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
					android.R.layout.simple_list_item_1, addresses);
			lookupResults.setAdapter(adapter);
			lookupResults.setVisibility(View.VISIBLE);

		}

	}

	private class SaveRestaurantTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {

			selectedAddress = addresses.get(selectedPosition);

			System.out.println("end parsing, start loading");


			try {
				bmp= Picasso.with(context).load(selectedUri).get();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			System.out.println("finished loading. start converting");

			// convert bitmap to byte[]
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
			byte[] byteArray = stream.toByteArray();

			System.out.println("new file");

			// parsefile storing the image
			file = new ParseFile("photo", byteArray);
			file.saveInBackground(new SaveCallback() {

				@Override
				public void done(ParseException arg0) {

					System.out.println(arg0);

					// set up the new Restaurant
					Restaurant newRestaurant = new Restaurant();
					newRestaurant.setRestaurantName(selectedName);
					newRestaurant
							.setRestaurantAddress(getAddress(selectedAddress));
					newRestaurant.setLatitude(selectedAddress.getLatitude());
					newRestaurant.setLongitude(selectedAddress.getLongitude());

					newRestaurant.setPhotoFile(file);
					newRestaurant.saveInBackground();
					System.out.println("savedinbackground");
				}
			});

			return null;
		}
	}

}
