package com.ds.rosetta.service;

import com.ds.rosetta.common.entity.dto.SSHConfigDTO;

/**
 * SSH服务接口
 * 定义SSH连接和命令执行的业务接口。
 * 该接口提供了与远程服务器进行SSH交互的基本功能，包括：
 * 1. 远程命令执行
 * 2. SSH连接测试
 * 
 * 实现类需要处理：
 * - SSH连接的建立和关闭
 * - 命令执行的异常处理
 * - 会话（Session）的生命周期管理
 * - 执行结果的处理和转换
 *
 * @author january
 * @since 1.0.0
 */
public interface SSHService {
    
    /**
     * 执行远程命令
     * 在指定的远程服务器上执行Shell命令，并返回执行结果。
     * 
     * 该方法会：
     * 1. 建立与远程服务器的SSH连接
     * 2. 执行指定的命令
     * 3. 获取命令执行结果
     * 4. 关闭SSH连接
     * 
     * 注意事项：
     * - 确保提供的SSH配置信息正确
     * - 命令执行可能需要较长时间，请合理设置超时时间
     * - 建议对敏感命令进行权限控制
     *
     * @param sshConfigDTO SSH连接配置，包含主机地址、端口、用户名、密码等信息
     * @param command 要执行的Shell命令
     * @return 命令执行的输出结果字符串
     * @throws RuntimeException 当SSH连接失败或命令执行出错时抛出
     */
    String executeRemoteCommand(SSHConfigDTO sshConfigDTO, String command);
    
    /**
     * 测试SSH连接
     * 验证是否能够成功连接到指定的远程服务器。
     * 
     * 该方法会：
     * 1. 尝试建立SSH连接
     * 2. 验证连接是否成功
     * 3. 关闭测试连接
     * 
     * 使用场景：
     * - 在执行实际操作前验证SSH配置是否正确
     * - 检查目标服务器是否可访问
     * - 验证认证信息是否有效
     *
     * @param sshConfigDTO SSH连接配置，包含主机地址、端口、用户名、密码等信息
     * @return 如果连接成功返回true，否则返回false
     */
    boolean testConnection(SSHConfigDTO sshConfigDTO);
} 