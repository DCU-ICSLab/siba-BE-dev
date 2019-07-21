package org.icslab.sibadev.common.config.redis.repository;

import org.icslab.sibadev.common.config.redis.RedisConstants;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

@Repository
public class KeepAliveRepository {

    private RedisTemplate<String, Boolean> redisTemplate;

    private ValueOperations<String, Boolean> valueOperations;

    public KeepAliveRepository(RedisTemplate<String, Boolean> redisTemplate){
        this.redisTemplate = redisTemplate;
        this.valueOperations = this.redisTemplate.opsForValue();
    }

    //상태 추가
    public void save(String hubAuthKey, Boolean status){
        valueOperations.set(hubAuthKey, status, RedisConstants.KEEP_ALIVE_TIMEOUT, TimeUnit.SECONDS);
    }

    //상태 갱신
    public void update(String hubAuthKey, Boolean status){
        valueOperations.set(hubAuthKey, status, RedisConstants.KEEP_ALIVE_TIMEOUT, TimeUnit.SECONDS);
    }

    //상태 조회
    public Boolean find(String key){
        return valueOperations.get(key);
    }
}
