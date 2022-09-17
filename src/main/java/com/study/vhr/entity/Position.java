package com.study.vhr.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @ClassName: Position
 * @Author: LiuYun
 * @Description: 技术岗位
 * @Data: Create in 20:56 2022/9/1
 */
@Data
@Setter
@Getter
public class Position {
    private int id;
    private String name;
    private Date createDate;
    private int enabled;
}
