package com.shoping.mall.dao;

import com.shoping.mall.dao.base.DAO;
import com.shoping.mall.dao.domain.HelpPO;


public interface HelpDao extends DAO<HelpPO> {

	/**
	 * 获取最新的版本信息
	 * @return
	 */
	public String getLastVersion();
}
