package in.ubee.example.maps;

import in.ubee.api.Ubee;
import in.ubee.api.exception.UbeeAPIException;
import in.ubee.api.ui.listener.OnMapViewLoadListener;
import in.ubee.api.ui.views.IndoorMapView;
import in.ubee.models.Retail;
import in.ubee.models.RetailMap;
import in.ubee.models.exceptions.InvalidMappingException;

import java.util.List;

import org.json.JSONObject;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.ViewGroup;

public class MainActivity extends Activity implements OnMapViewLoadListener {
	private IndoorMapView mIndoorMapView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ViewGroup mapContainer = (ViewGroup) this.findViewById(R.id.map_container);
		mIndoorMapView = new IndoorMapView(this);
		mapContainer.addView(mIndoorMapView);
		new LoadMapAsyncTask().execute();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	
	class LoadMapAsyncTask extends AsyncTask<Void, Void, Retail>{

		@Override
		protected Retail doInBackground(Void... params) {
			try {
				JSONObject retailsAsJson = Ubee.requestRetails();
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
			mIndoorMapView.setRetail(retail, MainActivity.this);
		}
	}


	@Override
	public void onLoadError(UbeeAPIException e) {
		e.printStackTrace();
		
	}

	@Override
	public void onRetailLoadFinished(Retail retail, List<RetailMap> retailMaps) {
		Log.d("TESTANDO", "RETAIL LOAD FINISHED " + retail.getId());
		mIndoorMapView.setNextFloor();
	}

	@Override
	public void onRetailMapLoadFinished(RetailMap retailMap) {
		Log.d("TESTANDO", "RETAIL MAP LOAD FINISHED " + retailMap.getFloor());
	}
}
