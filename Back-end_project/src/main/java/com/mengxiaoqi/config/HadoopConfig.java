package com.mengxiaoqi.config;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;


import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@org.springframework.context.annotation.Configuration
public class HadoopConfig {

    @Value("${hadoop.fs.uri}")
    private String hdfsUri;  // 从 application.properties 中读取 HDFS URI

    @Value("${hadoop.fs.username}")
    private String hdfsUser;  // 从 application.properties 中读取 HDFS 用户名

    @Value("${hadoop.yarn.resourcemanager.address}")
    private String yarnResourceManager;  // 从 application.properties 中读取 YARN ResourceManager 地址

    @Value("${hadoop.mapreduce.framework.name}")
    private String mapReduceFrameworkName;  // 从 application.properties 中读取 MapReduce 框架类型


    // 创建一个 Bean，用于初始化 Hadoop 文件系统
    @Bean
    public FileSystem fileSystem() throws IOException, URISyntaxException {
        // 创建 Hadoop 配置对象
        Configuration configuration = new Configuration();

        // 加载配置
        configuration.addResource("core-site.xml");
        configuration.addResource("hdfs-site.xml");
        configuration.addResource("mapred-site.xml");
        configuration.addResource("yarn-site.xml");

        // 通过 application.properties 中的配置设置 HDFS URI 和用户名
        configuration.set("fs.defaultFS", hdfsUri);  // 配置 HDFS 地址
        configuration.set("hadoop.job.ugi", hdfsUser);  // 配置 HDFS 用户名
        configuration.set("mapreduce.framework.name", yarnResourceManager);
        configuration.set("yarn.resourcemanager.address", mapReduceFrameworkName);


        // 初始化 Hadoop 的文件系统
        URI hdfsURI = new URI(hdfsUri);  // 获取 HDFS URI
        return FileSystem.get(hdfsURI, configuration);
    }
}