package com.ypy.pyojbackendsubmitservice.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ypy.pyojbackendcommon.app.AppCode;
import com.ypy.pyojbackendcommon.app.AppResponse;
import com.ypy.pyojbackendcommon.exception.AppException;
import com.ypy.pyojbackendcommon.model.entity.Question;
import com.ypy.pyojbackendcommon.model.entity.Submit;
import com.ypy.pyojbackendcommon.model.enums.LangEnum;
import com.ypy.pyojbackendcommon.model.enums.SubmitStatusEnum;
import com.ypy.pyojbackendcommon.model.query.SubmitPageQuery;
import com.ypy.pyojbackendcommon.model.request.SubmitRequest;
import com.ypy.pyojbackendcommon.model.vo.PageVO;
import com.ypy.pyojbackendcommon.model.vo.SubmitVO;
import com.ypy.pyojbackendserviceclient.service.JudgeFeignClient;
import com.ypy.pyojbackendserviceclient.service.QuestionFeignClient;
import com.ypy.pyojbackendserviceclient.service.UserFeignClient;
import com.ypy.pyojbackendsubmitservice.mapper.SubmitMapper;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class SubmitServiceImpl
        extends ServiceImpl<SubmitMapper, Submit>
        implements SubmitService {

    @Resource
    private UserFeignClient userFeignClient;

    @Resource
    private QuestionFeignClient questionFeignClient;

    @Resource
    @Lazy
    private JudgeFeignClient judgeService;

    private Submit toSubmit(SubmitRequest submitRequest, HttpServletRequest request) throws AppException {
        Submit submit = new Submit();
        submit.setUserId(userFeignClient.getLoginUserAuthDTO(request).getId());
        submit.setQuestionId(submitRequest.getQuestionId());
        submit.setLang(LangEnum.text2value.get(submitRequest.getLang()));
        submit.setCode(submitRequest.getCode());
        submit.setStatus(SubmitStatusEnum.WAITING.getValue());
        return submit;
    }

    private SubmitVO toSubmitVO(Submit submit, String questionTitle) {
        SubmitVO vo = new SubmitVO();

        vo.setId(submit.getId());

        vo.setUserId(submit.getUserId());
        vo.setUsername(userFeignClient.getById(submit.getUserId()).getUsername());

        vo.setQuestionId(submit.getQuestionId());
        vo.setQuestionTitle(questionTitle);

        vo.setStatus(SubmitStatusEnum.value2text.get(submit.getStatus()));

        vo.setLang(LangEnum.value2text.get(submit.getLang()));

        vo.setCode(submit.getCode());

        vo.setJudgeResult(submit.getJudgeResult());

        vo.setCreateTime(submit.getCreateTime());

        return vo;
    }

    private SubmitVO toSubmitVO(Submit submit) {
        SubmitVO vo = new SubmitVO();

        vo.setId(submit.getId());

        vo.setUserId(submit.getUserId());
        vo.setUsername(userFeignClient.getById(submit.getUserId()).getUsername());

        vo.setQuestionId(submit.getQuestionId());
        vo.setQuestionTitle(questionFeignClient.getById(submit.getQuestionId()).getTitle());

        vo.setStatus(SubmitStatusEnum.value2text.get(submit.getStatus()));

        vo.setLang(LangEnum.value2text.get(submit.getLang()));

        vo.setCode(submit.getCode());

        vo.setJudgeResult(submit.getJudgeResult());

        vo.setCreateTime(submit.getCreateTime());

        return vo;
    }

    @Override
    public AppResponse<SubmitVO> doSubmit(SubmitRequest submitRequest, HttpServletRequest request) throws AppException {
        Submit submit = toSubmit(submitRequest, request);
        Question question = questionFeignClient.getById(submit.getQuestionId());
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
        return new AppResponse<>(AppCode.OK, toSubmitVO(submit, question.getTitle()));
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
        return new AppResponse<>(AppCode.OK, new PageVO<>((int) resultPage.getTotal(), pageNum, pageSize, vos));
    }
}
