package cz.jednoadem.geochlast;

import java.util.Iterator;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

	public TextView latText;
	public TextView lngText;
	GPSTracker tracker;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		this.latText = (TextView) findViewById(R.id.lat);
		this.lngText = (TextView) findViewById(R.id.lng);

		tracker = new GPSTracker(MainActivity.this);
		if (tracker.canGetLocation()) {
			latText.setText("Lat: " + Double.toString(tracker.getLatitude()));
			lngText.setText("Lng: " + Double.toString(tracker.getLongitude()));
		}
		
		
		Button b = (Button) findViewById(R.id.get);
		b.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String toText = "";
				List<LocationRecord> le = tracker.getList();
				HttpSync http = new HttpSync();
				http.sendToServer();
				Iterator<LocationRecord> it = le.iterator();
				while(it.hasNext())
				{
					LocationRecord lr = it.next();
					toText = toText + "\nLat:" +  Double.toString(lr.getLat()) + " ; Lng: " + Double.toString(lr.getLng());
					latText.setText(toText);
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void setZmena(){
		latText.setText("Change");
	}

}
