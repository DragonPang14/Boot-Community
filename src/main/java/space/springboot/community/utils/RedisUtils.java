package space.springboot.community.utils;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import space.springboot.community.exception.CustomizeErrorCode;
import space.springboot.community.exception.CustomizeException;

import java.util.Set;
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
     * @desc 删除缓存
     * @param key 可以传一个值 或多个
     */
    @SuppressWarnings("unchecked")
    public void del(String... key) {
        if (key != null && key.length > 0) {
            if (key.length == 1) {
                redisTemplate.delete(key[0]);
            } else {
                redisTemplate.delete(CollectionUtils.arrayToList(key));
            }
        }
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

    /**
     * @desc hyperloglog递增
     * @param key
     * @param value
     * @return
     */
    public long hAdd(String key, String value){
        return redisTemplate.opsForHyperLogLog().add(key,value);
    }

    /**
     * @desc hyperloglog大小
     * @param key
     * @return
     */
    public Long hSize(String key){
        return redisTemplate.opsForHyperLogLog().size(key);
    }


    /**
     * @desc 匹配key
     * @param keyPattern
     * @return
     */
    public Set<String> keys(String keyPattern){
        return redisTemplate.keys(keyPattern);
    }

    /**
     * @desc 有序集合添加
     * @param key
     * @param value
     * @param score
     */
    public void zAdd(String key,String value,double score){
        redisTemplate.opsForZSet().add(key,value,score);
    }


    /**
     * @desc 有序集合增加delta
     * @param key
     * @param value
     * @param delta
     */
    public void zInc(String key,String value,double delta){
        redisTemplate.opsForZSet().incrementScore(key,value,delta);
    }

    /**
     * @desc 获取有序集合排行榜
     * @param key
     * @param min
     * @param max
     */
    public Set zRank(String key,double min,double max){
        return redisTemplate.opsForZSet().reverseRangeByScore(key,min,max);
    }

    public Double zScore(String key,String value){
        return redisTemplate.opsForZSet().score(key,value);
    }


    public boolean setBit(String key,long userId,boolean logged){
        return redisTemplate.opsForValue().setBit(key,userId,logged);
    }


    public boolean getBig(String key,long userId){
        return redisTemplate.opsForValue().getBit(key, userId);
    }

    public Long bitCount(String key){
        return redisTemplate.execute((RedisCallback<Long>) con -> con.bitCount(key.getBytes()));
    }

}
