package com.example.dell.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.listener.BarLineChartTouchListener;

import java.util.ArrayList;
import java.util.Date;
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

	private static final String DEFAULT_CITY = "张家埠";
	private static final int DEFAULT_CITYCODE = 42;

	private YViewPager viewPager;
	private Button button;
	private LineChart lineChart;
	private TextView city_textview;
	private TextView currHeighView;
	private TextView halView;
	private TextView lowView;

	private CatchbyJsoup catchbyJsoup;
	private Tools tools;
	private ChartUtils chartUtils;
	private SdMap sdMap;

    private List<View> viewList = new ArrayList<View>();
    private List<TidePoint> tidePointList ;
    private Viewadapter viewAdapter;
	private String jsoupString;

	private XAxis xAxis;
	private YAxis yAxis;
	private LimitLine limitLine ;
	private LimitLine rlimitLine ;
	private boolean changeflag = true;


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
		getDataAndDraw(DEFAULT_CITYCODE);
	}
 
	private void initView() {
		button = buildButton();
		//mTextView = buildtextView();
		viewPager = buildPager();
		city_textview = (TextView)viewList.get(0).findViewById(R.id.Citytext);
		currHeighView = (TextView)viewList.get(0).findViewById(R.id.CurrHeight_text);
		halView = (TextView)viewList.get(0).findViewById(R.id.heigthtext);
		lowView = (TextView)viewList.get(0).findViewById(R.id.lowtext);

		city_textview.setText(DEFAULT_CITY);
		lineChart = buildChart();

	}

	private LineChart buildChart() {
		lineChart = (LineChart) viewList.get(1).findViewById(R.id.linechart);
		lineChart.clear();
		Description description = new Description();
		description.setText("");
		lineChart.setDescription(description);
		lineChart.getLegend().setEnabled(false);
		lineChart.setNoDataText("暂无数据");
		lineChart.zoomToCenter(3f, 1f);
		lineChart.setScaleEnabled(false); //设置无缩放
        lineChart.getAxisRight().setEnabled(false);
		lineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);

		BarLineChartTouchListener barLineChartTouchListener = (BarLineChartTouchListener) lineChart.getOnTouchListener();
		barLineChartTouchListener.stopDeceleration();
		xAxis = lineChart.getXAxis();

		yAxis = lineChart.getAxisLeft();
		yAxis.setTextSize(14);
		yAxis.setTextColor(Color.WHITE);
		xAxis.setLabelCount(5,false);
		xAxis.setValueFormatter(new IAxisValueFormatter() {
			@Override
			public String getFormattedValue(float value, AxisBase axis) {
				return "";
			}
		});
		return lineChart;
	}

	private Button buildButton() {
		button = findViewById(R.id.btn);
		return button;
	}

	private YViewPager buildPager() {
		viewPager = findViewById(R.id.Ypager);
		layoutInflater =LayoutInflater.from(this);

		final View view1 = layoutInflater.inflate(R.layout.first_laout,null);
		View view2 = layoutInflater.inflate(R.layout.second_layout,null);

		view1.findViewById(R.id.btn).setOnClickListener(this);
		viewList.add(view1);
		viewList.add(view2);

		viewAdapter = new Viewadapter(viewList);
		viewPager.setAdapter(viewAdapter);
		viewPager.addOnPageChangeListener(new YViewPager.OnPageChangeListener() {
			@Override
			public void onPageScrolled(int i, float v, int i1) {

			}

			@Override
			public void onPageSelected(int i) {
				if (i == 1 && changeflag ==true ) {
					lineChart.setData(chartUtils.getlineData(tidePointList));
					lineChart.animateY(2000, Easing.EasingOption.EaseInCubic);
					changeflag = false;
				}
			}

			@Override
			public void onPageScrollStateChanged(int i) {

			}
		});
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
			//加上时区偏移的8h
			if(i%2==0){
				tidePointList.add(
						new TidePoint(
								Long.parseLong(tidedata[i].replace(" ","")),
								Integer.parseInt(tidedata[i+1].replace(" ",""))));
			}
		}
//		for(TidePoint t:tidePointList){
//			Log.e(TAG,t.toString());
//		}
		return tidePointList;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		changeflag = true;
		super.onActivityResult(requestCode, resultCode, data);
		final int code = data.getIntExtra("poscode",39);
		String city = data.getStringExtra("chooseCity");
		city_textview.setText(city);
		getDataAndDraw(code);
	}

	public void getDataAndDraw(final int code) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				jsoupString = catchbyJsoup.getData(code);
				tidePointList = putDataIn(jsoupString);
				TidePoint heigthpoint = getHeightpoint(tidePointList);
				TidePoint lowpoint = getLowpoint(tidePointList);
				TidePoint currpoint = getCurrPoint(tidePointList);
				//lineChart.setData(chartUtils.getlineData(tidePointList));

				drawLimitLine(currpoint);
				lineChart.notifyDataSetChanged();
				lineChart.invalidate();
				currHeighView.setText("当前潮高:"+currpoint.getHeigth()+"cm");
				halView.setText("最高点:"+heigthpoint.getHeigth()+"cm("+tools.UnxiTimetoDate(heigthpoint.getTime())+")");
				lowView.setText("最低点:"+lowpoint.getHeigth()+"cm("+tools.UnxiTimetoDate(lowpoint.getTime())+")");
			}
		}).start();
	}

	private void drawLimitLine(TidePoint currpoint) {
		if (xAxis.getLimitLines().size()!=0) {
			xAxis.removeAllLimitLines();
		}
		LimitLine limitLine = new LimitLine(currpoint.getTime());
		LimitLine rlimitLine = new LimitLine(currpoint.getTime());

		rlimitLine.setLabel("");
		rlimitLine.setLabel(currpoint.getHeigth()+"cm");
		rlimitLine.setTextColor(Color.WHITE);
		rlimitLine.setTextSize(14);
		limitLine.setLabel("");
		limitLine.setLabel("当前高度");
		limitLine.setTextSize(14);
		limitLine.setTextColor(Color.WHITE);
		rlimitLine.setTextColor(Color.WHITE);
		limitLine.setLabelPosition(LimitLine.LimitLabelPosition.LEFT_TOP);
		rlimitLine.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
		limitLine.setLineWidth(2f);
		limitLine.setLineColor(Color.RED);
		xAxis.addLimitLine(limitLine);
		xAxis.addLimitLine(rlimitLine);
	}

	private TidePoint getLowpoint(List<TidePoint> tidePointList) {
		int currl = tidePointList.get(0).getHeigth();
		TidePoint heigthPoint = tidePointList.get(0);
		for(TidePoint t:tidePointList){
			if(t.getHeigth()<currl){
				currl = t.getHeigth();
				heigthPoint = t;
			}
		}
		return heigthPoint;
	}

	private TidePoint getHeightpoint(List<TidePoint> tidePointList) {
		int currh = tidePointList.get(0).getHeigth();
		TidePoint heigthPoint = tidePointList.get(0);
		for (TidePoint t : tidePointList) {
			if (t.getHeigth() > currh) {
				currh = t.getHeigth();
				heigthPoint = t;
			}
		}
		return heigthPoint;
	}

	private TidePoint getCurrPoint(List<TidePoint> tidePointList) {
		long now = new Date().getTime()/1000;
		long der  = Math.abs(tidePointList.get(0).getTime()-now);
		TidePoint currpoint = tidePointList.get(0);
		for (TidePoint t:tidePointList) {
			long d = Math.abs(t.getTime()-now);
			if(d<der) {
				der = d;
				currpoint = t;
			}
		}
		return currpoint;
	}


}
