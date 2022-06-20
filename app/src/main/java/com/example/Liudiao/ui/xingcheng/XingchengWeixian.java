package com.example.Liudiao.ui.xingcheng;

import static com.example.Liudiao.Welcome.isConnect;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.amap.api.fence.GeoFence;
import com.amap.api.fence.GeoFenceClient;
import com.amap.api.fence.GeoFenceListener;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.DPoint;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CircleOptions;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.example.Liudiao.R;
import com.example.Liudiao.ui.xingcheng.Adapter.XingchengAdapter;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class XingchengWeixian extends AppCompatActivity {


    ArrayAdapter<String> adapter;

    private List<LatLng> circlelist = new ArrayList<>();
    private GeoFenceClient geo;

    private List<DPoint>  Dlist = new ArrayList<>();



    private ImageView back;

    private SharedPreferences preferences;
    SharedPreferences.Editor editor;
    private int current_banliId;


    private double lat;
    private double lot;

    private LatLonPoint centerpoint;

    List<PoiItem> poiItems = new ArrayList<>();
//    private ViewPoiOverlay poiOverlay;


    private List<String> str = new ArrayList<>();

    private String[]point = new String[20];
    private ListView listView1;
    private ImageView arrow;

private TextView openlist;
private AlertDialog.Builder builder;


    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;
    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明定位回调监听器
    public AMapLocationListener mLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation amapLocation) {
            if (amapLocation != null) {
                if (amapLocation.getErrorCode() == 0) {
//可在其中解析amapLocation获取相应内容。
                    amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                    amapLocation.getLatitude();//获取纬度
                    amapLocation.getLongitude();//获取经度




                    amapLocation.getAccuracy();//获取精度信息

                    amapLocation.getAddress();//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
                    amapLocation.getCountry();//国家信息
                    amapLocation.getProvince();//省信息
                    amapLocation.getCity();//城市信息
                    amapLocation.getDistrict();//城区信息
                    amapLocation.getStreet();//街道信息
                    amapLocation.getStreetNum();//街道门牌号信息
                    amapLocation.getCityCode();//城市编码
                    amapLocation.getAdCode();//地区编码
                    amapLocation.getAoiName();//获取当前定位点的AOI信息
                    amapLocation.getBuildingId();//获取当前室内定位的建筑物Id
                    amapLocation.getFloor();//获取当前室内定位的楼层
                    amapLocation.getGpsAccuracyStatus();//获取GPS的当前状态
//获取定位时间
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date date = new Date(amapLocation.getTime());
                    df.format(date);

                } else {
                    //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                    Log.e("AmapError", "location Error, ErrCode:"
                            + amapLocation.getErrorCode() + ", errInfo:"
                            + amapLocation.getErrorInfo());
                }
            }

        }
    };
    private MapView mMapView;
    private AMap aMap;


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            parse(msg.obj.toString());
        }
    };
//    GeoFenceListener fenceListenter = new GeoFenceListener() {
//
//        @Override
//        public void onGeoFenceCreateFinished(List<GeoFence> list, int i, String s) {
//
//        }
//
//
//    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xingcheng_weixian);
        listView1 = (ListView) findViewById(R.id.weixian_list);
        openlist = (TextView) findViewById(R.id.openlist);
        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        //获取地图控件引用
        mMapView = (MapView) findViewById(R.id.map);
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图
        mMapView.onCreate(savedInstanceState);


        if (aMap == null) {
            aMap = mMapView.getMap();
        }



        //定位
        MyLocationStyle myLocationStyle;
        myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        myLocationStyle.interval(1000000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
//aMap.getUiSettings().setMyLocationButtonEnabled(true);设置默认定位按钮是否显示，非必需设置。
        aMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。

//初始化定位
        try {
            mLocationClient = new AMapLocationClient(getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
//设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);


//初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();

        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);

        //设置定位间隔,单位毫秒,默认为2000ms，最低1000ms。
        mLocationOption.setInterval(1000000);

        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //单位是毫秒，默认30000毫秒，建议超时时间不要低于8000毫秒。
        mLocationOption.setHttpTimeOut(20000);

        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
//启动定位
        mLocationClient.startLocation();
        lot = mLocationClient.getLastKnownLocation().getLongitude();
        lat = mLocationClient.getLastKnownLocation().getLatitude();




        geo = new GeoFenceClient(XingchengWeixian.this);
//        geo.setActivateAction(GeoFenceClient.GEOFENCE_IN|GeoFenceClient.GEOFENCE_OUT|GeoFenceClient.GEOFENCE_STAYED);
//        geo.setGeoFenceListener (fenceListenter);
        //拿数据标点
        show(lat,lot);




        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                centerpoint = new LatLonPoint(Double.valueOf(str.get(position).split(",")[2]), Double.valueOf(str.get(position).split(",")[1]));
                aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(convertToLatLng(centerpoint), 16));


            }
        });

        openlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder = new AlertDialog.Builder(XingchengWeixian.this).setTitle("测试")
                        .setMessage("测试")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int a) {
                                dialogInterface.dismiss();
                            }
                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                dialog.dismiss();
                            }
                        });
                builder.create().show();
            }
        });



    /*    routeSearch = new RouteSearch(this);

        routeSearch.setRouteSearchListener(this);*/

    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mMapView.onDestroy();
        mLocationClient.onDestroy();//销毁定位客户端，同时销毁本地定位服务。
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mMapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mLocationClient.stopLocation();//停止定位后，本地定位服务并不会被销毁
    }
    public static LatLng convertToLatLng(LatLonPoint latLonPoint) {
        if (latLonPoint == null) {
            return null;
        }
        return new LatLng(latLonPoint.getLatitude(), latLonPoint.getLongitude());
    }

    private void show(final double lat, final double lot) {
        if (isConnect(this) == false) {
            new AlertDialog.Builder(this)
                    .setTitle("网络错误")
                    .setMessage("网络连接失败，请开启GPRS或者WIFI连接")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            android.os.Process.killProcess(android.os.Process.myPid());
                            System.exit(0);
                        }
                    }).show();
        }
        String url = "http://175.23.169.100:9029/getAroundCaseAddress?longitude=";
        WeixianThread rdt = new WeixianThread(url,lot,lat,handler);
        rdt.start();
    }
