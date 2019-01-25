package com.girl.update.controller;

import com.girl.update.task.DnsTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

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
    @GetMapping("/refresh")
    public void update(String newDomain){
        dnsTask.refresh(newDomain);
    }
}
