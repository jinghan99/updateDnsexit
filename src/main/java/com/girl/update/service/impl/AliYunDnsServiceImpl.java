package com.girl.update.service.impl;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.alidns.model.v20150109.DescribeDomainRecordsRequest;
import com.aliyuncs.alidns.model.v20150109.DescribeDomainRecordsResponse;
import com.aliyuncs.alidns.model.v20150109.DescribeDomainsRequest;
import com.aliyuncs.alidns.model.v20150109.DescribeDomainsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.girl.update.constant.ApiConstant;
import com.girl.update.pojo.Aliyun;
import com.girl.update.service.AliYunDnsService;
import com.girl.update.utils.IpUtils;
import com.girl.update.utils.ali.AliDdnsUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private ApiConstant apiConstant;

    @Autowired
    private DefaultAcsClient defaultAcsClient;



    /**
     * 设置域名参数
     *
     * @param request
     */
    public void setParam(DescribeDomainRecordsRequest request) {

        request.putQueryParameter("DomainName", apiConstant.getDomain());
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
            response = defaultAcsClient.getAcsResponse(request);

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
                analysisDns(yun);
                logger.info("域名更换ip结束：{}",newIp);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("域名更换异常：{}",newIp);
        }
    }

    /**
     * 获取 阿里 配置
     * @return
     */
    @Override
    public DescribeDomainRecordsResponse.Record getAli() {

        // 获取解析的数据
        String actionName = "DescribeDomainRecords";
        DescribeDomainRecordsResponse response;
        // 获取request
        DescribeDomainRecordsRequest request = AliDdnsUtils.getRequestQuery(actionName);
        // 设置request参数
        setParam(request);
        try {

            response = defaultAcsClient.getAcsResponse(request);

            // 获取阿里云的数据
            List<DescribeDomainRecordsResponse.Record> list = response.getDomainRecords();
            if (list == null || list.isEmpty()) {
                return null;
            }

            for (DescribeDomainRecordsResponse.Record domain: list) {
                return domain;
            }

        } catch (ClientException e) {
            logger.error("获取 阿里 配置 ：",e);
        }

        return null;
    }


    /**
     * 设置参数
     *
     * @param request
     */
    public void setParam(DescribeDomainsRequest request, Aliyun yun) {
        // 设置参数
        request.putQueryParameter("RecordId", yun.getRecordId());
        request.putQueryParameter("RR", yun.getRr());
        request.putQueryParameter("Type", yun.getType());
        request.putQueryParameter("Value", yun.getIpV4());
        request.putQueryParameter("TTL", yun.getTTL());
    }

    /**
     *  更新 ip
     */
    public void analysisDns(Aliyun yun) {
        String actionName = "UpdateDomainRecord";
        DescribeDomainsRequest request = AliDdnsUtils.getRequest(actionName);
        DescribeDomainsResponse response;
        setParam(request, yun);
        try {
            response = defaultAcsClient.getAcsResponse(request);
            List<DescribeDomainsResponse.Domain> list = response.getDomains();
            for (DescribeDomainsResponse.Domain domain : list) {
                System.out.println(domain.getDomainName());
                logger.info(domain.getDomainName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
