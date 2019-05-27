package com.paprika.aop.aspect;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Created by adam on 2019/5/27 at 16:23.
 * PS: You may say that I'm a dreamer.But I'm not the only one.
 */
public class ThrowableUtil {

    /**
     * 获取堆栈信息
     * @param throwable
     * @return
     */
    public static String getStackTrace(Throwable throwable){
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        try {
            throwable.printStackTrace(pw);
            return sw.toString();
        } finally {
            pw.close();
        }
    }
}
