package com.attiot.railAnaly.metro.controller;

import com.attiot.railAnaly.base.AppResult;
import com.attiot.railAnaly.common.util.StringUtil;
import com.attiot.railAnaly.metro.entity.ToolsRelation;
import com.attiot.railAnaly.metro.model.ToolsTypeLocationModel;
import com.attiot.railAnaly.metro.model.ToolsTypeModel;
import com.attiot.railAnaly.metro.param.ToolsRelationQueryParam;
import com.attiot.railAnaly.metro.service.ToolsRelationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @Description: 获取车辆作业接口
 * @author
 * @date 2017-09-05
 * @version V1.0
 * @Copyright:
 */
@RestController
@Slf4j
@RequestMapping(value = "metro/toolsRelation")
public class ToolsRelationController {
	@Autowired
	private ToolsRelationService toolsRelationService;


	@RequestMapping(value = "/getRelation")
	public void getJob(HttpServletRequest request , HttpServletResponse response){
		AppResult result = new AppResult();
		try {
			result.setDataList(getToolsList());
			result.writer(request,response);
		} catch (IllegalArgumentException e) {
			result.setMsg(e.getMessage());
			result.setSuccess(false);
			log.error("系统内部异常", e);
		}
	}

	private List<ToolsTypeModel> getToolsList(){
		ToolsRelationQueryParam param = new ToolsRelationQueryParam();
		List list = toolsRelationService.getGroupyByToolsType(param);
		List<ToolsTypeModel> resultList=new ArrayList<ToolsTypeModel>();
		for (int i=0;i<list.size();i++) {
			String toolsType=(String) ((HashMap) list.get(i)).get("tools_type");
			String toolsTypeText=(String) ((HashMap) list.get(i)).get("tools_type_text");
			if(StringUtil.isEmpty(toolsType))
			{
				continue;
			}
			ToolsTypeModel model=new ToolsTypeModel();
			model.setTypeCode(toolsType);
			model.setTypeText(toolsTypeText);
			ToolsRelationQueryParam childParam = new ToolsRelationQueryParam();
			childParam.setToolsType(toolsType);
			List<ToolsRelation> childList = toolsRelationService.getByToolsType(childParam);
			List<ToolsTypeLocationModel> childResultList=new ArrayList<ToolsTypeLocationModel>();
			for (ToolsRelation childEntity:childList) {
				ToolsTypeLocationModel childModel=new ToolsTypeLocationModel();
				childModel.setToolsType(childEntity.getToolsType());
				childModel.setId(childEntity.getId());
				childModel.setStorageLocation(childEntity.getStorageLocation());
				childModel.setStorageLocationText(childEntity.getStorageLocationText());
				childModel.setLocationCode(childEntity.getLocationCode());
				childModel.setStatus(childEntity.getStatus());
				childModel.setStatusText(childEntity.getStatusText());
				childModel.setUpdateUserid(childEntity.getUpdateUserid());
				childResultList.add(childModel);
			}
			model.setChild(childResultList);
			resultList.add(model);
		}
		return  resultList;
	}

	/**
	 *  开闸、关闸   ids 关系维护主键id    |  status 1 开闸  ，2关闸   | userid 操作人id
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/doUpdateStatus")
	public void doUpdateStatus(HttpServletRequest request , HttpServletResponse response){
		String ids=request.getParameter("ids");
		String status=request.getParameter("status");
		String userid=request.getParameter("userid");
		AppResult result = new AppResult();
		if(!"1".equals(status) &&!"2".equals(status)){
			result.setMsg("无效操作");
			result.setSuccess(false);
			result.writer(request,response);
			return;
		}
		if(ids == null){
			result.setMsg("ids为空");
			result.setSuccess(false);
			result.writer(request,response);
			return;
		}
		try {
			String[] id=ids.split(",");
			for ( int i=0;i<id.length;i++ ) {
                if(StringUtil.isEmpty(id[i]))
			    {
			    	continue;
			    }
				ToolsRelation entity=toolsRelationService.getById(id[i]);
				if(entity==null){
					continue;
				}
				if(("1".equals(entity.getStatus())&& "1".equals(status))||("2".equals(entity.getStatus())&& "2".equals(status))){//开闸操作||关闸操作
					switch (status)
					{
						case "1"://开闸
							entity.setStatus("2");//已打开
							break;
						case "2"://关闸
							entity.setStatus("1");//可借用
							break;
						default:
							break;
					}
					entity.setUpdateUserid(userid);
					toolsRelationService.update(entity);
				}
			}
			result.setDataList(getToolsList());
			result.writer(request,response);
		} catch (IllegalArgumentException e) {
			result.setMsg(e.getMessage());
			result.setSuccess(false);
			log.error("系统内部异常", e);
		}
	}


}
