package com.shoping.mall.dao.impl;

import android.content.Context;

import com.shoping.mall.dao.HelpDao;
import com.shoping.mall.dao.base.DAOSupport;
import com.shoping.mall.dao.domain.HelpPO;

public class HeloDaoImpl extends DAOSupport<HelpPO> implements HelpDao {
	
	public HeloDaoImpl(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public HeloDaoImpl() {
	}
	@Override
	public String getLastVersion() {
		// TODO Auto-generated method stub
		return "1";
	}

}
