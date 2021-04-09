package bg.softuni.hotelagency.aop;

import bg.softuni.hotelagency.service.LogService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogAspect {

    private final LogService logService;

    public LogAspect(LogService logService) {
        this.logService = logService;
    }

    @AfterReturning(pointcut="execution(* bg.softuni.hotelagency.service.UserService.registerUser(..))"
            ,returning="result")
    public void after(JoinPoint joinPoint,Object result) throws Throwable {
        logService.createRegisterLog((Long)result);
    }


    @AfterThrowing(pointcut = "execution(* bg.softuni.hotelagency..service..*(..))", throwing = "ex")
    public void exceptionThrownAspect(JoinPoint joinPoint, Throwable ex) {
        String action = joinPoint.getSignature().getName();
        logService.createLog(action, ex.getClass().getSimpleName());
    }


}
