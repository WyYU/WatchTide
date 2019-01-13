package Adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import cn.youngkaaa.yviewpager.YPagerAdapter;

/**
 * Created by dell on 2019/1/12 0012.
 */

public class Viewadapter extends YPagerAdapter {
	private List<View> viewList;


	public Viewadapter(List<View> viewList) {
		this.viewList = viewList;
	}

	@Override
	public int getCount() {
		return viewList.size();
	}

	@Override
	public boolean isViewFromObject(View view, Object o) {
		return view == o;
	}

	//对应页卡添加上数据
	public Object instantiateItem(ViewGroup container, int position) {
		container.addView(viewList.get(position));//千万别忘记添加到container
		return viewList.get(position);
	}

}
