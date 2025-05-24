package com.ypy.pyojbackend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ypy.pyojbackend.app.AppCode;
import com.ypy.pyojbackend.app.AppResponse;
import com.ypy.pyojbackend.exception.AppException;
import com.ypy.pyojbackend.judge.JudgeService;
import com.ypy.pyojbackend.mapper.SubmitMapper;
import com.ypy.pyojbackend.model.entity.Question;
import com.ypy.pyojbackend.model.entity.Submit;
import com.ypy.pyojbackend.model.enums.LangEnum;
import com.ypy.pyojbackend.model.enums.SubmitStatusEnum;
import com.ypy.pyojbackend.model.query.SubmitPageQuery;
import com.ypy.pyojbackend.model.request.SubmitRequest;
import com.ypy.pyojbackend.model.vo.PageVO;
import com.ypy.pyojbackend.model.vo.SubmitVO;
import com.ypy.pyojbackend.service.QuestionService;
import com.ypy.pyojbackend.service.SubmitService;
import com.ypy.pyojbackend.service.UserService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class SubmitServiceImpl
        extends ServiceImpl<SubmitMapper, Submit>
        implements SubmitService {

    @Resource
    private UserService userService;

    @Resource
    private QuestionService questionService;

    @Resource
    @Lazy
    private JudgeService judgeService;

    private Submit toSubmit(SubmitRequest submitRequest) {
        Submit submit = new Submit();
        submit.setUserId(submitRequest.getUserId());
        submit.setQuestionId(submitRequest.getQuestionId());
        submit.setLang(LangEnum.text2value.get(submitRequest.getLang()));
        submit.setCode(submitRequest.getCode());
        submit.setStatus(SubmitStatusEnum.WAITING.getValue());
        return submit;
    }

    private SubmitVO toSubmitVO(Submit submit) {
        SubmitVO vo = new SubmitVO();

        vo.setId(submit.getId());

        vo.setUserId(submit.getUserId());
        vo.setUsername(userService.getById(submit.getUserId()).getUsername());

        vo.setQuestionId(submit.getQuestionId());
        vo.setQuestionTitle(questionService.getById(submit.getQuestionId()).getTitle());

        vo.setStatus(SubmitStatusEnum.value2text.get(submit.getStatus()));

        vo.setLang(LangEnum.value2text.get(submit.getLang()));

        vo.setCode(submit.getCode());

        vo.setJudgeResult(submit.getJudgeResult());

        vo.setCreateTime(submit.getCreateTime());

        return vo;
    }

    @Override
    public AppResponse<SubmitVO> doSubmit(SubmitRequest submitRequest) throws AppException {
        Submit submit = toSubmit(submitRequest);
        Question question = questionService.getById(submit.getQuestionId());
        if (question == null) throw new AppException(AppCode.ERR_NOT_FOUND);
        if (!save(submit)) throw new AppException(AppCode.ERR_SYSTEM);

        long submitId = submit.getId(); // once save successfully, the id and some other field will be filled
        CompletableFuture.runAsync(() -> {
            try {
                judgeService.doJudge(submitId);
            } catch (AppException e) {
                throw new RuntimeException(e);
            }
        });
        return new AppResponse<>(AppCode.OK, toSubmitVO(submit));
    }

    @Override
    public AppResponse<SubmitVO> getSubmitVOById(Long id) {
        return new AppResponse<>(AppCode.OK, toSubmitVO(getById(id)));
    }

    @Override
    public AppResponse<PageVO<SubmitVO>> getSubmitVOListByUserIdOrQuestionId(SubmitPageQuery submitPageQuery) {
        Long userId = submitPageQuery.getUserId();
        Long questionId = submitPageQuery.getQuestionId();

        int pageSize = submitPageQuery.getPageSize();
        int pageNum = submitPageQuery.getPageNum();

        // pagination obj
        Page<Submit> pg = new Page<>(pageNum, pageSize);

        // query condition
        QueryWrapper<Submit> qw = new QueryWrapper<>();
        qw.eq(userId != null && userId != 0L, "user_id", userId);
        qw.eq(questionId != null && questionId != 0L, "question_id", questionId);
        qw.orderByDesc("create_time"); // 通常按创建时间倒序

        // do pagination
        Page<Submit> resultPage = this.page(pg, qw);

        List<Submit> submits = resultPage.getRecords();
        List<SubmitVO> vos = submits.stream().map(this::toSubmitVO).collect(Collectors.toList());
        return new AppResponse<>(AppCode.OK, new PageVO<>((int)resultPage.getTotal(), pageNum, pageSize, vos));
    }
}
