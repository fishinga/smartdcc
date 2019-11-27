package com.attiot.railAnaly.point.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.attiot.railAnaly.base.dao.TSBaseUserDao;
import com.attiot.railAnaly.base.entity.TSBaseUser;
import com.attiot.railAnaly.borrow.service.BorrowListService;
import com.attiot.railAnaly.fault.dao.AFaultListDao;
import com.attiot.railAnaly.fault.entity.AFaultList;
import com.attiot.railAnaly.fault.service.FaultService;
import com.attiot.railAnaly.goods.dao.ABorrowGoodsDao;
import com.attiot.railAnaly.goods.dao.PointListGoodsDao;
import com.attiot.railAnaly.goods.entity.ABorrowGoods;
import com.attiot.railAnaly.goods.entity.PointListGoods;
import com.attiot.railAnaly.metro.dao.ATaskTableColumnsDao;
import com.attiot.railAnaly.metro.dao.ATaskTableDataDao;
import com.attiot.railAnaly.metro.entity.ATaskTableColumns;
import com.attiot.railAnaly.metro.entity.ATaskTableData;
import com.attiot.railAnaly.point.dao.APointPleaseForgeinDao;
import com.attiot.railAnaly.point.entity.APointPleaseForgein;
import com.attiot.railAnaly.workflow.dao.AWorkflowDataDao;
import com.attiot.railAnaly.workflow.entity.AWorkflowData;
import com.attiot.railAnaly.workflow.service.WorkflowDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by Administrator on 2018/6/3.
 */
@Service
public class ForgeinPointPleaseService {
    @Autowired
    private ATaskTableColumnsDao aTaskTableColumnsDao;
    @Autowired
    private ATaskTableDataDao aTaskTableDataDao;
    @Autowired
    private APointPleaseForgeinDao aPointPleaseForgeinDao;
    @Autowired
    private TSBaseUserDao tsBaseUserDao;
    @Autowired
    private PointListGoodsDao pointListGoodsDao;
    @Autowired
    private AWorkflowDataDao aWorkflowDataDao;
    @Autowired
    private ABorrowGoodsDao aBorrowGoodsDao;
    @Autowired
    private ForgeinPointPleaseService forgeinPointPleaseService;
    @Autowired
    private WorkflowDataService workflowDataService;
    @Autowired
    private BorrowListService borrowService;
    @Autowired
    private AFaultListDao faultListDao;


    public List<ATaskTableColumns> getTableColumnsByTableCode(String tablecode) {
        return aTaskTableColumnsDao.getTableColumnsByTableCode(tablecode);
    }


    public List<TSBaseUser> getCoWorkerByPointId(String ppointId) {
        APointPleaseForgein pleaseForgein = aPointPleaseForgeinDao.getById(ppointId);
        if(null!=pleaseForgein && pleaseForgein.getWorker()!=null && pleaseForgein.getWorker().length()>0) {
            return tsBaseUserDao.getByIds(pleaseForgein.getWorker());
        }
        return null;
    }

    public List<ATaskTableData>  getTableDataByPPointId(String ppointid) {
        return aTaskTableDataDao.getTableDataByPPointId(ppointid);
    }

    /***
     * 根据主键查询
     * @param id
     * @return
     */
    public APointPleaseForgein getById(String id){
        return  aPointPleaseForgeinDao.getById(id);
    }

    public List<PointListGoods> getPointListGoodsByTrainNo(String trainNo) {
        return pointListGoodsDao.getPointListGoodsByTrainNo(trainNo);
    }

    /**
     * 修改
     *
     * @param aPointPleaseForgein 参数
     *
     * @author attiot
     * 2018-05-30 11:05:36
     */
    public void update(APointPleaseForgein aPointPleaseForgein){
        aPointPleaseForgeinDao.update(aPointPleaseForgein);
    }


    public void insertATaskTableDataList(List<ATaskTableData> list) {
        if(null != list && list.size()>0) {
            ATaskTableData data = list.get(0);
            String ppointId = data.getPpointId();
            String taskTableId = data.getTaskTableId();
            Map params = new HashMap();
            params.put("ppointId",ppointId);
            params.put("taskTableId",taskTableId);
            aTaskTableDataDao.deleteByPpointIdAndTableId(params);
            aTaskTableDataDao.batchInsert(list);
        }

    }

