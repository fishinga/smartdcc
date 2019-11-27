/**
 * Copyright (c) 2017-2027 厦门物之联智能科技有限公司.
 */
package com.attiot.railAnaly.goods.service;

import com.alibaba.fastjson.JSONObject;
import com.attiot.railAnaly.base.service.TSBaseUserService;
import com.attiot.railAnaly.borrow.dao.ABorrowListDao;
import com.attiot.railAnaly.borrow.dao.ABorrowListDetailDao;
import com.attiot.railAnaly.borrow.entity.ABorrowList;
import com.attiot.railAnaly.borrow.entity.ABorrowListDetail;
import com.attiot.railAnaly.category.dao.ABorrowCategoryDao;
import com.attiot.railAnaly.category.entity.ABorrowCategory;
import com.attiot.railAnaly.category.param.ABorrowCategoryQueryParam;
import com.attiot.railAnaly.foundation.Page;
import com.attiot.railAnaly.goods.dao.*;
import com.attiot.railAnaly.goods.entity.ABorrowGoods;
import com.attiot.railAnaly.goods.entity.PointList;
import com.attiot.railAnaly.goods.entity.PointListGoods;
import com.attiot.railAnaly.goods.param.PointListQueryParam;
import com.attiot.railAnaly.point.dao.APointPleaseBoardingDao;
import com.attiot.railAnaly.point.entity.APointPleaseBoarding;
import com.attiot.railAnaly.point.entity.APointPleaseCart;
import com.attiot.railAnaly.point.service.PointPleaseCartService;
import com.attiot.railAnaly.workflow.service.WorkflowDataService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 请点工单
 * @author attiot
 * 2018-04-11 17:29:04
 */
@Service
public class PointListService {

	@Autowired
	private PointListDao pointListDao;
	@Autowired
	private PointListGoodsDao pointListGoodsDao;
	@Autowired
	private ABorrowGoodsDao borrowGoodsDao;

	@Autowired
	private ABorrowCategoryDao aBorrowCategoryDao;
	@Autowired
	private TSBaseUserService userService;
	@Autowired
	private APointListGoodsHisDao pointListGoodsHisDao;
	@Autowired
	private APointListHisDao aPointListHisDao;
	@Autowired
	private ABorrowListDetailDao aBorrowListDetailDao;
	@Autowired
	private ABorrowListDao aBorrowListDao;
	@Autowired
	private APointPleaseBoardingDao boardingDao;
	@Autowired
	private PointPleaseCartService cartService;
	@Autowired
	private WorkflowDataService workflowDataService;


	/**
	 * 新增
	 * @param bo
	 */
	public String insert(PointList bo) {
		pointListDao.insert(bo);
		return bo.getId();
	}

	/**
	 * 修改
	 * @param bo
	 */
	public void update(PointList bo) {
		pointListDao.update(bo);
	}

	/**
	 * 删除
	 * @param id
	 */
	public void delete(String id) {
		pointListDao.delete(id);
	}

	/**
	 * 获取实体对象
	 * @param id
	 */
	public PointList getById(String id) {
		return pointListDao.getById(id);
	}

	/**
	 * 查询实体对象
	 * @param param
	 */
	public PointList getByParam(PointListQueryParam param) {
		return pointListDao.getByParam(param);
	}

	/**
	 * 查询
	 * @param param
	 */
	public Page<PointList> query(PointListQueryParam param) {
		Page<PointList> page = new Page<>();
		page.setPageNo(param.getPageNo());
		page.setPageSize(param.getPageSize());
		page.setTotalNum(pointListDao.queryCount(param));
		if (page.isOverCount()) {
			page.setResults(Collections.EMPTY_LIST);
		} else {
			page.setResults(pointListDao.query(param));
		}
		return page;
	}

	/**
	 * 查询统计
	 * @param param
	 */
	public long queryCount(PointListQueryParam param) {
		return pointListDao.queryCount(param);
	}

	/**
	 * 根据列车号获取实体对象
	 * @param trainNO
	 */
	public PointList getByTrainNo(String trainNO) {
		return pointListDao.getByTrainNo(trainNO);
	}

	public List<PointList> getByTrainNos(String trainNOs) {
		return pointListDao.getByTrainNos(trainNOs);
	}

