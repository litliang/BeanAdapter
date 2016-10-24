package com.softbind.beanadapter;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class DefaultValueViewConverter implements ValueViewConverter{

	@Override
	public void mapValueToView(Object value, View view, int position,
			BeanAdapter beanAdapter, ViewGroup parent) {
		// TODO Auto-generated method stub
		if(view instanceof TextView){
			mapValueToTextView(value,(TextView) view,position,beanAdapter,parent);
		}else if(view instanceof ImageView){
			mapValueToImageView(value,(ImageView) view,position,beanAdapter,parent);
		}else{
			mapValueToOtherView(value,view,position,beanAdapter,parent);
		}
	}

	@Override
	public void mapValueToTextView(Object value, TextView view, int position,
			BeanAdapter beanAdapter, ViewGroup parent) {
		// TODO Auto-generated method stub
		if(value!=null){
			view.setText(value.toString());			
		}
	}

	@Override
	public void mapValueToImageView(Object value, ImageView view, int position,
			BeanAdapter beanAdapter, ViewGroup parent) {
		// TODO Auto-generated method stub
		if(value instanceof Integer||value.getClass() ==int.class){
			view.setImageResource((Integer) value);
		}else if(value instanceof Drawable){
			view.setImageDrawable((Drawable) value);
		}else if(value instanceof Bitmap){
			view.setImageBitmap((Bitmap) value);
		}else if(value instanceof String){
			if(value.toString().toLowerCase().trim().startsWith("http")){
				new BitmapManager().loadBitmap(value.toString(), view);
			}
		}
	}

	@Override
	public void mapValueToOtherView(Object value, View view, int position,
			BeanAdapter beanAdapter, ViewGroup parent) {
		// TODO Auto-generated method stub
		
	}

}
