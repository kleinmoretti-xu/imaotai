package com.xzc.imaotai.http;

import com.github.lianjiatech.retrofit.spring.boot.core.RetrofitClient;
import com.xzc.imaotai.util.SpringBeanUtil;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.Path;

import java.util.Map;

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
                        @Header("MT-APP-Version") String version,
                        @Path("timestamp") String timestamp);

    default String getSessionId(String timestamp) {
        return getSessionId(HEADERS, getVersion(), timestamp);
    }

    default String getVersion() {
        Environment environment = SpringBeanUtil.getBean(Environment.class);
        String version = environment.getProperty("imaotai.version");
        return StringUtils.hasLength(version) ? version : "1.4.0";
    }

    @GET("/mt-backend/xhr/front/mall/shop/list/slim/v3/{sessionId}/{province}/{itemId}/{timestamp}")
    String getMallList(@HeaderMap Map<String, String> headers,
                       @Header("MT-APP-Version") String version,
                       @Path("sessionId") String sessionId,
                       @Path("province") String province,
                       @Path("itemId") String itemId,
                       @Path("timestamp") String timestamp);

    default String getMallList(String sessionId, String province, String itemId, String timestamp) {
        return getMallList(HEADERS, getVersion(), sessionId, province, itemId, timestamp);
    }

}
