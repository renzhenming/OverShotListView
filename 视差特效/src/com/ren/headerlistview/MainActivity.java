package com.ren.headerlistview;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {

	private ParacellerView paracellerView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		paracellerView = (ParacellerView) findViewById(R.id.listview);
		View view = View.inflate(getApplicationContext(), R.layout.header_view, null);
		ImageView imageView = (ImageView) view.findViewById(R.id.imageview);
		paracellerView.addHeaderView(view);
		paracellerView.setImageView(imageView);
		paracellerView.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, android.R.id.text1, Cheeses.NAMES){
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				TextView view = (TextView) super.getView(position, convertView, parent);
				view.setTextColor(Color.BLACK);
				return view;
			}
		});
	}

}
