package com.example.dell.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import Adapter.Viewadapter;
import Modle.SdMap;
import Modle.TidePoint;
import Utils.CatchbyJsoup;
import Utils.ChartUtils;
import Utils.Tools;
import cn.youngkaaa.yviewpager.YViewPager;

public class MainActivity extends WearableActivity implements View.OnClickListener {
	private String TAG = "MainActivity";

	private TextView mTextView;
	private YViewPager viewPager;
	private Button button;
	private LineChart lineChart;
	private TextView city_textview;

	private CatchbyJsoup catchbyJsoup;
	private Tools tools;
	private ChartUtils chartUtils;
	private SdMap sdMap;

    private List<View> viewList = new ArrayList<View>();
    private List<TidePoint> tidePointList ;
    private Viewadapter viewAdapter;
	private String jsoupString;

    private LayoutInflater layoutInflater;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		chartUtils = new ChartUtils();
		initData();
		initView();
		setAmbientEnabled();
	}

	private void initData() {
		catchbyJsoup = new CatchbyJsoup();
		sdMap = new SdMap();
		tools = new Tools();
	}

	private void initView() {
		button = buildButton();
		//mTextView = buildtextView();
		viewPager = buildPager();
		city_textview = (TextView)viewList.get(0).findViewById(R.id.Citytext);
	}

	private TextView buildcitytext() {
		city_textview = findViewById(R.id.CurrHeight_text);
		Log.e(TAG, String.valueOf(city_textview));
		return city_textview;
	}

	private LineChart buildChart() {
		lineChart =findViewById(R.id.linechart);
		lineChart.setData(chartUtils.getlineData(tidePointList));
		return lineChart;
	}

	private Button buildButton() {
		button = findViewById(R.id.btn);
		return button;
	}

	private TextView buildtextView() {
		//mTextView = findViewById(R.id.text11);
		return mTextView;
	}

	private YViewPager buildPager() {
		viewPager = findViewById(R.id.Ypager);
		layoutInflater =LayoutInflater.from(this);
		View view1 = layoutInflater.inflate(R.layout.first_laout,null);
		View view2 = layoutInflater.inflate(R.layout.second_layout,null);
		view1.findViewById(R.id.btn).setOnClickListener(this);
		viewList.add(view1);
		viewList.add(view2);
		viewAdapter = new Viewadapter(viewList);
		viewPager.setAdapter(viewAdapter);
		return viewPager;
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.btn:
						Intent intent = new Intent(MainActivity.this,Choose_Pos_Activity.class);
						startActivityForResult(intent,1);
			default:
				break;
		}
	}

	/**	将jsoup下的字符分割并放在点集合中
	 * @param dataIn
	 * @return 潮汐点集合
	 */
	public List<TidePoint> putDataIn(String dataIn) {
		String data = tools.changeBrace(dataIn);
		String tidedata[] = data.split(",");
		tidePointList = new LinkedList<>();
		for(int i=0;i<tidedata.length-1;i++){
			if(i%2==0){
				tidePointList.add(new TidePoint(tidedata[i].replace(" ",""),Integer.parseInt(tidedata[i+1].replace(" ",""))));
				//tidePointList.add(new TidePoint(tools.UnxiTimetoDate(tidedata[i].replace(" ","")),Integer.parseInt(tidedata[i+1].replace(" ",""))));
			}
		}
		for(TidePoint t:tidePointList){
			Log.e(TAG,t.toString());
		}

		return tidePointList;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		final int code = data.getIntExtra("poscode",39);
		String city = data.getStringExtra("chooseCity");
		Log.e(TAG,city);
		city_textview.setText(city);
		new Thread(new Runnable() {
			@Override
			public void run() {
				jsoupString = catchbyJsoup.getData(code);
				tidePointList = putDataIn(jsoupString);
				lineChart = buildChart();
				lineChart.notifyDataSetChanged();
				lineChart.invalidate();
			}
		}).start();

	}
}
