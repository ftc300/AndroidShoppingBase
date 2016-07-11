package com.shoping.mall.study.mvp.presenter;

import com.shoping.mall.study.mvp.bean.UserBean;
import com.shoping.mall.study.mvp.model.IUserModel;
import com.shoping.mall.study.mvp.model.UserModel;
import com.shoping.mall.study.mvp.view.IUserView;

public class UserPresenter {
	private IUserView mUserView;
	private IUserModel mUserModel;

	public UserPresenter(IUserView view) {
		mUserView = view;
		mUserModel = new UserModel();
	}

	public void saveUser(int id, String firstName, String lastName) {
		mUserModel.setID(id);
		mUserModel.setFirstName(firstName);
		mUserModel.setLastName(lastName);
	}

	public void loadUser(int id) {
		UserBean user = mUserModel.load(id);
		mUserView.setFirstName(user.getFirstName());
		mUserView.setLastName(user.getLastName());
	}
}
