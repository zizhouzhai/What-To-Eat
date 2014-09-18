package com.activities.wte;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import com.parse.Parse;
import com.parse.ParseObject;

import com.managers.ImageManager;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // set up the Parse
		ParseObject.registerSubclass(com.classes.Meal.class);
		ParseObject.registerSubclass(com.classes.Restaurant.class);
        Parse.initialize(this, "e9xx4wo9aH5j1nKUaLPjiW917X2Un09aN7OAnxl6", "pQy42CSoA6omvTXpNIje8kMn03fc9gH2p3lcgaJr");
        
        // get rid of top bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
	    this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
	    
        setContentView(R.layout.activity_main);
        
        ImageManager iManager = new ImageManager();
        
        Bitmap image1 = iManager.decodeSampledBitmapFromResource(getResources(), R.drawable.image1, 150, 150);
        Bitmap image2 = iManager.decodeSampledBitmapFromResource(getResources(), R.drawable.image2, 150, 150);
        Bitmap image3 = iManager.decodeSampledBitmapFromResource(getResources(), R.drawable.image3, 150, 150);
        Bitmap image4 = iManager.decodeSampledBitmapFromResource(getResources(), R.drawable.image4, 150, 150);
        
        
        /*LinearLayout imageLayout = (LinearLayout)findViewById(R.id.imageLayout);

        LayoutInflater  mInflater = (LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        
        LinearLayout custom = (LinearLayout)mInflater.inflate(R.layout.imagelayout,null);
        
        ArrayList<View> views = new ArrayList<View>();
        for(int x = 0; x < custom.getChildCount(); x++) {
            views.add(custom.getChildAt(x));
        }
        custom.removeAllViews();
        for(int x = views.size() - 1; x >= 0; x--) {
            custom.addView(views.get(x));
        }

        
        imageLayout.addView(custom);*/
        
        
        
        View imageV1 = (View) findViewById(R.id.image1);
        ImageView imageView1 = (ImageView) imageV1.findViewById(R.id.imageView1);
        imageView1.setImageBitmap(image1);
        
        View imageV2 = (View) findViewById(R.id.image2);
        ImageView imageView2 = (ImageView) imageV2.findViewById(R.id.imageView1);
        imageView2.setImageBitmap(image2);
        
        View imageV3 = (View) findViewById(R.id.image3);
        ImageView imageView3 = (ImageView) imageV3.findViewById(R.id.imageView1);
        imageView3.setImageBitmap(image3);
        
        View imageV4 = (View) findViewById(R.id.image4);
        ImageView imageView4 = (ImageView) imageV4.findViewById(R.id.imageView1);
        imageView4.setImageBitmap(image4);
        
	    
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

    
    /* onClick function for the camera button.
     * when clicked, load up the camera app, take a picture from it. and onreturn, 
     * save the picture to the database.
     */
    public void cameraClick(View view){
    	
    	Intent cameraIntent = new Intent(this,NewPhotoActivity.class);
    	startActivity(cameraIntent);
    	
    }
    
}
