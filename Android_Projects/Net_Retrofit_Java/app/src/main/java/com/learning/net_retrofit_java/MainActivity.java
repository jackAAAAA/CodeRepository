package com.learning.net_retrofit_java;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();

//        step_1
        Retrofit retrofit = new Retrofit.Builder()
                //GitHub
//                .baseUrl("https://api.github.com/")
//                GoRest
                .baseUrl("https://gorest.co.in/public/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

//        step_2
//        GitHubService
        GitHubService gitHubService = retrofit.create(GitHubService.class);
    //        GoRest
        GoRestService goRestService = retrofit.create((GoRestService.class));

//        step_3
//        GitHubService
        Call<List<Repo>> gitHubResponse = gitHubService.listRepos("octocat");

    //        GoRest
    //        getUserList()
            Call<UserList> goRestResponse = goRestService.getUserList();

    //        searchUsers()
//            Call<UserList> goRestResponse = goRestService.searchUsersEmail("wwwwwww@71.com");

    //        getUser()
    //        Call<UserResponse> goRestResponse = goRestService.getUser("121");

//        Data newer = new Data( "test", "female",
//                "wwwwwwwww@71.com", "inactive");
        Data newer = new Data();
        newer.setId("");
        newer.setName("testUpdate");
        newer.setGender("male");
        newer.setEmail("wwww@721.com");
        newer.setStatus("inactive");
    //    createUser()
//        Call<UserResponse> goRestResponse = goRestService.createUser(newer);

//        updateUser()
//        Call<UserResponse> goRestResponse = goRestService.updateUser("2329", newer);

//        Call<UserResponse> goRestResponse = goRestService.deleteUser("1");

    //        step_4
    //        GoRest
    goRestResponse.enqueue(
        new Callback<UserList>() {
          @Override
          public void onResponse(@NonNull Call<UserList> call, @NonNull Response<UserList> response) {
//              assert response.body() != null;

              System.out.println("Response: "+ response.code());
          }

          @Override
          public void onFailure(@NonNull Call<UserList> call, @NonNull Throwable t) {
              System.out.println("Failed!!!");
          }
        });

//        GitHubService
//        gitHubResponse.enqueue(new Callback<List<Repo>>() {
//            @Override
//            public void onResponse(Call<List<Repo>> call, Response<List<Repo>> response) {
//                if (response.body() != null) {
////                    该账户的第0个代码仓库的用户名
////                    System.out.println("Response：" + response.body().get(0).name);
////                    该账户下的代码仓库的数量
//                    System.out.println("Response：" + response.body().get(0).toString());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<Repo>> call, Throwable t) {
////                TO DO
//                System.out.println("Failed!!!");
//            }
//        });

    }
}

/*在Android中利用Retrofit进行网络请求的完整5步骤：
 * 1. 在module对应的build.gradle文件中添加retrofit和gson解析的依赖
 * 2. 利用工具将Json解析字符串数据转换成Java class文件/Kotlin data class文件(注意要选择Json格式)
 * 3. 根据后端编写的HTTP请求方案，配置用HTTP动词注解的包含相对地址的接口文件
 * 4. 在网络请求调用处（比如：MainActivity）按照“四部曲”：
 * retrofit -> service -> response -> response.enqueue 进行代码完善
 * 5. 在AndroidManifest.xml文件中添加
 * 网络请求权限——<uses-permission android:name="android.permission.INTERNET"/>
 */