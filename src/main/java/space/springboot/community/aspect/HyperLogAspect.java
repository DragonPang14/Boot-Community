package space.springboot.community.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import space.springboot.community.utils.IPUtils;
import space.springboot.community.utils.RedisUtils;

@Aspect
@Component
public class HyperLogAspect {

    @Autowired
    private RedisUtils redisUtils;

    /**
     * @desc aop切入点
     */
    @Pointcut("@annotation(space.springboot.community.aspect.HyperLogInc)")
    public void pointCut(){
    }

    /**
     * @desc 切入点后执行的内容，即通知，around，即切入点的方法执行前后执行通知，用joinPoint.proceed()来控制切入点方法的执行
     * @return
     */
    @Around("pointCut()")
    public Object around(ProceedingJoinPoint joinPoint){
        System.out.println("aop around start");
        Object[] args = joinPoint.getArgs();
        Object questionId = args[0];
        Object obj = null;
        try {
            String ip = IPUtils.getIpAddr();
            String redisKey = "questionViewsId_" + questionId;
            redisUtils.hAdd(redisKey,ip);
            obj = joinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return obj;
    }
}
