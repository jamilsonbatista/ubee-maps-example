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
        String mapsKey = "";
        String mapsSecret = "";

        
        
        Ubee.setMapsApp(mapsKey, mapsSecret);
        Ubee.setCacheSizeLimit(10485760L); //10MB

    }
}
