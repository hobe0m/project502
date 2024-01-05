package org.choongang.admin.config.controllers;

import lombok.Data;

@Data
public class BasicConfig {
    private String siteTitle = "";
    private String siteDescription = "";
    private String siteKeywords = "";
    private int cssJsVersion = 1;
    private String joinTerms = "";
    // 썸네일 추가
    private String thumbSize = "";

}
