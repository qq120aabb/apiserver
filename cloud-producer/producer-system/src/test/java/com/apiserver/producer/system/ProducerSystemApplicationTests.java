package com.apiserver.producer.system;

import com.apiserver.kernel.utils.SnowFlake;
import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProducerSystemApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Test
    public void GeneratorUserId(){

        SnowFlake snowFlake=new SnowFlake(2,3);
        for (int i = 0; i < 10; i++) {
            System.out.println(snowFlake.nextId());
        }
    }

    @Test
    public void GetTimeOut(){
        Config config = ConfigService.getAppConfig();
        Integer defaultRequestTimeout = 200;
        Integer requestTimeout = config.getIntProperty("timeout", defaultRequestTimeout);

        System.out.println(requestTimeout);
    }

}
