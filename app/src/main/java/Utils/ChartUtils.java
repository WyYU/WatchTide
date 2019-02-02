package Utils;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;

import com.github.mikephil.charting.components.IMarker;
import com.github.mikephil.charting.components.MarkerView;
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


	public List<Entry> tidetoEntry(List tidePointList){
		List<Entry> pointValues = new LinkedList<>();
		for (int i = 0; i < tidePointList.size(); i++) {
			TidePoint point = (TidePoint) tidePointList.get(i);
			pointValues.add(new Entry(point.getTime(), point.getHeigth()));
		}
		return pointValues;
	}

	public LineData getlineData(List<TidePoint> tidePointList) {
		final int Data_COUNT = 5;

		LineDataSet lineDataSet = new LineDataSet(tidetoEntry(tidePointList),null);
		lineDataSet.setColor(Color.parseColor("#5dbcfe"));
		//设置该线的宽度
		lineDataSet.setLineWidth(1f);
		//设置每个坐标点的圆大小
		lineDataSet.setCircleRadius(4f);
		//设置不能选中 flase
		lineDataSet.setHighlightEnabled(true);
		//设置是否画圆
		lineDataSet.setDrawCircles(true);
		lineDataSet.setDrawValues(false);//绘制坐标点的值
		lineDataSet.setDrawFilled(true);//向下填充
		lineDataSet.setCircleColor(Color.parseColor("#5bbcff"));

		return new LineData(lineDataSet);
	}


	class MarkView extends MarkerView{
		/**
		 * Constructor. Sets up the MarkerView with a custom layout resource.
		 *
		 * @param context
		 * @param layoutResource the layout resource to use for the MarkerView
		 */
		public MarkView(Context context, int layoutResource) {
			super(context, layoutResource);
		}
	}
}
