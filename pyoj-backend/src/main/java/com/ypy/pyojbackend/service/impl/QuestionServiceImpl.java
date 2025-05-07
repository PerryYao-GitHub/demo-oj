package com.ypy.pyojbackend.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ypy.pyojbackend.app.AppCode;
import com.ypy.pyojbackend.app.AppResponse;
import com.ypy.pyojbackend.exception.AppException;
import com.ypy.pyojbackend.job.QuestionSyncTask;
import com.ypy.pyojbackend.mapper.QuestionMapper;
import com.ypy.pyojbackend.model.entity.Question;
import com.ypy.pyojbackend.model.enums.TagEnum;
import com.ypy.pyojbackend.model.query.QuestionPageQuery;
import com.ypy.pyojbackend.model.request.QuestionRequest;
import com.ypy.pyojbackend.model.vo.PageVO;
import com.ypy.pyojbackend.model.vo.QuestionBriefVO;
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

    private Question toQuestion(QuestionRequest questionRequest) {
        List<Integer> tags = questionRequest.getTags().stream().map(TagEnum.text2value::get).collect(Collectors.toList());
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
    public QuestionVO toQuestionVO(Question question) {
        QuestionVO vo = new QuestionVO();
        vo.setId(question.getId());
        vo.setTitle(question.getTitle());
        vo.setDescription(question.getDescription());
        vo.setTags(question.getTags().stream().map(TagEnum.value2text::get).collect(Collectors.toList()));
        vo.setSubmitCnt(question.getSubmitCnt());
        vo.setAcceptedCnt(question.getAcceptedCnt());
        vo.setJudgeConfig(question.getJudgeConfig());
        return vo;
    }

    @Override
    public QuestionBriefVO toQuestionBriefVO(Question question) {
        QuestionBriefVO vo = new QuestionBriefVO();
        vo.setId(question.getId());
        vo.setTitle(question.getTitle());
        vo.setTags(question.getTags().stream().map(TagEnum.value2text::get).collect(Collectors.toList()));
        vo.setAcRate(question.getSubmitCnt().equals(0) ? 0f : (float) question.getAcceptedCnt() / question.getSubmitCnt());
        return vo;
    }

    @Override
    public AppResponse<Void> createQuestion(QuestionRequest questionRequest) throws AppException {
        Question question = toQuestion(questionRequest);
        if (question.getId() != null) throw new AppException(AppCode.ERR_FORBIDDEN);
        if (!save(question)) throw new AppException(AppCode.ERR_SYSTEM);
        return new AppResponse<>(AppCode.OK, null);
    }

    @Override
    public AppResponse<Void> updateQuestion(QuestionRequest questionRequest) throws AppException {
        Question question = toQuestion(questionRequest);
        if (question.getId() == null) throw new AppException(AppCode.ERR_FORBIDDEN);
        if (!updateById(question)) throw new AppException(AppCode.ERR_SYSTEM);
        return new AppResponse<>(AppCode.OK, null);
    }

    @Override
    public AppResponse<Void> deleteQuestion(Long id) throws AppException {
        if (id == null) throw new AppException(AppCode.ERR_FORBIDDEN);
        if (!removeById(id)) throw new AppException(AppCode.ERR_SYSTEM);
        return new AppResponse<>(AppCode.OK, null);
    }

    @Override
    public AppResponse<QuestionVO> getQuestionVOById(Long questionId) throws AppException {
        String key = QuestionSyncTask.DETAIL_PREFIX + questionId;
        QuestionVO questionVO = (QuestionVO) redisTemplate.opsForValue().get(key);
        if (questionVO == null) throw new AppException(AppCode.ERR_NOT_FOUND);
        return new AppResponse<>(AppCode.OK, questionVO);
    }

    @Override
    public AppResponse<PageVO<QuestionBriefVO>> getQuestionBriefVOPage(QuestionPageQuery questionPageQuery) {
        PageVO<QuestionBriefVO> data;
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
    private PageVO<QuestionBriefVO> getQuestionsNoQuery(QuestionPageQuery questionPageQuery) {
        int pageSize = questionPageQuery.getPageSize();
        int pageNum = questionPageQuery.getPageNum();
        int start = (pageNum - 1) * pageSize;
        int end = start + pageSize - 1;

        int total = Math.toIntExact(redisTemplate.opsForList().size(QuestionSyncTask.AC_RATE_ASC_LIST_KEY));

        List<Object> redisList;
        if ("ac_rate".equals(questionPageQuery.getOrderBy())) {
            if (questionPageQuery.isDesc()) {
                redisList = redisTemplate.opsForList().range(QuestionSyncTask.AC_RATE_DESC_LIST_KEY, start, end);
            } else {
                redisList = redisTemplate.opsForList().range(QuestionSyncTask.AC_RATE_ASC_LIST_KEY, start, end);
            }
        } else {
            redisList = redisTemplate.opsForList().range(QuestionSyncTask.TITLE_ORDER_LIST_KEY, start, end);
        }

        List<QuestionBriefVO> content = redisList.stream()
            .filter(Objects::nonNull)
            .map(obj -> (QuestionBriefVO) obj)
            .collect(Collectors.toList());

        // Object -> QuestionPageVO
        return new PageVO<>(total, pageNum, pageSize, content);
    }

    /**
     * search in mysql
     * @param questionPageQuery
     * @return
     */
    private PageVO<QuestionBriefVO> getQuestionsByTitle(QuestionPageQuery questionPageQuery) {
        return null;
    }

    /**
     * search in redis
     * @param questionPageQuery
     * @return
     */
    private PageVO<QuestionBriefVO> getQuestionsByTags(QuestionPageQuery questionPageQuery) {
        return null;
    }

    /**
     * todo
     * @param questionPageQuery
     * @return
     */
    private PageVO<QuestionBriefVO> getQuestionsByTitleAndTags(QuestionPageQuery questionPageQuery) {
        return null;
    }
}
