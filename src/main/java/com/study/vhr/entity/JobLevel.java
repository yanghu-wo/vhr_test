package com.study.vhr.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @ClassName: JobLevel
 * @Author: LiuYun
 * @Description: 工作职称
 * @Data: Create in 20:58 2022/9/1
 */
@Data
@Setter
@Getter
public class JobLevel {
    private int id;
    private String name;
    private String titleLevel; // 级别，如正高级、副高级
    private Date createDAte;
    private int enabled;
}
