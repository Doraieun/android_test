package doraieun.testapp4;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapGoogleActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Marker marker;
    private LocationManager locationManager;
    private double x, y;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_google);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);//onMapReady를 callback으로 사용

        System.out.println("############### mapFragment.getMap() : " + mapFragment.getMap().toString());

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        System.out.println("############### googleMap : " + googleMap.toString());

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if(googleMap == mapFragment.getMap()) System.out.println("@@@@@@@@@@@@@@@@@@@@@@@");

        // Add a marker in Sydney and move the camera
        //LatLng sydney = new LatLng(-34, 151);
        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        mMap.setMyLocationEnabled(true);

        locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);

        boolean gps_enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean network_enabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        //boolean passive_enabled = locationManager.isProviderEnabled(LocationManager.PASSIVE_PROVIDER); 다른앱에서 사용된 위치정보를 가져오기

        //GPS를 사용한 위치정보 갱신
        //locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListenerGPS);

        //Network provider를 사용한 위치정보 갱신
        //parameter(GPS/NETWORK, 갱신시간(msec), 갱신거리, listener)
        //갱신시간과 갱신거리가 동시에 만족해야 갱신되는듯
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 10, locationListenerNetwork);

        //Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        //System.out.println("init location.getLatitude() : " + location.getLatitude());
        //System.out.println("init location.getLongitude() : " + location.getLongitude());

        mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker in MyLocation"));

    }

    LocationListener locationListenerNetwork = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            //이전에 표기된 Marker 삭제
            if(marker != null) marker.remove();

            //현재 위도,경도
            x = location.getLatitude();
            y = location.getLongitude();

            System.out.println("listener location.getLatitude() " + location.getLatitude());
            System.out.println("listener location.getLongitude() " + location.getLongitude());

            LatLng myLoc = new LatLng(x, y);

            marker = mMap.addMarker(new MarkerOptions().position(myLoc).title("Marker in MyLocation"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(myLoc));//Map이동
            mMap.animateCamera(CameraUpdateFactory.zoomTo(13));//줌인

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };
}
