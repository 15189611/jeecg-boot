package org.jeecg.common.util;

import org.apache.commons.lang3.time.FastDateFormat;

import java.util.Date;

public class SerialNo {

    /**
     * 主要功能:生成流水号 yyyyMMddHHmmssSSS + 3位随机数
     * 注意事项:无
     *
     * @return 流水号
     */
    public static String getNo() {
        // 精确到毫秒
        String suffix = FastDateFormat.getInstance("yyyyMMddHHmmssSSS").format(new Date());
        suffix = suffix + Math.round((Math.random() * 100000));
        return suffix;
    }
}
