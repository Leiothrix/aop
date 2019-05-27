package com.paprika.aop.service.impl;

import cn.hutool.json.JSONObject;
import com.paprika.aop.domain.Log;
import com.paprika.aop.repository.LogRepository;
import com.paprika.aop.service.LogService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * @author adam
 * @date 2019/5/27
 * PS: You may say that I'm a dreamer.But I'm not the only one.
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class LogServiceImpl implements LogService{
    public static final String LOGIN_PATH = "login";

    private LogRepository logRepository;

    @Autowired
    public LogServiceImpl(LogRepository logRepository){
        this.logRepository = logRepository;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(ProceedingJoinPoint joinPoint, Log log){

        // 获取request
        HttpServletRequest request = RequestHolder.getHttpServletRequest();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        com.paprika.aop.log.Log aopLog = method.getAnnotation(com.paprika.aop.log.Log.class);

        // 描述
        if (log != null) {
            log.setDescription(aopLog.value());
        }

        // 方法路径
        String methodName = joinPoint.getTarget().getClass().getName()+"."+signature.getName()+"()";

        String params = "{";
        //参数值
        Object[] argValues = joinPoint.getArgs();
        //参数名称
        String[] args = ((MethodSignature)joinPoint.getSignature()).getParameterNames();
        // 用户名
        String userName = "";

        if(argValues != null){
            for (int i = 0; i < argValues.length; i++) {
                params += " " + args[i] + ": " + argValues[i];
            }
        }

        // 获取IP地址
        log.setRequestIp(StringUtils.getIP(request));

        if(!LOGIN_PATH.equals(signature.getName())){
            userName = SecurityUtils.getUsername();
        } else {
            try {
                JSONObject jsonObject = new JSONObject(argValues[0]);
                userName = jsonObject.get("username").toString();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        log.setMethod(methodName);
        log.setUserName(userName);
        log.setRequestParam(params + " }");
        logRepository.save(log);
    }
}
