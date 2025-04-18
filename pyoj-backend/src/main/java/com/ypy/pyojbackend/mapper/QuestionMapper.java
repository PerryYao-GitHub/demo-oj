package com.ypy.pyojbackend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ypy.pyojbackend.model.entity.Question;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface QuestionMapper extends BaseMapper<Question> {
}
