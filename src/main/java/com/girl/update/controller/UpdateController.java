package com.girl.update.controller;

import com.girl.update.task.DnsTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @Package com.girl.update.controller
 * @Description: TODO
 * @author: jingh
 * @date 2019/1/25 15:41
 */
@Controller
public class UpdateController {

    private final static Logger logger = LoggerFactory.getLogger(UpdateController.class);

    @Autowired
    private DnsTask dnsTask;


    /**
     * 手动更新
     */
    @GetMapping("")
    public String index(){
        return "index.html";
    }

    @GetMapping("/getInfo")
    @ResponseBody
    public Map getInfo(){
        Map map = new HashMap(5);
        map.put("ip",dnsTask.getIp());
        map.put("baseUrl",dnsTask.getBaseUrl());
        map.put("domain",dnsTask.getDomain());
        map.put("url",dnsTask.getUrl());
        return map;
    }

    /**
     * 手动更新
     */
    @PostMapping("/refresh")
    @ResponseBody
    public String update(String newDomain,String newIp){
        dnsTask.refresh(newDomain,newIp);
        return "ok";
    }


}
