package com.ds.rosetta.service.impl;

import com.ds.rosetta.common.entity.dto.SSHConfigDTO;
import com.ds.rosetta.service.SSHService;
import com.ds.rosetta.utils.SSHUtil;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * SSH服务实现类
 * 实现SSH连接和命令执行的具体业务逻辑。
 * 
 * 主要功能：
 * 1. 管理SSH会话的生命周期
 * 2. 处理SSH连接和命令执行的异常
 * 3. 确保资源的正确释放
 * 
 * 实现说明：
 * - 使用JSch库实现SSH连接
 * - 采用Spring的依赖注入管理组件
 * - 使用try-finally确保会话正确关闭
 * - 统一的异常处理和日志记录
 *
 * @author january
 * @since 1.0.0
 */
@Slf4j
@Service
public class SSHServiceImpl implements SSHService {
    
    /**
     * SSH工具类，提供底层的SSH操作功能
     */
    @Autowired
    private SSHUtil sshUtil;
    
    /**
     * {@inheritDoc}
     * 
     * 实现说明：
     * 1. 创建新的SSH会话
     * 2. 执行指定的命令
     * 3. 捕获可能的异常并转换为RuntimeException
     * 4. 确保在finally块中关闭会话
     * 
     * 异常处理：
     * - JSchException: SSH连接相关异常
     * - IOException: 命令执行IO异常
     * 以上异常都会被包装为RuntimeException并重新抛出
     */
    @Override
    public String executeRemoteCommand(SSHConfigDTO sshConfigDTO, String command) {
        // 声明会话变量，确保在finally块中能够访问到
        Session session = null;
        try {
            // 创建新的SSH会话
            session = sshUtil.createSession(sshConfigDTO);
            // 执行命令并返回结果
            return sshUtil.executeCommand(session, command);
        } catch (JSchException | IOException e) {
            // 记录错误日志
            log.error("执行远程命令失败，主机: {}，命令: {}", sshConfigDTO.getHost(), command, e);
            // 将检查异常转换为运行时异常
            throw new RuntimeException("执行远程命令失败: " + e.getMessage());
        } finally {
            // 确保会话被正确关闭
            sshUtil.closeSession(session);
        }
    }
    
    /**
     * {@inheritDoc}
     * 
     * 实现说明：
     * 1. 尝试建立SSH连接
     * 2. 验证连接状态
     * 3. 安全关闭连接
     * 
     * 特殊处理：
     * - 即使连接失败也不抛出异常，而是返回false
     * - 确保在所有情况下都能正确关闭会话
     * - 记录详细的连接失败日志
     */
    @Override
    public boolean testConnection(SSHConfigDTO sshConfigDTO) {
        // 声明会话变量
        Session session = null;
        try {
            // 尝试建立SSH连接
            session = sshUtil.createSession(sshConfigDTO);
            // 返回连接状态
            return session.isConnected();
        } catch (JSchException e) {
            // 记录连接失败的详细日志
            log.error("测试SSH连接失败，主机: {}, 端口: {}", 
                    sshConfigDTO.getHost(), 
                    sshConfigDTO.getPort(), 
                    e);
            // 连接失败返回false
            return false;
        } finally {
            // 确保会话被关闭
            sshUtil.closeSession(session);
        }
    }
} 