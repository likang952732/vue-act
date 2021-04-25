package com.plm.activiti.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Util {

    public static String getBusKey(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date();
        return sdf.format(date);
    }
}
