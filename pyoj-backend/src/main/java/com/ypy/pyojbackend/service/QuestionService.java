package com.ypy.pyojbackend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ypy.pyojbackend.app.AppResponse;
import com.ypy.pyojbackend.exception.AppException;
import com.ypy.pyojbackend.model.entity.Question;
import com.ypy.pyojbackend.model.query.QuestionPageQuery;
import com.ypy.pyojbackend.model.request.QuestionRequest;
import com.ypy.pyojbackend.model.vo.PageVO;
import com.ypy.pyojbackend.model.vo.QuestionBriefVO;
import com.ypy.pyojbackend.model.vo.QuestionVO;

public interface QuestionService extends IService<Question> {

    AppResponse<Void> syncQuestion();

    AppResponse<Void> createQuestion(QuestionRequest questionRequest) throws AppException;

    AppResponse<Void> updateQuestion(QuestionRequest questionRequest) throws AppException;

    AppResponse<Void> deleteQuestion(Long id) throws AppException;

    /**
     * for user to get one question's detail info
     * @param questionId
     * @return
     */
    AppResponse<QuestionVO> getQuestionVOById(Long questionId) throws AppException;

    /**
     * for user to browse questions' simple info
     * @param questionPageQuery
     * @return
     */
    AppResponse<PageVO<QuestionBriefVO>> getQuestionBriefVOPage(QuestionPageQuery questionPageQuery);
}
