package com.plm.activiti.util;

import java.io.File;
import java.io.FileOutputStream;

public class FileUtil {

    /**
     * 将字节流转换成文件
     *
     * @param filename
     * @param data
     * @throws Exception
     */
    public static void saveFile(String filename, byte[] data) throws Exception {
        if (data != null) {
            String filepath = "D:\\installkaifa\\Project\\ideaPriject\\activiti\\src\\main\\resources\\processes\\" + filename;
            File file = new File(filepath);
            if (file.exists()) {
                file.delete();
            }
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(data, 0, data.length);
            fos.flush();
            fos.close();
        }
    }
}
