package com.ypy.pyojbackend;

import com.ypy.pyojbackend.mapper.QuestionMapper;
import com.ypy.pyojbackend.model.entity.Question;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class PyojBackendApplicationTests {
    @Autowired
    QuestionMapper questionMapper;

    @Test
    void contextLoads() {
        List<Question> questions = questionMapper.selectList(null);
        System.out.println(questions);
    }
}
