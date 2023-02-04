package com.xuanchengwei.filemanagementsystem.constants;

import java.time.ZoneId;

/**
 * @author 禤成伟
 * @date 2022-10-03 - 3:50
 */
public final class DateConstants {

    /**
     * 参考 <a href="https://www.sqlite.org/lang_datefunc.html">Date And Time Functions</a> 选择了一个我喜欢的
     */
    public static final String LOCAL_DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss.SSS";

    public static final ZoneId DEFAULT_ZONE_ID = ZoneId.of("Asia/Shanghai");


}
