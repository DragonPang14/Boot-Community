package space.springboot.community.utils;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import space.springboot.community.exception.CustomizeErrorCode;
import space.springboot.community.exception.CustomizeException;

import java.util.concurrent.TimeUnit;

@Component
public class RedisUtils {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * @param key
     * @param time
     * @return
     * @desc 设置过期时间
     */
    public boolean expire(String key, long time) {
        try {
            redisTemplate.expire(key, time, TimeUnit.SECONDS);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    /**
     * @param key
     * @return
     * @desc 获取过期时间
     */
    public long getExpire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * @param key
     * @return
     * @desc 检查key是否存在
     */
    public boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * @param key
     * @param value
     * @return
     * @desc set值
     */
    public boolean set(String key, Object value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * @param key
     * @return
     * @desc get值
     */
    public Object get(String key) {
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }


    /**
     * @param key
     * @param num
     * @return
     * @desc 递增，num不能小于0
     */
    public long incr(String key, long num) {
        if (num < 0) {
            throw new CustomizeException(CustomizeErrorCode.INCREMENT_ERROR);
        }
        return redisTemplate.opsForValue().increment(key,num);
    }
}
