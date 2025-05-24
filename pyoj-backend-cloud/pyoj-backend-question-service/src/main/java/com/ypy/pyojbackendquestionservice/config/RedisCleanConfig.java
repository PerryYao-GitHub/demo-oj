package com.ypy.pyojbackendquestionservice.config;

import com.ypy.pyojbackendquestionservice.job.QuestionSyncTask;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import java.util.Set;

@Configuration
public class RedisCleanConfig {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @PreDestroy
    public void clearQuestionKeyOnShutdown() {
        Set<String> keys = redisTemplate.keys(QuestionSyncTask.PREFIX + "*");
        if (keys != null && !keys.isEmpty()) {
            redisTemplate.delete(keys);
            System.out.println("🧹 Clean keys in Redis: " + keys.size() + " in total.");
        } else {
            System.out.println("✅ No keys in Redis.");
        }
    }
}
