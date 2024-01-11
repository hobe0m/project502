package org.choongang.admin.product.controllers;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RequestCategory {

    @NotBlank
    private String cateCd; // 분류코드

    @NotBlank
    private String cateNm; // 분류명
    private int listOrder; // 진열 가중치 - 내림 차순 정렬 우선 순위, 숫자가 클수록 먼저 노출
    private boolean active; // 사용 여부
}
