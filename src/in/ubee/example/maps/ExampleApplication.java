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
    	Ubee.setLogsVisible(true);
        Ubee.init(this);
        String mapsKey;
        String mapsSecret;

        mapsKey = "3a8b1958d98851a2a8513457ee13ecb1ebe5c5ff3d2fd9e1bd7a33ccccf0a75d";
        mapsSecret = "0a588131c1247e03339787d0781a79f05a6332b6b2cfa4062018ccdc11e2a493";
        
        Ubee.setMapsApp(mapsKey, mapsSecret);
        Ubee.setCacheSizeLimit(10485760L); //10MB

    }
}
