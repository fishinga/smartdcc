package com.attiot.railAnaly.jpush.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.attiot.railAnaly.borrow.dao.ABorrowListDetailDao;
import com.attiot.railAnaly.borrow.entity.ABorrowListDetail;
import com.attiot.railAnaly.common.ConstantValue;
import com.attiot.railAnaly.common.JpushClientUtil;
import com.attiot.railAnaly.goods.dao.ABorrowGoodsDao;
import com.attiot.railAnaly.point.dao.APointPleaseDao;
import com.attiot.railAnaly.task.dao.ATaskListDao;
import com.attiot.railAnaly.workflow.dao.AWorkflowDataDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by zhangbf on 2018/8/20.
 */
@Slf4j
@Service(value="MyJPushService")
public class JPushService {
    @Autowired
    private AWorkflowDataDao aWorkflowDataDao;
    @Autowired
    private ATaskListDao aTaskListDao;
    @Autowired
    private APointPleaseDao aPointPleaseDao;
    @Autowired
    private ABorrowGoodsDao aBorrowGoodsDao;
    @Autowired
    private ABorrowListDetailDao aBorrowListDetailDao;
    private static Map<String,Map> msgCountMap = new HashMap();
    private ExecutorService threadPool = Executors.newFixedThreadPool(10);

    /***
     * 推送消息
     * @param msgtype 消息类型
     * @param userId 用户id
     * @param  msg 消息
     */
    public void sendMessage(String msgtype,String userId,String msg) {
        try {
            threadPool.execute(new JPushMsgCountRunnable( aWorkflowDataDao, aTaskListDao, aPointPleaseDao, aBorrowGoodsDao, aBorrowListDetailDao,msgCountMap, msgtype, userId));
//            JpushClientUtil.getInstance().pushByAlias(userId,msg,msgarray.toString());
        }catch(IllegalArgumentException e) {
            log.error("系统内部异常", e);
        }
    }

    public Map getUserMsgCount(String userId) {
        Map map = msgCountMap.get(userId);
        if(null == map) {
            map = new HashMap();
            map.put(ConstantValue.PUSH_MSG_GUIHUANG,getBorrowReturnCount(userId));
            map.put(ConstantValue.PUSH_MSG_DAIBANG,getWaitToDoCountByUserId(userId));
            map.put(ConstantValue.PUSH_MSG_DUANDIAN,this.getUnfinishPointPleaseCount(userId));
            map.put(ConstantValue.PUSH_MSG_GONGDAN,getUnfinishTaskCountByUserId(userId));
            map.put(ConstantValue.PUSH_MSG_GUAPAI,getGoodsCountByUser(userId));
            msgCountMap.put(userId,map);
        }
        return map;
    }

    public void clearUserMsg(String userId) {
        msgCountMap.put(userId,null);
    }

    public long getWaitToDoCountByUserId(String userId) {
        return aWorkflowDataDao.getWaitToDoCountByUserId(userId);
    }
    /***
     * 可归还工具数
     * @param userId
     * @return
     */
    public long getBorrowReturnCount(String userId) {
        Map params = new HashMap();
        params.put("borrower",userId);
        Long count = aBorrowListDetailDao.getBorrowReturnCount(params);
        return count;
    }

    //借用作业牌
    public long getGoodsCountByUser(String userId) {
        Map param = new HashMap();
        param.put("category","1001");
        param.put("userId",userId);
        return aBorrowGoodsDao.getGoodsCountByUser(param);
    }

    //未完成请点
    public long getUnfinishPointPleaseCount(String usrId) {
        return aPointPleaseDao.queryUnfinishCount(usrId);
    }

    //取未完成工单
    public long getUnfinishTaskCountByUserId(String userId) {
        return aTaskListDao.getUnfinishTaskCountByUserId(userId);
    }


}
