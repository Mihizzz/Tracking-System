package app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Dialog;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.login2.R;
import com.google.android.gms.common.ConnectionResult;
//import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerDragListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
//import com.google.android.gms.location.LocationClient;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends FragmentActivity {

	private GoogleMap mMap;
	Marker marker;
	private static final float DZOOM = 15;
	private static final View View = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (initMap()) {

			Toast.makeText(this, "Ready to map", Toast.LENGTH_SHORT).show();
			mMap.setMyLocationEnabled(true); // mylocation enable
			mMap.getUiSettings().setZoomControlsEnabled(true); // Zoom controls

		} else
			Toast.makeText(this, " not connected", Toast.LENGTH_SHORT).show();

		try {
			// Loading map
			initMap();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public boolean onOptionsItemSelected1(MenuItem item) {

		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/*
	 * function to load map. If map is not created it will create it for you
	 */

	private boolean initMap() {
		if (mMap == null) {
			mMap = ((MapFragment) getFragmentManager().findFragmentById(
					R.id.map)).getMap();

			if (mMap != null) {

				mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

					@Override
					public View getInfoWindow(Marker arg0) {
						return null;
					}

					@Override
					public View getInfoContents(Marker marker) {

						View v = getLayoutInflater().inflate(
								R.layout.info_window, null);
						TextView tvLocality = (TextView) v
								.findViewById(R.id.tv_locality); // marker
																	// infomations
						TextView tvLat = (TextView) v.findViewById(R.id.tv_lat);
						TextView tvLng = (TextView) v.findViewById(R.id.tv_lng);
						TextView tvSnippet = (TextView) v
								.findViewById(R.id.tv_snippet);

						LatLng ll = marker.getPosition();
						tvLocality.setText(marker.getTitle());
						tvLat.setText("Latitude:" + ll.latitude);
						tvLng.setText("Longitude:" + ll.longitude);
						tvSnippet.setText(marker.getSnippet());

						return v;
					}
				});

				mMap.setOnMapLongClickListener(new OnMapLongClickListener() { // long
																				// click
																				// marker
																				// option

					@Override
					public void onMapLongClick(LatLng ll) {
						// TODO Auto-generated method stub
						Geocoder gc = new Geocoder(MainActivity.this);
						List<Address> list = null;
						try {
							list = gc.getFromLocation(ll.latitude,
									ll.longitude, 1);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							return;
						}
						Address add = list.get(0);
						MainActivity.this.setMarker(add.getLocality(),
								add.getCountryName(), ll.latitude, ll.longitude);
					}
				});

				mMap.setOnMarkerClickListener(new OnMarkerClickListener() {

					@Override
					public boolean onMarkerClick(Marker marker) {
						// TODO Auto-generated method stub
						String msg = marker.getTitle() + "("
								+ marker.getPosition().latitude + ","
								+ marker.getPosition().longitude + ")";
						Toast.makeText(MainActivity.this, msg,
								Toast.LENGTH_LONG).show();
						return false;
					}
				});

				mMap.setOnMarkerDragListener(new OnMarkerDragListener() {

					@Override
					public void onMarkerDragStart(Marker marker) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onMarkerDragEnd(Marker marker) {
						// TODO Auto-generated method stub

						Geocoder gc = new Geocoder(MainActivity.this);
						List<Address> list = null;
						LatLng ll = marker.getPosition();
						try {
							list = gc.getFromLocation(ll.latitude,
									ll.longitude, 1);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							return;
						}
						Address add = list.get(0);
						marker.setTitle(add.getLocality());
						marker.setSnippet(add.getCountryName());
						marker.showInfoWindow();
					}

					@Override
					public void onMarkerDrag(Marker marker) {
						// TODO Auto-generated method stub

					}
				});

			}
		}
		return (mMap != null);
	}

	private void gotoLocation(double lat, double lng, float zoom) {
		// TODO Auto-generated method stub
		LatLng ll = new LatLng(lat, lng);
		CameraUpdate update = CameraUpdateFactory.newLatLngZoom(ll, zoom);
		mMap.moveCamera(update);
	}

	public void geoLocate(View v) { // button click options
		try {
			hideSoftKeyboard(v);
			EditText et = (EditText) findViewById(R.id.editText1);
			String location = et.getText().toString();
			if (location.length() == 0) {
				Toast.makeText(this, "Please Enter the Location",
						Toast.LENGTH_SHORT).show();
				return;
			} else {
				Toast.makeText(this, "processing", Toast.LENGTH_SHORT).show();

				Geocoder gc = new Geocoder(this);

				List<Address> list = gc.getFromLocationName(location, 8);
				Address add = list.get(0);
				String locality = add.getLocality();
				Toast.makeText(this, locality, Toast.LENGTH_LONG).show();

				String country = add.getCountryName();
				double lat = add.getLatitude();
				double lng = add.getLongitude();
				gotoLocation(lat, lng, DZOOM);
				if (marker != null) {
					marker.remove();
				}
				MarkerOptions options = new MarkerOptions().title(locality)
						.position(new LatLng(lat, lng))
						// .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_plaidmarker));
						.icon(BitmapDescriptorFactory
								.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
				if (country.length() > 0) {
					options.snippet(country);
				}
				marker = mMap.addMarker(options);

			}
		} catch (IOException e) {
			e.printStackTrace();

		}

	}

	private void hideSoftKeyboard(View v) {
		// TODO Auto-generated method stub
		InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
	}

	public boolean onOptionsItemSelected(MenuItem item) { // main menu selection
		// TODO Auto-generated method stub
		switch (item.getItemId()) {

		case R.id.mapTypeNormal:
			mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
			break;

		case R.id.mapTypeHybrid:
			mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
			break;

		case R.id.mapTypeSatellite:
			mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
			break;

		case R.id.mapTypeTerrain:
			mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
			break;

		case R.id.setAddress:
			setAddress(View);
			break;
		case R.id.AddMarker:
			try {
				AddMarkers(View);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	public void AddMarkers(View v) throws JSONException { // Database address marker function

		AsyncTaskRunner asynctask = new AsyncTaskRunner();
		String result = null;
		try {
			result = asynctask.execute().get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		JSONArray jArray = new JSONArray(result);
		for (int i = 0; i < jArray.length(); i++) {
			JSONObject json = jArray.getJSONObject(i);
					 
			Geocoder gc = new Geocoder(this);

			List<Address> list = null;
			try {
				list = gc.getFromLocationName(json.getString("Address"), 8);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Address add = list.get(0);
			String locality = add.getLocality();
			Toast.makeText(this, locality, Toast.LENGTH_LONG).show();

			String country = add.getCountryName();
			double lat = add.getLatitude();
			double lng = add.getLongitude();
			gotoLocation(lat, lng, DZOOM);
			
			MarkerOptions options = new MarkerOptions().title(locality)
			.position(new LatLng(lat, lng))
			// .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_plaidmarker));
			.icon(BitmapDescriptorFactory
					.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
		
			 marker = mMap.addMarker(options);
		}

	
			


	}

	private class AsyncTaskRunner extends AsyncTask<String, Void, String> { // calling
																			// asynctask
																			// runner

		@Override
		protected String doInBackground(String... Params)

		{
			String result = "";
			InputStream isr = null;
			try {
				DefaultHttpClient httpclient = new DefaultHttpClient();
				HttpPost httppost = new HttpPost(
						"http://www.phyrates.mwmalith.com/My/setLocations.php");
				HttpResponse response = httpclient.execute(httppost);
				HttpEntity entity = response.getEntity();
				isr = entity.getContent();

			} catch (Exception e) {
				Log.e("log_tag", " error in http connection" + e.toString());
				return "couldnt connect to data base";

			}

			try {

				BufferedReader reader = new BufferedReader(
						new InputStreamReader(isr, "iso-8859-1"), 8);
				StringBuilder sb = new StringBuilder();
				String line = null;
				while ((line = reader.readLine()) != null) {
					sb.append(line + "\n");
				}
				isr.close();

				result = sb.toString();

			} catch (Exception e) {
				Log.e("log_log", "Error converting" + e.toString());
			}

			String s = "";

			// parse JSON code
			try {

			}

			catch (Exception e) {
				Log.e("log_tag", "error parsing data" + e.toString());
			}
			return result;
		}

		@Override
		protected void onPostExecute(String result) {

			// TextView resultView=(TextView) findViewById(R.id.results);
			// resultView.setText(result);
			


		}

	}

	public void setAddress(View v) { // print previous raid locations
		Intent i = new Intent(this, Destination.class);
		startActivity(i);

	}

	@Override
	protected void onStart() {
		super.onStart();
		initMap();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		MapStateManager mgr = new MapStateManager(this);
		mgr.saveMapState(mMap);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onRestart();
		MapStateManager mgr = new MapStateManager(this);
		CameraPosition position = mgr.getSavedCameraPosition();
		if (position != null) {
			CameraUpdate update = CameraUpdateFactory
					.newCameraPosition(position);
			mMap.moveCamera(update);
		}

	}

	public void setMarker(String locality, String country, double lat,
			double lng) {

		MarkerOptions options = new MarkerOptions()
				.title(locality)
				.position(new LatLng(lat, lng))
				// .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_plaidmarker));
				.icon(BitmapDescriptorFactory
						.defaultMarker(BitmapDescriptorFactory.HUE_RED))
				.draggable(true);

		mMap.addMarker(options);
		if (country.length() > 0) {
			options.snippet(country);
		}
		marker = mMap.addMarker(options);
	}

}