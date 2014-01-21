package in.ubee.example.maps;

import in.ubee.api.Ubee;
import android.app.Application;

public class ExampleApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        this.ubeeSetup();
    }

    private void ubeeSetup() {
        Ubee.init(this);
        String mapsKey = "07be1c1963a2d5aab12dfb805a113bf28243a9c4c82136273af6b653ed989917";
        String mapsSecret = "5cf1baf2be27baad69d824de2a4d0af187db2c0065e0cf8766f351a880ef63e5";
        Ubee.setMapsApp(mapsKey, mapsSecret);
        Ubee.setCacheSizeLimit(10485760L);
    }
}