	public String addOrUpdate(PointList bo,String trainNo,String ids,String userId){
		if(StringUtils.isNotBlank(bo.getId())){
			update(bo);
		}else{
			insert(bo);
		}
		List<PointListGoods> pointListGoodsList = pointListGoodsDao.getByGoodsIds(ids);
		if(null ==pointListGoodsList || pointListGoodsList.size()<=0) {
			pointListGoodsList = new ArrayList();
		}
		Map<String,PointListGoods> pointListGoodsMap = new HashMap();
		for(PointListGoods record:pointListGoodsList) {
			pointListGoodsMap.put(record.getGoodsId(),record);
		}
		String[] idList = ids.split(",");
		List<PointListGoods>  list = new ArrayList();
		for(int i=0;i<idList.length;i++){
			PointListGoods pointListGoods0 =pointListGoodsMap.get(idList[i]);
			if(pointListGoods0 == null){//没挂过，挂牌
				ABorrowGoods borrowGoods = borrowGoodsDao.getById(idList[i]);
				PointListGoods pointListGoods = new PointListGoods();
				pointListGoods.setTrainNo(trainNo);
				pointListGoods.setGoodsId(idList[i]);
				pointListGoods.setGoodsName(borrowGoods.getName());
				pointListGoods.setGoodsCode(borrowGoods.getCode());
				pointListGoods.setCreator(userId);
				pointListGoods.setCreateTime(new Date());
				pointListGoods.setRemarks("APP挂牌");
				list.add(pointListGoods);
			}
		}

		if(null != list && list.size()>0) {
			pointListGoodsDao.batchInsert(list);
			pointListGoodsHisDao.batchInsert(list);
		}
		return bo.getId();
	}



	/***
	 * 请点审批通过后，调用接口
	 * @param trainNo 列车号
	 * @param ppointId 请点ID
	 * @param jobcontent 请点内容
	 * @param pointcreator 请点人,账号ID
	 * @param isElectric 是否有电：0无电，1有电；2有/无作业
	 * @param startJobTime 开始作业时间
	 * @param pointduration 作业时长
	 */
	public void addToPointList( String accessToken,String trainNo,String ppointId,String jobcontent,String pointcreator,String transfer,Integer isElectric,Date startJobTime,Float pointduration) {
		List<PointList> list = pointListDao.getParentAPointListByTrainNo(trainNo.split(","));
		if(null == list || list.size()<=0) {
			throw new RuntimeException("请点工单中找不到车辆"+trainNo);
		}
		for(int k=0;k<list.size();k++){
			PointList parentPointList= list.get(k);
			PointList aPointList = new PointList();
			aPointList.setTrainNo(parentPointList.getTrainNo());
			aPointList.setPpointId(ppointId);
			aPointList.setPpointContents(jobcontent);
			aPointList.setPpointCreator(pointcreator);
			aPointList.setPpointTransfer(pointcreator);
			aPointList.setParentId(parentPointList.getId());
			aPointList.setIsElectric(isElectric);
			aPointList.setStartJob(startJobTime);
			aPointList.setPpointDuration(pointduration);
			aPointList.setTrackName(parentPointList.getTrackName());
			aPointList.setMetroStatus(parentPointList.getMetroStatus());
			aPointList.setOriginalStatus(parentPointList.getOriginalStatus());
			aPointList.setCreator(accessToken);
			Calendar current = Calendar.getInstance();
			aPointList.setCreatetime(current.getTime());
			aPointList.setModifor(accessToken);
			aPointList.setModifytime(current.getTime());
			pointListDao.insert(aPointList);
			aPointListHisDao.create(aPointList);
			parentPointList.setMetroStatus("1");
			parentPointList.setModifor(accessToken);
			//将父列车状态改为维修
			pointListDao.updateMetroStatusById(parentPointList);
		}


		if(1==list.size()) {
			List<ABorrowList> borrowLists = aBorrowListDao.getByPointId(ppointId);
			List<PointListGoods> goodsList = new ArrayList();
			String goodsCategory = "";//编号为1001开头的
			ABorrowCategoryQueryParam param = new ABorrowCategoryQueryParam();
			param.setCode("1001");
			List<ABorrowCategory> categoryList =aBorrowCategoryDao.queryByPreCode(param);
			if(null != categoryList && categoryList.size()>0) {
				StringBuilder goodsCategory2 = new StringBuilder("");//编号为1001开头的
				for(ABorrowCategory record:categoryList) {
					if(goodsCategory2.length()>0) {
						goodsCategory2.append(",");
					}
					goodsCategory2.append(record.getId());
				}
				goodsCategory = goodsCategory2.toString();
			}
			if(null != borrowLists && borrowLists.size()>0) {
				for(ABorrowList borrowLst:borrowLists) {
					if(borrowLst.getBorrowState()==4) {
						String[] borrowId = new String[1];
						borrowId[0]=borrowLst.getId();
						List<ABorrowListDetail> goodList = aBorrowListDetailDao.getByBorrowId(borrowId);
						if(goodList!=null && goodList.size()>0) {
							for(ABorrowListDetail good:goodList) {
								String categoty = good.getBorrowGoodsCategory();
								//当借用的物品为作业牌时，才进行挂牌操作
								if(goodsCategory.indexOf(categoty)>=0) {
									PointListGoods record = new PointListGoods();
									record.setTrainNo(trainNo);
									record.setGoodsId(good.getBorrowGoodsId());
									record.setGoodsCode(good.getCode());
									record.setGoodsName(good.getBorrowGoodsName());
									record.setCreator(pointcreator);
									record.setRemarks("APP请点通过");
									List<PointListGoods> pointListGoods0 = pointListGoodsDao.getByGoodsId(good.getBorrowGoodsId());
									if(pointListGoods0 == null || pointListGoods0.size()<=0){
										goodsList.add(record);
									}
								}
							}
						}
					}
				}
			}
			if(null != goodsList && goodsList.size()>0) {
				pointListGoodsDao.batchInsert(goodsList);
				pointListGoodsDao.batchInsertHis(goodsList);
			}
		}
	}



