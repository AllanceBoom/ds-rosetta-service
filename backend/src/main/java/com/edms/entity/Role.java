package com.edms.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 角色实体类
 * 
 * @author EDMS Team
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "roles")
public class Role extends BaseEntity {
    
    @NotBlank(message = "角色名称不能为空")
    @Size(max = 50, message = "角色名称长度不能超过50个字符")
    @Column(name = "name", unique = true, nullable = false, length = 50)
    private String name;
    
    @Size(max = 255, message = "角色描述长度不能超过255个字符")
    @Column(name = "description")
    private String description;
    
    @Column(name = "enabled", nullable = false)
    private Boolean enabled = true;
}