package Utils;

import android.graphics.Color;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.LinkedList;
import java.util.List;

import Modle.TidePoint;

/**
 * Created by dell on 2019/1/6 0006.
 */

public class ChartUtils {
	public List<TidePoint> getTidePointList() {
		return tidePointList;
	}

	public void setTidePointList(List<TidePoint> tidePointList) {
		this.tidePointList = tidePointList;
	}

	private List<TidePoint> tidePointList;

	public List<Entry> TidetoEntry(List tidePointList){
		List<Entry> pointValues = new LinkedList<>();
		for (int i = 0; i < tidePointList.size(); i++) {
			TidePoint point = (TidePoint) tidePointList.get(i);
			pointValues.add(new Entry(i, point.getHeigth()));
		}
		return pointValues;
	}

	public LineData getlineData(List<TidePoint> tidePointList) {
		final int Data_COUNT = 5;

		LineDataSet lineDataSet = new LineDataSet(TidetoEntry(tidePointList),"");
		lineDataSet.setColor(Color.parseColor("#5dbcfe"));
		//设置该线的宽度
		lineDataSet.setLineWidth(1f);
		//设置每个坐标点的圆大小
		lineDataSet.setCircleRadius(4f);
		//设置不能选中 flase
		lineDataSet.setHighlightEnabled(true);
		//设置是否画圆
		lineDataSet.setDrawCircles(true);
		lineDataSet.setCircleColor(Color.parseColor("#5bbcff"));

		return new LineData(lineDataSet);
	}
}
