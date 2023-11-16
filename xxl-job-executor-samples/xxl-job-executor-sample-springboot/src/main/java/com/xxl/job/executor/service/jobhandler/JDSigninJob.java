package com.xxl.job.executor.service.jobhandler;


import com.alibaba.fastjson2.JSONObject;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import com.xxl.job.core.util.DateUtil;
import com.xxl.job.executor.model.SendMailReq;
import com.xxl.job.executor.util.DelayUtil;
import com.xxl.job.executor.util.EMailSender;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

@Component
public class JDSigninJob {
    private static Logger logger = LoggerFactory.getLogger(JDSigninJob.class);

    @Autowired
    private EMailSender eMailSender;


    /**
     * 签到领京豆
     */
    @XxlJob("signBeanAct")
    public void jdSignBeanAct() throws Exception {
        String param = XxlJobHelper.getJobParam();
        JSONObject json = JSONObject.parse(param);
        DelayUtil.delayExecRandom(json.getInteger("delayMinute") * 60);

        String cookie = "__jd_ref_cls=JingDou_SceneHome_SignIn; mba_muid=16620014423941537449433.4941.1677809997915; mba_sid=4941.16; __jda=47827316.16620014423941537449433.1662001442.1677802984.1677809584.350; __jdb=47827316.3.16620014423941537449433|350.1677809584; __jdv=47827316%7Ckong%7Ct_1003343691_7e158461624d4f8ea01c0a96cb2ff1e1-519%7Ctuiguang%7C7af10f7fbc2c415da1a09d3a4429e7d8%7C1677597341000; mobilev=touch; pwdt_id=%E5%A5%87%E8%BF%B9%E8%8B%8F%E5%AE%87%E8%B6%85; sid=2fe0c8b1aa0e02fac7575effa1b2b47w; unpl=JF8EAN9nNSttXhsDBRpVHUVDGV4GW10MHkcDP2YNAV8ITlEGE1YdRxl7XlVdXxRKFB9sbhRXX1NLUA4eAisiEEpcVlxYCkwfA19hUVRZUU5TBRwBHkcVHFQADlwIGRZTZ2BXB14PHFRRGx4fEhh7WlRcWwBOFjNsZzVVbVhMUgMfCh0QEkxZXF5YC0MSC2hhBmRcaEtcASsCGhMXS1lRXl8NSBQFZ1c1UV1ZSlAHHQUSESBKbVZfXA1JFgppZgxkHDZLVQUSAhwREAZdU1hbDEMRAW1gAVxdXUhcABMFHREgSm1X%7CJF8EAOJnNSttUEgADR4DEkcZHFoAW10BGR8BOGcDVVoNTQANHFUbE0R7XlVdXxRKFx9sZhRVVFNOVg4eBCsSFHteVV5VAE4eBGhvNVRYWENWARIBKxIVSTNWWlUNSxAKbmJrVF02e1cFKwMrGkMbXlBYX19OE1Q_N1BRCgxIBAMaURNFFB9YVVtZAB9HVjtiAl0ND3tVNRIDKxIRSlhVXVoPQhACb2EAZG1Ze1U1GjJafBBKXVZXWglOWgs8NwZQW1ocUQFMUktHFRwJVw5bCRgfVGszAFVYXEMAVU5WHhUZGwpkX20I; __jdc=47827316; pre_seq=6; pre_session=9ba2573f45faad4fe2a70b9f5e40459eade468af|11448; RT=\"z=1&dm=jd.com&si=dg7bzmdf1h&ss=leoe55j0&sl=4&tt=27n&ld=vbsw\"; 3AB9D23F7A4B3C9B=JZZB23WH2LW4JREZGCH4HKHDLNQNARMW4FVQDQDJ2LF44IZ4SFEJJYXCS3JQBWQG2WA3TVO4ZPN6XHF6FTJFLPHUOY; BATQW722QTLYVCRD={\"tk\":\"jdd01PCORMIHCFNPQHJMWBJBNFKVYI646EK3YDO6ENZHEFONISNHIN55ADSZDCXDV7JYT6OJJMYD2IM5PIDCAMBV4CW6JIKEZGMRXP3WWWNY01234567\",\"t\":1677598802323}; _gia_s_e_joint={\"eid\":\"JZZB23WH2LW4JREZGCH4HKHDLNQNARMW4FVQDQDJ2LF44IZ4SFEJJYXCS3JQBWQG2WA3TVO4ZPN6XHF6FTJFLPHUOY\",\"ma\":\"\",\"im\":\"\",\"os\":\"iOS\",\"osv\":\"\",\"ip\":\"116.230.26.211\",\"apid\":\"jdapp\",\"ia\":\"\",\"uu\":\"\",\"cv\":\"11.6.0\",\"nt\":\"UNKNOW\",\"at\":\"1\"}; _gia_s_local_fingerprint=213063016e9f295ac711f8bb16af8789; shshshfpb=dpcmJaLKMoLIBzgK0FQKMCA; shshshfp=0c9c262aef5454b1bbfce1c1a288777b; shshshfpa=229f934d-80f2-41a0-9ffe-571ffb8ec8dc-1650968164; shshshfpx=229f934d-80f2-41a0-9ffe-571ffb8ec8dc-1650968164; ipLoc-djd=2_2830_61099_0; __wga=1675790663858.1675790663858.1672504905691.1667881396968.1.11; cid=8; retina=1; sc_width=414; abtest=20230126133804034_19; jcap_dvzw_fp=TzWUkx7D-TUP8V6Avo2RSiDDk93W3UqYsu_oCv1UFldT3xur6t1Citam8oJhmWSLWVrMsg==; webp=1; __jdu=16620014423941537449433; b_avif=1; b_dh=896; b_dpr=2; b_dw=414; b_webp=1; visitkey=26820509058422518;";
        String ptKey = json.getString("ptKey");
        String ptPin = json.getString("ptPin");
        cookie = cookie + " " +  ptKey + " " +  ptPin;
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url("https://api.m.jd.com/client.action?functionId=signBeanAct&body=%7B%22fp%22%3A%22-1%22%2C%22shshshfp%22%3A%22-1%22%2C%22shshshfpa%22%3A%22-1%22%2C%22referUrl%22%3A%22-1%22%2C%22userAgent%22%3A%22-1%22%2C%22jda%22%3A%22-1%22%2C%22rnVersion%22%3A%223.9%22%7D&appid=ld&client=apple&clientVersion=11.6.2&networkType=wifi&osVersion=16.3.1&uuid=9ba2573f45faad4fe2a70b9f5e40459eade468af&openudid=9ba2573f45faad4fe2a70b9f5e40459eade468af&d_model=iPhone11,8&jsonp=jsonp_1677809997918_55111")
                .method("GET", null)
                .addHeader("Host", "api.m.jd.com")
                .addHeader("Cookie", cookie)
                .addHeader("accept", "*/*")
                .addHeader("referer", "https://h5.m.jd.com/")
                .addHeader("request-from", "native")
                .addHeader("user-agent", "jdapp;iPhone;11.6.2;;;M/5.0;appBuild/168548;jdSupportDarkMode/0;ef/1;ep/%7B%22ciphertype%22%3A5%2C%22cipher%22%3A%7B%22ud%22%3A%22EWTrCtU3C2Y0DWZrYWG0ZwUyYJcmYtvwDWU0CNG1EWVrZQU0DtrrZq%3D%3D%22%2C%22sv%22%3A%22CJYkCy4n%22%2C%22iad%22%3A%22%22%7D%2C%22ts%22%3A1677809583%2C%22hdid%22%3A%22JM9F1ywUPwflvMIpYPok0tt5k9kW4ArJEU3lfLhxBqw%3D%22%2C%22version%22%3A%221.0.3%22%2C%22appname%22%3A%22com.360buy.jdmobile%22%2C%22ridx%22%3A-1%7D;Mozilla/5.0 (iPhone; CPU iPhone OS 16_3_1 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Mobile/15E148;supportJDSHWK/1;")
                .addHeader("accept-language", "en-AU,en;q=0.9")
                .build();
        Response response = client.newCall(request).execute();
        String respBody = new String(response.body().bytes(), "utf-8");
        logger.info("京东签到完成... respBody={}",respBody);
        XxlJobHelper.log("京东签到完成... respBody={}", respBody);
        if (respBody.contains("用户未登录")) {
            this.sendBarkMsg("XXL任务执行失败", "京东签到-用户未登录");
            throw new RuntimeException("用户未登录");
        }

        //发送邮件
        Boolean sendFlag = json.getBoolean("sendFlag");
        if (!sendFlag) {
            return;
        }
        JSONObject resp = JSONObject.parse(respBody);
        String beanCount = "";
        if (resp.getJSONObject("data").getJSONObject("dailyAward") != null) {
            beanCount = resp.getJSONObject("data").getJSONObject("dailyAward").getJSONObject("beanAward").getString("beanCount");
        } else {
            beanCount = resp.getJSONObject("data").getJSONObject("continuityAward").getJSONObject("beanAward").getString("beanCount");
        }
        String continuousDays = resp.getJSONObject("data").getString("continuousDays");
        String content = json.getString("content");
        content = String.format(content, DateUtil.formatDateTime(new Date()), beanCount, continuousDays, respBody);
        SendMailReq mailReq = new SendMailReq();
        mailReq.setPersonal(json.getString("personal"));
        mailReq.setSendTo(json.getString("sendTo").split(";"));
        mailReq.setSubject(json.getString("subject"));
        mailReq.setContent(content);
        eMailSender.sendMail(mailReq);
    }

