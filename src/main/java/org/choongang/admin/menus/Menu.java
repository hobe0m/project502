package org.choongang.admin.menus;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Menu {
    private final static Map<String, List<MenuDetail>> menus;

    static {
        menus = new HashMap<>();
        menus.put("member", Arrays.asList(
                new MenuDetail("list", "회원 목록", "/admin/member"),
                new MenuDetail("authority", "회원 권한", "/admin/member/authority")
        ));

        // 상품관리
        menus.put("product", Arrays.asList(
                new MenuDetail("list", "상품 목록", "/admin/product"),
                new MenuDetail("add", "상품 등록", "/admin/product/add"),
                new MenuDetail("category", "상품 분류", "/admin/product/category")
        ));

        // 주문관리
        menus.put("order", Arrays.asList(
                new MenuDetail("list", "주문 목록", "/admin/order")
        ));

        menus.put("board", Arrays.asList(
                new MenuDetail("list", "게시판 목록", "/admin/board"),
                new MenuDetail("add", "게시판 등록", "/admin/board/add"),
                new MenuDetail("posts", "게시글 관리", "/admin/board/posts")
        ));

        menus.put("reservation", Arrays.asList(
                new MenuDetail("list", "예약 현황", "/admin/reservation"),
                new MenuDetail("branch", "지점 목록", "/admin/reservation/branch"),
                new MenuDetail("add_branch", "지점 등록", "/admin/reservation/add_branch"),
                new MenuDetail("holiday", "휴무일 관리", "/admin/reservation/holiday")
        ));
    }

    public static List<MenuDetail> getMenus(String code) {
        return menus.get(code);
    }
}