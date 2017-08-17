package com.ep.annotation;

import com.ep.commons.domain.dao.LogDao;
import com.ep.commons.domain.model.Log;
import com.ep.commons.domain.model.Organ;
import com.ep.commons.domain.model.User;
import com.ep.commons.domain.model.UserOrgan;
import com.ep.commons.domain.service.UserService;
import com.ep.commons.tool.handle.AuthToken;
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

@Aspect
@Component
public class LogAspect {
    @Autowired
    LogDao logDao;

    @Autowired
    UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);

    @Pointcut("@annotation(com.ep.annotation.ServiceLog)")
    public void aspect(){
    }

    @After("aspect() && @annotation(serviceLog)")
    public void after(ServiceLog serviceLog){
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();

        Log log = new Log();
        //用户
        User user = null;
        Organ organ = null;
        Object o = request.getAttribute("authToken");
        if (o != null){
            AuthToken authToken = (AuthToken)o;
            String uId = authToken.getUid();
            if (uId != null) {
                user = userService.get(uId);
                if (user != null && user.getUserOrgans() != null){
                    UserOrgan userOrgans;
                    for (UserOrgan userOrgan : user.getUserOrgans()) {
                        userOrgans = userOrgan;
                        if (userOrgans.getPrimary()) {
                            organ = userOrgans.getOrgan();
                        }
                    }
                }
            }
        }

        try {
            log.setContext(serviceLog.description());
            log.setCreator(user);
            log.setOrgan(organ);
            logDao.save(log);
            System.out.println("==================================后置通知=============================================");
            System.out.println(serviceLog.description());
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
}
