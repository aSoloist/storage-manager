package com.ep.annotation;

import com.ep.commons.domain.model.User;
import com.ep.commons.domain.service.LogService;
import com.ep.commons.domain.service.UserService;
import com.ep.commons.tool.handle.AuthToken;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

@Aspect
@Component
public class LogAspect {
    @Autowired
    LogService logService;

    @Autowired
    UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);

    @Pointcut("@annotation(com.ep.annotation.ServiceLog)")
    public void aspect(){
    }

    @After("aspect()")
    public void after(JoinPoint joinPoint){
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();

        //用户
        User user = null;
        Object o = request.getAttribute("authToken");
        if (o != null){
            AuthToken authToken = (AuthToken)o;
            String uId = authToken.getUid();
            if (uId != null) {
                user = userService.get(uId);
            }
        }

        try {
            String description = getServiceDescription(joinPoint);
            System.out.println("==================================后置通知=============================================");
            System.out.println(description);
            logService.save(description, user);
        } catch (Exception e){
            logger.error("异常信息:{}", e.getMessage());
        }
    }

    /*@AfterThrowing(pointcut = "aspect()", throwing = "e")
    public void afterThrow(JoinPoint joinPoint, Throwable e){
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();

        //用户
        User user = null;
        Object o = request.getAttribute("authToken");
        if (o != null){
            AuthToken authToken = (AuthToken)o;
            String uId = authToken.getUid();
            if (uId != null) {
                user = userService.get(uId);
            }
        }

        try {
            Object[] args = joinPoint.getArgs();
            String params = "";

            for (Object o1 : args) {
                if (o1 != null) {
                    params += o1.getClass().getName() + " " + o1 + ",";
                }
            }
            params += "\b";
            //异常信息
            String exception = "Exception:" + e.getClass().getName() + " " + e.getMessage();
            //异常方法
            String function = "Function:" + joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName() + "(" + params + ")";

            String msg = exception + "\n" + function;
            System.out.println("==========================================================================");
            System.out.println(msg);
            logService.save(msg, user);
        } catch (Exception ex){
            logger.error("异常信息:{}", ex.getMessage());
        }
    }*/

    private static String getServiceDescription(JoinPoint joinPoint)
            throws Exception {
        String targetName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Class targetClass = Class.forName(targetName);
        Method[] methods = targetClass.getMethods();
        String description = "";
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                description = method.getAnnotation(ServiceLog.class).description();
                break;
            }
        }
        return description;
    }
}
