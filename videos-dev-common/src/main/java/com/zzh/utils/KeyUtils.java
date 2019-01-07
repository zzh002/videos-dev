package com.zzh.utils;

import java.util.Random;

/**
 * @author ZZH
 * @date 2019/1/7 15:28
 **/
public class KeyUtils {
    /**
     * 生成唯一的主键
     * 格式: 时间+随机数
     * @return
     */
    public static synchronized String genUniqueKey() {
        Random random = new Random();
        Integer number = random.nextInt(900000) + 100000;

        return System.currentTimeMillis() + String.valueOf(number);
    }
}
