package com.ds.rosetta.controller;

import com.ds.rosetta.common.entity.dto.SSHConfigDTO;
import com.ds.rosetta.service.SSHService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * SSH控制器
 * 提供SSH操作相关的REST API接口。
 * 
 * 主要功能：
 * 1. 提供SSH连接测试接口
 * 2. 提供远程命令执行接口
 * 
 * API说明：
 * - 所有接口都以 /api/ssh 为基础路径
 * - 所有请求都使用POST方法，确保数据安全性
 * - 返回统一使用ResponseEntity封装，提供更好的HTTP响应控制
 * 
 * 安全考虑：
 * - 需要进行适当的访问控制和认证
 * - 敏感信息（如密码）应在传输过程中加密
 * - 应限制可执行的命令范围
 *
 * @author january
 * @since 1.0.0
 */
@RestController
@RequestMapping("/api/ssh")
public class SSHController {
    
    /**
     * SSH服务接口，提供SSH连接和命令执行的核心功能
     */
    @Autowired
    private SSHService sshService;
    
    /**
     * 测试SSH连接
     * 验证是否能够成功连接到指定的远程服务器。
     * 
     * 请求示例：
     * POST /api/ssh/test
     * {
     *     "host": "192.168.1.100",
     *     "port": 22,
     *     "username": "root",
     *     "password": "password"
     * }
     * 
     * 响应示例：
     * - 成功：true
     * - 失败：false
     * 
     * 注意事项：
     * - 建议在执行实际操作前先调用此接口测试连接
     * - 连接失败不会抛出异常，而是返回false
     * - 每次调用都会建立新的连接，完成后自动断开
     *
     * @param sshConfigDTO SSH连接配置，包含连接所需的所有信息
     * @return ResponseEntity<Boolean> 包装的连接测试结果
     */
    @PostMapping("/test")
    public ResponseEntity<Boolean> testConnection(@RequestBody SSHConfigDTO sshConfigDTO) {
        // 调用服务层测试连接
        boolean connected = sshService.testConnection(sshConfigDTO);
        // 返回测试结果
        return ResponseEntity.ok(connected);
    }
    
    /**
     * 执行SSH远程命令
     * 在指定的远程服务器上执行Shell命令。
     * 
     * 请求示例：
     * POST /api/ssh/execute?command=ls%20-la
     * {
     *     "host": "192.168.1.100",
     *     "port": 22,
     *     "username": "root",
     *     "password": "password"
     * }
     * 
     * 响应示例：
     * {
     *     "total 128"
     *     "drwxr-xr-x  2 root root  4096 Mar 15 10:30 ."
     *     "drwxr-xr-x 22 root root  4096 Mar 15 10:29 .."
     *     // ...
     * }
     * 
     * 注意事项：
     * - 命令执行是同步的，可能需要较长时间
     * - 命令的执行结果可能包含多行文本
     * - 某些命令可能需要特殊权限
     * - 建议对命令进行安全性验证
     *
     * @param sshConfigDTO SSH连接配置，包含连接所需的所有信息
     * @param command 要执行的Shell命令
     * @return ResponseEntity<String> 包装的命令执行结果
     * @throws RuntimeException 当命令执行失败时抛出
     */
    @PostMapping("/execute")
    public ResponseEntity<String> executeCommand(
            @RequestBody SSHConfigDTO sshConfigDTO,
            @RequestParam String command) {
        // 执行远程命令并获取结果
        String result = sshService.executeRemoteCommand(sshConfigDTO, command);
        // 返回执行结果
        return ResponseEntity.ok(result);
    }
} 