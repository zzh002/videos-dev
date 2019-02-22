package com.zzh.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author ZZH
 * @date 2019/1/23 9:31
 **/
@ConfigurationProperties(prefix = "projectConfig")
@Component
public class ProjectConfig {

    public String url;

    public String wxspAppid;

    public String wxspSecret;

    public String getWxspAppid() {
        return wxspAppid;
    }

    public void setWxspAppid(String wxspAppid) {
        this.wxspAppid = wxspAppid;
    }

    public String getWxspSecret() {
        return wxspSecret;
    }

    public void setWxspSecret(String wxspSecret) {
        this.wxspSecret = wxspSecret;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
