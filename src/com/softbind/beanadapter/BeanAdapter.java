package com.softbind.beanadapter;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.softbind.beanadapter.util.ExceptionUtil;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class BeanAdapter extends BaseAdapter {
	private List<Object> objs = new ArrayList<Object>();

	private MapInfo mapInfo = new MapInfo();

	private ValueViewConverter converter;

	public BeanAdapter build(List<Object> objs) {
		this.objs = objs;
		return this;
	}

	public BeanAdapter buildNames(String... names) {
		new ExceptionUtil().checkNotNull(names);
		mapInfo.names = Arrays.asList(names);
		return this;
	}

	public BeanAdapter buildItemLayout(int layoutid) {
		new ExceptionUtil().check(layoutid, ">0");
		mapInfo.layout_item_id = layoutid;
		return this;
	}

	public BeanAdapter buildViewId(Integer... r_view_ids) {
		new ExceptionUtil().checkNotNull(r_view_ids);
		mapInfo.viewids = Arrays.asList(r_view_ids);
		return this;
	}

	public BeanAdapter buildConverter(ValueViewConverter converter) {
		new ExceptionUtil().checkNotNull(converter);
		this.converter = converter;
		return this;
	}

	public static class MapInfo {
		private Integer layout_item_id;
		private List<String> names = new ArrayList<String>();;
		private List<Integer> viewids = new ArrayList<Integer>();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if (objs == null || objs.size() == 0) {
			return 0;
		}
		return objs.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return objs.size() > arg0 ? objs.get(arg0) : null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	Class beanCachedClazz;

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView == null) {
			convertView = LayoutInflater.from(parent.getContext()).inflate(
					mapInfo.layout_item_id, null);
		}
		new ExceptionUtil().checkNotNull(mapInfo.names, mapInfo.viewids,
				mapInfo.layout_item_id, this.converter);
		Object object = getItem(position);
		new ExceptionUtil().checkNotNull(object);
		View itemView = convertView;

		if (beanCachedClazz == null) {
			beanCachedClazz = object.getClass();
		}

		for (int i = 0; i < mapInfo.names.size(); i++) {
			String name = mapInfo.names.get(i);
			Field f = null;
			try {
				f = beanCachedClazz.getDeclaredField(name);
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchFieldException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			boolean accessible = f.isAccessible();
			f.setAccessible(true);
			Object fvalue = null;
			try {
				fvalue = f.get(object);
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			int viewid = mapInfo.viewids.get(i);
			View itemview = convertView.findViewById(viewid);
			converter.mapValueToView(fvalue, itemview, position, this, parent);
		}
		return convertView;
	}

}
