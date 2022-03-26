package com.example.Liudiao.login;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.Liudiao.MainActivity;
import com.example.Liudiao.R;
import com.example.Liudiao.ZhanghaoLogin;
import com.yzq.zxinglibrary.android.CaptureActivity;
import com.yzq.zxinglibrary.common.Constant;
//import com.example.Liudiao.zxinglibrary.android.CaptureActivity;


public class QRLogin extends AppCompatActivity {

    private Button login;
    private TextView login1;
    private TextView login2;

    private static final int REQUEST_CODE_SCAN = 0x0000;
    private static final String DECODED_CONTENT_KEY = "codedContent";
    private static final String DECODED_BITMAP_KEY = "codedBitmap";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qr_login);

        login = (Button) findViewById(R.id.qr_login);
        login1 = (TextView) findViewById(R.id.login1_btn);
        login2 = (TextView) findViewById(R.id.login2_btn);

        login1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QRLogin.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        login2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QRLogin.this, ZhanghaoLogin.class);
                startActivity(intent);
                finish();

            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(QRLogin.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
                    Intent intent = new Intent(QRLogin.this, Capture.class);
                    startActivityForResult(intent,1111);
                }else {
                    ActivityCompat.requestPermissions(QRLogin.this,new String[]{Manifest.permission.CAMERA},100);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1111) {
            if (data != null) {
                String content = data.getStringExtra(Constant.CODED_CONTENT);
                Bitmap bitmap = data.getParcelableExtra("conteng_key");
                // syncDecodeQRCode(bitmap);
                Toast.makeText(QRLogin.this,"111"+content,Toast.LENGTH_SHORT).show();
            }
        }
    }
//    public void recogQRcode(Bitmap bitmap){
//        int width = bitmap.getWidth();
//        int height = bitmap.getHeight();
//        int[] data = new int[width * height];
//        bitmap.getPixels(data, 0, width, 0, 0, width, height);    //得到像素
//        RGBLuminanceSource source = new RGBLuminanceSource(bitmap);   //RGBLuminanceSource对象
//        BinaryBitmap bitmap1 = new BinaryBitmap(new HybridBinarizer(source));
//        QRCodeReader reader = new QRCodeReader();
//        Result re = null;
//        try {
//            //得到结果
//            re = reader.decode(bitmap1);
//        } catch (NotFoundException e) {
//            e.printStackTrace();
//        } catch (ChecksumException e) {
//            e.printStackTrace();
//        } catch (FormatException e) {
//            e.printStackTrace();
//        }
//        //Toast出内容
//        Toast.makeText(QRLogin.this,re.getText(),Toast.LENGTH_SHORT).show();
//        //利用正则表达式判断内容是否是URL，是的话则打开网页
//        String regex = "(((https|http)?://)?([a-z0-9]+[.])|(www.))"
//                + "\\w+[.|\\/]([a-z0-9]{0,})?[[.]([a-z0-9]{0,})]+((/[\\S&&[^,;\u4E00-\u9FA5]]+)+)?([.][a-z0-9]{0,}+|/?)";//设置正则表达式
//
//        Pattern pat = Pattern.compile(regex.trim());//比对
//        Matcher mat = pat.matcher(re.getText().trim());
//        if (mat.matches()){
//            Uri uri = Uri.parse(re.getText());
//            Intent intent = new Intent(Intent.ACTION_VIEW, uri);//打开浏览器
//            startActivity(intent);
//        }
//
//    }

//    public class RGBLuminanceSource extends LuminanceSource {
//
//        private byte bitmapPixels[];
//
//        protected RGBLuminanceSource(Bitmap bitmap) {
//            super(bitmap.getWidth(), bitmap.getHeight());
//
//            // 首先，要取得该图片的像素数组内容
//            int[] data = new int[bitmap.getWidth() * bitmap.getHeight()];
//            this.bitmapPixels = new byte[bitmap.getWidth() * bitmap.getHeight()];
//            bitmap.getPixels(data, 0, getWidth(), 0, 0, getWidth(), getHeight());
//
//            // 将int数组转换为byte数组，也就是取像素值中蓝色值部分作为辨析内容
//            for (int i = 0; i < data.length; i++) {
//                this.bitmapPixels[i] = (byte) data[i];
//            }
//        }
//
//        @Override
//        public byte[] getMatrix() {
//            // 返回我们生成好的像素数据
//            return bitmapPixels;
//        }
//
//        @Override
//        public byte[] getRow(int y, byte[] row) {
//            // 这里要得到指定行的像素数据
//            System.arraycopy(bitmapPixels, y * getWidth(), row, 0, getWidth());
//            return row;
//        }
//    }
}
