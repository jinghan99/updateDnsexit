package com.girl.update.task;

import com.girl.update.utils.HttpRequest;
import com.girl.update.utils.IpUtils;
import com.girl.update.utils.PropertiesUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @Package com.girl.update.task
 * @Description: 更新
 * @author: jingh
 * @date 2019/1/25 10:09
 */
@Component
public class DnsTask {

    private final static Logger logger  = LoggerFactory.getLogger(DnsTask.class);

    /**
     * 配置信息 由config.properties 修改
     */
    private static String baseUrl = "http://update.dnsexit.com/";

    private static String url = "http://update.dnsexit.com/RemoteUpdate.sv";

    private static String domain = "jinghan.club";

    private static String ip = "118.112.111.231";


    public  String getBaseUrl() {
        return baseUrl;
    }

    public  String getUrl() {
        return url;
    }

    public  String getDomain() {
        return domain;
    }

    public  String getIp() {
        return ip;
    }

    /**
     * 手动更新
     * @param newDomain
     */
    public void refresh(String newDomain,String newIp){
        if(StringUtils.isNotBlank(newDomain)){
            domain = newDomain;
        }
        Map<String, String> map = HttpRequest.doGetToMap(PropertiesUtils.getInstance().get("dns_txt"));
        baseUrl = map.get("base");
        url = map.get("url");
        if (checkUrl(baseUrl)) {
            updateIp(newIp);
        }
    }



    /**
     * 获取信息
     * 每小时执行
     */
    @Scheduled(cron = "0 0 0/1 * * ? ")
    public void getUpdateInfo() {
        logger.info("每小时执行一次！");
        Map<String, String> map = HttpRequest.doGetToMap(PropertiesUtils.getInstance().get("dns_txt"));
        baseUrl = map.get("base");
        url = map.get("url");
        if (checkUrl(baseUrl)) {
            updateIp(null);
        }
    }

    /**
     * 检验 账户
     *
     * @param checkurl
     * @return
     */
    public boolean checkUrl(String checkurl) {
        String urlCheck = "ipupdate/account_validate.jsp?login=userName&password=pwd";
        urlCheck = urlCheck.replace("userName", PropertiesUtils.getInstance().get("update_user"));
        urlCheck = urlCheck.replace("pwd", PropertiesUtils.getInstance().get("update_ip_pwd"));
        String str = HttpRequest.doGet(checkurl + urlCheck);
        str = Pattern.compile("\t|\r|\n| ").matcher(str).replaceAll("");
        if ((str != null) && str.equals("0=OK")) {
            return true;
        }
        return false;
    }

    /**
     * 更新域名 与 ip 的关系
     */
    public void updateIp(String newIp) {
        String outerIp = "";
        String userInfo = "?login=userName&password=pwd&host=domain&myip=ipv4&force=Y";
        userInfo = userInfo.replace("userName", PropertiesUtils.getInstance().get("update_user"));
        userInfo = userInfo.replace("pwd", PropertiesUtils.getInstance().get("update_ip_pwd"));
        userInfo = userInfo.replace("domain", domain);
//        判断是手动 还是自动
        if(StringUtils.isNotBlank(newIp)){
            outerIp = newIp;
        }else {
            outerIp = IpUtils.getIpv4Ip();
        }

        if(!outerIp.equals(ip)){
            ip = outerIp;
            userInfo = userInfo.replace("ipv4",outerIp );
            String s = HttpRequest.doGet(url + userInfo);
            logger.info("更新域名 {}",s);
        }else{
            logger.info("无需更新");
        }
    }



    public static void main(String[] args){
        DnsTask dnsTask = new DnsTask();
        dnsTask.getUpdateInfo();
    }
}


