package com.attiot.railAnaly.base.controller;


import com.alibaba.fastjson.JSONObject;
import com.attiot.railAnaly.base.AppResult;
import com.attiot.railAnaly.base.entity.*;
import com.attiot.railAnaly.base.param.TSUserOrgQueryParam;
import com.attiot.railAnaly.base.service.*;
import com.attiot.railAnaly.common.ConstantValue;
import com.attiot.railAnaly.foundation.ParamVerifyUtil;
import com.attiot.railAnaly.foundation.exception.AppException;
import com.attiot.railAnaly.foundation.exception.ErrorInfo;
import com.attiot.railAnaly.goods.entity.PointList;
import com.attiot.railAnaly.goods.service.PointListService;
import com.attiot.railAnaly.jpush.service.JPushService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.util.*;
import java.util.List;

@RestController
@Slf4j
@RequestMapping(value = "base/BaseUtilController")
public class BaseUtilController {
    @Autowired
    private TSBaseUserService tsUserBaseService;
    @Autowired
    private TSTypeService tsTypeService;
    @Autowired
    private TSUserOrgService userOrgService;
    @Autowired
    private TSDepartService departService;
    @Autowired
    private TSLogService logService;
    @Autowired
    private TSRoleService roleService;
    @Autowired
    private PointListService pointListService;
    @Autowired
    private JPushService jpushService;
    @Value("${server_path}")
    private String serverPath;

    @RequestMapping(value = "/userLogin")
    public void customerLogin(HttpServletRequest request , HttpServletResponse response) {
        AppResult rtn = new AppResult();
        try {
           // log.info("serverpath:"+serverPath);
            Map<String, Object> _tempMap = new HashMap<>();
            String userName = ServletRequestUtils.getStringParameter(request, "userName", null);

            String pwd = ServletRequestUtils.getStringParameter(request, "pwd", null);
            ParamVerifyUtil.verifyNotBlank(userName);
            ParamVerifyUtil.verifyNotBlank(pwd);
            String userId = null;
            //查询用户信息
            TSBaseUser tsBaseUser = tsUserBaseService.checkUserExits(userName, pwd);
            if (tsBaseUser == null) {
                throw new AppException(ErrorInfo.USER_NAME_NOT_EXIST);
            }
            TSUserOrgQueryParam param = new TSUserOrgQueryParam();
            param.setUserId(tsBaseUser.getId());
            TSUserOrg userOrg = userOrgService.getByParam(param);
            if(userOrg==null) {
                throw new AppException(ErrorInfo.USER_UNUSUAL);
            }
            TSDepart tsDepart = null;
            if(null!=userOrg){
                tsDepart = departService.getById(userOrg.getOrgId());
                _tempMap.put("dep", tsDepart);
            }
            if (tsBaseUser.isLocked()) {
                throw new AppException(ErrorInfo.USER_LOCKED);
            }
            //根据用户查询roleCode
//            TSRole role = roleService.getRoleCode(tsBaseUser.getId());
//            if(null!=role){
//                _tempMap.put("roleCode", role.getRolecode());
//            }
            List<TSRole> roleList = roleService.getByUserId(tsBaseUser.getId());
            if(roleList.size()>0){
                StringBuilder ro = new StringBuilder("");
                for(TSRole r:roleList){
                    if(ro.length()<1){
                      ro.append(r.getRolecode());
                    }else {
                        ro.append(",").append(r.getRolecode());
                    }
                }
                _tempMap.put("roleCode", ro.toString());
            }
            // 登录日志
            TSLog tsLog = new TSLog();
            tsLog.setLogcontent(tsBaseUser.getUsername() + "登录成功！");
            tsLog.setLoglevel(1);
            tsLog.setOperatetype(1);
            tsLog.setOperatetime(new Date());
            tsLog.setUserid(tsBaseUser.getId());
            logService.insert(tsLog);
            jpushService.clearUserMsg(tsBaseUser.getId());
            Map map = jpushService.getUserMsgCount(tsBaseUser.getId());
            _tempMap.put(ConstantValue.PUSH_MSG_GUIHUANG,map.get(ConstantValue.PUSH_MSG_GUIHUANG));
            _tempMap.put(ConstantValue.PUSH_MSG_DAIBANG,map.get(ConstantValue.PUSH_MSG_DAIBANG));
            _tempMap.put(ConstantValue.PUSH_MSG_DUANDIAN,map.get(ConstantValue.PUSH_MSG_DUANDIAN));
            _tempMap.put(ConstantValue.PUSH_MSG_GONGDAN,map.get(ConstantValue.PUSH_MSG_GONGDAN));
            _tempMap.put(ConstantValue.PUSH_MSG_GUAPAI,map.get(ConstantValue.PUSH_MSG_GUAPAI));


            _tempMap.put("accessToken",tsBaseUser.getId());
            _tempMap.put("user", tsBaseUser);
            
            boolean isNeedTask = false;
            String orgId = userOrg.getOrgId();
            TSDepart depart =  departService.getById(orgId);
            if(depart!=null&&depart.getDepartType()<3) {
            	isNeedTask=true;
            }
            _tempMap.put("isNeedTask", isNeedTask);
            log.info("app user login:" + tsBaseUser.getUsername());
            List lst = new ArrayList();
            lst.add(_tempMap);
            rtn.setDataList(lst);
            rtn.setSuccess(true);
        }catch(IllegalArgumentException e) {
            log.error("系统内部异常", e);
            rtn.setSuccess(false);
            rtn.setMsg("登录信息出错了！");
        }
        rtn.writer(request,response);
    }

