package com.xxl.job.executor.service.jobhandler;


import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import com.xxl.job.executor.util.DateUtil;
import com.xxl.job.executor.util.DelayUtil;
import com.xxl.job.executor.util.EMailSender;
import com.xxl.job.executor.util.MsgUtil;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 阿里云盘签到
 */
@Component
public class AliyunSigninJob {
    private static Logger logger = LoggerFactory.getLogger(AliyunSigninJob.class);

    @Autowired
    private EMailSender eMailSender;

    public static void main(String[] args) throws IOException {
        AliyunSigninJob job = new AliyunSigninJob();
        String token1 = job.getToken("a6448b4747e148359487f1bd98d63bab");
        System.out.println(token1);
//        String token = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiI2ODEyNmZhM2ZkMmQ0YTIyOTA0NDFhOThjNjQxZjQwMyIsImN1c3RvbUpzb24iOiJ7XCJjbGllbnRJZFwiOlwicEpaSW5OSE4yZFpXazhxZ1wiLFwiZG9tYWluSWRcIjpcImJqMjlcIixcInNjb3BlXCI6W1wiRFJJVkUuQUxMXCIsXCJGSUxFLkFMTFwiLFwiVklFVy5BTExcIixcIlNIQVJFLkFMTFwiLFwiU1RPUkFHRS5BTExcIixcIlNUT1JBR0VGSUxFLkxJU1RcIixcIlVTRVIuQUxMXCIsXCJCQVRDSFwiLFwiQUNDT1VOVC5BTExcIixcIklNQUdFLkFMTFwiLFwiSU5WSVRFLkFMTFwiLFwiU1lOQ01BUFBJTkcuTElTVFwiXSxcInJvbGVcIjpcInVzZXJcIixcInJlZlwiOlwiXCIsXCJkZXZpY2VfaWRcIjpcIjY2YzZiOTVmYzQzYjQ0MzdiNTJiYjdiZTA1NWZjZjA0XCJ9IiwiZXhwIjoxNjg1NjkzMjg3LCJpYXQiOjE2ODU2ODYwMjd9.Vxxjd75zB_rwHilGoqceN8GfkoH52YIv9TioIzd-cbaLOJl-dndzM-lBsYY1BfwdFUKdbxUr7RD38ba2ZDj7cGG34YrjB8yY9SNqFPk1iK-NorLEYiIaQYc4VzmvVG3IMZMlhXDMd5ohjWTDoSe9lnbrW0_Y6F0zjq7MNohvX9A";
//        List<Integer> days = job.signInList(token);
//        logger.info("阿里云盘签到 2.签到完成 待领取奖励days={}",JSONObject.toJSONString(days));

    }


    /**
     * 阿里云盘签到领奖励
     */
    @XxlJob("aliyunSignIn")
    public void aliyunSignIn() throws Exception {
        String param = XxlJobHelper.getJobParam();
        JSONObject json = JSONObject.parse(param);
        DelayUtil.delayExecRandom(json.getInteger("delayMinute") * 60);

        //1.获取token
        String token = this.getToken(json.getString("refreshToken"));

        //2.签到
        List<Integer> days = this.signInList(token);
        String getRewardSwitch = json.getString("getReward");
        logger.info("阿里云盘签到 2.签到完成 待领取奖励days={},是否领取={}",JSONObject.toJSONString(days), getRewardSwitch);
        XxlJobHelper.log("阿里云盘签到 2.签到完成 待领取奖励days={},是否领取={}",JSONObject.toJSONString(days), getRewardSwitch);
        DelayUtil.delayExecRandom(3);
        //3.领奖励
        //当月最后一天，-->领取
        if (DateUtil.isLastDayOfMonth(new Date())) {
            logger.info("当前为本月最后一天，领取所有待领取奖励.....");
            XxlJobHelper.log("当前为本月最后一天，领取所有待领取奖励.....");
            getRewardSwitch = "ON";
        }
        if ("ON".equals(getRewardSwitch)) {
            for (Integer day : days) {
                this.reward(day, token);
            }
        }
    }

