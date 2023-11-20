package com.myweb.sbb;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

//해당 속성을 필요로 하는 생성자가 lombok에 의해 자동으로 생성.
@RequiredArgsConstructor
@Getter
public class HelloLombok {

    //final을 적용했기때문에 Setter는 의미가 없음.
    private final String hello;
    private final int lombok;

    public static void main(String[] args) {

        HelloLombok helloLombok = new HelloLombok("hello", 5);

        System.out.println(helloLombok.getHello());
        System.out.println(helloLombok.getLombok());
    }
}
