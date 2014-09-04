package com.yh.yhbus.busline;

import java.util.ArrayList;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.special.ResideMenu.ResideMenu;
import com.yh.yhbus.Config;
import com.yh.yhbus.MainActivity;
import com.yh.yhbus.R;
import com.yh.yhbus.adapter.Busline_list_Adapter;
import com.yh.yhbus.domain.Bus_Line_Info;
import com.yh.yhbus.json.Bus_Line_Info_Json;

/**
 * User: LOVE Date: 14-08-30 Time: 下午1:33 Mail: 1130402124@qq.com
 */
public class BusLine_Search extends Fragment {

	private Context context;
	private View parentView;
	private MainActivity parentActivity;

	private ResideMenu resideMenu;
	private TextView ed_busline;
	private ProgressBar loading;
	private Handler handler;
	private static final int MESSAGE_TEXT_CHANGED = 0;
	private static final int DEFAULT_AUTOCOMPLETE_DELAY = 750;

	private Busline_list_Adapter adapter;

	private ListView listview;

	private String temp_Bus_Id = "";

	private boolean isSave = false;

	private ArrayList<Bus_Line_Info> allLine;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		parentView = inflater
				.inflate(R.layout.busline_search, container, false);
		init();

		return parentView;
	}

	/**
	 * 初始化
	 * 
	 */
	private void init() {
		parentActivity = (MainActivity) getActivity();

		resideMenu = parentActivity.getResideMenu();

		context = parentActivity.getContext();

		handler = new myHandler();

		adapter = new Busline_list_Adapter(context);

		ed_busline = (TextView) parentView.findViewById(R.id.busline_ed);

		ed_busline.addTextChangedListener(new MyTextChangedListener());

		loading = (ProgressBar) parentView.findViewById(R.id.busline_loading);

		listview = (ListView) parentView.findViewById(R.id.busline_listview);

		listview.setAdapter(adapter);

		listview.setOnItemClickListener(new ListItemClickListener());

	}

	/**
	 * 解析数据
	 * 
	 * @param body
	 */
	public void analyzeInfo(String body) {

		// 关闭进度条
		loading.setVisibility(View.GONE);
		// 解析json，判断是否正确返回了结果
		try {

			JSONObject returnInfo = new JSONObject(body);
			// 得到返回码
			int code = returnInfo.getJSONObject("status").getInt("code");
			// 如果返回码是0则成功返回信息，否则失败，用toast提示
			if (code == 0) {
				// 在这里解析json然后显示信息

				allLine = Bus_Line_Info_Json.getBusLineInfo(returnInfo
						.getJSONObject("result").getJSONArray("result"));

				if (allLine != null || allLine.size() > 0) {
					adapter.clear();
					adapter.addAll(allLine);

					// 存储信息
					if (isSave) {
						Config.saveBusLineSearchInfo(context, allLine.get(0)
								.getLineName().replace("K", ""), body);
						isSave = false;
					}

					// 得到宽高
					getTotalHeightofListView(listview);
					getLocation(listview);
				} else {
					Toast.makeText(context, "暂无列表", Toast.LENGTH_SHORT).show();
				}

			} else {
				adapter.clear();
				Toast.makeText(context,
						returnInfo.getJSONObject("status").getString("msg"),
						Toast.LENGTH_SHORT).show();
			}

		} catch (JSONException e) {
			e.printStackTrace();
			Toast.makeText(context, "返回结果解析失败", Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * 得到公交信息
	 * 
	 * @param bus_code
	 */
	public void getbusinfo(String bus_code) {

		isSave = true;
		// 当文字改变清空信息
		System.out.println("网络模块执行");

		adapter.clear();
		AsyncHttpClient client = new AsyncHttpClient();
		client.get(Config.BUS_LINE_SEARCH_PATH + bus_code + "/0/20",
				new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(int statusCode, Header[] headers,
							byte[] responseBody) {
						temp_Bus_Id = ed_busline.getText().toString();

						String body = new String(responseBody);

						analyzeInfo(body);

					}

					@Override
					public void onFailure(int statusCode, Header[] headers,
							byte[] responseBody, Throwable error) {
						loading.setVisibility(View.GONE);

						Toast.makeText(context, "获取信息失败，连接超时",
								Toast.LENGTH_SHORT).show();
					}

				});

	}

	/**
	 * 线路列表的监听器
	 * 
	 * @author LOVE
	 *
	 */
	class ListItemClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// 改变界面
			ArrayList<String> bus_ids = new ArrayList<String>();

			Bus_Line_Info info = (Bus_Line_Info) parent.getAdapter().getItem(
					position);
			Bus_Line_Info info2;
			if (position % 2 == 0) {
				info2 = (Bus_Line_Info) parent.getAdapter().getItem(
						position + 1);
			} else {
				info2 = (Bus_Line_Info) parent.getAdapter().getItem(
						position - 1);
			}

			bus_ids.add(info.getId());
			bus_ids.add(info2.getId());
			parentActivity.chengBusView(bus_ids);

			temp_Bus_Id = "";
			// 设置标题
			parentActivity.setTitleOfActionBar(info.getLineName() + "路");

			Toast.makeText(context, info.getId(), Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * 线程延迟处理
	 * 
	 * @author LOVE
	 *
	 */
	class myHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			getbusinfo(msg.obj.toString());

		}
	}

	/**
	 * 文字改变监听器
	 * 
	 * @author LOVE
	 *
	 */
	class MyTextChangedListener implements TextWatcher {

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {

		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {

		}

		@Override
		public void afterTextChanged(Editable s) {

			// 清除以前的消息
			handler.removeMessages(MESSAGE_TEXT_CHANGED);
			// 如果文本框不为空
			if (ed_busline.getText().toString().length() > 0
					&& !temp_Bus_Id.endsWith(ed_busline.getText().toString())) {

				loading.setVisibility(View.VISIBLE);

				// 通过线程延时
				String temp = Config.getBusLineSearchInfo(context, ed_busline
						.getText().toString());
				if (temp != null) {
					analyzeInfo(temp);
					temp_Bus_Id = "";
				} else {
					handler.sendMessageDelayed(handler.obtainMessage(
							MESSAGE_TEXT_CHANGED, ed_busline.getText()
									.toString()), DEFAULT_AUTOCOMPLETE_DELAY);
				}

			} else {
				adapter.clear();
				loading.setVisibility(View.GONE);
			}
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

	/**控件得到坐标
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
