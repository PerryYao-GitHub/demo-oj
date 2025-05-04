package com.ypy.pyojbackend.judge;

import com.ypy.pyojbackend.exception.AppException;

public interface JudgeService {

    void doJudge(long submitId) throws AppException;
}
