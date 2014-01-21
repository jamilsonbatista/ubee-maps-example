Ubee Maps
========
For Android SDK 8+

###Getting Started - Version 0.1.1

 - Create your application [**here**] [create_app]
 - Download the jar [**here**] [thin_jar]
 - View the example project [**here**] [maps_demo_project]
 - Javadoc [Under Construction]

####Dependencies
- [Android Support Library v4:] [android_support_library]  [**download here**] [v4_download_link]

#### Add the jar file:
 - Create a folder called "libs" in your Android project.
 - Move the jar to the libs folder.

##Android Manifest.xml
Write the lines below in your AndroidManifest.xml file 

```xml
<application> 
[...]
<service android:name="in.ubee.api.location.LocationService" android:exported="false"/>
</aplication>
```
###Required Permissions

```xml
<manifest>
[...]
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
<uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
<uses-permission android:name="android.permission.CHANGE_WIFI_STATE" />
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
</manifest>
```

###Additional Permissions
To allow the application to write it's temporary data on the external storage instead of the application internal memory
```xml
<manifest>
[...]
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
</manifest>
```

##Initializating
You will need to use your application id (app_id) and secret (app_secret) that you received when you created your application [**here**] [create_app].
Just add the lines below in your **Application** onCreate method.
**Warning** Do not put it in your activity for the current version.
```java
Ubee.setLogsVisible(true);
Ubee.init(this.getApplicationContext());
String appId = "your_app_id";
String appSecret = "your_app_secret";
Ubee.setMapsApp(appId, appSecret);
Ubee.setCacheSizeLimit(10485760L); //10MB
```
----------------------
Usage Indoor Location
===
- Register/Unregister a context to receive the device current indoor location

```java
private OnMapsLocationListener mLocationListener = new OnMapsLocationListener() {
    
	@Override
	public void onLocationChanged(Location location) {
	}
	
	@Override
	public void onError(LocationError locationError) {
	}
};

@Override
protected void onResume() {
    super.onResume();
    Ubee.registerLocationCallback(this, mLocationListener);
}

@Override
protected void onPause() {
    super.onPause();
    Ubee.unregisterLocationCallback(this, mLocationListener);
}
```

------------------
Request User Retails
==
Request the list of the retails authorized for the user application 
```java
JSONObject retailsAsJson = Ubee.requestRetails();
List<Retail> retails = Retail.parseListFromJSON(retailsAsJson);
```

------------------
Usage IndoorMapView
==

###Current Features: 
- Zoom
- Translation
- Fling
- SVG Parser

###QuickStart:
- Load a retail at the view
- Receive the list of retail maps loaded for the selected retail on the method **onRetailLoadFinished(Retail retail, List<RetailMap> retailMaps)**
- Use one of the following methods switch floors (No map will be shown until any of the following methods are run)

```java
public void setPreviousFloor();
public void setNextFloor();
public void setFloor(int floor);
```

###Example

```java

public class MyClass extends Activity {
    private IndoorMapView mIndoorMapView;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mIndoorMapView = new IndoorMapView(this);
        mIndoorMapView.setRetailById("your_retail_id", new OnMapViewLoadListener() {
        
            		@Override
		    		public void onRetailMapLoadFinished(RetailMap retailMap) {
                    }
				
			    	@Override
			    	public void onRetailLoadFinished(Retail retail, List<RetailMap> retailMaps) {
                        mIndoorMapView.setNextFloor();
			    	}
				
			    	@Override
			    	public void onLoadError(UbeeAPIException e) {
			    		e.printStackTrace();
			    	}
	        });
        this.setContentView(mIndoorMapView);
	}
}
```
####You can also validate if there is a floor to be shown on the next/previous methods.
```java
public boolean hasNextFloor();
public boolean hasPreviousFloor();
```
###Loading the user position
```java 
public void setLocationPoint(Location newLocationPoint);
```

####Usage
Steps:
- Create your IndoorMapView.
- Create the OnMapsLocationListener
- Pass the callback of OnLocationChanged(Location location) to the IndoorMapView.
- Register the LocationCallback when you want to start tracking
- Unregister the LocationCallback when you want to stop tracking

####Example
```java
IndoorMapView mIndoorMapView = [...];
OnMapsLocationListener mLocationListener =  new OnMapsLocationListener() {

    @Override
    public void onLocationChanged(Location location) {
	    mIndoorMapView.setLocationPoint(location);	
    }

	@Override
		public void onError(LocationError locationError) {
	}
};
Ubee.registerLocationCallback(context, mLocationListener);
[...]
Ubee.unregisterLocationCallback(context, mLocationListener);

```
---
###Disable Logs
```java
Ubee.setLogsVisible(false);
```

  [android_support_library]: http://developer.android.com/tools/support-library/setup.html
  [v4_download_link]: https://s3.amazonaws.com/mobile-api/android-support-v4.jar
  [thin_jar]: https://s3.amazonaws.com/mobile-api/0.2/Maps/ubee-api-0.2.jar
  [maps_demo_project]: https://github.com/ubee/ubee-maps-example
  [create_app]: http://maps.ubee.in/oauth/applications/new




