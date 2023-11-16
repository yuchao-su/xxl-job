package com.xxl.job.executor.util;

import java.util.Calendar;
import java.util.Date;

public class DateUtil {


    /**
     * 判断是否是本月最后一天
     */
    public static boolean isLastDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);

        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.DATE, -1);

        int lastDayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        return currentDay == lastDayOfMonth;
    }

}
