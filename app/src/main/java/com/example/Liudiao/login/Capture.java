package com.example.Liudiao.login;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.Liudiao.Main;
import com.example.Liudiao.R;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.FormatException;
import com.google.zxing.LuminanceSource;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;
import com.yzq.zxinglibrary.android.CaptureActivity;
import com.yzq.zxinglibrary.android.CaptureActivityHandler;
import com.yzq.zxinglibrary.android.InactivityTimer;
import com.yzq.zxinglibrary.camera.CameraManager;

import java.io.File;
import java.io.IOException;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.yzq.zxinglibrary.decode.ImageUtil.getImageAbsolutePath;

public class Capture extends CaptureActivity {

    private RelativeLayout xiangce;

    private MyViewfinderView viewfinderView;// 绘制扫描区域
    private CaptureActivityHandler handler;// 消息中心
    private boolean hasSurface;// 控制调用相机属性
    private Vector<BarcodeFormat> decodeFormats;// 存储二维格式的数组
    private String characterSet;// 字符集
    private InactivityTimer inactivityTimer;// 相机扫描刷新timer
    private MediaPlayer mediaPlayer;// 播放器
    private boolean playBeep;// 声音布尔
    private static final float BEEP_VOLUME = 0.10f;// 声音大小
    private boolean vibrate;// 振动布尔

    private String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera);

        xiangce = (RelativeLayout)findViewById(R.id.xiangce);

        viewfinderView = (MyViewfinderView) findViewById(R.id.viewfinder_view);

        xiangce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (String permission:permissions){
                    if (ContextCompat.checkSelfPermission(getApplicationContext(), permission) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(Capture.this, permissions, 1);
                    }
                }
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, 3);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 3){
            if (data != null){
                String path = null;
                //取得返回的Uri,基本上选择照片的时候返回的是以Uri形式，但是在拍照中有得机子呢Uri是空的，所以要特别注意
                Uri mImageCaptureUri = data.getData();
                if (mImageCaptureUri != null) {
                    //返回的Uri不为空时，那么图片信息数据都会在Uri中获得。如果为空，那么我们就进行下面的方式获取
                    Bitmap image;
                    String imagepath = null;

                    if (mImageCaptureUri.toString().contains("content://")) { //如果包含有content开头，需要转化为其实际路径，不能用content开头

                        imagepath = getImageAbsolutePath(this, mImageCaptureUri);

                    } else if (mImageCaptureUri.toString().contains("file://")) {
                        imagepath = mImageCaptureUri.toString().replace("file://", "");       //如果用file开头，去掉file头

                    }

                    image = loadResBitmap(imagepath, 5);

                    //String name = recogQRcode(image);
                    Intent intent = new Intent(Capture.this, Main.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }
            }
        }
    }

    public static Bitmap loadResBitmap(String path, int scalSize) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = false;
        options.inSampleSize = scalSize;
        Bitmap bmp = BitmapFactory.decodeFile(path, options);
        return bmp;
    }

    //识别二维码
    public String recogQRcode(Bitmap QRbmp){
        //Bitmap QRbmp = ((BitmapDrawable) (imageView).getDrawable()).getBitmap();   //将图片bitmap化
        int width = QRbmp.getWidth();
        int height = QRbmp.getHeight();
        int[] data = new int[width * height];
        QRbmp.getPixels(data, 0, width, 0, 0, width, height);    //得到像素
        RGBLuminanceSource source = new RGBLuminanceSource(QRbmp);   //RGBLuminanceSource对象
        BinaryBitmap bitmap1 = new BinaryBitmap(new HybridBinarizer(source));
        QRCodeReader reader = new QRCodeReader();
        Result re = null;
        try {
            //得到结果
            re = reader.decode(bitmap1);
        } catch (NotFoundException e) {
            e.printStackTrace();
        } catch (ChecksumException e) {
            e.printStackTrace();
        } catch (FormatException e) {
            e.printStackTrace();
        }
        return re.getText();
        //Toast出内容
       // Toast.makeText(MainActivity.this,re.getText(),Toast.LENGTH_SHORT).show();

        //利用正则表达式判断内容是否是URL，是的话则打开网页
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

    }

    public static class RGBLuminanceSource extends LuminanceSource {

        private byte bitmapPixels[];

        protected RGBLuminanceSource(Bitmap bitmap) {
            super(bitmap.getWidth(), bitmap.getHeight());

            // 首先，要取得该图片的像素数组内容
            int[] data = new int[bitmap.getWidth() * bitmap.getHeight()];
            this.bitmapPixels = new byte[bitmap.getWidth() * bitmap.getHeight()];
            bitmap.getPixels(data, 0, getWidth(), 0, 0, getWidth(), getHeight());

            // 将int数组转换为byte数组，也就是取像素值中蓝色值部分作为辨析内容
            for (int i = 0; i < data.length; i++) {
                this.bitmapPixels[i] = (byte) data[i];
            }
        }

        @Override
        public byte[] getMatrix() {
            // 返回我们生成好的像素数据
            return bitmapPixels;
        }

        @Override
        public byte[] getRow(int y, byte[] row) {
            // 这里要得到指定行的像素数据
            System.arraycopy(bitmapPixels, y * getWidth(), row, 0, getWidth());
            return row;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view);
        SurfaceHolder surfaceHolder = surfaceView.getHolder();
        if (hasSurface) {
            initCamera(surfaceHolder);
        } else {
            surfaceHolder.addCallback(this);
            surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }
        decodeFormats = null;
        characterSet = null;
        // 声音
        playBeep = true;
        // 初始化音频管理器
        AudioManager audioService = (AudioManager) getSystemService(AUDIO_SERVICE);
        if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
            playBeep = false;
        }
        initBeepSound();
        // 振动
        vibrate = true;
    }

    private void initBeepSound() {
        if (playBeep && mediaPlayer == null) {
            // The volume on STREAM_SYSTEM is not adjustable, and users found it
            // too loud,
            // so we now play on the music stream.
            setVolumeControlStream(AudioManager.STREAM_MUSIC);
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setOnCompletionListener(beepListener);

            AssetFileDescriptor file = getResources().openRawResourceFd(
                    R.raw.beep);
            try {
                mediaPlayer.setDataSource(file.getFileDescriptor(),
                        file.getStartOffset(), file.getLength());
                file.close();
                mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
                mediaPlayer.prepare();
            } catch (IOException e) {
                mediaPlayer = null;
            }
        }
    }

    /**
     * When the beep has finished playing, rewind to queue up another one.
     */
    private final MediaPlayer.OnCompletionListener beepListener = new MediaPlayer.OnCompletionListener() {
        public void onCompletion(MediaPlayer mediaPlayer) {
            mediaPlayer.seekTo(0);
        }
    };

    /**
     * 初始化相机
     */
    private void initCamera(SurfaceHolder surfaceHolder) {
        try {
            CameraManager.get().openDriver(surfaceHolder);
        } catch (IOException ioe) {
            return;
        } catch (RuntimeException e) {
            return;
        }

    }

    private static final int SHOW_TOAST_MSG = 0;
    Handler msgHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case SHOW_TOAST_MSG:

                    Toast.makeText(getApplicationContext(), "未发现二维码图片", Toast.LENGTH_SHORT).show();
                    break;

                default:
                    break;
            }
        };
    };


}
