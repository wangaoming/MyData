package com.example.mydata;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener {private ImageView imageView;
private Button button1,button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_main2 );
        imageView = findViewById ( R.id.pic );
        button1 = findViewById ( R.id.bt1 );
        button2 = findViewById ( R.id.bt2 );
        button2.setOnClickListener ( this);
        button1.setOnClickListener ( this );
    }

    @Override
    public void onClick(View v) {
        switch (v.getId ()){
            case R.id.bt1:
                saveToSd("link1.jpg");
            break;
            //读取sd卡中的图片
            case  R.id.bt2:
                readFromSd();
                break;
        }

    }
//读取到sd卡
    private void readFromSd() {
//          String path = Environment.getExternalStorageDirectory ( )+"Pictures/weimei.jpg";

    }

    //保存在sd卡中
    private void saveToSd(String fileName) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            //共有文件，需要申请权限
            if(ContextCompat.checkSelfPermission ( this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE )!= PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions ( this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},2 );
                return;
            }
        }
        //写到sd卡的步骤
        //1.获取sd的Download
        String path = Environment.getExternalStoragePublicDirectory ( "").getPath()
        + File.separator
        +Environment.DIRECTORY_PICTURES;
        File file = new File(path,fileName);

        try {
            if(file.createNewFile ()){
                //2.将获取ImageView的Bitmap的图片对象
                BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable ();
                Bitmap bitmap = drawable.getBitmap ();
                //将Bitmap的对象写入到sd卡中
                FileOutputStream outputStream = new FileOutputStream ( file );
                //3.带缓冲
                bitmap.compress ( Bitmap.CompressFormat.JPEG,100,outputStream );
                //4.关闭
                outputStream.flush ();
                outputStream.close ();
            }
        } catch (IOException e) {
            e.printStackTrace ( );
        }
    }

//回调的方法 申请的回调方式,分支结构
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult ( requestCode, permissions, grantResults );

        if(grantResults.length==0|| grantResults[0]!= PackageManager.PERMISSION_GRANTED){
            Toast.makeText ( this,"权限申请被拒绝",Toast.LENGTH_LONG ).show ();
            return;
        }
        switch (requestCode){
            case 1:
                saveToSd ( "link1.jpg" );
                break;
            case 2:
                break;
        }
    }
}
