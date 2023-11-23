package com.myweb.sbb.question;

import com.myweb.sbb.answer.Answer;
import com.myweb.sbb.user.SiteUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity //Entity가 있어야 jpa가 엔티티로 인식
public class Question {

    @Id //id 속성을 기본키로 지정
    //generatedvalue : 따로 설정하지 않아도 저장할때마다 1씩 자동 증가
    //strategy : 고유번호를 생성하는 옵션, generationtype.identity는 해당 컬럼만의 독자적인 시퀀스를 생성
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 200)
    private String subject;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createDate;

    //질문 엔티티에서 답변 엔티티를 참조하기 위해 추가
    //질문 객체에서 답변을 참조하려면 question.getAnswerList()를 호출하면 됨
    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
    private List<Answer> answerList;

    @ManyToOne
    private SiteUser author;

    private LocalDateTime modifyDate;
}
