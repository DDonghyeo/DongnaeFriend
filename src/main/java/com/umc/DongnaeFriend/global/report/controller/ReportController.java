package com.umc.DongnaeFriend.global.report.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/accuse")
public class ReportController {

    public ResponseEntity<?> reportBoard(@RequestBody int id) {
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
