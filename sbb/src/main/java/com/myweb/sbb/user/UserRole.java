package com.myweb.sbb.user;

import lombok.Getter;

//사용자에게 부여할 권한
@Getter
public enum UserRole {
    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER");

    UserRole(String value){
        this.value = value;
    }

    private String value;

}
