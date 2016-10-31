package com.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by root on 16-8-17.
 */
public class DateUtil {

    /**
     * 根据指定格式获取String类型的Date
     *
     * @param date
     * @param format
     * @return
     */
    public static String dateFormat(Date date, String format) {
        return new SimpleDateFormat(format).format(date);
    }
}
