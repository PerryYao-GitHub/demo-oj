package com.ypy.pyojbackend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ypy.pyojbackend.common.AppResponse;
import com.ypy.pyojbackend.model.query.QuestionPageQuery;
import com.ypy.pyojbackend.model.entity.Question;
import com.ypy.pyojbackend.model.vo.QuestionSimpleVO;
import com.ypy.pyojbackend.model.vo.QuestionVO;

import java.util.List;

public interface QuestionService extends IService<Question> {

    AppResponse<QuestionVO> createQuestion(Question question);

    AppResponse<QuestionVO> updateQuestion(Question question);

    AppResponse<QuestionVO> deleteQuestion(Long id);

    /**
     * for user to get one question's detail info
     * @param questionId
     * @return
     */
    AppResponse<QuestionVO> getOneQuestion(Long questionId);

    /**
     * for user to browse questions' simple info
     * @param questionPageQuery
     * @return
     */
    AppResponse<List<QuestionSimpleVO>> getQuestions(QuestionPageQuery questionPageQuery);
}
