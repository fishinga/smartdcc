package com.attiot.railAnaly.task.controller;


import com.alibaba.fastjson.JSON;
import com.attiot.railAnaly.base.AppResult;
import com.attiot.railAnaly.common.util.JacksonUtil;
import com.attiot.railAnaly.foundation.Page;
import com.attiot.railAnaly.foundation.SqlQueryParam;
import com.attiot.railAnaly.point.entity.APointPleaseBoarding;
import com.attiot.railAnaly.task.entity.TaskTable;
import com.attiot.railAnaly.task.entity.TaskTableColumns;
import com.attiot.railAnaly.task.entity.TaskTableData;
import com.attiot.railAnaly.task.param.TaskTableColumnsQueryParam;
import com.attiot.railAnaly.task.param.TaskTableQueryParam;
import com.attiot.railAnaly.task.service.TaskTableColumnsService;
import com.attiot.railAnaly.task.service.TaskTableDataService;
import com.attiot.railAnaly.task.service.TaskTableService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;


@RestController
@Slf4j
@RequestMapping(value = "/task/TaskTableController")
public class TaskTableController {

    @Autowired
    private TaskTableService taskTableService;
    @Autowired
    private TaskTableColumnsService taskTableColumnsService;


    /**
     * 获取作业前/后表格数据
     * 根据tableCode区分
     * @param request
     * @param response
     */
    @RequestMapping(value = "workOption")
    public void workOption(HttpServletRequest request, HttpServletResponse response){
        AppResult result = new AppResult();
        String tableCode = request.getParameter("tableCode");
        TaskTableQueryParam param = new TaskTableQueryParam();
        param.setTablecode(tableCode);
        TaskTable taskTable  = taskTableService.getByParam(param);
        if(null != taskTable){
            TaskTableColumnsQueryParam paramColunmns = new TaskTableColumnsQueryParam();
            paramColunmns.setColumn("col_sort");
            paramColunmns.setSort(SqlQueryParam.SORT_ASC);
            paramColunmns.setTaskTableId(taskTable.getId());
            Page<TaskTableColumns> page =  taskTableColumnsService.query(paramColunmns);
            result.setDataList(page.getResults());
        }else{
            result.setMsg("参数错误");
            result.setSuccess(false);
        }
        result.writer(request,response);
    }



}
