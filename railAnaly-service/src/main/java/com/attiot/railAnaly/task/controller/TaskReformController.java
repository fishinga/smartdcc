package com.attiot.railAnaly.task.controller;


import com.attiot.railAnaly.base.AppResult;
import com.attiot.railAnaly.foundation.Page;
import com.attiot.railAnaly.foundation.SqlQueryParam;
import com.attiot.railAnaly.task.entity.ATaskList;
import com.attiot.railAnaly.task.entity.TaskReformColumns;
import com.attiot.railAnaly.task.param.TaskReformColumnsQueryParam;
import com.attiot.railAnaly.task.service.TaskListService;
import com.attiot.railAnaly.task.service.TaskReformColumnsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@RestController
@Slf4j
@RequestMapping(value = "/task/TaskReformController")
public class TaskReformController {

    @Autowired
    private TaskListService taskListService;
    @Autowired
    private TaskReformColumnsService taskReformColumnsService;

    @RequestMapping(value = "reformOption")
    public void workOption(HttpServletRequest request, HttpServletResponse response){
        AppResult result = new AppResult();
        String taskListId = request.getParameter("taskListId");
        try {
            ATaskList taskList = taskListService.getById(taskListId);
            if(taskList == null){
                result.setMsg("参数错误");
                result.setSuccess(false);
                result.writer(request,response);
                return;
            }
            Integer taskSource = taskList.getTaskSource();
            if("2".equals(taskList.getTaskSource()+"") || "2".equals(taskList.getPreTransferSource()+"")){
                String jobId = taskList.getJobId();
                TaskReformColumnsQueryParam param = new TaskReformColumnsQueryParam();
                param.setReformId(jobId);
                param.setSort(SqlQueryParam.SORT_ASC);
                param.setColumn("col_code");
                Page<TaskReformColumns> page =  taskReformColumnsService.query(param);
                result.setDataList(page.getResults());
            }
        } catch (IllegalArgumentException e) {
            result.setMsg("参数错误");
            result.setSuccess(false);
            log.error("系统内部异常", e);
        }
        result.writer(request,response);

    }



}
