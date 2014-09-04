package com.yh.yhbus.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.PopupWindow.OnDismissListener;

import com.yh.yhbus.R;
import com.yh.yhbus.domain.Bus_Line_Info;
import com.yh.yhbus.domain.Bus_View_Site;

/**
 * User: LOVE Date: 14-08-30 Time: 下午1:33 Mail: 1130402124@qq.com
 *
 */
public class BusView_list_Adapter extends BaseAdapter {
	private ArrayList<Bus_View_Site> bus_View_Sites = new ArrayList<Bus_View_Site>();
	private Context context;

	/**
	 * 不含数据的构造放方法
	 * 
	 * @param context
	 */
	public BusView_list_Adapter(Context context) {
		this.context = context;
	}

	/**
	 * 带数据的构造方法
	 * 
	 * @param context
	 * @param bus_Line_Infos
	 */
	public BusView_list_Adapter(Context context,
			ArrayList<Bus_View_Site> bus_View_Sites) {
		this.context = context;
		this.bus_View_Sites = bus_View_Sites;
	}

	/**
	 * 设置adapter数据
	 * 
	 * @param data
	 */
	public void addAll(ArrayList<Bus_View_Site> data) {
		bus_View_Sites.addAll(data);
		notifyDataSetChanged();
	}

	/**
	 * 清空adapter信息
	 */
	public void clear() {
		bus_View_Sites.clear();
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return bus_View_Sites.size();
	}

	@Override
	public Bus_View_Site getItem(int position) {
		return bus_View_Sites.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		final Bus_View_Site bus_View_Site = bus_View_Sites.get(position);

		if (convertView == null) {
			convertView = LayoutInflater.from(getContext()).inflate(
					R.layout.busview_list_item, null);
			convertView.setTag(new ListCell((TextView) convertView
					.findViewById(R.id.busview_siteName),
					(TextView) convertView.findViewById(R.id.busview_siteNum)));
		}

		ListCell lc = (ListCell) convertView.getTag();

		lc.getName().setText(bus_View_Site.getStationName());
		lc.getNum().setText(position + 1 + "");

		return convertView;
	}

	/**
	 * 得到上下文
	 * 
	 * @return
	 */
	public Context getContext() {
		return context;
	}

	/**
	 * 条目内容
	 * 
	 * @author LOVE
	 *
	 */
	private static class ListCell {
		private TextView name;
		private TextView num;

		public ListCell(TextView name, TextView num) {
			super();
			this.name = name;
			this.num = num;

		}

		public TextView getName() {
			return name;
		}

		public TextView getNum() {
			return num;
		}

	}

}
