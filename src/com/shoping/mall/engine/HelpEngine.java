package com.shoping.mall.engine;

import java.util.List;

import com.shoping.mall.bean.Help;
import com.shoping.mall.bean.HelpDetail;

/**
 * 帮助模块的业务操作
 * @author shen
 *
 */
public interface HelpEngine {

	//1.检查本地是否有帮助列表
	//如果有，直接展示给用户
	//检查服务服务器是否有新数据
	//如果没有，到服务器端获取
	
	/**
	 * 获取服务端帮助列表
	 */
	public List<Help> getServiceHelpList();
	
	/**
	 * 根据id获取服务端帮助详情
	 */
	public HelpDetail getServiceHelpDetail(int helpId);
	
	/**
	 * 获取本地的帮助列表
	 */
	public List<Help> getLocalHelpList();
	
	/**
	 * 根据id获取本地的帮助详情
	 * @param helpId
	 */
	public HelpDetail getLocalHelpDeatil(int helpId);
}
