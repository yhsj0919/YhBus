package com.yh.yhbus.busview;

import java.util.ArrayList;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.PopupWindow.OnDismissListener;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.special.ResideMenu.ResideMenu;
import com.yh.yhbus.Config;
import com.yh.yhbus.MainActivity;
import com.yh.yhbus.R;
import com.yh.yhbus.adapter.BusView_list_Adapter;
import com.yh.yhbus.domain.Bus_View_Site;
import com.yh.yhbus.json.Bus_View_Site_Json;
import com.yh.yhbus.tools.Tools;

/**
 * User: LOVE Date: 14-08-30 Time: 下午1:33 Mail: 1130402124@qq.com
 */
public class Bus_View extends Fragment {

	private Context context;

	private View parentView;
	private ResideMenu resideMenu;

	private TextView startStationSite;
	private TextView startStationTime;

	private TextView endStationSite;
	private TextView endStationTime;

	private LinearLayout chengeView;

	private ListView listView;

	private BusView_list_Adapter adapter;

	private ArrayList<String> bus_ids;

	private ArrayList<Bus_View_Site> allSite;

	private boolean isSave = false;

	public Bus_View(ArrayList<String> bus_ids) {
		this.bus_ids = bus_ids;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		parentView = inflater.inflate(R.layout.bus_view, container, false);
		init();
		return parentView;
	}

	/**
	 * 初始化
	 * 
	 */
	private void init() {
		MainActivity parentActivity = (MainActivity) getActivity();
		resideMenu = parentActivity.getResideMenu();

		context = parentActivity.getContext();

		startStationSite = (TextView) parentView
				.findViewById(R.id.busview_startStationName);
		startStationTime = (TextView) parentView
				.findViewById(R.id.busview_startStationTime);

		endStationSite = (TextView) parentView
				.findViewById(R.id.busview_endStationName);
		endStationTime = (TextView) parentView
				.findViewById(R.id.busview_endStationTime);

		chengeView = (LinearLayout) parentView
				.findViewById(R.id.busview_chengeView);

		listView = (ListView) parentView.findViewById(R.id.bus_view_list);

		adapter = new BusView_list_Adapter(context);

		listView.setAdapter(adapter);

		getLineInfo(bus_ids.get(0));

	}

	/**
	 * 解析数据
	 * 
	 * @param body
	 */
	public void analyzeInfo(String bus_id, String body) {
		try {

			// System.out.println(body);

			JSONObject returnInfo = new JSONObject(body);
			// 得到返回码
			int code = returnInfo.getJSONObject("status").getInt("code");
			// 如果返回码是0则成功返回信息，否则失败，用toast提示
			if (code == 0) {
				// 保存信息
				Config.saveBusViewSite(context, bus_id, body);

				// 在这里解析json然后显示信息
				JSONObject result = returnInfo.getJSONObject("result");
				startStationSite.setText(result.getString("startStationName"));
				endStationSite.setText(result.getString("endStationName"));

				startStationTime.setText(Tools.getStartTime(
						result.getString("startStationName"),
						result.getString("operationTime")));
				endStationTime.setText(Tools.getEndTime(
						result.getString("startStationName"),
						result.getString("operationTime")));
				// 所有站点的集合
				allSite = Bus_View_Site_Json.getBusSiteInfo(result
						.getJSONArray("stations"));

				adapter.clear();
				adapter.addAll(allSite);
				// 得到宽高

				getTotalHeightofListView(listView);

			} else {
				Toast.makeText(context,
						returnInfo.getJSONObject("status").getString("msg"),
						Toast.LENGTH_SHORT).show();
			}
			getLocation(listView);
		} catch (JSONException e) {
			e.printStackTrace();
			Toast.makeText(context, "返回结果解析失败", Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * 得到线路信息
	 */
	private void getLineInfo(final String bus_id) {

		String info = Config.getBusViewSite(context, bus_ids.get(0));

		if (info != null) {
			analyzeInfo(bus_id, info);
			isSave = false;
		} else {
			isSave = true;
			System.out.println("网络模块执行了");

			AsyncHttpClient client = new AsyncHttpClient();
			client.get(Config.BUS_VIEW_GET_SITE_PATH + bus_id,
					new AsyncHttpResponseHandler() {

						@Override
						public void onSuccess(int statusCode, Header[] headers,
								byte[] responseBody) {
							String body = new String(responseBody);
							// 解析json，判断是否正确返回了结果
							analyzeInfo(bus_id, body);

						}

						@Override
						public void onFailure(int statusCode, Header[] headers,
								byte[] responseBody, Throwable error) {

							Toast.makeText(context, "获取信息失败，连接超时",
									Toast.LENGTH_SHORT).show();

						}
					});
		}
	}

	/**
	 * 获取listview高度
	 * 
	 * @param listView
	 */
	public static void getTotalHeightofListView(ListView listView) {
		ListAdapter mAdapter = listView.getAdapter();
		if (mAdapter == null) {
			return;
		}
		int totalHeight = 0;
		for (int i = 0; i < mAdapter.getCount(); i++) {
			View mView = mAdapter.getView(i, null, listView);
			mView.measure(
					MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
					MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
			// mView.measure(0, 0);
			totalHeight += mView.getMeasuredHeight();

			System.out.println(">>>>>> Height " + totalHeight);
			System.out.println(">>>>>> Width " + mView.getMeasuredWidth());

		}

	}

	/**
	 * 控件得到坐标
	 * 
	 * @param v
	 */
	public void getLocation(View v) {
		int[] location = new int[2];
		v.getLocationOnScreen(location);
		int x = location[0];
		int y = location[1];
		System.out.println("x:" + x + "y:" + y);
		System.out.println("图片各个角Left：" + v.getLeft() + "Right：" + v.getRight()
				+ "Top：" + v.getTop() + "Bottom：" + v.getBottom());
	}

}
