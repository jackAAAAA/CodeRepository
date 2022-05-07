package com.funnyweather.android.logic.network

import com.funnyweather.android.FunnyWeatherApplication
import com.funnyweather.android.logic.model.PlaceResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PlaceService {
//    不管哪种情形，“?/& + @Query的value + = + 查询内容”都添加在最后
//    由于在url？之后添加查询内容，所以直接在url的最后添加&query=shenzhen
//    https://api.caiyunapp.com/v2/place?token=BNBRvBveaD2VfHVI&lang=zh_CN&query=shenzhen
    @GET("v2/place?token=${FunnyWeatherApplication.TOKEN}&lang=zh_CN")

//    问号的末尾为查询内容，不/可省略——1；2
//    1. 在url最后添加问号，此时默认自动添加四部分内容：1.& + 2.@Query的value + 3.= + 4.查询内容；
    @GET("v2/place?")

//    2. 不添加问号，则默认自动也在url的最后添加四部分内容：1.? + 2.@Query的value + 3.= + 4.查询内容
    @GET("v2/place")

//    纠错！！！——1；2；3
//    1. 对于错误的写法，当时的猜测解释不通时，不要强行解释

//    2. 不得有替换体{query}
//    @GET("v2/place?query={query}&token=${FunnyWeatherApplication.TOKEN}&lang=zh_CN")

//    3. 有注解@Query时不得填写"query="，否则导致实际请求的url为：https://api.caiyunapp.com/v2/place?query=&token=BNBRvBveaD2VfHVI&lang=zh_CN&query=thanks
//    @GET("v2/place?query=&token=${FunnyWeatherApplication.TOKEN}&lang=zh_CN")
    fun searchPlaces(@Query("query") query: String): Call<PlaceResponse>
}