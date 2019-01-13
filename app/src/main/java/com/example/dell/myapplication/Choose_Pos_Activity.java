package com.example.dell.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import Data.SpinnerSDdata;
import Modle.SdMap;

public class Choose_Pos_Activity extends WearableActivity implements View.OnClickListener {
	private Spinner spinner;
	private SdMap sdMap;
	private Button corfim_btn;
	int chooseid;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choose__pos);
		sdMap = new SdMap();
		initView();
	}

	private void initView() {
		spinner = buildSpinner();
		corfim_btn = buildcorfim((int)R.id.corfim_btn);
	}

	private Button buildcorfim(int i) {
		corfim_btn = findViewById(i);
		corfim_btn.setOnClickListener(this);
		return corfim_btn;
	}

	private Spinner buildSpinner() {
		spinner = findViewById(R.id.spinner);
		ArrayAdapter<String> adapter =
				new ArrayAdapter<String>(
						Choose_Pos_Activity.this,android.R.layout.simple_list_item_1,new SpinnerSDdata().getSDData());
		spinner.setAdapter(adapter);
		return spinner;
	}


	@Override
	public void onClick(View v) {
		String string = (String) spinner.getSelectedItem();
		chooseid = sdMap.get(string);
		Intent intent = new Intent();
		intent.putExtra("posCode",chooseid);
		intent.putExtra("chooseCity",string);
		setResult(0,intent);
		finish();
	}
}
