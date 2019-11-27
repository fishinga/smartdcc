/**
 * Copyright (c) 2017-2027 厦门物之联智能科技有限公司.
 */
package com.attiot.railAnaly.goods.service;

import com.attiot.railAnaly.goods.dao.APointListGoodsHisDao;
import com.attiot.railAnaly.goods.entity.PointListGoods;
import com.attiot.railAnaly.goods.dao.PointListGoodsDao;
import com.attiot.railAnaly.goods.param.PointListGoodsQueryParam;
import com.attiot.railAnaly.foundation.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * 请点工单作业牌关系
 * @author attiot
 * 2018-04-11 17:27:16
 */
@Service
public class PointListGoodsService {

	@Autowired
	private PointListGoodsDao pointListGoodsDao;
	@Autowired
	private APointListGoodsHisDao pointListGoodsHisDao;

	/**
	 * 新增
	 * @param bo
	 */
	public String insert(PointListGoods bo) {
		pointListGoodsDao.insert(bo);
		return bo.getId();
    }

	/**
	 * 修改
	 * @param bo
	 */
	public void update(PointListGoods bo) {
		pointListGoodsDao.update(bo);
    }

	/**
	 * 删除
	 * @param id
	 */
	public void delete(String id) {
		pointListGoodsDao.delete(id);
    }

	public void deleteGoods(String goodsIds,String userId) {

		List<PointListGoods> pointListGoodsList = pointListGoodsDao.getByGoodsIds(goodsIds);

		if(pointListGoodsList.size()>0) {
			StringBuilder pointListGoodsIds = new StringBuilder("");
			for(PointListGoods pointListGoods:pointListGoodsList) {
				if(pointListGoodsIds.length()>0) {
					pointListGoodsIds.append(",");
				}
				pointListGoodsIds.append(pointListGoods.getId());
			}

			Map params = new HashMap();
			params.put("id",pointListGoodsIds.toString());
			params.put("modifor",userId);
			pointListGoodsHisDao.updateModifor(params);
		}
		if(goodsIds.length()>0) {
			pointListGoodsDao.deleteByGoodsIds(goodsIds);
		}

	}

	@Transactional
	public void deleteRepeatGoods(String userId) {
		List<PointListGoods> pointListGoodsList = pointListGoodsDao.getByCreator(userId);
		StringBuilder repeatIds = new StringBuilder("");
		Map<String,PointListGoods> pointListGoodsMap = new HashMap();
		if(null != pointListGoodsList && pointListGoodsList.size()>0) {
			for(PointListGoods record:pointListGoodsList) {
				if(pointListGoodsMap.get(record.getGoodsId())==null) {
					pointListGoodsMap.put(record.getGoodsId(),record);
				}else {
					if(repeatIds.length()>0) {
						repeatIds.append(",");
					}
					repeatIds.append(record.getId());
				}
			}

			if(repeatIds.length()>0) {
				pointListGoodsDao.delete(repeatIds.toString());
			}
		}
	}

	/**
	 * 获取实体对象
	 * @param id
	 */
	public PointListGoods getById(String id) {
		return pointListGoodsDao.getById(id);
	}

	/**
	 * 查询实体对象
	 * @param param
	 */
	public PointListGoods getByParam(PointListGoodsQueryParam param) {
		return pointListGoodsDao.getByParam(param);
	}

	/**
	 * 查询
	 * @param param
	 */
	public Page<PointListGoods> query(PointListGoodsQueryParam param) {
		Page<PointListGoods> page = new Page<>();
		page.setPageNo(param.getPageNo());
		page.setPageSize(param.getPageSize());
		page.setTotalNum(pointListGoodsDao.queryCount(param));
		if (page.isOverCount()) {
			page.setResults(Collections.EMPTY_LIST);
		} else {
			page.setResults(pointListGoodsDao.query(param));
		}
		return page;
	}

	/**
	 * 查询统计
	 * @param param
	 */
	public long queryCount(PointListGoodsQueryParam param) {
		return pointListGoodsDao.queryCount(param);
	}

	/***
	 * 归还作业牌ids
	 * @param brandIds
	 */
	public void removeBrands(String brandIds) {
		pointListGoodsDao.batchDeleteByGoodIds(brandIds);
	}
}
