package com.ypy.pyojbackend.handler;

import com.ypy.pyojbackend.judge.model.JudgeConfig;
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
