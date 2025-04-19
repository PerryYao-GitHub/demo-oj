package com.ypy.pyojbackend.job;

import com.ypy.pyojbackend.mapper.QuestionMapper;
import com.ypy.pyojbackend.model.entity.Question;
import com.ypy.pyojbackend.model.vo.QuestionVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class QuestionSyncTask {

    public static final String DETAIL_PREFIX = "pyoj:job:question-sync-task:detail:";

    @Resource
    private QuestionMapper questionMapper;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Scheduled(cron = "0 0 12 * * ?")
    public void syncQuestionDetail() {
        log.info("start sync question to redis");

        List<Question> questions = questionMapper.selectList(null);
        questions.forEach((question) -> {
            QuestionVO qvo = QuestionVO.from(question);
            String key = DETAIL_PREFIX + qvo.getId();
            redisTemplate.opsForValue().set(key, qvo, 25, TimeUnit.HOURS);
        });

        log.info("end sync question to redis, {} questions in total", questions.size());
    }

    @EventListener(ApplicationReadyEvent.class) // do sync when app start
    public void syncAll() {
        syncQuestionDetail();
    }
}
