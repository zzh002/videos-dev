package com.zzh.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author ZZH
 * @date 2019/1/23 9:31
 **/
@ConfigurationProperties(prefix = "projectUrl")
@Component
public class ProjectUrlConfig {

    public String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
