package com.ypy.pyojbackendjudgeservice.service;

import com.ypy.pyojbackendcommon.exception.AppException;

public interface JudgeService {

    void doJudge(long submitId) throws AppException;
}
