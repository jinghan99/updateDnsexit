package com.girl.update.controller;

import com.aliyuncs.alidns.model.v20150109.DescribeDomainRecordsResponse;
import com.aliyuncs.alidns.model.v20150109.DescribeDomainsResponse;
import com.girl.update.service.AliYunDnsService;
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
 * @Description: 主页
 * @author: jingh
 * @date 2019/1/25 15:41
 */
@Controller
public class IndexController {

    private final static Logger logger = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    private DnsTask dnsTask;

    @Autowired
    private AliYunDnsService aliYunDnsService;

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


    @GetMapping("/ali")
    public String ali(){
        return "ali.html";
    }



    /**
     * 手动更新
     */
    @PostMapping("/ali/refresh")
    @ResponseBody
    public String ali_refresh(String newIp){
        aliYunDnsService.analysisAliDns(newIp);
        return "ok";
    }

    /**
     * 阿里 配置获取
     * @return
     */
    @GetMapping("/ali/get")
    @ResponseBody
    public DescribeDomainRecordsResponse.Record aliGet(){
        return  aliYunDnsService.getAli();
    }
}
