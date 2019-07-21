package org.icslab.sibadev.common.config.redis.repository;

import org.icslab.sibadev.common.config.redis.RedisConstants;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

//@Repository
public class TestKeyManagementRepository {

    /*private RedisTemplate<Integer, Long> redisTestManageTemplate;

    private ValueOperations<Integer, Long> valueOperations;

    public TestKeyManagementRepository(RedisTemplate<Integer, Long> redisTestManageTemplate){
        this.redisTestManageTemplate = redisTestManageTemplate;
        this.valueOperations = this.redisTestManageTemplate.opsForValue();
    }

    //테스트 상태 조회
    public Long findTestId(Integer key){
        return valueOperations.get(key);
    }

    //테스트 상태 추가
    public void saveTestId(Integer testId, Long startedAt){
        valueOperations.set(testId, startedAt, RedisConstants.TEST_WAIT_TIMEOUT, TimeUnit.SECONDS);
    }*/
}