    @RequestMapping(value = "/initData")
    public void initData(HttpServletRequest request , HttpServletResponse response) {
        AppResult rtn = new AppResult();
        try {
            String userId = request.getParameter("accessToken");
            Map map = jpushService.getUserMsgCount(userId);
//            log.info("serverpath:"+serverPath);
            List lst = new ArrayList();
            lst.add(map);
            rtn.setDataList(lst);
            rtn.setSuccess(true);
        }catch(IllegalArgumentException e) {
            log.error("系统内部异常", e);
            rtn.setSuccess(false);
            rtn.setMsg("登录信息出错了！");
        }
        rtn.writer(request,response);
    }

    /***
     * 清空推送数据
     * @param request
     * @param response
     */
    @RequestMapping(value = "/clearPushData")
    public void clearUserData(HttpServletRequest request , HttpServletResponse response) {
        AppResult rtn = new AppResult();
        try {
            String userId = request.getParameter("accessToken");
            jpushService.clearUserMsg(userId);
            Map map = new HashMap();
            List lst = new ArrayList();
            map.put("sucess",true);
            lst.add(map);
            rtn.setDataList(lst);
            rtn.setSuccess(true);
        }catch(IllegalArgumentException e) {
            log.error("系统内部异常", e);
            rtn.setSuccess(false);
            rtn.setMsg("清空用户推送信息失败");
        }
        rtn.writer(request,response);
    }

    /***
     * 下载数据字典
     * @param request
     * @param response
     */
    @RequestMapping(value = "/loadDataDict")
    public void loadDataDict(HttpServletRequest request , HttpServletResponse response) {
        AppResult rtn = new AppResult();
        try {
            String userId = request.getParameter("accessToken");
            String typeGroupCode = request.getParameter("typeGroupCode");//
            if(typeGroupCode == null){
                rtn.setSuccess(false);
                rtn.setMsg("参数错误");
                rtn.writer(request,response);
                return;
            }
            String[] groupCodeArray = typeGroupCode.split(",");
            List dataList = new ArrayList();
            for(int i=0;i<groupCodeArray.length;i++) {
                List<TSType> typeList = tsTypeService.getByTypeeGroupCode(groupCodeArray[i]);
                List lst = new ArrayList();
                if(null != typeList && typeList.size()>0) {
                    for(TSType record:typeList) {
                        Map map = new HashMap();
                        map.put("text",record.getTypename());
                        map.put("value",record.getTypecode());
                        lst.add(map);
                    }
                }
                Map groupMap = new HashMap();
                groupMap.put(groupCodeArray[i],lst);
                dataList.add(groupMap);
            }

            rtn.setDataList(dataList);
            rtn.setSuccess(true);
        }catch(IllegalArgumentException e) {
            log.error("系统内部异常", e);
            rtn.setSuccess(false);
            rtn.setMsg("登录信息出错了！");
        }
        rtn.writer(request,response);
    }

    /****
     * 加载所有列车
     * @param request
     * @param response
     */
    @RequestMapping(value = "/loadTrainData")
    public void loadTrainData(HttpServletRequest request , HttpServletResponse response) {
        AppResult rtn = new AppResult();
        try {
            String userId = request.getParameter("accessToken");
            List<PointList> list = pointListService.getAllParentPointList();
            List dataList = new ArrayList();
            Map _map = new HashMap();
            List rows = new ArrayList();
            for(PointList record:list) {
                Map m = new HashMap();
                m.put("text",record.getTrainNo());
                m.put("value",record.getTrainNo());
                rows.add(m);
            }

            _map.put("trains",rows);
            dataList.add(_map);
            rtn.setDataList(dataList);
            rtn.setSuccess(true);
        }catch(IllegalArgumentException e) {
            log.error("系统内部异常", e);
            rtn.setSuccess(false);
            rtn.setMsg("登录信息出错了！");
        }
        rtn.writer(request,response);
    }
}
