package com.myweb.sbb.question;

import org.springframework.data.jpa.repository.JpaRepository;

//jparepository를 상속할 때는 제네릭스 타입으로 리포지터리의 대상이 되는 엔티티의 타입과 해당 엔티티의 pk 속성을 지정해야함
public interface QuestionRepository extends JpaRepository<Question, Integer> {
}