    /**
     * 京东天天领福利
     */
    @XxlJob("jingBeanReceive")
    public void jingBeanReceive() throws Exception {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(mediaType, "avifSupport=1&body=%7B%22firstType%22%3A0%2C%22encryptAssignmentId%22%3A%226bzcu8ZNPHFhuWZC55MhLgJCPiW%22%7D&build=168392&client=apple&clientVersion=11.3.6&d_brand=apple&d_model=iPhone11%2C8&ef=1&eid=eidIede8812231s7WhrQbUFyQH61Rz7yvwQDB1oa0daiSKAKGQyXz7Klozl7qMp/DuICVDlkWAZy0X7Lu3Q8zBc21cBwIjDjf9OBVrl5UPHCf8DqntBo&ep=%7B%22ciphertype%22%3A5%2C%22cipher%22%3A%7B%22screen%22%3A%22ENS4AtO3EJS%3D%22%2C%22wifiBssid%22%3A%22CwVvZQSyDzq4CNU5CtvsYWSzZNc3YtqnYzq2CzdwDJu%3D%22%2C%22osVersion%22%3A%22CJYkCI4n%22%2C%22area%22%3A%22D180DJrpDNY0XzU1Czun%22%2C%22openudid%22%3A%22EWTrCtU3C2Y0DWZrYWG0ZwUyYJcmYtvwDWU0CNG1EWVrZQU0DtrrZq%3D%3D%22%2C%22uuid%22%3A%22aQf1ZRdxb2r4ovZ1EJZhcxYlVNZSZz09%22%7D%2C%22ts%22%3A1671257977%2C%22hdid%22%3A%22JM9F1ywUPwflvMIpYPok0tt5k9kW4ArJEU3lfLhxBqw%3D%22%2C%22version%22%3A%221.0.3%22%2C%22appname%22%3A%22com.360buy.jdmobile%22%2C%22ridx%22%3A-1%7D&ext=%7B%22prstate%22%3A%220%22%2C%22pvcStu%22%3A%221%22%7D&isBackground=N&joycious=84&lang=zh_CN&lmt=0&networkType=wifi&networklibtype=JDNetworkBaseAF&partner=apple&rfs=0000&scope=01&sign=74bb3bc9d7e88e3b6ef2ff32ffe50ef6&st=1671257983927&sv=121&uemps=0-0&uts=0f31TVRjBStuVBbtpZQbyxF0CkdSYclXY4DSq3h24GGwignoeOkjGxzKme68VSTx7AYeJYyZC8DyKCVPCThxijy9iUC0AXNm%2BbwamN9n7HGZ%2BnK21k5l/Yirh47GcSSDWUCZmd2ej2LJWQ9frv1ijuc4TzN86ycI9At5PQa61PYoEAWRsFjN%2By7EaCqrxmo0dKkYtYD6kl5OM8rz94a5bg%3D%3D");
        Request request = new Request.Builder()
                .url("https://api.m.jd.com/client.action?functionId=jingBeanReceive")
                .method("POST", body)
                .addHeader("Host", "api.m.jd.com")
                .addHeader("Cookie", "wskey=AARjiNO7AEANshnJvTmeDJyjuP6-Zt7dCWppo27zLuJkrurPjDfpwSLlYJ0UOHfeU48UB09d4rNwsF1FWkya6WrSAv9-maPI;whwswswws=JD0111d47dH9wvVBTunu167125791635102m1zaBtrUUz2PLwwhcesJuHQ2ZS3bTS30F7uY1sz2iqUJZPTZhll63jDsrT6T7Csvi8zb6oudCNKjgw7vcAh0kVFA7IrzrjV1CN24AvQhcdM0pzjuie~yax5YzwdTZx4CzSRt2XygmYJZZCvSgSMY8vmxV2TRUvpsSnXGMoBa+LDCxPZksMNF2HWRK3f1Yej20STzjSNY8Q==;unionwsws={\"jmafinger\":\"JD0111d47dH9wvVBTunu167125791635102m1zaBtrUUz2PLwwhcesJuHQ2ZS3bTS30F7uY1sz2iqUJZPTZhll63jDsrT6T7Csvi8zb6oudCNKjgw7vcAh0kVFA7IrzrjV1CN24AvQhcdM0pzjuie~yax5YzwdTZx4CzSRt2XygmYJZZCvSgSMY8vmxV2TRUvpsSnXGMoBa+LDCxPZksMNF2HWRK3f1Yej20STzjSNY8Q==\",\"devicefinger\":\"eidIede8812231s7WhrQbUFyQH61Rz7yvwQDB1oa0daiSKAKGQyXz7Klozl7qMp\\/DuICVDlkWAZy0X7Lu3Q8zBc21cBwIjDjf9OBVrl5UPHCf8DqntBo\"};pin_hash=763186459; TARGET_UNIT=bjcenter")
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .addHeader("j-e-c", "%7B%22ciphertype%22:5,%22cipher%22:%7B%22pin%22:%22TUU1TUO1TJq3TUU4TUTQTUS5TUU4TJrMTJrQTUU1TUPPTJq3TUU4TUS2TJq1%22%7D,%22ts%22:1671257983,%22hdid%22:%22JM9F1ywUPwflvMIpYPok0tt5k9kW4ArJEU3lfLhxBqw=%22,%22version%22:%221.0.3%22,%22appname%22:%22com.360buy.jdmobile%22,%22ridx%22:-1%7D")
                .addHeader("accept", "*/*")
                .addHeader("j-e-h", "%7B%22ciphertype%22:5,%22cipher%22:%7B%22User-Agent%22:%22IuG0aVLeb25vBzO2ENC5CsUyCMrfUQrlbwU7TJSmaU9JTJSmCJYkCI4nEyUyCPDtYWnvBzSkCNKf%22%7D,%22ts%22:1671257913,%22hdid%22:%22JM9F1ywUPwflvMIpYPok0tt5k9kW4ArJEU3lfLhxBqw=%22,%22version%22:%221.0.3%22,%22appname%22:%22com.360buy.jdmobile%22,%22ridx%22:-1%7D")
                .addHeader("accept-language", "en-CN;q=1, zh-Hans-CN;q=0.9")
                .addHeader("user-agent", "JD4iPhone/168392%20(iPhone;%20iOS;%20Scale/2.00)")
                .addHeader("referer", "")
                .build();
        Response response = client.newCall(request).execute();
        String respBody = new String(response.body().bytes(), "utf-8");
        logger.info("京东天天领福利完成... respBody={}",respBody);
        XxlJobHelper.log("京东天天领福利完成... respBody={}", respBody);
    }


