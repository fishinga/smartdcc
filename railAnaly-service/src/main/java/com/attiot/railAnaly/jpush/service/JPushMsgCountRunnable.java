package com.attiot.railAnaly.jpush.service;

import com.attiot.railAnaly.borrow.dao.ABorrowListDetailDao;
import com.attiot.railAnaly.common.ConstantValue;
import com.attiot.railAnaly.goods.dao.ABorrowGoodsDao;
import com.attiot.railAnaly.point.dao.APointPleaseDao;
import com.attiot.railAnaly.task.dao.ATaskListDao;
import com.attiot.railAnaly.workflow.dao.AWorkflowDataDao;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2018/8/31.
 */
public class JPushMsgCountRunnable implements Runnable{

    private AWorkflowDataDao aWorkflowDataDao;

    private ATaskListDao aTaskListDao;

    private APointPleaseDao aPointPleaseDao;

    private ABorrowGoodsDao aBorrowGoodsDao;

    private ABorrowListDetailDao aBorrowListDetailDao;

    private Map<String,Map> msgCountMap;
    private String msgtype;
    private String userId;
    public JPushMsgCountRunnable(AWorkflowDataDao aWorkflowDataDao,ATaskListDao aTaskListDao,APointPleaseDao aPointPleaseDao,ABorrowGoodsDao aBorrowGoodsDao,ABorrowListDetailDao aBorrowListDetailDao,Map map,String msgtype,String userId) {
        this.aWorkflowDataDao = aWorkflowDataDao;
        this.aTaskListDao = aTaskListDao;
        this.aPointPleaseDao = aPointPleaseDao;
        this.aBorrowGoodsDao = aBorrowGoodsDao;
        this.aBorrowListDetailDao=aBorrowListDetailDao;
        this.msgCountMap = map;
        this.msgtype = msgtype;
        this.userId = userId;
    }
    public void run() {
        String[] array = msgtype.split(",");
        Map map = this.getUserMsgCount(userId);
        for(int i=0;i<array.length;i++) {
            if(ConstantValue.PUSH_MSG_DAIBANG.equals(array[i])) {//待办:待办数量
                Long count = aWorkflowDataDao.getWaitToDoCountByUserId(userId);
                map.put(ConstantValue.PUSH_MSG_DAIBANG,count);

            }else if(ConstantValue.PUSH_MSG_GUIHUANG.equals(array[i])) {//归还,显示可交接数量
                Map params = new HashMap();
                params.put("borrower",userId);
                Long count = aBorrowListDetailDao.getBorrowReturnCount(params);
                map.put(ConstantValue.PUSH_MSG_GUIHUANG,count);


            }else if(ConstantValue.PUSH_MSG_GUAPAI.equals(array[i])) {//挂牌,显示挂牌列表里的总数量
                Map param = new HashMap();
                param.put("category","1001");
                param.put("userId",userId);
                Long count = aBorrowGoodsDao.getGoodsCountByUser(param);
                map.put(ConstantValue.PUSH_MSG_GUAPAI,count);

            }else if(ConstantValue.PUSH_MSG_DUANDIAN.equals(array[i])) {//销点/断送电,可销点数+不可销点数
                Long count = aPointPleaseDao.queryUnfinishCount(userId);
                map.put(ConstantValue.PUSH_MSG_DUANDIAN,count);

            }else if(ConstantValue.PUSH_MSG_GONGDAN.equals(array[i])) {//工单,显示未完成的任务数量
                Long count = aTaskListDao.getUnfinishTaskCountByUserId(userId);
                map.put(ConstantValue.PUSH_MSG_GONGDAN,count);
            }
        }
        msgCountMap.put(userId,map);
    }

    public Map getUserMsgCount(String userId) {
        Map map = msgCountMap.get(userId);
        if(null == map) {
            map = new HashMap();
            map.put(ConstantValue.PUSH_MSG_GUIHUANG,getBorrowReturnCount(userId));
            map.put(ConstantValue.PUSH_MSG_DAIBANG,getWaitToDoCountByUserId(userId));
            map.put(ConstantValue.PUSH_MSG_DUANDIAN,getUnfinishTaskCountByUserId(userId));
            map.put(ConstantValue.PUSH_MSG_GONGDAN,getUnfinishTaskCountByUserId(userId));
            map.put(ConstantValue.PUSH_MSG_GUAPAI,getGoodsCountByUser(userId));
            msgCountMap.put(userId,map);
        }
        return map;
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
