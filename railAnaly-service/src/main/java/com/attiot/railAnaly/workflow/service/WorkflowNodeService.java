package com.attiot.railAnaly.workflow.service;

import com.attiot.railAnaly.foundation.Page;
import com.attiot.railAnaly.workflow.dao.AWorkflowNodeDao;
import com.attiot.railAnaly.workflow.entity.AWorkflowNode;
import com.attiot.railAnaly.workflow.param.AWorkflowNodeQueryParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;


@Service
public class WorkflowNodeService {
    @Autowired
    private AWorkflowNodeDao aWorkflowNodeDao;

    /***
     * 根据主键查询
     * @param id
     * @return
     */
    public AWorkflowNode getById(String id){
        return  aWorkflowNodeDao.getById(id);
    }

    /***
     * 根据流程ID查询 节点信息
     * @param processId
     * @param ntype
     * @return
     */
    public AWorkflowNode getByProcessIdAndNtype(String processId,String ntype){
        return  aWorkflowNodeDao.getByProcessIdAndNtype(processId, ntype);
    }

    /**
     * 查询
     *
     * @param param 参数
     *
     * @author attiot
     * 2018-04-19 11:03:31
     */
    public Page<AWorkflowNode> query(AWorkflowNodeQueryParam param){
        Page<AWorkflowNode> page = new Page<>();
        page.setPageNo(param.getPageNo());
        page.setPageSize(param.getPageSize());
        page.setTotalNum(aWorkflowNodeDao.queryCount(param));
        if(page.isOverCount()){
            page.setResults(Collections.EMPTY_LIST);
        }else{
            page.setResults(aWorkflowNodeDao.query(param));
        }
        return page;
    }

}
