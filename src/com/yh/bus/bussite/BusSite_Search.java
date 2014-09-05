package com.yh.bus.bussite;

import java.util.ArrayList;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.special.ResideMenu.ResideMenu;
import com.yh.bus.Config;
import com.yh.bus.MainActivity;
import com.yh.bus.R;
import com.yh.bus.adapter.BusSite_list_Adapter;
import com.yh.bus.domain.Bus_Line_Info;
import com.yh.bus.domain.Bus_Site_Info;
import com.yh.bus.json.Bus_Site_Info_Json;

/**
 * User: LOVE Date: 14-08-30 Time: 下午1:33 Mail: 1130402124@qq.com
 */
public class BusSite_Search extends Fragment {

	private View parentView;
	private ResideMenu resideMenu;
	private Context context;
	private MainActivity parentActivity;

	private TextView ed_busSite;
	private ProgressBar loading;
	private Handler handler;

	private ArrayList<Bus_Site_Info> allSite;

	private static final int MESSAGE_TEXT_CHANGED = 0;
	private static final int DEFAULT_AUTOCOMPLETE_DELAY = 750;

	private BusSite_list_Adapter adapter;

	private ListView listview;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		parentView = inflater
				.inflate(R.layout.bussite_search, container, false);
		init();
		return parentView;
	}

	private void init() {
		parentActivity = (MainActivity) getActivity();
		resideMenu = parentActivity.getResideMenu();

		context = parentActivity.getContext();

		handler = new myHandler();

		ed_busSite = (TextView) parentView.findViewById(R.id.bussite_ed);
		ed_busSite.addTextChangedListener(new MyTextChangedListener());

		loading = (ProgressBar) parentView.findViewById(R.id.bussite_loading);

		listview = (ListView) parentView.findViewById(R.id.bussite_listview);

		adapter = new BusSite_list_Adapter(context);

		listview.setAdapter(adapter);

		listview.setOnItemClickListener(new ListItemClickListener());
	}

	/**
	 * 解析数据
	 * 
	 * @param body
	 */
	public void analyzeInfo(String body) {
		// 解析json，判断是否正确返回了结果
		try {

			JSONObject returnInfo = new JSONObject(body);
			// 得到返回码
			int code = returnInfo.getJSONObject("status").getInt("code");
			// 如果返回码是0则成功返回信息，否则失败，用toast提示
			if (code == 0) {
				// 在这里解析json然后显示信息

				allSite = Bus_Site_Info_Json.getBusSiteInfo(returnInfo
						.getJSONObject("result").getJSONArray("result"));

				if (allSite != null || allSite.size() > 0) {
					adapter.clear();
					adapter.addAll(allSite);
					Toast.makeText(context, "列表" + allSite.size(),
							Toast.LENGTH_SHORT).show();

				} else {
					adapter.clear();
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
	public void getbusinfo(String bus_Site_Name) {

		// 当文字改变清空信息
		System.out.println("网络模块执行");

		AsyncHttpClient client = new AsyncHttpClient();
		client.get(Config.BUS_SITE_SEARCH_PATH + bus_Site_Name + "/0/200",
				new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(int statusCode, Header[] headers,
							byte[] responseBody) {
						loading.setVisibility(View.GONE);

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

			Bus_Site_Info info = (Bus_Site_Info) parent.getAdapter().getItem(
					position);

			bus_ids.add(info.getBuslines());

			parentActivity.chengBusView(bus_ids);

			// 设置标题
			parentActivity.setTitleOfActionBar(info.getLineName() + "路");

			Toast.makeText(context, info.getBuslines(), Toast.LENGTH_SHORT)
					.show();
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
			if (ed_busSite.getText().toString().length() > 0) {

				loading.setVisibility(View.VISIBLE);

				// 通过线程延时

				handler.sendMessageDelayed(handler.obtainMessage(
						MESSAGE_TEXT_CHANGED, ed_busSite.getText().toString()),
						DEFAULT_AUTOCOMPLETE_DELAY);

			} else {
				loading.setVisibility(View.GONE);
			}
		}

	}

}
