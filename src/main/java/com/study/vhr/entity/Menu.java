package com.study.vhr.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @ClassName: Menu
 * @Author: LiuYun
 * @Description:
 * @Data: Create in 10:49 2022/9/3
 */
@Data
@Setter
@Getter
@AllArgsConstructor
public class Menu implements Serializable {
    private int id;
    private String url;
    private String path;
    private String component;
    private String name;
    private String iconCls;
    private short KeepAlive;
    private short requireAuth;
    private int parentId;
    private short enabled;

}
