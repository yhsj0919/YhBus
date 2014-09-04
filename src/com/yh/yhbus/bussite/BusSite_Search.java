package com.yh.yhbus.bussite;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.special.ResideMenu.ResideMenu;
import com.yh.yhbus.MainActivity;
import com.yh.yhbus.R;

/**
 * User: LOVE Date: 14-08-30 Time: 下午1:33 Mail: 1130402124@qq.com
 */
public class BusSite_Search extends Fragment {

	private View parentView;
	private ResideMenu resideMenu;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		parentView = inflater
				.inflate(R.layout.bussite_search, container, false);
		setUpViews();
		return parentView;
	}

	private void setUpViews() {
		MainActivity parentActivity = (MainActivity) getActivity();
		resideMenu = parentActivity.getResideMenu();

		// 可以忽视手势的区域
		// resideMenu.addIgnoredView(parentView);
	}

}
