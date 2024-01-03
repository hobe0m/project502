package org.choongang.admin.menus;

// 값을 바꿀 일이 없어서 record 클래스로 사용
public record MenuDetail(
        String code, // 하위 메뉴 코드
        String name, // 메뉴명
        String url // 메뉴 URL
) {}