    /**
     * 京东PLUS生活特权-天天领京豆
     * @throws Exception
     */
    @XxlJob("doInteractiveAssignment")
    public void doInteractiveAssignment() throws Exception {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(mediaType, "appid=babelh5&body=%7B%22sourceCode%22%3A%22acetttsign%22%2C%22encryptProjectId%22%3A%2245C45PQWWd2373uUWqfQshj6nAoF%22%2C%22encryptAssignmentId%22%3A%222Xnn2dHwmj1gBAvKxyreT5vq92p3%22%2C%22completionFlag%22%3Atrue%2C%22itemId%22%3A%221%22%2C%22extParam%22%3A%7B%22forceBot%22%3A%221%22%2C%22businessData%22%3A%7B%22random%22%3A%22duUUlOHI%22%7D%2C%22signStr%22%3A%221670662409562~1VkRADzPPxxMDFJWFlWaDk5MQ%3D%3D.eG5uZl5%2FamtuW3lsbCg5KxNsGhAfIRodFnh0b3pZZWknZBZ4Jj0mCyQSOBojBDcVHyozPxJmLhgTFBUpNyY%3D.e3cbafec~1%2C1~EC2EC1F96F027F6E950ED7AC2191BE736648EC80~1uafszn~C~TRJCWRsMbRJSAhQDBhx6fxUEBnsPGFgaQhIaFl0AGwUFGHUHGgIGf3QaVxxCFhUUUgYbAQ0afGgaBgl6BhxXGE0UGhJSAhQDBBx8DBUEBnxxGFgaQhJrGBtCWF0UDmIUUgcbAQsafAgaBgl6fBxXGE0UGhJSARQOYxx3BhUEZmh7GFgaQhIaFl0BGwhjGHgEGgJmbW0aVxxCFmQaFFdEWhsMBxwUR0oUDBIHAg4GAAYEBgEEAQcABwkODhIaFk5TUhIMFk1CQkRCUkxQFBwUQ1xXFAoUUl9CQkRCQVgUGhJGUFcUDGsFBhUHAgIaBQsaDxwHGAxrGhJcXhsMBxwUV0oUDBJVV1gEBgBVAgkAB1JXDAsAAVYEBApSVFNVAwsCBQQBBxsaFF5GFgMUX2BeW1dTFBwUQBsMBwYBBA8ABAQGDQwABBwUXlIUDBIPVlgEAwcFUg0DUFFXUA1QUwJXAwlUD1YDUQ0GAgUPUVhSUwQADFhQFBwUUklUFAoUUElVW3pXeHJ7WXx%2FdkNRfQBwZXJ7dXEUGBtYQBIMFnhGRlxTFHpZW0BDQFxEGhB%2FWloYFBwUWlhAFAoUBQ8OBQkEFhUURVNEFgNtDgkAGA0HAm0aFktZFAptFlBmXl9YUQgHGggUGBtfeWMUGBsHAB4HGggUGhIHBRcFGAYUGBsHAAgFDQsUGhIPVlgEAwcFUg0DUFFXUA1QUwJXAwlUD1YDUQ0GAgUPUVhSUwQADFhQFBwUVRtrGhJfW1gUDBJQUl9QUFZCQBsaFFFcFgMUQxIaFlpfFAoUQwoYAx4EFhUUVVZpQhsMFAkPFhUUVFQUDhtEV15SW1QLUkVjYVV5fnkUGBtbXBIMbwgAGgEGAhUCaxwUVlVZURIMFggAAQAAAgsEDgIDAAhIB0UFf2gGDwNGUHN7cnpfbm5sBGFidUB4dw0LGmwHeGJkWl1jYAFSc11wcGRgU14GY2F8Ym0FWmZ1dQB%2BZkhkfnFwRkJiWGhvZ3QPYH5ODmh5YloOfnFzWHdcDlVnU3BveFpnd1pMWVN3ZkpvfXQODHdiVUh3XmwBcXZZYHpBAmhjcHwGf0pVZ39cY2lxZ2xZdWRnU2plXVl4YFJPZkcGcn11QVpgcA1he3MDYnx%2Bdwd3dUFzVWRzTHF0QWN6cEFTfANOUn5OUQ0YAgtQU1YPBAlIQBwHSkdIdE5idn9jblhzfnxgZ3VxZWxkcUhSYWt3RWVyTHxTZ2FOcn9gUWZzZkpgZEhzfn9nY3N3Q3BgcWJjcm8EXWFzZVF0Y2FVYm1eYHJwZQlkc2IOYmFdY2Z1ZmxwcQFvdntab2J%2BX3x3cEcHdX9jAg1IBAxXQV8AVxsaFF1FUxsMFBJL~1cb2acn%22%2C%22sceneid%22%3A%22babel_3joSPpr7RgdHMbcuqoRQ8HbcPo9U%22%7D%2C%22activity_id%22%3A%223joSPpr7RgdHMbcuqoRQ8HbcPo9U%22%2C%22template_id%22%3A%2200019605%22%2C%22floor_id%22%3A%2288115922%22%2C%22enc%22%3A%22A4737E261D02E91C30C566C1C671734D124B75F8759F591EFAFB127342C1070878750788ACC19A782AB26C16501A48A091BAEB8234326C651D7748F3896838E7%22%7D&sign=11&t=1670662409818");
        Request request = new Request.Builder()
                .url("https://api.m.jd.com/client.action?functionId=doInteractiveAssignment")
                .method("POST", body)
                .addHeader("Host", "api.m.jd.com")
                .addHeader("Cookie", "__jd_ref_cls=Babel_dev_other_sign; joyya=1670662282.1670662409.52.1xfpoaz; mba_muid=16620014423941537449433.4687.1670662409782; mba_sid=4687.23; joyytokem=babel_3joSPpr7RgdHMbcuqoRQ8HbcPo9UMDFJWFlWaDk5MQ==.eG5uZl5/amtuW3lsbCg5KxNsGhAfIRodFnh0b3pZZWknZBZ4Jj0mCyQSOBojBDcVHyozPxJmLhgTFBUpNyY=.e3cbafec; shshshfp=73a2b3b3cccc5eda3098e753103a1204; shshshfpa=229f934d-80f2-41a0-9ffe-571ffb8ec8dc-1650968164; shshshfpb=dpcmJaLKMoLIBzgK0FQKMCA; shshshsID=b5755d7ab57f5853f8ca2e563eea2a7c_1_1670662283196; unpl=JF8EAK9nNSttX0NUVksAH0FDSVsAW10ISh8GZzVRVFxaHgBRSwEbEEV7XlVdXhRLFB9ubhRUWFNKVQ4YAisiEEpcVlxYCkwfA19XNVddaEpkBRwEGhcWQ15XX1UPTBQDamEAXV9ce1U1GwofIhNPXFVXWw5KEwdqVzVRXVlKUAcdBRIRIEptVl9cDU4RB2hhAGQcNk1WAh4KHV8QTFtVW1sASBQCZ2ACV11dTVEMGQYrEyBI%7CJF8EANxnNSttWh4BUE4AHUJEGQlSWw8MSB9QZzIEVV4NTwBRElZPFxJ7XlVdXhRLFx9sYhRXX1NOUA4aBysSFHtdVV9fDkIRC2phNWRbNktUDBwLGRUTJV5VXzNVE1BXX2QFZFxoQwdVGAYdEEdOWQMODV1OQFdsNwNVDlAcUFEeAx4WGB8NAQpYD0JHVF9mNV1caEtVBBkGHRAXSF1cVlsOeycCX2Y1VW0ZJVQEGgsYGhBOEFwNDQtPEQE4YgEDDQgeUVJPAUsUERhVA1oJDUoSB2czVQEJXUxdVUwyGiIQ; __jda=47827316.16620014423941537449433.1662001442.1670650617.1670662112.218; __jdb=47827316.6.16620014423941537449433|218.1670662112; __jdc=47827316; __jdv=47827316%7Ckong%7Ct_1000170136%7Ctuiguang%7Cnotset%7C1670651947253; pre_seq=17; pre_session=9ba2573f45faad4fe2a70b9f5e40459eade468af|11087; pt_key=app_openAAJjk0_rADAIiBvSIq83cr1XBDWq_85H-ns4qXdsjG_WwE0ill8JsUqRbbE2XQwLchTL8SMUM9A; pt_pin=%E5%A5%87%E8%BF%B9%E8%8B%8F%E5%AE%87%E8%B6%85; pwdt_id=%E5%A5%87%E8%BF%B9%E8%8B%8F%E5%AE%87%E8%B6%85; shshshfpv=JD0111d47d4lhOIuwe1j1670662105836024Ks-gqAoPi-mf0Tqdb04zIm4Qy4hrmaxk7pvJ0qoTuyY7ObWGVGaDHUNY_PNPYjFQO9o99h7AC4v55O1yZwPUQ3PFXlogDJk_NseWpe3L2E1nl18on~yax5YzwdTZx4CzSRt2XygmYJZZCvSgSMY8vmxV2TRUvpsSnXGMoBa+LDCxPZksMNF2HWRK3f1Yej20STzjSNY8Q==; sid=de005c64ef7be9f0ecaaa681a229488w; p-referer-id=http%3A%2F%2Fplus.m.jd.com%2Fliferight%2Findex; 3AB9D23F7A4B3C9B=4AXTZQIJGK6URNFYWA6X35ACPLIHDVMU3IJS32AHGUOOPTC2FUVTHN4DECABFYRIEBOAVA4C56MEGYUPZSMWA5JBDE; _gia_s_e_joint={\"eid\":\"4AXTZQIJGK6URNFYWA6X35ACPLIHDVMU3IJS32AHGUOOPTC2FUVTHN4DECABFYRIEBOAVA4C56MEGYUPZSMWA5JBDE\",\"ma\":\"\",\"im\":\"\",\"os\":\"iOS\",\"osv\":\"\",\"ip\":\"116.227.232.221\",\"apid\":\"jdapp\",\"ia\":\"\",\"uu\":\"\",\"cv\":\"11.3.6\",\"nt\":\"UNKNOW\",\"at\":\"1\"}; _gia_s_local_fingerprint=fcb917aa00ee9ead3b70a4971723fa06; wxa_level=1; BATQW722QTLYVCRD={\"tk\":\"jdd012HLMIYLWIKPNHOYYV33QPMELAYHW53IU2PLA2RHPXTMGK7JFFLHNPDY43NSVH57L6T6U63SUFSS4DTXXU2TYTAEDBFEZSSENLOISZXA01234567\",\"t\":1670662112639}; ipLoc-djd=2_2830_51808_0; PPRD_P=LOGID.1670633350967.1335559569-UUID.16620014423941537449433; __wga=1670639405436.1670639347184.1669052096525.1667881396968.4.7; deviceName=JDAPP; deviceOS=ios; deviceOSVersion=16.1.1; deviceVersion=0; equipmentId=4AXTZQIJGK6URNFYWA6X35ACPLIHDVMU3IJS32AHGUOOPTC2FUVTHN4DECABFYRIEBOAVA4C56MEGYUPZSMWA5JBDE; fingerprint=c59549a325af9e22036855e60b656108; jxsid_s_t=1670639405559; jxsid_s_u=https%3A//coupon.m.jd.com/coupons/show.action; cid=8; retina=1; sc_width=414; jxsid=16706393467304524448; jcap_dvzw_fp=lOH9EVicY1wScMn0GtrdueYNFmQtBB3HTJCC4ChboxSP9tkwtSGufFV_4_jf5gbOKyLCTg==; webp=1; __jdu=16620014423941537449433; b_avif=1; b_dh=896; b_dpr=2; b_dw=414; b_webp=1; visitkey=26820509058422518; TARGET_UNIT=bjcenter")
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .addHeader("accept", "*/*")
                .addHeader("accept-language", "en-US,en;q=0.9")
                .addHeader("origin", "https://pro.m.jd.com")
                .addHeader("user-agent", "jdapp;iPhone;11.3.6;;;M/5.0;appBuild/168392;jdSupportDarkMode/0;ef/1;ep/%7B%22ciphertype%22%3A5%2C%22cipher%22%3A%7B%22ud%22%3A%22EWTrCtU3C2Y0DWZrYWG0ZwUyYJcmYtvwDWU0CNG1EWVrZQU0DtrrZq%3D%3D%22%2C%22sv%22%3A%22CJYkCI4n%22%2C%22iad%22%3A%22%22%7D%2C%22ts%22%3A1670662282%2C%22hdid%22%3A%22JM9F1ywUPwflvMIpYPok0tt5k9kW4ArJEU3lfLhxBqw%3D%22%2C%22version%22%3A%221.0.3%22%2C%22appname%22%3A%22com.360buy.jdmobile%22%2C%22ridx%22%3A-1%7D;Mozilla/5.0 (iPhone; CPU iPhone OS 16_1_1 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Mobile/15E148;supportJDSHWK/1;")
                .addHeader("referer", "https://pro.m.jd.com/mall/active/3joSPpr7RgdHMbcuqoRQ8HbcPo9U/index.html?babelChannel=ttt1&tttparams=eOcg05oiOeyJncHNfYXJlYSI6IjJfMjgzMF81MTgwOF8wIiwicHJzdGF0ZSI6IjAiLCJ1bl9hcmVhIjoiN180NThfNDY0XzU1MzkxIiwiZExhdCI6IiIsImdMYXQiOiIzNS4yMDgxMDciLCJnTG5nIjoiMTE0LjczNTA4NSIsImxuZyI6IjEyMS41NzkwMjYiLCJsYXQiOiIzMS4yOTk0NDYiLCJkTG5nIjoiIiwibW9kZWwiOiJpUGhvbmUxMSw4In90%3D&channel=brand&sid=de005c64ef7be9f0ecaaa681a229488w&un_area=7_458_464_55391")
                .addHeader("request-from", "native")
                .build();
        Response response = client.newCall(request).execute();
        String respBody = new String(response.body().bytes(), "utf-8");
        logger.info("京东PLUS生活特权-天天领京豆签到完成... respBody={}",respBody);
        XxlJobHelper.log("京东PLUS生活特权-天天领京豆签到完成... respBody={}", respBody);

        //发送邮件
        String param = XxlJobHelper.getJobParam();
        JSONObject json = JSONObject.parse(param);
        Boolean sendFlag = json.getBoolean("sendFlag");
        if (!sendFlag) {
            return;
        }
        JSONObject resp = JSONObject.parse(respBody);
//        String beanCount = resp.getJSONObject("data").getJSONObject("dailyAward").getJSONObject("beanAward").getString("beanCount");
//        String continuousDays = resp.getJSONObject("data").getString("continuousDays");
        String content = json.getString("content");
        content = String.format(content, DateUtil.formatDateTime(new Date()), respBody);
        SendMailReq mailReq = new SendMailReq();
        mailReq.setPersonal(json.getString("personal"));
        mailReq.setSendTo(json.getString("sendTo").split(";"));
        mailReq.setSubject(json.getString("subject"));
        mailReq.setContent(content);
        eMailSender.sendMail(mailReq);
    }


