package edu.nf.demo.controller;


import edu.nf.demo.common.MinioOptions;
import edu.nf.demo.vo.ResultVO;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

// MinIO控制器
@RestController
public class UploadController extends BaseController {
    // MinIO的API对象
    private MinioOptions options;

    // 构造器，初始化MinIO的API对象
    public UploadController(MinioOptions options) {
        this.options = options;
    }

    /**
     * 上传
     * @param path 文件路径（绑定URL中的{path}至方法参数path）
     * @param files 文件组
     * @return
     * @throws Exception
     */
    @PostMapping("/upload/{path}")
    public ResultVO upload(@PathVariable("path") String path,
                            @RequestParam("file") MultipartFile[] files) throws Exception{
        for(MultipartFile file : files) {
            //获取文件名
            String fileName = file.getOriginalFilename();
            //获取文件输入流
            InputStream input = file.getInputStream();
            //获取文件类型
            String contentType = file.getContentType();
            //获取文件大小
            long size = file.getSize();
            //上传文件到minio文件服务器
            options.upload(
                    path + "/" + fileName,
                    contentType,
                    size,
                    input);
        }
        return success();
    }
}