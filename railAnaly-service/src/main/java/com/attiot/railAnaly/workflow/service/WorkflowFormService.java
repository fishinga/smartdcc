package com.attiot.railAnaly.workflow.service;


import com.attiot.railAnaly.foundation.Page;
import com.attiot.railAnaly.workflow.dao.AWorkflowFormDao;
import com.attiot.railAnaly.workflow.entity.AWorkflowForm;
import com.attiot.railAnaly.workflow.param.AWorkflowFormQueryParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class WorkflowFormService {
    @Autowired
    private AWorkflowFormDao aWorkflowFormDao;

    /***
     * 根据主键查询
     * @param id
     * @return
     */
    public AWorkflowForm getById(String id){
        return  aWorkflowFormDao.getById(id);
    }


    /**
     * 查询
     *
     * @param param 参数
     *
     * @author attiot
     * 2018-04-19 10:56:17
     */
    public Page<AWorkflowForm> query(AWorkflowFormQueryParam param){
        Page<AWorkflowForm> page = new Page<>();
        page.setPageNo(param.getPageNo());
        page.setPageSize(param.getPageSize());
        page.setTotalNum(aWorkflowFormDao.queryCount(param));
        if(page.isOverCount()){
            page.setResults(Collections.EMPTY_LIST);
        }else{
            page.setResults(aWorkflowFormDao.query(param));
        }
        return page;
    }
}
