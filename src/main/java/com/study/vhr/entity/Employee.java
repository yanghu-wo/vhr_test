package com.study.vhr.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @ClassName: Employee
 * @Author: LiuYun
 * @Description:
 * @Data: Create in 20:44 2022/9/1
 */
@Data
@Setter
@Getter
public class Employee {
    private int id;
    private String name;
    private String sex;
    private Date birthday;
    private String idCard;  // 身份证号
    private String wedlock; //婚姻状态
    private String nationId; // 民族
    private String nativePlace; // 户籍
    private String politicId; // 政治面貌
    private String email;
    private String phone;
    private String address;  //现居地址
    private int departmentId;  // 部门id
    private int jobLevelId; // 职称id
    private int posId; // 职位id
    private String engageForm; // 聘用方式 ，劳务合同、派遣合同
    private String eduction; //学历
    private String major; //专业，例如计算机、医学
    private String school;
    private Date beginDate; //入职时间


}