    /**
     * 华住会签到
     */
    @XxlJob("huaZhuSignIn")
    public void huaZhuSignIn() throws Exception {
        String param = XxlJobHelper.getJobParam();
        JSONObject json = JSONObject.parse(param);

        DelayUtil.delayExecRandom(json.getInteger("delayMinute") * 60);

        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String dayStr = String.format("%02d", day);

        String cookie = "SK=9b64c266c3f44fcd9fadf118fd85964d207578661; _efmdata=yB0ZjsNF40t1yyn6wkbR%2F1VrKmjL2ctAWSQxj5G2YEm4TguvNOzcT5fTW1laac3DnKD5AiFK6bhgsXlT5sbgyw1gypEJnte0aHivJdQov8Q%3D; _exid=zwQsmXyw6RdncesTBARAxyHRdxKKdtNWK3WgPPb6SVxLCzzZgwF%2FibNkZ82g6Mql%2BEw%2BLJHN4VuZjQz442B3GQ%3D%3D; _hudPVID=16; _hudSID_TS=1680969615472; _hudSource=; ec=Z46nOMcK-1680878639239-e3fb60c15e41d-902836675; gr_user_id=77b4b48f-4f7a-4eb9-9287-ca9a1018108f; CSRF-NWACT=b4bd4a23-7d56-49ea-b8f9-7659d0a9494b; hud_refer=campaign.huazhu.com/pointsShop/index.html|5499; gr_session_id_8f6e3e7f89d647cab9784afa81ea87bd_84684952-fa69-4650-a964-e57ca61f07bb=true; gr_session_id_8f6e3e7f89d647cab9784afa81ea87bd=84684952-fa69-4650-a964-e57ca61f07bb; _hudSID=1680912258071_3; install_id=7010098612390649101; ttreq=1$ba15fc77bab956f6c6d21a8f361d740d597c6414; _hudVID=87e8b11e-1b03-1c28-2358-7ba78bbc0fc0;";
        cookie = cookie + " " + json.getString("userToken");

        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(mediaType, "state=1&day=" + dayStr);
        Request request = new Request.Builder()
                .url("https://hweb-mbf.huazhu.com/api/signIn")
                .method("POST", body)
                .addHeader("Host", "hweb-mbf.huazhu.com")
                .addHeader("Accept", "application/json, text/plain, */*")
                .addHeader("Accept-Language", "en-AU,en;q=0.9")
                .addHeader("Origin", "https://campaign.huazhu.com")
                .addHeader("User-Agent", "HUAZHU/ios/iPhone11,8/16.3.1/9.7.0/HUAZHU/ios/iPhone11,8/16.3.1/9.7.0/HUAZHU/ios/iPhone11,8/16.3.1/9.7.0/Mozilla/5.0 (iPhone; CPU iPhone OS 16_3_1 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Mobile/15E148")
                .addHeader("Client-Platform", "APP-IOS")
                .addHeader("Referer", "https://campaign.huazhu.com/")
                .addHeader("User-Token", "null")
                .addHeader("Cookie", cookie)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build();
        Response response = client.newCall(request).execute();

        String respBody = new String(response.body().bytes(), "utf-8");
        logger.info("华住签到完成... respBody={}",respBody);
        XxlJobHelper.log("华住签到完成... respBody={}", respBody);

        //发送邮件
        Boolean sendFlag = json.getBoolean("sendFlag");
        if (!sendFlag) {
            return;
        }
        JSONObject resp = JSONObject.parse(respBody);
        Long point = resp.getJSONObject("content").getLong("point");
        String pointStr = point == null ? "null" : String.valueOf(point);
        String content = json.getString("content");
        content = String.format(content, DateUtil.formatDateTime(new Date()), pointStr, respBody);
        SendMailReq mailReq = new SendMailReq();
        mailReq.setPersonal(json.getString("personal"));
        mailReq.setSendTo(json.getString("sendTo").split(";"));
        mailReq.setSubject(json.getString("subject"));
        mailReq.setContent(content);
        eMailSender.sendMail(mailReq);
    }


