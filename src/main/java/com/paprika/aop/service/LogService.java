package com.paprika.aop.service;

import com.paprika.aop.domain.Log;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.scheduling.annotation.Async;

/**
 * @author adam
 * @date 2019/5/27
 * PS: You may say that I'm a dreamer.But I'm not the only one.
 */
public interface LogService {

    /**
     * 保存日志
     *
     * @param joinPoint Spring aop中的方法
     * @param log 需要记录的日志
     */
    @Async
    public void save(ProceedingJoinPoint joinPoint, Log log);
}
