package com.girl.update.service;

/**
 * @author jinghan
 * @title: AliYunDnsService
 * @projectName update-dns
 * @description:  阿里云 动态解析 ip
 * @date 2019/8/13 17:11
 */
public interface AliYunDnsService {
    /**
     * 更新ip
     */
    void analysisAliDns(String newIp);
}