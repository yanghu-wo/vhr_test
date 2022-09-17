package com.study.vhr.controller;

import com.study.config.accnotation.UnInterception;
import com.study.vhr.entity.Hr;
import com.study.vhr.service.IHrService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName: HrController
 * @Author: LiuYun
 * @Description:
 * @Data: Create in 21:38 2022/9/1
 */
@RestController
@RequestMapping("/hr")
public class HrController {

    @Resource
    private IHrService hrService;

    @RequestMapping("/insert")
    public String insertHr(Hr hr){
        int isUpdate = hrService.updateHrInfo(hr);
        return String.valueOf(isUpdate);
    }

    @RequestMapping("/del")
    public String delHr(int id){
        int isDel = hrService.delHrById(id);
        if (isDel!=0){
            return "删除成功！！！";
        }
        return "删除失败！！";
    }

    @RequestMapping(value = "/find",produces="text/html;charset=UTF-8")
    @UnInterception
    public String findHr(int id){
        Hr hr = hrService.findOneHrById(id);
//        System.out.println(hr);
        return "成功";
    }

    @RequestMapping("/findAll")
    @ResponseBody
    public List<Hr> findAll(){
        List<Hr>  hrs = hrService.findAllHr();
        return hrs;
    }

    @RequestMapping("/update")
    public String updateHr(Hr hr){
        int isUpdate = hrService.updateHrInfo(hr);
        if (isUpdate!=0){
            return "修改成功！！！";
        }
        return "修改失败！！";
    }

}
