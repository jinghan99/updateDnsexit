package com.girl.update.task;

import com.girl.update.service.AliYunDnsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


/**
 * @Package com.girl.update.task
 * @Description: TODO
 * @author: jingh
 * @date 2019/8/13 23:19
 */
@Component
public class AliTask {

    private Logger           logger = LoggerFactory.getLogger(AliTask.class);

    @Autowired
    private AliYunDnsService aliYunDnsService;


    /**
     * 定时刷新
     * 每分钟执行
     * 0 0/10 * * * ?
     */
    @Scheduled(cron = "0 0/1 * * * ? ")
    public void scheduled() {
        logger.info("每10分钟执行一次！");
        aliYunDnsService.analysisAliDns(null);
    }

}
