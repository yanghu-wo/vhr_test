package com.study.vhr.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @ClassName: Role
 * @Author: LiuYun
 * @Description: 角色
 * @Data: Create in 21:04 2022/9/1
 */
@Data
@Setter
@Getter
public class Role {
    private int id;
    private String name;    //英文名       ROLE_manager
    private String nameZh; // 中文名，例如 部门经理
}
