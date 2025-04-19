package com.ypy.pyojbackend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ypy.pyojbackend.common.AppResponse;
import com.ypy.pyojbackend.exception.AppException;
import com.ypy.pyojbackend.model.query.QuestionPageQuery;
import com.ypy.pyojbackend.model.entity.Question;
import com.ypy.pyojbackend.model.request.QuestionRequest;
import com.ypy.pyojbackend.model.vo.QuestionSimpleVO;
import com.ypy.pyojbackend.model.vo.QuestionVO;

import java.util.List;

public interface QuestionService extends IService<Question> {
    Question toQuestion(QuestionRequest questionRequest);

    AppResponse<?> createQuestion(Question question) throws AppException;

    AppResponse<?> updateQuestion(Question question) throws AppException;

    AppResponse<?> deleteQuestion(Long id) throws AppException;

    /**
     * for user to get one question's detail info
     * @param questionId
     * @return
     */
    AppResponse<QuestionVO> getOneQuestion(Long questionId) throws AppException;

    /**
     * for user to browse questions' simple info
     * @param questionPageQuery
     * @return
     */
    AppResponse<List<QuestionSimpleVO>> getQuestions(QuestionPageQuery questionPageQuery);
}
