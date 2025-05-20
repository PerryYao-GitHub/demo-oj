package com.ypy.pyojbackendcommon.handler;

import com.ypy.pyojbackendcommon.model.judge.JudgeResult;
import org.apache.ibatis.type.MappedTypes;

@MappedTypes(JudgeResult.class)
public class JudgeResultTypeHandler extends JsonBaseTypeHandler<JudgeResult> {
    public JudgeResultTypeHandler() {
        super(JudgeResult.class);
    }
}
