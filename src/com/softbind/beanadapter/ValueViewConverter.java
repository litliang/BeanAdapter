package com.softbind.beanadapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public interface ValueViewConverter {
	void mapValueToView(Object value, View view, int position,
			BeanAdapter beanAdapter, ViewGroup parent);

	void mapValueToTextView(Object value, TextView view, int position,
			BeanAdapter beanAdapter, ViewGroup parent);

	void mapValueToImageView(Object value, ImageView view, int position,
			BeanAdapter beanAdapter, ViewGroup parent);

	void mapValueToOtherView(Object value, View view, int position,
			BeanAdapter beanAdapter, ViewGroup parent);
}
