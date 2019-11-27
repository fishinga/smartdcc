package com.attiot.railAnaly.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import sun.misc.BASE64Decoder;

import javax.imageio.stream.FileImageOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by zhangbf on 2019/4/18.
 */
@Slf4j
public class ImageUtils {

    public static boolean generateImageFromStr(String contextPath,String filename,String fileContents) {
        boolean flag = true;
        try {
            initParentFolder(contextPath);
            buff2Image(fileContents,contextPath+filename);
        }catch (IOException e) {
            flag = false;
            log.error("系统内部异常", e);

        }
        return flag;
    }

    public static void initParentFolder(String parentFolder) {
        List list = new ArrayList();
        File folder = new File(parentFolder);
        list.add(folder);
        while(folder.getParentFile()!=null) {
            list.add(folder.getParentFile());
            folder = folder.getParentFile();
        }
        for(int i=list.size()-1;i>=0;i--) {
            File file = (File)list.get(i);
            if(!file.exists()) {
                boolean isMkdir = file.mkdir();
            }
        }
    }

    public static void buff2Image(String base64,String tagSrc) throws IOException
    {
        BASE64Decoder decoder = new sun.misc.BASE64Decoder();

        byte[] b = decoder.decodeBuffer(base64);
        FileImageOutputStream fout = new FileImageOutputStream(new File(tagSrc));
        //将字节写入文件
        fout.write(b,0,b.length);
        fout.close();
    }

//    public static void main(String args[]) {
//        Long filenum = Calendar.getInstance().getTimeInMillis();
//        for(int i=0;i<100;i++) {
//            System.out.println((filenum+i)+"");
//        }
//    }

}