//    public class ViewPoiOverlay extends PoiOverlay {
//
//        private ArrayList<Marker> mPoiMarks = new ArrayList<Marker>();
//
//
////        public ViewPoiOverlay(AMap aMap, List<PoiItem> list) {
////            super(aMap, list);
////        }
//
//        public ViewPoiOverlay(AMap aMap, List<PoiItem> list) {
//            super(aMap, list);
//        }
//
//        @Override
//        public void addToMap() {
//            try {
//                for (int i = 0; i < str.size(); i++) {
//                    Marker marker = aMap.addMarker(getMarkerOptions(i));
//                    marker.setObject(i);
//                    mPoiMarks.add(marker);
//                }
//            } catch (Throwable e) {
//                e.printStackTrace();
//            }
//        }
//
//        private MarkerOptions getMarkerOptions(int index) {
//            return new MarkerOptions()
//                    .position(
//                            new LatLng(Double.valueOf(str.get(index).split(",")[2]), Double.valueOf(str.get(index).split(",")[1])))
//                    .title(str.get(index).split(",")[0])
//                    .icon(getBitmapDescriptor(index));
//        }
//
//        @Override
//        protected BitmapDescriptor getBitmapDescriptor(int index) {
//            View view = null;
//            view = View.inflate(XingchengWeixian.this, R.layout.custom_view, null);
//            TextView textView = ((TextView) view.findViewById(R.id.title));
//            textView.setText(str.get(index).split(",")[0]);
//
//            return BitmapDescriptorFactory.fromView(view);
//        }
//    }






    public void parse(String var) {
        JSONObject object1 = null;
        try {
            object1 = new JSONObject(var);

            int code = object1.getInt("code");
            int acti_num = 0;
            if (code == 0) {
                JSONArray json1 = object1.getJSONArray("aroundCaseAddress");
                String[] acti_long = new String[0];
                String[] acti_lati = new String[0];
                String[] acti_name = new String[0];


                acti_num = json1.length();
                String[]point = new String[acti_num];
                if (acti_num != 0) {
                    acti_long = new String[acti_num];
                    acti_lati = new String[acti_num];
                    acti_name = new String[acti_num];


                    for (int i = 0; i < acti_num; i++) {
                        acti_long[i] = json1.getJSONObject(i).getString("longitude");
                        acti_lati[i] = json1.getJSONObject(i).getString("latitude");
                        acti_name[i] = json1.getJSONObject(i).getString("address_detail");
                        DPoint Dp = new DPoint();
                        Dp.setLatitude(Double.parseDouble(acti_lati[i]));
                        Dp.setLongitude(Double.parseDouble(acti_long[i]));
                        LatLng latLng = new LatLng(Double.parseDouble(acti_lati[i]),Double.parseDouble(acti_long[i]));
                        if (!(acti_name[i].length() == 0)){
                            point[i] = (String)acti_name[i];
                            str.add(acti_name[i] + "," + acti_long[i] + "," + acti_lati[i]);
                            geo.addGeoFence(Dp,50,acti_name[i]);
                            circlelist.add(latLng);
                        }else{
                            point[i] = "附近某地点";
                            str.add("附近某地点" + "," + acti_long[i] + "," + acti_lati[i]);
                            geo.addGeoFence(Dp,50,"某地点");
                            circlelist.add(latLng);
                        }


                    }
//                    poiOverlay = new ViewPoiOverlay(aMap, poiItems);
//                    //aMap.clear();// 清理之前的图标
//                    poiOverlay = new ViewPoiOverlay(aMap, poiItems);
//                    poiOverlay.removeFromMap();
//                    poiOverlay.addToMap();

                        for (int i = 0; i < circlelist.size(); i++) {
                            aMap.addCircle(new CircleOptions().
                                    center(circlelist.get(i)).
                                    radius(50).
                                    fillColor(Color.argb(50,255,0,0)).
                                    strokeColor(Color.argb(255,255,0,0)).
                                    strokeWidth(1));
                        }







                    centerpoint = new LatLonPoint(lat, lot);
                    aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(convertToLatLng(centerpoint), 16));



                    adapter = new ArrayAdapter<String>(XingchengWeixian.this, android.R.layout.simple_list_item_1, point);

                    listView1.setAdapter(adapter);


                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
