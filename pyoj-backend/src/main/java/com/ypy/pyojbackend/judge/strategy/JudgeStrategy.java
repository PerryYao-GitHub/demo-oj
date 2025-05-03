package com.ypy.pyojbackend.judge.strategy;

import com.ypy.pyojbackend.exception.AppException;
import com.ypy.pyojbackend.judge.model.JudgeResult;

public interface JudgeStrategy {
    JudgeResult doJudge(JudgeContext judgeContext) throws AppException;
}
