package cz.jednoadem.geochlast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import android.util.Log;

public class HttpSync {

	private List<LocationRecord> data;

	public HttpSync(List<LocationRecord> l) {
		this.data = l;
	}

	public void sendToServer() {
		HttpThread t = new HttpThread();
		t.setData(this.data);
		t.start();
	}

}

class HttpThread extends Thread {

	private List<LocationRecord> data;

	public void setData(List<LocationRecord> l) {
		this.data = l;
	}

	public void run() {
		// Create a new HttpClient and Post Header
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost("http://mobile.talko.cz/index.php");
		try {
			// Add your data
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
			Iterator<LocationRecord> it = this.data.iterator();
			while (it.hasNext()) {
				LocationRecord lr = it.next();
				nameValuePairs.add(new BasicNameValuePair("lat[]", Double.toString(lr.getLat())));
				nameValuePairs.add(new BasicNameValuePair("lng[]", Double.toString(lr.getLng())));
			}
			nameValuePairs.add(new BasicNameValuePair("user_key", "123456798"));
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			// Execute HTTP Post Request
			Log.d("PRESEND", "OK");
			HttpResponse response = httpclient.execute(httppost);

		} catch (ClientProtocolException e) {
			Log.d("THROW", "ClientProtocolException");
		} catch (IOException e) {
			Log.d("THROW", "IOException");
		} catch (Exception e) {
			Log.d("Internet excepion", e.getMessage());
		}
	}

}
