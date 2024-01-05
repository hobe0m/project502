package org.choongang.file.controllers;

import org.choongang.commons.ExceptionRestProcessor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/file")
public class ApiFileController implements ExceptionRestProcessor {

    @PostMapping
    public void upload(@RequestParam("file") MultipartFile[] files,
                       @RequestParam(name = "gid", required = false) String gid,
                       // required = false : 값이 없으면 임의로 하나 생성
                       @RequestParam(name = "location", required = false) String location) {

    }
}
