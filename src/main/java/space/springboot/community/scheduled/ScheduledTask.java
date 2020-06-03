package space.springboot.community.scheduled;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import space.springboot.community.mapper.QuestionMapper;
import space.springboot.community.utils.RedisUtils;

import java.util.Set;

@Component
public class ScheduledTask {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private RedisUtils redisUtils;

    @Transactional(rollbackFor=Exception.class)
    @Scheduled(initialDelay = 1,fixedDelay = 300000)
    public void updateViews(){
        Set<String> keySet = redisUtils.keys("questionViewsId_*");
        System.out.println("阅读数定时任务启动=============");
        if (keySet.size() > 0){
            keySet.forEach(key -> {
                long viewCount = redisUtils.hSize(key);
                String[] questionId = key.split("_");
                questionMapper.updateViews(Integer.valueOf(questionId[1]),viewCount);
                redisUtils.del(key);
            });
        }
        System.out.println("阅读数定时任务结束=============");
    }

}
