package com.learning.net_retrofit_java;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GoRestService {

//    集成步骤：1.添加依赖；2.根据接口的响应来生成对应的解析文件；3.编辑接口文件中各个HTTP动词的注解；4.在调用处按照四部曲进行操作；5.添加网络访问权限
//    2021.07.20: 已完成1、2、5
//    2021.07.20: 待完成3、4
//    2021.07.21: 已完成CRUD

    //https://gorest.co.in/public/v1/users
    @GET("users")
    @Headers({"Accept:application/json","Content-Type:application/json"})
    Call<UserList> getUserList();

    //https://gorest.co.in/public/v1/users?name=a
    @GET("users")
    @Headers({"Accept:application/json","Content-Type:application/json"})
    Call<UserList> searchUsersName(@Query("name") String searchText);

    @GET("users")
    @Headers({"Accept:application/json","Content-Type:application/json"})
    Call<UserList> searchUsersEmail(@Query("email") String searchText);
//    fun getUserList() : Call<UserList>
//    fun searchUsers(@Query("name") searchText: String): Call<UserList>

    //https://gorest.co.in/public/v1/users/121
    @GET("users/{user_id}")
    @Headers({"Accept:application/json","Content-Type:application/json"})
    Call<UserResponse> getUser(@Path("user_id") String user_id);
//    fun getUser(@Path("user_id") user_id: String): Call<UserResponse>

    @POST("users")
    @Headers({"Accept:application/json", "Content-Type:application/json",
            "Authorization: Bearer 73668350bdf06c66f3388408c1a712b378c3e25da599753b21b664a6261e246c"})
    Call<UserResponse> createUser(@Body Data params);
//    fun createUser(@Body params: User): Call<UserResponse>

    @PATCH("users/{user_id}")
    @Headers({"Accept:application/json", "Content-Type:application/json",
            "Authorization: Bearer 73668350bdf06c66f3388408c1a712b378c3e25da599753b21b664a6261e246c"})
    Call<UserResponse> updateUser(@Path("user_id") String user_id, @Body Data params);
//    fun updateUser(@Path("user_id") user_id: String, @Body params: User): Call<UserResponse>

    @DELETE("users/{user_id}")
    @Headers({"Accept:application/json", "Content-Type:application/json",
            "Authorization: Bearer 73668350bdf06c66f3388408c1a712b378c3e25da599753b21b664a6261e246c"})
    Call<UserResponse> deleteUser(@Path("user_id") String user_id);
//    fun deleteUser(@Path("user_id") user_id: String): Call<UserResponse>
}
