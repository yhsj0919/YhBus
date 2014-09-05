package com.yh.bus;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.special.ResideMenu.ResideMenu;
import com.special.ResideMenu.ResideMenuItem;
import com.yh.bus.busline.BusLine_Search;
import com.yh.bus.bussite.BusSite_Search;
import com.yh.bus.busview.Bus_View;

/**
 * User: LOVE Date: 14-08-30 Time: 下午1:33 Mail: 1130402124@qq.com
 *
 */
public class MainActivity extends FragmentActivity implements
		View.OnClickListener {
	private ResideMenu resideMenu;
	private MainActivity mContext;
	private ResideMenuItem itemHome;
	private ResideMenuItem itemProfile;
	private ResideMenuItem itemCalendar;
	private ResideMenuItem itemSettings;
	private TextView title;

	private Fragment busLine_Search;
	private Fragment busSite_Search;
	private Fragment busView;

	private boolean isBusView = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		title = (TextView) findViewById(R.id.title);

		mContext = this;
		setUpMenu();
		changeFragment(busLine_Search);
		title.setText("线路查询");
	}

	private void setUpMenu() {

		resideMenu = new ResideMenu(this);
		resideMenu.setBackground(R.drawable.menu_background);
		resideMenu.attachToActivity(this);
		resideMenu.setSwipeDirectionDisable(ResideMenu.DIRECTION_RIGHT);

		busLine_Search = new BusLine_Search();
		busSite_Search = new BusSite_Search();
		// resideMenu.setMenuListener(menuListener);
		// valid scale factor is between 0.0f and 1.0f. leftmenu'width is
		// 150dip.
		// 设置窗口打开的比例
		resideMenu.setScaleValue(0.6f);

		// create menu items;
		itemHome = new ResideMenuItem(this, R.drawable.icon_home, "线路查询");
		itemProfile = new ResideMenuItem(this, R.drawable.icon_profile, "站点查询");
		itemCalendar = new ResideMenuItem(this, R.drawable.icon_calendar,
				"线路规划");
		itemSettings = new ResideMenuItem(this, R.drawable.icon_settings,
				"关于设置");

		itemHome.setOnClickListener(this);
		itemProfile.setOnClickListener(this);
		itemCalendar.setOnClickListener(this);
		itemSettings.setOnClickListener(this);

		resideMenu.addMenuItem(itemHome, ResideMenu.DIRECTION_LEFT);
		resideMenu.addMenuItem(itemProfile, ResideMenu.DIRECTION_LEFT);
		resideMenu.addMenuItem(itemCalendar, ResideMenu.DIRECTION_LEFT);
		resideMenu.addMenuItem(itemSettings, ResideMenu.DIRECTION_LEFT);

		// You can disable a direction by setting ->
		// resideMenu.setSwipeDirectionDisable(ResideMenu.DIRECTION_RIGHT);

		findViewById(R.id.title_bar_left_menu).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
					}
				});
		findViewById(R.id.title_bar_right_menu).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						resideMenu.openMenu(ResideMenu.DIRECTION_RIGHT);
					}
				});
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		return resideMenu.dispatchTouchEvent(ev);
	}

	@Override
	public void onClick(View view) {

		if (view == itemHome) {

			title.setText(itemHome.getTitle());
			changeFragment(busLine_Search);
		} else if (view == itemProfile) {
			title.setText(itemProfile.getTitle());
			changeFragment(busSite_Search);
		} else if (view == itemCalendar) {
			title.setText(itemCalendar.getTitle());
			// changeFragment(new CalendarFragment());
		} else if (view == itemSettings) {
			title.setText(itemSettings.getTitle());
			// changeFragment(new SettingsFragment());
		}

		resideMenu.closeMenu();
	}

	/**
	 * 改变界面
	 * 
	 * @param targetFragment
	 */
	public void changeFragment(Fragment targetFragment) {
		resideMenu.clearIgnoredViewList();

		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		// 设置切换动画
		if (!isBusView) {
			ft.setCustomAnimations(R.anim.slide_left_in, R.anim.slide_left_out);
		} else {
			ft.setCustomAnimations(R.anim.slide_right_in,
					R.anim.slide_right_out);

		}

		ft.replace(R.id.main_fragment, targetFragment, "fragment")
				.setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
				.commit();
	}

	/**
	 * 得到菜单对象
	 * 
	 * @return
	 */
	public ResideMenu getResideMenu() {
		return resideMenu;
	}

	/**
	 * 得到上下文
	 * 
	 * @return
	 */
	public Context getContext() {
		return mContext;
	}

	/**
	 * 设置标题文字
	 * 
	 * @param title
	 */
	public void setTitleOfActionBar(String title) {
		this.title.setText(title);
	}

	public void chengBusView(ArrayList<String> bus_ids) {
		changeFragment(new Bus_View(bus_ids));
		isBusView = true;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (isBusView) {
				changeFragment(busLine_Search);
				title.setText(itemHome.getTitle());
				isBusView = false;
			} else {
				finish();
			}

		} 
		return true;
	}

}
