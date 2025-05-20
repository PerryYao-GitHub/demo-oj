package com.ypy.pyojbackendcommon.handler;

import com.ypy.pyojbackendcommon.model.judge.JudgeConfig;
import org.apache.ibatis.type.MappedTypes;


/**
 * parse json to class
 */
@MappedTypes(JudgeConfig.class)
public class JudgeConfigTypeHandler extends JsonBaseTypeHandler<JudgeConfig> {
    public JudgeConfigTypeHandler() {
        super(JudgeConfig.class);
    }
}
