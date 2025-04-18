package com.ypy.pyojbackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ypy.pyojbackend.common.AppResponse;
import com.ypy.pyojbackend.mapper.QuestionMapper;
import com.ypy.pyojbackend.model.entity.Question;
import com.ypy.pyojbackend.model.query.QuestionPageQuery;
import com.ypy.pyojbackend.model.vo.QuestionSimpleVO;
import com.ypy.pyojbackend.model.vo.QuestionVO;
import com.ypy.pyojbackend.service.QuestionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question> implements QuestionService {
    @Override
    public AppResponse<QuestionVO> createQuestion(Question question) {
        return null;
    }

    @Override
    public AppResponse<QuestionVO> updateQuestion(Question question) {
        return null;
    }

    @Override
    public AppResponse<QuestionVO> deleteQuestion(Long id) {
        return null;
    }

    @Override
    public AppResponse<QuestionVO> getOneQuestion(Long questionId) {
        return null;
    }

    @Override
    public AppResponse<List<QuestionSimpleVO>> getQuestions(QuestionPageQuery questionPageQuery) {
        return null;
    }
}
