Ubee Maps
========
For Android SDK 8+

###Getting Started - Version 0.1.1

 - Create your application [**here**] [create_app]
 - Download the jar [**here**] [thin_jar]
 - View the example project [**here**] [ads_demo_project]

####Dependencies
[Android Support Library v4] [android_support_library]  [**here**] [v4_download_link]

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
Ubee.init(this.getApplicationContext());
String appId = "your_app_id";
String appSecret = "your_app_secret";
Ubee.setMapsApp(appId, appSecret);
Ubee.setCacheSizeLimit(10485760L); //10MB
```
----------------------
Usage Indoor Location
===
Register an Activity to receive the device current indoor location

```java
@Override
protected void onResume() {
    super.onResume();
    Ubee.registerLocationCallback(this, Ubee.registerLocationCallback(this, new OnMapsLocationListener() {
            
            @Override
            public void onLocationChanged(Location location) {
                
            }
            
            @Override
            public void onError(LocationError error) {
            }
        });
}

@Override
protected void onPause() {
    super.onPause();
    Ubee.unregisterLocationCallback(this, this);
}
```
------------------
Usage IndoorMapView
===

###Current Features: 
- Zoom
- Translation
- Fling
- SVG


###Loading a Retail
```java
@Override
protected void onCreate() {
    super.onResume();
    IndoorMapView indoorMapView = new IndoorMapView(this);
        mIndoorMapView.setRetailById("513df2911613e75c53000014", new OnMapViewLoadListener() {
        
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
		}
}
```
###Loading the user position (method signature)
> public void setLocationPoint(Location newLocationPoint);

####Usage
```java
    IndoorMapView indoorMapView = [...];
    Ubee.registerLocationCallback(this, Ubee.registerLocationCallback(this, new OnMapsLocationListener() {
            
            @Override
            public void onLocationChanged(Location location) {
                indoorMapView.setLocationPoint(location);
            }
            
            @Override
            public void onError(LocationError error) {
            }
        });
```

  [android_support_library]: http://developer.android.com/tools/support-library/setup.html
  [v4_download_link]: https://s3.amazonaws.com/mobile-api/android-support-v4.jar
  [thin_jar]: https://s3.amazonaws.com/mobile-api/0.1.3/Ads/ubee-api-0.1.3.jar
  [ads_demo_project]: https://github.com/ubee/ubee-ads-example
  [bottom_banner_image_example]: https://dl.dropboxusercontent.com/u/31445445/banner_example/BOTTOM_BANNER.png "Bottom Banner Example"
  [top_banner_image_example]: https://dl.dropboxusercontent.com/u/31445445/banner_example/TOP_BANNER.png "Top Banner Example"
  [create_app]: http://maps.ubee.in/oauth/applications/new


