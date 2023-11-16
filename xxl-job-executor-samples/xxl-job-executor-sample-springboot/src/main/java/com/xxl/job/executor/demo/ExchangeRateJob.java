package com.xxl.job.executor.demo;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;

import java.io.IOException;

@Slf4j
public class ExchangeRateJob {


    public static void main(String[] args) {
        ExchangeRateJob rateJob = new ExchangeRateJob();
        long sleepTime = 10000L;
        Float minRate = 465f;
        Float maxRate = 480f;

        while (true) {
            try {
                String currentRate = rateJob.getExchangeRate();
                log.info("rate={}", currentRate);
                if (minRate.compareTo(Float.valueOf(currentRate)) >= 0) {
                    log.info("当前汇率:{},低于:{},发送消息通知。。。", currentRate, minRate);
                    rateJob.sendMsg(currentRate);
                }
                if (maxRate.compareTo(Float.valueOf(currentRate)) <= 0) {
                    log.info("当前汇率:{},高于:{},发送消息通知。。。", currentRate, maxRate);
                    rateJob.sendMsg(currentRate);
                }
                Thread.sleep(sleepTime);
            } catch (Exception e) {
                log.error("出错了。。。", e);
            }
        }

    }



    private String getExchangeRate() throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded; charset=UTF-8");
        RequestBody body = RequestBody.create(mediaType, "oratype=1&Area_code=0200&ajaxsend=1&tranCode=A00525");
        Request request = new Request.Builder()
                .url("https://mybank.icbc.com.cn/servlet/AsynGetDataServlet")
                .method("POST", body)
                .addHeader("Host", "mybank.icbc.com.cn")
                .addHeader("sec-ch-ua", "\"Chromium\";v=\"110\", \"Not A(Brand\";v=\"24\", \"Microsoft Edge\";v=\"110\"")
                .addHeader("Accept", "application/json, text/javascript, */*; q=0.01")
                .addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                .addHeader("X-Requested-With", "XMLHttpRequest")
                .addHeader("sec-ch-ua-mobile", "?0")
                .addHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/110.0.0.0 Safari/537.36 Edg/110.0.1587.69")
                .addHeader("sec-ch-ua-platform", "\"macOS\"")
                .addHeader("Origin", "https://mybank.icbc.com.cn")
                .addHeader("Sec-Fetch-Site", "same-origin")
                .addHeader("Sec-Fetch-Mode", "cors")
                .addHeader("Sec-Fetch-Dest", "empty")
                .addHeader("Referer", "https://mybank.icbc.com.cn/icbc/newperbank/perbank3/foreign/foreign_closing_sell_noSession.jsp?Area_code=1001")
                .addHeader("Accept-Language", "en-US,en;q=0.9")
                .addHeader("Cookie", "mainAreaCode=1702; grxxbhzz_time=20220406173906; isP3bank=1; isEn_US=0; isPri=; firstZoneNo=%E4%B8%8A%E6%B5%B7_1001; ariaDefaultTheme=undefined; sg___persistence__sugoio=%7B%22distinct_id%22%3A%20%221869d1e341a2828-0d691a5d8b1087-4f681f79-16a7f0-1869d1e341b2f43%22%2C%22first_visit_time%22%3A%201677674125323%2C%22curmenuid%22%3A%20%221438058326864052268%22%2C%22curmenu%22%3A%20%22%E5%AE%9A%E5%88%B6%E9%A1%B5%E9%9D%A2%22%2C%22srcchannel%22%3A%20%22%22%2C%22transitionid%22%3A%20%22%22%2C%22word1%22%3A%20%22%7B%5C%22p_scr%5C%22%3A%5C%221512*982%5C%22%2C%5C%22p_lg%5C%22%3A%5C%22en-US%5C%22%2C%5C%22p_user_analysis_id%5C%22%3A%5C%2220230301589087622%5C%22%2C%5C%22p_pv_type%5C%22%3A%5C%221%5C%22%2C%5C%22p_search_word%5C%22%3A%5C%22%5C%22%2C%5C%22p_inject_channel%5C%22%3A%5C%22%5C%22%2C%5C%22p_transaction_name%5C%22%3A%5C%22%5C%22%2C%5C%22p_transaction_code%5C%22%3A%5C%22%5C%22%2C%5C%22p_cur_channel%5C%22%3A%5C%220%5C%22%2C%5C%22p_cur_column_path%5C%22%3A%5C%22%2FICBC%2F%E7%BD%91%E4%B8%8A%E6%B1%87%E5%B8%82%2F%E5%AE%9A%E5%88%B6%E9%A1%B5%E9%9D%A2%5C%22%2C%5C%22p_cur_column_id%5C%22%3A%5C%221438058326864052268%5C%22%2C%5C%22p_cur_page_name%5C%22%3A%5C%22%E5%A4%96%E5%B8%81%E4%B9%B0%E5%8D%96%E8%AE%A1%E7%AE%97%E5%99%A8%5C%22%2C%5C%22p_cur_page_id%5C%22%3A%5C%22748115481806049280%5C%22%2C%5C%22p_cur_page_url%5C%22%3A%5C%22http%3A%2F%2Fwww.icbc.com.cn%2Ficbc%2Fpage%2F748115481806049280.html%5C%22%2C%5C%22p_cur_page_access_id%5C%22%3A%5C%22a1127b35-65ee-05d8-9b35-a91660d1faee%5C%22%2C%5C%22p_browser%5C%22%3A%5C%22chrome%5C%22%2C%5C%22p_search_engine%5C%22%3A%5C%22%5C%22%7D%22%2C%22__timers%22%3A%20%7B%7D%2C%22referring_domain%22%3A%20%22mybank.icbc.com.cn%22%2C%22session_id%22%3A%20%226b2ed009-ae7f-4ed4-a626-3a1d224faf3f%22%2C%22srcpagename%22%3A%20%22%E5%A4%96%E5%B8%81%E5%85%91%E6%8D%A2%EF%BC%88%E5%A4%96%E5%B8%81%E4%B8%8E%E5%A4%96%E5%B8%81%E9%97%B4%E5%85%91%E6%8D%A2%EF%BC%89%E8%AE%A1%E7%AE%97%E5%99%A8%20-%20%E5%A4%96%E6%B1%87%20-%20%E4%B8%AD%E5%9B%BD%E5%B7%A5%E5%95%86%E9%93%B6%E8%A1%8C%E4%B8%AD%E5%9B%BD%E7%BD%91%E7%AB%99%22%2C%22srcpageurl%22%3A%20%22https%3A%2F%2Fwww.icbc.com.cn%2Fpage%2F748115481806049280.html%22%7D; CK_ISW_EBANKP_EBANKP-WEB-IPV6-NEW_80=bambfbmbegmbfgnia-10|ZBJu3|ZBJtV; CK_ISW_EBANKP_EBANKP-WEB-IPV6-NEW_80=bambfbmbegmbfgnia-10|ZBJvt|ZBJtV")
                .build();
        Response response = client.newCall(request).execute();
        String respBody = new String(response.body().bytes(), "utf-8");

        JSONObject jsonObject = JSONObject.parse(respBody);

        JSONArray array = jsonObject.getJSONArray("rf");
        for (Object o : array) {
            JSONObject item = (JSONObject) o;
            if ("澳大利亚元".equals(item.getString("currTypeName"))) {
//                log.info("澳大利亚元 汇率={}",item.getString("sellePrice"));
                return item.getString("sellePrice");
            }
        }
        throw new RuntimeException("没有找到对应的汇率");
    }

    private void sendMsg(String currentRate) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        Request request = new Request.Builder()
                .url("https://api.day.app/NjqoqfctzkPkYqpLFERdrT/澳大利亚元汇率变动/当前汇率：" + currentRate)
                .method("GET", null)
                .build();
        Response response = client.newCall(request).execute();
    }
}
