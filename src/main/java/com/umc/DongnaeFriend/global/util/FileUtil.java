package com.umc.DongnaeFriend.global.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Slf4j
public class FileUtil {

    public static void fileUpload(MultipartFile file, String fileName) throws IOException{
        String filePath = "/Users/soobin/UMC/Server/src/main/resources/static/img/";

        log.info("fileupload 들어옴!!");
        File dest = new File(filePath + fileName);

        log.info("dest : " + dest);
        file.transferTo(dest);
    }
}
