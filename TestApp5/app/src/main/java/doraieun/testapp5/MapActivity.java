package doraieun.testapp5;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import com.nhn.android.maps.NMapActivity;
import com.nhn.android.maps.NMapController;
import com.nhn.android.maps.NMapView;
import com.nhn.android.maps.maplib.NGeoPoint;
import com.nhn.android.maps.nmapmodel.NMapError;

/**
 * Created by e900537 on 2015-12-03.
 */
public class MapActivity extends NMapActivity implements NMapView.OnMapStateChangeListener, NMapView.OnMapViewTouchEventListener {
    public static final String MAP_API_KEY = "e709a2c994a6a3c3edae4e66f6e652fe";
    NMapView nMapView = null;
    NMapController nMapController = null;
    LinearLayout mapContainer = null;

    private SharedPreferences nPreferences;

    private static final NGeoPoint NMAP_LOCATION_DEFAULT = new NGeoPoint(126.978371, 37.5666091);
    private static final int NMAP_ZOOMLEVEL_DEFAULT = 11;

    private static final String KEY_ZOOM_LEVEL = "NMapViewer.zoomLevel";
    private static final String KEY_CENTER_LONGITUDE = "NMapViewer.centerLongitudeE6";
    private static final String KEY_CENTER_LATITUDE = "NMapViewer.centerLatitudeE6";
    private static final String KEY_VIEW_MODE = "NMapViewer.viewMode";
    private static final String KEY_TRAFFIC_MODE = "NMapViewer.trafficMode";
    private static final String KEY_BICYCLE_MODE = "NMapViewer.bicycleMode";

    double x,y;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_sub);

        //MAP 처리
        nMapView = new NMapView(this);
        nMapView.setApiKey(MAP_API_KEY);
        setContentView(nMapView);
        nMapView.setClickable(true);
        nMapView.setOnMapStateChangeListener(this);
        nMapView.setOnMapStateChangeListener(this);

        nMapController = nMapView.getMapController();
    }

    private void restoreInstanceState() {


        LocationManager lm = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        if(checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) return;

        Location loc = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        System.out.println("loc.getLatitude() : " + loc.getLatitude());
        System.out.println("loc.getLongitude() : " + loc.getLongitude());

        //System.out.println("Network 위치정보 활성화 : " + lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER));
/*
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    public void requestPermissions(@NonNull String[] permissions, int requestCode)
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            //return;
        }


        lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, new LocationListener() {
            public void onLocationChanged(Location location) {
                //timer.cancel();
                x = location.getLatitude();
                y = location.getLongitude();
                //lm.removeUpdates(this);
                //lm.removeUpdates(locationListenerGps);
                //Context context = getApplicationContext();
            }

            public void onProviderDisabled(String provider) {
            }

            public void onProviderEnabled(String provider) {
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

        });
*/

        nPreferences= getPreferences(MODE_PRIVATE);

        int longitudeE6 = nPreferences.getInt(KEY_CENTER_LONGITUDE, NMAP_LOCATION_DEFAULT.getLongitudeE6());
        int latitudeE6 = nPreferences.getInt(KEY_CENTER_LATITUDE, NMAP_LOCATION_DEFAULT.getLatitudeE6());
        int level = nPreferences.getInt(KEY_ZOOM_LEVEL, NMAP_ZOOMLEVEL_DEFAULT);
//        int viewMode = mPreferences.getInt(KEY_VIEW_MODE, NMAP_VIEW_MODE_DEFAULT);
//        boolean trafficMode = mPreferences.getBoolean(KEY_TRAFFIC_MODE, NMAP_TRAFFIC_MODE_DEFAULT);
//        boolean bicycleMode = mPreferences.getBoolean(KEY_BICYCLE_MODE, NMAP_BICYCLE_MODE_DEFAULT);

//        nMapController.setMapViewMode(viewMode);
//        nMapController.setMapViewTrafficMode(trafficMode);
//        nMapController.setMapViewBicycleMode(bicycleMode);
        nMapController.setMapCenter(new NGeoPoint(longitudeE6, latitudeE6), level);
        //nMapController.setMapCenter(new NGeoPoint(y, x),level);
        //nMapController.setMapCenter(new NGeoPoint(126.978371, 37.5666091), 11);

    }


    @Override
    public void onMapInitHandler(NMapView nMapView, NMapError nMapError) {
        if (nMapError == null) { // success
            // restore map view state such as map center position and zoom level.
            restoreInstanceState();

        } else { // fail
            //Log.e(LOG_TAG, "onFailedToInitializeWithError: " + errorInfo.toString());

            //Toast.makeText(NMapViewer.this, errorInfo.toString(), Toast.LENGTH_LONG).show();

            System.out.println("MAP initialize error." + nMapError.toString());
        }
    }

    @Override
    public void onMapCenterChange(NMapView nMapView, NGeoPoint nGeoPoint) {

    }

    @Override
    public void onMapCenterChangeFine(NMapView nMapView) {

    }

    @Override
    public void onZoomLevelChange(NMapView nMapView, int i) {

    }

    @Override
    public void onAnimationStateChange(NMapView nMapView, int i, int i1) {

    }

    @Override
    public void onLongPress(NMapView nMapView, MotionEvent motionEvent) {

    }

    @Override
    public void onLongPressCanceled(NMapView nMapView) {

    }

    @Override
    public void onTouchDown(NMapView nMapView, MotionEvent motionEvent) {

    }

    @Override
    public void onTouchUp(NMapView nMapView, MotionEvent motionEvent) {

    }

    @Override
    public void onScroll(NMapView nMapView, MotionEvent motionEvent, MotionEvent motionEvent1) {

    }

    @Override
    public void onSingleTapUp(NMapView nMapView, MotionEvent motionEvent) {

    }
}
