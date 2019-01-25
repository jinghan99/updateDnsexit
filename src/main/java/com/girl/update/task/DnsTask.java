package com.girl.update.task;

import com.girl.update.utils.HttpRequest;
import com.girl.update.utils.PropertiesUtils;

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
public class DnsTask {

    /**
     * 配置信息 由config.properties 修改
     */
    private static String baseUrl = "http://update.dnsexit.com/";

    private static String url = "http://update.dnsexit.com/RemoteUpdate.sv";


    /**
     * 获取信息
     */
    public void getUpdateInfo() {
        Map<String, String> map = HttpRequest.doGetToMap(PropertiesUtils.getInstance().get("dns_txt"));
        baseUrl = map.get("base");
        url = map.get("url");
        if(checkUrl(baseUrl)){
            updateIp();
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
    public void updateIp() {
        String userInfo = "?login=userName&password=pwd&host=domain&myip=ipv4&force=Y";
        userInfo = userInfo.replace("userName",PropertiesUtils.getInstance().get("update_user"));
        userInfo = userInfo.replace("pwd",PropertiesUtils.getInstance().get("update_ip_pwd"));
        userInfo = userInfo.replace("domain",PropertiesUtils.getInstance().get("update_domain"));
        userInfo = userInfo.replace("ipv4",getOuterIp());
        System.out.println(HttpRequest.doGet(url + userInfo));
    }


    /**
     * 亚马逊提供
     * 获取外网地址
     * @return
     */
    public static String getOuterIp(){
        BufferedReader br = null;
        try {
            URL url = new URL("http://checkip.amazonaws.com");
            br = new BufferedReader(new InputStreamReader(url.openStream()));
            String outerIp = br.readLine();
            return outerIp;
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(br != null){
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return "";
    }

    public static void main(String[] args) throws IOException {
        DnsTask dnsTask = new DnsTask();
        dnsTask.getUpdateInfo();
    }
}


