package com.xxl.job.executor.util;

import com.xxl.job.core.context.XxlJobHelper;
import lombok.extern.slf4j.Slf4j;

import java.util.Random;
@Slf4j
public class DelayUtil {

    /**
     * 随机延迟执行
     * @param second 秒
     * @throws InterruptedException
     */
    public static void delayExecRandom(Integer second) throws InterruptedException {
        //随机延迟执行x秒
        Random random = new Random();
        second = second == null ? 0 : second;
        if (second <= 0) {
            return;
        }
        int delaySecond = random.nextInt(second) + 1;
        log.info("延迟{}秒执行", delaySecond);
        XxlJobHelper.log("延迟{}秒执行", delaySecond);
        Thread.sleep(delaySecond * 1000);
    }
}
