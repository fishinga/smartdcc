package com.attiot.railAnaly.workflow.service;


import com.attiot.railAnaly.foundation.Page;
import com.attiot.railAnaly.workflow.dao.AWorkflowProcessDao;
import com.attiot.railAnaly.workflow.entity.AWorkflowProcess;
import com.attiot.railAnaly.workflow.param.AWorkflowProcessQueryParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class WorkflowProcessService {

    @Autowired
    private AWorkflowProcessDao aWorkflowProcessDao;

    /***
     * 根据主键查询
     * @param id
     * @return
     */
    public AWorkflowProcess getById(String id){
        return  aWorkflowProcessDao.getById(id);
    }

    /***
     * 根据编号查询
     * @param processCode
     * @return
     */
    public AWorkflowProcess getByProcessCode(String processCode){
        return  aWorkflowProcessDao.getByProcessCode(processCode);
    }

    /**
     * 查询
     *
     * @param param 参数
     *
     * @author attiot
     * 2018-04-19 10:33:23
     */
    public Page<AWorkflowProcess> query(AWorkflowProcessQueryParam param){
        Page<AWorkflowProcess> page = new Page<>();
        page.setPageNo(param.getPageNo());
        page.setPageSize(param.getPageSize());
        page.setTotalNum(aWorkflowProcessDao.queryCount(param));
        if(page.isOverCount()){
            page.setResults(Collections.EMPTY_LIST);
        }else{
            page.setResults(aWorkflowProcessDao.query(param));
        }
        return page;
    }
}
