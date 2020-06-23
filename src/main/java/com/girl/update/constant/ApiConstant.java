package com.girl.update.constant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Package com.girl.update.constant
 * @Description: 常量
 * @author: jingh
 * @date 2019/8/13 22:10
 */
@Component
public class ApiConstant {


    @Value("${api.AccessKeyId}")
    private String AccessKeyId;


    @Value("${api.AccessKeySecret}")
    private String AccessKeySecret;

    @Value("${api.domain}")
    private String domain;

    @Value("${api.prefixUrl}")
    private String prefixUrl;

    public String getAccessKeyId() {
        return AccessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        AccessKeyId = accessKeyId;
    }

    public String getAccessKeySecret() {
        return AccessKeySecret;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        AccessKeySecret = accessKeySecret;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getPrefixUrl() {
        return prefixUrl;
    }

    public void setPrefixUrl(String prefixUrl) {
        this.prefixUrl = prefixUrl;
    }
}
