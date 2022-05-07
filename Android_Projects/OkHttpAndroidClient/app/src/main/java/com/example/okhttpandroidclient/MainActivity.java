package com.example.okhttpandroidclient;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Android OkHttp网络请求
 * get 和 post （带Token）
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setget();
//        setpost();

    }



    /**
     * post请求
     * http://www.lygjjdj.com:82/HZZHDJ/api/base64Upload.do?base64String=data:image/png;base64,......
     * token : 2413a775f7d3fcfa0ebb625ac4e8ac35
     */
    public void setpost() {
        //post请求 传参
//        FormBody formBody = new FormBody.Builder()
//                .add("base64String","data:image/png;base64,"+imgStr)
//                .add("id","15")
//                .add("name","这是")
//                .build();

        Request request = new Request.Builder()
                .url("http://www.baidu.com")
//                .url("http://api-dev.bs.timework.cn/property/v1/tenement/faceSync")
//                .addHeader("Authorization","2413a775f7d3fcfa0ebb625ac4e8ac35")
//                .post(formBody)
                .build();

        OkHttpClient client = new OkHttpClient();
        client.newCall(request).enqueue(new Callback() {
            public void onFailure(Call call, IOException e) {
                Log.d("111111post请求失败======","e.getMessage()"+e.getMessage());
            }

            public void onResponse(Call call, Response response) throws IOException {
                //请求成功返回结果
                //如果希望返回的是字符串
                final String responseData=response.body().string();
                // 如果希望返回的是inputStream,有inputStream我们就可以通过IO的方式写文件.
//                InputStream responseStream=response.body().byteStream();
                // 注意，此时的线程不是ui线程，
                // 如果此时我们要用返回的数据进行ui更新，操作控件，就要使用相关方法
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // 更新UI的操作
                         Log.d("111111post请求成功======",responseData );
//                        Bean bean = new Gson().fromJson(responseData, Bean.class );
//                        Log.d("111111post请求成功======","getHeadImg"+bean.getData().getHeadImg());
//                        Log.d("111111post请求成功======","getHeadImg"+bean.getData().getLoginName());
                    }
                });
            }
        });

    }


    /**
     * get请求
     * http://www.lygjjdj.com:82/HZZHDJ/api/base64Upload.do?base64String=data:image/png;base64,......
     * token : 2413a775f7d3fcfa0ebb625ac4e8ac35
     */
    public void setget() {
        String url = "http://www.baidu.com";
//                "&name=" + mine_02.getText().toString() +
//                "&phone=" + mine_03.getText().toString() +
//                "&address=" + mine_04.getText().toString() +
//                "&headImg=" + imgsrc;

        Request.Builder builder = new Request.Builder()
                .url( url );
//                .addHeader("Authorization","bearer cb61f47b-d1a3-4a4d-9abf-3684b20e3ec3");
        Request build = builder.build();

        OkHttpClient client = new OkHttpClient.Builder().readTimeout( 5000, TimeUnit.SECONDS ).build();
        Call call = client.newCall( build );
        call.enqueue( new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("111111get请求失败======","e.getMessage()"+e.getMessage());
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //请求成功返回结果
                //如果希望返回的是字符串
                final String responseData=response.body().string();
                //如果希望返回的是inputStream,有inputStream我们就可以通过IO的方式写文件.
//                InputStream responseStream=response.body().byteStream();
                //注意，此时的线程不是ui线程，
                // 如果此时我们要用返回的数据进行ui更新，操作控件，就要使用相关方法
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // 更新UI的操作
                         Log.d("111111get请求成功======",responseData );
//                        Bean bean = new Gson().fromJson(responseData, Bean.class );
//                        Log.d("111111get请求成功======","getHeadImg"+bean.getData().getHeadImg());
//                        Log.d("111111get请求成功======","getHeadImg"+bean.getData().getLoginName());
                    }
                });
            }
        });
    }

}