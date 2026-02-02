package com.example.fundestimate.service;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.example.fundestimate.model.FundEstimateInfo;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class FundService {

    // 天天基金估值接口
    private static final String FUND_API_URL = "http://fundgz.1234567.com.cn/js/%s.js?rt=%d";

    public FundEstimateInfo getFundEstimate(String fundCode) {
        try {
            String url = String.format(FUND_API_URL, fundCode, System.currentTimeMillis());
            String response = HttpUtil.get(url);

            // 天天基金返回的是 js 回调格式：jsonpgz({"fundcode":"001186",...});
            Pattern pattern = Pattern.compile("jsonpgz\\((.*)\\);");
            Matcher matcher = pattern.matcher(response);

            if (matcher.find()) {
                String jsonStr = matcher.group(1);
                return JSONUtil.toBean(jsonStr, FundEstimateInfo.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
