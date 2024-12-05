package com.ds.rosetta.utils;

import com.ds.rosetta.common.entity.dto.SSHConfigDTO;
import com.jcraft.jsch.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * SSH连接工具类
 * 提供SSH连接、命令执行等基础功能
 *
 * @author january
 * @since 1.0.0
 */
@Slf4j
@Component
public class SSHUtil {
    
    /**
     * 创建SSH会话
     *
     * @param sshConfigDTO SSH连接配置信息
     * @return SSH会话对象
     * @throws JSchException SSH连接异常
     */
    public Session createSession(SSHConfigDTO sshConfigDTO) throws JSchException {
        JSch jsch = new JSch();
        
        // 如果使用密钥认证
        if (sshConfigDTO.getPrivateKeyPath() != null) {
            jsch.addIdentity(sshConfigDTO.getPrivateKeyPath());
        }
        
        Session session = jsch.getSession(
            sshConfigDTO.getUsername(),
            sshConfigDTO.getHost(),
            sshConfigDTO.getPort()
        );
        
        // 如果使用密码认证
        if (sshConfigDTO.getPassword() != null) {
            session.setPassword(sshConfigDTO.getPassword());
        }
        
        // 设置 SSH 连接配置
        java.util.Properties config = new java.util.Properties();
        config.put("StrictHostKeyChecking", "no");
        session.setConfig(config);
        
        // 建立连接
        session.connect();
        return session;
    }
    
    /**
     * 执行远程命令
     *
     * @param session SSH会话对象
     * @param command 要执行的命令
     * @return 命令执行结果
     * @throws JSchException SSH连接异常
     * @throws IOException IO异常
     */
    public String executeCommand(Session session, String command) throws JSchException, IOException {
        StringBuilder result = new StringBuilder();
        ChannelExec channel = null;
        
        try {
            channel = (ChannelExec) session.openChannel("exec");
            channel.setCommand(command);
            
            InputStream in = channel.getInputStream();
            channel.connect();
            
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line).append("\n");
            }
            
        } finally {
            if (channel != null) {
                channel.disconnect();
            }
        }
        
        return result.toString();
    }
    
    /**
     * 关闭SSH会话
     *
     * @param session 要关闭的SSH会话对象
     */
    public void closeSession(Session session) {
        if (session != null && session.isConnected()) {
            session.disconnect();
        }
    }
} 