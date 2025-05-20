package com.ypy.pyojbackendquestionservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ypy.pyojbackendcommon.app.AppResponse;
import com.ypy.pyojbackendcommon.exception.AppException;
import com.ypy.pyojbackendcommon.model.entity.Question;
import com.ypy.pyojbackendcommon.model.query.QuestionPageQuery;
import com.ypy.pyojbackendcommon.model.request.QuestionRequest;
import com.ypy.pyojbackendcommon.model.vo.PageVO;
import com.ypy.pyojbackendcommon.model.vo.QuestionBriefVO;
import com.ypy.pyojbackendcommon.model.vo.QuestionVO;

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
