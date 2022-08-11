package com.offcn.controller;

import com.offcn.entity.StatusCode;
import com.offcn.utils.*;
import com.offcn.entity.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/file")
public class UploadController {
    @Value("${FILE_SERVER_URL}")
    private String FILE_SERVER_URL;

    @PostMapping("/upload")
    public Result<String> upload(@RequestParam(value = "file") MultipartFile file){
        String originalFilename = file.getOriginalFilename();
        int index = originalFilename.lastIndexOf(".");
        try {
            FastDFSClient fastDFSClient = new FastDFSClient("classpath:fdfs_client.conf");
            String path = fastDFSClient.uploadFile(file.getBytes(), originalFilename.substring(index + 1));
            return new Result(true, StatusCode.OK,FILE_SERVER_URL+path);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,StatusCode.ERROR,"上传失败");
        }
    }
}
