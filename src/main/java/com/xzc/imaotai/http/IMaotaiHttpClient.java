package com.xzc.imaotai.http;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.github.lianjiatech.retrofit.spring.boot.core.RetrofitClient;
import com.xzc.imaotai.util.SpringBeanUtil;
import okhttp3.ResponseBody;
import org.springframework.core.env.Environment;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.*;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author Klein Moretti
 */
@RetrofitClient(baseUrl = "https://static.moutai519.com.cn")
public interface IMaotaiHttpClient {

    Map<String, String> HEADERS = Map.of(
            "Origin", "https://h5.moutai519.com.cn",
            "Accept", "application/json, text/javascript, */*; q=0.01",
            "MT-R", "clips_OlU6TmFRag5rCXwbNAQ/Tz1SKlN8THcecBp/",
            "Client-User-Agent", "iOS;16.0.1;Apple;iPhone 14 ProMax",
            "User-Agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 15_0_1 like Mac OS X)",
            "Accept-Language", "zh-CN,zh-Hans;q=1"
    );

    @GET("/mt-backend/xhr/front/mall/index/session/get/{timestamp}")
    String getSessionId(@HeaderMap Map<String, String> headers,
                        @Path("timestamp") String timestamp);

    default String getSessionId(String timestamp) {
        return getSessionId(getHeader(), timestamp);
    }

    default Map<String, String> getHeader() {
        Environment environment = SpringBeanUtil.getBean(Environment.class);
        String version = environment.getProperty("imaotai.version");
        Map<String, String> map = new HashMap<>(HEADERS);
        map.put("MT-APP-Version", StringUtils.hasLength(version) ? version : "1.4.0");
        return map;
    }

    @GET("/mt-backend/xhr/front/mall/shop/list/slim/v3/{sessionId}/{province}/{itemId}/{timestamp}")
    String getMallList(@HeaderMap Map<String, String> headers,
                       @Path("sessionId") String sessionId,
                       @Path("province") String province,
                       @Path("itemId") String itemId,
                       @Path("timestamp") String timestamp);

    default String getMallList(String sessionId, String province, String itemId, String timestamp) {
        return getMallList(getHeader(), sessionId, province, itemId, timestamp);
    }

    @GET("/mt-backend/xhr/front/mall/resource/get")
    String getMap(@HeaderMap Map<String, String> headers);

    default String getMap() {
        String map = getMap(getHeader());
        JSONObject jsonObject = (JSONObject) JSONPath.read(map, "$.data.mtshops_pc");
        StringBuffer str = new StringBuffer();
        Optional.ofNullable(jsonObject).ifPresent(j -> {
            String url = jsonObject.getString("url");
            if (StringUtils.hasLength(url)) {
                Call<ResponseBody> call = downloadFile(url);
                try {
                    Response<ResponseBody> response = call.execute();
                    if (response.isSuccessful() && response.body() != null) {
                        InputStream inputStream = response.body().byteStream();
                        str.append(new String(FileCopyUtils.copyToByteArray(inputStream), StandardCharsets.UTF_8));
                    }
                } catch (IOException ignore) {
                }
            }
        });
        return str.toString();
    }

    @GET
    @Streaming
    Call<ResponseBody> downloadFile(@Url String url);

}
