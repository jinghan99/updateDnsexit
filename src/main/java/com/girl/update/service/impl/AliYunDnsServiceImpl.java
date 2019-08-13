package com.girl.update.service.impl;

import com.aliyuncs.alidns.model.v20150109.DescribeDomainRecordsRequest;
import com.aliyuncs.alidns.model.v20150109.DescribeDomainRecordsResponse;
import com.girl.update.pojo.Aliyun;
import com.girl.update.service.AliYunDnsService;
import com.girl.update.utils.IpUtils;
import com.girl.update.utils.ali.AliDdnsUtils;
import com.girl.update.utils.ali.DemoListDomains;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author jinghan
 * @title: AliYunDnsServiceImpl
 * @projectName update-dns
 * @description: 阿里云 动态解析 ip
 * @date 2019/8/13 17:11
 */
@Service
public class AliYunDnsServiceImpl implements AliYunDnsService {

    private Logger logger = LoggerFactory.getLogger(AliYunDnsServiceImpl.class);

    /**
     * 设置域名参数
     *
     * @param request
     */
    public void setParam(DescribeDomainRecordsRequest request) {

        request.putQueryParameter("DomainName", "yangfan.cloud");
    }


    /**
     * 更新 域名 ip 绑定
     */
    @Override
    public void analysisAliDns(String newIp) {

        if(StringUtils.isEmpty(newIp)){
            newIp = IpUtils.getIpv4Ip();
        }

        // 获取解析的数据
        String actionName = "DescribeDomainRecords";
        DescribeDomainRecordsResponse response;
        // 获取request
        DescribeDomainRecordsRequest request = AliDdnsUtils.getRequestQuery(actionName);
        // 设置request参数
        setParam(request);
        try {
            response = AliDdnsUtils.getClient().getAcsResponse(request);
            // 声明解析对象
            DemoListDomains demo = new DemoListDomains();
            // 获取阿里云的数据
            List<DescribeDomainRecordsResponse.Record> list = response.getDomainRecords();
            if (list == null || list.isEmpty()) {
                return;
            }

            //更新ip
            DescribeDomainRecordsResponse.Record record = list.get(0);

            // 进行判定记录是否需要更新
            if (record.getValue().equals(newIp)) {
                logger.info("当前域名解析地址为：{}不需要更新！",newIp);
                System.out.println();
            }else {
                logger.info("更新域名：{}",newIp);
                Aliyun yun = new Aliyun();
                // 进行替换关键数据
                yun.setIpV4(newIp);
                yun.setRecordId(record.getRecordId());
                yun.setRr(record.getRR());
                yun.setTTL(record.getTTL());
                yun.setType(record.getType());
                logger.info("域名更换ip开始：{}",newIp);
                System.out.println("域名更换ip开始");
                demo.analysisDns(yun);
                logger.info("域名更换ip结束：{}",newIp);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("域名更换异常：{}",newIp);
        }
    }

}
