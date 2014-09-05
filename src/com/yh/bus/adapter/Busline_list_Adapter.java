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

/**
 * User: LOVE Date: 14-08-30 Time: 下午1:33 Mail: 1130402124@qq.com
 *
 */
public class Busline_list_Adapter extends BaseAdapter {
	private ArrayList<Bus_Line_Info> bus_Line_Infos = new ArrayList<Bus_Line_Info>();
	private Context context;

	/**
	 * 不含数据的构造放方法
	 * 
	 * @param context
	 */
	public Busline_list_Adapter(Context context) {
		this.context = context;
	}

	/**
	 * 带数据的构造方法
	 * 
	 * @param context
	 * @param bus_Line_Infos
	 */
	public Busline_list_Adapter(Context context,
			ArrayList<Bus_Line_Info> bus_Line_Infos) {
		this.context = context;
		this.bus_Line_Infos = bus_Line_Infos;
	}

	/**
	 * 设置adapter数据
	 * 
	 * @param data
	 */
	public void addAll(ArrayList<Bus_Line_Info> data) {
		bus_Line_Infos.addAll(data);
		notifyDataSetChanged();
	}

	/**
	 * 清空adapter信息
	 */
	public void clear() {
		bus_Line_Infos.clear();
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return bus_Line_Infos.size();
	}

	@Override
	public Bus_Line_Info getItem(int position) {
		return bus_Line_Infos.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		final Bus_Line_Info bus_Line_Info = bus_Line_Infos.get(position);

		if (convertView == null) {
			convertView = LayoutInflater.from(getContext()).inflate(
					R.layout.search_info_list_item, null);
			convertView
					.setTag(new ListCell((TextView) convertView
							.findViewById(R.id.list_item_busname),
							(TextView) convertView
									.findViewById(R.id.list_item_busline),
							(ImageView) convertView
									.findViewById(R.id.list_item_busimg)));
		}

		ListCell lc = (ListCell) convertView.getTag();

		lc.getName().setText(bus_Line_Info.getLineName());
		lc.getLine().setText(
				bus_Line_Info.getStartStationName() + " --> "
						+ bus_Line_Info.getEndStationName());
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
		private TextView name;
		private TextView line;
		private ImageView busImg;

		public ListCell(TextView name, TextView line, ImageView busImg) {
			super();
			this.name = name;
			this.line = line;
			this.busImg = busImg;
		}

		public TextView getName() {
			return name;
		}

		public TextView getLine() {
			return line;
		}

		public ImageView getBusImg() {
			return busImg;
		}
	}

}
