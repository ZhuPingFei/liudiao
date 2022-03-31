package com.example.Liudiao.ui.notifications;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.Liudiao.R;
import com.example.Liudiao.login.QRLogin;
import com.example.Liudiao.zxing.encode.CodeCreator;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Erweima extends AppCompatActivity {

    private String savedPath = Environment.getExternalStorageDirectory().getAbsolutePath() +
            File.separator  + String.valueOf(System.currentTimeMillis()) + ".png";

    private ImageView image;
    private TextView shengcheng;
    private ImageView r_back;
    private TextView save;
    private SharedPreferences preferences;
    SharedPreferences.Editor editor;
    private String user_phone;
    private boolean hasQR ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_erweima);

        preferences = getSharedPreferences("user", Activity.MODE_PRIVATE);
        editor = preferences.edit();
        user_phone = preferences.getString("user_phone","");
        hasQR = preferences.getBoolean(user_phone+"hasQR",false);


        image = (ImageView) findViewById(R.id.image);
        shengcheng = (TextView) findViewById(R.id.shengcheng);
        save = (TextView) findViewById(R.id.save);

        r_back = (ImageView) findViewById(R.id.back);
        r_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        if (hasQR == true){
            Bitmap qrcodeBitmap = createQRCode(user_phone, 800,800);
            image.setImageBitmap(qrcodeBitmap);
            shengcheng.setVisibility(View.GONE);
            save.setVisibility(View.VISIBLE);
        }

        shengcheng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap qrcodeBitmap = createQRCode(user_phone, 800,800);
                image.setImageBitmap(qrcodeBitmap);
                //Bitmap bitmap = createQRCode(String.valueOf(current_transId),image.getHeight(),image.getWidth());

                shengcheng.setVisibility(View.GONE);
                save.setVisibility(View.VISIBLE);
                editor.putBoolean(user_phone+"hasQR",true);
                editor.commit();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (ActivityCompat.checkSelfPermission(Erweima.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED
                            || ActivityCompat.checkSelfPermission(Erweima.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {
// 申请一个（或多个）权限，并提供用于回调返回的获取码（用户定义）
                        ActivityCompat.requestPermissions(Erweima.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                    } else {
//                       mLDialog.setDialogText("正在保存图片...");
//                        mLDialog.show();
                        saveMyBitmap("AuthCode", createViewBitmap(image));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1111) {
            if (data != null) {
                //String content = data.getStringExtra(Constant.CODED_CONTENT);
                Bitmap bitmap = data.getParcelableExtra("conteng_key");
                // syncDecodeQRCode(bitmap);
                //Toast.makeText(Erweima.this,"111"+content,Toast.LENGTH_SHORT).show();
            }
        }
    }

    //将要存为图片的view传进来 生成bitmap对象

    public Bitmap createViewBitmap(View v) {
        Bitmap bitmap = Bitmap.createBitmap(v.getWidth(), v.getHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        v.draw(canvas);
        // Toast.makeText(MainActivity.this,"已经生成Bitmap对象",Toast.LENGTH_SHORT).show();
        return bitmap;
    }

    public void saveMyBitmap(final String bitName, final Bitmap bitmap) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String filePath = Environment.getExternalStorageDirectory().getPath();
                File file = new File(savedPath);

                try {
                    file.createNewFile();

                    FileOutputStream fOut = null;

                    fOut = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);

                    Message msg = Message.obtain();
                    msg.obj = file.getPath();

                    handler.sendMessage(msg);
                    Log.d("tag1111", "run: 保存成功");
                    //Toast.makeText(MainActivity.this, "保存成功", Toast.LENGTH_LONG).show();
                    fOut.flush();
                    fOut.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String picFile = (String) msg.obj;
            String[] split = picFile.split("/");
            String fileName = split[split.length - 1];
            try {
                MediaStore.Images.Media.insertImage(getApplicationContext()
                        .getContentResolver(), picFile, fileName, null);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
// 最后通知图库更新
            sendBroadcast(new Intent(
                    Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://"
                    + picFile)));
            Toast.makeText(Erweima.this, "图片保存图库成功", Toast.LENGTH_LONG).show();
//            if (mLDialog != null && mLDialog.isShowing()) {
//                mLDialog.dismiss();
//            }
            save.setClickable(true);
        }
    };

    public static Bitmap createQRCode(String content, int widthPix, int heightPix) {

        try {
            if (content == null || "".equals(content)) {
                return null;
            }
            // 配置参数
            Map<EncodeHintType, Object> hints = new HashMap<>();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            // 容错级别
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
            // 图像数据转换，使用了矩阵转换
            BitMatrix bitMatrix = new QRCodeWriter().encode(content, BarcodeFormat.QR_CODE, widthPix,
                    heightPix, hints);
            int[] pixels = new int[widthPix * heightPix];
            // 下面这里按照二维码的算法，逐个生成二维码的图片，
            // 两个for循环是图片横列扫描的结果
            for (int y = 0; y < heightPix; y++) {
                for (int x = 0; x < widthPix; x++) {
                    if (bitMatrix.get(x, y)) {
                        pixels[y * widthPix + x] = 0xff000000;
                    } else {
                        pixels[y * widthPix + x] = 0xffffffff;
                    }
                }
            }
            // 生成二维码图片的格式，使用ARGB_8888
            Bitmap bitmap = Bitmap.createBitmap(widthPix, heightPix, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, widthPix, 0, 0, widthPix, heightPix);
            //必须使用compress方法将bitmap保存到文件中再进行读取。直接返回的bitmap是没有任何压缩的，内存消耗巨大！
            return bitmap;
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return null;
    }
}
