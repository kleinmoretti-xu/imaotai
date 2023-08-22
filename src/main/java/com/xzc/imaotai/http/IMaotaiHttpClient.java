package com.xzc.imaotai.http;

import com.github.lianjiatech.retrofit.spring.boot.core.RetrofitClient;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * @author Klein Moretti
 */
@RetrofitClient(baseUrl = "https://static.moutai519.com.cn")
public interface IMaotaiHttpClient {



    @GET("/mt-backend/xhr/front/mall/index/session/get/{timestamp}")
    String getSessionId(@Path("timestamp") String timestamp);


    @GET("/mt-backend/xhr/front/mall/shop/list/slim/v3/{sessionId}/{province}/{itemId}/{timestamp}")
    String getMallList(@Path("sessionId") String sessionId, @Path("province") String province, @Path("itemId") String itemId, @Path("timestamp") String timestamp);

}
