package com.attiot.railAnaly.foundation;

import lombok.extern.apachecommons.CommonsLog;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * 功能/模块：<br/>
 * 类描述：<br/>
 * 修订历史：：<br/>
 * 日期  作者  参考  描述：<br/>
 *
 * @author dengsc
 * @version 1.0 17-9-18
 * @see
 */
@CommonsLog
public class FileDownloadUtil {

    public static void downloadInputStream(String range, String fileName, long fileLength, InputStream inputStream, String contentType, HttpServletResponse response, Map<String, String> headers) throws IOException {
        response.reset();
        response.setStatus(HttpServletResponse.SC_OK);
//        if (headers != null) {
//            for (Map.Entry<String, String> entry : headers.entrySet()) {
//                response.setHeader(entry.getKey(), entry.getValue());
//            }
//        }
        //告诉客户端允许断点续传多线程连接下载 响应的格式是: Accept-Ranges: bytes
        //response.setHeader("Accept-Ranges", "bytes");
        response.setDateHeader("Last-Modified", 0);

        response.setHeader("Age", "30");
        long startNum = 0;
        long endNum = fileLength - 1;
        //文件大小
        // response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);
        if (StringUtils.isNotBlank(range)) {
            FileDownloadUtil.log.debug(range);
            //如果是下载文件的范围而不是全部,向客户端声明支持并开始文件块下载
            //要设置状态 响应的格式是:HTTP/1.1 206 Partial Content
            int i = 0;
            for (String s : range.replaceAll("bytes=", "").split("-")) {
                i++;
                if (StringUtils.isBlank(s)) {
                    continue;
                }
                if (i == 1) {
                    startNum = Long.valueOf(s);
                } else if (i == 2) {
                    endNum = Long.valueOf(s) > endNum ? endNum : Long.valueOf(s);
                }
            }
        }
        //下载的文件(或块)长度
        //响应的格式是: Content-Length: [文件的总大小] - [客户端请求的下载的文件块的开始字节]
        long contentLength = endNum + 1 - startNum;
        response.setContentLength(Long.valueOf(contentLength).intValue());
        //不是从最开始下载, 响应的格式是:Content-Range: bytes [文件块的开始字节]-[请求的总大小 - 1]/[文件的总大小]
        StringBuilder builder = new StringBuilder("bytes ");
        builder.append(startNum)
                .append("-")
                .append("")
                .append(endNum)
                .append("/")
                .append(fileLength);
        response.setHeader("Content-Range", builder.toString());

        //如果有此句话不能用 IE 直接下载
        //response.setHeader("Connection", "Close");

        //使客户端直接下载 响应的格式是:Content-Type: application/octet-stream
        response.setContentType(contentType);

        //为客户端下载指定默认的下载文件名称 响应的格式是:
        //Content-Disposition: attachment;filename="[文件名]"
        String fn = removeSpecialChar(UTF8URLEncode(fileName));
        response.setHeader("Content-Disposition", "attachment;filename=\"" + fn + "\";");
        long skipLong = inputStream.skip(startNum);
        ServletOutputStream outputStream = response.getOutputStream();
        long count = 0;
        try {
            byte[] buffer = new byte[4096];
            int n;
            while (-1 != (n = inputStream.read(buffer))) {
                FileDownloadUtil.log.debug(count);
                count += n;
                if (count > contentLength) {
                    outputStream.write(buffer, 0, Long.valueOf(count - contentLength).intValue());
                    break;
                } else {
                    outputStream.write(buffer, 0, n);
                }
            }
        } catch (IOException e) {
            FileDownloadUtil.log.error("download inputStream error", e);
        } finally {
//            IOUtils.closeQuietly(outputStream);
//            IOUtils.closeQuietly(inputStream);
            if (outputStream != null) {
                try {
                    outputStream.close();
                }catch (IOException e){
                    log.error("error",e);
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                }catch (IOException e){
                    log.error("error",e);
                }
            }
        }
    }


    public static void downloadInputStream(String range, String fileName, long fileLength, InputStream inputStream, String contentType, HttpServletResponse response) throws IOException {
        downloadInputStream(range, fileName, fileLength, inputStream, contentType, response, null);
    }

    public static void downloadInputStream(String contentType, InputStream inputStream, HttpServletResponse response) throws IOException {
        downloadInputStream(contentType, inputStream, response, 0);
    }

    public static void downloadInputStream(String contentType, InputStream inputStream, HttpServletResponse response, int contentLength) throws IOException {
        response.reset();
//        if (contentLength != 0) {
//            response.setContentLength(contentLength);
//        }
        ServletOutputStream outputStream = null;
        try {
            response.setDateHeader("Last-Modified", 0);
            response.setContentType(contentType);
            outputStream = response.getOutputStream();
            IOUtils.copy(inputStream, outputStream);
            outputStream.flush();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                }catch (IOException e){
                    log.error("error",e);
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                }catch (IOException e){
                    log.error("error",e);
                }
            }
        }
    }


    /**
     * 仅对中文字符进行转义
     *
     * @param text 被转义的字符串
     * @return 转义后的字符串
     * @throws java.io.UnsupportedEncodingException
     *          异常
     */
    private static String UTF8URLEncode(String text) throws UnsupportedEncodingException {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c >= 0 && c <= 255) {
                builder.append(c);
            } else {
                byte[] b = Character.toString(c).getBytes("UTF-8");
                for (byte aB : b) {
                    int k = aB;
                    if (k < 0) {
                        k += 256;
                    }
                    builder.append("%").append(Integer.toHexString(k).toUpperCase());
                }
            }
        }
        return builder.toString();
    }


    private static String removeSpecialChar(String str) {
        if (str == null || str.length()<1) {
            return str;
        }
        return str.replaceAll("[/^:/\\\\/?\"<>|*]*", "");
    }

}
