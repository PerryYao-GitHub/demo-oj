package com.ypy.pyojbackendjudgeservice.strategy;


import com.ypy.pyojbackendcommon.exception.AppException;
import com.ypy.pyojbackendcommon.model.judge.JudgeResult;

public interface JudgeStrategy {
    JudgeResult doJudge(JudgeContext judgeContext) throws AppException;
}
