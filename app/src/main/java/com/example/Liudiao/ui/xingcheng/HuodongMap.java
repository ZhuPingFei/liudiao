package com.example.Liudiao.ui.xingcheng;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.MapsInitializer;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.example.Liudiao.R;
import com.example.Liudiao.ui.huodong.Huodong;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.example.Liudiao.Welcome.isConnect;

public class HuodongMap extends AppCompatActivity {

    private ImageView back;

    private SharedPreferences preferences;
    SharedPreferences.Editor editor;
    private int current_banliId;

    private double myLontitude;
    private double myLatitude;


    private ViewPoiOverlay poiOverlay;
    private AMap aMap;
    MapView mMapView;

    private LatLonPoint centerpoint;

    List<PoiItem> poiItems = new ArrayList<>();

    private List<String> str = new ArrayList<>();
    private MyLocationStyle myLocationStyle;
    //??????AMapLocationClient?????????
    public AMapLocationClient mLocationClient = null;
    //???????????????????????????
    public AMapLocationListener mLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            if (aMapLocation != null) {
                if (aMapLocation.getErrorCode() == 0) {
                    myLontitude = aMapLocation.getLongitude();
                    myLatitude = aMapLocation.getLatitude();

                }
            }
        }

    };
    //??????AMapLocationClientOption??????
    public AMapLocationClientOption mLocationOption = null;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.huodong_map);

        preferences = getSharedPreferences("daiban", Activity.MODE_PRIVATE);
        editor = preferences.edit();
        current_banliId = preferences.getInt("current_banliId", 0);

        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });




        MapsInitializer.updatePrivacyShow(this, true, true);
        MapsInitializer.updatePrivacyAgree(this, true);
        mMapView = (MapView) findViewById(R.id.map);
        //???activity??????onCreate?????????mMapView.onCreate(savedInstanceState)???????????????
        mMapView.onCreate(savedInstanceState);
        //???????????????
        try {
            mLocationClient = new AMapLocationClient(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
//????????????????????????
        mLocationClient.setLocationListener(mLocationListener);
//?????????AMapLocationClientOption??????
        mLocationOption = new AMapLocationClientOption();
        //?????????????????????AMapLocationMode.Hight_Accuracy?????????????????????
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        mLocationOption.setOnceLocation(true);
        //??????????????????????????????????????????
        mLocationClient.setLocationOption(mLocationOption);
//????????????
        mLocationClient.startLocation();
        init();
        show();

//        List<String> permissionList = new ArrayList<>();
//        if (ContextCompat.checkSelfPermission(HuodongMap.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            permissionList.add(Manifest.permission.ACCESS_COARSE_LOCATION);
//        }
//        if (ContextCompat.checkSelfPermission(HuodongMap.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
//            permissionList.add(Manifest.permission.READ_PHONE_STATE);
//        }
//        if (ContextCompat.checkSelfPermission(HuodongMap.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
//        }
//        if (!permissionList.isEmpty()) {
//            String[] permissions = permissionList.toArray(new String[permissionList.size()]);
//            ActivityCompat.requestPermissions(HuodongMap.this, permissions, 1);
//        } else {
//
//            show();
//        }
        //??????????????????
    }

    private void init() {
        if (aMap == null) {
            aMap = mMapView.getMap();
//            centerpoint = new LatLonPoint(myLatitude, myLontitude);
//            aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(convertToLatLng(centerpoint), 13));
//            myLocationStyle = new MyLocationStyle();
//            myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE);
//            aMap.setMyLocationStyle(myLocationStyle);//?????????????????????Style
//            //myLocationStyle.interval(2000);
//            //aMap.getUiSettings().setMyLocationButtonEnabled(true);
//            //myLocationStyle.showMyLocation(true);
//            aMap.setMyLocationEnabled(true);
            //aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(convertToLatLng(centerpoint), 13));
        }


    }

    private void show() {
        if (isConnect(this) == false) {
            new AlertDialog.Builder(this)
                    .setTitle("????????????")
                    .setMessage("??????????????????????????????GPRS??????WIFI??????")
                    .setPositiveButton("??????", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            android.os.Process.killProcess(android.os.Process.myPid());
                            System.exit(0);
                        }
                    }).show();
        }

        new Thread() {
            @Override
            public void run() {
                try {
                    String url = "http://175.23.169.100:9028/case-aggregated-activity/get?caseId=" + current_banliId;
                    URL httpUrl = new URL(url);
                    HttpURLConnection conn = (HttpURLConnection) httpUrl.openConnection();

                    conn.setRequestMethod("GET");
                    conn.setReadTimeout(5000);
                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuffer sb = new StringBuffer();
                    String str1;
                    while ((str1 = reader.readLine()) != null) {
                        sb.append(str1);
                    }
                    JSONObject object1 = new JSONObject(sb.toString());
                    int code = object1.getInt("code");
                    int acti_num = 0;
                    if (code == 0) {
                        JSONArray json1 = object1.getJSONArray("activities");
                        String[] acti_long = new String[0];
                        String[] acti_lati = new String[0];
                        String[] acti_name = new String[0];
                        String[] start_time = new String[0];
                        String[] end_time = new String[0];


                        acti_num = json1.length();
                        if (acti_num != 0) {
                            acti_long = new String[acti_num];
                            acti_lati = new String[acti_num];
                            acti_name = new String[acti_num];
                            start_time = new String[acti_num];
                            end_time = new String[acti_num];

                            for (int i = 0; i < acti_num; i++) {
                                acti_long[i] = json1.getJSONObject(i).getString("longitude");
                                acti_lati[i] = json1.getJSONObject(i).getString("latitude");
                                acti_name[i] = json1.getJSONObject(i).getString("actipla_detail");
                                String sd = json1.getJSONObject(i).getString("acti_start_date");
                                String st = json1.getJSONObject(i).getString("acti_start_time");
                                String ed = json1.getJSONObject(i).getString("acti_end_date");
                                String et = json1.getJSONObject(i).getString("acti_end_time");
                                start_time[i] = sd + " " + st;
                                end_time[i] = ed + " " + et;
                                str.add(acti_name[i] + "," + acti_long[i] + "," + acti_lati[i]+ "," +start_time[i]+ "," +end_time[i]);
                                poiOverlay = new ViewPoiOverlay(aMap, poiItems);
                                //aMap.clear();// ?????????????????????
                                poiOverlay = new ViewPoiOverlay(aMap, poiItems);
                                poiOverlay.removeFromMap();
                                poiOverlay.addToMap();
                                if (i == 0){
                                    centerpoint = new LatLonPoint(Double.valueOf(acti_lati[i]), Double.valueOf(acti_long[i]));
                                    aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(convertToLatLng(centerpoint), 13));
                                }

                                //aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(convertToLatLng(centerpoint), 13));

                            }
                        }
                        else {
                            centerpoint = new LatLonPoint(43.853945, 125.308620);
                            aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(convertToLatLng(centerpoint), 13));
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(HuodongMap.this,"?????????????????????????????????????????????",Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //???activity??????onDestroy?????????mMapView.onDestroy()???????????????
        mMapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //???activity??????onResume?????????mMapView.onResume ()???????????????????????????
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //???activity??????onPause?????????mMapView.onPause ()????????????????????????
        mMapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //???activity??????onSaveInstanceState?????????mMapView.onSaveInstanceState (outState)??????????????????????????????
        mMapView.onSaveInstanceState(outState);
    }

    /**
     * ???LatLonPoint???????????????LatLon??????
     */
    public static LatLng convertToLatLng(LatLonPoint latLonPoint) {
        if (latLonPoint == null) {
            return null;
        }
        return new LatLng(latLonPoint.getLatitude(), latLonPoint.getLongitude());
    }

//    @Override
//    public void onMyLocationChange(Location location) {
//        myLontitude = location.getLongitude();
//        myLatitude = location.getLatitude();
//        LatLonPoint centerpoint1 = new LatLonPoint(location.getLatitude(),location.getLongitude());
//        aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(convertToLatLng(centerpoint1), 13));
//    }


    public class ViewPoiOverlay extends PoiOverlay {

        private ArrayList<Marker> mPoiMarks = new ArrayList<Marker>();


//        public ViewPoiOverlay(AMap aMap, List<PoiItem> list) {
//            super(aMap, list);
//        }

        public ViewPoiOverlay(AMap aMap, List<PoiItem> list) {
            super(aMap, list);
        }

        @Override
        public void addToMap() {
            try {
                for (int i = 0; i < str.size(); i++) {
                    Marker marker = aMap.addMarker(getMarkerOptions(i));
                    marker.setObject(i);
                    mPoiMarks.add(marker);
                }
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }

        private MarkerOptions getMarkerOptions(int index) {
            return new MarkerOptions()
                    .position(
                            new LatLng(Double.valueOf(str.get(index).split(",")[2]), Double.valueOf(str.get(index).split(",")[1])))
                    .title(str.get(index).split(",")[0]+"\n"+str.get(index).split(",")[3]+"~"+str.get(index).split(",")[4])
                    .icon(getBitmapDescriptor(index));
        }

        @Override
        protected BitmapDescriptor getBitmapDescriptor(int index) {
            View view = null;
            view = View.inflate(HuodongMap.this, R.layout.custom_view, null);
            TextView textView = ((TextView) view.findViewById(R.id.title));
            textView.setText(str.get(index).split(",")[0]);

            return BitmapDescriptorFactory.fromView(view);
        }
    }
}