    /**
     * 叮咚买菜签到
     */
    @XxlJob("dingDongSignIn")
    public void dingDongSignIn() throws Exception {
        String param = XxlJobHelper.getJobParam();
        JSONObject json = JSONObject.parse(param);
        String cookie = json.getString("cookie");

        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(mediaType, "api_version=9.7.3&app_version=1.0.0&app_client_id=3&native_version=10.0.0&city_number=0101&device_token=BjvPoyxbVu5F8CixPRzwt1oEiqgYlvvyVUp9aXwWe+CIX8C14UmNFK+jHGLHOu0EgfdlnR6nfMfmd7UFRbcHhjA==&device_id=1067476d3c864b1a9266977a75c86f5c892503d6&latitude=31.299389&longitude=121.579529");
        Request request = new Request.Builder()
                .url("https://sunquan.api.ddxq.mobi/api/v2/user/signin/")
                .method("POST", body)
                .addHeader("cookie", cookie)
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .build();
        Response response = client.newCall(request).execute();

        String respBody = new String(response.body().bytes(), "utf-8");
        logger.info("叮咚买菜签到完成... respBody={}",respBody);
        XxlJobHelper.log("叮咚买菜签到完成... respBody={}", respBody);

        //发送邮件
        Boolean sendFlag = json.getBoolean("sendFlag");
        if (!sendFlag) {
            return;
        }
        JSONObject resp = JSONObject.parse(respBody);
        Long point = 0L;
        Long sign_series = 0L;
        Long new_sign_series = 0L;
//        Long ticket_money = 0L;
        if (resp.getBoolean("success")) {
            point = resp.getJSONObject("data").getLong("point");
            sign_series = resp.getJSONObject("data").getLong("sign_series");
            new_sign_series = resp.getJSONObject("data").getLong("new_sign_series");
//            ticket_money = resp.getJSONObject("data").getLong("ticket_money");
        }
        String pointStr = point == null ? "null" : String.valueOf(point);
        String sign_seriesStr = String.valueOf(sign_series);
        String new_sign_seriesStr = String.valueOf(new_sign_series);
        String content = json.getString("content");
        content = String.format(content, DateUtil.formatDateTime(new Date()), pointStr, sign_seriesStr, new_sign_seriesStr,  respBody);
        SendMailReq mailReq = new SendMailReq();
        mailReq.setPersonal(json.getString("personal"));
        mailReq.setSendTo(json.getString("sendTo").split(";"));
        mailReq.setSubject(json.getString("subject"));
        mailReq.setContent(content);
        eMailSender.sendMail(mailReq);
    }



    private void sendBarkMsg(String title, String text) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        Request request = new Request.Builder()
                .url("https://api.day.app/NjqoqfctzkPkYqpLFERdrT/" + title +"/" + text)
                .method("GET", null)
                .build();
        Response response = client.newCall(request).execute();
    }

}
