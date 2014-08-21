package in.ubee.example.maps;

import in.ubee.android.view.IndoorMapView;
import in.ubee.api.Ubee;
import in.ubee.api.exception.UbeeAPIException;
import in.ubee.api.location.LocationError;
import in.ubee.api.maps.OnMapsLocationListener;
import in.ubee.api.models.Location;
import in.ubee.api.ui.listener.OnMapViewLoadListener;
import in.ubee.models.Retail;
import in.ubee.models.RetailMap;
import in.ubee.models.exceptions.InvalidMappingException;

import java.util.List;

import org.json.JSONObject;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;

public class MainActivity extends Activity implements OnClickListener {
	private IndoorMapView mIndoorMapView;
	private ImageButton mNextFloorButton;
	private ImageButton mPreviousFloorButton;

	private OnMapsLocationListener mLocationListener;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ViewGroup mapContainer = (ViewGroup) this.findViewById(R.id.map_container);
		mPreviousFloorButton = (ImageButton) this.findViewById(R.id.previous_floor);
		mNextFloorButton = (ImageButton) this.findViewById(R.id.next_floor);
		mIndoorMapView = new IndoorMapView(this);
		mapContainer.addView(mIndoorMapView);
		mNextFloorButton.setEnabled(mIndoorMapView.hasNextFloor());
		mPreviousFloorButton.setEnabled(mIndoorMapView.hasPreviousFloor());

		mLocationListener = new OnMapsLocationListener() {
			
			@Override
			public void onLocationChanged(Location location) {
				mIndoorMapView.setUserLocation(location);	
			}
			
			@Override
			public void onError(LocationError locationError) {
				// TODO Auto-generated method stub
				
			}
		};

		new LoadMapAsyncTask().execute();
	}

	@Override
	protected void onStart() {
		super.onStart();
		Ubee.registerLocationCallback(this, mLocationListener);
	};

	@Override
	protected void onStop() {
		super.onStop();
		Ubee.registerLocationCallback(this, mLocationListener);
	}
	
	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.next_floor) {
			mIndoorMapView.setNextFloor();
		} else if (v.getId() == R.id.previous_floor) {
			mIndoorMapView.setPreviousFloor();
		}
	}

	class LoadMapAsyncTask extends AsyncTask<Void, Void, Retail> {

		@Override
		protected Retail doInBackground(Void... params) {
			try {
				JSONObject retailsAsJson = Ubee.requestRetails(MainActivity.this);
				List<Retail> retails = Retail.parseListFromJSON(retailsAsJson);
				return retails.get(0);
			} catch (UbeeAPIException e) {
				e.printStackTrace();
			} catch (InvalidMappingException e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Retail retail) {
			super.onPostExecute(retail);
			
			mIndoorMapView.setRetail(retail, new OnMapViewLoadListener() {

				@Override
				public void onRetailLoadFinished(Retail retail, List<RetailMap> retailMaps) {
					mIndoorMapView.setNextFloor();
					mPreviousFloorButton.setOnClickListener(MainActivity.this);
					mNextFloorButton.setOnClickListener(MainActivity.this);
				}
				
				@Override
				public void onRetailMapLoadFinished(RetailMap retailMap) { 
					mNextFloorButton.setEnabled(mIndoorMapView.hasNextFloor());
					mPreviousFloorButton.setEnabled(mIndoorMapView.hasPreviousFloor());
				}


				@Override
				public void onLoadError(UbeeAPIException e) {
					e.printStackTrace();
				}
			});
		}
	}
}
