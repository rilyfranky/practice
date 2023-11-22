package com.myweb.sbb;

import com.myweb.sbb.question.Question;
import com.myweb.sbb.question.QuestionRepository;
import com.myweb.sbb.question.QuestionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
class SbbApplicationTests {

	@Autowired
	private QuestionService questionService;

	@Test
	void testJpa(){
		for (int i = 1; i <= 300; i++){
			String subject = String.format("테스트 데이터 : [%03d]", i);
			String content = "내용";
			this.questionService.create(subject, content);
		}
	}
}
