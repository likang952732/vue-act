package com.plm.activiti;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ActivitiApplicationTests {

    @Test
    public void contextLoads() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date();
        System.out.println(sdf.format(date));
    }

}