    /**
     * 获取token
     */
    private String getToken(String refreshToken) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json; charset=UTF-8");
        RequestBody body = RequestBody.create(mediaType, "{\"grant_type\":\"refresh_token\",\"app_id\":\"pJZInNHN2dZWk8qg\",\"refresh_token\":\"" + refreshToken +"\"}");
        Request request = new Request.Builder()
                .url("https://auth.aliyundrive.com/v2/account/token")
                .method("POST", body)
                .addHeader("Host", "auth.aliyundrive.com")
                .addHeader("Cookie", "isg=BFNThn5LMwrFsPjdNeAlJpVh6NN9COfKft0exAVwr3KphHMmjdh3GrHGuHrqPz_C; _nk_=t-2210970082246-52; _tb_token_=f5db58055bbe0; cookie2=6e57246e784ec952654e04c2d89aff2b; csg=61d196a4; munb=2210970082246; t=53a8a0eac53f209bfac077cfcb819342; cna=RZkfGxKjz04CAWVUupxV9hCB")
                .addHeader("content-type", "application/json; charset=UTF-8")
                .addHeader("accept", "*/*")
                .addHeader("authorization", "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiI2ODEyNmZhM2ZkMmQ0YTIyOTA0NDFhOThjNjQxZjQwMyIsImN1c3RvbUpzb24iOiJ7XCJjbGllbnRJZFwiOlwicEpaSW5OSE4yZFpXazhxZ1wiLFwiZG9tYWluSWRcIjpcImJqMjlcIixcInNjb3BlXCI6W1wiRFJJVkUuQUxMXCIsXCJGSUxFLkFMTFwiLFwiVklFVy5BTExcIixcIlNIQVJFLkFMTFwiLFwiU1RPUkFHRS5BTExcIixcIlNUT1JBR0VGSUxFLkxJU1RcIixcIlVTRVIuQUxMXCIsXCJCQVRDSFwiLFwiQUNDT1VOVC5BTExcIixcIklNQUdFLkFMTFwiLFwiSU5WSVRFLkFMTFwiLFwiU1lOQ01BUFBJTkcuTElTVFwiXSxcInJvbGVcIjpcInVzZXJcIixcInJlZlwiOlwiXCIsXCJkZXZpY2VfaWRcIjpcIjE1NDI4ZDRjODBmNzQyNjRiMDQ1ZDIxZTBiOGYzYTdkXCJ9IiwiZXhwIjoxNjg1MDkwNDUyLCJpYXQiOjE2ODUwODMxOTJ9.mLZLjExyivyt422fNeReVZ5N4ldeWjYx4ewJNSES_8NemHrQTKqN0YK5W-2tueEsLZ0NrpxtdiKDOv_37CyboXSq9bwBZImRScK_EXKtEZqCRQOIQD3ZTne1oftH1zs8fLbz7wSE8lmMfC5vL-cvfOgPQaxIBfjScUWKvwiSTEk")
                .addHeader("x-canary", "client=iOS,app=adrive,version=v4.6.1")
                .addHeader("accept-language", "en-AU,en;q=0.9")
                .addHeader("user-agent", "AliApp(AYSD/4.6.1) com.alicloud.smartdrive/4.6.1 Version/16.3.1 Channel/201200 Language/en-AU /iOS Mobile/iPhone11,8")
                .addHeader("x-device-id", "a5e1b5ba3695371065e2bfa7be08be69fa6eda4d1b18086cca92cfe133c89b27")
                .build();
        Response response = client.newCall(request).execute();
        String respBody = new String(response.body().bytes(), "utf-8");
        logger.info("阿里云盘签到 1.获取token respBody={}",respBody);
        XxlJobHelper.log("阿里云盘签到 1.获取token respBody={}", respBody);
        JSONObject resJson = JSONObject.parse(respBody);
        return resJson.getString("access_token");
    }

    /**
     * 签到并返回未领取奖励的days
     */
    private List<Integer> signInList(String token) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json; charset=UTF-8");
        RequestBody body = RequestBody.create(mediaType, "{\"isReward\":false}");
        Request request = new Request.Builder()
                .url("https://member.aliyundrive.com/v1/activity/sign_in_list?_rx-s=mobile")
                .method("POST", body)
                .addHeader("Host", "member.aliyundrive.com")
                .addHeader("Cookie", "isg=BHZ2nTgpDrfmCv1W0I8ozShGzah4l7rR05rbD-BfYtn0Ixa9SCcK4dzBPXFPkLLp; _nk_=t-2210970082246-52; _tb_token_=bed5b9ebe3b7; cookie2=1de00b66181aac15036ae4e7cc8051fc; csg=e4ce2d10; munb=2210970082246; t=90fc55c3cfc046308fa5846eaef89e9d; cna=RZkfGxKjz04CAWVUupxV9hCB")
                .addHeader("content-type", "application/json; charset=UTF-8")
                .addHeader("authorization", token)
                .addHeader("accept", "*/*")
                .addHeader("x-signature", "9442fb80bdb9aafc8dcce9cbbbd9d47c9d20d8f30f5ed566aeace06dae419eec6ad04e930ad9ab3a67c2f47dd13c4be6916b828306fde902452375deb3e9798401")
                .addHeader("x-umt", "x5UBT/pLPLKtYxKIcAkUpRbDPvP4KBvz")
                .addHeader("x-sign", "izK9q4002xAALD2QTOTBpQLb6BR2nD2cM5twqhdV+NyJ2U2N9siOU0WGrLS76qqEOqXT4HeC2tru7vn4baV52++r7zy93D2cPdw9nD")
                .addHeader("x-canary", "client=web,app=other,version=v0.1.0")
                .addHeader("x-sgext", "JAfWJJ2LjkBp/qJKXuJ80iTjFOcH5BfvFfUV4RL1B+cS7hHjFeYX4hT1FOYW4BTmFOYU5hTmFOYU5gfmB+YH5hT1FOYU5gfmB+YH5gfmB+YH5gfmB+YH5hTmFOY=")
                .addHeader("accept-language", "en-AU,en;q=0.9")
                .addHeader("x-mini-wua", "ipAS6VF41QlVGfE30MF9WOanB6CrNZDu+YMCE8ZqM3okZCfGQubwQYBcwvoTGcsAZ8UR6g5XDfeGVawugelbQmL/rwqxLUpB6j/teve6cCdfAoKeExAYqf5MTNVRqgGcBETqTPsUMleEtvgL/K2zSDTbEsuV4Gfhlf1nwOX9TBlz2dw==")
                .addHeader("user-agent", "AliApp(AYSD/4.6.1) com.alicloud.smartdrive/4.6.1 Version/16.3.1 Channel/201200 Language/en-AU /iOS Mobile/iPhone11,8")
                .addHeader("x-device-id", "a5e1b5ba3695371065e2bfa7be08be69fa6eda4d1b18086cca92cfe133c89b27")
                .addHeader("referer", "https://aliyundrive.com/")
                .build();
        Response response = client.newCall(request).execute();
        String respBody = new String(response.body().bytes(), "utf-8");
        logger.info("阿里云盘签到 2.签到 respBody={}",respBody);
        XxlJobHelper.log("阿里云盘签到 2.签到 respBody={}", respBody);
        JSONObject resJson = JSONObject.parse(respBody);
        if ("AccessTokenInvalid".equals(resJson.getString("code"))) {
            //token失效
            MsgUtil.sendBarkMsg("阿里云盘签到执行失败", "token失效");
            throw new RuntimeException("token失效");
        }
        JSONObject resultJson = resJson.getJSONObject("result");
        JSONArray signInLogs = resultJson.getJSONArray("signInLogs");
        //待领取奖励的day 已签到，未领奖励
        List<Integer> days = new ArrayList<>();
        for (Object obj : signInLogs) {
            JSONObject signInLog = (JSONObject) obj;
            if ("normal".equals(signInLog.getString("status")) && Boolean.FALSE == signInLog.getBoolean("isReward")) {
                days.add(signInLog.getInteger("day"));
            }
        }
        return days;
    }

    /**
     * 领奖励
     */
    private void reward(Integer signInDay, String token) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json; charset=UTF-8");
        JSONObject req = new JSONObject();
        req.put("signInDay", signInDay);
        RequestBody body = RequestBody.create(mediaType, req.toJSONString());
        Request request = new Request.Builder()
                .url("https://member.aliyundrive.com/v1/activity/sign_in_reward?_rx-s=mobile")
                .method("POST", body)
                .addHeader("Host", "member.aliyundrive.com")
                .addHeader("Cookie", "isg=BAcHanUrL-qSnKxhCWSpwqGtnL_RDNvu6qFqWNn0Ixa9SCcK4dxrPkUa7IYWu7Nm; _nk_=t-2210970082246-52; _tb_token_=f5653ed5d33a9; cookie2=1de00b66181aac15036ae4e7cc8051fc; csg=c08b32ef; munb=2210970082246; t=eef02c704b3028e8b61357617836551c; cna=RZkfGxKjz04CAWVUupxV9hCB")
                .addHeader("content-type", "application/json; charset=UTF-8")
                .addHeader("authorization", token)
                .addHeader("accept", "*/*")
                .addHeader("x-signature", "aa90910c3e5420b11c784d10ccd850c8372aa643592850bf5d0fb8e232dd4d1039d80fe2938b6900d7610e94a8c82e09e25ed5d5278332bc98e79195029b56a700")
                .addHeader("x-umt", "RFcBHLVLPPeBqRKIZ2lxgHOe58Sh7nRt")
                .addHeader("x-sign", "izK9q4002xAALoLAfRx9CKkouQCgPoLOjMnP+KgHR448rrLfKOoxDzeic+YH9gn+tnXIDKPU22LxvEaq0vfGiVD5UG6C3oLOgt6Czo")
                .addHeader("x-canary", "client=web,app=other,version=v0.1.0")
                .addHeader("x-sgext", "JAdpd8403f86QfH1DV0vbXdcR1hUWEdeQEpDXlRKRl9PXEReR11GXlRZR1tBWUdZR1lHWUdZR1lHSkdKR0pHWVRZR1lHSkdKR0pHSkdKR0pHSkdKR0pHWUdZRw==")
                .addHeader("accept-language", "en-AU,en;q=0.9")
                .addHeader("x-mini-wua", "ixwRfxnB5uMMnr2mqypcYEjA/UX8bTP+I49cZbN4dCXUYe+zCHjLzg+ScDZcUH0+F+zAWtYTSykA6nyBYhSyYrp+CHLnnsTFIpTJ6J9YBIaShDAGHXlHofaWbkicRbWyaxyPb2KQW5I3e9IniDrUWonmiOazDlLpiLObHQ7VsQ3BuLw==")
                .addHeader("user-agent", "AliApp(AYSD/4.6.1) com.alicloud.smartdrive/4.6.1 Version/16.3.1 Channel/201200 Language/en-AU /iOS Mobile/iPhone11,8")
                .addHeader("x-device-id", "a5e1b5ba3695371065e2bfa7be08be69fa6eda4d1b18086cca92cfe133c89b27")
                .addHeader("referer", "https://aliyundrive.com/")
                .build();
        Response response = client.newCall(request).execute();
        String respBody = new String(response.body().bytes(), "utf-8");
        logger.info("阿里云盘签到 3.领奖励 reqBody={},respBody={}",respBody, JSONObject.toJSONString(req));
        XxlJobHelper.log("阿里云盘签到 3.领奖励 reqBody={},respBody={}", respBody, JSONObject.toJSONString(req));

        JSONObject resJson = JSONObject.parse(respBody);
        if (!resJson.getBoolean("success")) {
            MsgUtil.sendBarkMsg("阿里云盘签到执行失败", respBody);
            throw new RuntimeException("阿里云盘签到执行失败 success=" + resJson.getBoolean("success"));
        }
        if ("AccessTokenInvalid".equals(resJson.getString("code"))) {
            //token失效
            MsgUtil.sendBarkMsg("阿里云盘签到执行失败", "token失效");
            throw new RuntimeException("token失效");
        }


    }



}
