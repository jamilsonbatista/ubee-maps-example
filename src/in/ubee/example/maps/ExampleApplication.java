package in.ubee.example.maps;

import in.ubee.api.Ubee;
import in.ubee.api.UbeeOptions;
import android.app.Application;

public class ExampleApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        this.ubeeSetup();
    }

    private void ubeeSetup() {
    	
    	UbeeOptions options = UbeeOptions.getInstance(this);

		options.setMapsKey(
				"4dbf36ace426cec4353900630b06c572d7b416bd23f7ba4aae0f2842e8ea1e38",
				"795931ff8c1344c2656416f157f99c858391fe8d003bf1e994f4176537f79af9");

		options.setLogEnabled(true);

		Ubee.init(this, options);
    }
}