package edu.nf.demo.common;

import io.minio.MinioClient;
import org.springframework.beans.factory.FactoryBean;

// 创建MinIO的API对象与初始化桶
// 直接使用Spring的FactoryBean，传入Minio的API对象，表示这个MinIO工厂产出的所有MinIO的API对象均被IOC收容
public class MinioFactoryBean implements FactoryBean<MinioOptions> {
    /**
     * 文件服务器连接url
     */
    private String host;
    /**
     * 账号
     */
    private String account;
    /**
     * 密码
     */
    private String password;
    /**
     * 桶名称
     */
    private String bucketName;

    // 设置方法
    public void setHost(String host) {
        this.host = host;
    }
    public void setAccount(String account) {
        this.account = account;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    // 获取Class对象
    @Override
    public Class<?> getObjectType() {
        return MinioOptions.class;
    }

    /**
     * 初始化MinIO的API对象并纳入IOC容器管理
     * @return
     * @throws Exception
     */
    @Override
    public MinioOptions getObject() throws Exception {
        //创建MinIO的API对象
        MinioClient client = MinioClient.builder() // 创建一个MinIO的API对象的构造器对象
                .endpoint(host) // MinIO服务的访问地址
                .credentials(account, password) // MinIO的服务的访问账号与密码
                .build(); // 执行
        //将client封装到MinioOptions中
        return new MinioOptions(client, bucketName);
    }

}