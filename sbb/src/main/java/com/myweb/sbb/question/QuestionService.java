package com.myweb.sbb.question;


import com.myweb.sbb.answer.Answer;
import com.myweb.sbb.user.SiteUser;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import com.myweb.sbb.DataNotFoundException;

@RequiredArgsConstructor
@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

    /*
    q : Root, 기준을 의미하는 Question 엔티티의 객체 (질문 제목과 내용을 검색하기 위해 필요)
    u1 : Question과 SiteUser를 아우터 조인하여 만든 SiteUser 객체. 둘은 author 속성으로 연결되어 있기때문에 q.join("author")와
    같이 조인해야 한다. -> 질문 작성자를 검색하기 위해 필요
    a : Question과 Answer를 아우터조인하여 만든 Answer의 객체. 둘은 answerList 속성으로 연결되어있음 -> 답변 내용을 검색하기 위해 필요
    u2 : 위에서 작성한 a를 다시 한번 SiteUser와 조인하여 만든 SiteUser 객체 -> 답변 작성자를 검색하기 위해 필요
     */
    private Specification<Question> search(String kw){
        return new Specification<>(){
            private static final long serialVersionUID = 1L;
            @Override
            public Predicate toPredicate(Root<Question> q, CriteriaQuery<?> query, CriteriaBuilder cb){
                query.distinct(true); //중복 제거
                Join<Question, SiteUser> u1 = q.join("author", JoinType.LEFT);
                Join<Question, Answer> a = q.join("answerList", JoinType.LEFT);
                Join<Answer, SiteUser> u2 = a.join("author", JoinType.LEFT);
                return cb.or(cb.like(q.get("subject"), "%" + kw + "%"), //제목
                    cb.like(q.get("content"), "%" + kw + "%"), //내용
                    cb.like(u1.get("username"), "%" + kw + "%"), //질문 작성자
                    cb.like(a.get("content"), "%" + kw + "%"), // 답변 내용
                    cb.like(u2.get("username"), "%" + kw + "%")); // 답변 작성자
            }
        };
    }

    public Page<Question> getList(int page, String kw){
        //가장 최근에 작성한 게시물이 먼저 보이게
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        Specification<Question> spec = search(kw);
        return this.questionRepository.findAll(spec, pageable);
    }

    public Question getQuestion(Integer id){
        Optional<Question> question = this.questionRepository.findById(id);
        if(question.isPresent()){
            return question.get();
        } else {
            throw new DataNotFoundException("question not found");
        }
    }

    public void create(String subject, String content, SiteUser user){
        Question q = new Question();
        q.setSubject(subject);
        q.setContent(content);
        q.setCreateDate(LocalDateTime.now());
        q.setAuthor(user);
        this.questionRepository.save(q);
    }

    public void modify(Question question, String subject, String content){
        question.setSubject(subject);
        question.setContent(content);
        question.setModifyDate(LocalDateTime.now());
        this.questionRepository.save(question);
    }

    public void delete(Question question){
        this.questionRepository.delete(question);
    }

    public void vote(Question question, SiteUser siteUser){
        question.getVoter().add(siteUser);
        this.questionRepository.save(question);
    }
}
