package space.springboot.community.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import space.springboot.community.utils.DateUtils;
import space.springboot.community.utils.IPUtils;
import space.springboot.community.utils.RedisUtils;

@Aspect
@Component
public class HyperLogAspect {

    @Autowired
    private RedisUtils redisUtils;

    @Value("${RANK_KEY}")
    private String RANK_KEY;

    @Value("${COMMENTS_COUNT_KEY}")
    private String COMMENTS_COUNT_KEY;

    @Value("${VIEWS_COUNT_KEY}")
    private String VIEWS_COUNT_KEY;

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
        System.out.println(joinPoint.getSignature());
        Object[] args = joinPoint.getArgs();
        Object questionId = args[0];
        Object obj = null;
        String redisKey = "";
        String redisZkey = RANK_KEY + ":" + DateUtils.getDate();
        try {
            String ip = IPUtils.getIpAddr();
            if (joinPoint.getSignature().toString().contains("insertComment")){
               if (redisUtils.zScore(redisZkey,questionId.toString()) == null){
                   redisUtils.zAdd(redisZkey,questionId.toString(),3);
               }else {
                   redisUtils.zInc(redisZkey,questionId.toString(),3);
               }
                redisKey = COMMENTS_COUNT_KEY + ":" + questionId;
            }else {
                if (redisUtils.zScore(redisZkey,questionId.toString()) == null){
                    redisUtils.zAdd(redisZkey,questionId.toString(),1);
                }else {
                    redisUtils.zInc(redisZkey,questionId.toString(),1);
                }
                redisKey = VIEWS_COUNT_KEY + ":" + questionId;
            }
            System.out.println("views aop " + redisKey);
            redisUtils.hAdd(redisKey,ip);
            obj = joinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return obj;
    }
}
