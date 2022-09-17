package com.study.vhr.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @ClassName: Hr
 * @Author: LiuYun
 * @Description:
 * @Data: Create in 21:06 2022/9/1
 */
@Data
@Setter
@Getter
public class Hr {
    private int id;
    private String name;
    private String phone;
    private String telephone;
    private String address;
    private int enabled;
    private String username;
    private String password;
    private String userface; // 头像
    private String remark;
}
