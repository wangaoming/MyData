package com.example.mydata;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import static android.content.Context.MODE_PRIVATE;
import static java.lang.System.in;

public class MainActivity extends AppCompatActivity  {
    private EditText etUsername;
    private EditText etPassWord;
    private Button btnLogin,button1,button2;
    private CheckBox sharedPreferences;
    private ImageView imageView1;
    private String fileName = "login.txt";
    private String passWord = "login.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_main );
        etUsername = findViewById ( R.id.username );
        etPassWord = findViewById ( R.id.password );
        btnLogin = findViewById ( R.id.login );
        imageView1 = findViewById ( R.id.pic );
        button1 = findViewById ( R.id.bt1 );
        button2 = findViewById ( R.id.bt2 );
//        button1.setOnClickListener ( this );
//       button2.setOnClickListener (this ) ;
        sharedPreferences = findViewById ( R.id.rember );


//数据存储方式一
//        String username = readPrfs ( );
        //数据的读取
        String username = null;
        String password =null;
        username = readData (  fileName);
        password = readData ( passWord );
        if (username != null&&password!=null) {
            etUsername.setText ( username );
            etPassWord.setText ( password );
        }
        btnLogin.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {
                // 3处理按钮的点击事件
                // 3.1 获取用户名和密码值
                String username = etUsername.getText ( ).toString ( ).trim ( );
                String password = etPassWord.getText ( ).toString ( );
                //判断“记住我”是否勾选，若勾选，则存储用户名，否则清除
                if (sharedPreferences.isChecked ( )) {
                    //保存
                    savePref ( username );
                    saveData ( fileName, username );
                    saveDataprivate ( fileName,username,password);

                } else {
                    clearPref ( );
                    deleteData( fileName);
                    deleteDataFile(fileName);


                }
                // 3.2 比较用户名和密码是否正确，然后给出提示
                if (username.equals ( "Naomi" ) && password.equals ( "1234" )) {
                    Intent intent = new Intent ( MainActivity.this, Main2Activity.class );
                    startActivity ( intent );
                    Toast.makeText ( MainActivity.this, "登录成功", Toast.LENGTH_LONG ).show ( );
                } else {
                    Toast.makeText ( MainActivity.this, "用户名或密码不对", Toast.LENGTH_LONG ).show ( );
                }

            }

            //清除
            private void clearPref() {
                SharedPreferences.Editor editor = getSharedPreferences ( "userInfo", MODE_PRIVATE ).edit ( );
                editor.clear ( ).apply ( );
            }

            //保存

            private void savePref(String username) {
                SharedPreferences.Editor editor = getSharedPreferences ( "userInfo", MODE_PRIVATE ).edit ( );
                editor.clear ( ).putString ( "name", username );

            }
        } );
    }

    private String readData(String passWord) {
        String data  = null;
        FileInputStream in = null;
        try {
            in = openFileInput ( passWord);
            BufferedReader reader = new BufferedReader (new InputStreamReader ( in) );
            data = reader.readLine ();
            reader.close ();
            return data;
        } catch (IOException e) {
            e.printStackTrace ( );
        }
return  data;
    }

    //数据的保存
    private void saveData(String fileName, String username) {


        try {
            FileOutputStream out = openFileOutput ( fileName, Context.MODE_PRIVATE );
            BufferedWriter writer = new BufferedWriter ( new OutputStreamWriter ( out ) );
            writer.write ( username );
            writer.close ( );
        } catch (IOException e) {
            e.printStackTrace ( );
        }
    }


    //内部存储目录：storage/emulated/0/Android/data/files
    private  void saveDataprivate(String fileName,String  username,String password) {


        //
        try {  //打开文件输入流
            File file = new File ( getExternalFilesDir ( "" ), fileName);
            File file1 = new File ( getExternalFilesDir ( "" ), password);
            //创建Buffer与writer
            FileOutputStream out = new FileOutputStream ( file );
            FileOutputStream out1 = new FileOutputStream ( file1 );
            BufferedWriter writer = new BufferedWriter ( new OutputStreamWriter ( out ) );
            BufferedWriter writer1 = new BufferedWriter ( new OutputStreamWriter ( out1 ) );
            //写入数据
            writer.write ( username);
            writer1.write ( password );
            ;
            //关闭数据
            writer1.close ();
            writer.close ( );
        } catch (IOException e) {
            e.printStackTrace ( );

        }
    }
//数据的删除
    private  void deleteData(String fileName) {
        File file = new File ( getExternalFilesDir ( "" ), fileName );
    if(file.isFile ()){
        if (file.delete()) {
            Toast.makeText(this, "删除外部公有文件成功", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "删除外部公有文件失败", Toast.LENGTH_SHORT).show();
        }


    }
    }
    private void deleteDataFile(String fileName) {
        if (deleteFile(fileName)) {
            Toast.makeText(this, "删除成功", Toast.LENGTH_SHORT).show();
        }
    }


    //数据的读取
    private String readData(String fileName,String passWord) throws IOException {

        String data  = null;
        FileInputStream in = openFileInput ( fileName );
        BufferedReader reader = new BufferedReader (new InputStreamReader ( in) );
        data = reader.readLine ();
        reader.close ();
        return data;
    }

    //数据存储方式一
//    读取
    private String readPrfs() {

        SharedPreferences sh = getSharedPreferences ( "userInfo",MODE_PRIVATE );
        return  sh.getString ( "username" ,"");

    }



//图片的保存

//    @Override
//    public void onClick(View v) {
//        switch (v.getId ()){
//            case R.id.bt1:
//                break;
//            case  R.id.bt2:
//                break;
//        }
//
//    }



}
