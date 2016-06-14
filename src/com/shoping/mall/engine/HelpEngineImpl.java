package com.shoping.mall.engine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.alibaba.fastjson.JSON;
import com.shoping.mall.ConstantValue;
import com.shoping.mall.bean.Help;
import com.shoping.mall.bean.HelpDetail;
import com.shoping.mall.dao.HelpDao;
import com.shoping.mall.dao.domain.HelpPO;
import com.shoping.mall.util.BeanFactory;
import com.shoping.mall.util.net.HttpClientUtil;

public class HelpEngineImpl implements HelpEngine {

	private HelpDao dao;
	
	public HelpEngineImpl(){

	}
	
	@Override
	public List<Help> getServiceHelpList() {
		
		dao = BeanFactory.getImpl(HelpDao.class);
		
		List<Help> result = null;
		// 1.本地的帮助列表信息，获取本地最新version
		String version = dao.getLastVersion();
		// 2.发送version到服务器端，回复json的字符串
		HttpClientUtil util = new HttpClientUtil();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("version", version);
		String json = util.sendPost(ConstantValue.SERVER_URL + ConstantValue.HELP , params);
		// 3.数据处理：检查一下数据是否回复正常
		try {
			JSONObject object = new JSONObject(json);
			if(checkError(object)){
				// 4.帮助列表数据处理
				String helpListStr = object.getString("helplist");//[{},{},{}]
				result = JSON.parseArray(helpListStr, Help.class);
				
				//持久化操作，如果数据量过大：开启子线程完成数据的操作
				if(null != result && result.size() > 0){
					for(Help item:result)
					{
						HelpPO helpPO = new HelpPO();
						helpPO.setId((int)item.getId());
						helpPO.setTitle(item.getTitle());
						helpPO.setVersion(object.getString("version"));
						dao.insert(helpPO);
					}
				}
				
				return result;
			}
			else {
				
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	private boolean checkError(JSONObject object) {
		try {
			String response = object.getString("response");
			if(ConstantValue.ERROR.equals(response)){
				return false;
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public HelpDetail getServiceHelpDetail(int helpId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Help> getLocalHelpList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HelpDetail getLocalHelpDeatil(int helpId) {
		// TODO Auto-generated method stub
		return null;
	}

}
