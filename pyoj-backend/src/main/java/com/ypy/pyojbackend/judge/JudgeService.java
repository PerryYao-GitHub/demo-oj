package com.ypy.pyojbackend.judge;

import com.ypy.pyojbackend.exception.AppException;
import com.ypy.pyojbackend.model.entity.Submit;

public interface JudgeService {

    void doJudge(long submitId) throws AppException;
}
