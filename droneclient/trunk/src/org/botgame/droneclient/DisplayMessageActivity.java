package org.botgame.droneclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.MenuItem;
import android.widget.TextView;

public class DisplayMessageActivity extends Activity {

	private TextView textView;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Intent intent = getIntent();
		// String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

		ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		textView = new TextView(this);
		textView.setMovementMethod(new ScrollingMovementMethod());
		textView.setTextSize(12);

		if (networkInfo != null && networkInfo.isConnected()) {
			new GetFightLog().execute();
		} else {
			textView.setText("No network connection available");
		}

		// textView.setText(message);

		setContentView(textView);

		// Show the Up button in the action bar.
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
			getActionBar().setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private class GetFightLog extends AsyncTask {

		@Override
		protected Object doInBackground(Object... params) {
			try {
				URL url = new URL("http://192.168.1.5:8888/botgame");
				HttpURLConnection conn = (HttpURLConnection) url
						.openConnection();
				conn.setReadTimeout(10000 /* milliseconds */);
				conn.setConnectTimeout(15000 /* milliseconds */);
				conn.setRequestMethod("POST");
				conn.setDoInput(true);

				// Starts the query
				conn.connect();
				int response = conn.getResponseCode();
				// Log.d(DEBUG_TAG, "The response is: " + response);
				InputStream is = conn.getInputStream();

				BufferedReader r = new BufferedReader(new InputStreamReader(is));
				StringBuilder total = new StringBuilder();
				String line;
				while ((line = r.readLine()) != null) {
					final String tempLine = line;
					runOnUiThread(new Runnable() {

						@Override
						public void run() {
							textView.append("\n");
							textView.append(tempLine);
						}
					});
				}
				// Convert the InputStream into a string
				// String contentAsString = readIt(is, len);
				return null;

			} catch (IOException e) {

				return "Unable to start fight";
			}
		}
	}

}