    /***
     * 提交待办审批
     * @param _map
     */
    @Transactional
    public void submitWorkflow(Map _map) {
        String workflowId = String.valueOf(_map.get("workflowId"));//工作流id
        Integer auditState = Integer.valueOf(_map.get("auditState")+"");//审批通过与否
        String auditResults = String.valueOf(_map.get("auditResults"));//审批意见
        String accessToken = String.valueOf(_map.get("accessToken"));
        String orgId= String.valueOf(_map.get("departId"));
        JSONObject dataobj = new JSONObject();
        AWorkflowData workflow = aWorkflowDataDao.getById(workflowId);
        String processCode = workflow.getProcessCode();
        String nodeCode = workflow.getNodeCode();
        Integer runState = workflow.getRunState();

        if("4011".equals(processCode)  && 1==runState) {//外单位请点
            String sourceId = workflow.getSourceId();
            APointPleaseForgein forgein = aPointPleaseForgeinDao.getById(sourceId);
            if("401102".equals(nodeCode)) {//外单位请点，检调指派工班长
                String assignIds = null != _map.get("assignIds")?String.valueOf(_map.get("assignIds")):"";
                dataobj.put("assignIds",assignIds);
            }else if("401103".equals(nodeCode)) {//外单位请点，工班长指派作业人员
                String assignIds = null != _map.get("assignIds")?String.valueOf(_map.get("assignIds")):"";
                forgein.setWorker(assignIds);
                dataobj.put("assignIds",assignIds);
                List<AFaultList> faultList = faultListDao.getFaultByPointId(forgein.getId());
                if(faultList!=null&&faultList.size()>0){
                    for(AFaultList fault:faultList){
                        fault.setScheOperators(dataobj.get("assignIds")+"");
                        faultListDao.update(fault);
                    }
                }

            }else if("401104".equals(nodeCode)) {//外单位请点，配合人员借作业牌，需要保存到源记录表中
                String brands = null !=_map.get("brands")?String.valueOf(_map.get("brands")):"";
                dataobj.put("brands",brands);//借作业牌
                if(brands!=null && brands.length()>0) {
                    List<ABorrowGoods> brandList = aBorrowGoodsDao.getByIds(brands.split(","));
                    JSONArray array = new JSONArray();
                    String[] goodIds = new String[brandList.size()];
                    int idx = 0;
                    for (ABorrowGoods record : brandList) {
                        JSONObject obj = new JSONObject();
                        obj.put("id", record.getId());
                        obj.put("code", record.getCode());
                        obj.put("name", record.getName());
                        goodIds[idx++] = record.getId();
                        array.add(obj);
                    }
                    forgein.setBrands(array.toString());
                    if(1==auditState) {//审批通过
                        String remarks="配合外来人员["+workflow.getCreator()+"]作业,借用作业牌";
                        String jobContent = forgein.getJobContent();
                        borrowService.save(goodIds,accessToken,orgId,workflow.getSourceId(),jobContent,remarks);
                    }
                }

                //保存风险预控表
                String ppointId = workflow.getSourceId();
                String tableCode = String.valueOf(_map.get("tableCode"));
                String riskdata = String.valueOf(_map.get("riskdata"));
                JSONArray  riskarray = new JSONArray();
                riskarray = JSONArray.parseArray(riskdata);
                saveRiskTable( ppointId, tableCode, accessToken, riskarray);
            }
            aPointPleaseForgeinDao.update(forgein);
        }else if("4012".equals(processCode)  && 1==runState) {//外单位销点
            if("401202".equals(nodeCode)) {
                //保存风险预控表
                String ppointId = workflow.getSourceId();
                String tableCode = String.valueOf(_map.get("tableCode"));
                String riskdata = String.valueOf(_map.get("riskdata"));
                JSONArray  riskarray = new JSONArray();
                riskarray = JSONArray.parseArray(riskdata);
                saveRiskTable( ppointId, tableCode, accessToken, riskarray);
            }
        }
        //提交到工作流
        StringBuilder newData = new StringBuilder("");
        Iterator iterator = dataobj.keySet().iterator();
        while(iterator.hasNext()) {
            String key = (String)iterator.next();
            if(newData.length()>0) {
                newData.append("&");
            }
            newData.append(key).append("=").append(dataobj.get(key));
        }
        workflowDataService.submitWorkflowWorkflow(workflowId,auditState,auditResults,newData.toString(),accessToken);
    }

    private void saveRiskTable(String ppointId,String tableCode,String accessToken,JSONArray riskarray) {
        List<ATaskTableColumns> columnList = aTaskTableColumnsDao.getTableColumnsByTableCode(tableCode);
        if(null == columnList || columnList.size()<=0) {
            columnList = new ArrayList();
        }
        Map map = new HashMap();
        for(int i=0;i<riskarray.size();i++) {
            JSONObject json = riskarray.getJSONObject(i);
            map.put(json.get("id")+"",json.get("defaultVal"));
        }
        List<ATaskTableData> dataList = new ArrayList();
        for(ATaskTableColumns column:columnList) {
            ATaskTableData data = new ATaskTableData();
            data.setPpointId(ppointId);
            data.setTaskTableId(column.getTaskTableId());
            data.setColId(column.getId());
            data.setColCode(column.getColCode());
            data.setColName(column.getColName());
            data.setColValue(map.get(column.getId())+"");
            data.setCreator(accessToken);
            data.setModifor(accessToken);
            dataList.add(data);
        }
        if(null != dataList && dataList.size()>0) {
            forgeinPointPleaseService.insertATaskTableDataList(dataList);
        }
    }
}