	/***
	 * 销点审批后，调用接口
	 * @param trainNo 列车号
	 * @param ppointId 原请点ID
	 */
	public void removePointList(String trainNo,String ppointId,String userId) {
		Map params = new HashMap();
		params.put("trainNo",trainNo);
		params.put("ppointId",ppointId);
		pointListDao.removePointList(params);
		updateParentMetroStatus(trainNo,userId,ppointId);

	}



	public void updateParentMetroStatus(String trainNo,String userId,String pointId) {
		List<PointList> children = pointListDao.queryChildrenByTrainNo(trainNo);
		if(children.size()<=0) {children = new ArrayList();}
		Map params = new HashMap();
		boolean electric_0 = false;//无电
		boolean electric_1 = false;//有电
		for(PointList child:children) {
//			if(null!=child.getIsElectric()){
				if(0==child.getIsElectric()) {//无电
					electric_0 = true;
				}else if(1==child.getIsElectric()) {//有电
					electric_1 = true;
				}else {
					electric_0 = true;
					electric_1 = true;
				}
//			}
		}
		if(electric_0) {//有无电作业
			if(electric_1) {//有有电作业
				params.put("isElectric",2);
			}else {//无有电作业
				params.put("isElectric",0);
			}
		}else {//无无电作业
			if(electric_1) {//有有电作业
				params.put("isElectric",1);
			}else {//无有电作业
				params.put("isElectric",null);
			}
		}
		params.put("trainNo",trainNo);
		params.put("modifor",userId);
		if( children.size()<=0) {
			params.put("metroStatus",null);
			APointPleaseBoarding entity = boardingDao.getByPointId(pointId);
			if(entity != null){
				String status = entity.getMetroStatus();
				JSONObject jsStr = JSONObject.parseObject(status);
				String s = (String) jsStr.get(trainNo);
				params.put("metroStatus",s);
			}
			APointPleaseCart cart = cartService.getByPointId(pointId);
			if(cart != null){
				String status = cart.getMetroStatus();
				JSONObject jsStr = JSONObject.parseObject(status);
				String s = (String) jsStr.get(trainNo);
				params.put("metroStatus",s);
			}

		}else {
			params.put("metroStatus","1");
		}
		pointListDao.updateMetroStatusAndElectricByTrainNo(params);
	}


	/****
	 * 更新交接情况
	 * @param params
	 */
	public void updateTransfer(Map params){
		pointListDao.updateTransfer(params);
	}

	public List<PointList>  getAllPointList() {
		return pointListDao.getAllPointList();
	}

	public List<PointList>  getAllParentPointList() {
		return pointListDao.getAllParentPointList();
	}

	public void batchInsertLstGoods(List<PointListGoods> list) {
		pointListGoodsDao.batchInsert(list);
	}

	public void removeBrands(String brandIds) {
		pointListGoodsDao.batchDeleteByGoodIds(brandIds);
	}


