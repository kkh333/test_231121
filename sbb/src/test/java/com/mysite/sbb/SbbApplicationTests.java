package com.mysite.sbb;

import com.mysite.sbb.answer.Answer;
import com.mysite.sbb.answer.AnswerRepository;
import com.mysite.sbb.question.Question;
import com.mysite.sbb.question.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class SbbApplicationTests {

	@Autowired
	private QuestionRepository questionRepository;

	@Autowired
	private AnswerRepository answerRepository;

	//question 데이터 생성
	@Test
	void testJpa1() {
		Question q1 = new Question();
		q1.setSubject("제목1");
		q1.setContent("내용1");
		q1.setCreateDate(LocalDateTime.now());
		this.questionRepository.save(q1);

		Question q2 = new Question();
		q2.setSubject("제목2");
		q2.setContent("내용2");
		q2.setCreateDate(LocalDateTime.now());
		this.questionRepository.save(q2);

		Question q3 = new Question();
		q3.setSubject("제목3");
		q3.setContent("내용3");
		q3.setCreateDate(LocalDateTime.now());
		this.questionRepository.save(q3);
	}

	//question 모든 데이터 조회
	@Test
	void testJpa2() {
		List<Question> questionList = this.questionRepository.findAll();
		assertEquals(3, questionList.size());

		Question question = questionList.get(0);
		assertEquals("제목1", question.getSubject());
	}

	//question id값 데이터 조회
	@Test
	void testJpa3() {
		Optional<Question> oq = this.questionRepository.findById(1);
		oq.isPresent();
		Question question = oq.get();
		assertEquals("제목1", question.getSubject());
	}

	//question 해당 제목 데이터 조회
	@Test
	void testJpa4() {
		//여기서 Optional타입으로 해서 안되는 이유는 기본적으로 제공하는 메소드는 null일 경우 대응이 되어있지만 findBySubject 같은 경우 만들어준 메소드이기 때문에 null에 대한 대응이 되어있지않다.
		//Optional<Question> oq = this.questionRepository.findBySubject("제목1");
		Question question = this.questionRepository.findBySubject("제목1");
		assertEquals(1, question.getId());
	}

	//question 해당 제목과 내용 데이터 조회
	@Test
	void testJpa5() {
		Question question = this.questionRepository.findBySubjectAndContent("제목1", "내용1");
		assertEquals(1, question.getId());
	}

	//question 해당 제목 검색 내용 포함 데이터 조회
	@Test
	void testJpa6() {
		List<Question> questionList = this.questionRepository.findBySubjectLike("제목%");
		assertEquals(1, questionList.get(0).getId());
		assertEquals("제목2", questionList.get(1).getSubject());
	}

	//question 내용 수정하기
	@Test
	void testJpa7() {
		Optional<Question> oq = this.questionRepository.findById(1);
		assertTrue(oq.isPresent());
		Question question = oq.get();
		question.setSubject(question.getSubject() + " 수정됌");
		question.setContent(question.getContent() + " 수정됌");
		this.questionRepository.save(question);
	}

	//question 내용 삭제하기
	@Test
	void testJpa8() {
		Optional<Question> oq = this.questionRepository.findById(3);
		assertTrue(oq.isPresent());
		Question question = oq.get();
		this.questionRepository.delete(question);
	}

	//answer 데이터 생성
	@Test
	void testJpa9() {
		Optional<Question> oq = this.questionRepository.findById(1);
		assertTrue(oq.isPresent());
		Question question = oq.get();

		Answer a1 = new Answer();
		a1.setContent("답변1-1");
		a1.setCreateDate(LocalDateTime.now());
		a1.setQuestion(question);

		this.answerRepository.save(a1);

		Answer a2 = new Answer();
		a2.setContent("답변1-2");
		a2.setCreateDate(LocalDateTime.now());
		a2.setQuestion(question);

		this.answerRepository.save(a2);
	}

	//answer 데이터 조회
	@Test
	void testJpa10() {
		Optional<Answer> oa = this.answerRepository.findById(1);
		assertTrue(oa.isPresent());
		Answer answer = oa.get();
		assertEquals(1, answer.getQuestion().getId());
		assertEquals("제목1 수정됌", answer.getQuestion().getSubject());
	}

	//question에 연결된 answer 데이터 찾기
	//Question 리포지터리가 findById를 호출하여 Question 객체를 조회하고 나면 DB세션이 끊어지기 때문에 오류가 발생
	//@Transactional 애너테이션을 사용하면 메서드가 종료될 때까지 DB 세션이 유지된다.
	@Transactional
	@Test
	void testJpa11() {
		Optional<Question> oq = this.questionRepository.findById(1);
		assertTrue(oq.isPresent());
		Question question = oq.get();

		List<Answer> answerList = question.getAnswerList();

		assertEquals(2, answerList.size());
		assertEquals("답변1-1", answerList.get(0).getContent());
	}
}
