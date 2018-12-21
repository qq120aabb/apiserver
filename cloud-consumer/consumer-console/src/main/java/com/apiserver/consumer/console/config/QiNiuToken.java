package com.apiserver.consumer.console.config;

import com.qiniu.common.Zone;
import com.qiniu.storage.Configuration;
import com.qiniu.util.Auth;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author jarvis
 */
@Component
public class QiNiuToken {

//    @Value("${qiniu_accessKey}")
    private String accessKey;

//    @Value("${qiniu_secretKey}")
    private String secretKey;

//    @Value("${qiniu_bucket}")
    private String bucket;

    private String upToken;

    public String getUpToken() {
        return upToken;
    }

    public void setUpToken(String upToken) {
        this.upToken = upToken;
    }

    public String getQiniuToken(){
        Configuration cfg = new Configuration(Zone.zone0());
        Auth auth = Auth.create(accessKey, secretKey);
        this.upToken = auth.uploadToken(bucket);
        return upToken;
    }


}
