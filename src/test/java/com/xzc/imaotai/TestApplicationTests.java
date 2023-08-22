package com.xzc.imaotai;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xzc.imaotai.http.IMaotaiHttpClient;
import com.xzc.imaotai.util.DateUtil;
import com.xzc.imaotai.vo.MaotaiBaseVO;
import com.xzc.imaotai.vo.MaotaiSessionVO;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.IOException;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestApplicationTests extends TestCase {

    @Resource
    IMaotaiHttpClient client;

    @Test
    public void test() throws IOException {
        String concat = DateUtil.dateToString(DateUtil.getCurrentDate(), DateUtil.DATE_FORMAT_YYYY_MM_DD).concat(" 00:00:00");
        String result = client.getSessionId(String.valueOf(DateUtil.dateStringToDate(concat, DateUtil.DATE_FORMAT_YYYY_MM_DD_HH_MM_SS).getTime()));
        JSONObject parse = (JSONObject) JSON.parse(result);
        ObjectMapper objectMapper = new ObjectMapper();
        MaotaiBaseVO<MaotaiSessionVO> readValue = objectMapper.readValue(parse.toJSONString(), new TypeReference<>() {
        });
        System.out.println(readValue);
    }
}
