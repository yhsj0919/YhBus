package com.yh.bus.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yh.bus.R;
import com.yh.bus.domain.Bus_Line_Info;
import com.yh.bus.domain.Bus_Site_Info;

/**
 * User: LOVE Date: 14-08-30 Time: 下午1:33 Mail: 1130402124@qq.com
 *
 */
public class BusSite_list_Adapter extends BaseAdapter {
	private ArrayList<Bus_Site_Info> bus_site_Infos = new ArrayList<Bus_Site_Info>();
	private Context context;

	/**
	 * 不含数据的构造放方法
	 * 
	 * @param context
	 */
	public BusSite_list_Adapter(Context context) {
		this.context = context;
	}

	/**
	 * 带数据的构造方法
	 * 
	 * @param context
	 * @param bus_Line_Infos
	 */
	public BusSite_list_Adapter(Context context,
			ArrayList<Bus_Site_Info> bus_site_Infos) {
		this.context = context;
		this.bus_site_Infos = bus_site_Infos;
	}

	/**
	 * 设置adapter数据
	 * 
	 * @param data
	 */
	public void addAll(ArrayList<Bus_Site_Info> data) {
		bus_site_Infos.addAll(data);
		notifyDataSetChanged();
	}

	/**
	 * 清空adapter信息
	 */
	public void clear() {
		bus_site_Infos.clear();
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return bus_site_Infos.size();
	}

	@Override
	public Bus_Site_Info getItem(int position) {
		return bus_site_Infos.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		final Bus_Site_Info bus_site_Info = bus_site_Infos.get(position);

		if (convertView == null) {
			convertView = LayoutInflater.from(getContext()).inflate(
					R.layout.search_site_list_item, null);
			convertView
					.setTag(new ListCell((TextView) convertView
							.findViewById(R.id.list_item_bussite),
							(TextView) convertView
									.findViewById(R.id.list_item_busline),
							(ImageView) convertView
									.findViewById(R.id.list_item_busimg)));
		}

		ListCell lc = (ListCell) convertView.getTag();

		lc.getSite().setText(bus_site_Info.getStationName());
		lc.getLine().setText(
				bus_site_Info.getLineName() + "  "
						+ bus_site_Info.getStartStationName() + " --> "
						+ bus_site_Info.getEndStationName());
		if (position % 2 == 0) {
			lc.getBusImg().setImageResource(R.drawable.list_bus_icon_0);
		}

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
		private TextView site;
		private TextView line;
		private ImageView busImg;

		public ListCell(TextView site, TextView line, ImageView busImg) {
			super();
			this.site = site;
			this.line = line;
			this.busImg = busImg;
		}

		public TextView getSite() {
			return site;
		}

		public TextView getLine() {
			return line;
		}

		public ImageView getBusImg() {
			return busImg;
		}
	}

}
