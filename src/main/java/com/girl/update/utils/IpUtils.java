package com.girl.update.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * @author jinghan
 * @title: IpUtils
 * @projectName update-dns
 * @description: TODO
 * @date 2019/8/13 18:22
 */
public class IpUtils {


    /**
     * 亚马逊提供
     * 获取外网地址 IPV4
     *
     * @return
     */
    public static String getIpv4Ip() {
        BufferedReader br = null;
        try {
            URL url = new URL("http://checkip.amazonaws.com");
            br = new BufferedReader(new InputStreamReader(url.openStream()));
            String outerIp = br.readLine();
            return outerIp;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return "";
    }
}
