package com.myweb.sbb.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<SiteUser, Long> {
    //사용자 조회 기능
    Optional<SiteUser> findByusername(String username);
}
