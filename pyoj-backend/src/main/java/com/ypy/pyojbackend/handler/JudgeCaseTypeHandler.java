package com.ypy.pyojbackend.handler;

import com.ypy.pyojbackend.judge.model.JudgeCase;
import org.apache.ibatis.type.MappedTypes;


/**
 * parse json to class
 */
@MappedTypes(JudgeCase.class)
public class JudgeCaseTypeHandler extends JsonBaseTypeHandler<JudgeCase> {
    public JudgeCaseTypeHandler() {
        super(JudgeCase.class);
    }
}
