package com.girl.update.config;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.girl.update.constant.ApiConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @Package com.girl.update.config
 * @Description: TODO
 * @author: jingh
 * @date 2019/8/13 22:58
 */
@Component
public class AliConfig {

    @Autowired
    private ApiConstant apiConstant;


    @Bean
    public DefaultAcsClient defaultAcsClient() {
        //必填固定值，必须为“cn-hanghou”
        String regionId = "cn-hangzhou";
        IClientProfile profile = DefaultProfile.getProfile(regionId, apiConstant.getAccessKeyId(), apiConstant.getAccessKeySecret());
        return new DefaultAcsClient(profile);
    }
}
