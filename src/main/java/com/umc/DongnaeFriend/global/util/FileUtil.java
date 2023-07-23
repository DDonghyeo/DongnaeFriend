package com.umc.DongnaeFriend.global.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {

    public static String fileUpload(List<MultipartFile> files) throws IOException {
        String filepath = "/resources/static/img";

        List<String> list = new ArrayList<>();
        for (MultipartFile file : files) {
            String originalfileName = file.getOriginalFilename();
            File dest = new File(filepath + originalfileName);
            file.transferTo(dest);
            list.add(dest.getPath());
        }
        return String.join(",", list);
    }

}