	public void deleteByPPointId(String ppointId) {
		pointListDao.deleteByPPointId(ppointId);
	}

	public void batchUpdateMetroStatusById(List<PointList> list) {
		for(PointList pointList:list) {
			pointListDao.updateMetroStatusById(pointList);
		}
//		pointListDao.batchUpdateMetroStatusById(list);
	}

	/**
	 * 获取车辆预警信息
	 * 检测车辆作业状态
	 * jobType 作业类型, 1有电，2无电,3有/无电
	 * trainNo 列车号，多辆车以逗号隔开
	 * 请点时（包括所有登车作业）对有无电作业字段做判断。
	 * （假设请点的列车已存在请点作业为A，当前请点为B）
	 * 规则：
	 * 1、A的有无电状态为：有/无电，B申请有电或者无电，
	 * 则提示：xxx车存在[有/无电]作业[作业内容]，
	 * 注意作业前做好沟通。（多列车就往后叠加，用逗号分隔）；
	 * 2、A：有电或者无电，B申请有/无电，
	 * 则提示：xxx车存在[有电或者无电]作业[作业内容]，
	 * 注意作业前做好沟通。（多列车就往后叠加，用逗号分隔）；
	 * 3、A：有电/无电，B申请无电/有电，
	 * 则提示：xxx车存在[有电/无电]作业[作业内容]，作业有冲突，请谨慎。
	 * （多列车就往后叠加，用逗号分隔）；
	 */
	public String getTrainAlarm(Integer appElectric,String trainNo) {
		List<PointList> pointLists = pointListDao.queryChildrenByTrainNo(trainNo);
		if(null == pointLists || pointLists.size()<=0) pointLists = new ArrayList();

		Map<String,Integer> electricMap = new HashMap();//每辆车当前的状态
		Map<String,String> jobContentMap = new HashMap();//作业内容
		for(PointList record:pointLists) {
			Integer isElectric = electricMap.get(record.getTrainNo());
			if(isElectric==null) {
				isElectric = record.getIsElectric();
			}else if(isElectric==1) {
				if(record.getIsElectric()==2 || record.getIsElectric()==3) {
					isElectric = 3;
				}
			}else if(isElectric==2) {
				if(record.getIsElectric()==3 || record.getIsElectric()==1) {
					isElectric = 3;
				}
			}
			electricMap.put(record.getTrainNo(),isElectric);
			String jobcontent = jobContentMap.get(record.getTrainNo());
			if(null == jobcontent) {
				jobcontent = record.getPpointContents();
			}else {
				jobcontent += record.getPpointContents()+";";
			}
			jobContentMap.put(record.getTrainNo(),jobcontent);
		}

		String[] trainArray = trainNo.split(",");
		StringBuilder msg = new StringBuilder("");
		for(int i=0;i<trainArray.length;i++) {
			Integer isElectric = electricMap.get(trainArray[i]);
			if(null == isElectric) {
				continue;
			}
			if(1 == isElectric) {
				if(3==appElectric) {//申请有电/无电状态时
					msg.append(trainArray[i]).append("车存在[有电]作业[").append(jobContentMap.get(trainArray[i])).append("]，注意作业前做好沟通;");
				}else if(2==appElectric) {//申请无电
					msg.append(trainArray[i]).append("车存在[有电]作业[").append(jobContentMap.get(trainArray[i])).append("]，作业有冲突，请谨慎;");
				}
			}else if(2 == isElectric) {
				if(3==appElectric) {//申请有电/无电状态时
					msg.append(trainArray[i]).append("车存在[有电]作业[").append(jobContentMap.get(trainArray[i])).append("]，注意作业前做好沟通;");
				}else if(1==appElectric) {//申请无电
					msg.append(trainArray[i]).append("车存在[有电]作业[").append(jobContentMap.get(trainArray[i])).append("]，作业有冲突，请谨慎;");
				}
			}else if(3 == isElectric) {
				if(1==appElectric || 2==appElectric) {//申请有电或无电状态时
					msg.append(trainArray[i]).append("车存在[有/无电]作业[").append(jobContentMap.get(trainArray[i])).append("]，注意作业前做好沟通;");
				}else if(3==appElectric) {
					msg.append(trainArray[i]).append("车存在[有电/无电]作业[").append(jobContentMap.get(trainArray[i])).append("]，作业有冲突，请谨慎;");
				}
			}

		}

		return msg.toString();
	}
}
