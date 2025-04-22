package com.ypy.pyojbackend.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ypy.pyojbackend.common.AppCode;
import com.ypy.pyojbackend.common.AppResponse;
import com.ypy.pyojbackend.common.TagEnum;
import com.ypy.pyojbackend.exception.AppException;
import com.ypy.pyojbackend.job.QuestionSyncTask;
import com.ypy.pyojbackend.mapper.QuestionMapper;
import com.ypy.pyojbackend.model.entity.Question;
import com.ypy.pyojbackend.model.query.QuestionPageQuery;
import com.ypy.pyojbackend.model.request.QuestionRequest;
import com.ypy.pyojbackend.model.vo.QuestionPageVO;
import com.ypy.pyojbackend.model.vo.QuestionVO;
import com.ypy.pyojbackend.service.QuestionService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question> implements QuestionService {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public Question toQuestion(QuestionRequest questionRequest) {
        List<Integer> tags = questionRequest.getTags().stream().map(TagEnum.textValueMap::get).collect(Collectors.toList());
        Question question = new Question();
        question.setId(questionRequest.getId());
        question.setTitle(questionRequest.getTitle());
        question.setDescription(questionRequest.getDescription());
        question.setTags(tags);
        question.setJudgeCase(questionRequest.getJudgeCase());
        question.setJudgeConfig(questionRequest.getJudgeConfig());
        return question;
    }

    @Override
    public AppResponse<?> createQuestion(Question question) throws AppException {
        System.out.println(question.getTags());
        if (question.getId() != null) throw new AppException(AppCode.ERR_FORBIDDEN);
        if (!save(question)) throw new AppException(AppCode.ERR_SYSTEM);
        return new AppResponse<>(AppCode.OK, null);
    }

    @Override
    public AppResponse<?> updateQuestion(Question question) throws AppException {
        if (question.getId() == null) throw new AppException(AppCode.ERR_FORBIDDEN);
        if (!updateById(question)) throw new AppException(AppCode.ERR_SYSTEM);
        return new AppResponse<>(AppCode.OK, null);
    }

    @Override
    public AppResponse<?> deleteQuestion(Long id) throws AppException {
        if (id == null) throw new AppException(AppCode.ERR_FORBIDDEN);
        if (!removeById(id)) throw new AppException(AppCode.ERR_SYSTEM);
        return new AppResponse<>(AppCode.OK, null);
    }

    @Override
    public AppResponse<QuestionVO> getOneQuestion(Long questionId) throws AppException {
        String key = QuestionSyncTask.DETAIL_PREFIX + questionId;
        QuestionVO questionVO = (QuestionVO) redisTemplate.opsForValue().get(key);
        if (questionVO == null) throw new AppException(AppCode.ERR_NOT_FOUND);
        return new AppResponse<>(AppCode.OK, questionVO);
    }

    @Override
    public AppResponse<List<QuestionPageVO>> getQuestionPage(QuestionPageQuery questionPageQuery) {
        List<QuestionPageVO> data;
        boolean hasTitle = StrUtil.isNotBlank(questionPageQuery.getTitle());
        boolean hasTags = CollUtil.isNotEmpty(questionPageQuery.getTags());

        if (!hasTitle && !hasTags) {
            // no condition search
            data = getQuestionsNoQuery(questionPageQuery);
        } else if (!hasTitle) {
            // only tags
            data = getQuestionsByTags(questionPageQuery);
        } else if (!hasTags) {
            // only title
            data = getQuestionsByTitle(questionPageQuery);
        } else {
            // title + tags
            data = getQuestionsByTitleAndTags(questionPageQuery);
        }
        return new AppResponse<>(AppCode.OK, data);
    }

    /**
     * no condition search
     * @param questionPageQuery
     * @return
     */
    private List<QuestionPageVO> getQuestionsNoQuery(QuestionPageQuery questionPageQuery) {
        int pageSize = questionPageQuery.getPageSize();
        int pageNum = questionPageQuery.getPageNum();
        int start = (pageNum - 1) * pageSize;
        int end = start + pageSize - 1;

        List<Object> redisList;
        if (questionPageQuery.getOrderBy() == QuestionPageQuery.OrderBy.AC_RATE) {
            if (questionPageQuery.getOrderType() == QuestionPageQuery.OrderType.DESC) {
                redisList = redisTemplate.opsForList().range(QuestionSyncTask.AC_RATE_DESC_LIST_KEY, start, end);
            } else {
                redisList = redisTemplate.opsForList().range(QuestionSyncTask.AC_RATE_ASC_LIST_KEY, start, end);
            }
        } else {
            redisList = redisTemplate.opsForList().range(QuestionSyncTask.TITLE_ORDER_LIST_KEY, start, end);
        }

        // Object -> QuestionPageVO
        return redisList.stream()
            .filter(Objects::nonNull)
            .map(obj -> (QuestionPageVO) obj)
            .collect(Collectors.toList());
    }

    /**
     * search in mysql
     * @param questionPageQuery
     * @return
     */
    private List<QuestionPageVO> getQuestionsByTitle(QuestionPageQuery questionPageQuery) {
        return null;
    }

    /**
     * search in redis
     * @param questionPageQuery
     * @return
     */
    private List<QuestionPageVO> getQuestionsByTags(QuestionPageQuery questionPageQuery) {
        return null;
    }

    /**
     * todo
     * @param questionPageQuery
     * @return
     */
    private List<QuestionPageVO> getQuestionsByTitleAndTags(QuestionPageQuery questionPageQuery) {
        return null;
    }
}
