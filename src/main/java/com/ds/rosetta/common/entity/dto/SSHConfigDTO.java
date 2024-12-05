package com.ds.rosetta.common.entity.dto;

import lombok.Data;

/**
 * SSH连接配置数据传输对象
 * 用于存储SSH连接所需的配置信息
 *
 * @author january
 * @since 1.0.0
 */
@Data
public class SSHConfigDTO {
    /**
     * SSH服务器主机地址
     */
    private String host;
    
    /**
     * SSH服务器端口号，默认为22
     */
    private int port;
    
    /**
     * SSH登录用户名
     */
    private String username;
    
    /**
     * SSH登录密码
     */
    private String password;
    
    /**
     * SSH私钥文件路径（使用密钥认证时需要）
     */
    private String privateKeyPath;
} 