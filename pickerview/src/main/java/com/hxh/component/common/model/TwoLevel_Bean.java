package com.hxh.component.common.model;

import java.util.ArrayList;

/**
 * 市
 */
public class TwoLevel_Bean {
	private String name;
	private ArrayList<ThreeLevel_Bean> data;
	private String id;//二级数据的的id

	private String extra;// 额外数据


	public TwoLevel_Bean() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<ThreeLevel_Bean> getData() {
		return data;
	}

	public void setData(ArrayList<ThreeLevel_Bean> data) {
		this.data = data;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getExtra() {
		return extra;
	}

	public void setExtra(String extra) {
		this.extra = extra;
	}



	@Override
	public boolean equals(Object obj) {
		TwoLevel_Bean bean = obj instanceof TwoLevel_Bean ? ((TwoLevel_Bean) obj) : null;

		return bean.getName().equals(this.name);
	}

	@Override
	public String toString() {
		return name;
	}
}

