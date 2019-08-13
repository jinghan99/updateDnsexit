package com.girl.update.utils.ali;

import com.aliyuncs.alidns.model.v20150109.DescribeDomainRecordsRequest;
import com.aliyuncs.alidns.model.v20150109.DescribeDomainsRequest;
import com.aliyuncs.http.FormatType;

/**
 * 阿里云解析工具
 *
 * @author xiang
 */
public class AliDdnsUtils {


    /**
     * 获取一次inter连接，修改DNS
     */
    public static DescribeDomainsRequest getRequest(String actionName) {
        DescribeDomainsRequest request = new DescribeDomainsRequest();
        request.setAcceptFormat(FormatType.JSON); // 指定api返回格式
        // 设置请求action
        request.setActionName(actionName);
        return request;
    }

    /**
     * 获取一次inter连接,查询dns
     */
    public static DescribeDomainRecordsRequest getRequestQuery(String actionName) {
        DescribeDomainRecordsRequest request = new DescribeDomainRecordsRequest();
        request.setAcceptFormat(FormatType.JSON); // 指定api返回格式
        // 设置请求action
        request.setActionName(actionName);
        return request;
    }

}
