package com.ypy.pyojbackend.job;

import com.ypy.pyojbackend.mapper.QuestionMapper;
import com.ypy.pyojbackend.model.entity.Question;
import com.ypy.pyojbackend.model.vo.QuestionBriefVO;
import com.ypy.pyojbackend.model.vo.QuestionVO;
import com.ypy.pyojbackend.service.QuestionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@Component
public class QuestionSyncTask {

    public static final String DETAIL_PREFIX = "pyoj:job:question-sync-task:detail:";

    public static final String TITLE_ORDER_LIST_KEY = "pyoj:job:question-sync-task:title-order-list";

    public static final String AC_RATE_ASC_LIST_KEY = "pyoj:job:question-sync-task:ac-rate-asc-list";

    public static final String AC_RATE_DESC_LIST_KEY = "pyoj:job:question-sync-task:ac-rate-desc-list";

    @Resource
    QuestionService questionService;

    @Resource
    private QuestionMapper questionMapper;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Scheduled(cron = "0 0 12 * * ?")
    public void syncQuestionDetail() {
        log.info("start sync question detail to redis");

        List<Question> questions = questionMapper.selectList(null);
        questions.forEach((question) -> {
            QuestionVO qvo = questionService.toQuestionVO(question);
            String key = DETAIL_PREFIX + qvo.getId();
            redisTemplate.opsForValue().set(key, qvo, 25, TimeUnit.HOURS);
        });

        log.info("end sync question detail to redis, {} questions in total", questions.size());
    }

    @Scheduled(cron = "0 0 12 * * ?")
    public void syncQuestionList() {
        log.info("start sync question list to redis");

        List<Question> questions = questionMapper.selectList(null);
        List<QuestionBriefVO> voList = questions.stream()
                .map(q -> questionService.toQuestionBriefVO(q))
                .sorted(Comparator.comparing(QuestionBriefVO::getTitle))
                .collect(Collectors.toList());
        // sort by title
        redisTemplate.delete(TITLE_ORDER_LIST_KEY);
        redisTemplate.opsForList().rightPushAll(TITLE_ORDER_LIST_KEY, voList);

        voList.sort(Comparator.comparing(QuestionBriefVO::getAcRate));
        // sort by ac rate asc
        redisTemplate.delete(AC_RATE_ASC_LIST_KEY);
        voList.forEach(vo -> redisTemplate.opsForList().rightPush(AC_RATE_ASC_LIST_KEY, vo));

        // sort by ac rate desc
        redisTemplate.delete(AC_RATE_DESC_LIST_KEY);
        voList.forEach(vo -> redisTemplate.opsForList().leftPush(AC_RATE_DESC_LIST_KEY, vo));

        log.info("end sync question list to redis, {} questions in total", questions.size());
    }

    @EventListener(ApplicationReadyEvent.class) // do sync when app start
    public void syncAll() {
        syncQuestionDetail();
        syncQuestionList();
    }
}
