package doraieun.testapp4;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResolvingResultCallbacks;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.AddPlaceRequest;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.AutocompletePredictionBuffer;
import com.google.android.gms.location.places.GeoDataApi;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.PlacePhotoMetadataResult;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.zip.Inflater;

public class MapGoogleActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Marker marker;
    private LocationManager locationManager;
    private double x, y;

    private LatLng longClicklatLng;

    private HashMap<String, String> placeIdMap;

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
    public void onMapReady(final GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                longClicklatLng = latLng;
                System.out.println("################ MapLongClickListener");

                //
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final LinearLayout linearLayout = (LinearLayout)inflater.inflate(R.layout.text_input, null);
                //TextView textView = (TextView) linearLayout.findViewById(R.id.locName);

                //getApplicationContext()사용시 error, Activity명.this 사용시 정상
                AlertDialog.Builder inputLocName = new AlertDialog.Builder(MapGoogleActivity.this);
                inputLocName.setTitle("input location name");
                inputLocName.setView(linearLayout);
                inputLocName.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText editText = (EditText) linearLayout.findViewById(R.id.locName);
                        System.out.println("############# editText.getText() : " + editText.getText());

                        marker = mMap.addMarker(new MarkerOptions().position(longClicklatLng).title(editText.getText().toString()));
                    }
                });

                inputLocName.show();



                // 키보드 띄우기
                //InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                //mgr.showSoftInput(cy_id, InputMethodManager.SHOW_IMPLICIT);
            }
        });

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {

            }
        });

        System.out.println("############### googleMap : " + googleMap.toString());

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if(googleMap == mapFragment.getMap()) System.out.println("@@@@@@@@@@@@@@@@@@@@@@@");

        // Add a marker in Sydney and move the camera
        //LatLng sydney = new LatLng(-34, 151);
        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        //mMap.setMyLocationEnabled(true);
        UiSettings uiSettings = mMap.getUiSettings();
        uiSettings.setAllGesturesEnabled(true);
        uiSettings.setCompassEnabled(true);
        uiSettings.setIndoorLevelPickerEnabled(true);
        uiSettings.setMapToolbarEnabled(true);
        uiSettings.setMyLocationButtonEnabled(true);
        uiSettings.setRotateGesturesEnabled(true);
        uiSettings.setScrollGesturesEnabled(true);
        uiSettings.setTiltGesturesEnabled(true);
        uiSettings.setZoomControlsEnabled(true);
        uiSettings.setZoomGesturesEnabled(true);

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
        mMap.setOnMarkerClickListener(onMarkerClickListener);




        Button searchButton = (Button)findViewById(R.id.button5);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("############# SearchButton onClick!");
                callThread();

            }
        });
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

    GoogleMap.OnMarkerClickListener onMarkerClickListener = new GoogleMap.OnMarkerClickListener() {
        @Override
        public boolean onMarkerClick(Marker marker) {
            System.out.println("############## onMarkerClick!!!!");

            System.out.println("############## marker.getTitle() : " + marker.getTitle());
            System.out.println("############## marker.getSnippet() : " + marker.getSnippet());
            System.out.println("############## marker.getPosition().latitude : " + marker.getPosition().latitude);
            System.out.println("############## marker.getPosition().longitude : " + marker.getPosition().longitude);

            String mapKey = String.valueOf(marker.getPosition().latitude) + "," + String.valueOf(marker.getPosition().longitude);
            String placeId = placeIdMap.get(mapKey);

            GoogleApiClient.Builder googleApiClientBuilder = new GoogleApiClient.Builder(MapGoogleActivity.this);
            googleApiClientBuilder.addApi(Places.GEO_DATA_API);
            googleApiClientBuilder.addApi(Places.PLACE_DETECTION_API);
            googleApiClientBuilder.addApi(LocationServices.API);

            GoogleApiClient googleApiClient = googleApiClientBuilder.build();
            googleApiClient.connect();

            PendingResult<PlaceBuffer> pendingResult = Places.GeoDataApi.getPlaceById(googleApiClient, placeId);
            pendingResult.setResultCallback(new ResultCallback<PlaceBuffer>() {
                @Override
                public void onResult(PlaceBuffer places) {
                    System.out.println("############## getPlaceById Callback : " + places);
                    System.out.println("############## places.getStatus() : " + places.getStatus());
                }
            });



            TextView textName = (TextView)findViewById(R.id.textName);
            TextView textLat = (TextView)findViewById(R.id.textLat);
            TextView textLng = (TextView)findViewById(R.id.textLng);
            textName.setText(marker.getTitle());
            textLat.setText(String.valueOf(marker.getPosition().latitude));
            textLng.setText(String.valueOf(marker.getPosition().longitude));

            return true;
        }
    };


    private void callThread(){

        // 주위 정보 조회
        // android.os.NetworkOnMainThreadException, illegalException:not on the main thread url
        //  ==> 메인 Method에서는 UI 관련 작업등 필수 요소만 담당하고, 네트워크 작업등 지연요소가 있는 행위를
        // 메인 스레드에서 할 수 없도록 제한이 추가됨(android 3.0 이후)
        // 발생시 처리방법
        // 1. AsyncTask를 이용한 별도 thread로 처리
        // 2. 아래 구문 추가
//            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//            StrictMode.setThreadPolicy(policy);
        try{

            if(marker != null) marker.remove();

            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            System.out.println("############################ x : " + x);
            System.out.println("############################ y : " + y);

            TextView searchTextView = (TextView)findViewById(R.id.editText);
            CharSequence charSequence = searchTextView.getText();
            String searchWord = charSequence.toString();

            System.out.println("###################### searchWord : " + searchWord);

            StringBuilder responseBuilder = new StringBuilder();
            String searchUrl = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" + x + "," + y + "&radius=10000&types=&name=" + URLEncoder.encode(searchWord, "UTF-8") + "&key=AIzaSyDN1QX-gWUR-mIYo_D21PNFLHHpNQkIkGU";
            System.out.println("######## searchUrl : " + searchUrl);
            //URL url = new URL("http://ajax.googleapis.com/ajax/services/search/local?v=1.0&q="+ URLEncoder.encode("커피", "UTF-8")+"&sll="+ x + "," + y + "&hl=kr");
            URL url = new URL(searchUrl);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()));
            /*
            {
               "html_attributions" : [],
               "next_page_token" : "CoQC-wAAAIL0Z1HYdB1WTT_cYSAhpdye2NSOsjfqwfMVxLIQcKsd69H6OdRa4dQpgPVRlIUlMcSOVElKlNsXkq53pCwyoXPpVRIxD2FFyuBboXTXR6gLCcUHs8k5v2v_2tnhYUe2j1nJkTQNUt1VfywQzwTZxSQ70hN7J8px5--ErBbq68xJx7dIGYDcqN6Kw99fJFpn_Mypdxw64c-TIdoiNa3eB9ZapfWcWu5BqCLIeeWg0kRqn6rQUbXTL3Jawf14QibjHNz5BcIpcW-_dIscYIZjsVutD2fUy6zP4FzXLlootxbYgfZQMtpzPS3TiwSWOF190Mi8zgyTqSHOJi0d9S6kx4ESEMGqZAlgSnWnfLdZzeKegUwaFKsp1QtNm693QIbmCLImOtSD1cGs",
               "results" : [
                  {
                     "geometry" : {
                        "location" : {
                           "lat" : 35.1900447,
                           "lng" : 128.605213
                        }
                     },
                     "icon" : "https://maps.gstatic.com/mapfiles/place_api/icons/generic_business-71.png",
                     "id" : "8dea01e4973f5e6b5228b4be09bcce08169a2e59",
                     "name" : "두산",
                     "place_id" : "ChIJtQ8_N5ktbzURHAFTgznLkPc",
                     "reference" : "CmRaAAAAYi2D1aem7_pYg6yl5RowMRJ4_NEmn7_Okh9PQawCPetKHOAhBVuEEycYB0VPRJlnPUvyjR1dz34an9cMysvf-qBRfKoOEV0-jl_VKRfxLqzENxPncdFd43VlZ4ggDn3JEhA1Lw9vTscAwZ5lg7M59FRQGhQRGiySyTZaPCtIAGq_tB5HKLfhJg",
                     "scope" : "GOOGLE",
                     "types" : [ "establishment" ],
                     "vicinity" : "창원시 성산구"
                  },
                  {
                     "geometry" : {
                        "location" : {
                           "lat" : 35.1797958,
                           "lng" : 128.6031301
                        }
                     },
                     "icon" : "https://maps.gstatic.com/mapfiles/place_api/icons/generic_business-71.png",
                     "id" : "1d692540c80d833155fedef625029ec98d352b0e",
                     "name" : "두산중공업",
                     "place_id" : "ChIJ-9FyLQgtbzUR5Aq2iBOGe4c",
                     "reference" : "CnRjAAAAi8E3Vj4RY-J10E5iFGW4fATYXOwmOc7h8y-MJnwz4M0pRmMRczCFi7Mjk2_SM7lJvR7506GNzM7X6cIYE85mkWq48XV-tixoECX7Sjt6S5jBFFUe2hdxCN5SxdCq04B7UsQUu2-uqbIvEEEcwefwWxIQpMGOfRbY6GE1VFMvgEdYAxoU3BZCOzR0Y-ayCZfkR4N-1yhyFnk",
                     "scope" : "GOOGLE",
                     "types" : [ "point_of_interest", "establishment" ],
                     "vicinity" : "창원시 성산구 두산볼보로 22"
                  }
             }
             */
            String inputLine;
            while((inputLine = bufferedReader.readLine()) != null){
                responseBuilder.append(inputLine);
            }
            bufferedReader.close();

            System.out.println("############################");
            System.out.println(responseBuilder.toString());
            System.out.println("############################");

            //Marker
            JSONObject searchResultObject = new JSONObject(responseBuilder.toString());
            JSONArray searchResultList = searchResultObject.getJSONArray("results");
            for(int i = 0; i < searchResultList.length(); i++){
                JSONObject searchResult = (JSONObject) searchResultList.get(i);
                String storeName = (String)searchResult.get("name");
                String storeAdr = (String)searchResult.get("vicinity");
                String storeIco = (String)searchResult.get("icon");
                //System.out.println("############ Store Name : " + storeName);
                //System.out.println("############ Store Name : " + storeAdr);

                JSONObject geoMetryObject = (JSONObject) searchResult.getJSONObject("geometry");
                JSONObject locationObject = (JSONObject) geoMetryObject.getJSONObject("location");
                //System.out.println("########### lat : " + locationObject.get("lat"));
                //System.out.println("########### lng : " + locationObject.get("lng"));

                Double aroundLat = (Double)locationObject.get("lat");
                Double aroundLng = (Double)locationObject.get("lng");

                LatLng aroundLoc = new LatLng(aroundLat, aroundLng);
                MarkerOptions markerOptions= new MarkerOptions();
                markerOptions.position(aroundLoc);
                markerOptions.title(storeName);
                markerOptions.snippet(storeAdr);


                URL icoUrl = new URL(storeIco);
                Bitmap icoBmp = BitmapFactory.decodeStream(icoUrl.openConnection().getInputStream());
                markerOptions.icon(BitmapDescriptorFactory.fromBitmap(icoBmp));
                marker = mMap.addMarker(new MarkerOptions().position(aroundLoc).title(storeName));

                placeIdMap = new HashMap<String, String>();
                String mapKey = String.valueOf(aroundLat) + "," + String.valueOf(aroundLng);
                String mapValue = (String)searchResult.get("place_id");
                placeIdMap.put(mapKey, mapValue);

            }

        }catch (Exception e){
            System.out.println("##### 주위정보조회 fail");
            System.out.println("##### " + e.toString());
        }

        //SearchThread searchThread = new SearchThread();
        //searchThread.execute();

//        Thread thread = new Thread(new Runnable(){
//            @Override
//            public void run() {
//                try{
//                    SearchThread searchThread = new SearchThread();
//                    searchThread.execute();
//
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
//            }
//        });
//
//        thread.run();

    }


    private class SearchThread extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {


            return null;
        }
    }


    //Test Commit in Github.com
}

