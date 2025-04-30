package com.ypy.pyojbackend.judge.strategy;

import com.ypy.pyojbackend.exception.AppException;
import com.ypy.pyojbackend.judge.model.JudgeInfo;

public interface JudgeStrategy {
    JudgeInfo doJudge(JudgeContext judgeContext) throws AppException;
}
