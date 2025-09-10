package edu.nf.demo.common;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;

import java.io.InputStream;

// MinIO的API对象
public class MinioOptions {
    // 客户端对象
    private MinioClient client;
    // 桶名称
    private String bucket;

    // 构造器
    public MinioOptions(MinioClient client, String bucket) {
        this.client = client;
        this.bucket = bucket;
        // 初始化桶
        initBucket();
    }

    // 初始化桶
    private void initBucket() {
        try {
            // 若桶不存在就创建一个桶
            if(!client.bucketExists(BucketExistsArgs.builder() // 创建一个专门用于检查桶是否存在的对象
                    .bucket(bucket) // 桶名
                    .build())){ // 执行
                client.makeBucket(MakeBucketArgs.builder() // 创建一个桶创建参数构造器对象
                        .bucket(bucket) // 桶名
                        .build()); // 执行
            }
        } catch (Exception e) {
            throw new RuntimeException("Create bucket fail.", e);
        }
    }

    /**
     * 上传文件
     * @param path 文件在桶中的路径
     * @param contentType 文件类型（MIME类型）
     * @param size 文件大小（字节）
     * @param input 文件输入流
     */
    public void upload(String path,
                       String contentType,
                       long size,
                       InputStream input) {
        try {
            // 上传文件至桶
            client.putObject(PutObjectArgs.builder() // 创建一个上传对象参数构造器
                    .bucket(bucket) // 桶名
                    .object(path) // 文件在桶中的路径
                    .contentType(contentType) // MIME文件类型
                    .stream(input, size, -1) // 文件输入流，文件大小，定义MinIO自动选择上传策略
                    .build());
        } catch (Exception e) {
            throw new RuntimeException("Upload fail.", e);
        }
    }
}