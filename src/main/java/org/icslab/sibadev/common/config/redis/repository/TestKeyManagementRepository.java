package org.icslab.sibadev.common.config.redis.repository;

import org.icslab.sibadev.common.config.redis.RedisConstants;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

@Repository
public class TestKeyManagementRepository {

    private RedisTemplate<String, Long> redisTestManageTemplate;

    private ValueOperations<String, Long> valueOperations;

    public TestKeyManagementRepository(RedisTemplate<String, Long> redisTestManageTemplate){
        this.redisTestManageTemplate = redisTestManageTemplate;
        this.valueOperations = this.redisTestManageTemplate.opsForValue();
    }

    //테스트 상태 조회
    public Long findTestId(String key){
        return valueOperations.get(key);
    }

    //테스트 상태 추가
    public void saveTestId(String testId, Long startedAt){
        valueOperations.set(testId, startedAt, RedisConstants.TEST_WAIT_TIMEOUT, TimeUnit.SECONDS);
    }

    //테스트 상태 추가
    public void saveTestId(String testId, Long startedAt, Long reservationAt){
        valueOperations.set(testId, startedAt, (reservationAt - startedAt)+10, TimeUnit.SECONDS);
    }
}
