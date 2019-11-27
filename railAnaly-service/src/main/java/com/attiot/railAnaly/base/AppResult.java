package com.attiot.railAnaly.base;

import com.alibaba.fastjson.JSONObject;
import com.attiot.railAnaly.common.util.StringUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringEscapeUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/12/26.
 */
@Slf4j
@Data
public class AppResult {
    private Integer rtn=1;

    private String msg = "成功!";

    private List DataList;

    private Object DataModel;

    private Date datetime = Calendar.getInstance().getTime();

    private int version;//版本号

    private String url;//更新地址

    private boolean success = true;

    public String getJsonStr() {
        JSONObject obj = new JSONObject();
        obj.put("success", success);
        obj.put("msg", this.getMsg());
        obj.put("obj", this.getDataList());
        obj.put("version", this.getVersion());
        return obj.toJSONString();
    }

    public void writer(HttpServletRequest request , HttpServletResponse response){
        try{
            /*response.setHeader("Content-type", "text/html;charset=UTF-8");*/
            response.setHeader("Set-Cookie","cookiename=cookievalue; path=/; Domain=domainvaule; Max-age=seconds; HttpOnly");
            response.setCharacterEncoding("UTF-8");
            String successCallback = request.getParameter("jsonp");
            StringEscapeUtils.escapeHtml(successCallback);
             if(StringUtil.isNotEmpty(successCallback)){
                response.getWriter().print(successCallback + "("+getJsonStr()+")");
            }else {
                response.getWriter().print(getJsonStr());
            }
        }catch (IOException e){
            log.error("系统内部异常", e);
        }
    }

    public void writerWithoutJsonp(HttpServletRequest request , HttpServletResponse response){
        try{
            /*response.setHeader("Content-type", "text/html;charset=UTF-8");*/
            response.setCharacterEncoding("UTF-8");
            response.getWriter().print(getJsonStr());
        }catch (IOException e){
            log.error("系统内部异常", e);
        }
    }

}
