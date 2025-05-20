package com.ypy.pyojbackendcommon.handler;

import com.ypy.pyojbackendcommon.model.judge.JudgeCase;
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
