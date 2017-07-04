package app;

import com.example.login2.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

import android.support.v7.app.ActionBarActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.content.SharedPreferences;

public class MapStateManager extends ActionBarActivity {
private static final String LONGITUDE= "longitude";
private static final String LATITUDE="latitude";
private static final String ZOOM="zoom";
private static final String BEARING="bearing";
private static final String TILT="tilt";
private static final String MAPTYPE="MAPTYPE";
private static final String PREFS_NAME="MapCameraState";

private SharedPreferences mapStatePrefs;
public MapStateManager(Context context)
{
	mapStatePrefs=context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
}
public void saveMapState(GoogleMap map)
{
SharedPreferences.Editor editor= mapStatePrefs.edit();
CameraPosition position= map.getCameraPosition();
editor.putFloat(LATITUDE, (float) position.target.latitude);
editor.putFloat(LONGITUDE, (float)position.target.longitude);
editor.putFloat(ZOOM , (float)position.zoom);
editor.putFloat(TILT,(float)position.tilt);
editor.putFloat(BEARING, (float) position.bearing);
editor.putInt(MAPTYPE, map.getMapType());
editor.commit();
}
public CameraPosition getSavedCameraPosition()
{
	double latitude=mapStatePrefs.getFloat(LATITUDE, 0);
	if(latitude== 0)
	{
		return null;
	}
	double longitude=mapStatePrefs.getFloat(LONGITUDE, 0);
	
	LatLng target= new LatLng(latitude,longitude);
	
	float zoom =mapStatePrefs.getFloat(ZOOM, 0);
	float tilt =mapStatePrefs.getFloat(TILT, 0);
	float bearing =mapStatePrefs.getFloat(BEARING, 0);
	
	CameraPosition position= new CameraPosition(target, zoom, tilt, bearing);
	
	return position;
	
}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map_state_manager);
	}

	
	
	
	
	
	
	
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.map_state_manager, menu);
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
}
