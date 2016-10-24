package com.softbind.beanadapter.test;

import java.util.ArrayList;
import java.util.List;

import com.softbind.beanadapter.BeanAdapter;
import com.softbind.beanadapter.DefaultValueViewConverter;
import com.softbind.beanadapter.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		AdapterView adapterView = (AdapterView) findViewById(R.id.adapterview);
		Trip trip1 = new Trip().setName("��Դɽ")
				.setDesc("������Ȼ����ʥ��").setDistrict("����Ȫ��")
				.setPositionRef("Ȫ�ݸ���վ��").setShareSuggestion("�＾�����ʵ�Ϊ��")
				.setUrl("http://f.hiphotos.baidu.com/baike/w%3D268%3Bg%3D0/sign=39d7297690dda144da096bb48a8cb79f/a50f4bfbfbedab64364fc0a5f036afc379311e91.jpg");
		Trip trip2 = new Trip().setName("��ˮ��").setDesc("�й��ڶ����˹����ۺ�")
				.setDistrict("�Ϻ��ֶ�����")
				.setPositionRef("16���ߵ�ˮ��վ").setShareSuggestion("���߶���ļ����˳���")
				.setUrl("http://file20.mafengwo.net/M00/7D/D0/wKgB3FGrGNSAfoucAATqPdUiAl828.rbook_comment.w600_h400.jpeg");

		Trip trip3 = new Trip().setName("��ҽ�")
				.setDesc("��һ���й���ɫС��\n�Ϻ��Ĵ���ʷ�Ļ�����֮һ").setDistrict("�Ϻ�����")
				.setPositionRef("�ڽ�17������ҽ�վ").setShareSuggestion("�������")
				.setUrl("http://h.hiphotos.baidu.com/baike/c0%3Dbaike150%2C5%2C5%2C150%2C50/sign=c51d88446b63f62408503151e62d809d/902397dda144ad346e8084fad0a20cf431ad858b.jpg");

		List trips = new ArrayList();
		trips.add(trip1);
		trips.add(trip2);
		trips.add(trip3);
		BeanAdapter beanAdapter = new BeanAdapter()
		.buildNames("name","desc","positionRef","shareSuggestion","district","url")
		.buildViewId(R.id.name,R.id.desc,R.id.positionref,R.id.sharesuggestion,R.id.district,R.id.image)
		.buildItemLayout(R.layout.item_layout)
		.buildConverter(new DefaultValueViewConverter()).build(trips);
		adapterView.setAdapter(beanAdapter);
		

	}

}
