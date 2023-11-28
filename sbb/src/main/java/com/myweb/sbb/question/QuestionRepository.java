package com.myweb.sbb.question;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

//jparepository를 상속할 때는 제네릭스 타입으로 리포지터리의 대상이 되는 엔티티의 타입과 해당 엔티티의 pk 속성을 지정해야함
public interface QuestionRepository extends JpaRepository<Question, Integer> {
    Question findBySubject(String subject);
    Question findBySubjectAndContent(String subject, String content);
    List<Question> findBySubjectLike(String subject);
    Page<Question> findAll(Pageable pageable);
    Page<Question> findAll(Specification<Question> spec, Pageable pageable);

    //Specification 대신 어노테이션을 이용해 직접 쿼리를 작성하는 방법
    @Query("select " +
        "distinct q " +
        "from Question q " +
        "left outer join SiteUser u1 on q.author=u1 " +
        "left outer join Answer a on a.question=q " +
        "left outer join SiteUser u2 on a.author=u2 " +
        "where " +
        "   q.subject like %:kw% " +
        "   or q.content like %:kw% " +
        "   or u1.username like %:kw% " +
        "   or a.content like %:kw% " +
        "   or u2.username like %:kw% ")
    Page<Question> findAllByKeyword(@Param("kw") String kw, Pageable pageable);
}
